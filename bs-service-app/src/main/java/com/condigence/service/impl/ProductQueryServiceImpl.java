package com.condigence.service.impl;

import com.condigence.dto.ProductDTO;
import com.condigence.model.Product;
import com.condigence.repository.ProductQueryDao;
import com.condigence.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	@Autowired
	private ProductQueryDao productQueryDao;

	@Override
	public List<ProductDTO> getAll() {
		System.out.println("Inside Product Query Service Impl");
		List<ProductDTO> productDTOS = new ArrayList<>();
		List<Product> products = productQueryDao.getAll();
		for(Product product : products){
			ProductDTO productDTO = new ProductDTO();

			productDTO.setTitle(product.getName());
			productDTO.setPrice(product.getPrice());
			productDTO.setImage(product.getImageLink());
			productDTO.setId(product.getId());
			productDTO.setCategory(product.getCategory());
			productDTO.setDescription(product.getDescription());
			productDTO.setDiscount(product.getDiscount());
			if(product.getOffers() != null ){
				if(product.getOffers().contains(",")){
					productDTO.setPromoCodes(product.getOffers().split(","));
				}else if(!product.getOffers().equalsIgnoreCase("")){
					productDTO.setPromoCodes(new String[] {product.getOffers()});
				}else{
					productDTO.setPromoCodes(new String[] {"NA"});
				}

			}
			productDTO.setStockLeft(product.getQuantityInStock());
			productDTO.setRating(product.getRating());



			productDTOS.add(productDTO);
		}

		return productDTOS;
	}

	@Override
	public List<Product> getProductByName(String name) {

		if (!StringUtils.isEmpty(name)) {
			return productQueryDao.getProductByName(name);
		}

		return null;
	}


	@Override
	public Product getOneProductByName(String name) {

		if (!StringUtils.isEmpty(name)) {
			return productQueryDao.getSingleProductByName(name);
		}

		return null;
	}
	@Override
	public List<Product> getProductByNameLike(String name) {

		if (!StringUtils.isEmpty(name)) {
			return productQueryDao.getProductByNameLike(name);
		}

		return null;
	}


//	@Override
//	public Product getSingleProductByName(String lastName) {
//
//		if (!StringUtils.isEmpty(lastName)) {
//			return productQueryDao.getSingleProductByLastName(lastName);
//		}
//		return Employee.builder().empId(0).firstName("Not Found").lastName("Please enter valid last name").salary(0f).build();
//	}
//
//	@Override
//	public List<Employee> getEmployeeBySalaryGreaterThan(int salary) {
//
//		if (salary > 0) {
//			return employeeQueryDao.getEmployeeBySalaryGreaterThan(salary);
//		}
//		return Collections.emptyList();
//	}
//
//	@Override
//	public List<Employee> getEmployeeByCondition(Employee employee) {
//		List<Employee> list = employeeQueryDao.getAll();
//		List<Employee> employees = new ArrayList<>();
//
//		if (null != employee && (null != employee.getFirstName() || employee.getEmpId() > 0
//				|| null != employee.getLastName() || employee.getSalary() > 0)) {
//
//			for (Employee emp : list) {
//
//				if (null != employee.getFirstName() && employee.getEmpId() > 0 && null != employee.getLastName()
//						&& employee.getSalary() > 0) {
//
//					if (emp.getEmpId() == employee.getEmpId()
//							&& emp.getFirstName().equalsIgnoreCase(employee.getFirstName())
//							&& emp.getLastName().equalsIgnoreCase(employee.getLastName())
//							&& emp.getSalary() == employee.getSalary()) {
//						employees.add(emp);
//
//						break;
//					} else {
//						continue;
//					}
//				}
//				if (employee.getEmpId() == emp.getEmpId()) {
//					employees.add(emp);
//					continue;
//				}
//
//				// First name
//				if (null != employee.getFirstName()) {
//					if (emp.getFirstName().toLowerCase().contains(employee.getFirstName().toLowerCase())) {
//						employees.add(emp);
//						// Go back to first statement (nothing but for loop)
//						continue;
//					}
//				}
//
//				// Last name
//				if (null != employee.getLastName()) {
//					if (emp.getLastName().equalsIgnoreCase(employee.getLastName()))
//						employees.add(emp);
//					// Go back to first statement (nothing but for loop)
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
