package com.gugulethugillz.auctionsystem.schedulers.processors.impl;
import com.gugulethugillz.auctionsystem.common.enums.EntityStatus;
import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.winner.model.Winner;
import com.gugulethugillz.auctionsystem.schedulers.processors.api.WinnerProcessor;
import com.gugulethugillz.auctionsystem.asset.service.AssetService;
import com.gugulethugillz.auctionsystem.bid.service.BidService;
import com.gugulethugillz.auctionsystem.winner.service.WinnerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class WinnerProcessorImpl implements WinnerProcessor {
    private final BidService bidService;
    private final AssetService assetService;
    private final WinnerService winnerService;

    @Override
    public List<Winner> determineWinners() {
        OffsetDateTime now = OffsetDateTime.now();

        log.info("Starting the winners determiner job at {}", now);

        log.info("Fetching the winning bids");

//        log.info("Starting the winners determiner job at {}", now);
//        log.info("Fetching the winning bids");
        List<Bid> winningBids = bidService.getRepository().getWinningBids(now, EntityStatus.PENDING_PROCESSING);

        if(winningBids.isEmpty()) {
//            log.info("No winning bids found as at {}", now);
            return new ArrayList<>();
        }

        List<Asset> assets = winningBids.stream().map(Bid::getAsset).collect(Collectors.toList());
//        log.info("Found {} winning bids", winningBids.size());

        assets = updateAssetStatuses(assets, EntityStatus.PROCESSING_IN_PROGRESS);

//        log.info("Generating winner entities...");
        List<Winner> winners = winningBids.stream().map(bid -> {

            Winner winner = new Winner();
            winner.setName(bid.getUser().getFullName());
            winner.setBid(bid);
            winner.setUser(bid.getUser());
            winner.setDateCreated(OffsetDateTime.now());
            winner.setLastUpdated(OffsetDateTime.now());
            winner.setStatus(EntityStatus.ACTIVE);
            winner.setCreatedBy("system");
            winner.setLastUpdatedBy("system");
            return winner;
        }).collect(Collectors.toList());
//        log.info("Generated {} winners", winners.size());

        assets = updateAssetStatuses(assets, EntityStatus.PROCESSED);

        return winnerService.getRepository().saveAll(winners);
    }

    private List<Asset> updateAssetStatuses(List<Asset> assets, EntityStatus status) {
//        log.info("Updating asset statuses to {}", status);
        assets.forEach(asset -> asset.setStatus(status));
        List<Asset> updatedAssets = assetService.getRepository().saveAll(assets);
//        log.info("Updated assets to {}", status);
        return updatedAssets;
    }
}
