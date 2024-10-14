package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.category.controller.CategoryController;
import com.gugulethugillz.auctionsystem.category.form.CategoryForm;
import com.gugulethugillz.auctionsystem.category.model.Category;
import com.gugulethugillz.auctionsystem.category.service.CategoryService;
import com.gugulethugillz.auctionsystem.person.model.Admin;
import com.gugulethugillz.auctionsystem.person.model.Person;
import com.gugulethugillz.auctionsystem.person.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CategoryController categoryController;

    private Person person;
    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        person = new User();
        person.setUsername("testUser");

        admin = new Admin();
        admin.setFirstName("John");
        admin.setMiddleName("");
        admin.setLastName("Doe");
        admin.setEmail("email@example.com");
        admin.setUsername("email@example.com");
        admin.setId(1L);
    }

    @Test
    void testViewCategories() {
        when(categoryService.findAll()).thenReturn(null);

        String result = categoryController.viewCategories(person, model);

        verify(model).addAttribute("authUser", person);
        verify(model).addAttribute("listCategories", null);
        assertEquals("/admin/view_categories", result);
    }

    @Test
    void testCreateCategoryPostWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = categoryController.createCategory(person, new CategoryForm(), bindingResult, model, redirectAttributes);

        verify(bindingResult).hasErrors();
        verify(redirectAttributes).addFlashAttribute(eq("Errors"), any());
        assertEquals("redirect:/admin/categories", result);
    }

    @Test
    void testCreateCategoryPostCategoryExists() {
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName("ExistingCategory");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(categoryService.findByName("ExistingCategory")).thenReturn(Optional.of(new Category()));

        String result = categoryController.createCategory(person, categoryForm, bindingResult, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("error", "Could not create category, category already exists");
        assertEquals("redirect:/admin/categories", result);
    }

    @Test
    void testCreateCategoryPostSuccess() {
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName("NewCategory");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(categoryService.findByName("NewCategory")).thenReturn(Optional.empty());
        when(categoryService.createCategory(any(Category.class))).thenReturn(Optional.of(new Category()));

        String result = categoryController.createCategory(person, categoryForm, bindingResult, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("info", "Created successfully");
        assertEquals("redirect:/admin/categories", result);
    }

    @Test
    void testEditCategoryWithInvalidId() {
        long categoryId = 1L;
        when(categoryService.findById(categoryId)).thenReturn(Optional.empty());

        String result = categoryController.editCategory(categoryId, model, redirectAttributes, person);

        verify(redirectAttributes).addFlashAttribute("Error", "Category does not exist with ID " + categoryId);
        assertEquals("redirect:/admin/categories", result);
    }

    @Test
    void testUpdateCategoryWithErrors() {
        long categoryId = 1L;
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = categoryController.updateCategory(person, categoryId, new CategoryForm(), bindingResult, model, redirectAttributes);

        verify(bindingResult).hasErrors();
        assertEquals("/admin/edit_category", result);
    }

    @Test
    void testUpdateCategorySuccess() throws Exception {
        long categoryId = 1L;
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName("UpdatedCategory");

        Category category = new Category();
        category.setId(categoryId);
        category.setName("OldCategory");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(categoryService.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryService.updateCategory(any(Category.class))).thenReturn(Optional.of(category));

        String result = categoryController.updateCategory(person, categoryId, categoryForm, bindingResult, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("info", "Successful Update");
        assertEquals("redirect:/admin/categories", result);
    }

    @Test
    void testDeleteCategorySuccess() {
        long categoryId = 1L;
        when(categoryService.findById(categoryId)).thenReturn(Optional.of(new Category()));

        String result = categoryController.deleteCategory(categoryId, redirectAttributes, person);

        verify(categoryService).deleteCategoryById(categoryId);
        verify(redirectAttributes).addFlashAttribute("info", "Successful Delete of Category");
        assertEquals("redirect:/admin/categories", result);
    }

    @Test
    void testDeleteCategoryFailure() {
        long categoryId = 1L;
        when(categoryService.findById(categoryId)).thenReturn(Optional.empty());

        String result = categoryController.deleteCategory(categoryId, redirectAttributes, person);

        verify(redirectAttributes).addFlashAttribute("Error", "Category does not exist");
        assertEquals("redirect:/admin/categories", result);
    }
}
