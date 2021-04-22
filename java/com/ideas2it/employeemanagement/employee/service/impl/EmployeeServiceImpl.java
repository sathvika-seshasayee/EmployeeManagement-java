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

import com.ideas2it.CustomException.EmployeeManagementException;
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
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public int createEmployee(int employeeId, String name, String designation, 
                              double salary, Date date, 
                              long mobileNumber, 
                              List<List<String>> addresses) throws EmployeeManagementException {
        List<Address> newAddresses = 
                new ArrayList<Address>();
        if(null != addresses){
            for(List<String> address : addresses) {    
                newAddresses.add(getAddressObj(address));
            }
        }
        Employee employee = new Employee(name, designation, salary, 
                                                 date, mobileNumber, 
                                                 newAddresses );
        if(0 != employeeId) {
            employee.setId(employeeId);
        }
        return employeeDao.createEmployee(employee);
    } 

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        List<Employee> employees = new ArrayList<Employee>();
        if(!employeeIds.isEmpty()) {
            employees = employeeDao.getSetOfEmployees(employeeIds);
            
        } 
        return employees;
    }
    
    
    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean addAddresses(List<List<String>> addresses, int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        List<Address> employeeAddresses = new ArrayList<Address>();

            if(!employee.getAddresses().isEmpty()) {
                    employeeAddresses = employee.getAddresses();
            }

            for(List<String> address : addresses) {      
                employeeAddresses.add(getAddressObj(address));
                employee.setAddresses(employeeAddresses);
            }
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean deleteOneAddress(int addressId, int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        List<Address> addresses = employee.getAddresses();

        for(Address address : addresses) {

            if(addressId == address.getId()) {
                addresses.remove(address);
                address.setIsDeleted(true);
                addresses.add(address);            
                employee.setAddresses(addresses);
                break;
            }
        }      
        return employeeDao.updateEmployee(employee);
    }
    
    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean updateEmployee(int employeeId, String name, String designation, 
                                  double salary, Date dob, long phoneNumber, List<List<String>> addresses) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        List<Address> newAddresses = new ArrayList<Address>();

        if ("" != name) {
            employee.setName(name);
        }

        if ("" != designation) {
            employee.setDesignation(designation);
        }

        if (0.0 != salary) {
            employee.setSalary(salary);
        }

        if (0 != phoneNumber) {
            employee.setPhoneNumber(phoneNumber);
        }

        if (null != dob) {
            employee.setDob(dob);
        }
        
        if(null != addresses){
            for(List<String> address : addresses) {    
                newAddresses.add(getAddressObj(address));
            }
            employee.setAddresses(newAddresses);
        }

        return employeeDao.updateEmployee(employee);
    } 

  
    /**
     * Gets address string and converts to object
     * @param address details
     * @return address object
     */
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
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean restoreEmployee(int employeeId) throws EmployeeManagementException { 
        Employee employee = employeeDao.getEmployee(employeeId);
        employee.setIsDeleted(false);
        return employeeDao.updateEmployee(employee);
    }
    
    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean updateAssignedProjects(int employeeId, List<Integer> projectIds) throws EmployeeManagementException {
    	ProjectServiceImpl projectService = new ProjectServiceImpl();
    	Employee employee = employeeDao.getEmployee(employeeId);
    	if(!projectIds.isEmpty()) {
    	    List<Project> projects = projectService.getMultipleProjects(projectIds);
    	    employee.setProjects(projects);
    	} else {
    		employee.setProjects(null);
    	}

    	return employeeDao.updateEmployee(employee);
    }


    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    public List<String> getProjectsAssigned(int employeeId) throws EmployeeManagementException {
    	Employee employee = employeeDao.getEmployee(employeeId);
    	List<String> projectIds = new ArrayList<String>();
    	if(!(employee.getProjects().isEmpty())) {
           for (Project project : employee.getProjects()) {
           	projectIds.add(String.valueOf(project.getId()));
           }
        }
    	return projectIds;
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public List<String> getEmployee(int employeeId) throws EmployeeManagementException {
    	Employee employee = employeeDao.getEmployee(employeeId);
        List<String> employeeDetail = getEmployeeDetail(employeeId);
        
        if(!(employee.getProjects().isEmpty())) {
        	 String projectIds = "";
            for (Project project : employee.getProjects()) {
            	projectIds += project.getId() + "-";
            }
            projectIds = projectIds.substring(0, projectIds.length() - 1);  
            employeeDetail.add(projectIds);
        } else {
        	employeeDetail.add("-");
        }
        return employeeDetail;
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public List<String> getEmployeeDetail(int employeeId) throws EmployeeManagementException {
    	Employee employee = employeeDao.getEmployee(employeeId);
        List<String> employeeDetail = new ArrayList<String>();
        
        employeeDetail.add(String.valueOf(employee.getId()));
        employeeDetail.add(employee.getName());
        employeeDetail.add(employee.getDesignation());
        employeeDetail.add(String.valueOf(employee.getSalary()));
        employeeDetail.add(String.valueOf(employee.getDob()));
        employeeDetail.add(String.valueOf(employee.getPhoneNumber()));
        
        if(null != employee.getAddresses()) {
            for(Address address: employee.getAddresses()) {
                if(false == address.getIsDeleted()) {
                	String addressType = address.getIsPermanantAddress() ? "Permanant" : "Temporary";
                	employeeDetail.add(addressType);
                	employeeDetail.add(String.valueOf(address.getDoorNoAndStreet()));
                	employeeDetail.add(String.valueOf(address.getCity()));
                	employeeDetail.add(String.valueOf(address.getState()));
                	employeeDetail.add(String.valueOf(address.getCountry()));
                	employeeDetail.add(String.valueOf(address.getPincode()));
                }
            }
        } 
        return employeeDetail;
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */  
    @Override
    public List<Employee> getEmployeesDetails () throws EmployeeManagementException {
        List<Employee> employees = employeeDao.getAllEmployees(false);
        List<String> employeeDetails = new ArrayList<String>();
        List<Employee> employeess = new ArrayList<Employee>();
        String line = "----------------------------";
        if(null != employees) {
            for(Employee employee : employees) {
                List<Address> addresses = employee.getAddresses();
                if(!addresses.isEmpty()) {
                    for(Address address: addresses) {
                        if(true == address.getIsDeleted()) {
                            addresses.remove(address);
                        }
                    }
                }
                employee.setAddresses(addresses);
                employeess.add(employee);
            }
        }
        return employeess;
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */  
    @Override
    public List<String> getAllProjects(boolean isDeleted) throws EmployeeManagementException {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        return projectService.getAllProjects(isDeleted);
    }
 
    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */  
    @Override
    public boolean checkProjectId(int projectId) throws EmployeeManagementException {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        return projectService.checkProjectId(projectId, false);
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public List<String> getAllEmployees(boolean isDeleted) throws EmployeeManagementException {
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        List<Employee> employees = employeeDao.getAllEmployees(isDeleted);
        List<String> allEmployees = new ArrayList<String>();
        if(!employees.isEmpty()) {
            for(Employee employee : employees) {
            	 String employeeDetails = "";
            	 String projectDetails = "";
            	 String addressDetails = "";
                employeeDetails = employeeDetails + employee.toString();
                while(employeeDetails.contains(";")) {
                	String[] details = employeeDetails.split(";",2);
                	allEmployees.add(details[0]);
                	employeeDetails = details[1];
                }
                allEmployees.add(employeeDetails);
                if(!employee.getAddresses().isEmpty()) {
                    for(Address address : employee.getAddresses()) {
                        if(false == address.getIsDeleted()) {
                            addressDetails = addressDetails + address.toString() + "<br/>";
                        }
                    }
                } else {
                	addressDetails = addressDetails + "-";
                }
                
                if(!employee.getProjects().isEmpty()) {
                    for(Project project : employee.getProjects()) {
                        projectDetails += String.valueOf(project.getId()) + "-";
                     }     
                     projectDetails = projectDetails.substring(0, projectDetails.length() - 1);             
                } else {
                   projectDetails = projectDetails + "-";
                }
                allEmployees.add(addressDetails);
                allEmployees.add(projectDetails);
            }
        }
        return allEmployees;
       }     
      
  
    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */   
    @Override
    public boolean deleteEmployee(int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        employee.setIsDeleted(true);
        employee.setProjects(null);
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */ 
    @Override 
    public boolean checkEmployeeID(int employeeId, boolean isDeleted) throws EmployeeManagementException {
        return employeeDao.checkEmployeeId(employeeId, isDeleted);
    }
 
    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */  
    @Override
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return (Pattern.matches("[7-9][0-9]{9}", phoneNumber)) 
                ? Long.parseLong(phoneNumber) 
                : 0;
    }

    /**
  
     * {@inheritDoc}
    
     */    
    @Override
    public boolean checkPinCode(String pinCode) {
         return (Pattern.matches("[1-9][0-9]{5}", pinCode));
    }

    /**
  
     * {@inheritDoc}
    
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