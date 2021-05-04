package com.ideas2it.employeemanagement.employee.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    final EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeDao.class);
  
    public EmployeeDaoImpl() {};

    /**
     * {@inheritDoc}
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
        } catch (HibernateException ex) {
            logger.logError(ex);
            throw new EmployeeManagementException("Creation failed");
        } finally {
            DataBaseConnection.closeSession(session);
        }
        return employeeId;
    }

    /**
     * {@inheritDoc}
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
        } catch (HibernateException e) {
            logger.logError(e);
        } finally {
            DataBaseConnection.closeSession(session);
        }
        return updateStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAddress(int addressId) throws EmployeeManagementException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(session.get(Address.class, addressId));
            transaction.commit();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Deleted address failed");
        } finally {
            DataBaseConnection.closeSession(session);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(int employeeId) throws EmployeeManagementException {
        Session session = null;
        Employee employee = null;

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeId);
            Hibernate.initialize(employee.getAddresses());
            Hibernate.initialize(employee.getProjects());
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employee fetching failed");
        } finally {
            DataBaseConnection.closeSession(session);
        }
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkEmployeeId(int employeeId, boolean isDeleted) throws EmployeeManagementException {
        Session session = null;
        List<Integer> employeeid = new ArrayList<Integer>();
        String checkEmployeeQuery = "select id FROM Employee  where id = :employeeId and isDeleted = :isDeleted";

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery(checkEmployeeQuery);
            query.setParameter("employeeId", employeeId);
            query.setParameter("isDeleted", isDeleted);
            employeeid = query.list();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employees fetching failed");
        } finally {
            DataBaseConnection.closeSession(session);
        }
        return (!employeeid.isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getSetOfEmployees(List<Integer> employeeIds) throws EmployeeManagementException {
        Session session = null;
        List<Employee> employees = new ArrayList<Employee>();

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Employee where isDeleted = false and id in :ids")
                    .setParameter("ids", employeeIds);
            employees = query.getResultList();
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employees fetching failed");
        } finally {
            DataBaseConnection.closeSession(session);
        }
        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees(boolean isDeleted) throws EmployeeManagementException {
        Session session = null;
        List<Employee> employees = new ArrayList<Employee>();
        String getAllEmployeesQuery = "FROM Employee where isDeleted = " + isDeleted;

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            employees = session.createQuery(getAllEmployeesQuery).getResultList();
            for (Employee employee : employees) {
                Hibernate.initialize(employee.getAddresses());
                Hibernate.initialize(employee.getProjects());
            }
        } catch (HibernateException e) {
            logger.logError(e);
            throw new EmployeeManagementException("Employees fetching failed");
        } finally {
            DataBaseConnection.closeSession(session);
        }
        return employees;
    }
}