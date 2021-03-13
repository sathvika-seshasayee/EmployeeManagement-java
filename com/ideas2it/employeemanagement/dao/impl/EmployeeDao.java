package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;
import com.ideas2it.sessionfactory.SingletonConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeDao {
    SingletonConnection singletonConnection = SingletonConnection.getInstance();
    Connection connection = singletonConnection.mysqlConnection();
    EmployeeModel employeeModelObj;
    EmployeeAddressModel employeeAddressObj;

    public int createEmployee(EmployeeModel employeeModelObj, ArrayList<EmployeeAddressModel> employeeAddressObj) throws ClassNotFoundException, SQLException {
        int employeeId = createEmployeeDetails(employeeModelObj);
        boolean createEmployeeStatus = createAddress(employeeId, employeeAddressObj);
        return (createEmployeeStatus ? 0 : employeeId);
    }

    public boolean createAddress(int employeeId, ArrayList<EmployeeAddressModel> employeeAddressObj) throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        boolean createStatus = true;
        ResultSet resultSet = statement.executeQuery("select max(address_id) from employee_address");
        resultSet.next();
        int addressId = resultSet.getInt("max(address_id)");
        addressId++;
        resultSet.close();
        PreparedStatement prepareStatement = null;
        String insertQuery = "insert into employee_address values(?, ?, ?, ?, ?, ?, ?, ?, NULL)";
       // PrepareStatement prepareStatement = connection.prepareStatement(insertQuery);
        for(EmployeeAddressModel i : employeeAddressObj) {
        prepareStatement = connection.prepareStatement(insertQuery);
        prepareStatement.setInt(1, addressId);
        prepareStatement.setInt(2, employeeId);
        prepareStatement.setString(3, i.getAddress());
        prepareStatement.setString(4, i.getCity());
        prepareStatement.setString(5, i.getState());
        prepareStatement.setString(6, i.getCountry());
        prepareStatement.setString(7, i.getPinCode());
        prepareStatement.setString(8, i.getAddressType());
        createStatus = prepareStatement.execute();
        addressId = addressId + 1;
        }
        prepareStatement.close();
        return createStatus;
    }

   public int createEmployeeDetails(EmployeeModel employeeModelObj) throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select max(employee_id) from employee_model");
        resultSet.next();
        int currentId = resultSet.getInt("max(employee_id)");
        String insertQuery = "insert into employee_model values(?, ?, ?, ?, ?, ?, NULL)";
        PreparedStatement prepareStatement = connection.prepareStatement(insertQuery);
        int employeeId = currentId + 1;
        prepareStatement.setInt(1, employeeId);
        prepareStatement.setString(2, employeeModelObj.getName());
        prepareStatement.setString(3, employeeModelObj.getDesignation());
        prepareStatement.setDouble(4, employeeModelObj.getSalary());
        prepareStatement.setDate(5, employeeModelObj.getDOB());
        prepareStatement.setLong(6, employeeModelObj.getPhoneNumber());
        prepareStatement.execute();
        prepareStatement.close();
        return employeeId;
    }

    public boolean setAddress(int employeeId, EmployeeAddressModel employeeAddressObj, int updateOption) throws ClassNotFoundException, SQLException {
        boolean setAddressStatus = true;
        String updateQuery = "select address_id from (select row_number() over " 
                             + "(order by address_id asc) as rownumber, employee_id "
                             + "from employee_address where employee_id = ?) "
                             + "as table where rownumber = ? " ; 
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery);
        prepareStatement.setInt(1, employeeId);
        prepareStatement.setInt(2, updateOption);
        ResultSet resultSet = prepareStatement.executeQuery();
        resultSet.next();
        int addressId = resultSet.getInt("address_id");
        updateQuery = "update employee_address set address = ?, city = ?, state = ?, country = ?, pincode = ?"
                             + " where address_id = ?";
        prepareStatement = connection.prepareStatement(updateQuery);
        prepareStatement.setString(1, employeeAddressObj.getAddress());
        prepareStatement.setString(2, employeeAddressObj.getCity());
        prepareStatement.setString(3, employeeAddressObj.getState());
        prepareStatement.setString(4, employeeAddressObj.getCountry());
        prepareStatement.setString(5, employeeAddressObj.getPinCode());
        prepareStatement.setInt(6, addressId);
        setAddressStatus = prepareStatement.execute();
        prepareStatement.close();
        return setAddressStatus;
    }

     public boolean deleteSingleAddress(int employeeId, int updateOption) throws ClassNotFoundException, SQLException {
        String getAddressQuery = "select address_id from (select row_number()"
                             	+ "over (order by address_id asc) as rownumber, employee_id "
                                + " FROM employee_address WHERE employee_id = ? "
                                + ") as table where rownumber = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(getAddressQuery);
        prepareStatement.setInt(1, employeeId);
        prepareStatement.setInt(2, updateOption);
        ResultSet resultSet = prepareStatement.executeQuery();
        resultSet.next();
        int addressId = resultSet.getInt("address_id");
        resultSet.close();
        String deleteQuery = "update employee_address set deleted_flag = \"deleted\" where address_id = ?";
        prepareStatement = connection.prepareStatement(deleteQuery);    
        prepareStatement.setInt(1, addressId);
        int deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteAddressStatus);
    }

    public boolean deleteAllAddress(int employeeId) throws ClassNotFoundException, SQLException {
        String deleteQuery = "update employee_address set deleted_flag = \"deleted\" where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(deleteQuery);    
        prepareStatement.setInt(1, employeeId);
        int deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteAddressStatus);  
    }  
 
    public EmployeeModel viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        String displayQuery = "select employee_name, employee_designation, employee_salary,"
                              + " employee_dob, employee_mobile_number from employee_model where employee_id = ?";
        PreparedStatement preparestatement = connection.prepareStatement(displayQuery);  
        preparestatement.setInt(1, employeeId);
        ResultSet resultSet = preparestatement.executeQuery();
        resultSet.next();
        String employeeName = resultSet.getString("employee_name");
        String employeeDesignation = resultSet.getString("employee_designation");
        double employeeSalary = resultSet.getDouble("employee_salary");
        Date employeeDob = resultSet.getDate("employee_dob");
        long employeeMobileNumber = resultSet.getLong("employee_mobile_number");
        resultSet.close();
        EmployeeModel employeeModelObj = new EmployeeModel(employeeName, employeeDesignation,
                                           employeeSalary, employeeDob, employeeMobileNumber);
        resultSet.close();
        preparestatement.close();
        return employeeModelObj;
    }

    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId) throws ClassNotFoundException, SQLException {
       // String displayQuery = "select count(address_id) from employee_address where employee_id = ?";
        
        ArrayList<EmployeeAddressModel> employeeAddressModelObjs = new ArrayList<EmployeeAddressModel>();
      //  PreparedStatement prepareStatement = connection.prepareStatement(displayQuery);
      //  prepareStatement.setInt(1, employeeId);
      //  ResultSet resultSet = prepareStatement.executeQuery();
      //  resultSet.next();
       // int size = resultSet.getInt("count(address_id)");
      //  System.out.println("inside dao  " + size);
      
        // int addressCount = 0;
        String displayQuery = "select permanant_or_temporary, address, city, state, country, pincode from employee_address"
                              + " where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(displayQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            String addressType =  resultSet.getString("permanant_or_temporary"); 
            String address =  resultSet.getString("address");        
            String city =  resultSet.getString("city");      
            String state =  resultSet.getString("state");      
            String country =  resultSet.getString("country");      
            String pinCode =  resultSet.getString("pincode");   
            EmployeeAddressModel employeeAddressModelObj = new EmployeeAddressModel(address, city, state, 
                                                              country, pinCode, addressType);     
            employeeAddressModelObjs.add(employeeAddressModelObj);
           // addressCount = addressCount + 1;
        }
            resultSet.close();
            prepareStatement.close();
            return employeeAddressModelObjs;
    }

   // public EmployeeAddressModel[] 

    public Map<EmployeeModel, ArrayList<EmployeeAddressModel>> viewAllEmployees() throws ClassNotFoundException, SQLException {
        EmployeeModel employeeModelObj;
        ArrayList<EmployeeAddressModel> employeeAddressModelObjs = new ArrayList<EmployeeAddressModel>();
		Statement statement = connection.createStatement();
        String employeeDisplayQuery = "select employee_id, employee_name, employee_designation, employee_salary,"
                                      + " employee_dob, employee_mobile_number from employee_model";
        Map<EmployeeModel, ArrayList<EmployeeAddressModel>> employeeDetails= new HashMap<EmployeeModel, ArrayList<EmployeeAddressModel>>();
        int employeeId;
        String employeeName;
        String employeeDesignation;
        Double employeeSalary;
        Date employeeDob;
        long employeeMobileNumber;
        ResultSet resultSet = statement.executeQuery(employeeDisplayQuery);
        int employeeCount = 0;
      //  ArrayList<String> employeeIds = ArrayList<String>();
        while(resultSet.next()) {
            employeeId = resultSet.getInt("employee_id");
            employeeName = resultSet.getString("employee_name");
            employeeDesignation = resultSet.getString("employee_designation");
            employeeSalary = resultSet.getDouble("employee_salary");
            employeeDob = resultSet.getDate("employee_dob");
            employeeMobileNumber = resultSet.getLong("employee_mobile_number");
            employeeModelObj = new EmployeeModel(employeeName, employeeDesignation,
                                                 employeeSalary, employeeDob, employeeMobileNumber);
           
          //  employeeIds.add(employeeId);
            // employeeAddressModelObjs = singleEmployeeAddress(employeeId);
     
        employeeDetails.put(employeeModelObj, singleEmployeeAddress(employeeId));
        }
        resultSet.close();
        return employeeDetails;
    }

    public ArrayList<Integer> getEmployeeIds () throws ClassNotFoundException, SQLException {
        String getIdQuery = "select employee_id from employee_model";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getIdQuery);
        ArrayList<Integer> employeeIds = new ArrayList<Integer>();
        int employeeId = 0;
        while(resultSet.next()) {
            employeeId = resultSet.getInt("employee_id");
            employeeIds.add(employeeId);
        }
        return employeeIds;
    }


    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        String deleteQuery = "update employee_model set deleted_flag = \"deleted\" where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(deleteQuery);    
        prepareStatement.setInt(1, employeeId);
        int deleteEmployeeStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteEmployeeStatus) ? deleteAllAddress(employeeId) : true;                             
    }

    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set employee_name = ?  where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery); 
        prepareStatement.setString(1, name);
        prepareStatement.setInt(2, employeeId);
        boolean setNameStatus = prepareStatement.execute();
        prepareStatement.close();
        return setNameStatus;
    }

    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set employee_designation = ? where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery); 
        prepareStatement.setString(1, designation);
        prepareStatement.setInt(2, employeeId);
        boolean setDesignationStatus = prepareStatement.execute();
        prepareStatement.close();
        return setDesignationStatus;
    }

    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set employee_dob = ? where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery); 
        prepareStatement.setDate(1, date);
        prepareStatement.setInt(2, employeeId);
        boolean setDobStatus = prepareStatement.execute();
        prepareStatement.close();
        return setDobStatus;
    }

     public boolean setEmployeeSalary(double salary, int employeeId) throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set employee_salary = ?  where employee_id = ? " ; 
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery);
        prepareStatement.setDouble(1, salary);
        prepareStatement.setInt(2, employeeId);
        boolean setSalaryStatus = prepareStatement.execute();
        prepareStatement.close();
        return setSalaryStatus;
    }
 
     public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set employee_mobile_number = ? where employee_id = ?" ;
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery); 
        prepareStatement.setLong(1, phoneNumber);
        prepareStatement.setInt(2, employeeId);
        boolean setPhoneNumberStatus = prepareStatement.execute();
        prepareStatement.close();
        return setPhoneNumberStatus;
    }

     public Boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, SQLException {
        String updateQuery = "select employee_id from employee_model where employee_id = ?" ;
        PreparedStatement prepareStatement = connection.prepareStatement(updateQuery); 
        prepareStatement.setInt(1, employeeId);
        boolean checkEmployeeIDStatus = prepareStatement.execute();
        prepareStatement.close();
        return checkEmployeeIDStatus;
    }
}