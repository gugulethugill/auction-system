package com.gugulethugillz.auctionsystem.status;

import com.gugulethugillz.auctionsystem.status.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

}
