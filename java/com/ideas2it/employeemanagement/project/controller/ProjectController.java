package com.ideas2it.employeemanagement.project.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employeemanagement.project.service.ProjectService;
import com.ideas2it.employeemanagement.project.service.impl.ProjectServiceImpl;

/**
 * Links Jsp and service layer. 
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectController extends HttpServlet {
	ProjectService projectService = new ProjectServiceImpl();
	
	/**
	 * Gets request and response from servlet. Values are passed with url.
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		switch (request.getParameter("action")) {
			case "displayProjects":
				displayProjects(request, response);
				break;
			case "displayProject":                                    
				displayProject(request, response);
				break;
			case "displayEmployeesForAssign":
				displayEmployeesForAssign(request, response);
				break;
			case "deleteProject":
				deleteProject(request, response);
				break;
			case "restoreProject":
				restoreProject(request, response);
				break;
			case "displayProjectForUpdate" :
				displayProjectForUpdate(request, response);
				break;
			default :
				displayNotFound(request, response);
		}
	}

	/**
	 * Gets request and response from servlet. 
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		switch (request.getParameter("method_name")) {
			case "createProject":
				createProject(request, response);
				break;
			case "displayProject":
				displayProject(request, response);
				break;
			case "assignOrUnAssignEmployees":
				assignOrUnAssignEmployees(request, response);
				break;
			default :
				displayNotFound(request, response);
		}
	}
	
	/**
	 * Displays page not found
	 * @param request 
	 * @param response
	 * @throws ServletException 
	 * @throws IOException, ServletException
	 */
	private void displayNotFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String notFound = "Requested page not working at the moment. Please try after some time.";
		request.setAttribute("notFound", notFound);
		RequestDispatcher rd = request.getRequestDispatcher("pageNotFound.jsp");
		rd.forward(request, response);
	}

	/**
	 * Assigns and un-assigns employee. 
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void assignOrUnAssignEmployees(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		String[] employees = request.getParameterValues("employeeIds");
		List<Integer> employeeIds = new ArrayList<Integer>();
		String assignStatus = "";
		if(null != employees) {
		    for(String employee: employees) {
			    employeeIds.add(Integer.parseInt(employee));
		    }
		}
		if(projectService.assignEmployees(employeeIds, projectId)) {
			assignStatus = "Updated assigned employees sucessfully for project Id " + projectId;
		} else {
			assignStatus = "Assigned employees not updated";
		}
		request.setAttribute("assignOrUnAssignStatus", assignStatus);
		RequestDispatcher rd = request.getRequestDispatcher("Project.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Display employees for Assigns and un assigns employee. 
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	private void displayEmployeesForAssign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> employees = projectService.getAllEmployees(false);
		List<List<String>> employeeDetails = new ArrayList<List<String>>();
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		List<String> assignedEmployeeIds = projectService.getSetOfEmployeeIds(projectId);
		
		for(int i = 0; i < employees.size() ; i = i + 8) {
			List<String> details = new ArrayList<String>();
			for(int j = i; j < i + 8 ; j++) {
				details.add(employees.get(j));
			}
			employeeDetails.add(details);
		}
		
		request.setAttribute("projectId", projectId);
		request.setAttribute("assignedEmployeeIds", assignedEmployeeIds);
		request.setAttribute("employeeDetails", employeeDetails);
		RequestDispatcher rd = request.getRequestDispatcher("assignOrUnAssignEmployees.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Restores Project
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void restoreProject(HttpServletRequest request, HttpServletResponse response) {
		String restoreProjectStatus = "";
		String checkProjectIdStatus = "";
		if (projectService.checkProjectId(Integer.parseInt(request.getParameter("projectId")), true)) {
			if (projectService.restoreProject(Integer.parseInt(request.getParameter("projectId")))) {
				restoreProjectStatus = "Project ID " + request.getParameter("projectId")
						+ " restored sucess fully.";
			} else {
				restoreProjectStatus = "Project ID " + request.getParameter("projectId") + " not restored.";
			}
		} else {
			checkProjectIdStatus = "Project id does not exist in deleted Projects.";
		}
		request.setAttribute("restoreProjectStatus", restoreProjectStatus);
		request.setAttribute("checkProjectIdStatus", checkProjectIdStatus);
		RequestDispatcher rd = request.getRequestDispatcher("restoreProject.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Gets Deletes Project 
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void deleteProject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String deleteProjectStatus = "";
		if (projectService.checkProjectId(Integer.parseInt(request.getParameter("projectId")), false)) {
			if (projectService.deleteProject(Integer.parseInt(request.getParameter("projectId")))) {
				deleteProjectStatus = "Project ID " + request.getParameter("projectId") + " Deleted Sucessfully.";
			} else {
				deleteProjectStatus = "Project ID " + request.getParameter("projectId") + " not deleted.";
			}
		} else {
			deleteProjectStatus = "Project ID is not present. Please try again";
		}
		request.setAttribute("deleteProjectStatus", deleteProjectStatus);
		RequestDispatcher rd = request.getRequestDispatcher("displayAllProjects.jsp");
		rd.forward(request, response);
	}

	/**
	 * Gets Check If project id is in database 
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public boolean checkProjectId(int projectId, boolean isDeleted) {
		return projectService.checkProjectId(projectId, isDeleted);
	}

	/**
	 * Displays all projects
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void displayProjects(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> allProjects = Boolean.parseBoolean(request.getParameter("isDeleted")) 
				                  ? projectService.getAllProjects(true)
				                  : projectService.getAllProjects(false);
		
		if (!allProjects.isEmpty()) {
			List<List<String>> projects = new ArrayList<List<String>>();
			for(int i = 0; i < allProjects.size() ; i = i + 7) {
				List<String> details = new ArrayList<String>();
				for (int j = i ; j < i + 7; j++) {
					details.add(allProjects.get(j));
				}
				projects.add(details);
			}
			request.setAttribute("projects", projects);
		} else {
			String displayStatus = "No active projects present.";
			request.setAttribute("displayStatus", displayStatus);
		}
		RequestDispatcher rd = Boolean.parseBoolean(request.getParameter("isDeleted")) 
	              ? request.getRequestDispatcher("restoreProject.jsp") 
	              : request.getRequestDispatcher("displayAllProjects.jsp");
        rd.forward(request, response);	
	}

	/**
	 * Creates a project
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void createProject(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        String updateStatus = "";
		String name = request.getParameter("name");
		String details = request.getParameter("details");
		String client = request.getParameter("client");
		Date startDate = Date.valueOf(request.getParameter("startDate"));
		Date targetDate = Date.valueOf(request.getParameter("endDate"));
		List<Integer> employees = new ArrayList<Integer>();

		if (!("".equals(request.getParameter("projectId")))) {
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			if(projectService.updateProject(projectId, name, details, startDate, client, targetDate, employees)) {
				updateStatus = "Project Id " + projectId + " Updated sucessfully";
			} else {
				updateStatus = "Project Id " + projectId + " not Updated";
			}
		} else {
			int projectId = projectService.createProject(name, details, startDate, client, targetDate, employees);
			if(0 != projectId) {
				updateStatus = "Project Id " + projectId + " created sucessfully";
			} else {
				updateStatus = "Project not created ";
			}
		}
		request.setAttribute("updateStatus", updateStatus);
		RequestDispatcher rd = request.getRequestDispatcher("createProject.jsp");
		rd.forward(request, response);
	}
		
	/**
	 * Displays project
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void displayProject(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

			List<String> project = projectService.getOneProject(Integer.parseInt(request.getParameter("projectId")));
			List<String> projectDetails = new ArrayList<String>();
			List<List<String>> employeeDetails = new ArrayList<List<String>>();
			projectDetails = project.subList(0, 6);
			project = project.subList(6, project.size());

			for (String employees : project) {
				List<String> employee = new ArrayList<String>();
				while (employees.contains(";")) {
					String[] details = employees.split(";", 2);
					employee.add(details[0]);
					employees = details[1];
				}
				employee.add(employees);
				employeeDetails.add(employee);
			}
			request.setAttribute("projectDetails", projectDetails);
			request.setAttribute("employeeDetails", employeeDetails);
		
		if (Boolean.parseBoolean(request.getParameter("forUpdation"))) {
			RequestDispatcher rd = request.getRequestDispatcher("createProject.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("displayProject.jsp");
			rd.forward(request, response);
		}

	}
	
	/**
	 * Display project for updating
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void displayProjectForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> project = projectService.getOneProject(Integer.parseInt(request.getParameter("projectId")));
		project = project.subList(0, 6);
		request.setAttribute("project", project);
		RequestDispatcher rd = request.getRequestDispatcher("createProject.jsp");
		rd.forward(request, response);
	}
}