package com.ideas2it.employeemanagement.project.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.CustomLogger.EmployeeManagementLogger;
import com.ideas2it.employeemanagement.employee.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.project.controller.ProjectController;
import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.sessionfactory.DataBaseConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectDaoImpl implements ProjectDao {
	final EmployeeManagementLogger logger = new EmployeeManagementLogger(ProjectController.class);
    /**
     * {@inheritDoc} 
     */    
    @Override
    public int createProject(Project project) throws EmployeeManagementException {
        int projectId = 0;
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            projectId = (Integer) session.save(project);
            transaction.commit();
        } catch (Exception ex) {
            logger.logError(ex);
            throw new EmployeeManagementException("Creation failed");
        } finally {
        	closeSession(session);
        }
        return projectId;
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
     */ 
    @Override
    public List<Project> getMultipleProjects(List<Integer> projectIds) throws EmployeeManagementException {
        Session session = null;
        List<Project> projects = new ArrayList<Project>();

         try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Project where isDeleted = false and id in :ids").setParameter("ids", projectIds);
            projects = query.getResultList();
        } catch (Exception e) {
            logger.logError(e);
            throw new EmployeeManagementException("Projects fetching failed");
        } finally {
             session.close(); 
        }
        return projects;
    }   

    /** 
     * {@inheritDoc}   
     */    
    @Override
    public boolean updateProject(Project project) {
        Session session = null;
        Transaction transaction = null;
        boolean updateStatus = false;
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
            updateStatus = true;
        } catch (Exception e) {
        	logger.logError(e);
        } finally {
        	closeSession(session);
        }
        return updateStatus;
    }                  

    /**  
     * {@inheritDoc}   
     */    
    @Override
    public boolean checkProjectId(int projectId, boolean isDeleted) throws EmployeeManagementException {
        Session session = null;
        Transaction transaction = null;
        List projectid = null;
        String checkProject = "select id FROM Project  where id = :projectId and isDeleted = :isDeleted";

        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Query query = session.createQuery(checkProject);
            query.setParameter("projectId", projectId);
            query.setParameter("isDeleted", isDeleted);
            projectid = query.list();
        } catch(Exception e) {
            e.printStackTrace();
            logger.logError(e);
            throw new EmployeeManagementException("Id checking failed");
        } finally {
        	closeSession(session);
        }
        return (!projectid.isEmpty());
    }

    /**
     * {@inheritDoc}  
     */    
    @Override
    public Project getOneProject(int projectId) throws EmployeeManagementException {
        Session session = null;
        Project project = null;       

        try {
           session = DataBaseConnection.getSessionFactory().openSession();
           project = (Project) session.get(Project.class, projectId);
           for(Employee employee : project.getEmployees()) {}
        } catch(Exception e) {
            e.printStackTrace();
            logger.logError(e);
            throw new EmployeeManagementException("Project fetching failed");
        } finally {
        	closeSession(session);
        }
        return project;
    }

    /**  
     * {@inheritDoc}    
     */    
    @Override
    public List<Project> getAllProjects(boolean isDeleted) throws EmployeeManagementException {
        Session session = null;
        List<Project> projects = new ArrayList<Project>();
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            projects = session.createQuery("FROM Project where isDeleted = false").getResultList();
            for(Project project : projects) {
                for(Employee employee : project.getEmployees()) {}
            }
        } catch (Exception e) {
        	logger.logError(e);
        	e.printStackTrace();
            throw new EmployeeManagementException("Projects fetching failed");
        } finally {
        	closeSession(session);
        }
        return projects;
    }      
    
    
    //  Criteria criteria = session.createCriteria(Project.class);
    //    criteria.add(Restrictions.like("isDeleted", isDeleted));
    //    projects = criteria.list();
 
}