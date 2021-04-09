package com.ideas2it.employeemanagement.employee.dao;

import java.util.List;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ideas2it.employeemanagement.employee.model.Employee;
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
     * This method gets project ids for an employee
     * @param employeeId id of employee
     * @return list of projects with ids
     */
    List<Project> getProjects(int employeeId);

    /**
     * This method gets set of employee details.
     * @param employeeIds Ids of employees
     * @return employee details list
     */
    List<Employee> getSetOfEmployees(List<Integer> employeeIds);

    /**
     * This method is used to add employee details name, salary,
     * date of birth, phone number, address into mysql database.
     * @param employeeModelObj is the object of Employee POJO
     * @return id of employee
     */
    int createEmployee(Employee employeeModelObj);   

    /**
     * This method updates employee details.
     * @param employeeModelObj details of employee
     * @return true if updation was sucessfull, false otherwise.
     */
    boolean updateEmployee(Employee employeeModelObj);

    /**
     * This method gets one employee details from database
     * @param employeeId is id of employee.
     * @return Employee pojo object with employee details
     */
    public Employee getSingleEmployee(int employeeId);
 
    /** 
     * This method retrives all employee and corresponding address
     * details from database.
     * @return array list of Employee POJO objects with details of all employee.
     */
    List<Employee> getAllEmployees(boolean isDeleted);

    /**
     * Check if employee Id is in database.
     * @param employeeId is id of employee
     * @param isDeleted status of employees, active or deleted
     * @return false if upation was sucessful.
     */
    Employee checkEmployeeID(int employeeId);

}