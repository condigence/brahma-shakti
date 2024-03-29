package com.condigence.controller;

import com.condigence.bean.ProductBean;
import com.condigence.dto.ProductDTO;
import com.condigence.model.Product;
import com.condigence.service.ProductService;
import com.condigence.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/bs-products")
public class ProductController {

    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/healthCheck")
    public String sayHello() {
        return "Hello, I am doing Great!";
    }

    @GetMapping
    public List<ProductDTO> getAll() {

        logger.info("Entering getAll Products");
        return productService.getAll();
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> addProduct(@RequestBody ProductBean productBean) {

        logger.info("Entering addProduct with Product Details >>>>>>>>  : {}", productBean);
        HttpHeaders headers = new HttpHeaders();
        productService.save(productBean);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/addAll")
    public ResponseEntity<?> addProducts(@RequestBody List<ProductBean> products) {

        logger.info("Entering addAllProduct with Products Details >>>>>>>>  : {}", products);
        HttpHeaders headers = new HttpHeaders();
        productService.saveAll(products);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        ProductDTO product = productService.getProductById(id);
        if (product == null || product.getId().equalsIgnoreCase("0")) {
            return new ResponseEntity(new CustomErrorType("Product not found."), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/name/{name}")
    public List<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    // get product by name (equals())
    @GetMapping("/one-by-name/{name}")
    public Product getOneProductByName(@PathVariable String name) {
        return productService.getOneProductByName(name);
    }

    // get product by first name %LIKE%
    @GetMapping("/name-like/{name}")
    public List<Product> getProductByNameLike(@PathVariable String name) {
        return productService.getProductByNameLike(name);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Item with id {}", id);
        ProductDTO product = productService.getProductById(id);
        if (product != null && !product.getId().equalsIgnoreCase("0")) {
            productService.deleteById(id);
        } else {
            logger.error("Unable to delete. Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductDTO>(HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateProduct(@RequestBody ProductBean productBean) {
        // check if product is available
        if (productService.findById(productBean.getId()).isPresent()) {
            ProductDTO productDTO = productService.updateProduct(productBean);
            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product That your are trying to update does not exist! Please contact admin!");
        }
    }


}




