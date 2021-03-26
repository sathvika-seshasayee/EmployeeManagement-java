<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/dao/EmployeeDao.java
package com.ideas2it.employeemanagement.employee.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeAddressModel;

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
     */
    int createEmployee(EmployeeModel employeeModelObj);   

    /**
     * This method updates employee details.
     * @param employeeModelObj details of employee
     * @return true if updation was sucessfull, false otherwise.
     */
    boolean updateEmployee(EmployeeModel employeeModelObj);

    /**
     * This method retrives single employee address from database.
     * @param employeeId is id of employee
     * @return list of employee address POJO objects.
     */
    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId);

    /**
     * This method adds one address to database.
     * @param employeeId is id of employee.
     * @param employeeAddressModelObj contains details of 
     * employee in Employee address POJO.
     * @return false if address was created
     */
    public boolean addAddress(int employeeId, 
	                      EmployeeAddressModel employeeAddressObj);

    /**
     * This method restores deleted employee
     * @param employeeId is id of employee.
     * @return false if employee was restored
     */
    public boolean restoreEmployee(int employeeId);

    /**
     * This method gets one employee details from database
     * @param employeeId is id of employee.
     * @return Employee pojo object with employee details
     */
    public EmployeeModel getSingleEmployee(int employeeId);
 
    /**
     * This methods soft deletes all the address of a single 
     * employee from database. 
     * employeeId is id of employee.
     * @return false if addresses are deleted
     */
    boolean deleteAllAddress(int employeeId);

    /** 
     * This method retrives all employee and corresponding address
     * details from database.
     * @return array list of Employee POJO objects with details of all employee.
     */
    ArrayList<EmployeeModel> getAllEmployees(String option);

    /**
     * This method retrives single employee details from database.
     * @params employeeId is id of employee.
     * @return employee POJO object with employee details
     */
    
    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     */
    boolean deleteEmployee(int employeeId);

    /**
     * deletes one address of an employee.
     * @param employeeId id of employee
     * @param updateOption row number of address chosen by employee
     * @return false is single address of employee was soft deleted
     */
    boolean deleteSingleAddress(int addressId); 

    /**
     * check if employee Id is in database.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean checkEmployeeID(int employeeId);

}

=======
package com.ideas2it.employeemanagement.employee.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeAddressModel;

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
     */
    int createEmployee(EmployeeModel employeeModelObj);   

    /**
     * This method updates employee details.
     * @param employeeModelObj details of employee
     * @return true if updation was sucessfull, false otherwise.
     */
    boolean updateEmployee(EmployeeModel employeeModelObj);

    /**
     * This method retrives single employee address from database.
     * @param employeeId is id of employee
     * @return list of employee address POJO objects.
     */
    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId);

    /**
     * This method adds one address to database.
     * @param employeeId is id of employee.
     * @param employeeAddressModelObj contains details of 
     * employee in Employee address POJO.
     * @return false if address was created
     */
    public boolean addAddress(int employeeId, 
	                      EmployeeAddressModel employeeAddressObj);

    /**
     * This method restores deleted employee
     * @param employeeId is id of employee.
     * @return false if employee was restored
     */
    public boolean restoreEmployee(int employeeId);

    /**
     * This method gets one employee details from database
     * @param employeeId is id of employee.
     * @return Employee pojo object with employee details
     */
    public EmployeeModel getSingleEmployee(int employeeId);
 
    /**
     * This methods soft deletes all the address of a single 
     * employee from database. 
     * employeeId is id of employee.
     * @return false if addresses are deleted
     */
    boolean deleteAllAddress(int employeeId);

    /** 
     * This method retrives all employee and corresponding address
     * details from database.
     * @return array list of Employee POJO objects with details of all employee.
     */
    ArrayList<EmployeeModel> getAllEmployees(String option);

    /**
     * This method retrives single employee details from database.
     * @params employeeId is id of employee.
     * @return employee POJO object with employee details
     */
    
    /**
     * soft deletes one employee details.
     * @param is id of employee.
     * @return false if employee details were soft deleted.
     */
    boolean deleteEmployee(int employeeId);

    /**
     * deletes one address of an employee.
     * @param employeeId id of employee
     * @param updateOption row number of address chosen by employee
     * @return false is single address of employee was soft deleted
     */
    boolean deleteSingleAddress(int addressId); 

    /**
     * check if employee Id is in database.
     * @param employeeId is id of employee
     * @return false if upation was sucessful.
     */
    boolean checkEmployeeID(int employeeId);

}

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/dao/EmployeeDao.java
     