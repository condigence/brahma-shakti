package com.condigence.service;

import com.condigence.model.Employee;

import java.util.List;

public interface EmployeeQueryService {

    List<Employee> getAll();

    List<Employee> getEmployeeByFirstName(String firstName);

    Employee getSingleEmployeeByLastName(String lastName);

    List<Employee> getEmployeeByFirstNameLike(String firstName);

    Employee getOneEmployeeByFirstName(String firstName);

    List<Employee> getEmployeeBySalaryGreaterThan(int salary);

    List<Employee> getEmployeeByCondition(Employee employee);


}
