package com.ideas2it.employeemanagement.employee.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;

/**
 * Links view and service layers.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeController {
    EmployeeServiceImpl serviceObj = new EmployeeServiceImpl();

    /**
     * Checks if ID of employee is present in database.
     * @param employeeId id of employee
     */
    public boolean checkEmployeeID(int employeeId, boolean isDeleted) {
        return serviceObj.checkEmployeeID(employeeId, isDeleted);
    }
 
    /**
     * This method passes employee details to service layer 
     * to create an employee.
     * @param name name of employee
     * @param designation designation of employee
     * @param employeeSalary salary of employee
     * @param date date of birth of employee
     * @param mobileNumber phone number of employee
     * @return id of employee.
     *
     */ 
    public int createEmployee(String name, String designation, 
            double employeeSalary, Date date, long mobileNumber, 
            List<List<String>> addresses) {
        return serviceObj.createEmployee(name, designation, employeeSalary,
                                         date, mobileNumber, addresses);
    }

    public boolean unAssignProject(int projectId, int employeeId) {
        return serviceObj.unAssignProject(projectId, employeeId);
    }

    /**
     * Checks id of project if present in database
     * @param projectId id of project
     * @return true if id of project id active, false otherwise
     */
    public boolean checkProjectId(int projectId) {
        return serviceObj.checkProjectId(projectId);
    }

    /**
     * Add addresses to employee details
     * @param addresses one or more address details
     * @param employeeId id of employee
     */
    public boolean addAddresses(List<List<String>> addresses, int employeeId) {
        return serviceObj.addAddresses(addresses, employeeId);
    }

    /**
     * Deletes one address for an employee
     * @param addressId id of address to be deleted
     * @param employeeId id of employee
     */
    public boolean deleteOneAddress(int addressId, int employeeId) {
        return serviceObj.deleteOneAddress(addressId, employeeId);
    }

    /**
     * Assigns project to an employee
     * @param projectId id of project
     * @param employeeId id of employee
     */
    public boolean assignProject(int projectId, int employeeId) {
        return serviceObj.assignProject(projectId, employeeId);
    }

    /**
     * Dispalys all projects
     * @param option to print active or deleted projects
     * @return list of projects
     */ 
    public String displayAllProjects(boolean isDeleted) {
        return serviceObj.displayAllProjects(isDeleted);
    }

    /**
     * This method update employee details
     * @param employeeId id of employee
     * @param name nam of employee
     * @param designation designation of employee
     * @param employeeSalary salary of employee
     * @param date date of birth of employee
     * @param mobileNumber phone number of employee
     * @return true if updation was sucessful
     */
    public boolean updateEmployee(int employeeId, String name, String designation, 
                              double salary, Date dob, 
                              long phoneNumber) {
        return serviceObj.updateEmployee(employeeId, name, designation, salary,
                                         dob, phoneNumber);
    }        
    

    /**
     * This method passes id of employee to get addresses of that employee
     * @param employeeId is id of employee.
     * @return tree map of sorted values of employee addresses based on address id.
     */
    public Map<Integer, String> singleEmployeeAddress(int employeeId) {
        return serviceObj.singleEmployeeAddress(employeeId);
    }

    /**
     * This method restores deleted employee in database
     * @param employeeId is id of employee
     */
    public boolean restoreEmployee(int employeeId) {
        return serviceObj.restoreEmployee(employeeId);
    }

    /**
     * This method validates the pincode.
     * @param pinCode is the pin code entered by user.
     * @throws ClassNotFoundException, SQLException
     */
    public boolean checkPinCode(String pinCode) {
         return serviceObj.checkPinCode(pinCode);
    }

    /**
     * This method passes employeeId to service layer.
     * @param employeeId
     * @return string of one employee's details for display
     * @throws ClassNotFoundException, SQLException 
     */
    public String displaySingleEmployee(int employeeId) {
        return serviceObj.getSingleEmployee(employeeId);
    }

    /**
     * This method passes employeeId to service layer.
     * @return array of strings of employee details
     */
    public String displayAllEmployees(boolean isDeleted) {
        return serviceObj.getAllEmployees(isDeleted);
    }

    /**
     * This method passes employeeId to service layer for 
     * deleting employee details.
     * @param employeeId
     */
    public void deleteEmployee(int employeeId) {
        serviceObj.deleteEmployee(employeeId);
    }

    /**
     * This method passes date of birth to service layer for validation.
     * @param dob date of birth of employee
     */
    public Date checkEmployeeDOB(String dob) {
        return serviceObj.checkEmployeeDOB(dob);
    }

    /**
     * This method passes phone number to service layer for validation.
     * @param phone number
     */
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return serviceObj.checkEmployeePhoneNumber(phoneNumber);
    }

    /**
     * This method passes salary to service layer for validation.
     * @param salary
     */
    public double checkEmployeeSalary(String salary) {
        return serviceObj.checkEmployeeSalary(salary);
    }
}