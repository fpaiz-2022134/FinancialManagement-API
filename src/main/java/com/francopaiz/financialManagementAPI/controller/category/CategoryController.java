package com.francopaiz.financialManagementAPI.controller.category;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> findAll(){
        return categoryService.getCategories();
    }

    @GetMapping("/{idCategory}")
    public Category findById(@PathVariable String idCategory){
        return categoryService.findCategoryById(idCategory);
    }

    @PostMapping()
    public Category save(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @PutMapping("/{idCategory}")
    public Category update(@PathVariable String idCategory, @RequestBody Category category){
        return categoryService.updateCategory(idCategory, category);
    }

    @DeleteMapping("/{idCategory}")
    public void deleteById(@PathVariable String idCategory){
        categoryService.deleteCategory(idCategory);
    }
}
