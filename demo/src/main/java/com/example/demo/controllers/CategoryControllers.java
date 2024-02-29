package com.example.demo.controllers;


import com.example.demo.models.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class CategoryControllers {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
