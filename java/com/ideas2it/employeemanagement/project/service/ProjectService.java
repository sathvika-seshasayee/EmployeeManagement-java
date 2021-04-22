package com.ideas2it.employeemanagement.project.service;

import java.sql.Date;
import java.util.List;

import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.CustomException.EmployeeManagementException;
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
     * @throws EmployeeManagementException 
     */
    boolean checkProjectId(int projectId, boolean isDeleted) throws EmployeeManagementException;

    /**
     * This method passes employee and project details 
     * to un assigns employees from a project.
     * @param employeeIds ids of employees
     * @param projectId id of project
     * @return true if un assigned
     * @throws EmployeeManagementException 
     */
    boolean unAssignEmployees(List<Integer> employeeIds, int projectId) throws EmployeeManagementException;

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
    boolean assignEmployees(List<Integer> employeeIds, int projectId) throws EmployeeManagementException;

    /**
     * This method checks if id of employee is present in database
     * @param employeeId is id of employee
     * @throws EmployeeManagementException 
     * @retuen true if employee id if present in database
     */
    public boolean checkEmployeeId(int employeeId) throws EmployeeManagementException;

    /**
     * Passes id to dao for deletion.
     * @param projectId id of the project
     * @return true if id is present in database, false otherwise
     * @throws EmployeeManagementException 
     */
    boolean deleteProject(int projectId) throws EmployeeManagementException; 

    /**
     * Passes project object to dao.
     * @param name name of the project
     * @param startDate start date of project
     * @param client client organization name
     * @return id of the project
     * @throws EmployeeManagementException 
     */
    int createProject(String name, String details, Date startDate,
                      String client, Date targetDate, List<Integer> employees) throws EmployeeManagementException;

    /**
     * Converts one project details from dao into string.
     * @param projectId id of project
     * @return project details.
     * @throws EmployeeManagementException 
     */
    List<String> getOneProject(int projectId) throws EmployeeManagementException;

    /**
     * Passes project id to dao for restoration.
     * @param projectId id of project
     * @return true if restored sucessfully, false otherwise.
     * @throws EmployeeManagementException 
     */
    boolean restoreProject(int projectId) throws EmployeeManagementException;

    /**
     * Converts all project details from dao into strings.
     * @return all project details
     * @throws EmployeeManagementException 
     */
    List<String> getAllProjects(boolean isDeleted) throws EmployeeManagementException; 
 
    /**
     * Gets all employee details.
     * @return all employee details
     * @throws EmployeeManagementException 
     */ 
    List<String> getAllEmployees(boolean isDeleted) throws EmployeeManagementException;

    /**
     * Gets only projects details
     * @return project details.
     * @throws EmployeeManagementException 
     */ 
    String getOnlyProjects(boolean isDeleted) throws EmployeeManagementException;

    /**
     * Gets set of projects.
     * @return set of projects details.
     * @throws EmployeeManagementException 
     */
    List<Project> getMultipleProjects(List<Integer> projectIds) throws EmployeeManagementException;

    /**
     * Gets set of employee details of a project
     * @return Employee details
     * @throws EmployeeManagementException 
     */
    String getSetOfEmployees(int projectId) throws EmployeeManagementException; 

    /**
     * Updates all project details.
     * @return true if updated sucessfully
     * @throws EmployeeManagementException 
     */
    boolean updateProject(int projectId, String name,
                          String details, Date startDate, String client,
                          Date targetDate, List<Integer> employees) throws EmployeeManagementException;

	/**
	 * Gets lists of Employee Ids assigned to a project
	 * @param projectId id of project
	 * @return List of string of employee Ids
	 * @throws EmployeeManagementException 
	 */
	List<String> getSetOfEmployeeIds(int projectId) throws EmployeeManagementException;
}