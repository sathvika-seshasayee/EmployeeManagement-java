package com.ideas2it.employeemanagement.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

import com.ideas2it.employeemanagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;
import com.ideas2it.employeemanagement.service.EmployeeService;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    EmployeeModel employeeModelObj;
    EmployeeAddressModel employeeAddressObj;

    /**
  
     * {@inheritdoc}
    
     */    
  //  @Override
    public int createEmployee(String name, String designation, double employeeSalary, Date date, 
            long mobileNumber, ArrayList<ArrayList<String>> addresses) throws ClassNotFoundException, SQLException {
        ArrayList<EmployeeAddressModel> employeeAddressObjs = new ArrayList<EmployeeAddressModel>();
        int addressIndex = 0;
        for(ArrayList<String> address : addresses) {
            employeeAddressObj = new EmployeeAddressModel(address.get(addressIndex++), address.get(addressIndex++),
                            address.get(addressIndex++), address.get(addressIndex++), address.get(addressIndex++), address.get(addressIndex++));
            employeeAddressObjs.add(employeeAddressObj);
            addressIndex = 0;
        }
        employeeModelObj = new EmployeeModel(name, designation, employeeSalary, 
                                             date, mobileNumber, employeeAddressObjs);
        return employeeDao.createEmployee(employeeModelObj);
    } 

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean restoreEmployee(int employeeId) throws ClassNotFoundException, SQLException  { 
        return employeeDao.restoreEmployee(employeeId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean addAddress(int employeeId, ArrayList<String> address) throws ClassNotFoundException,
   							        SQLException {
        int addressIndex = 0; 
        employeeAddressObj = new EmployeeAddressModel(address.get(addressIndex), address.get(addressIndex++),
                            address.get(addressIndex++), address.get(addressIndex++), address.get(addressIndex++), 
                            address.get(addressIndex++));
        return employeeDao.addAddress(employeeId, employeeAddressObj);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteSingleAddress(int employeeId, int updateOption) 
                                       throws ClassNotFoundException, SQLException {
        return employeeDao.deleteSingleAddress(employeeId, updateOption);
    } 

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setAddress(int employeeId, ArrayList<String> address, int updateOption) 
                               throws ClassNotFoundException, SQLException {
        String addressTypeDummy = "  ";
        employeeAddressObj = new EmployeeAddressModel(address.get(0), address.get(1), address.get(2), address.get(3), 
                                                      address.get(4), addressTypeDummy);
        return employeeDao.setAddress(employeeId, employeeAddressObj, updateOption);
    }
    

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public String viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException  {
        return (employeeDao.viewSingleEmployee(employeeId)).toString();
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<String> singleEmployeeAddress(int employeeId) throws ClassNotFoundException, SQLException {
        int length;
        ArrayList<EmployeeAddressModel> singleEmployeeAddresses = employeeDao.singleEmployeeAddress(employeeId);
       
        ArrayList<String> addresses = new ArrayList<String>();
       
        for (int i = 0; i < singleEmployeeAddresses.size(); i++) {
            addresses.add((singleEmployeeAddresses.get(i)).toString());
        }
        return addresses;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<String> viewAllEmployees() throws ClassNotFoundException, SQLException  {
        ArrayList<EmployeeModel> employee = employeeDao.viewAllEmployees();
        ArrayList<String> employeeDetails = new ArrayList<String>();

        for(int i = 0; i < employee.size(); i++) {
            employeeDetails.add((employee.get(i)).toString());
        }
 
        return employeeDetails;
    }
  
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.deleteEmployee(employeeId);
    }

   /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException  {
        return employeeDao.setEmployeeName(name, employeeId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException  {
        return employeeDao.setEmployeeDesignation(designation, employeeId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override  
    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.setEmployeeDOB(date, employeeId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override  
    public boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.setEmployeeSalary(salary, employeeId);
    }

   /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.setEmployeePhoneNumber(phoneNumber, employeeId);
    }
   

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, SQLException {
        return employeeDao.checkEmployeeID(employeeId);
    }
 
    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public Date checkEmployeeDOB(String dob) throws ClassNotFoundException, SQLException {
        Date date = null;
            try {
                date = Date.valueOf(dob);
            } catch (IllegalArgumentException e) {
                date = null;
            }
         return date;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return (Pattern.matches("[7-9][0-9]{9}", phoneNumber)) ? Long.parseLong(phoneNumber) : 0;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkPinCode(String pinCode) {
         return (Pattern.matches("[1-9][0-9]{5}", pinCode));
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
     public double checkEmployeeSalary(String salary) {
        double employeeSalary = 0;
        try {
            employeeSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            employeeSalary = 0;
        }
        return employeeSalary;
    } 
}