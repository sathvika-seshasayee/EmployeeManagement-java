package com.ideas2it.employeemanagement.model;

import java.sql.Date;
import java.util.ArrayList;

import com.ideas2it.employeemanagement.model.EmployeeAddressModel;

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
    private double salary;
    private Date dob;           
    private long phoneNumber;
    private ArrayList<EmployeeAddressModel> address;
    
    public EmployeeModel (String name, String designation, double salary, 
                         Date date, long phoneNumber, ArrayList<EmployeeAddressModel> addresses) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.dob = date;
        this.address = addresses;
    }

    public void setName(String newName) {
         this.name = newName;
    }

    public void setDesignation(String newDesignation) {
         this.designation = newDesignation;
    }

     public void setSalary(double newSalary) {
         this.salary = newSalary;
    }
 
    public void setDOB(Date newDOB) {
         this.dob = newDOB;    
    }

    public void setPhoneNumber(long newPhoneNumber) {
         this.phoneNumber = newPhoneNumber;
    }

    public void setAddresses(ArrayList<EmployeeAddressModel> address) {
         this.address = address;
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

    public Date getDOB() {
        return this.dob;
    }

    public long getPhoneNumber() {
        return this.phoneNumber;
    }

    public ArrayList<EmployeeAddressModel> getAddresses() {
        return this.address;
    }

    public String toString() {
        String addresses = "";
        for (int i = 0; i < address.size(); i++) {
           addresses = addresses +  (address.get(i)).toString() + "\n";
        }
        return "Name : " + name + "\nDesignation :  " + designation + "\nSalary : " + "Rs " 
                + salary + "\nDate of Birth :  " + dob + "\nPhone number : " + phoneNumber + "\n\n"
                + addresses + "\n";
     }
}