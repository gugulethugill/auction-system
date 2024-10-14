package com.gugulethugillz.auctionsystem.repository;

import com.gugulethugillz.auctionsystem.person.model.Admin;
import com.gugulethugillz.auctionsystem.person.repository.AdminRepository;
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
public class AdminRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    public void init() {

    }

    @Test
    public void givenAAdmin_whenPersistAdmin_thenShouldReturnPersistedAdmin() throws Exception {
        Admin bid = new Admin();
        Admin newAdmin = entityManager.persist(bid);
        assertThat(newAdmin).isNotNull();
    }
}
