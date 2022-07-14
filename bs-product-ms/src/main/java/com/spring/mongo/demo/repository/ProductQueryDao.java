package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Product;

import java.util.List;

public interface ProductQueryDao {
	
	List<Product> getAll();

	List<Product> getProductByName(String name);

	Product getSingleProductByName(String name);

	List<Product> getProductByNameLike(String name);

}
