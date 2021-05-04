package com.ideas2it.employeemanagement.employee.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.lang.IllegalArgumentException;

import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.project.service.ProjectService;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * Contains business logics for create, display, update, delete, restore
 * employee.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeServiceImpl() {}

    private EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

//    public void setEmployeeDao(EmployeeDao employeeDao) {
//        this.employeeDao = employeeDao;
//    }
//    

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertEmployee(Employee employee) throws EmployeeManagementException {
        return employeeDao.createEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        List<Employee> employees = new ArrayList<Employee>();
        if (!employeeIds.isEmpty()) {
            employees = employeeDao.getSetOfEmployees(employeeIds);

        }
        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmployee(Employee employee) throws EmployeeManagementException {
        List<Project> projects = employeeDao.getEmployee(employee.getId()).getProjects();
        employee.setProjects(projects);
        employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreEmployee(int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        employee.setIsDeleted(false);
        if (!employeeDao.updateEmployee(employee)) {
            throw new EmployeeManagementException("Employee restoring failed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAssignedProjects(int employeeId, List<Integer> projectIds) throws EmployeeManagementException {
        ProjectService projectService = new ProjectServiceImpl();
        Employee employee = employeeDao.getEmployee(employeeId);
        if (!projectIds.isEmpty()) {                   
            List<Project> projects = projectService.getMultipleProjects(projectIds);
            employee.setProjects(projects);
        } else {
            employee.setProjects(null);
        }

        if (!employeeDao.updateEmployee(employee)) {
            throw new EmployeeManagementException("Updating assigned projects to an employee failed");
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getProjectsAssigned(int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        List<String> projectIds = new ArrayList<String>();
        if (!(employee.getProjects().isEmpty())) {
            for (Project project : employee.getProjects()) {
                projectIds.add(String.valueOf(project.getId()));
            }
        }
        return projectIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        List<Address> addresses = new ArrayList<Address>();
        for (Address address : employee.getAddresses()) {
            if (!address.getIsDeleted()) {
                addresses.add(address);
            }
        }
        employee.setAddresses(addresses);
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee setNewAddresses(Employee employee, String addAddress) throws EmployeeManagementException {
        List<Address> newAddresses = new ArrayList<Address>();

        if ("yes".equals(addAddress)) {
            Address address = new Address();
            newAddresses.add(employee.getAddresses().get(0));
            if ("" != employee.getAddresses().get(1).getCity()) {
                address = employee.getAddresses().get(1);
                employeeDao.deleteAddress(address.getId());
            }
            employee.setAddresses(newAddresses);
        }
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAllProjects(boolean isDeleted) throws EmployeeManagementException {
        ProjectService projectService = new ProjectServiceImpl();
        return projectService.getAllProjects(isDeleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkProjectId(int projectId) throws EmployeeManagementException {
        ProjectService projectService = new ProjectServiceImpl();
        return projectService.checkProjectId(projectId, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees(boolean isDeleted) throws EmployeeManagementException {
        List<Employee> employees = employeeDao.getAllEmployees(isDeleted);
        List<Employee> allEmployees = new ArrayList<Employee>();
        for (Employee employee : employees) {
            List<Address> addresses = new ArrayList<Address>();
            for (Address address : employee.getAddresses()) {
                if (false == address.getIsDeleted()) {
                    addresses.add(address);
                }
            }
            employee.setAddresses(addresses);
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void deleteEmployee(int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        employee.setIsDeleted(true);
        employee.setProjects(null);
        if (!employeeDao.updateEmployee(employee)) {
            throw new EmployeeManagementException("Employee deletion failed");
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public boolean checkEmployeeID(int employeeId, boolean isDeleted) throws EmployeeManagementException {
        return employeeDao.checkEmployeeId(employeeId, isDeleted);
    }

    /**
     * {@inheritDoc}
     */
  /*  @Override
    public Date checkEmployeeDOB(String dob) {
     /**   Date dateOfBirth = null;
        boolean isDate = false;
        try {
            isDate = Pattern.matches("[1][9][0-9][0-9][-](?:0?[1-9]|(1)[02])[-](?:[012]?[0-9]|(3)[01])", dob);
            dateOfBirth = isDate ? Date.valueOf(dob) : null;
        } catch (IllegalArgumentException e) {
        } 
        return null;
    } */

    /**
     * {@inheritDoc}
     */
    @Override
    public long checkEmployeePhoneNumber(String phoneNumber) {
        return (Pattern.matches("[7-9][0-9]{9}", phoneNumber)) ? Long.parseLong(phoneNumber) : 0;
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

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public boolean addAddresses(List<List<String>> addresses, int employeeId) throws EmployeeManagementException {
        Employee employee = employeeDao.getEmployee(employeeId);
        List<Address> employeeAddresses = new ArrayList<Address>();

        if (!employee.getAddresses().isEmpty()) {
            employeeAddresses = employee.getAddresses();
        }

        for (List<String> address : addresses) {
            employeeAddresses.add(getAddressObj(address));
            employee.setAddresses(employeeAddresses);
        }
        return employeeDao.updateEmployee(employee);
    }

    /**
     * {@inheritDoc}
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

        if (null != employee.getAddresses()) {
            for (Address address : employee.getAddresses()) {
                if (false == address.getIsDeleted()) {
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
     */
    @Override
    public List<Employee> getEmployeesDetails() throws EmployeeManagementException {
        List<Employee> employees = employeeDao.getAllEmployees(false);
        List<Employee> employeess = new ArrayList<Employee>();
        if (null != employees) {
            for (Employee employee : employees) {
                List<Address> addresses = employee.getAddresses();
                if (!addresses.isEmpty()) {
                    for (Address address : addresses) {
                        if (true == address.getIsDeleted()) {
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
     * Gets address string and converts to object
     * 
     * @param address details
     * @return address object
     */
    public Address getAddressObj(List<String> address) {
        boolean isPermanantaddress = ((address.get(5)).equals("permanant"));
        Address employeeAddressObj = new Address(address.get(0), address.get(1), address.get(2), address.get(3),
                address.get(4), isPermanantaddress);
        return employeeAddressObj;
    }
}