package com.gugulethugillz.auctionsystem.repository;

import com.gugulethugillz.auctionsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonBaseRepository<T extends Person> extends JpaRepository<T, Long> {
}
