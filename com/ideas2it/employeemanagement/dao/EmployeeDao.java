package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;
/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeDao {
    EmployeeModel employeeModelObj;
    EmployeeAddressModel employeeAddressObj;
    Connection connection;
   
    public Connection connectDataBase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "Sath1996@");
        return connection;
    }

    public boolean createAddress(int employeeId, EmployeeAddressModel employeeAddressObj) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String insertQuery = "insert into employee_address values(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepstatement = connection.prepareStatement(insertQuery);
        prepstatement.setInt(1, employeeId);
        prepstatement.setString(2, employeeAddressObj.getAddress());
        prepstatement.setString(3, employeeAddressObj.getCity());
        prepstatement.setString(4, employeeAddressObj.getState());
        prepstatement.setString(5, employeeAddressObj.getCountry());
        prepstatement.setInt(6, employeeAddressObj.getPinCode());
        prepstatement.setString(7, employeeAddressObj.getAddressType());
        prepstatement.setInt(8, employeeAddressObj.getYesOrNo());
        boolean createStatus = prepstatement.execute();
        prepstatement.close();
        return createStatus;
    }

    public boolean setAddress(int employeeId, EmployeeAddressModel employeeAddressObj) throws ClassNotFoundException, SQLException {
        boolean setAddressStatus = true;
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        if (("permanant" == employeeAddressObj.getAddressType()) && (1 == employeeAddressObj.getYesOrNo())) {
            deleteAddress(employeeId, "permanant");
            deleteAddress(employeeId, "temporary");
            setAddressStatus = createAddress(employeeId, employeeAddressObj);
        } else if ("permanant" == employeeAddressObj.getAddressType()) {
            deleteAddress(employeeId, "permanant");
            setAddressStatus = createAddress(employeeId, employeeAddressObj);
        } else if ("temporary" == employeeAddressObj.getAddressType()) {
            deleteAddress(employeeId, "temporary");
            setAddressStatus = createAddress(employeeId, employeeAddressObj);
        }
        return (false == setAddressStatus);
    }

    public void deleteAddress(int employeeId, String permananatOrTemporary) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String deleteQuery = "delete from employee_address where employee_id = " + employeeId
                      + " where permanant_or_temporary = " + permananatOrTemporary;          
        ResultSet resultSet = statement.executeQuery(deleteQuery);  
    }  
 
    public int createEmployee(EmployeeModel employeeModelObj) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
// can you use other than resultset
        ResultSet resultSet = statement.executeQuery("select max(employee_id) from employee_model");
        resultSet.next();
        int currentId = resultSet.getInt("max(employee_id)");
    
        String insertQuery = "insert into employee_model values(?, ?, ?, ?, ?, ?)";
        PreparedStatement prepstatement = connection.prepareStatement(insertQuery);
        currentId ++;
        int employeeId = currentId;
        prepstatement.setInt(1, employeeId);
        prepstatement.setString(2, employeeModelObj.getName());
        prepstatement.setString(3, employeeModelObj.getDesignation());
        prepstatement.setLong(4, employeeModelObj.getSalary());
        prepstatement.setDate(5, employeeModelObj.getDOB());
        prepstatement.setLong(6, employeeModelObj.getPhoneNumber());
        prepstatement.execute();
        prepstatement.close();
 
        return employeeId;
    }

    public EmployeeModel viewSingleEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        String displayQuery = "select employee_name, employee_designation, employee_salary"
                              + " employee_dob, employee_mobile_number from employee_model"
                              + " where employee_id = " + employeeId;
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(displayQuery);
        resultSet.next();
// is it better using setname, setdesignation methods?
        String employeeName = resultSet.getString("employee_name");
        String employeeDesignation = resultSet.getString("employee_designation");
        long employeeSalary = resultSet.getLong("employee_salary");
        Date employeeDob = resultSet.getDate("employee_dob");
        long employeeMobileNumber = resultSet.getLong("employee_mobile_number");
        resultSet.close();
        EmployeeModel employeeModelObj = new EmployeeModel(employeeName, employeeDesignation,
                                           employeeSalary, employeeDob, employeeMobileNumber);
        return employeeModelObj;
    }

    public EmployeeModel[] viewAllEmployees() throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        EmployeeModel[] employeeModelObj;
        String displayQuery = "select employee_name, employee_designation, employee_salary"
                              + " employee_dob, employee_mobile_number from employee_model";
        String employeeName;
        String employeeDesignation;
        long employeeSalary;
        Date employeeDob;
        long employeeMobileNumber;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(employee_id) from employee_model");
        resultSet.next();
        int size = resultSet.getInt("count(employee_id)");
        employeeModelObj = new EmployeeModel[size];
        resultSet.close(); 
        resultSet = statement.executeQuery(displayQuery);
        int employeeCount = 0;
        while(resultSet.next()) {
            employeeName = resultSet.getString("employee_name");
            employeeDesignation = resultSet.getString("employee_designation");
            employeeSalary = resultSet.getLong("employee_salary");
            employeeDob = resultSet.getDate("employee_dob");
            employeeMobileNumber = resultSet.getLong("employee_mobile_number");
            employeeModelObj[employeeCount] = new EmployeeModel(employeeName, employeeDesignation,
                                              employeeSalary, employeeDob, employeeMobileNumber);
            employeeCount++;
        }
        return employeeModelObj;
    }

    public boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String deleteQuery = "delete from employee_model where employee_id = " + employeeId;
        ResultSet resultSet = statement.executeQuery(deleteQuery);
        return true;                               // chk
    }

    public boolean setEmployeeName(String name, int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set employee_name = " + name 
                             + " where employee_id = " + employeeId;
        int setNameStatus = statement.executeUpdate(updateQuery); 
        statement.close();
        return (1 == setNameStatus);
    }

    public boolean setEmployeeDesignation(String designation, int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set employee_designation = " 
                             + designation + " where employee_id = " + employeeId;
        int setDesignationStatus = statement.executeUpdate(updateQuery); 
        statement.close();
        return (1 == setDesignationStatus);
    }

    public boolean setEmployeeDOB(Date date, int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set employee_dob = "
                            + date + " where employee_id = " + employeeId;
        int setDobStatus = statement.executeUpdate(updateQuery); 
        statement.close();
        return (1 == setDobStatus);
    }

     public boolean setEmployeeSalary(long salary, int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set employee_salary = "
                            + salary + " where employee_id = " + employeeId;
        int setSalaryStatus = statement.executeUpdate(updateQuery); 
        statement.close();
        return (1 == setSalaryStatus);
    }
 
     public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set employee_mobile_number = "
                            + phoneNumber + " where employee_id = " + employeeId;
        int setPhoneNumberStatus = statement.executeUpdate(updateQuery); 
        statement.close();
        return (1 == setPhoneNumberStatus);
    }

     public Boolean checkEmployeeID(int employeeId) throws ClassNotFoundException, SQLException {
        connection = connectDataBase();
        Statement statement = connection.createStatement();
        String updateQuery = "select employee_id from employee_model where employee_id = " + employeeId;
        int checkEmployeeIDStatus = statement.executeUpdate(updateQuery); 
        statement.close();
        return (employeeId == checkEmployeeIDStatus);
    }
}

         
            



            
        




















