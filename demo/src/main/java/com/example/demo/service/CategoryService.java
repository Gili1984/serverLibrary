package com.example.demo.service;

import com.example.demo.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class CategoryService {

    public static List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }

}
