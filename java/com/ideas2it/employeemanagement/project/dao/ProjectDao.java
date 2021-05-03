package com.ideas2it.employeemanagement.project.dao;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.employeemanagement.employee.model.Employee;

/**
 * This interface creates, updates, deletes , gets 
 * project details.  
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-24
 */
public interface ProjectDao {
    /** 
     * Checks if id of project is present in database
     * @param projectId id of the project
     * @return true if id is present in database, false otherwise
     * @throws EmployeeManagementException 
     */     
    boolean checkProjectId(int projectId, boolean isDeleted) throws EmployeeManagementException;

    /**
     * Passes project details for updation.
     * @param projectModelObj project details
     * @return true if updated sucessfully
     * @throws EmployeeManagementException 
     */
    boolean updateProject(Project project) throws EmployeeManagementException;

    /** 
     * gets set of projects.
     * @param projectIds list of project ids
     * @return list of project details
     * @throws EmployeeManagementException 
     */ 
    List<Project> getMultipleProjects(List<Integer> projectIds) throws EmployeeManagementException;

    /**
     * Creates project in database.
     * @param name name of the project
     * @param startDate start date of project
     * @param client client organization name
     * @return id of the project
     * @throws EmployeeManagementException 
     */
    int createProject(Project project) throws EmployeeManagementException;

    /**
     * Gets one project details from database
     * @param projectId id of project
     * @return project details
     * @throws EmployeeManagementException 
     */
    Project getOneProject(int projectId) throws EmployeeManagementException;    

    /**
     * Gets all project details from database
     * @return list of all project details
     * @throws EmployeeManagementException 
     */
    List<Project> getAllProjects(boolean isDeleted) throws EmployeeManagementException;

}