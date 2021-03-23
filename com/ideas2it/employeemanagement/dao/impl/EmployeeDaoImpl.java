package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;
import com.ideas2it.employeemanagement.sessionfactory.DataBaseConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeDaoImpl implements EmployeeDao {
    DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
    Connection connection = dataBaseConnection.mysqlConnection();
    final static String deleteEmployeeQuery = "update employee_model "
                                              + " set is_deleted "
                                              + " = true  "
                                              + " where employee_id = ? ";
    final static String deleteAddressQuery = "update employee_address "
                                              + " set is_deleted "
                                              + " = true  "
                                              + " where employee_id = ? ";
    final static String restoreEmployeeQuery = "update employee_model "
                                               + " set is_deleted "
                                               + " = false  "
                                               + " where employee_id = ? ";
    final static String restoreAddressQuery = "update employee_address "
                                              + " set is_deleted "
                                              + " = false  "
                                              + " where employee_id = ? ";
    final static String singleEmployeeDisplayQuery = "SELECT *FROM employee_model left JOIN"
		                                      + " employee_address ON "
                                                      + "employee_address.employee_id = "
                                                      + " employee_model.employee_id where "
                                                      + " employee_model.employee_id = ? and "
                                                      + " employee_model.is_deleted "
                                                      + "= false";
    final static String createEmployeeQuery = " insert into employee_model(employee_name, designation, "
                                              + " salary, dob, mobile_number) values(?, ?, ?, ?, ?)"; 
    final static String addAddressQuery = "insert into employee_address(employee_id, "
                                          + " address, city, state, country, pincode "
                                          + " ,is_permanant_address)"
      		                          + "values (?, ?, ?, ?, ?, ?, ?) ";
    final static String displayAllQuery = "SELECT * FROM employee_model LEFT JOIN "
		                         + "employee_address ON "
				         + " employee_model.employee_id = "
                                         + " employee_address.employee_id where "
                                         + " employee_model.is_deleted "
                                         + " = false ";
    final static String restoreQuery = "SELECT * FROM employee_model LEFT JOIN "
		                       + "employee_address ON "
			               + " employee_address.employee_id = "
                                       + " employee_model.employee_id where "
                                       + "employee_model.is_deleted = true ";
    final static String deleteAllAddressQuery = "update employee_address set " 
                                                + "is_deleted = true where"
                                                + " employee_id = ?";
    final static String updateEmployeeQuery = "update employee_model set employee_name = ? ,"
                                              + " designation = ?, salary = ? ,"
                                              + " dob = ?, mobile_number = ? "
                                              + " where employee_id = ? ";
    final static String singleEmployeeAddressesQuery = "select address_id, is_permanant_address, "
                                                       + " address, city, state, country, pincode "
                                                       + " from employee_address"
                                                       + " where is_deleted = false and employee_id = ?";
    final static String deleteSingleAddressQuery = "update employee_address set is_deleted ="
                        	                    + "true where address_id = ?";
    final static String checkEmployeeIdQuery = "select employee_id from employee_model where "
                                               + " employee_id = ? and is_deleted "
                                               + "= false ";

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createEmployee(EmployeeModel employeeModelObj) throws 
	        ClassNotFoundException, SQLException {
        connection.setAutoCommit(false);
        int employeeId = 0;
        ArrayList<EmployeeAddressModel> address = employeeModelObj.getAddresses();
        employeeId = createOnlyEmployee(employeeModelObj);
        if ((null != address) && (employeeId != 0)) {
            String value = "";
            
	    // number of values to be entered at run time
            for(int i = 0; i < (address.size() - 1) ; i++) {    
                value = value + ",(?, ?, ?, ?, ?, ?, ?)";
            }
            String insertAddressQuery =  " insert into employee_address(employee_id, address, "
                    + " city, state, country, pincode, is_permanant_address) "
                    + " values(?, ?, ?, ?, ?, ?, ?) " + value ;
            int size = address.size();
            PreparedStatement prepareStatement = connection.prepareStatement(insertAddressQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                  // insertIndex specifies index at which values should be entered
            int insertIndex = 0;                 
            for(EmployeeAddressModel i :  address) {  
                prepareStatement.setInt(insertIndex*7 + 1, employeeId);
                prepareStatement.setString(insertIndex*7 + 2, i.getAddress());
                prepareStatement.setString(insertIndex*7 + 3, i.getCity());
                prepareStatement.setString(insertIndex*7 + 4, i.getState());
                prepareStatement.setString(insertIndex*7 + 5, i.getCountry());
                prepareStatement.setString(insertIndex*7 + 6, i.getPinCode());
                prepareStatement.setBoolean(insertIndex*7 + 7, i.getAddressType());
	        insertIndex = insertIndex + 1;
            } 
            prepareStatement.execute();
            connection.commit();
        } else {
            connection.rollback();
        }
        return employeeId;
    }

    public int createOnlyEmployee(EmployeeModel employeeModelObj) throws 
	        ClassNotFoundException, SQLException {
        PreparedStatement prepareStatement = null;
        int employeeId = 0;
        prepareStatement = connection.prepareStatement(createEmployeeQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, employeeModelObj.getName());
        prepareStatement.setString(2, employeeModelObj.getDesignation());
        prepareStatement.setDouble(3, employeeModelObj.getSalary());
        prepareStatement.setDate(4, employeeModelObj.getDOB());
        prepareStatement.setLong(5, employeeModelObj.getPhoneNumber());
	int rowsAffected = prepareStatement.executeUpdate();
        ResultSet resultSet = prepareStatement.getGeneratedKeys();

        if((1 == rowsAffected) && (resultSet.next())) {
            employeeId = resultSet.getInt(1);
	    }
        resultSet.close();
        return employeeId;
        }


    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean addAddress(int employeeId, 
                              EmployeeAddressModel employeeAddressObj) throws 
                              ClassNotFoundException, SQLException {
        boolean addAddressStatus = true;
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(addAddressQuery);
        prepareStatement.setString(2, employeeAddressObj.getAddress());
        prepareStatement.setString(3, employeeAddressObj.getCity());
        prepareStatement.setString(4, employeeAddressObj.getState());
        prepareStatement.setString(5, employeeAddressObj.getCountry());
        prepareStatement.setString(6, employeeAddressObj.getPinCode());
        prepareStatement.setBoolean(7, employeeAddressObj.getAddressType());
        prepareStatement.setInt(1, employeeId);
        addAddressStatus = prepareStatement.execute();
        prepareStatement.close();
        return addAddressStatus;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean restoreEmployee(int employeeId) throws 
            ClassNotFoundException, SQLException { 
        connection.setAutoCommit(false);
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(restoreEmployeeQuery);    
        prepareStatement.setInt(1, employeeId);
        int restoreEmployeeStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        System.out.println(restoreEmployeeStatus);
        if(1 == restoreEmployeeStatus) {
      
            prepareStatement = 
		        connection.prepareStatement(restoreAddressQuery);    
            prepareStatement.setInt(1, employeeId);
            prepareStatement.executeUpdate();
            prepareStatement.close();
            connection.commit();
        } 
        return (0 != restoreEmployeeStatus);                              
     }
 
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteAllAddress(int employeeId) 
            throws ClassNotFoundException, SQLException {
        PreparedStatement prepareStatement = 
                connection.prepareStatement(deleteAllAddressQuery);    
        prepareStatement.setInt(1, employeeId);
        int deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteAddressStatus);  
    }  

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public ArrayList<EmployeeModel> viewAllEmployees(String option) 
	        throws ClassNotFoundException, SQLException {
        
        ArrayList<EmployeeModel> employee = new ArrayList<EmployeeModel>();
        Statement statement =  connection.createStatement();
        String query = (option.equals("deleted")) ?  restoreQuery : displayAllQuery;
        ResultSet resultSet = statement.executeQuery(query);	
        boolean ans = resultSet.next();		   
        do {
            int employeeId = resultSet.getInt("employee_id");
            EmployeeModel employeeModelObj = 
                    new EmployeeModel(resultSet.getString("employee_name"), 
				      resultSet.getString("designation"),
                                      resultSet.getDouble("salary"), 
                                      resultSet.getDate("dob"), 
                                      resultSet.getLong("mobile_number"), null);
            employeeModelObj.setId(employeeId);
            ArrayList<EmployeeAddressModel> address =  
                    new ArrayList<EmployeeAddressModel>();
            while(employeeId == resultSet.getInt("employee_id")) {
                EmployeeAddressModel employeeAddressModelObj = 
	                new EmployeeAddressModel(resultSet.getString("address"),
                 	                         resultSet.getString("city"), 
		                                 resultSet.getString("state"),
					         resultSet.getString("country"), 
                                                 resultSet.getString("pincode"), 
                                 resultSet.getBoolean("is_permanant_address"));
               address.add(employeeAddressModelObj);
               if(!resultSet.next()) {
                   employeeModelObj.setAddresses(address);
                   employee.add(employeeModelObj);
                   return employee;
               }
           } 
           employeeModelObj.setAddresses(address);
           employee.add(employeeModelObj);
          }while(true);
    }


    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean updateEmployee(EmployeeModel employeeModelObj) throws 
                                      ClassNotFoundException, SQLException {
        PreparedStatement prepareStatement = 
                connection.prepareStatement(updateEmployeeQuery); 

        prepareStatement.setString(1, employeeModelObj.getName());
        prepareStatement.setString(2, employeeModelObj.getDesignation());
        prepareStatement.setDouble(3, employeeModelObj.getSalary());
        prepareStatement.setDate(4, employeeModelObj.getDOB());
        prepareStatement.setLong(5, employeeModelObj.getPhoneNumber());
        prepareStatement.setInt(6, employeeModelObj.getId());
        int updateStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        int employeeId = employeeModelObj.getId();
        ArrayList<EmployeeAddressModel> employeeAddressObjs = 
                new ArrayList<EmployeeAddressModel>();
        employeeAddressObjs = employeeModelObj.getAddresses();
        if(null != employeeAddressObjs) {
            for(EmployeeAddressModel employeeAddress : employeeAddressObjs) {
                EmployeeAddressModel employeeAddressObj = employeeAddress;
                addAddress(employeeId, employeeAddressObj);
             }
         }
            
        return (0 != updateStatus);
        }
          
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public EmployeeModel getSingleEmployee(int employeeId) throws 
            ClassNotFoundException, SQLException {
        EmployeeModel employeeModelObj;
        EmployeeAddressModel employeeAddressModelObj;
        ArrayList<EmployeeAddressModel> address = 
		        new ArrayList<EmployeeAddressModel>();
        Statement statement = connection.createStatement();
        PreparedStatement prepareStatement = 
                connection.prepareStatement(singleEmployeeDisplayQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        resultSet.next();
        employeeModelObj = new EmployeeModel(resultSet.getString("employee_name"), resultSet.getString("designation"), 
                                             resultSet.getDouble("salary"), resultSet.getDate("dob"), 
					     resultSet.getLong("mobile_number"), null); 
        employeeId = resultSet.getInt("employee_id");
        employeeModelObj.setId(employeeId);
        do {
            employeeAddressModelObj = 
                    new EmployeeAddressModel(resultSet.getString("address"), 
                                             resultSet.getString("city"), 
                                             resultSet.getString("state"), 
                                             resultSet.getString("country"),
			                     resultSet.getString("pincode"), 
                              resultSet.getBoolean("is_permanant_address"));
            address.add(employeeAddressModelObj); 
        } while(resultSet.next()) ;
        employeeModelObj.setAddresses(address); 
        employeeModelObj.setId(employeeId);
        return employeeModelObj;
       }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId)
        	throws ClassNotFoundException, SQLException {
        ArrayList<EmployeeAddressModel> addresses  = 
		        new ArrayList<EmployeeAddressModel>();
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(singleEmployeeAddressesQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            int addressId = resultSet.getInt("address_id");
            boolean addressType = resultSet.getBoolean("is_permanant_address"); 
            String address =  resultSet.getString("address");        
            String city =  resultSet.getString("city");      
            String state =  resultSet.getString("state");      
            String country =  resultSet.getString("country");      
            String pinCode =  resultSet.getString("pincode");   
            EmployeeAddressModel employeeAddressModelObj = 
                    new EmployeeAddressModel(address, city, state, country,
                           	             pinCode, addressType);  
            employeeAddressModelObj.setId(addressId);   
            addresses.add(employeeAddressModelObj);
        }
        resultSet.close();
        prepareStatement.close();
        return addresses;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override            
    public boolean deleteEmployee(int employeeId) 
	        throws ClassNotFoundException, SQLException {
        connection.setAutoCommit(false);
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(deleteEmployeeQuery);    
        prepareStatement.setInt(1, employeeId);
        int rowsAffected = prepareStatement.executeUpdate();
        if(1 == rowsAffected) {
            prepareStatement = 
		        connection.prepareStatement(deleteAddressQuery);    
            prepareStatement.setInt(1, employeeId);
            rowsAffected = prepareStatement.executeUpdate();
            connection.commit();
        } else {
           connection.rollback();
        }
        prepareStatement.close();
        return (false);                             
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteSingleAddress(int addressId) 
	        throws ClassNotFoundException, SQLException {
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(deleteSingleAddressQuery);    
        prepareStatement.setInt(1, addressId);
        int deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (1 == deleteAddressStatus);
    }
 
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkEmployeeID(int employeeId) 
	         throws ClassNotFoundException, SQLException {
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(checkEmployeeIdQuery); 
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        return (resultSet.next());
    }
}