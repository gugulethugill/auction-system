package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.asset.controller.AssetController;
import com.gugulethugillz.auctionsystem.asset.form.AssetForm;
import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.asset.service.AssetService;
import com.gugulethugillz.auctionsystem.bid.service.BidService;
import com.gugulethugillz.auctionsystem.category.service.CategoryService;
import com.gugulethugillz.auctionsystem.person.model.Admin;
import com.gugulethugillz.auctionsystem.person.model.Person;
import com.gugulethugillz.auctionsystem.person.model.User;
import com.gugulethugillz.auctionsystem.resource.FileResourceForm;
import com.gugulethugillz.auctionsystem.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AssetControllerTest {

    @Mock
    private AssetService assetService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BidService bidService;

    @Mock
    private StorageService storageService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AssetController assetController;

    private Admin admin;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        admin = new Admin();
        admin.setFirstName("Tinashe");
        admin.setUsername("admin@auctionsystem.co.zw");
        admin.setPassword("#pass123");
    }



    @Test
    public void testDisplayAssetsByCategory() {
        User user = new User();
        user.setFirstName("John");
        Long categoryId = 1L;

        when(assetService.findByCategoryId(categoryId)).thenReturn(Arrays.asList(new Asset()));

        String viewName = assetController.displayAssetsByCategory(user, categoryId, model);

        verify(model).addAttribute("authUser", user);
        verify(model).addAttribute("message", "Welcome " + user.getFirstName());
        verify(model).addAttribute("categories", categoryService.findAll());
        verify(model).addAttribute("assets", assetService.findByCategoryId(categoryId));
        assertEquals("assets_user", viewName);
    }

    @Test
    public void testDisplayAssets() {
        User user = new User();
        user.setFirstName("Jane");
        String keyword = "laptop";

        when(assetService.searchByKeyWord(keyword)).thenReturn(Arrays.asList(new Asset()));

        String viewName = assetController.displayAssets(user, model, keyword, redirectAttributes);

        verify(model).addAttribute("authUser", user);
        verify(model).addAttribute("keyWord", keyword);
        verify(model).addAttribute("message", "Welcome " + user.getFirstName());
        verify(model).addAttribute("assets", assetService.searchByKeyWord(keyword));
        assertEquals("assets_user", viewName);
    }

    @Test
    public void testDeleteAssetExists() {
        Long assetId = 1L;
        Asset asset = new Asset();
        when(assetService.findById(assetId)).thenReturn(Optional.of(asset));

        String viewName = assetController.delete(assetId, redirectAttributes);

        verify(assetService).deleteAssetById(assetId);
        verify(redirectAttributes).addFlashAttribute("info", "Successful Delete");
        assertEquals("redirect:/admin/assets", viewName);
    }

    @Test
    public void testDeleteAssetNotExists() {
        Long assetId = 1L;
        when(assetService.findById(assetId)).thenReturn(Optional.empty());

        String viewName = assetController.delete(assetId, redirectAttributes);

        verify(redirectAttributes, never()).addFlashAttribute(anyString(), anyString());
        assertEquals("redirect:/admin/assets", viewName);
    }

//    @Test
//    @WithMockUser("ADMIN")
//    public void testEditAssetExists() {
//        Long assetId = 1L;
//        Asset asset = new Asset();
//        asset.setId(assetId);
//        when(assetService.findById(assetId)).thenReturn(Optional.of(asset));
//
//        String viewName = assetController.editAsset(assetId, model, redirectAttributes);
//
//        verify(model).addAttribute(anyString(), any(AssetForm.class));
//        assertEquals("edit_asset", viewName);
//    }

    @Test
    public void testEditAssetNotExists() {
        Long assetId = 1L;
        when(assetService.findById(assetId)).thenReturn(Optional.empty());

        String viewName = assetController.editAsset(assetId, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("Error", "Asset does not exist wih ID" + assetId);
        assertEquals("redirect:/admin", viewName);
    }

//    @Test
//    public void testCreateAsset() {
//        Admin admin= new Admin();
//        admin.setUsername("admin");
//        AssetForm assetForm = new AssetForm();
//        assetForm.setCategoryId(1L);
//        assetForm.setName("Test Asset");
//
//        when(bindingResult.hasErrors()).thenReturn(false);
//        when(categoryService.findAll()).thenReturn(Arrays.asList());
//        when(assetService.createAsset(any(Asset.class))).thenReturn(Optional.of(new Asset()));
//
//        String viewName = assetController.createAsset(admin, assetForm, bindingResult, model, redirectAttributes);
//
//        verify(assetService).createAsset(any(Asset.class));
//        verify(redirectAttributes).addFlashAttribute("info", "Created successfully");
//        assertEquals("redirect:/admin/assets", viewName);
//    }

    @Test
    public void testAddAssetImageFailsWithBindingErrors() {
        FileResourceForm fileResourceForm = new FileResourceForm();
        fileResourceForm.setId(1L);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = assetController.addAssetImage(null, fileResourceForm, bindingResult, model, redirectAttributes);

        verify(bindingResult).hasErrors();
        assertEquals("redirect:/admin/assets/" + fileResourceForm.getId(), viewName);
    }
}
