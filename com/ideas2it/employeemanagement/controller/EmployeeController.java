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
     * @param name nam of employee
     * @param designation designation of employee
     * @param employeeSalary salary of employee
     * @param date date of birth of employee
     * @param mobileNumber phone number of employee
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
                              long phoneNumber, 
                              ArrayList<ArrayList<String>> addresses) throws 
                                      ClassNotFoundException, SQLException {
        return serviceObj.updateEmployee(employeeId, name, designation, salary,
                                         dob, phoneNumber, addresses);
    }        
    

    /**
     * This method passes id of employee to get addresses of that employee
     * @param employeeId is id of employee.
     * @return tree map of sorted values of employee addresses based on address id.
     * @throws ClassNotFoundException, SQLException
     */
    public Map<Integer, String> singleEmployeeAddress(int employeeId) throws 
            ClassNotFoundException, SQLException {
        return serviceObj.singleEmployeeAddress(employeeId);
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
        return serviceObj.getSingleEmployee(employeeId);
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