package com.ideas2it.EmployeeManagementSystem.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import javafx.util.Pair;

import com.ideas2it.EmployeeManagementSystem.Service.EmployeeService;

/**
 * Links view and service layers.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeController {
    ServiceEmployee serviceObj = new Service();
    public boolean checkEmployeeID(String employeeID) {
        return serviceObj.checkEmployeeID(employeeID);
    }
  
    public void createEmployee(String employeeID, String name, String designation, 
                               int employeeSalary, Date date, long mobileNumber) {
        serviceObj.createEmployee(employeeID, name, designation, employeeSalary, date, mobileNumber);
    }

    public void setEmployeeName(String name, String employeeID) {
        serviceObj.setEmployeeName(name, employeeID);
    }

    public void setEmployeeDesignation(String designation, String employeeID) {
        serviceObj.setEmployeeDesignation(designation, employeeID);
    }
    
    public void setEmployeeDOB(Date date, String employeeID) {
        serviceObj.setEmployeeDOB(date, employeeID);
    }
 
    public void setEmployeeSalary(int salary, String employeeID) {
        serviceObj.setEmployeeSalary(salary, employeeID);
    }

    public void setEmployeePhoneNumber(long phoneNumber, String employeeID) {
        serviceObj.setEmployeePhoneNumber(phoneNumber, employeeID);
    }

    public void deleteEmployee(String employeeID) {
        serviceObj.deleteEmployee(employeeID);
    }

    public String displaySingleEmployee(String employeeID) {
        return serviceObj.displaySingleEmployee(employeeID);
    }

    public String[] displayAllEmployees() {
        return serviceObj.displayAllEmployees();
    }

    public Date checkEmployeeDOB(String dob) {
        return serviceObj.checkEmployeeDOB(dob);
    }

    public long checkEmployeePhoneNumber(String phoneNumber) {
        return serviceObj.checkEmployeePhoneNumber(phoneNumber);
    }

    public int checkEmployeeSalary(String salary) {
        return serviceObj.checkEmployeeSalary(salary);
    }
}