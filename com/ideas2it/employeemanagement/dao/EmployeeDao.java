package com.ideas2it.employeemanagement.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;

/**
 * interface for Employee Dao
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-16
 */
public interface EmployeeDao {
    /**
     * This method is used to add employee details name, salary,
     * date of birth, phone number, address into mysql database.
     * @param employeeModelObj is the object of Employee POJO
     * @return id of employee
     * @throws ClassNotFoundException, SQLException.
     */
    int createEmployee(EmployeeModel employeeModelObj) throws 
            ClassNotFoundException, SQLException;   

    /**
     * This method updates employee details.
     * @param employeeModelObj details of employee
     * @return true if updation was sucessfull, false otherwise.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean updateEmployee(EmployeeModel employeeModelObj) throws 
                                      ClassNotFoundException, SQLException;

    /**
     * This method retrives single employee address from database.
     * @param employeeId is id of employee
     * @return list of employee address POJO objects.
     * @throws ClassNotFoundException, SQLException.
     */
    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId)
        	throws ClassNotFoundException, SQLException;

    /**
     * This method adds one address to database.
     * @param employeeId is id of employee.
     * @param employeeAddressModelObj contains details of 
     * employee in Employee address POJO.
     * @return false if address was created
     * @throws ClassNotFoundException, SQLException.
     */
    public boolean addAddress(int employeeId, 
	                      EmployeeAddressModel employeeAddressObj) throws 
                                         ClassNotFoundException, SQLException;

    /**
     * This method restores deleted employee
     * @param employeeId is id of employee.
     * @return false if employee was restored
     * @throws ClassNotFoundException, SQLException.
     */
    public boolean restoreEmployee(int employeeId) throws 
            ClassNotFoundException, SQLException;

    /**
     * This method gets one employee details from database
     * @param employeeId is id of employee.
     * @return Employee pojo object with employee details
     * @throws ClassNotFoundException, SQLException.
     */
    public EmployeeModel getSingleEmployee(int employeeId) throws            // change name of function
	        ClassNotFoundException, SQLException;
 
    /**
     * This methods soft deletes all the address of a single 
     * employee from database. 
     * employeeId is id of employee.
     * @return false if addresses are deleted
     * @throws ClassNotFoundException, SQLException.
     */
    boolean deleteAllAddress(int employeeId) throws ClassNotFoundException, 
                                                    SQLException;

    /** 
     * This method retrives all employee and corresponding address
     * details from database.
     * @return array list of Employee POJO objects with details of all employee.
     * @throws ClassNotFoundException, SQLException.
     */
    ArrayList<EmployeeModel> viewAllEmployees(String option) throws 
            ClassNotFoundException, SQLException;

    /**
     * This method retrives single employee details from database.
     * @params employeeId is id of employee.
     * @return employee POJO object with employee details
     * @throws ClassNotFoundException, SQLException.
     */
    
    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean deleteEmployee(int employeeId) throws ClassNotFoundException, 
                                                  SQLException;

    /**
     * deletes one address of an employee.
     * @param employeeId id of employee
     * @param updateOption row number of address chosen by employee
     * @return false is single address of employee was soft deleted
     * @throws ClassNotFoundException, SQLException.
     */
    boolean deleteSingleAddress(int addressId) throws ClassNotFoundException, 
                                                      SQLException; 

    /**
     * check if employee Id is in database.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, 
                                                   SQLException;

}

     