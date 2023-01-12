package com.condigence.service;


import com.condigence.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    List<Employee> getEmployeeByFirstName(String firstName);

    Employee getOneEmployeeByFirstName(String firstName);

    List<Employee> getEmployeeByFirstNameLike(String firstName);

    Employee getEmployeeById(int empId);

    Employee getEmployeeByLastName(String lastName);

    List<Employee> getEmployeeBySalaryGreaterThan(int salary);

    List<Employee> getEmployeeByCondition(Employee employee);

}
