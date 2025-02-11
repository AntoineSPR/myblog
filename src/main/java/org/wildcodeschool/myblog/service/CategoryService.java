package org.wildcodeschool.myblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myblog.dto.CategoryDTO;
import org.wildcodeschool.myblog.exception.ResourceNotFoundException;
import org.wildcodeschool.myblog.mapper.CategoryMapper;
import org.wildcodeschool.myblog.model.Category;
import org.wildcodeschool.myblog.repository.CategoryRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + id + " n'existe pas."));
    }

    public CategoryDTO createCategory(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    category.setUpdatedAt(LocalDateTime.now());
                    Category updatedCategory = categoryRepository.save(category);
                    return categoryMapper.convertToDTO(updatedCategory);
                })
                .orElse(null);
    }

    public boolean deleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return true;
                })
                .orElse(false);
    }
}