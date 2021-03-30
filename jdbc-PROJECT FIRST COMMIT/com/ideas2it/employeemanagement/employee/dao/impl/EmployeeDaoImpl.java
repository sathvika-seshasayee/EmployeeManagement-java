package com.ideas2it.employeemanagement.employee.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeAddressModel;
import com.ideas2it.employeemanagement.sessionfactory.DataBaseConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeDaoImpl implements EmployeeDao {
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
    public int createEmployee(EmployeeModel employeeModelObj) {
        int employeeId = 0;
        ArrayList<EmployeeAddressModel> address = 
                employeeModelObj.getAddresses();
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        String value = "";
        try {
            connection.setAutoCommit(false);
            employeeId = createOnlyEmployee(employeeModelObj);
            if ((null != address)) {
                
	        // number of values to be entered at run time
                for(int i = 0; i < (address.size() - 1) ; i++) {    
                    value = value + ",(?, ?, ?, ?, ?, ?, ?)";
                }
                String insertMultipleAddressQuery =  " insert into employee_address(employee_id, address, "
                                                       + " city, state, country, pincode, is_permanant_address) "
                                                       + " values(?, ?, ?, ?, ?, ?, ?) " + value ;
                PreparedStatement prepareStatement = 
                        connection.prepareStatement(insertMultipleAddressQuery, 
                                PreparedStatement.RETURN_GENERATED_KEYS);
                // insertIndex specifies index at which values should be entered
                int insertIndex = 1;                 
                for(EmployeeAddressModel i :  address) {  
                    prepareStatement.setInt(insertIndex++, employeeId);
                    prepareStatement = setAddress(prepareStatement, i, insertIndex);
                    insertIndex = insertIndex + 6;
                } 
                prepareStatement.execute();
                connection.commit();
                connection.close();
            }
        } catch (SQLException ex) {
            try {   
            employeeId = 0;
            connection.rollback();
            } catch (SQLException ex1) {}
        } 
        return employeeId;
    } 

    public PreparedStatement setEmployee(PreparedStatement prepareStatement, 
            EmployeeModel employeeModelObj, int insertIndex) throws SQLException {
        prepareStatement.setString(insertIndex++, employeeModelObj.getName());
        prepareStatement.setString(insertIndex++, employeeModelObj.getDesignation());
        prepareStatement.setDouble(insertIndex++, employeeModelObj.getSalary());
        prepareStatement.setDate(insertIndex++, employeeModelObj.getDOB());
        prepareStatement.setLong(insertIndex++, employeeModelObj.getPhoneNumber());
        return prepareStatement;
    }      

    public PreparedStatement setAddress(PreparedStatement prepareStatement, 
            EmployeeAddressModel employeeAddressObj, int insertIndex) throws SQLException {
        prepareStatement.setString(insertIndex++, employeeAddressObj.getAddress());
        prepareStatement.setString(insertIndex++, employeeAddressObj.getCity());
        prepareStatement.setString(insertIndex++, employeeAddressObj.getState());
        prepareStatement.setString(insertIndex++, employeeAddressObj.getCountry());
        prepareStatement.setString(insertIndex++, employeeAddressObj.getPinCode());
        prepareStatement.setBoolean(insertIndex++, employeeAddressObj.getAddressType()); 
        return prepareStatement;
    }        

    public int createOnlyEmployee(EmployeeModel employeeModelObj) {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        int employeeId = 0;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(createEmployeeQuery, 
                                                        PreparedStatement.RETURN_GENERATED_KEYS);
            prepareStatement = setEmployee(prepareStatement, employeeModelObj, 1);
	    int rowsAffected = prepareStatement.executeUpdate();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();

            if((1 == rowsAffected) && (resultSet.next())) {
                employeeId = resultSet.getInt(1);
	   }
           resultSet.close();
        } catch (SQLException ex) {}
        return employeeId;
        }


    public ArrayList<EmployeeModel> getSetOfEmployees(ArrayList<Integer> employeeIds) {
        String value = "";
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<EmployeeModel> employeeModelObjects = new ArrayList<EmployeeModel>();
        for(int i = 0; i < (employeeIds.size() - 1) ; i++) {    
            value = value + ",? ";
        }
        String getSetOfEmployeesQuery = " select * from employee_model where employee_id in (? " + value + " ) and is_deleted = false " ;
        try {
            PreparedStatement prepareStatement = 
		        connection.prepareStatement(getSetOfEmployeesQuery);
            for(int i = 0; i < (employeeIds.size()) ; i++) {
                prepareStatement.setInt(i + 1, employeeIds.get(i));
            }
            ResultSet resultSet = prepareStatement.executeQuery();
            while(resultSet.next()) {
                EmployeeModel employeeObj = getEmployee(resultSet);
                employeeObj.setId(resultSet.getInt(1));
                employeeModelObjects.add(employeeObj);
            }
        } catch (SQLException e) {
          System.out.println(e);
        }
        return employeeModelObjects;
    }
                

    /**
  
     * {@inheritdoc}

     */   
    @Override 
    public boolean addAddress(int employeeId, 
                              EmployeeAddressModel employeeAddressObj) {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        boolean addAddressStatus = true;
        try {
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(addAddressQuery);
        prepareStatement.setInt(1, employeeId);
        prepareStatement = setAddress(prepareStatement, employeeAddressObj, 2);
        addAddressStatus = prepareStatement.execute();
        prepareStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return addAddressStatus;
    }
 
    /**
  
     * {@inheritdoc}

     */     
    @Override 
    public boolean restoreEmployee(int employeeId) {
	DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        int restoreEmployeeStatus = 0; 
        try {
        connection.setAutoCommit(false);
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(restoreEmployeeQuery);    
        prepareStatement.setInt(1, employeeId);
        restoreEmployeeStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        if(1 == restoreEmployeeStatus) {
      
            prepareStatement = 
		        connection.prepareStatement(restoreAddressQuery);    
            prepareStatement.setInt(1, employeeId);
            prepareStatement.executeUpdate();
            prepareStatement.close();
            connection.commit();
        } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return (0 != restoreEmployeeStatus);                              
     }
 
    /**
  
     * {@inheritdoc}

     */   
    @Override
    public boolean deleteAllAddress(int employeeId) {
		DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        int deleteAddressStatus = 0;
        try {
        PreparedStatement prepareStatement = 
                connection.prepareStatement(deleteAllAddressQuery);    
        prepareStatement.setInt(1, employeeId);
        deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return (0 != deleteAddressStatus);  
    }  

    public EmployeeModel getEmployee(ResultSet resultSet) {
        EmployeeModel employeeModelObj = null;
        try {
            employeeModelObj = 
                    new EmployeeModel(resultSet.getString("employee_name"), 
				      resultSet.getString("designation"),
                                      resultSet.getDouble("salary"), 
                                      resultSet.getDate("dob"), 
                                      resultSet.getLong("mobile_number"), null);
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       return employeeModelObj;
    }

    public EmployeeAddressModel getAddress(ResultSet resultSet) {
        EmployeeAddressModel employeeAddressModelObj = null;
        try {
            employeeAddressModelObj = 
	                new EmployeeAddressModel(resultSet.getString("address"),
                 	                         resultSet.getString("city"), 
		                                 resultSet.getString("state"),
					         resultSet.getString("country"), 
                                                 resultSet.getString("pincode"), 
                                 resultSet.getBoolean("is_permanant_address"));
        } catch (SQLException ex) {
           ex.printStackTrace();
       }
        return employeeAddressModelObj;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override 
    public ArrayList<EmployeeModel> getAllEmployees(String option) {
		DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<EmployeeModel> employee = new ArrayList<EmployeeModel>();
        try {
            Statement statement =  connection.createStatement();
            String query = (option.equals("deleted")) ?  restoreQuery : displayAllQuery;
            int isDeleted = (option.equals("deleted")) ? 1 : 0;
            ResultSet resultSet = statement.executeQuery(query);	
            if (resultSet.next()) {	
               outer: do {
                    int employeeId = resultSet.getInt("employee_id");
            EmployeeModel employeeModelObj = getEmployee(resultSet);
            employeeModelObj.setId(employeeId);
            ArrayList<EmployeeAddressModel> address =  
                    new ArrayList<EmployeeAddressModel>();
            while(employeeId == resultSet.getInt("employee_id")) {
                if (isDeleted == resultSet.getInt(16)) {
                EmployeeAddressModel employeeAddressModelObj = getAddress(resultSet);
                address.add(employeeAddressModelObj);
                }
                if(!resultSet.next()) {
                   break outer;
               }
           } 
               employeeModelObj.setAddresses(address);
               employee.add(employeeModelObj);
          }while(true); 
          } else {
             employee = null;
          }
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
       return employee;
    }

    /**
  
     * {@inheritdoc}

     */   
    @Override 
    public boolean updateEmployee(EmployeeModel employeeModelObj) {
	DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        int updateStatus = 0;
        try {
            PreparedStatement prepareStatement = 
                connection.prepareStatement(updateEmployeeQuery); 
            prepareStatement = setEmployee(prepareStatement, employeeModelObj, 1);
            prepareStatement.setInt(6, employeeModelObj.getId());
            updateStatus = prepareStatement.executeUpdate();
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        return (0 != updateStatus);
        }
          
    /**
  
     * {@inheritdoc}

     */   
    @Override
    public EmployeeModel getSingleEmployee(int employeeId) {
	DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        EmployeeModel employeeModelObj = null;
        EmployeeAddressModel employeeAddressModelObj;
        ArrayList<EmployeeAddressModel> address = 
		        new ArrayList<EmployeeAddressModel>();
        try {
        PreparedStatement prepareStatement = 
                connection.prepareStatement(singleEmployeeDisplayQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        resultSet.next();
        employeeModelObj = getEmployee(resultSet);
        employeeId = resultSet.getInt("employee_id");
        employeeModelObj.setId(employeeId);
        do {
            employeeAddressModelObj = getAddress(resultSet);
            address.add(employeeAddressModelObj); 
        } while(resultSet.next()) ;
        employeeModelObj.setAddresses(address); 
        employeeModelObj.setId(employeeId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employeeModelObj;
       }

    /**
  
     * {@inheritdoc}

     */   
    @Override
    public ArrayList<EmployeeAddressModel> singleEmployeeAddress(int employeeId) {
		DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<EmployeeAddressModel> addresses  = 
		        new ArrayList<EmployeeAddressModel>();
        try {
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(singleEmployeeAddressesQuery);
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            EmployeeAddressModel employeeAddressModelObj = getAddress(resultSet); 
            employeeAddressModelObj.setId(resultSet.getInt("address_id"));   
            addresses.add(employeeAddressModelObj);
        }
        resultSet.close();
        prepareStatement.close();
     } catch (SQLException ex) {
            ex.printStackTrace();
     }
        return addresses;
    }


    /**
     * {@inheritdoc}   
     */    
    @Override            
    public boolean deleteEmployee(int employeeId) {
	DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        boolean deletEmployeeStatus = false;
        Connection connection = dataBaseConnection.mysqlConnection();
        try {
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
            deletEmployeeStatus = true;
        } else {
           connection.rollback();
        }
        prepareStatement.close();
       } catch (SQLException ex) {
            deletEmployeeStatus = false;
        }
        return deletEmployeeStatus;                             
    }
    
    /**
  
     * {@inheritdoc}

     */   
    @Override
    public boolean deleteSingleAddress(int addressId) {
		DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        int deleteAddressStatus = 0;
        try {
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(deleteSingleAddressQuery);    
        prepareStatement.setInt(1, addressId);
        deleteAddressStatus = prepareStatement.executeUpdate();
        prepareStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return (1 == deleteAddressStatus);
    }
 
    /**
  
     * {@inheritdoc}

     */   
    @Override
    public boolean checkEmployeeID(int employeeId) {
		DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        boolean checkIdStatus = false;
        try {
        PreparedStatement prepareStatement = 
		        connection.prepareStatement(checkEmployeeIdQuery); 
        prepareStatement.setInt(1, employeeId);
        ResultSet resultSet = prepareStatement.executeQuery();
        checkIdStatus = resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return checkIdStatus;
    }

 
}