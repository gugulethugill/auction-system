package com.gugulethugillz.auctionsystem.repository;

import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.bid.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BidRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BidRepository bidRepository;

    @BeforeEach
    public void init() {

    }

    @Test
    public void givenABid_whenPersistBid_thenShouldReturnPersistedBid() throws Exception {
        Bid bid = new Bid();
        Bid newBid = entityManager.persist(bid);
        assertThat(newBid).isNotNull();
    }
}
