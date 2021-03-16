package com.ideas2it.employeemanagement.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;

public inetrface EmployeeDao {

    public int createEmployee(EmployeeModel employeeModelObj) throws ClassNotFoundException, SQLException;

    public boolean setAddress(int employeeId, EmployeeAddressModel employeeAddressObj, int updateOption) throws ClassNotFoundException, SQLException ;

    public int getAddressId(int employeeId, int updateOption)  throws ClassNotFoundException, SQLException ;
  
    public boolean deleteAllAddress(int employeeId) throws ClassNotFoundException, SQLException;

    public ArrayList<EmployeeModel> viewAllEmployees() throws ClassNotFoundException, SQLException;

    public EmployeeModel viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException;
        public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId) throws ClassNotFoundException, SQLException;

    public ArrayList<Integer> getEmployeeIds () throws ClassNotFoundException, SQLException;

    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    public boolean deleteSingleAddress(int employeeId, int updateOption) throws ClassNotFoundException, SQLException; 

    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException;

}

     