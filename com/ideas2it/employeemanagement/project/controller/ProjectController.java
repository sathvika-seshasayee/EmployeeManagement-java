package com.ideas2it.employeemanagement.project.controller;

import java.sql.Date;
import java.util.ArrayList;

import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;

/**
 * Links view and service layers.
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectController {
    ProjectServiceImpl serviceObj = new ProjectServiceImpl();

    /**
     * Passes Id of project to service layer to check if id
     * is present in database.
     * @param employeeId id of employee
     * @return true if id is present in database.
     */
    public boolean checkProjectId(int projectId) {
        return serviceObj.checkProjectId(projectId);
    }

   /**
    * This method restores a project.
    * @param projectId id of project
    * @return true if restored
    */
    public boolean restoreProject(int projectId) {
        return serviceObj.restoreProject(projectId);
    }

   /**
    * This method deletes an project.
    * @param projectId id of project
    * @return true if deleted
    */
    public boolean deleteProject(int projectId) {
        return serviceObj.deleteProject(projectId);
    }

    /**
     * This method passes date to service layer for validation
     * @param date date 
     */
    public Date validateDate(String date) {
        return serviceObj.validateDate(date);
    }

    /**
     * This method passes project details to service layer for creating project.
     * @param name name of project
     * @param startDate start date of project
     * @param client name of the client organization
     * @param targetDate is estimated target date of project
     * @return project id. 
     */
    public int createProject(String name, String details, Date startDate, String client, Date targetDate) {
        return serviceObj.createProject(name, details, startDate, client, targetDate);
    }

    /**
     * This method passes project id to service layer
     * @param projectId id of project
     * @return one project details string
     */
    public String displayOneProject(int projectId) {
        return serviceObj.getOneProject(projectId);
    }

    /**
     * This method calls service layer for displaying project
     * details.
     * @return all projects details
     */
    public ArrayList<String> displayAllProjects(String option) {
        return serviceObj.getAllProjects(option);
    }
 
    /**
     * This method validates employee Ids
     * @param  employees employee ids entered by user.
     * @return list of employee Ids
     */
    public boolean checkEmployeeId(int employeeId) {
        return serviceObj.checkEmployeeId(employeeId);
    }

    /**
     * This method updates project details
     * @param projectId id of project
     * @param newName name of project
     * @param newDetails details of project
     * @param newStartDate start date of project
     * @param client client name
     * @param targetDate target date of project
     * @return true if updation was sucessfull            //check if it is true
     */
    public boolean updateProject(int projectId, String newName,
                                 String newDetails, Date newStartDate, String newClient, 
                                 Date newTargetDate,
                                 ArrayList<Integer> employees) {
        return serviceObj.updateProject(projectId, newName,
                                        newDetails, newStartDate, 
                                        newClient, newTargetDate, employees);
    }

}
 