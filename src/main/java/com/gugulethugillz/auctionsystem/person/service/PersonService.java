package com.gugulethugillz.auctionsystem.person.service;


import com.gugulethugillz.auctionsystem.person.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findById(Long id);
    Optional<Person> findByUsername(String username);
    Optional<Person> createPerson(Person person);
    List<Person> findAll();
    List<Person> findAllAdmins();
    List<Person> findAllUsers();
    List<Person> searchPersons(String searchPhrase);
}
