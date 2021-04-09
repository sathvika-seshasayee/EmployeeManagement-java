package com.ideas2it.employeemanagement.employee.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Subqueries;

import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Address;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.sessionfactory.DataBaseConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 09 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeDaoImpl implements EmployeeDao {

    /**
  
     * {@inheritdoc}
    
     */  
    @Override
    public int createEmployee(Employee employee) {
        int employeeId = 0;
        Session session = DataBaseConnection.getSessionFactory().openSession();   
        Transaction transaction = null;  
        
        try {
            transaction = session.beginTransaction();
            employeeId = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return employeeId;
    }

    /**
  
     * {@inheritdoc}
    
     */  
    @Override
    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) {
        Session session = DataBaseConnection.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Employee> employees = new ArrayList<Employee>();

         try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted",false));
            criteria.add(Restrictions.in("id", employeeIds));
            employees = criteria.list();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
           session.close();
        }
        return employees;
    }
                
    /**
     * {@inheritdoc}  
     */    
    @Override 
    public List<Employee> getAllEmployees(boolean isDeleted) {
        Session session = DataBaseConnection.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Employee> employees = new ArrayList<Employee>();
        Address address = new Address();
        
        try {
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted",isDeleted));
            employees = criteria.list();
            for(Employee employee : employees) {
                for(Address addresses : employee.getAddresses()) {}
                for(Project project : employee.getProjects()) {}
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
           session.close();
        }
        return employees;
    }    

    /** 
     * {@inheritdoc}
     */   
    @Override 
    public boolean updateEmployee(Employee employee) {
        Session session = DataBaseConnection.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean updateStatus = false;
        
        try {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            updateStatus = true;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
           session.close();
        }
        return updateStatus;
    }

    /**  
     * {@inheritdoc}
     */   
    @Override
    public Employee getSingleEmployee(int employeeId) {
        Session session = DataBaseConnection.getSessionFactory().openSession();
        Transaction transaction = null;
        Employee employee = null;
        
        try {
           employee = (Employee) session.get(Employee.class, employeeId);
           for(Address address : employee.getAddresses()) {}
           for(Project project : employee.getProjects()) {}
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
           session.close();
        }
        return employee;
    }

    /**  
     * {@inheritdoc}
     */ 
    public List<Project> getProjects(int employeeId) {
        Project projectModelObj = null;
        List<Project> projects = new ArrayList<Project>();
            return projects;
    }     


    /**  
     * {@inheritdoc}
     */   
    @Override
    public Employee checkEmployeeID(int employeeId) {
         Session session = null;
         Transaction transaction = null;
         Employee employee = null;

         try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            employee = (Employee) session.get(Employee.class, employeeId);
            System.out.println(employee);
            transaction.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return employee;
    }
 
}