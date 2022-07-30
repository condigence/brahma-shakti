package com.spring.mongo.demo.service.impl;

import com.spring.mongo.demo.bean.ProductBean;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.repository.ProductRepository;
import com.spring.mongo.demo.service.ProductService;
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
    public List<ProductDTO> getAll() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = repository.findAll();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
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
            if (product.getOffers() != null) {
                if (product.getOffers().contains(",")) {
                    productDTO.setPromoCodes(product.getOffers().split(","));
                } else if (!product.getOffers().equalsIgnoreCase("")) {
                    productDTO.setPromoCodes(new String[]{product.getOffers()});
                } else {
                    productDTO.setPromoCodes(new String[]{"NA"});
                }
            }
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
            if (product.getOffers() != null) {
                if (product.getOffers().contains(",")) {
                    productDTO.setPromoCodes(product.getOffers().split(","));
                } else if (!product.getOffers().equalsIgnoreCase("")) {
                    productDTO.setPromoCodes(new String[]{product.getOffers()});
                } else {
                    productDTO.setPromoCodes(new String[]{"NA"});
                }
            }
            return productDTO;
        } else {
            return productDTO.builder().id("0").title("Not Found").build();
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
        product.setRating(productBean.getRating());
        product.setSubscribable(productBean.isSubscribable());
        if (productBean.getPromoCodes() != null && productBean.getPromoCodes().length > 0) {
            product.setOffers(productBean.getPromoCodes().toString());
        }
        repository.save(product);

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

//	@Override
//	public Employee getEmployeeByFirstName(Employee employee) {
//		List<Employee> list = repository.findAll();
//		for (Employee emp : list) {
//			if (emp.getFirstName().equals(employee.getFirstName()))
//				return emp;
//		}
//		return Employee.builder().empId(0).firstName("Not Found").lastName("Please enter valid id").salary(0f).build();
//	}
//
//	@Override
//	public List<Employee> getEmployeeByFrName(Employee employee) {
//		List<Employee> employees = new ArrayList<>();
//
//		if (null != employee && null != employee.getFirstName()
//				&& !(employee.getFirstName().equals(""))) {
//			List<Employee> list = repository.findAll();
//
//			for (Employee emp : list) {
//				if (emp.getFirstName().toLowerCase().contains(employee.getFirstName().toLowerCase())) {
//					employees.add(emp);
//				}
//			}
//		}
//		return employees;
//	}


//
//
//	@Override
//	public List<Employee> getEmployeeByCondition(Employee employee) {
//		List<Employee> list = repository.findAll();
//		List<Employee> employees = new ArrayList<>();
//
//		// This will return true if employee object is present(not null) any one of
//		// property is not null OR greater than 0
//		if (null != employee && (null != employee.getFirstName() || employee.getEmpId() > 0
//				|| null != employee.getLastName() || employee.getSalary() > 0)) {
//
//			for (Employee emp : list) {
//
//				// If all 4 properties are present then only this block will execute
//				if (null != employee.getFirstName() && employee.getEmpId() > 0 && null != employee.getLastName()
//						&& employee.getSalary() > 0) {
//
//					if (emp.getEmpId() == employee.getEmpId()
//							&& emp.getFirstName().equalsIgnoreCase(employee.getFirstName())
//							&& emp.getLastName().equalsIgnoreCase(employee.getLastName())
//							&& emp.getSalary() == employee.getSalary()) {
//						employees.add(emp);
//						// Break the for loop
//						break;
//					} else {
//						// Go back to first statement
//						continue;
//					}
//				}
//
//				// if any one of above property is null or less than equals to 0 then below
//				// block is executing
//				// Emp Id
//				if (employee.getEmpId() == emp.getEmpId()) {
//					employees.add(emp);
//					// Go back to first statement
//					continue;
//				}
//
//				// First name
//				if (null != employee.getFirstName()) {
//					if (emp.getFirstName().toLowerCase().contains(employee.getFirstName().toLowerCase())) {
//						employees.add(emp);
//						// Go back to first statement
//						continue;
//					}
//				}
//
//				// Last name
//				if (null != employee.getLastName()) {
//					if (emp.getLastName().equalsIgnoreCase(employee.getLastName()))
//						employees.add(emp);
//					// Go back to first statement
//					continue;
//				}
//				// salary
//				if (employee.getSalary() == emp.getSalary()) {
//					employees.add(emp);
//				}
//				// ---------------------------------------------------------
//			}
//			// returning the list
//			return employees;
//		}
//		// if below statements return false only then below list is returning
//		// if (null != employee &&
//		// (null != employee.getFirstName() || employee.getEmpId() > 0
//		// || null != employee.getLastName() || employee.getSalary() > 0))
//		return employees;
//	}

}
