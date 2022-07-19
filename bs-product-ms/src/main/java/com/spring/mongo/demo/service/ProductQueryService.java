package com.spring.mongo.demo.service;

import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Product;

import java.util.List;

public interface ProductQueryService {
	
	List<ProductDTO> getAll();

	List<Product> getProductByName(String name);

	List<Product> getProductByNameLike(String name);

	Product getOneProductByName(String name);

	//List<Product> getEmployeeBySalaryGreaterThan(int salary);

	//List<Product> getProductByCondition(Product product);


}
