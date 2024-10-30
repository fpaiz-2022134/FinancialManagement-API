package com.francopaiz.financialManagementAPI.category;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.francopaiz.financialManagementAPI.controller.category.CategoryController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId("1");
        category.setName("Groceries");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        when(categoryService.getCategories()).thenReturn(categories);

        // Act
        List<Category> result = categoryController.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getName());
        verify(categoryService, times(1)).getCategories();
    }

    @Test
    void testFindById() {
        // Arrange
        when(categoryService.findCategoryById("1")).thenReturn(category);

        // Act
        Category result = categoryController.findById("1");

        // Assert
        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).findCategoryById("1");
    }

    @Test
    void testSave() {
        // Arrange
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryController.save(category);

        // Assert
        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(categoryService.updateCategory(eq("1"), any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryController.update("1", category);

        // Assert
        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).updateCategory(eq("1"), any(Category.class));
    }

    @Test
    void testDeleteById() {
        // Act
        categoryController.deleteById("1");

        // Assert
        verify(categoryService, times(1)).deleteCategory("1");
    }
}
