package com.gugulethugillz.auctionsystem.service.ifaces;

import com.gugulethugillz.auctionsystem.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    Optional<Category> createCategory(Category category);

    List<Category> findAll();

    Optional<Category> updateCategory(Category category);

    void deleteCategoryById(Long id);


}
