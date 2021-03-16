package com.ideas2it.employeemanagement.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;

public class EmployeeService {
    public int createEmployee(String name, String designation, double employeeSalary, Date date, 
            long mobileNumber, ArrayList<String> address) throws ClassNotFoundException, SQLException;

    public boolean deleteSingleAddress(int employeeId, int updateOption) 
                                       throws ClassNotFoundException, SQLException;

    public boolean setAddress(int employeeId, ArrayList<String> address, int updateOption) 
                               throws ClassNotFoundException, SQLException;

    public String viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    public ArrayList<String> singleEmployeeAddress(int employeeId) throws ClassNotFoundException, SQLException;

    public ArrayList<String> viewAllEmployees() throws ClassNotFoundException, SQLException;

    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException;

    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException;
        return employeeDao.setEmployeeDOB(date, employeeId);

    public boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException;
        return employeeDao.setEmployeeSalary(salary, employeeId);

    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException;
}

    