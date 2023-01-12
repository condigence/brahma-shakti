package com.condigence.service;

import com.condigence.dto.ProductDTO;
import com.condigence.model.Product;

import java.util.List;

public interface ProductQueryService {

    List<ProductDTO> getAll();

    List<Product> getProductByName(String name);

    List<Product> getProductByNameLike(String name);

    Product getOneProductByName(String name);

    //List<Product> getEmployeeBySalaryGreaterThan(int salary);

    //List<Product> getProductByCondition(Product product);


}
