// address pojo
package com.ideas2it.employeemanagement.model;

/**
 * Plain old Java object for Employee Address Database.
 * It contains specific details of employee address
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeAddressModel {
    private String address;            // contains apartment number and street name
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String addressType;
  
    public EmployeeAddressModel(String address, String city, String state,
            String country, String pinCode, String addressType) {
    this.address = address;
    this.city = city;
    this.state = state;
    this.country = country;
    this.pinCode = pinCode;
    this.addressType = addressType;
 //   this.yesOrNo = yesOrNo;
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

    public String getAddressType() {
        return this.addressType;
    }

 /*   public int getYesOrNo() {
        return ((yesOrNo) ? 1 :0) ;
    }        */

    public String toString() {
        String newLine = "\n";
        return "Address Type : " + addressType + newLine + address + newLine + city + newLine + state + newLine + country 
                + newLine + pinCode + newLine;
    }
}
