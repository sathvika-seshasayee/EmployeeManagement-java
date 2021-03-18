package com.ideas2it.employeemanagement.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl;

/**
 * Links view and service layers.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeController {
    EmployeeServiceImpl serviceObj = new EmployeeServiceImpl();
    public boolean checkEmployeeID(int employeeId)  throws 
            ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeeID(employeeId);
    }
 
    /**
     * This method passes employee details to service layer.
     * @param employeeID
     * @param name
     * @param designation
     * @param employeeSalary
     * @param date
     * @param mobileNumber
     * @return id of employee.
     *
     */ 
    public int createEmployee(String name, String designation, 
            double employeeSalary, Date date, long mobileNumber, 
            ArrayList<ArrayList<String>> addresses) throws 
                    ClassNotFoundException, SQLException {
        return serviceObj.createEmployee(name, designation, employeeSalary,
                                         date, mobileNumber, addresses);
    }
    
    /**
     * This method passes id of employee to get addresses of that employee
     * @param employeeId is id of employee.
     * @return tree map of sorted values of employee addresses based on address id.
     * @throws ClassNotFoundException, SQLException
     */
    public Map<Integer, String> singleEmployeeAddress(int employeeId, String option) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.singleEmployeeAddress(employeeId, option);
    }

    /**
     * This method deletes one address of one employee
     * @param addressId is address id to be deleted
     * @return false if deleted.
     * @throws ClassNotFoundException, SQLException
     */
    public boolean deleteSingleAddress(int addressId) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.deleteSingleAddress(addressId);
    } 

    /** 
     * This method updates the existing address.                                                                   // change method name to update address
     * @param addressId is the id of address to be updated.
     * @param address is the address entered by user.
     * @throws ClassNotFoundException, SQLException
     */
    public boolean setAddress(int addressId, ArrayList<String> address) throws
            ClassNotFoundException, SQLException {
        return serviceObj.setAddress(addressId, address);
    }

    /**
     * This method restores deleted employee in database
     * @param employeeId is id of employee
     * @throws ClassNotFoundException, SQLException
     */
    public boolean restoreEmployee(int employeeId)  throws 
            ClassNotFoundException, SQLException {
        return serviceObj.restoreEmployee(employeeId);
    }

    /**
     * This method adds one address to the database
     * @param employeeId is the Id of employee.
     * @param is address is the address entered by user.
     * @throws ClassNotFoundException, SQLException
     */
    public boolean addAddress(int employeeId, ArrayList<String> address)
            throws ClassNotFoundException, SQLException {
        return serviceObj.addAddress(employeeId, address);
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
     * @return concatinated string employee id and details
     * @throws ClassNotFoundException, SQLException 
     */
    public String displaySingleEmployee(int employeeId) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.viewSingleEmployee(employeeId);
    }

    /**
     * This method passes employeeId to service layer.
     * @return array of strings of employee details
     * @throws ClassNotFoundException, SQLException 
     */
    public ArrayList<String> displayAllEmployees(String option) 
            throws ClassNotFoundException, SQLException {
        return serviceObj.viewAllEmployees(option);
    }

    /**
     * This method passes employeeId to service layer for 
     * deleting employee details.
     * @param employeeId
     * @throws ClassNotFoundException, SQLException 
     */
    public void deleteEmployee(int employeeId) throws ClassNotFoundException,
                                                      SQLException {
        serviceObj.deleteEmployee(employeeId);
    }

    /**
     * This method passes new employee name for updation using employeeId as 
     * referance for employee details to service layer.
     * @param name
     * @param employeeId
     * @throws ClassNotFoundException, SQLException 
     */  
    public boolean setEmployeeName(String name, int employeeId) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeName(name, employeeId);
    }

    /**
     * This method passes new designation for updation using employeeId as 
     * referance for employee details to service layer.
     * @param designation
     * @param employeeId
     * @throws ClassNotFoundException, SQLException 
     */
    public boolean setEmployeeDesignation(String designation, int employeeId) 
            throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeDesignation(designation, employeeId);
    }
    
    /**
     * This method passes new date of birth for updation using employeeId as 
     * referance for employee details to service layer.
     * @param date, date of birth for employee
     * @param employeeId
     * @throws ClassNotFoundException, SQLException 
     */
    public boolean setEmployeeDOB(Date date, int employeeId) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeDOB(date, employeeId);
    }
 
    /**
     * This method passes new salary for updation using employeeId as 
     * referance for employee details to service layer.
     * @param salary
     * @param employeeId
     * @throws ClassNotFoundException, SQLException 
     */
    public boolean setEmployeeSalary(double salary, int employeeId) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.setEmployeeSalary(salary, employeeId);
    }

    /**
     * This method passes new phone number for updation using employeeId as 
     * referance for employee details to service layer.
     * @param phoneNumber
     * @param employeeId
     * @throws ClassNotFoundException, SQLException 
     */
    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) 
            throws ClassNotFoundException, SQLException {
        return serviceObj.setEmployeePhoneNumber(phoneNumber, employeeId);
    }

    /**
     * This method passes date of birth to service layer for 
     * converting to date format
     * @param dob date of birth of employee
     * @throws ClassNotFoundException, SQLException 
     */
    public Date checkEmployeeDOB(String dob) throws ClassNotFoundException,
                                                    SQLException {
        return serviceObj.checkEmployeeDOB(dob);
    }

    /**
     * This method passes phone number to service layer for validation.
     * @param phone number
     * @throws ClassNotFoundException, SQLException 
     */
    public long checkEmployeePhoneNumber(String phoneNumber) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeePhoneNumber(phoneNumber);
    }

    /**
     * This method passes salary to service layer for validation.
     * @param salary
     * @throws ClassNotFoundException, SQLException 
     */
    public double checkEmployeeSalary(String salary) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.checkEmployeeSalary(salary);
    }
}