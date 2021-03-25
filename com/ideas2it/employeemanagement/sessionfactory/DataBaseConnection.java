package com.ideas2it.employeemanagement.sessionfactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for connecting  to database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class DataBaseConnection {
    private static DataBaseConnection dataBaseConnection = null;

    /**
     * This method creates new connection with database 
     * if conncection was not made already.
     * @return dataBaseConnection new instance of 
     * database connection object.
     */
    public static DataBaseConnection getInstance() {
        if(dataBaseConnection == null) {
            dataBaseConnection = new DataBaseConnection();
        }
        return dataBaseConnection;
    }    

    /**
     * This method connects with mysql database.
     * @return connection mysql connection
     */
    public Connection mysqlConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employees", "root",
                     "Sath1996@");
        } catch (SQLException e) {
            System.out.println("Not able to connect to server");
        }
        return connection;
    }
}   