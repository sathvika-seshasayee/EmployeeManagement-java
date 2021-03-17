package com.ideas2it.employeemanagement.dao.impl;

import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.model.EmployeeModel;
import com.ideas2it.employeemanagement.model.EmployeeAddressModel;
import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.sessionfactory.SingletonConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeDaoImpl implements EmployeeDao {
    SingletonConnection singletonConnection = 
	        SingletonConnection.getInstance();
    Connection connection = singletonConnection.mysqlConnection();

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createEmployee(EmployeeModel employeeModelObj) throws 
	        ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        PreparedStatement prepareStatement = null;
        String insertQuery = "insert into employee_model(employee_name, "
		              + " designation, salary, dob, mobile_number) values(?, ?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(insertQuery);
        prepareStatement.setString(1, employeeModelObj.getName());
        prepareStatement.setString(2, employeeModelObj.getDesignation());
        prepareStatement.setDouble(3, employeeModelObj.getSalary());
        prepareStatement.setDate(4, employeeModelObj.getDOB());
        prepareStatement.setLong(5, employeeModelObj.getPhoneNumber());
        prepareStatement.execute();
		
        String getIdQuery = "select employee_id from employee_model ORDER "
		                    + "BY employee_id DESC LIMIT 1";
        ResultSet resultSet = statement.executeQuery(getIdQuery); 
        resultSet.next();
        int employeeId = resultSet.getInt(1);
        resultSet.close();
        ArrayList<EmployeeAddressModel> address = employeeModelObj.getAddresses();
        int size = address.size();
        String value = "";
		// number of data to be entered at run time
        for(int i = 0; i < (address.size() - 1) ; i++) {    
            value = value + ",(?, ?, ?, ?, ?, ?, ?)";
        }
        insertQuery = "insert into employee_address(employee_id, address," 
		       + "city, state, country, pincode ,permanant_or_temporary)"
      		       + "values (?, ?, ?, ?, ?, ?, ?) " + value ;
        prepareStatement = connection.prepareStatement(insertQuery);      
        // insertIndex specifies index at which values should be entered
        int insertIndex = 0;                 
        for(EmployeeAddressModel i :  address) {  
            prepareStatement.setInt(insertIndex*7 + 1, employeeId);
            prepareStatement.setString(insertIndex*7 + 2, i.getAddress());
            prepareStatement.setString(insertIndex*7 + 3, i.getCity());
            prepareStatement.setString(insertIndex*7 + 4, i.getState());
            prepareStatement.setString(insertIndex*7 + 5, i.getCountry());
            prepareStatement.setString(insertIndex*7 + 6, i.getPinCode());
            prepareStatement.setString(insertIndex*7 + 7, i.getAddressType());
            insertIndex = insertIndex + 1;
        }
        prepareStatement.execute();
        prepareStatement.close();
        return employeeId;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setAddress(int employeeId, 
	                      EmployeeAddressModel employeeAddressObj, 
	                      int updateOption) throws ClassNotFoundException,
   							        SQLException {
        boolean setAddressStatus = true;
        int addressId = getAddressId(employeeId, updateOption);
        String updateQuery = "update employee_address set address = ?, "
		              + "city = ?, state = ?, country = ?, pincode = ?"
                              + " where address_id = ?";
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(updateQuery);
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

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean addAddress(int employeeId, 
	                      EmployeeAddressModel employeeAddressObj) throws ClassNotFoundException,
   							        SQLException {
        boolean addAddressStatus = true;
        String addAddressQuery = "insert into employee_address(employee_id, address, city, state,"
                                 + " country, pincode, addressType) values(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement prepareStatement = 
		        connection.prepareStatement(addAddressQuery);
        prepareStatement.setInt(1, employeeId);
        prepareStatement.setString(2, employeeAddressObj.getAddress());
        prepareStatement.setString(3, employeeAddressObj.getCity());
        prepareStatement.setString(4, employeeAddressObj.getState());
        prepareStatement.setString(5, employeeAddressObj.getCountry());
        prepareStatement.setString(6, employeeAddressObj.getPinCode());
        prepareStatement.setString(7, employeeAddressObj.getAddressType());
        addAddressStatus = prepareStatement.execute();
        prepareStatement.close();
        return addAddressStatus;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public boolean restoreEmployee(int employeeId) throws ClassNotFoundException, SQLException  { 
        String restoreEmployeeQuery = "update employee_model set deleted_flag = "
		                    + " \"active\" where employee_id = ?";    
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(restoreEmployeeQuery);    
        prepareStatement.setInt(1, employeeId);
        int restoreEmployeeStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != restoreEmployeeStatus);                              
     }
 
    /**
  
     * {@inheritdoc}
    
     */    
    @Override       
    public int getAddressId(int employeeId, int updateOption) throws 
	        ClassNotFoundException, SQLException {
        String addressQuery = "select address_id from (select row_number() "
                              + " over (order by address_id asc) as rownumber, address_id" 
                              + " from employee_address where employee_id = ?) as " 
                              + " new_table where rownumber = ?"; 
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(addressQuery);
        prepareStatement.setInt(1, employeeId);
        prepareStatement.setInt(2, updateOption);
        ResultSet resultSet = prepareStatement.executeQuery();
        resultSet.next();
        int addressId = resultSet.getInt("address_id");
        return addressId;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteAllAddress(int employeeId) 
	        throws ClassNotFoundException, SQLException {
        String deleteQuery = "update employee_address set " 
		             + "deleted_flag = \"deleted\" where employee_id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(deleteQuery);    
        prepareStatement.setInt(1, employeeId);
        int deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteAddressStatus);  
    }  

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public ArrayList<EmployeeModel> viewAllEmployees() 
	        throws ClassNotFoundException, SQLException {
        String displayQuery = "SELECT * FROM employee_model INNER JOIN "
		                      + "employee_address ON "
				      + " employee_address.employee_id = "
                              + "employee_model.employee_id";
        Statement statement = connection.createStatement();
        ArrayList<EmployeeModel> employee = new ArrayList<EmployeeModel>();
        ResultSet resultSet = statement.executeQuery(displayQuery);
			   
        String employeeName = "";
        String employeeDesignation = "";
        Double employeeSalary = 1.0;
        Date employeeDob = null;
        long employeeMobileNumber = 1;
	int employeeId = 0;
        int employeeIdprev = 0;
	resultSet.next();	
	employeeId =  resultSet.getInt("employee_id");
	while(true) {
            employeeIdprev =  resultSet.getInt("employee_id");
            employeeName = resultSet.getString("employee_name");
            employeeDesignation = resultSet.getString("designation");
            employeeSalary = resultSet.getDouble("salary");
            employeeDob = resultSet.getDate("dob");
            employeeMobileNumber = resultSet.getLong("mobile_number");
            ArrayList<EmployeeAddressModel> address =
            new ArrayList<EmployeeAddressModel>();	
            EmployeeAddressModel employeeAddressModelObj = 
	        new EmployeeAddressModel(resultSet.getString("address"),
                 	                 resultSet.getString("city"), 
		                         resultSet.getString("state"),
					 resultSet.getString("country"), 
				         resultSet.getString("pincode"), 
                                         resultSet.getString("permanant_or_temporary"));
            address.add(employeeAddressModelObj);
            resultSet.next();
	    employeeId = resultSet.getInt("employee_id");
 
            while (employeeIdprev == resultSet.getInt("employee_id")) {
                employeeAddressModelObj = 
                        new EmployeeAddressModel(
                                resultSet.getString("address"),
		                resultSet.getString("city"), 
				resultSet.getString("state"),
				resultSet.getString("country"), 
                                resultSet.getString("pincode"), 
                                resultSet.getString("permanant_or_temporary"));
                address.add(employeeAddressModelObj);
		if (!resultSet.next()){
	            EmployeeModel employeeModelObj = 
	                    new EmployeeModel(employeeName, 
					      employeeDesignation,
					      employeeSalary, employeeDob, 
					      employeeMobileNumber, address);
		        employee.add(employeeModelObj);
			return employee;
		}
            }
            EmployeeModel employeeModelObj = new EmployeeModel(employeeName, 
		                                               employeeDesignation, 
							       employeeSalary, 
							       employeeDob, 
                                                               employeeMobileNumber,
							       address);
	    employee.add(employeeModelObj);
        } 
    }  

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public EmployeeModel viewSingleEmployee(int employeeId) throws 
	        ClassNotFoundException, SQLException {
        EmployeeModel employeeModelObj;
        EmployeeAddressModel employeeAddressModelObj;
        ArrayList<EmployeeAddressModel> address = 
		        new ArrayList<EmployeeAddressModel>();
        Statement statement = connection.createStatement();
        String employeeDisplayQuery = "SELECT *FROM employee_model INNER JOIN"
		        + " employee_address ON employee_address.employee_id = "
				+ " employee_model.employee_id where "
				+ " employee_model.employee_id = ?";
        String employeeName = "";
        String employeeDesignation = "";
        Double employeeSalary = 0.0;
        Date employeeDob = null;
        long employeeMobileNumber = 0;
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(employeeDisplayQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
     
        while(resultSet.next()) {
            employeeId = resultSet.getInt("employee_id");
            employeeName = resultSet.getString("employee_name");
            employeeDesignation = resultSet.getString("designation");
            employeeSalary = resultSet.getDouble("salary");
            employeeDob = resultSet.getDate("dob");
            employeeMobileNumber = resultSet.getLong("mobile_number");
            employeeAddressModelObj = 
			        new EmployeeAddressModel(resultSet.getString("address"), 
					                 resultSet.getString("city"), 
                                                         resultSet.getString("state"), 
							 resultSet.getString("country"),
							 resultSet.getString("pincode"), 
                                         resultSet.getString("permanant_or_temporary"));
            address.add(employeeAddressModelObj); 
        }
        employeeModelObj = new EmployeeModel(employeeName, employeeDesignation, 
                                             employeeSalary, employeeDob, 
					     employeeMobileNumber, address); 
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
        String displayQuery = "select permanant_or_temporary, address, city," 
		                      + "state, country, pincode from employee_address"
                              + " where employee_id = ?";
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(displayQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            String addressType = resultSet.getString("permanant_or_temporary"); 
            String address =  resultSet.getString("address");        
            String city =  resultSet.getString("city");      
            String state =  resultSet.getString("state");      
            String country =  resultSet.getString("country");      
            String pinCode =  resultSet.getString("pincode");   
            EmployeeAddressModel employeeAddressModelObj = 
			        new EmployeeAddressModel(address, city, state, country,
                           			         pinCode, addressType);     
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
    public ArrayList<Integer> getEmployeeIds() 
	        throws ClassNotFoundException, SQLException {
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

    /**
  
     * {@inheritdoc}
    
     */    
    @Override            
    public boolean deleteEmployee(int employeeId) 
	        throws ClassNotFoundException, SQLException {
        String deleteQuery = "update employee_model set deleted_flag = "
		                    + " \"deleted\" where employee_id = ?";
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(deleteQuery);    
        prepareStatement.setInt(1, employeeId);
        int deleteEmployeeStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteEmployeeStatus) ? deleteAllAddress(employeeId) 
		                                   : true;                             
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteSingleAddress(int employeeId, int updateOption) 
	        throws ClassNotFoundException, SQLException {
        int addressId = getAddressId(employeeId, updateOption);
        String deleteQuery = "update employee_address set deleted_flag ="
                        	+ "\"deleted\" where address_id = ?";
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(deleteQuery);    
        prepareStatement.setInt(1, addressId);
        int deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        return (0 != deleteAddressStatus);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setEmployeeName(String name, int employeeId) 
	        throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set employee_name = ? "
   		                     + " where employee_id = ?";
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(updateQuery); 
        prepareStatement.setString(1, name);
        prepareStatement.setInt(2, employeeId);
        boolean setNameStatus = prepareStatement.execute();
        prepareStatement.close();
        return setNameStatus;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setEmployeeDesignation(String designation, int employeeId) 
	        throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set designation = ? where "
                          	+ " employee_id = ?";
        PreparedStatement prepareStatement =  
		        connection.prepareStatement(updateQuery); 
        prepareStatement.setString(1, designation);
        prepareStatement.setInt(2, employeeId);
        boolean setDesignationStatus = prepareStatement.execute();
        prepareStatement.close();
        return setDesignationStatus;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setEmployeeDOB(Date date, int employeeId) 
	    throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        String updateQuery = "update employee_model set dob = ? where" 
                       		+ " employee_id = ?";
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(updateQuery); 
        prepareStatement.setDate(1, date);
        prepareStatement.setInt(2, employeeId);
        boolean setDobStatus = prepareStatement.execute();
        prepareStatement.close();
        return setDobStatus;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setEmployeeSalary(double salary, int employeeId) 
            throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set salary = ?  where "
                  		     + " employee_id = ? " ; 
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(updateQuery);
        prepareStatement.setDouble(1, salary);
        prepareStatement.setInt(2, employeeId);
        boolean setSalaryStatus = prepareStatement.execute();
        prepareStatement.close();
        return setSalaryStatus;
    }
 
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean setEmployeePhoneNumber(long phoneNumber, int employeeId)
 	    throws ClassNotFoundException, SQLException {
        String updateQuery = "update employee_model set mobile_number = ? "
		                     + " where employee_id = ?" ;
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(updateQuery); 
        prepareStatement.setLong(1, phoneNumber);
        prepareStatement.setInt(2, employeeId);
        boolean setPhoneNumberStatus = prepareStatement.execute();
        prepareStatement.close();
        return setPhoneNumberStatus;
    }
 
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkEmployeeID(int employeeId) 
	         throws ClassNotFoundException, SQLException {
        String updateQuery = "select employee_id from employee_model where "
                         	+ " employee_id = ?" ;
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(updateQuery); 
        prepareStatement.setInt(1, employeeId);
        boolean checkEmployeeIDStatus = prepareStatement.execute();
        prepareStatement.close();
        return checkEmployeeIDStatus;
    }
}