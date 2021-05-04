package com.ideas2it.employeemanagement.employee.model;

import org.springframework.stereotype.Component;

import com.ideas2it.employeemanagement.employee.model.Employee;

/**
 * Plain old Java object for Employee Address Database.
 * It contains specific details of employee address
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
@Component
public class Address {
    private int id;
    private String doorNoAndStreet;            
    private String city;
    private String state;
    private String country;
    private String pincode;
    private boolean isPermanantAddress;       
    private boolean isDeleted;
    private Employee employee;

    public Address() {};
  
    public Address (String doorNoAndStreet, String city, String state,
            String country, String pincode, boolean isPermanantAddress) {
    this.doorNoAndStreet = doorNoAndStreet;
    this.city = city;
    this.state = state;
    this.country = country;
    this.pincode = pincode;
    this.isPermanantAddress= isPermanantAddress;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    public int getId() {
        return this.id;
    }

    public String getDoorNoAndStreet() {
        return this.doorNoAndStreet;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPincode() {
        return this.pincode;
    }

    public boolean getIsPermanantAddress() {
        return this.isPermanantAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setdoorNoAndStreet(String doorNoAndStreet) {
        this.doorNoAndStreet = doorNoAndStreet;
    }
 
    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setIsPermanantAddress(boolean isPermanantAddress) {
        this.isPermanantAddress = isPermanantAddress;
    }

    public String toString() {
        String addressType = isPermanantAddress ? "permanant" : "temporary";
        return "Address type : " + addressType + "\n" + doorNoAndStreet + " , " 
                +  city + " , " + state + " , " + country  + " , " +  pincode
                + "\n";
    }
}