package com.ideas2it.employeemanagement.employee.dao;

import java.util.List;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * interface for Employee Dao
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-16
 */
public interface EmployeeDao {
    
    /**
     * This method gets set of employee details.
     * @param employeeIds Ids of employees
     * @return employee details list
     * @throws EmployeeManagementException 
     */
    List<Employee> getSetOfEmployees(List<Integer> employeeIds) throws EmployeeManagementException;

    /**
     * This method is used to add employee details name, salary,
     * date of birth, phone number, address into mysql database.
     * @param employeeModelObj is the object of Employee POJO
     * @return id of employee
     * @throws EmployeeManagementException 
     */
    int createEmployee(Employee employeeModelObj) throws EmployeeManagementException;   

    /**
     * This method updates employee details.
     * @param employeeModelObj details of employee
     * @return true if updation was sucessfull, false otherwise.
     * @throws EmployeeManagementException 
     */
    boolean updateEmployee(Employee employeeModelObj) throws EmployeeManagementException;

    /**
     * This method gets one employee details from database
     * @param employeeId is id of employee.
     * @return Employee pojo object with employee details
     * @throws EmployeeManagementException 
     */
    public Employee getEmployee(int employeeId) throws EmployeeManagementException;
 
    /** 
     * This method retrives all employee and corresponding address
     * details from database.
     * @return array list of Employee POJO objects with details of all employee.
     * @throws EmployeeManagementException 
     */
    List<Employee> getAllEmployees(boolean isDeleted) throws EmployeeManagementException;

    /**
     * Check if employee Id is in database.
     * @param employeeId is id of employee
     * @param isDeleted status of employees, active or deleted
     * @return false if upation was sucessful.
     * @throws EmployeeManagementException 
     */
    boolean checkEmployeeId(int employeeId, boolean isDeleted) throws EmployeeManagementException;

}