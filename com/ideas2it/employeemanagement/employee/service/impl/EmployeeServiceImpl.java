package com.ideas2it.employeemanagement.employee.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.TreeMap;
import java.lang.IllegalArgumentException;

import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    
  
    /**
     * {@inheritdoc}
     */
    @Override
    public int createEmployee(String name, String designation, 
                              double salary, Date date, 
                              long mobileNumber, 
                              List<List<String>> addresses) {
        List<Address> newAddresses = 
                new ArrayList<Address>();
        if(null != addresses){
            for(List<String> address : addresses) {
                Address singleAddress = getAddressObj(address);       
                newAddresses .add(singleAddress);
            }
        } else {
            newAddresses  = null;
        }
        Employee employee = new Employee(name, designation, salary, 
                                                 date, mobileNumber, 
                                                 newAddresses );
        return employeeDao.createEmployee(employee);
    } 

    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) {
        List<Employee> employees = new ArrayList<Employee>();
        if(!employeeIds.isEmpty()) {
            employees = employeeDao.getSetOfEmployees(employeeIds);
        } 
        return employees;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public boolean updateEmployee(int employeeId, String name, String designation, 
                              double salary, Date dob, long phoneNumber, 
                              List<List<String>> addresses, int addressId,
                              int projectId) {
        List<Address> employeeAddressObjs = 
                new ArrayList<Address>();
        Employee employee = new Employee(employeeId);
        employee = employeeDao.getSingleEmployee(employeeId);
        ProjectServiceImpl projectService = new ProjectServiceImpl();

        if("" != name) {
            employee.setName(name);
        }

        if("" != designation) {
            employee.setDesignation(designation);
        }

        if(0.0 != salary) {
            employee.setSalary(salary);
        }

        if(0 != phoneNumber) {
            employee.setPhoneNumber(phoneNumber);
        }

        if(null != dob) {
            employee.setDob(dob);
        }

        if (0 != addressId) {
            employeeAddressObjs = employee.getAddresses();
            Address addressToRemove = new Address();
            for(Address address : employeeAddressObjs) {
                if(addressId == address.getId()) {
                    addressToRemove = address;
                    employeeAddressObjs.remove(addressToRemove);
                    addressToRemove.setIsDeleted(true);
                    employeeAddressObjs.add(addressToRemove);            
                    employee.setAddresses(employeeAddressObjs);
                    break;
                }
            }  
            
        }

        if(0 != projectId) {
            List<Project> projects = employee.getProjects();
            projects.add(projectService.getSingleProject(projectId));
            employee.setProjects(projects);
        }
        
        if(null != addresses){
            for(List<String> address : addresses) {
                Address employeeAddressObj = getAddressObj(address);       
                employeeAddressObjs.add(employeeAddressObj);
                employee.setAddresses(employeeAddressObjs);
            }
        } else {
            employeeAddressObjs = null;
        }
        return employeeDao.updateEmployee(employee);
    } 


    public Address getAddressObj(List<String> address) {
    boolean isPermanantaddress = ((address.get(5)).equals("permanant"));
    Address employeeAddressObj = new Address(address.get(0), 
                                                      address.get(1), 
                                                      address.get(2), 
                                                      address.get(3), 
                                                      address.get(4), 
                                                      isPermanantaddress);
    return employeeAddressObj;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public boolean restoreEmployee(int employeeId) { 
        Employee employee = new Employee(employeeId);
        employee.setIsDeleted(false);
        return employeeDao.updateEmployee(employee);
    }

    public boolean unAssignProject(int projectId, int employeeId) {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        Employee employee = employeeDao.getSingleEmployee(employeeId);
        List<Project> projects = employee.getProjects();
            for(Project project : projects) {
                 if(projectId == project.getId()) {
                     projects.remove(project);
                     break;
                 }
             } 
        employee.setProjects(projects);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public String getSingleEmployee(int employeeId) {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        List<Integer> projectIds = new ArrayList<Integer>();
        String employeeDetails = "";
        Employee employee = employeeDao.getSingleEmployee(employeeId);
        employeeDetails = employeeDetails + employee.toString();
        if(null != employee.getAddresses()) {
            Set<Address> addresses = new HashSet<Address>(employee.getAddresses());
            for(Address address: addresses) {
                if(false == address.getIsDeleted()) {
                    employeeDetails += address.toString();
                }
            }
        }
        if(!(employee.getProjects().isEmpty())) {
            for (Project project : employee.getProjects()) {
                projectIds.add(project.getId());
            }
            employeeDetails += "\n";
            for(Project project : projectService.getSetOfProjects(projectIds)) {
                if(false == project.getIsDeleted()) {
                    employeeDetails += project.toString();
                }
            }
        }    
        return employeeDetails;
    }

    /**
     * {@inheritdoc}
     */  
    @Override
    public Map<Integer, String> singleEmployeeAddress(int employeeId) {
        int length;
        Employee employee = new Employee(employeeId);
        employee = 
                employeeDao.getSingleEmployee(employeeId);
        System.out.println(employee);
        List<Address> addresses = employee.getAddresses();
        Map<Integer, String> address = new TreeMap<Integer, String>();
        for (int i = 0; i < addresses.size(); i++) {
            int addressId = (addresses.get(i)).getId();
            address.put(addressId, 
                        (addresses.get(i)).toString());  
        }
        return address;
    }

    public List<Employee> getAllEmployeesModel() {
        List<Employee> employees = employeeDao.getAllEmployees(false);
        List<String> employeeDetails = new ArrayList<String>();
        List<Employee> employeess = new ArrayList<Employee>();
        String line = "----------------------------";
        if(null != employees) {
            for(Employee employee : employees) {
                Set<Address> addresses = new HashSet<Address>(employee.getAddresses());
                if(!addresses.isEmpty()) {
                for(Address address: addresses) {
                    if(true == address.getIsDeleted()) {
                        addresses.remove(address);
                    }
                  }
                }
                employee.setAddresses(new ArrayList(addresses));
                employeess.add(employee);
         }
      }
        return employeess;
    }

    public String displayAllProjects(boolean isDeleted) {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        return projectService.getOnlyProjects(isDeleted);
    }
 
    public boolean checkProjectId(int projectId) {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        return projectService.checkProjectId(projectId, false);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public String getAllEmployees(boolean isDeleted) {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        List<Employee> employees = employeeDao.getAllEmployees(isDeleted);
        String employeeDetails = "";
        String line = "----------------------------------------\n";
        if(!employees.isEmpty()) {
            for(Employee employee : employees) {
                employeeDetails = employeeDetails + employee.toString();
                if(!employee.getAddresses().isEmpty()) {
                    for(Address address : employee.getAddresses()) {
                        if(false == address.getIsDeleted()) {
                            employeeDetails = employeeDetails + address.toString();
                        }
                    }
                }
                if(!employee.getProjects().isEmpty()) {
                    employeeDetails = employeeDetails + "\nProjects assigned  : ";
                    for(Project project : employee.getProjects()) {
                        employeeDetails = employeeDetails + String.valueOf(project.getId()) + "   ";
                     }     
                     employeeDetails += "\n";             
                }
                employeeDetails = employeeDetails + line;
            }
       }     
       return employeeDetails;
    }
  
    /**
     * {@inheritdoc}
     */   
    @Override
    public boolean deleteEmployee(int employeeId) {
        Employee employee = employeeDao.getSingleEmployee(employeeId);
        employee.setIsDeleted(true);
        employee.setProjects(null);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritdoc}
     */ 
    @Override 
    public boolean checkEmployeeID(int employeeId, boolean isDeleted) {
        Employee employee = employeeDao.checkEmployeeID(employeeId);
        boolean idPresent = false;
        if(null != employee) {
            idPresent = isDeleted 
                        ? (true == employee.getIsDeleted())
                        : (false == employee.getIsDeleted());
        }
        return idPresent;
    }
 
    /**
     * {@inheritdoc}
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