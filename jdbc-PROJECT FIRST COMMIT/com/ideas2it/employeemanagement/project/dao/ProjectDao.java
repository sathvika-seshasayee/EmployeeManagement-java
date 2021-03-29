package com.ideas2it.employeemanagement.project.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ideas2it.employeemanagement.project.model.ProjectModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeModel;

/**
 * interface for Project Dao
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-24
 */
public interface ProjectDao {
    /** 
     * Checks if id of project is present in database
     * @param projectId id of the project
     * @return true if id is present in database, false otherwise
     */     
    boolean checkProjectId(int projectId);

    /** 
     * Restores project if present in database
     * @param projectId id of the project
     * @return true if restored sucessfully, false otherwise
     */     
    boolean restoreProject(int projectId);


    /**
     * Passes project details for updation.
     * @param projectModelObj project details
     * @return true if updated sucessfully
     */
    boolean updateProject(ProjectModel projectModelObj);

    /** 
     * Deletes project from database.
     * @param projectId id of the project
     * @return true if project is delted from database, false otherwise
     */ 
    boolean deleteProject(int projectId);    

    /**
     * Creates project in database.
     * @param name name of the project
     * @param startDate start date of project
     * @param client client organization name
     * @return id of the project
     */
    int createProject(ProjectModel projectModelObj);
  
    /**
     * Sets the values of project in query.
     * @param preparedStatement statement object
     * @param projectModelObj details of project
     * @param insertIndex index at which project details 
     * should start being inserted
     * @return query with project details inserted
     */
    PreparedStatement setProject(PreparedStatement prepareStatement, 
                                        ProjectModel projectModelObj, 
                                        int insertIndex);

    /**
     * Gets one project details from database
     * @param projectId id of project
     * @return project details
     */
    ProjectModel getOneProject(int projectId);    

    /**
     * Gets all project details from database
     * @return list of all project details
     */
    ArrayList<ProjectModel> getAllProjects(String option);
 
    /**
     * Gets project details alone from result set
     * @param resultSet is result from database
     * @return project details alone from database
     */ 
    ProjectModel getProject(ResultSet resultSet);

    /**
     * Gets employee details alone from result set
     * @param resultSet is result from database
     * @return employee details alone from database
     */ 
    EmployeeModel getEmployee(ResultSet resultSet);
}