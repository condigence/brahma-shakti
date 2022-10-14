package com.condigence;


import com.condigence.dto.ProductDTO;
import com.condigence.model.*;
import com.condigence.service.ProductService;
import com.condigence.utils.ProductData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final String PRODUCTS_JSON = "/data/products.json";

    @Autowired
    ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner runner() {
        return args -> {

            try {
                List<ProductDTO> products = productService.getAll();
                if (products.size() == 0) {
                    logger.info("******* Inserting Products to DB *******");

                    TypeReference<List<ProductData>> typeReference = new TypeReference<List<ProductData>>() {
                    };
                    InputStream inputStream = TypeReference.class.getResourceAsStream(PRODUCTS_JSON);
                    List<ProductData> productsData = new ObjectMapper().readValue(inputStream, typeReference);

                    if (productsData != null && !productsData.isEmpty()) {
                        List<Product> list = new ArrayList<>();
                        for(ProductData pd : productsData){
                            Product product = new Product();
                            product.setCategory(pd.getCategory());
                            product.setDescription(pd.getDescription());
                            product.setDiscount(pd.getDiscount());
                            product.setImageLink(pd.getImage());
                            product.setPrice(pd.getPrice());
                            if (pd.getPromoCodes() != null && !pd.getPromoCodes().equalsIgnoreCase("")) {
                                product.setOffers(pd.getPromoCodes());
                            }
                            product.setRating(pd.getRating());
                            product.setQuantityInStock(pd.getStockLeft());
                            product.setSubscribable(pd.isSubscribable());
                            product.setName(pd.getTitle());
                            product.setProductType(pd.getProductType());
                            product.setUnit(pd.getUnit());
                            product.setDisplayPrice(pd.getPrice());
                            list.add(product);
                        }
                        productService.saveAllProducts(list);
                    }

                } else {
                    logger.info("******* Products stored in DB Size :: {}", products.size());
                    logger.info("******* Products stored in DB :: {}", products);
                }
            } catch (Exception e) {
                logger.info("******* Error while Saving *******");
            }
        };
    }


//    @Bean
//    CommandLineRunner runner() {
//        return args -> {
//
//
//            List<Product> products = productRepository.findAll();
//            if (products.size() == 0) {
//                LOGGER.info("******* Inserting Products to DB *******");
//                productRepository.saveAll(HelperUtil.productSupplier.get());
//            } else {
//                LOGGER.info("******* Products stored in DB Size :: {}", products.size());
//                LOGGER.info("******* Products stored in DB :: {}", products);
//            }
//
//            List<Cart> carts = cartRepository.findAll();
//            if (carts.size() == 0) {
//                LOGGER.info("******* Inserting carts to DB *******");
//                cartRepository.saveAll(HelperUtil.cartSupplier.get());
//            } else {
//                LOGGER.info("******* carts stored in DB Size :: {}", carts.size());
//                LOGGER.info("******* carts stored in DB :: {}", carts);
//            }
//
//            List<Category> categories = categoryRepository.findAll();
//            if (categories.size() == 0) {
//                LOGGER.info("******* Inserting Categories to DB *******");
//                categoryRepository.saveAll(HelperUtil.categorySupplier.get());
//            } else {
//                LOGGER.info("******* Categories stored in DB Size :: {}", categories.size());
//                LOGGER.info("******* Categories stored in DB :: {}", categories);
//            }
//
//            List<Order> orders = orderRepository.findAll();
//            if (orders.size() == 0) {
//                LOGGER.info("******* Inserting Orders to DB *******");
//                orderRepository.saveAll(HelperUtil.orderSupplier.get());
//            } else {
//                LOGGER.info("******* Orders stored in DB Size :: {}", orders.size());
//                LOGGER.info("******* Orders stored in DB :: {}", orders);
//            }
//
//            List<User> users = userRepository.findAll();
//            if (users.size() == 0) {
//                LOGGER.info("******* Inserting users to DB *******");
//                userRepository.saveAll(HelperUtil.userSupplier.get());
//            } else {
//                LOGGER.info("******* users stored in DB Size :: {}", users.size());
//                LOGGER.info("******* users stored in DB :: {}", users);
//            }
//
//            List<Favourite> favourites = favouriteRepository.findAll();
//            if (favourites.size() == 0) {
//                LOGGER.info("******* Inserting favourites to DB *******");
//                favouriteRepository.saveAll(HelperUtil.favouriteSupplier.get());
//            } else {
//                LOGGER.info("******* favourites stored in DB Size :: {}", favourites.size());
//                LOGGER.info("******* favourites stored in DB :: {}", favourites);
//            }
//
//        };
//
//
//    }

}
