package com.ideas2it.employeemanagement.employee.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
        Session session = null;   
        Transaction transaction = null;  
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            employeeId = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            if(null != session) {
                session.close();
            }
            if(null != transaction) {
                transaction.close();
            }
        }
        return employeeId;
    }

    /**
  
     * {@inheritdoc}
    
     */  
    @Override
    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) {
        Session session = null;
        List<Employee> employees = new ArrayList<Employee>();

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted",false));
            criteria.add(Restrictions.in("id", employeeIds));
            employees = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
        }
        return employees;
    }
                
    /**
     * {@inheritdoc}  
     */    
    @Override 
    public List<Employee> getAllEmployees(boolean isDeleted) {
        Session session = null;
        List<Employee> employees = new ArrayList<Employee>();
        Address address = new Address();
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
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
            if(null != session) {
                session.close();
            }
        }
        return employees;
    }    

    /** 
     * {@inheritdoc}
     */   
    @Override 
    public boolean updateEmployee(Employee employee) {
        Session session = null;
        Transaction transaction = null;
        boolean updateStatus = false;
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            updateStatus = true;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
            if(null != transaction) {
                transaction.close();
            }
        }
        return updateStatus;
    }

    /**  
     * {@inheritdoc}
     */   
    @Override
    public Employee getSingleEmployee(int employeeId) {
        Session session = null;
        Employee employee = null;
        
        try {
           session = DataBaseConnection.getSessionFactory().openSession();
           employee = (Employee) session.get(Employee.class, employeeId);
           for(Address address : employee.getAddresses()) {}
           for(Project project : employee.getProjects()) {}
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
            if(null != transaction) {
                transaction.close();
            }
        }
        return employee;
    }    

    /**  
     * {@inheritdoc}
     */   
    @Override
    public boolean checkEmployeeId(int employeeId, boolean isDeleted) {
        Session session = null;
        List<Integer> employeeid = new ArrayList<Integer>();
        String checkEmployee = "select id FROM Employee  where id = :employeeId and isDeleted = :isDeleted";

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery(checkEmployee);
            query.setParameter("employeeId", employeeId);
            query.setParameter("isDeleted", isDeleted);
            employeeid = query.list();
        } catch(HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if(null != session) {
                session.close();
            }
            if(null != transaction) {
                transaction.close();
            }
        }
        return (!employeeid.isEmpty());
    }
 
}