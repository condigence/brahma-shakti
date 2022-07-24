package com.spring.mongo.demo;

import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.model.*;
import com.spring.mongo.demo.repository.*;
import com.spring.mongo.demo.utils.HelperUtil;
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
public class SpringBootMongoDBApplication {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongoDBApplication.class, args);
	}


	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SuperHeroRepository superHeroRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	
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



			List<Employee> employees = employeeRepository.findAll();
				if (employees.size() == 0) {
					LOGGER.info("******* Inserting Employees to DB *******");
					employeeRepository.saveAll(HelperUtil.employeeSupplier.get());
				} else {
					LOGGER.info("******* Employees stored in DB Size :: {}", employees.size());
					LOGGER.info("******* Employees stored in DB :: {}", employees);
				}

			List<Category> categories = categoryRepository.findAll();
			if (categories.size() == 0) {
				LOGGER.info("******* Inserting Categories to DB *******");
				categoryRepository.saveAll(HelperUtil.categorySupplier.get());
			} else {
				LOGGER.info("******* Categories stored in DB Size :: {}", categories.size());
				LOGGER.info("******* Categories stored in DB :: {}", categories);
			}

			List<SuperHero> superHeroes = superHeroRepository.findAll();
			if (superHeroes.size() == 0) {
				LOGGER.info("******* Inserting Super heroes to DB *******");
				superHeroRepository.saveAll(HelperUtil.superHeroesSupplier.get());
			} else {
				LOGGER.info("******* Super heroes stored in DB Size :: {}", superHeroes.size());
				LOGGER.info("******* Super heroes stored in DB :: {}", superHeroes);
			}
			List<User> users = userRepository.findAll();
			if (categories.size() == 0) {
				LOGGER.info("******* Inserting Categories to DB *******");
				userRepository.saveAll(HelperUtil.userSupplier.get());
			} else {
				LOGGER.info("******* Categories stored in DB Size :: {}", categories.size());
				LOGGER.info("******* Categories stored in DB :: {}", categories);
			}
		};




	}

}
