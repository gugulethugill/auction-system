package com.gugulethugillz.auctionsystem.bid.repository;

import com.gugulethugillz.auctionsystem.common.enums.EntityStatus;
import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.person.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query("SELECT b " +
            " FROM Bid b LEFT OUTER JOIN Asset a ON b.asset.id = a.id " +
            " WHERE b.value = (SELECT MAX(bid.value) FROM Bid bid WHERE bid.asset.id = a.id)" +
            " AND a.bidEndDate <= :cutOffDate AND a.status = :status")
    List<Bid> getWinningBids(@Param("cutOffDate") OffsetDateTime cutOffDate, @Param("status") EntityStatus status);

    List<Bid> findByAssetIdOrderByValueDesc(Long asset_id);

    @Query("SELECT b " +
            " FROM Bid b LEFT OUTER JOIN Asset a ON b.asset.id = a.id " +
            " WHERE :referenceTime BETWEEN a.bidStartDate AND a.bidEndDate AND b.user.id = :userId")
    List<Bid> getActiveBids(@Param("userId") Long userId, @Param("referenceTime") OffsetDateTime referenceTime);

    List<Bid> findAllByUser(User user);

    List<Bid> findByUser(Long userId);


}
