package com.ideas2it.employeemanagement.service;

import java.util.Scanner;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javafx.util.Pair;

import com.ideas2it.employeemanagement.model.EmployeeModel;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeService {
    Map<String, EmployeeModel> employees = new TreeMap<String, EmployeeModel>(); 
    EmployeeModel employeeModelObj;
    
    /**
     * This method adds new employee details into map.
     * @params employeeID
     * @params name
     * @params designation
     * @params employeeSalary
     * @params date
     * @params mobileNumber
     * @return true if employee object is created, false otherwise.
     */    
    public boolean createEmployee(String employeeID, String name, String designation, 
                                int employeeSalary, Date date, long mobileNumber) {
        employeeModelObj = new EmployeeModel(name, designation, employeeSalary, date, mobileNumber);
        return (null == employees.put(employeeID, employeeModelObj)) ;
    }

   /**
    * This method is logic for viewing single employee details.
    * @return concatinated string of employee id and details
    */
    public String viewSingleEmployee(String employeeID) {
        return employeeID + " " + (employees.get(employeeID)).toString();
    }

    /**
     * This method is logic for viewing all employee details.
     * @return array of strings of employee details.
     */
    public String[] viewAllEmployees() {
        String[] employeeDetails = new String[employees.size()];
        int i = 0;
        for(Map.Entry < String, EmployeeModel> employee : employees.entrySet()) {          
            employeeDetails[i] = employee.getKey() + "  " + employee.getValue();
            i++;
        }
    return employeeDetails;
    }
 
    /**
     * This method deletes the employee details if present.
     * @return false if employee id was not present, true otherwise.
     */
    public boolean deleteEmployee(String employeeID) {
        return (null == employees.remove(employeeID) ? false : true);
    }

    /**
     * This method updates employee name.
     * @params name
     * @params employeeID
     */  
    public void setEmployeeName(String name, String employeeID) {
        employeeModelObj.setName(name);
    }

    /**
     * This method updates employee designation.
     * @params designation
     * @params employeeID
     */  
    public void setEmployeeDesignation(String designation, String employeeID) {
        employeeModelObj.setDesignation(designation);
    }

    /**
     * This method updates employee date of birth.
     * @params date is date of birth of employee
     * @params employeeID
     */  
    public void setEmployeeDOB(Date date, String employeeID) {
        employeeModelObj.setDOB(date);
    }

    /**
     * This method updates employee salary.
     * @params salary
     * @params employeeID
     */  
    public void setEmployeeSalary(int salary, String employeeID) {
        employeeModelObj.setSalary(salary);
    }

    /**
     * This method updates employee phone number.
     * @params phoneNumber
     * @params employeeID
     */  
    public void setEmployeePhoneNumber(long phoneNumber, String employeeID) {
        employeeModelObj.setPhoneNumber(phoneNumber);
    }

    /**
     * This method validates presence of employee id.
     */  
    public Boolean checkEmployeeID(String employeeID) {
        return employees.containsKey(employeeID);
    }

    /**
     * This method validates date of birth.
     */  
    public Date checkEmployeeDOB(String dob) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * This method validates mobile number.
     * @params phoneNumber
     * @return mobileNumber
     */
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return (Pattern.matches("[1-9][0-9]{9}", phoneNumber)) ? Long.parseLong(phoneNumber) : 0;
    }

    /**
     * This method validates salary.
     * @params salary
     * @return employeeSalary
     */
     public int checkEmployeeSalary(String salary) {
        int employeeSalary = 0;
        try {
            employeeSalary = Integer.parseInt(salary);
        } catch (NumberFormatException e) {
            employeeSalary = 0;
        }
        return employeeSalary;
    }
}