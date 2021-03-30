package com.ideas2it.employeemanagement.project.service;

import java.sql.Date;
import java.util.ArrayList;

import com.ideas2it.employeemanagement.project.model.ProjectModel;

/** 
 * contains business logics for Project database.
 * @author Sathvika seshasayee
 * @version 1.0
 * @since 2021-03-24
 */
public interface ProjectService {
    /**
     * Passes id to dao.
     * @param projectId id of the project
     * @return true if id is present in database, false otherwise
     */
    boolean checkProjectId(int projectId);

    /**
     * Validates and converts into date format.
     * @param date date entered by user.
     * @return validated date
     */
    Date validateDate(String date);

    /**
     * This method checks if id of employee is present in database
     * @param employeeId is id of employee
     * @retuen true if employee id if present in database
     */
    public boolean checkEmployeeId(int employeeId);

    /**
     * Passes id to dao for deletion.
     * @param projectId id of the project
     * @return true if id is present in database, false otherwise
     */
    boolean deleteProject(int projectId); 

    /**
     * Passes project object to dao.
     * @param name name of the project
     * @param startDate start date of project
     * @param client client organization name
     * @return id of the project
     */
    int createProject(String name, String details, Date startDate,
                      String client, Date targetDate, ArrayList<Integer> employees);

    /**
     * Converts one project details from dao into string.
     * @param projectId id of project
     * @return project details.
     */
    String getOneProject(int projectId);

    /**
     * Passes project id to dao for restoration.
     * @param projectId id of project
     * @return true if restored sucessfully, false otherwise.
     */
    boolean restoreProject(int projectId);

    /**
     * Converts all project details from dao into strings.
     * @return all project details
     */
    ArrayList<String> getAllProjects(String option); 

    /**
     * Updates all project details.
     * @return true if updated sucessfully
     */
    boolean updateProject(int projectId, String name,
                          String details, Date startDate, String client,
                          Date targetDate, ArrayList<Integer> employees);

}