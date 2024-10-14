package com.gugulethugillz.auctionsystem.person.repository;

import com.gugulethugillz.auctionsystem.person.model.Admin;
import com.gugulethugillz.auctionsystem.person.model.Person;
import com.gugulethugillz.auctionsystem.person.repository.PersonBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends PersonBaseRepository<Admin> {
    Optional<Admin> findByUsername(String username);
    @Query("SELECT p FROM HumanBeing p WHERE LOWER(p.firstName) LIKE LOWER(:searchPhrase)")
    List<? extends Person> search(@Param("searchPhrase") String searchPhrase);
}
