package com.ideas2it.employeemanagement.employee.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
//import org.hibernate.SessionException;
import org.hibernate.Transaction;
//import org.hibernate.criterion.DetachedCriteria;
//import org.hibernate.criterion.Subqueries;

import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.CustomLogger.EmployeeManagementLogger;
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
	//private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class); 
	final EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeDao.class);
	
    /**
     * {@inheritDoc}  
     * 
     */  
    @Override
    public int createEmployee(Employee employee) throws EmployeeManagementException {
        int employeeId = 0;
        Session session = null;   
        Transaction transaction = null; 
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            employeeId = (Integer) session.save(employee);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.logError(ex);
            throw new EmployeeManagementException("Creation failed");
        } finally {
        	closeSession(session);
        }
        return employeeId;
    }
 
	/**
     * Closes session object
     * @param session
     * @throws EmployeeManagementException 
     */
    private void closeSession(Session session) {
    	try {
    		if(null != session) {
			    session.close();
        	}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}	
    }

    /**
     * {@inheritDoc} 
     * @throws EmployeeManagementException 
     */  
    @Override
    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        Session session = null;
        List<Employee> employees = new ArrayList<Employee>();

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted",false));
            criteria.add(Restrictions.in("id", employeeIds));
            employees = criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            logger.logError(e);
            throw new EmployeeManagementException("Employees fetching failed");
        } finally {
        	closeSession(session);
        }
        return employees;
    }
                
    /**
     * {@inheritDoc}  
     * @throws EmployeeManagementException 
     */    
    @Override 
    public List<Employee> getAllEmployees(boolean isDeleted) throws EmployeeManagementException {
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
        } catch (Exception e) {
        	logger.logError(e);
            e.printStackTrace();
            throw new EmployeeManagementException("Employees fetching failed");
        } finally {
        	closeSession(session);
        }
        return employees;
    }    

    /** 
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */   
    @Override 
    public boolean updateEmployee(Employee employee) throws EmployeeManagementException {
        Session session = null;
        Transaction transaction = null;
        boolean updateStatus = false;
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            updateStatus = true;
        } catch (Exception e) {
        	logger.logError(e);
            e.printStackTrace();
        } finally {
        	closeSession(session);
        }
        return updateStatus;
    }

    /**  
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */   
    @Override
    public Employee getEmployee(int employeeId) throws EmployeeManagementException {
        Session session = null;
        Employee employee = null;
        
        try {
           session = DataBaseConnection.getSessionFactory().openSession();
           employee = (Employee) session.get(Employee.class, employeeId);
           for(Address address : employee.getAddresses()) {}
           for(Project project : employee.getProjects()) {}
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmployeeManagementException("Employee fetching failed");
        } finally {
        	closeSession(session);
        }
        return employee;
    }    

    /**  
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */   
    @Override
    public boolean checkEmployeeId(int employeeId, boolean isDeleted) throws EmployeeManagementException {
        Session session = null;
        List<Integer> employeeid = new ArrayList<Integer>();
        String checkEmployee = "select id FROM Employee  where id = :employeeId and isDeleted = :isDeleted";

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery(checkEmployee);
            query.setParameter("employeeId", employeeId);
            query.setParameter("isDeleted", isDeleted);
            employeeid = query.list();
        } catch(Exception e) {
        	logger.logError(e);
            e.printStackTrace();
            throw new EmployeeManagementException("Employees fetching failed");
        } finally {
        	closeSession(session);
        }
        return (!employeeid.isEmpty());
    }
 
}