package com.gugulethugillz.auctionsystem.repository;

import com.gugulethugillz.auctionsystem.model.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

}
