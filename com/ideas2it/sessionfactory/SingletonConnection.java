package com.ideas2it.sessionfactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for connecting  to database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class SingletonConnection {
    private static SingletonConnection singleInstance = null;

    public static SingletonConnection getInstance() {
        if(singleInstance == null) {
            singleInstance = new SingletonConnection();
        }
        return singleInstance;
    }    

    public Connection mysqlConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employees", "root",
                     "Sath1996@");
            return connection;
        } catch (SQLException e) {}
        return null;
    }
}
    