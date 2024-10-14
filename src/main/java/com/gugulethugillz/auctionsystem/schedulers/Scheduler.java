package com.gugulethugillz.auctionsystem.schedulers;

import com.gugulethugillz.auctionsystem.common.enums.EntityStatus;
import com.gugulethugillz.auctionsystem.common.enums.PaymentMethod;
import com.gugulethugillz.auctionsystem.common.enums.PaymentStatus;
import com.gugulethugillz.auctionsystem.model.PaymentRequest;
import com.gugulethugillz.auctionsystem.model.StatusHistory;
import com.gugulethugillz.auctionsystem.model.User;
import com.gugulethugillz.auctionsystem.model.Winner;
import com.gugulethugillz.auctionsystem.repository.StatusHistoryRepository;
import com.gugulethugillz.auctionsystem.schedulers.processors.api.WinnerProcessor;
import com.gugulethugillz.auctionsystem.service.ifaces.PaymentRequestService;
import com.gugulethugillz.auctionsystem.service.ifaces.mail.AuctionSystemMailer;
import com.gugulethugillz.auctionsystem.service.ifaces.payments.PaynowProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.gugulethugillz.auctionsystem.common.Utils.getPaymentRequestHelper;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final WinnerProcessor winnerProcessor;
    private final PaynowProcessor paynowProcessor;
    private final PaymentRequestService paymentRequestService;
    private final StatusHistoryRepository statusHistoryRepository;
    private final AuctionSystemMailer mailer;

    //Second    Minutes     Hours       Day of month    Month       Day of Week
    @Scheduled(cron = "0 * * * * ?")
    public void schedulerMethod() {
//        log.info("The scheduler method was called at {}", OffsetDateTime.now());
        processWinners();
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void schedulerForPaynowPollRequestsMethod() {
        paynowProcessor.handlePollRequest();
    }

    private void processWinners() {
        List<Winner> winners = winnerProcessor.determineWinners();
        Map<String, User> userMap = winners.stream().collect(Collectors.toMap(w -> w.getUser().getEmail(), Winner::getUser, (k1, k2) -> k1));
        Map<String, List<Winner>> winningUsersMap = winners.stream()
                .collect(Collectors.groupingBy(winner -> winner.getUser().getEmail()));
        winningUsersMap.entrySet().forEach(stringListEntry -> {
            String email = stringListEntry.getKey();
            List<Winner> winnerList = stringListEntry.getValue();
            processAndSendEmail(userMap.get(email), winnerList);
        });
        createPaymentRequests(winners);
    }

    private void createPaymentRequests(List<Winner> winners) {
//        log.info("Creating payment requests for {} winners", winners.size());
        final List<PaymentRequest> paymentRequests = winners.stream().map(Scheduler::createPaymentRequest).collect(Collectors.toList());
        List<PaymentRequest> savedPaymentRequests = paymentRequestService.getRepository().saveAll(paymentRequests);
        List<StatusHistory> statusHistories = savedPaymentRequests.stream()
                .map(paymentRequest -> getStatusHistory(paymentRequest, "Scheduler Initiation"))
                .collect(Collectors.toList());
        statusHistoryRepository.saveAll(statusHistories);
    }

    private StatusHistory getStatusHistory(PaymentRequest paymentRequest, String description) {
        return getPaymentRequestHelper(paymentRequest, description, "scheduler");
    }

    private static PaymentRequest createPaymentRequest(Winner winner) {
        final PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setValue(winner.getBid().getValue());
        paymentRequest.setDate(OffsetDateTime.now());
        paymentRequest.setPaymentMethod(PaymentMethod.ECOCASH);
        paymentRequest.setPaymentStatus(PaymentStatus.PENDING);
        paymentRequest.setAsset(winner.getBid().getAsset());
        paymentRequest.setBid(winner.getBid());
        paymentRequest.setMobileNumber(winner.getUser().getPhone());
        paymentRequest.setUser(winner.getUser());
        paymentRequest.setDateCreated(OffsetDateTime.now());
        paymentRequest.setLastUpdated(OffsetDateTime.now());
        paymentRequest.setStatus(EntityStatus.ACTIVE);
        paymentRequest.setCreatedBy("system");
        paymentRequest.setLastUpdatedBy("system");
        return paymentRequest;
    }

    private void processAndSendEmail(User user, List<Winner> winnerList) {
        mailer.sendEmail("Congratulations", getWinnerMessage(user, winnerList), user.getEmail());
    }

    private String getWinnerMessage(User user, List<Winner> winnerList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Congratulations, ").append(user.getFullName()).append(", on participating in our last auction round.")
                .append("We are pleased to let you know that you have won the following items:")
        .append("\n\n");
        AtomicInteger count = new AtomicInteger(1);
        winnerList.forEach(winner -> {
            sb.append(count).append(".\t").append(winner.getBid().getAsset().getName())
                    .append("\tworth ").append(winner.getBid().getValue()).append("\n");
        });
        sb.append("\n\n").append("Please make your paymentRequest and bring your receipt for collection");
        return sb.toString();
    }
}
