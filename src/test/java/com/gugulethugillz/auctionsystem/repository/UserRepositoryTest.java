package com.gugulethugillz.auctionsystem.repository;

import com.gugulethugillz.auctionsystem.person.model.User;
import com.gugulethugillz.auctionsystem.person.repository.UserRepository;
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
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {

    }

    @Test
    public void givenAUser_whenPersistUser_thenShouldReturnPersistedUser() throws Exception {
        User user = new User();
        User newUser = entityManager.persist(user);
        assertThat(newUser).isNotNull();
    }
}
