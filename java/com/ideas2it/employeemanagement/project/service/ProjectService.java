package com.ideas2it.employeemanagement.project.service;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.employee.model.Employee;

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
     * @param isDeleted project deleted or active status
     * @return true if id is present in database, false otherwise
     */
    boolean checkProjectId(int projectId, boolean isDeleted);

    /**
     * This method passes employee and project details 
     * to un assigns employees from a project.
     * @param employeeIds ids of employees
     * @param projectId id of project
     * @return true if un assigned
     */
    boolean unAssignEmployees(List<Integer> employeeIds, int projectId);

    /**
     * Validates and converts into date format.
     * @param date date entered by user.
     * @return validated date
     */
    Date validateDate(String date);
    
    /*
     * assigns Employees
     * @param employeeIds id of employees
     * @param projectId id of project
     */
    boolean assignEmployees(List<Integer> employeeIds, int projectId);

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
                      String client, Date targetDate, List<Integer> employees);

    /**
     * Converts one project details from dao into string.
     * @param projectId id of project
     * @return project details.
     */
    List<String> getOneProject(int projectId);

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
    List<String> getAllProjects(boolean isDeleted); 
 
    /**
     * Gets all employee details.
     * @return all employee details
     */ 
    List<String> getAllEmployees(boolean isDeleted);

    /**
     * Gets only projects details
     * @return project details.
     */ 
    String getOnlyProjects(boolean isDeleted);

    /**
     * Gets set of projects.
     * @return set of projects details.
     */
    List<Project> getMultipleProjects(List<Integer> projectIds);

    /**
     * Gets set of employee details of a project
     * @return Employee details
     */
    String getSetOfEmployees(int projectId); 

    /**
     * Updates all project details.
     * @return true if updated sucessfully
     */
    boolean updateProject(int projectId, String name,
                          String details, Date startDate, String client,
                          Date targetDate, List<Integer> employees);

	/**
	 * Gets lists of Employee Ids assigned to a project
	 * @param projectId id of project
	 * @return List of string of employee Ids
	 */
	List<String> getSetOfEmployeeIds(int projectId);
}