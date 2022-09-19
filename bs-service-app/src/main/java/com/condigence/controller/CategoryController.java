package com.condigence.controller;
import com.condigence.model.Category;
import com.condigence.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bs-category")
public class CategoryController {

    @Autowired(required=true)
    private CategoryService categoryService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/")
    public List<Category> getAll() {
        return categoryService.getAll();
    }


    // get category by category name (equals())
    @GetMapping("/one-by-categoryName/{categoryName}")
    public Category getOneEmployeeByFirstName(@PathVariable String categoryName) {
        return categoryService.getOneCategoryByCategoryName(categoryName);
    }

    // get employee by first name %LIKE%
    @GetMapping("/categoryName-like/{categoryName}")
    public List<Category> getEmployeeByFirstNameLike(@PathVariable String categoryName) {
        return categoryService.getCategoryByCategoryNameLike(categoryName);
    }

}
