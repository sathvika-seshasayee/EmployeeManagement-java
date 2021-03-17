package com.ideas2it.employeemanagement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;

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
    int createEmployee(String name, String designation, double employeeSalary, Date date, 
            long mobileNumber, ArrayList<ArrayList<String>> addresses) throws ClassNotFoundException, SQLException;


    /**
     * restores one  employee.
     * @param employeeId id of employee
     * @return false if employee was restored.
     */
    boolean restoreEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * converts address array list into employee address POJO object.
     * @param employeeId id of employee
     * @param address address entered by user.
     * @return false is single address of employee was soft deleted
     */
    boolean addAddress(int employeeId, ArrayList<String> address) throws ClassNotFoundException,
   							        SQLException;

    /**
     * deletes one address of an employee.
     * @param employeeId id of employee
     * @param updateOption row number of address chosen by employee
     * @return false is single address of employee was soft deleted
     */
    boolean deleteSingleAddress(int employeeId, int updateOption) 
                                       throws ClassNotFoundException, SQLException;

    /**
     * This method converts the address list to address POJO objects.
     * @param employeeId is id of employee.
     * @param employeeAddressObj is object of Employee address POJO.
     * @param updateOption is the option of address to be updated.
     * @return false if address was updated.
     */
    boolean setAddress(int employeeId, ArrayList<String> address, int updateOption) 
                               throws ClassNotFoundException, SQLException;

    /**
     * This method converts single employee details from database to string.
     * @params employeeId is id of employee.
     * @return a string with employee details
     */
    public String viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * This method converts single employee address to string.
     * @param employeeId is id of employee
     * @return list of employee address strings.
     */
    ArrayList<String> singleEmployeeAddress(int employeeId) throws ClassNotFoundException, SQLException;

    /** 
     * This method converts all employee and corresponding address details to string.
     * @return array list of strings of details of all employee.
     */
    ArrayList<String> viewAllEmployees() throws ClassNotFoundException, SQLException;

    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     */
    boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates name of an employee in database.
     * @param name is new name entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException;

    
    /**
     * updates designation of an employee in database.
     * @param designation is new designation entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates date of employee in database.
     * @param date is new date entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates salary of employee in database.
     * @param salary is new salary entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException;

     /**
     * updates phone number of employee in database.
     * @param phoneNumber is new phone number entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException;
  
    /**
     * checks if employee id is present in database
     * @param employeeId id of employee
     * @return false is single address of employee was soft deleted
     * @throws ClassNotFoundException, SQLException.
     */ 
    boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * checks for correct format of date.
     * @param dob is the date of birth of employee entered by user.
     * @return date of birth in right format.
     * @throws ClassNotFoundException, SQLException.
     */ 
    Date checkEmployeeDOB(String dob) throws ClassNotFoundException, SQLException;

    /**
     * checks for correct format of phone number.
     * @param phoneNumber is the phone number of employee.
     * @return phone number in right format.
     * @throws ClassNotFoundException, SQLException.
     */ 
    long checkEmployeePhoneNumber(String phoneNumber);
    
    /**
     * checks for correct format of pincode.
     * @param pinCode is the phone number of employee.
     * @return true if pinCode in right format.
     * @throws ClassNotFoundException, SQLException.
     */ 
    boolean checkPinCode(String pinCode);

    /**
     * checks for correct format of salary.
     * @param salary is the salary of employee.
     * @return salary in right format.
     * @throws ClassNotFoundException, SQLException.
     */
    double checkEmployeeSalary(String salary);
}

    