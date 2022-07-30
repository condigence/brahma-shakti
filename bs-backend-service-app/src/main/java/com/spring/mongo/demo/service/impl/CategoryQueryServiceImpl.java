package com.spring.mongo.demo.service.impl;

import com.spring.mongo.demo.model.Category;
import com.spring.mongo.demo.model.Employee;
import com.spring.mongo.demo.repository.CategoryQueryDao;
import com.spring.mongo.demo.repository.EmployeeQueryDao;
import com.spring.mongo.demo.service.CategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    @Autowired
    private CategoryQueryDao categoryQueryDao;

    @Override
    public List<Category> getAll() {
        System.out.println("Inside Category Query Service Impl");
        return categoryQueryDao.getAll();
    }


    @Override
    public Category getOneCategoryByCategoryName(String categoryName) {

        if (!StringUtils.isEmpty(categoryName)) {
            return categoryQueryDao.getSingleCategoryByCategoryName(categoryName);
        }

        return null;
    }

    @Override
    public List<Category> getCategoryByCategoryNameLike(String categoryName) {

        if (!StringUtils.isEmpty(categoryName)) {
            return categoryQueryDao.getCategoryByCategoryNameLike(categoryName);
        }

        return null;
    }



}
