package com.ideas2it.employeemanagement.employee.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * Plain old Java object for Employee Database. 
 * It contains specific details of employee
 * 
 * @version 3.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */

public class Employee {
    private int id;
    private String name;
    private String designation;
    private double salary;
    private Date dob;           
    private long phoneNumber;
    private boolean isDeleted;
    private List<Address> addresses;
    private List<Project> projects;

    public Employee () {};
    
    public Employee (String name, String designation, double salary, 
                          Date date, long phoneNumber, 
                          List<Address> addresses) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.dob = date;
        this.addresses = addresses;
    }
    
    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    public Employee (int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
         this.name = name;
    }

    public void setDesignation(String designation) {
         this.designation = designation;
    }

     public void setSalary(double newSalary) {
         this.salary = newSalary;
    }
 
    public void setDob(Date dob){
         this.dob = dob;    
    }

    public void setPhoneNumber(long phoneNumber) {
         this.phoneNumber = phoneNumber;
    }

    public void setAddresses(List<Address> address) {
         this.addresses = address;
    }
  
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public int getId() {
        return this.id;
    }   

    public String getName() {
        return this.name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public double getSalary() {
        return this.salary;
    }

    public Date getDob() {
        return this.dob;
    }

    public long getPhoneNumber() {
        return this.phoneNumber;
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public String toString() {
        return    id  + ";" + name 
                + ";" + designation + ";" 
                + salary + ";" + dob 
                + ";" + phoneNumber;
     }
}