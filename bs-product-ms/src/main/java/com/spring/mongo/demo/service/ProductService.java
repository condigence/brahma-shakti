package com.spring.mongo.demo.service;


import com.spring.mongo.demo.model.Product;

import java.util.List;

public interface ProductService {
	
	List<Product> getAll();

	List<Product> getProductByName(String name);

	Product getOneProductByName(String name);

	List<Product> getProductByNameLike(String name);

	Product getProductById(String productId);
	
//	List<Product> getProductByCondition(Product product);

}
