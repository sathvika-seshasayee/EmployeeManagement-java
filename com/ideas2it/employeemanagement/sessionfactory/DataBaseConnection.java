package com.ideas2it.employeemanagement.sessionfactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 * Singleton class for connecting  to database
 * 
 * @version 2.0 09 Apr 2021
 * @author Sathvika Seshasayee
 */
public class DataBaseConnection {
    private static DataBaseConnection dataBaseConnection = null;
    private static SessionFactory sessionFactory = null;

    private DataBaseConnection() {}
 
    /**
     * Configures and gets session factory.
     * @return sessionFactory session factory object
     */
    public static SessionFactory getSessionFactory() {
        try {
            if (null == sessionFactory) {
                Configuration configuration = new Configuration();
                sessionFactory = configuration.configure("resources/hibernate/properties/hibernate.cfg.xml").buildSessionFactory();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sessionFactory;
    }
}   
