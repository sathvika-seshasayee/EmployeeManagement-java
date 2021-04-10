package com.ideas2it.employeemanagement.employee.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.model.Address;

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
                       List<List<String>> addresses);

    /**
     * Add addresses to employee details
     * @param addresses one or more address details
     * @param employeeId id of employee
     */
    boolean addAddresses(List<List<String>> addresses, int employeeId);

    /**
     * Assigns a project to employee
     * @param projectId id of project
\    * @param employeeId id of employee
     * @return true if assigned, false otherwise
     */
    boolean assignProject(int projectId, int employeeId);

    /**
     * Deleted one address for an employee
     * @param addressId id of address to be deleted
     * @param employeeId id of employee
     */
    boolean deleteOneAddress(int addressId, int employeeId);
 
    /**
     * Adds one or more address to employee details
     * @param addresses address details
     * @return true if updated;
    boolean addAddress(List<List<String>> addresses, int employeeId);

    /**
     * Gets multiple employees given the id
     * @param employeeIds ids of multiple employees
     * @return employees details
     */
    List<Employee> getSetOfEmployees(List<Integer> employeeIds);

    /**
     * Checks id of project of it is active.
     * @return true if active and present
     */
    boolean checkProjectId(int projectId);

    /**
     * Gets all project for display
     * @param isDeleted true for deleted projects, false for active projects
     * @return projects details  
     */
    String displayAllProjects(boolean isDeleted);

    /** 
     * This method converts list of address details into objects
     * @param address is list of address details
     * @return address object with details of address
     */
    Address getAddressObj(List<String> address);

    /**
     * Gets employee details of all active employees
     * @return list of employee details
     */
    List<Employee> getEmployeesDetails();

    /**
     * Deletes employees from project
     * @param projectId id of project
     * @param employeeId id of employee
     */
    boolean unAssignProject(int projectId, int employeeId) ;
  
    /**
     * This method updates employee details.
     * @param employeeId employee id of employee
     * @param name represents employee name.
     * @param designation represents employee designation.
     * @param employeeSalary represents salary of employee.
     * @param date represents date of birth of employee
     * @param mobileNumber represents mobile number of employee
     * @return true if updation was sucessful, false otherwise.
     */
    public boolean updateEmployee(int employeeId, String name, String designation, 
                                  double salary, Date dob, long phoneNumber);

    /**
     * restores one  employee.
     * @param employeeId id of employee
     * @return false if employee was restored.
     */
    boolean restoreEmployee(int employeeId);

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
    String getAllEmployees(boolean isDeleted);

    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     */
    boolean deleteEmployee(int employeeId);

    /**
     * checks if employee id is present in database
     * @param employeeId id of employee
     * @param isDeleted true for deleted employees, false for active employees
     * @return false is single address of employee was soft deleted
     */ 
    boolean checkEmployeeID(int employeeId, boolean isDeleted);

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