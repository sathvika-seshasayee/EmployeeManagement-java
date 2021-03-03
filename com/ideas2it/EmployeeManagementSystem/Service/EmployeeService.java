package com.ideas2it.EmployeeManagementSystem.Service;

import java.util.Scanner;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javafx.util.Pair;

import com.ideas2it.EmployeeManagementSystem.Model.EmployeeModel;

/**
 * Contains logic behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeService {
    Map<String, EmployeeModel> employees = new TreeMap<String, EmployeeModel>(); 
    EmployeeModel employeeModelObj;
    
    /**
     * This method adds new employee details into map.
     */    
    public void createEmployee(String employeeID, String name, String designation, 
                                int employeeSalary, Date date, long mobileNumber) {
        employeeModelObj = new EmployeeModel(name, designation, employeeSalary, date, mobileNumber);
        employees.put(employeeID, employeeModelObj);
    }

    public String displaySingleEmployee(String employeeID) {
        return employeeID + " " + (employees.get(employeeID)).toString();
    }

    /**
     * This method returns array of strings of employee details.
     */
    public String[] displayAllEmployees() {
        String[] employeeDetails = new String[employees.size()];
        int i = 0;
        for(Map.Entry < String, EmployeeModel> entry : employees.entrySet()) {          
            employeeDetails[i] = entry.getKey() + "  " + entry.getValue();
            i++;
        }
    return employeeDetails;
    }
 
    public void deleteEmployee(String employeeID) {
        employees.remove(employeeID);
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
        boolean checkEmployeeIDStatus = false;
        if(employees.containsKey(employeeID)) {
            checkEmployeeIDStatus = true;
        }
    return checkEmployeeIDStatus;
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
        long mobileNumber = 0;
        if ((Pattern.matches("[1-9][0-9]{9}", phoneNumber))) {
           mobileNumber = Long.parseLong(phoneNumber);
        } 
        return mobileNumber;
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