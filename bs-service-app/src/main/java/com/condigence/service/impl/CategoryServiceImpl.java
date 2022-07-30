package com.condigence.service.impl;

import com.condigence.repository.CategoryRepository;
import com.condigence.model.Category;
import com.condigence.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
 class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;



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
