package com.gugulethugillz.auctionsystem.payment.repository;

import com.gugulethugillz.auctionsystem.common.enums.PaymentStatus;
import com.gugulethugillz.auctionsystem.payment.model.PaymentRequest;
import com.gugulethugillz.auctionsystem.report.projections.PaymentReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    @Query("SELECT p FROM PaymentRequest p WHERE p.user.username = :username")
    List<PaymentRequest> getByUsername(@Param("username") String username);

    List<PaymentRequest> findByUserId(Long userId);

    @Query(value = "SELECT * FROM Payment p WHERE p.asset LIKE %:keyword% or p.bid LIKE %:keyword% ", nativeQuery=true)
    List<PaymentRequest> findByKeyword(@Param("keyword") String keyword);

    @Query(nativeQuery = true,
            value = "SELECT to_char(sh.date_created, 'DD Mon YYYY HH12:MI:SS')                        AS datePaid, " +
                    "       pr.value                               AS amount, " +
                    "       a.name                                 AS assetName, " +
                    "       c.name                                 AS categoryName, " +
                    "       CONCAT(p.first_name, ' ', p.last_name) AS winnerName, " +
                    "       to_char(b.date_created, 'DD Mon YYYY HH12:MI:SS')                         AS winDate " +
                    "FROM payment_request pr " +
                    "         LEFT OUTER JOIN status_history sh ON pr.id = sh.payment_request_id " +
                    "         LEFT OUTER JOIN asset a ON pr.asset_id = a.id " +
                    "         LEFT OUTER JOIN category c ON a.category_id = c.id " +
                    "         LEFT OUTER JOIN person p ON pr.user_id = p.id " +
                    "         LEFT OUTER JOIN bid b ON pr.bid_id = b.id " +
                    "WHERE pr.date_created BETWEEN :start AND :end")
    List<PaymentReportEntry> getPaymentReportEntries(@Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);

    @Query(nativeQuery = true,
            value = "SELECT to_char(sh.date_created, 'DD Mon YYYY HH12:MI:SS')                        AS datePaid, " +
                    "       pr.value                               AS amount, " +
                    "       a.name                                 AS assetName, " +
                    "       c.name                                 AS categoryName, " +
                    "       CONCAT(p.first_name, ' ', p.last_name) AS winnerName, " +
                    "       to_char(b.date_created, 'DD Mon YYYY HH12:MI:SS')                         AS winDate " +
                    "FROM payment_request pr " +
                    "         LEFT OUTER JOIN status_history sh ON pr.id = sh.payment_request_id " +
                    "         LEFT OUTER JOIN asset a ON pr.asset_id = a.id " +
                    "         LEFT OUTER JOIN category c ON a.category_id = c.id " +
                    "         LEFT OUTER JOIN person p ON pr.user_id = p.id " +
                    "         LEFT OUTER JOIN bid b ON pr.bid_id = b.id " +
                    "WHERE pr.date BETWEEN :start AND :end")
    List<PaymentReportEntry> getPaymentReportEntriesByPaymentDate(@Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);

    List<PaymentRequest> findByPaymentStatus(PaymentStatus processingInProgress);
}
