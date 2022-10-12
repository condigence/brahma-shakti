package com.condigence.service.impl;

import com.condigence.bean.ProductBean;
import com.condigence.dto.ProductDTO;
import com.condigence.model.Product;
import com.condigence.repository.ProductRepository;
import com.condigence.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository repository;

    @Override
    public Page<Product> findAllProductsPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public Optional<Product> findById(String productId) {
        return repository.findById(productId);
    }

    @Override
    public Optional<Product> findSubscribleProductById(String productId) {
        Optional<Product> p = repository.findById(productId);
        if(p.isPresent() && p.get().isSubscribable()){
            return repository.findById(productId);
        }else{
            return Optional.empty();
        }

    }


    @Override
    public List<ProductDTO> getAll() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = repository.findAll();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setCategory(product.getCategory());
            productDTO.setDescription(product.getDescription());
            productDTO.setDiscount(product.getDiscount());
            productDTO.setImage(product.getImageLink());
            productDTO.setPrice(product.getPrice());
            populateOffers(productDTO, product);
            productDTO.setRating(product.getRating());
            productDTO.setStockLeft(product.getQuantityInStock());
            productDTO.setSubscribable(product.isSubscribable());
            productDTO.setTitle(product.getName());
            productDTO.setProductType(product.getProductType());
            productDTO.setUnit(product.getUnit());
            productDTO.setId(product.getId());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }


    @Override
    public ProductDTO getProductById(String productId) {
        Optional<Product> productData = repository.findById(productId);
        ProductDTO productDTO = new ProductDTO();
        if (productData.isPresent()) {
            Product product = productData.get();
            productDTO.setId(product.getId());
            productDTO.setImage(product.getImageLink());
            productDTO.setDiscount(product.getDiscount());
            productDTO.setTitle(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setUnit(product.getUnit());
            productDTO.setDescription(product.getDescription());
            productDTO.setStockLeft(product.getQuantityInStock());
            productDTO.setCategory(product.getCategory());
            productDTO.setRating(product.getRating());
            productDTO.setSubscribable(product.isSubscribable());
            populateOffers(productDTO, product);
            return productDTO;
        } else {
            return productDTO.builder().id("0").title("Not Found").build();
        }

    }

    private static void populateOffers(ProductDTO productDTO, Product product) {
        if (product.getOffers() != null) {
            if (product.getOffers().contains(",")) {
                productDTO.setPromoCodes(product.getOffers());
            } else if (!product.getOffers().equalsIgnoreCase("")) {
                productDTO.setPromoCodes(product.getOffers());
            } else {
                productDTO.setPromoCodes("NA");
            }
        }
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    /**
     * @param productBean
     */
    @Override
    public void save(ProductBean productBean) {
        Product product = new Product();
        product.setImageLink(productBean.getImage());
        product.setDiscount(productBean.getDiscount());
        product.setName(productBean.getTitle());
        product.setDisplayPrice(productBean.getPrice());
        product.setUnit(productBean.getUnit());
        product.setDescription(productBean.getDescription());
        product.setQuantityInStock(productBean.getStockLeft());
        product.setCategory(productBean.getCategory());
        product.setProductType(productBean.getProductType());
        product.setRating(productBean.getRating());
        product.setPrice(productBean.getPrice());
        product.setSubscribable(productBean.isSubscribable());
        if (productBean.getPromoCodes() != null && !productBean.getPromoCodes().isEmpty()) {
            product.setOffers(productBean.getPromoCodes());
        }
        repository.save(product);

    }

    @Override
    public void saveAll(List<ProductBean> products) {

        List<Product> productList = new ArrayList<>();
        for(ProductBean productBean : products){
            Product product = new Product();
            product.setCategory(productBean.getCategory());
            product.setDescription(productBean.getDescription());
            product.setDiscount(productBean.getDiscount());
            product.setImageLink(productBean.getImage());
            product.setPrice(productBean.getPrice());
            if (productBean.getPromoCodes() != null && !productBean.getPromoCodes().equalsIgnoreCase("")) {
                product.setOffers(productBean.getPromoCodes());
            }
            product.setRating(productBean.getRating());
            product.setQuantityInStock(productBean.getStockLeft());
            product.setSubscribable(productBean.isSubscribable());
            product.setName(productBean.getTitle());
            product.setProductType(productBean.getProductType());
            product.setUnit(productBean.getUnit());
            product.setDisplayPrice(productBean.getPrice()+productBean.getDiscount());
            productList.add(product);
        }
        repository.saveAll(productList);
    }

    @Override
    public void saveAllProducts(List<Product> products) {
        repository.saveAll(products);
    }

    @Override
    public List<Product> getProductByName(String name) {
        List<Product> products = new ArrayList<>();
        List<Product> list = repository.findAll();
        for (Product p : list) {
            if (p.getName().equalsIgnoreCase(name))
                products.add(p);
        }
        return products;
    }

    @Override
    public Product getOneProductByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Product> getProductByNameLike(String name) {
        return repository.findByNameLike(name);
    }

}
