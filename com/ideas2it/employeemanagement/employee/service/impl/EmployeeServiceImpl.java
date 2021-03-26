<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
package com.ideas2it.employeemanagement.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.TreeMap;
import java.lang.IllegalArgumentException;

import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeAddressModel;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

=======
package com.ideas2it.employeemanagement.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.TreeMap;
import java.lang.IllegalArgumentException;

import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeAddressModel;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public int createEmployee(String name, String designation, 
                              double salary, Date date, 
                              long mobileNumber, 
                              ArrayList<ArrayList<String>> addresses) {
        ArrayList<EmployeeAddressModel> employeeAddressObjs = 
                new ArrayList<EmployeeAddressModel>();
        if(null != addresses){
        for(ArrayList<String> address : addresses) {
            EmployeeAddressModel employeeAddressObj = getAddressObj(address);       
            employeeAddressObjs.add(employeeAddressObj);
        }
        } else {
            employeeAddressObjs = null;
        }
        EmployeeModel employeeModelObj = new EmployeeModel(name, designation, salary, 
                                             date, mobileNumber, 
                                             employeeAddressObjs);
        return employeeDao.createEmployee(employeeModelObj);
    } 

    public ArrayList<EmployeeModel> getSetOfEmployees(ArrayList<Integer> employeeIds) {
        return employeeDao.getSetOfEmployees(employeeIds);
    }

=======
    
     */    
    @Override
    public int createEmployee(String name, String designation, 
                              double salary, Date date, 
                              long mobileNumber, 
                              ArrayList<ArrayList<String>> addresses) {
        ArrayList<EmployeeAddressModel> employeeAddressObjs = 
                new ArrayList<EmployeeAddressModel>();
        if(null != addresses){
        for(ArrayList<String> address : addresses) {
            EmployeeAddressModel employeeAddressObj = getAddressObj(address);       
            employeeAddressObjs.add(employeeAddressObj);
        }
        } else {
            employeeAddressObjs = null;
        }
        EmployeeModel employeeModelObj = new EmployeeModel(name, designation, salary, 
                                             date, mobileNumber, 
                                             employeeAddressObjs);
        return employeeDao.createEmployee(employeeModelObj);
    } 

    public ArrayList<EmployeeModel> getSetOfEmployees(ArrayList<Integer> employeeIds) {
        return employeeDao.getSetOfEmployees(employeeIds);
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public boolean updateEmployee(int employeeId, String name, String designation, 
                              double salary, Date dob, 
                              long phoneNumber, 
                              ArrayList<ArrayList<String>> addresses) {
        EmployeeModel employeeModelObj = employeeDao.getSingleEmployee(employeeId);
        employeeModelObj.setId(employeeId);
        if("" != name) {
            employeeModelObj.setName(name);
        }

        if("" != designation) {
            employeeModelObj.setDesignation(designation);
        }

        if(0.0 != salary) {
            employeeModelObj.setSalary(salary);
        }

        if(0 != phoneNumber) {
            employeeModelObj.setPhoneNumber(phoneNumber);
        }

        if(null != dob) {
            employeeModelObj.setDOB(dob);
        }
        
        ArrayList<EmployeeAddressModel> employeeAddressObjs = 
                new ArrayList<EmployeeAddressModel>();
        if(null != addresses){
        for(ArrayList<String> address : addresses) {
            EmployeeAddressModel employeeAddressObj = getAddressObj(address);       
            employeeAddressObjs.add(employeeAddressObj);
        }
        } else {
            employeeAddressObjs = null;
        }
        employeeModelObj.setAddresses(employeeAddressObjs);
        
        return employeeDao.updateEmployee(employeeModelObj);
    } 


    public EmployeeAddressModel getAddressObj(ArrayList<String> address) {
    boolean isPermanantaddress = ((address.get(5)).equals("permanant"));
    EmployeeAddressModel employeeAddressObj = new EmployeeAddressModel(address.get(0), 
                                                      address.get(1), 
                                                      address.get(2), 
                                                      address.get(3), 
                                                      address.get(4), 
                                                      isPermanantaddress);
    return employeeAddressObj;
    }

=======
    
     */    
    @Override
    public boolean updateEmployee(int employeeId, String name, String designation, 
                              double salary, Date dob, 
                              long phoneNumber, 
                              ArrayList<ArrayList<String>> addresses) {
        EmployeeModel employeeModelObj = employeeDao.getSingleEmployee(employeeId);
        employeeModelObj.setId(employeeId);
        if("" != name) {
            employeeModelObj.setName(name);
        }

        if("" != designation) {
            employeeModelObj.setDesignation(designation);
        }

        if(0.0 != salary) {
            employeeModelObj.setSalary(salary);
        }

        if(0 != phoneNumber) {
            employeeModelObj.setPhoneNumber(phoneNumber);
        }

        if(null != dob) {
            employeeModelObj.setDOB(dob);
        }
        
        ArrayList<EmployeeAddressModel> employeeAddressObjs = 
                new ArrayList<EmployeeAddressModel>();
        if(null != addresses){
        for(ArrayList<String> address : addresses) {
            EmployeeAddressModel employeeAddressObj = getAddressObj(address);       
            employeeAddressObjs.add(employeeAddressObj);
        }
        } else {
            employeeAddressObjs = null;
        }
        employeeModelObj.setAddresses(employeeAddressObjs);
        
        return employeeDao.updateEmployee(employeeModelObj);
    } 


    public EmployeeAddressModel getAddressObj(ArrayList<String> address) {
    boolean isPermanantaddress = ((address.get(5)).equals("permanant"));
    EmployeeAddressModel employeeAddressObj = new EmployeeAddressModel(address.get(0), 
                                                      address.get(1), 
                                                      address.get(2), 
                                                      address.get(3), 
                                                      address.get(4), 
                                                      isPermanantaddress);
    return employeeAddressObj;
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public boolean restoreEmployee(int employeeId) { 
        return employeeDao.restoreEmployee(employeeId);
    }

=======
    
     */    
    @Override
    public boolean restoreEmployee(int employeeId) { 
        return employeeDao.restoreEmployee(employeeId);
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public boolean addAddress(int employeeId, ArrayList<String> address) {
        int addressIndex = 0; 
        EmployeeAddressModel employeeAddressObj = getAddressObj(address);
        return employeeDao.addAddress(employeeId, employeeAddressObj);
    }

=======
    
     */    
    @Override
    public boolean addAddress(int employeeId, ArrayList<String> address) {
        int addressIndex = 0; 
        EmployeeAddressModel employeeAddressObj = getAddressObj(address);
        return employeeDao.addAddress(employeeId, employeeAddressObj);
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public boolean deleteSingleAddress(int addressId) {
        return employeeDao.deleteSingleAddress(addressId);
    } 
    
=======
    
     */    
    @Override
    public boolean deleteSingleAddress(int addressId) {
        return employeeDao.deleteSingleAddress(addressId);
    } 
    
>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public String getSingleEmployee(int employeeId) {
        return (employeeDao.getSingleEmployee(employeeId)).toString();
    }

=======
    
     */    
    @Override
    public String getSingleEmployee(int employeeId) {
        return (employeeDao.getSingleEmployee(employeeId)).toString();
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public Map<Integer, String> singleEmployeeAddress(int employeeId) {
        int length;
        ArrayList<EmployeeAddressModel> singleEmployeeAddresses = 
                employeeDao.singleEmployeeAddress(employeeId);
        Map<Integer, String> address = new TreeMap<Integer, String>();
        for (int i = 0; i < singleEmployeeAddresses.size(); i++) {
            int addressId = (singleEmployeeAddresses.get(i)).getId();
            address.put(addressId, 
                        (singleEmployeeAddresses.get(i)).toString());  
        }
        return address;
    }

    public ArrayList<EmployeeModel> getAllEmployeesModel() {
        return employeeDao.getAllEmployees("active");
    }

=======
    
     */    
    @Override
    public Map<Integer, String> singleEmployeeAddress(int employeeId) {
        int length;
        ArrayList<EmployeeAddressModel> singleEmployeeAddresses = 
                employeeDao.singleEmployeeAddress(employeeId);
        Map<Integer, String> address = new TreeMap<Integer, String>();
        for (int i = 0; i < singleEmployeeAddresses.size(); i++) {
            int addressId = (singleEmployeeAddresses.get(i)).getId();
            address.put(addressId, 
                        (singleEmployeeAddresses.get(i)).toString());  
        }
        return address;
    }

    public ArrayList<EmployeeModel> getAllEmployeesModel() {
        return employeeDao.getAllEmployees("active");
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public ArrayList<String> getAllEmployees(String option) {
        ArrayList<EmployeeModel> employee = employeeDao.getAllEmployees(option);
        ArrayList<String> employeeDetails = new ArrayList<String>();
        if(null != employee) {
        for(int i = 0; i < employee.size(); i++) {
            employeeDetails.add((employee.get(i)).toString());
        }
        } else {
	    employeeDetails = null;
	}
 
        return employeeDetails;
    }
  
=======
    
     */    
    @Override
    public ArrayList<String> getAllEmployees(String option) {
        ArrayList<EmployeeModel> employee = employeeDao.getAllEmployees(option);
        ArrayList<String> employeeDetails = new ArrayList<String>();
        if(null != employee) {
        for(int i = 0; i < employee.size(); i++) {
            employeeDetails.add((employee.get(i)).toString());
        }
        } else {
	    employeeDetails = null;
	}
 
        return employeeDetails;
    }
  
>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override
    public boolean deleteEmployee(int employeeId) {
        return employeeDao.deleteEmployee(employeeId);
    }

=======
    
     */    
    @Override
    public boolean deleteEmployee(int employeeId) {
        return employeeDao.deleteEmployee(employeeId);
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override 
    public boolean checkEmployeeID(int employeeId) {
        return employeeDao.checkEmployeeID(employeeId);
    }
 
=======
    
     */    
    @Override 
    public boolean checkEmployeeID(int employeeId) {
        return employeeDao.checkEmployeeID(employeeId);
    }
 
>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
<<<<<<< HEAD:com/ideas2it/employeemanagement/employee/service/impl/EmployeeServiceImpl.java
    
     */    
    @Override 
    public Date checkEmployeeDOB(String dob) {
        Date dateOfBirth = null;
        boolean isDate = false;
            try {
               isDate = Pattern.matches("[1][9][0-9][0-9][-](?:0?[1-9]|(1)[02])[-](?:[012]?[0-9]|(3)[01])", dob);
               dateOfBirth = isDate ? Date.valueOf(dob) : null;
            } catch (IllegalArgumentException e) {}
         return dateOfBirth;
    }

=======
    
     */    
    @Override 
    public Date checkEmployeeDOB(String dob) {
        Date dateOfBirth = null;
        boolean isDate = false;
            try {
               isDate = Pattern.matches("[1][9][0-9][0-9][-](?:0?[1-9]|(1)[02])[-](?:[012]?[0-9]|(3)[01])", dob);
               dateOfBirth = isDate ? Date.valueOf(dob) : null;
            } catch (IllegalArgumentException e) {}
         return dateOfBirth;
    }

>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3:com/ideas2it/employeemanagement/service/impl/EmployeeServiceImpl.java
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return (Pattern.matches("[7-9][0-9]{9}", phoneNumber)) 
                ? Long.parseLong(phoneNumber) 
                : 0;
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