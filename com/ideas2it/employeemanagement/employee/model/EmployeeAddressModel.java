package com.ideas2it.employeemanagement.employee.model;

/**
 * Plain old Java object for Employee Address Database.
 * It contains specific details of employee address
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeAddressModel {
    private int id;
    private String address;            // contains apartment number and street name
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private boolean isPermanantAddress;       // permanant or temporary address
  
    public EmployeeAddressModel(String address, String city, String state,
            String country, String pinCode, boolean isPermanantAddress) {
    this.address = address;
    this.city = city;
    this.state = state;
    this.country = country;
    this.pinCode = pinCode;
    this.isPermanantAddress= isPermanantAddress;
    }

    public int getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
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

    public String getPinCode() {
        return pinCode;
    }

    public boolean getAddressType() {
        return this.isPermanantAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setAddressType(boolean isPermanantAddress) {
        this.isPermanantAddress = isPermanantAddress;
    }

    public String toString() {
        String addressType = isPermanantAddress ? "permanant" : "temporary";
        return "Address type : " + addressType + "\n" + address + " , " 
                +  city + " , " + state + " , " + country  + " , " +  pinCode
                + "\n";
    }
}
