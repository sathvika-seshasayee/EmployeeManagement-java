package com.ideas2it.employeemanagement.controller;

import java.sql.Date;
import java.sql.SQLException;

import com.ideas2it.employeemanagement.service.EmployeeService;

/**
 * Links view and service layers.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeController {
    EmployeeService serviceObj = new EmployeeService();
    public boolean checkEmployeeID(int employeeId)  throws ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeeID(employeeId);
    }
 
    /**
     * This method passes employee details to service layer.
     * @params employeeID
     * @params name
     * @params designation
     * @params employeeSalary
     * @params date
     * @params mobileNumber
     * @return true if employee object is created, false otherwise.
     */ 
    public int createEmployee(String name, String designation, 
            long employeeSalary, Date date, long mobileNumber) throws ClassNotFoundException, SQLException {
        return serviceObj.createEmployee(name, designation, employeeSalary, date, mobileNumber);
    }

    public boolean createAddress(int employeeId, String address, String city, 
            String state, String country, int pinCode, String addressType, boolean yesOrNo) throws ClassNotFoundException, SQLException {
        return serviceObj.createAddress(employeeId, address, city, state, country, pinCode, addressType, yesOrNo);
    }

    public boolean setAddress(int employeeId, String address, String city, 
            String state, String country, int pinCode, String addressType, boolean yesOrNo) throws ClassNotFoundException, SQLException {
        return serviceObj.setAddress(employeeId, address, city, state, country, pinCode, addressType, yesOrNo);
    }

    /**
     * This method passes employeeId to service layer.
     * @params employeeId
     * @return concatinated string employee id and details 
     */
    public String displaySingleEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return serviceObj.viewSingleEmployee(employeeId);
    }

    /**
     * This method passes employeeId to service layer.
     * @return array of strings of employee details
     */
    public String[] displayAllEmployees() throws ClassNotFoundException, SQLException {
        return serviceObj.viewAllEmployees();
    }

    /**
     * This method passes employeeId to service layer for deleting employee details.
     * @params employeeId
     */
    public void deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        serviceObj.deleteEmployee(employeeId);
    }

    /**
     * This method passes new employee name for updation using employeeId as referance
     * for employee details to service layer.
     * @params name
     * @params employeeId
     */  
    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeName(name, employeeId);
    }

    /**
     * This method passes new designation for updation using employeeId as referance
     * for employee details to service layer.
     * @params designation
     * @params employeeId
     */
    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeDesignation(designation, employeeId);
    }
    
    /**
     * This method passes new date of birth for updation using employeeId as referance
     * for employee details to service layer.
     * @params date, date of birth for employee
     * @params employeeId
     */
    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeDOB(date, employeeId);
    }
 
    /**
     * This method passes new salary for updation using employeeId as referance
     * for employee details to service layer.
     * @params salary
     * @params employeeId
     */
    public boolean setEmployeeSalary(long salary, int employeeId) throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeSalary(salary, employeeId);
    }

    /**
     * This method passes new phone number for updation using employeeId as referance
     * for employee details to service layer.
     * @params phoneNumber
     * @params employeeId
     */
    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeePhoneNumber(phoneNumber, employeeId);
    }

    /**
     * This method passes date of birth to service layer for 
     * converting to date format
     * @params dob date of birth of employee
     */
    public Date checkEmployeeDOB(String dob) throws ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeeDOB(dob);
    }

    /**
     * This method passes phone number to service layer for validation.
     * @params phone number
     */
    public long checkEmployeePhoneNumber(String phoneNumber) throws ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeePhoneNumber(phoneNumber);
    }

    /**
     * This method passes salary to service layer for validation.
     * @params salary
     */
    public long checkEmployeeSalary(String salary) throws ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeeSalary(salary);
    }
}