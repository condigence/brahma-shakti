package com.spring.mongo.demo.service.impl;

import com.spring.mongo.demo.model.Category;
import com.spring.mongo.demo.model.Employee;
import com.spring.mongo.demo.repository.CategoryRepository;
import com.spring.mongo.demo.repository.EmployeeRepository;
import com.spring.mongo.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
 class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository  repository;



    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }


     @Override
    public Category getOneCategoryByCategoryName(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }

    @Override
    public List<Category> getCategoryByCategoryNameLike(String firstName) {
        return repository.findByCategoryNameLike(firstName);
    }



}
