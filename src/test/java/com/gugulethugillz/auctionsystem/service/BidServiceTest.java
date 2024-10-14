package com.gugulethugillz.auctionsystem.service;

import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.asset.repository.AssetRepository;
import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.bid.repository.BidRepository;
import com.gugulethugillz.auctionsystem.bid.service.impl.BidServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BidServiceTest {

    @InjectMocks
    private BidServiceImpl bidService;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AssetRepository assetRepository;

    private Bid bid;
    private Asset asset;

    @BeforeEach
    public void init() {
        asset = new Asset();
        asset.setId((long) 2);
        asset.setName("Table");

        bid = new Bid();
        bid.setId((long) 2);

    }

    @AfterEach
    public void cleanUp() {asset = null;
    }

    @Test
    public void givenAnId_whenFindBid_shouldReturnBid() throws Exception {
        when(bidRepository.findById(any())).thenReturn(Optional.of(bid));

        Optional<Bid> bidOptional = bidService.findById((long) 2);
        assertTrue(bidOptional.isPresent(), "Bid not found");
        Bid bid = bidOptional.get();
        assertEquals(2, bid.getId());
    }


    @Test
    public void givenAssetRecords_whenFindAllBids_thenShouldReturnNonEmptyList() throws Exception {
        when(bidRepository.findAll()).thenReturn(List.of(bid));
        List<Bid> bidList = bidService.findAllBids();
        assertAll(
                () -> assertThat(bidList).isNotNull(),
                () -> assertTrue(bidList.size() > 0),
                () -> assertThat(bidList.get(0).getId()).isEqualTo(bid.getId())
        );
    }

    @Test
    public void givenNoAssetRecords_whenFindAllBids_thenShouldReturnEmptyList() throws Exception {
        List<Bid> bidList = bidService.findAllBids();
        assertAll(
                () -> assertThat(bidList).isNotNull(),
                () -> assertEquals(bidList.size(), 0)
        );
    }


}

