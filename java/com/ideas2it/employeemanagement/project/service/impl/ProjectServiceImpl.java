package com.ideas2it.employeemanagement.project.service.impl;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Set;

import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;
import com.ideas2it.employeemanagement.project.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagement.project.model.Project;
import com.ideas2it.employeemanagement.project.service.ProjectService;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectServiceImpl implements ProjectService {
	EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
	ProjectDaoImpl projectDao = new ProjectDaoImpl();

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean checkProjectId(int projectId, boolean isDeleted) throws EmployeeManagementException {
		return projectDao.checkProjectId(projectId, isDeleted);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProject(int projectId) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);
		project.setIsDeleted(true);
		project.setEmployees(null);
		if(!projectDao.updateProject(project)) {	
			throw new EmployeeManagementException("Project Deletion failed");
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Date validateDate(String date) {
		Date projectDate = null;

		try {
			projectDate = Pattern
					.matches("(?:1?[9]|(2)[0])[0-9][0-9][-]" + "(?:0?[1-9]|(1)[02])[-](?:[012]?[0-9]|(3)[01])", date)
							? Date.valueOf(date)
							: null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return projectDate;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public void updateProject(int projectId, String name, String details, Date startDate, String client,
			Date targetDate, List<Integer> employeeIds) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);

		if ("" != name) {
			project.setName(name);
		}

		if ("" != details) {
			project.setDetails(details);
		}

		if (null != startDate) {
			project.setStartDate(startDate);
		}

		if (null != targetDate) {
			project.setStartDate(startDate);
		}

		if ("" != client) {
			project.setDetails(client);
		}
		
		projectDao.updateProject(project);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public void assignEmployees(List<Integer> employeeIds, int projectId) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);

		if (!employeeIds.isEmpty()) {
			project.setEmployees(employeeService.getSetOfEmployees(employeeIds));		
		} else {
			project.setEmployees(null);
		}
		
		if(!projectDao.updateProject(project)) {
			throw new EmployeeManagementException("Project Updation failed");
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public List<String> getAllEmployees(boolean isDeleted) throws EmployeeManagementException {
		return employeeService.getAllEmployees(isDeleted);
	}


	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public List<Project> getMultipleProjects(List<Integer> projectIds) throws EmployeeManagementException {
		return projectDao.getMultipleProjects(projectIds);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public void restoreProject(int projectId) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);
		project.setIsDeleted(false);
		if(!projectDao.updateProject(project)) {
			throw new EmployeeManagementException("Project restoring failed");
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public boolean checkEmployeeId(int employeeId) throws EmployeeManagementException {
		return employeeService.checkEmployeeID(employeeId, false);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public int createProject(String name, String details, Date startDate, String client, Date targetDate,
			List<Integer> employeeIds) throws EmployeeManagementException {
		List<Employee> employees = new ArrayList<Employee>();

		if (!employeeIds.isEmpty()) {
			employees = employeeService.getSetOfEmployees(employeeIds);
		}
		Project project = new Project(name, details, startDate, client, targetDate, employees);
		
		return projectDao.createProject(project);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public String getSetOfEmployees(int projectId) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);
		List<Employee> employees = project.getEmployees();
		String employeeDetails = "";

		for (Employee employee : employees) {
			employeeDetails = employeeDetails + employee.toString();
		}
		return employeeDetails;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public List<String> getSetOfEmployeeIds(int projectId) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);
		List<Employee> employees = project.getEmployees();
		List<String> employeeDetails = new ArrayList<String>();

		for (Employee employee : employees) {
			employeeDetails.add(String.valueOf(employee.getId()));
		}
		return employeeDetails;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	public Project getSingleProject(int projectId) throws EmployeeManagementException {
		return projectDao.getOneProject(projectId);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public List<String> getOneProject(int projectId) throws EmployeeManagementException {
		Project project = projectDao.getOneProject(projectId);
		String projectDetails = "";
		List<String> projectDetail = new ArrayList<String>();
		projectDetails += project.toString();
        
	/*	projectDetail.add(String.valueOf(project.getId()));
		projectDetail.add(project.getName());
		projectDetail.add(project.getClient());
		projectDetail.add(String.valueOf(project.getStartDate()));
		projectDetail.add(String.valueOf(project.getTargetDate())); */
		
		while (projectDetails.contains(";")) {
			String[] details = projectDetails.split(";", 2);
			projectDetail.add(details[0]);
			projectDetails = details[1];
		}
		projectDetail.add(projectDetails);

		if (!project.getEmployees().isEmpty()) {
			List<Integer> employeeIds = new ArrayList<Integer>();

			for (Employee employee : project.getEmployees()) {
				employeeIds.add(employee.getId());
			}
			List<Employee> employees = employeeService.getSetOfEmployees(employeeIds);
			for (Employee employee : employees) {
				projectDetail.add(employee.toString());
			}
		} else {
			projectDetail.add("-;-;-;-;-;-;");
		}

		return projectDetail;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public String getOnlyProjects(boolean isDeleted) throws EmployeeManagementException {
		List<Project> projects = projectDao.getAllProjects(isDeleted);
		String projectString = "";

		for (Project project : projects) {
			projectString = projectString + project.toString();
		}
		return projectString;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @throws EmployeeManagementException 
	 * 
	 */
	@Override
	public List<String> getAllProjects(boolean isDeleted) throws EmployeeManagementException {
		List<Project> projects = projectDao.getAllProjects(isDeleted);
		List<String> allProjects = new ArrayList<String>();

		for (Project project : projects) {
			List<Employee> employees = project.getEmployees();
			List<Integer> employeeIds = new ArrayList<Integer>();
			String employeeStrings = "";
			String projectDetails = "";
			projectDetails += project.toString();
			while (projectDetails.contains(";")) {
				String[] details = projectDetails.split(";", 2);
				allProjects.add(details[0]);
				projectDetails = details[1];
			}
			allProjects.add(projectDetails);
			if (!employees.isEmpty()) {

				for (Employee singleEmployee : employees) {
					employeeIds.add(singleEmployee.getId());
					employeeStrings += singleEmployee.getId() + "-";
				}
				employeeStrings = employeeStrings.substring(0, employeeStrings.length() - 1);
				employees = employeeService.getSetOfEmployees(employeeIds);

				for (Employee singleEmployee : employees) {
					// employeeStrings = employeeStrings + singleEmployee.toString();
				}
			} else {
				employeeStrings += "-";
			}
			allProjects.add(employeeStrings);
			System.out.println(allProjects);
		}
		return allProjects;
	}
}