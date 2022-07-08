package com.sujankhadka.blog.services;

import com.sujankhadka.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    CategoryDto getCategoryById(Integer categoryId);

    List<CategoryDto> getAllCategories();

    void deleteCategory(Integer categoryId);
}
