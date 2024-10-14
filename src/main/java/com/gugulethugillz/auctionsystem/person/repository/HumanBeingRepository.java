package com.gugulethugillz.auctionsystem.person.repository;

import com.gugulethugillz.auctionsystem.integrations.camel.HumanBeing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanBeingRepository extends JpaRepository<HumanBeing, Long> {

}
