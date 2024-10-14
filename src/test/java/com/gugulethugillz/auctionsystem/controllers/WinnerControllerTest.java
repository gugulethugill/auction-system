package com.gugulethugillz.auctionsystem.controllers;


import com.gugulethugillz.auctionsystem.person.model.User;
import com.gugulethugillz.auctionsystem.winner.controller.WinnerController;
import com.gugulethugillz.auctionsystem.winner.model.Winner;
import com.gugulethugillz.auctionsystem.winner.service.WinnerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WinnerController.class)
public class WinnerControllerTest {

    @MockBean
    private WinnerService winnerService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    private Winner winner;
    private User user;


    @BeforeEach
    public void init() {
        winner = new Winner();
        winner.setName("Kombo");
        winner.setId(1L);



    }

    @AfterEach
    public void destroy() {
        winner = null;
    }



    @Test
    @WithMockUser(roles = {"USER"})
    public void givenTheMultipleWinnerPath_whenGetRequest_thenShouldReturnPageContainingWinners() throws Exception {
        mockMvc.perform(get("/winner/multiple"))
                .andExpect(view().name("multiple"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Winners Listing Grid")));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void givenTheWinnerPath_whenGetRequest_thenShouldReturnPageContainingWinner() throws Exception {
        mockMvc.perform(get("/winner/winner"))
                .andExpect(view().name("winner"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Final Winner")));
    }







}


