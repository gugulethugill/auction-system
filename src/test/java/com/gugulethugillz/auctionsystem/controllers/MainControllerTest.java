package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.MainController;
import com.gugulethugillz.auctionsystem.asset.service.AssetService;
import com.gugulethugillz.auctionsystem.category.service.CategoryService;
import com.gugulethugillz.auctionsystem.common.enums.UserRole;
import com.gugulethugillz.auctionsystem.person.model.Admin;
import com.gugulethugillz.auctionsystem.person.model.User;
import com.gugulethugillz.auctionsystem.person.service.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    @MockBean
    private PersonService personService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private AssetService assetService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setFirstName("Alfred");
        user.setUsername("alfred.samanga@gmail.com");
    }

    @AfterEach
    public void destroy() {
        user = null;
    }

    @Test
    @WithMockUser("USER")
    public void givenTheRootPath_whenGetRequest_thenShouldReturnPageContainingString() throws Exception {
        mockMvc.perform(get("/")
                .with(user(user))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/assets"));
    }

    @Test
    public void givenTheLoginCredentials_whenLogin_thenShouldRedirectToRootPage() throws Exception {
        when(userDetailsService.loadUserByUsername(any())).thenReturn(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            }

            @Override
            public String getPassword() {
                String password = passwordEncoder.encode("password");
                System.out.println("Password is " + password);
                return password;
            }

            @Override
            public String getUsername() {
                return "alfred.samanga@gmail.com";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        });
        mockMvc.perform(post("/login").with(csrf())
                .param("username", "alfred.samanga@gmail.com")
                .param("password", "password")
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testRenderHome() throws Exception {
        when(personService.createPerson(any())).thenReturn(Optional.of(new Admin()));

        mockMvc.perform(
                post("/register")
                        .with(csrf())
                        .param("userRole", UserRole.USER.name())
                        .param("firstName", "Alfd")
                        .param("middleName", "Tadyanemhandu")
                        .param("lastName", "Samanga")
                        .param("username", "alfred.samanga@gmail.com")
                        .param("password", "password")
                        .param("phone", "263772222499")
                        .param("email", "alfred.samanga@gmail.com")
                        .param("nationalId", "44-100818W75")
                        .param("passportNumber", "3333333")
                        .param("address", "602 Off Carrick Creagh")
                        .param("dateOfBirth", "2020-05-17")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
