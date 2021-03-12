package com.ideas2it.employeemanagement.model;

import java.sql.Date;

/**
 * Plain old Java object for Employee Database. 
 * It contains specific details of employee
 * 
 * @version 3.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeModel {
    private String name;
    private String designation;
    private long salary;
    private Date dob;           
    private long phoneNumber;
    
    public EmployeeModel (String name, String designation, long salary, 
                         Date date, long phoneNumber) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.dob = date;
    }

    public void setName(String newName) {
         this.name = newName;
    }

     public void setDesignation(String newDesignation) {
         this.designation = newDesignation;
    }

     public void setSalary(long newSalary) {
         this.salary = newSalary;
    }
 
    public void setDOB(Date newDOB) {
         this.dob = newDOB;    
    }

    public void setPhoneNumber(long newPhoneNumber) {
         this.phoneNumber = newPhoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public long getSalary() {
        return this.salary;
    }

    public Date getDOB() {
        return this.dob;
    }

    public long getPhoneNumber() {
        return this.phoneNumber;
    }

     public String toString() {
          return "Name : " + name + "\nDesignation :  " + designation + "\nSalary : " + "Rs " 
                  + salary + "\nDate of Birth :  " + dob + "\nPhone number : " + phoneNumber;
     }
}