package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.bid.controller.BidController;
import com.gugulethugillz.auctionsystem.bid.form.BidForm;
import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.person.model.Admin;
import com.gugulethugillz.auctionsystem.person.model.Person;
import com.gugulethugillz.auctionsystem.person.model.User;
import com.gugulethugillz.auctionsystem.asset.service.AssetService;
import com.gugulethugillz.auctionsystem.bid.service.BidService;
import com.gugulethugillz.auctionsystem.category.service.CategoryService;
import com.gugulethugillz.auctionsystem.person.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BidControllerTest {

    @Mock
    private BidService bidService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private PersonService personService;

    @Mock
    private AssetService assetService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private BidController bidController;

    private Person person;
    private Admin admin;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        admin = new Admin();
        admin.setUsername("testUser");

        user = new User();
        user.setUsername("testUser");
    }

    @Test
    void testGetBids() {
        when(personService.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(bidService.findAllByUser(any(User.class))).thenReturn(null);

        String result = bidController.getBids(user, model);

        verify(model).addAttribute("authUser", user);
        verify(model).addAttribute("bids", null);
        assertEquals("all_bids", result);
    }

//    @Test
//    void testActiveBids() {
//        when(personService.findByUsername(anyString())).thenReturn(Optional.of(admin));
//        when(bidService.getActiveBids(anyLong())).thenReturn(null);
//        when(categoryService.findAll()).thenReturn(null);
//
//        String result = bidController.activeBids(admin, model);
//
//        verify(model).addAttribute("authUser", admin);
//        verify(model).addAttribute("message", "Welcome " + admin.getFirstName());
//        verify(model).addAttribute("categories", null);
//        verify(model).addAttribute("bids", null);
//        assertEquals("active_bids", result);
//    }

    @Test
    void testViewAdminBids() {
        when(bidService.findAllBids()).thenReturn(null);

        String result = bidController.viewAdminBids(model);

        verify(model).addAttribute("bids", null);
        assertEquals("admin/admin_bids", result);
    }

    @Test
    void testCreateBid() {
        long assetId = 1L;
        when(assetService.findById(assetId)).thenReturn(Optional.of(new Asset()));

        String result = bidController.createBid(assetId, model);

        verify(model).addAttribute("asset", Optional.of(new Asset()));
        verify(model).addAttribute("bidForm", new BidForm());
        assertEquals("create_bid", result);
    }

    @Test
    void testCreateBidPostWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = bidController.createBid(person, new BidForm(), bindingResult, model, redirectAttributes);

        verify(bindingResult).hasErrors();
        assertEquals("create_bid", result);
    }
}
