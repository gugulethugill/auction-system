package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.model.Person;
import com.gugulethugillz.auctionsystem.service.ifaces.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public List<Person> getPeople() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") Long id) {
        return personService.findById(id).orElse(null);
    }
}
