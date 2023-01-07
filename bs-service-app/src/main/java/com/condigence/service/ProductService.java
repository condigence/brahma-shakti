package com.condigence.service;


import com.condigence.bean.ProductBean;
import com.condigence.dto.ProductDTO;
import com.condigence.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

	Optional<Product> findSubscribleProductById(String productId);

	List<ProductDTO> getAll();

	List<Product> getProductByName(String name);

	Product getOneProductByName(String name);

	List<Product> getProductByNameLike(String name);

	ProductDTO getProductById(String productId);

	void deleteById(String id);

	void save(ProductBean productBean);

	void saveAll(List<ProductBean> products);

	void saveAllProducts(List<Product> products);

//	List<Product> getProductByCondition(Product product);

	 Page<Product> findAllProductsPageable(Pageable pageable);

    Optional<Product> findById(String productId);

	ProductDTO updateProduct(ProductBean productBean);
}
