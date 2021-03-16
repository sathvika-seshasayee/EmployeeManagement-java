package com.ideas2it.employeemanagement.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;  // delete these
import java.util.Map;

import com.ideas2it.employeemanagement.dao.impl.EmployeeDao;
import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDaoImpl employeeDao = new EmployeeDao();
    EmployeeModel employeeModelObj;
    EmployeeAddressModel employeeAddressObj;

    /**
     * This method adds new employee details into map.
     * @params employeeId
     * @params name
     * @params designation
     * @params employeeSalary
     * @params date
     * @params mobileNumber
     * @return true if employee object is created, false otherwise.
     */    
    public int createEmployee(String name, String designation, double employeeSalary, Date date, 
            long mobileNumber, ArrayList<String> address) throws ClassNotFoundException, SQLException {
        ArrayList<EmployeeAddressModel> employeeAddressObjs = new ArrayList<EmployeeAddressModel>();
        int j = 0;
        for(int i = 0; i < (address.size())/6; i++) {
            employeeAddressObj = new EmployeeAddressModel(address.get(j++), address.get(j++),
                            address.get(j++), address.get(j++), address.get(j++), address.get(j++));
            employeeAddressObjs.add(employeeAddressObj);
        }
        employeeModelObj = new EmployeeModel(name, designation, employeeSalary, 
                                             date, mobileNumber, employeeAddressObjs);
        return employeeDao.createEmployee(employeeModelObj);
    } 


    public boolean deleteSingleAddress(int employeeId, int updateOption) 
                                       throws ClassNotFoundException, SQLException {
        return employeeDao.deleteSingleAddress(employeeId, updateOption);
    } 

    public boolean setAddress(int employeeId, ArrayList<String> address, int updateOption) 
                               throws ClassNotFoundException, SQLException {
        String addressTypeDummy = "  ";
        employeeAddressObj = new EmployeeAddressModel(address.get(0), address.get(1), address.get(2), address.get(3), 
                                                      address.get(4), addressTypeDummy);
        return employeeDao.setAddress(employeeId, employeeAddressObj, updateOption);
    }
    
  

   /**
    * This method is logic for viewing single employee details.
    * @return concatinated string of employee id and details
    */
    public String viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException  {
        return (employeeDao.viewSingleEmployee(employeeId)).toString();
    }

    public ArrayList<String> singleEmployeeAddress(int employeeId) throws ClassNotFoundException, SQLException {
        int length;
        ArrayList<EmployeeAddressModel> singleEmployeeAddresses = employeeDao.singleEmployeeAddress(employeeId);
       
        ArrayList<String> addresses = new ArrayList<String>();
       
        for (int i = 0; i < singleEmployeeAddresses.size(); i++) {
            addresses.add((singleEmployeeAddresses.get(i)).toString());
        }
        return addresses;
    }

    /**
     * This method is logic for viewing all employee details.
     * @return array of strings of employee details.
     */
    public ArrayList<String> viewAllEmployees() throws ClassNotFoundException, SQLException  {
        ArrayList<EmployeeModel> employee = employeeDao.viewAllEmployees();
        ArrayList<String> employeeDetails = new ArrayList<String>();

        for(int i = 0; i < employee.size(); i++) {
            employeeDetails.add((employee.get(i)).toString());
        }
 
        return employeeDetails;
    }
  
    /**
     * This method deletes the employee details if present.
     * @return false if employee id was not present, true otherwise.
     */
    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.deleteEmployee(employeeId);
    }

    /**
     * This method updates employee name.
     * @params name
     * @params employeeId
     */  
    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException  {
        return employeeDao.setEmployeeName(name, employeeId);
    }

    /**
     * This method updates employee designation.
     * @params designation
     * @params employeeId
     */  
    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException  {
        return employeeDao.setEmployeeDesignation(designation, employeeId);
    }

    /**
     * This method updates employee date of birth.
     * @params date is date of birth of employee
     * @params employeeId
     */  
    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.setEmployeeDOB(date, employeeId);
    }

    /**
     * This method updates employee salary.
     * @params salary
     * @params employeeId
     */  
    public boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.setEmployeeSalary(salary, employeeId);
    }

    /**
     * This method updates employee phone number.
     * @params phoneNumber
     * @params employeeId
     */  
    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.setEmployeePhoneNumber(phoneNumber, employeeId);
    }
   

    /**
     * This method validates presence of employee id.
     */  
    public Boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.checkEmployeeID(employeeId);
    }
 
    /**
     * This method validates date of birth.
     */  
    public Date checkEmployeeDOB(String dob) throws ClassNotFoundException, SQLException {
        Date date = Date.valueOf(dob);
     /*   try {
            date = Date.valueOf(dob);
        } catch (Exception e) {
            date = null;
        } */
        return date;
    }

    /**
     * This method validates mobile number.
     * @params phoneNumber
     * @return mobileNumber
     */
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return (Pattern.matches("[7-9][0-9]{9}", phoneNumber)) ? Long.parseLong(phoneNumber) : 0;
    }

    public boolean checkPinCode(String pinCode) {
         return (Pattern.matches("[1-9][0-9]{5}", pinCode));
    }

    /**
     * This method validates salary.
     * @params salary
     * @return employeeSalary
     */
     public double checkEmployeeSalary(String salary) {
        double employeeSalary = 0;
        try {
            employeeSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            employeeSalary = 0;
        }
        return employeeSalary;
    } 
}