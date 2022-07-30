package com.condigence;

import com.condigence.model.*;
import com.condigence.repository.*;
import com.condigence.utils.HelperUtil;
import com.spring.mongo.demo.model.*;
import com.spring.mongo.demo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;


@SpringBootApplication
@EnableMongoRepositories
public class Application {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository StockRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {


            List<Product> products = productRepository.findAll();
            if (products.size() == 0) {
                LOGGER.info("******* Inserting Products to DB *******");
                productRepository.saveAll(HelperUtil.productSupplier.get());
            } else {
                LOGGER.info("******* Products stored in DB Size :: {}", products.size());
                LOGGER.info("******* Products stored in DB :: {}", products);
            }

            List<Cart> carts = cartRepository.findAll();
            if (carts.size() == 0) {
                LOGGER.info("******* Inserting carts to DB *******");
                cartRepository.saveAll(HelperUtil.cartSupplier.get());
            } else {
                LOGGER.info("******* carts stored in DB Size :: {}", carts.size());
                LOGGER.info("******* carts stored in DB :: {}", carts);
            }

            List<Category> categories = categoryRepository.findAll();
            if (categories.size() == 0) {
                LOGGER.info("******* Inserting Categories to DB *******");
                categoryRepository.saveAll(HelperUtil.categorySupplier.get());
            } else {
                LOGGER.info("******* Categories stored in DB Size :: {}", categories.size());
                LOGGER.info("******* Categories stored in DB :: {}", categories);
            }

            List<Order> orders = orderRepository.findAll();
            if (orders.size() == 0) {
                LOGGER.info("******* Inserting Orders to DB *******");
                orderRepository.saveAll(HelperUtil.orderSupplier.get());
            } else {
                LOGGER.info("******* Orders stored in DB Size :: {}", orders.size());
                LOGGER.info("******* Orders stored in DB :: {}", orders);
            }

            List<User> users = userRepository.findAll();
            if (users.size() == 0) {
                LOGGER.info("******* Inserting users to DB *******");
                userRepository.saveAll(HelperUtil.userSupplier.get());
            } else {
                LOGGER.info("******* users stored in DB Size :: {}", users.size());
                LOGGER.info("******* users stored in DB :: {}", users);
            }

        };


    }

}
