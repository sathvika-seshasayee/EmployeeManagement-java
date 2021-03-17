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

    // java compiler adds public abstract for methods
    /**
     * This method is used to add employee details name, salary,
     * date of birth, phone number, address into mysql database.
     * @param employeeModelObj is the object of Employee POJO
     * @return id of employee
     * @throws ClassNotFoundException, SQLException.
     */
    int createEmployee(EmployeeModel employeeModelObj) throws ClassNotFoundException, SQLException;   

    /**
     * This method updates the address of employee in the database.
     * @param employeeId is id of employee.
     * @param employeeAddressObj is object of Employee address POJO.
     * @param updateOption is the option of address to be updated
     * @return false if address was updated.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean setAddress(int employeeId, EmployeeAddressModel employeeAddressObj, int updateOption) throws ClassNotFoundException, SQLException ;

    /**
     * This method retrives single employee address from database.
     * @param employeeId is id of employee
     * @return list of employee address POJO objects.
     * @throws ClassNotFoundException, SQLException.
     */
    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId)
        	throws ClassNotFoundException, SQLException;

    public boolean addAddress(int employeeId, 
	                      EmployeeAddressModel employeeAddressObj) throws ClassNotFoundException,
   							        SQLException;

    public boolean restoreEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    public EmployeeModel viewSingleEmployee(int employeeId) throws 
	        ClassNotFoundException, SQLException;

    /**
     * This method is used to retrive the address Id. 
     * @ employeeId is id of employee
     * @ updateOption row number of chosen address by the user from given options of 
     * particular address of employee.
     * @return address Id is returned.
     * @throws ClassNotFoundException, SQLException.
     */ 
    int getAddressId(int employeeId, int updateOption)  throws ClassNotFoundException, SQLException ;
 
    /**
     * This methods soft deletes all the address of a single employee from database. 
     * employeeId is id of employee.
     * @return false if addresses are deleted
     * @throws ClassNotFoundException, SQLException.
     */
    boolean deleteAllAddress(int employeeId) throws ClassNotFoundException, SQLException;

    /** 
     * This method retrives all employee and corresponding address details from database.
     * @return array list of Employee POJO objects with details of all employee.
     * @throws ClassNotFoundException, SQLException.
     */
    ArrayList<EmployeeModel> viewAllEmployees() throws ClassNotFoundException, SQLException;

    /**
     * This method retrives single employee details from database.
     * @params employeeId is id of employee.
     * @return employee POJO object with employee details
     * @throws ClassNotFoundException, SQLException.
     */
    

    /**
     * gets all employee Ids from database.
     * @return array list of employee ids.
     * @throws ClassNotFoundException, SQLException.
     */
    ArrayList<Integer> getEmployeeIds () throws ClassNotFoundException, SQLException;

    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * deletes one address of an employee.
     * @param employeeId id of employee
     * @param updateOption row number of address chosen by employee
     * @return false is single address of employee was soft deleted
     * @throws ClassNotFoundException, SQLException.
     */
    boolean deleteSingleAddress(int employeeId, int updateOption) throws ClassNotFoundException, SQLException; 

    /**
     * updates name of an employee in database.
     * @param name is new name entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates designation of an employee in database.
     * @param designation is new designation entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates date of employee in database.
     * @param date is new date entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates salary of employee in database.
     * @param salary is new salary entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * updates phone number of employee in database.
     * @param phone number is new phone number entered by user.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     * @throws ClassNotFoundException, SQLException.
     */
    boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException;

    /**
     * check if employee Id is in database.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, SQLException;

}

     