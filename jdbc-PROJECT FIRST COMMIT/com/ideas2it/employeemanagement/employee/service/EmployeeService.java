package com.ideas2it.employeemanagement.employee.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeAddressModel;

/** 
 * contains business logics for Employee database.
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-16
 */
public interface EmployeeService {
    /**
     * creates employee POJO object from user entered details.
     * @param name represents employee name.
     * @param designation represents employee designation.
     * @param employeeSalary represents salary of employee.
     * @param date represents date of birth of employee
     * @param mobileNumber represents mobile number of employee
     * @param address represents list of addresses entered by user
     * @return employee id from database.
     */
    int createEmployee(String name, String designation, double employeeSalary,
                       Date date, long mobileNumber, 
                       ArrayList<ArrayList<String>> addresses);

    /** 
     * This method converts list of address details into objects
     * @param address is list of address details
     * @return address object with details of address
     */
    EmployeeAddressModel getAddressObj(ArrayList<String> address);
  
    /**
     * This method updates employee details.
     * @param employeeId employee id of employee
     * @param name represents employee name.
     * @param designation represents employee designation.
     * @param employeeSalary represents salary of employee.
     * @param date represents date of birth of employee
     * @param mobileNumber represents mobile number of employee
     * @param address represents list of addresses entered by user
     * @return true if updation was sucessful, false otherwise.
     */
    public boolean updateEmployee(int employeeId, String name, String designation, 
                                  double salary, Date dob, long phoneNumber, 
                                  ArrayList<ArrayList<String>> addresses);

    /**
     * restores one  employee.
     * @param employeeId id of employee
     * @return false if employee was restored.
     */
    boolean restoreEmployee(int employeeId);

    /**
     * converts address array list into employee address POJO object.
     * @param employeeId id of employee
     * @param address address entered by user.
     * @return false is single address of employee was soft deleted
     */
    boolean addAddress(int employeeId, ArrayList<String> address);

    /**
     * deletes one address of an employee.
     * @param employeeId id of employee
     * @param updateOption row number of address chosen by employee
     * @return false is single address of employee was soft deleted
     */
    boolean deleteSingleAddress(int employeeId);

    /**
     * This method converts single employee details from database to string.
     * @params employeeId is id of employee.
     * @return a string with employee details.
     */
    public String getSingleEmployee(int employeeId);

    /**
     * This method converts single employee address to string.
     * @param employeeId is id of employee
     * @return list of employee address strings.
     */
    Map<Integer, String> singleEmployeeAddress(int employeeId);

    /** 
     * This method converts all employee and corresponding address 
     * details to string.
     * @return array list of strings of details of all employee.
     */
    ArrayList<String> getAllEmployees(String Option);

    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     */
    boolean deleteEmployee(int employeeId);

    /**
     * checks if employee id is present in database
     * @param employeeId id of employee
     * @return false is single address of employee was soft deleted
     */ 
    boolean checkEmployeeID(int employeeId);

    /**
     * checks for correct format of date.
     * @param dob is the date of birth of employee entered by user.
     * @return date of birth in right format.
     */ 
    Date checkEmployeeDOB(String dob);

    /**
     * checks for correct format of phone number.
     * @param phoneNumber is the phone number of employee.
     * @return phone number in right format.
     */ 
    long checkEmployeePhoneNumber(String phoneNumber);
    
    /**
     * checks for correct format of pincode.
     * @param pinCode is the phone number of employee.
     * @return true if pinCode in right format.
     */ 
    boolean checkPinCode(String pinCode);

    /**
     * checks for correct format of salary.
     * @param salary is the salary of employee.
     * @return salary in right format.
     */
    double checkEmployeeSalary(String salary);
}