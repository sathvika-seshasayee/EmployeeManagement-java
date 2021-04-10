package com.ideas2it.employeemanagement.project.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.model.Employee;
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
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createProject(Project project) {
        int projectId = 0;
        Session session = null;
        Transaction transaction = null;
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            projectId = (Integer) session.save(project);
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
        return projectId;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public List<Project> getSetOfProjects(List<Integer> projectIds) {
        Session session = null;
        List<Project> projects = new ArrayList<Project>();

         try {
            session = DataBaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.like("isDeleted", false));
            criteria.add(Restrictions.in("id", projectIds));
            projects = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
        }
        return projects;
    }   

    /**
  
     * {@inheritdoc}
    
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
    public boolean checkProjectId(int projectId, boolean isDeleted) {
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
        } catch(HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if(null != session) {
                session.close();
            }
        }
        return (!projectid.isEmpty());
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public Project getOneProject(int projectId) {
        Session session = null;
        Project project = null;       

        try {
           session = DataBaseConnection.getSessionFactory().openSession();
           project = (Project) session.get(Project.class, projectId);
           for(Employee employee : project.getEmployees()) {}
        } catch(HibernateException e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
        }
        return project;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public List<Project> getAllProjects(boolean isDeleted) {
        Session session = null;
        List<Project> projects = new ArrayList<Project>();
        
        try {
            session = DataBaseConnection.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.like("isDeleted", isDeleted));
            projects = criteria.list();
            for(Project project : projects) {
                for(Employee employee : project.getEmployees()) {}
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(null != session) {
                session.close();
            }
        }
        return projects;
    }                 
}