package com.spring.mongo.demo.service;


import com.spring.mongo.demo.bean.ProductBean;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Product;

import java.util.List;

public interface ProductService {
	
	List<ProductDTO> getAll();

	List<Product> getProductByName(String name);

	Product getOneProductByName(String name);

	List<Product> getProductByNameLike(String name);

	ProductDTO getProductById(String productId);

	void deleteById(String id);

	void save(ProductBean productBean);

//	List<Product> getProductByCondition(Product product);

}
