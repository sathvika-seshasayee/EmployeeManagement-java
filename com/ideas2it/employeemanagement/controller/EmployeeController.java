package com.ideas2it.employeemanagement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

import com.ideas2it.employeemanagement.service.EmployeeService;

/**
 * Links view and service layers.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeController {
    EmployeeService serviceObj = new EmployeeService();
    public boolean checkEmployeeID(String employeeID) {
        return serviceObj.checkEmployeeID(employeeID);
    }
 
    /**
     * This method passes employee details to service layer.
     * @return true if employee object is created, false otherwise.
     */ 
    public boolean createEmployee(String employeeID, String name, String designation, 
                                  int employeeSalary, Date date, long mobileNumber) {
        return serviceObj.createEmployee(employeeID, name, designation, employeeSalary, date, mobileNumber);
    }

    /**
     * This method passes new employee name for updation using employeeID as referance
     * for employee details to service layer.
     * @params name
     * @params employeeID
     */  
    public void setEmployeeName(String name, String employeeID) {
        serviceObj.setEmployeeName(name, employeeID);
    }

    /**
     * This method passes new designation for updation using employeeID as referance
     * for employee details to service layer.
     * @params designation
     * @params employeeID
     */
    public void setEmployeeDesignation(String designation, String employeeID) {
        serviceObj.setEmployeeDesignation(designation, employeeID);
    }
    
    /**
     * This method passes new date of birth for updation using employeeID as referance
     * for employee details to service layer.
     * @params date, date of birth for employee
     * @params employeeID
     */
    public void setEmployeeDOB(Date date, String employeeID) {
        serviceObj.setEmployeeDOB(date, employeeID);
    }
 
    /**
     * This method passes new salary for updation using employeeID as referance
     * for employee details to service layer.
     * @params salary
     * @params employeeID
     */
    public void setEmployeeSalary(int salary, String employeeID) {
        serviceObj.setEmployeeSalary(salary, employeeID);
    }

    /**
     * This method passes new phone number for updation using employeeID as referance
     * for employee details to service layer.
     * @params phoneNumber
     * @params employeeID
     */
    public void setEmployeePhoneNumber(long phoneNumber, String employeeID) {
        serviceObj.setEmployeePhoneNumber(phoneNumber, employeeID);
    }

    /**
     * This method passes employeeID to service layer for deleting employee details.
     * @params employeeID
     */
    public void deleteEmployee(String employeeID) {
        serviceObj.deleteEmployee(employeeID);
    }

    /**
     * This method passes employeeID to service layer.
     * @params employeeID
     * @return concatinated string employee id and details 
     */
    public String viewSingleEmployee(String employeeID) {
        return serviceObj.viewSingleEmployee(employeeID);
    }

    /**
     * This method passes employeeID to service layer.
     * @return array of strings of employee details
     */
    public String[] viewAllEmployees() {
        return serviceObj.viewAllEmployees();
    }

    /**
     * This method passes date of birth to service layer for 
     * checking map if id exists in database
     * @params employeeID
     */
    public Date checkEmployeeDOB(String dob) {
        return serviceObj.checkEmployeeDOB(dob);
    }

    /**
     * This method passes phone number to service layer for validation.
     * @params phone number
     */
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return serviceObj.checkEmployeePhoneNumber(phoneNumber);
    }
 
    /**
     * This method passes salary to service layer for validation.
     * @params salary
     */
    public int checkEmployeeSalary(String salary) {
        return serviceObj.checkEmployeeSalary(salary);
    }
}