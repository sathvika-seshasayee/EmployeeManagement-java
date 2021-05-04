package com.ideas2it.employeemanagement.employee.service;

import java.util.Date;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.employeemanagement.employee.model.Address;

/** 
 * contains business logics for Employee database.
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-16
 */
public interface EmployeeService {

    /**
     * Gets list of project Ids assigned 
     * 
     * @param employeeId
     * @return List of project ids assigned to employee
     * @throws EmployeeManagementException 
     */
    List<String> getProjectsAssigned(int employeeId) throws EmployeeManagementException;

    /**
     * Adds one or more address to employee details
     * 
     * @param addresses address details
     * @return true if updated;
     * @throws EmployeeManagementException 
     */   
    boolean addAddresses(List<List<String>> addresses, int employeeId) throws EmployeeManagementException;

    /**
     * Gets multiple employees given the id
     * 
     * @param employeeIds ids of multiple employees
     * @return employees details
     * @throws EmployeeManagementException 
     */
    List<Employee> getSetOfEmployees(List<Integer> employeeIds) throws EmployeeManagementException;

    /**
     * Checks id of project of it is active.
     * 
     * @return true if active and present
     * @throws EmployeeManagementException 
     */
    boolean checkProjectId(int projectId) throws EmployeeManagementException;

    /**
     * Gets all project for display
     * 
     * @param isDeleted true for deleted projects, false for active projects
     * @return projects details  
     * @throws EmployeeManagementException 
     */
    List<Project> getAllProjects(boolean isDeleted) throws EmployeeManagementException;

    /** 
     * This method converts list of address details into objects
     * 
     * @param address is list of address details
     * @return address object with details of address
     */
    Address getAddressObj(List<String> address);

    /**
     * Gets employee details of all active employees
     * 
     * @return list of employee details
     * @throws EmployeeManagementException 
     */
    List<Employee> getEmployeesDetails() throws EmployeeManagementException;

    /**
     * This method updates employee details.
     * 
     * @param employeeId employee id of employee
     * @param name represents employee name.
     * @param designation represents employee designation.
     * @param employeeSalary represents salary of employee.
     * @param date represents date of birth of employee
     * @param mobileNumber represents mobile number of employee
     * @return true if updation was sucessful, false otherwise.
     * @throws EmployeeManagementException 
     */
    void updateEmployee(Employee employee) throws EmployeeManagementException;

    /**
     * Restores one  employee.
     * 
     * @param employeeId id of employee
     * @return false if employee was restored.
     * @throws EmployeeManagementException 
     */
    void restoreEmployee(int employeeId) throws EmployeeManagementException;

    /**
     * This method converts single employee details from database to string.
     * 
     * @param employeeId is id of employee.
     * @return a string with employee details.
     * @throws EmployeeManagementException 
     */
    Employee getEmployee(int employeeId) throws EmployeeManagementException;

    /** 
     * This method converts all employee and corresponding address 
     * details to string.
     * 
     * @return array list of strings of details of all employee.
     * @throws EmployeeManagementException 
     */
    List<Employee> getAllEmployees(boolean isDeleted) throws EmployeeManagementException;

    /**
     * soft deletes one employee details.
     * 
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     * @throws EmployeeManagementException 
     */
    void deleteEmployee(int employeeId) throws EmployeeManagementException;

    /**
     * checks if employee id is present in database
     * 
     * @param employeeId id of employee
     * @param isDeleted true for deleted employees, false for active employees
     * @return false is single address of employee was soft deleted
     * @throws EmployeeManagementException 
     */ 
    boolean checkEmployeeID(int employeeId, boolean isDeleted) throws EmployeeManagementException;

    /**
     * checks for correct format of phone number.
     * @param phoneNumber is the phone number of employee.
     * @return phone number in right format.
     */ 
    long checkEmployeePhoneNumber(String phoneNumber);
    
    /**
     * checks for correct format of pincode.
     * 
     * @param pinCode is the phone number of employee.
     * @return true if pinCode in right format.
     */ 
    boolean checkPinCode(String pinCode);

    /**
     * checks for correct format of salary.
     * 
     * @param salary is the salary of employee.
     * @return salary in right format.
     */
    double checkEmployeeSalary(String salary);

	/**
	 * Gets one employee details
	 *  
	 * @param employeeId id of employee
	 * @throws EmployeeManagementException  
	 */
	List<String> getEmployeeDetail(int employeeId) throws EmployeeManagementException;

	/**
     * checks for correct format of salary.
     * @param salary is the salary of employee.
     * @return salary in right format.
	 * @throws EmployeeManagementException 
     */
	void updateAssignedProjects(int employeeId, List<Integer> projectIds) throws EmployeeManagementException;
	
	/**
     * Insert employee into database
     * 
     * @param employee .
     * @return id of employee.
     * @throws EmployeeManagementException 
     */
    int insertEmployee(Employee employee) throws EmployeeManagementException;

    /**
     * Sets new addresses to an employee based on users choice
     * 
     * @return Employee object with new addresses set
     * @throws EmployeeManagementException
     */
    Employee setNewAddresses(Employee employee, String addAddress) throws EmployeeManagementException;
}