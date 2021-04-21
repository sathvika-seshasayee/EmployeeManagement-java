package com.ideas2it.employeemanagement.employee.controller;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;

/**
 * Links view and service layers.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeController extends HttpServlet {
	static EmployeeService employeeService = new EmployeeServiceImpl();

	/**
	 * Gets request and response from servlet. values are passed in url
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		switch (request.getParameter("action")) {
			case "displayEmployeeDetails":
				displayEmployeeDetails(request, response);
				break;
			case "displayEmployees":
				displayEmployees(request, response);
				break;
			case "deleteEmployee":
				deleteEmployee(request, response);
				break;
			case "restoreEmployee":
				restoreEmployee(request, response);
				break;
			case "displayForAssign":
				displayForAssign(request, response);
				break;
			case "displayEmployeeforUpdate":
				displayEmployeeforUpdate(request, response);
				break;
			default :
				displayNotFound(request, response);
		}
	}

	/**
	 * Gets request and response from servlet. values are hided from url.
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		switch (request.getParameter("method_name")) {
			case "createEmployee":
				createEmployee(request, response);
				break;
			case "updateAssignedProjects":
				updateAssignedProjects(request, response);
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
	 * Gets request and response from servlet. values are hided from url.
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	private void updateAssignedProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] projectId = request.getParameterValues("projectId");
		List<Integer> projectIds = new ArrayList<Integer>();
		String assignStatus = "";
		if(null != projectId) {
		    for(String project : projectId) {
			    projectIds.add(Integer.parseInt(project));
		    }
		}
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		
		if(employeeService.updateAssignedProjects(employeeId, projectIds)) {
			assignStatus = "Assigned projects updated for employee Id " + employeeId + " sucessfully";
		} else {
			assignStatus = "Assigned projects not updated";
		}
		request.setAttribute("assignStatus", assignStatus);
		RequestDispatcher rd = request.getRequestDispatcher("assignProject.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Gets request and response from servlet. values are hided from url.
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	private void displayForAssign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> allProjects = employeeService.getAllProjects(false);
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		List<String> assignedProjects = employeeService.getProjectsAssigned(employeeId);
		if (!allProjects.isEmpty()) {
			List<List<String>> projects = new ArrayList<List<String>>();
			for (int i = 0; i < allProjects.size() / 7; i++) {
				List<String> details = new ArrayList<String>();
				details.add(allProjects.get(0 + 7 * i));
				details.add(allProjects.get(1 + 7 * i));
				details.add(allProjects.get(2 + 7 * i));
				details.add(allProjects.get(3 + 7 * i));
				details.add(allProjects.get(4 + 7 * i));
				details.add(allProjects.get(5 + 7 * i));
				details.add(allProjects.get(6 + 7 * i));
				projects.add(details);
			}
			request.setAttribute("projects", projects);
		} else {
			String displayStatus = "No active projects present.";
			request.setAttribute("displayStatus", displayStatus);
		}
		request.setAttribute("employeeId", employeeId);
		request.setAttribute("assignedProjects", assignedProjects);
		RequestDispatcher rd = request.getRequestDispatcher("assignProject.jsp");
		rd.forward(request, response);
	}


	/**
	 * Restores an employee
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void restoreEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String restoreEmployeeStatus = "";
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		
			if (employeeService.restoreEmployee(employeeId)) {
				restoreEmployeeStatus = "Employee Id " + employeeId + " restored sucessfully.";
			} else {
				restoreEmployeeStatus = "Employee not restored.";
			}
		
		request.setAttribute("restoreEmployeeStatus", restoreEmployeeStatus);
		RequestDispatcher rd = request.getRequestDispatcher("restoreEmployee.jsp");
		rd.forward(request, response);
	}

	/**
	 * Deletes an employee. 
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String deletedEmployeeStatus = "";
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		boolean deleteStatus = employeeService.deleteEmployee(employeeId);

			if (deleteStatus) {
				deletedEmployeeStatus = "Employee Id " + employeeId + " deleted sucessfully";
			} else {
				deletedEmployeeStatus = "Employee Id " + employeeId + " not deleted";
			}
	
		request.setAttribute("deleted", deletedEmployeeStatus);
		RequestDispatcher rd = request.getRequestDispatcher("displayAllEmployees.jsp");
		rd.forward(request, response);
	}

	/**
	 * Displays active/non active employees
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	public static void displayEmployees(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> employees = Boolean.parseBoolean(request.getParameter("isDeleted")) 
				                 ? employeeService.getAllEmployees(true)
				                 : employeeService.getAllEmployees(false);
	
		List<List<String>> employeeDetails = new ArrayList<List<String>>();

		for(int i = 0; i < employees.size() ; i = i + 8) {
			List<String> details = new ArrayList<String>();
			for(int j = i; j < i + 8 ; j++) {
				details.add(employees.get(j));
			}
			employeeDetails.add(details);
		}
		
		request.setAttribute("employeeDetails", employeeDetails);
		RequestDispatcher rd = Boolean.parseBoolean(request.getParameter("isDeleted"))
				               ? request.getRequestDispatcher("restoreEmployee.jsp") 
				               : request.getRequestDispatcher("displayAllEmployees.jsp");
		rd.forward(request, response);
	}
		
	
	/**
	 * Displays one employee
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	private void displayEmployeeDetails (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		
			List<String> employee = employeeService.getEmployee(employeeId);
			request.setAttribute("employee", employee);
			RequestDispatcher rd = request.getRequestDispatcher("displayEmployee.jsp");
			rd.forward(request, response);
		
	}
	
	/**
	 * Displays employee details for updation
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	private void displayEmployeeforUpdate (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		
			List<String> employee = employeeService.getEmployee(employeeId);
			request.setAttribute("employee", employee);
			RequestDispatcher rd = request.getRequestDispatcher("createEmployee.jsp");
			rd.forward(request, response);
		
	}
	
	/**
	 * Creates or updates an employee
	 * @param request 
	 * @param response
	 * @throws IOException, ServletException
	 */
	private void createEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String updateStatus = "Employee not created or updated";
		String name = request.getParameter("name");
		String designation = request.getParameter("designation");
		Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
		double salary = Double.parseDouble(request.getParameter("salary"));
		long phoneNumber = Long.parseLong(request.getParameter("mobileNumber"));
		List<List<String>> addresses = getAddressDetails(request);
		
		int employeeId = 0;
		if (!("".equals(request.getParameter("employeeId")))) {
			employeeId = Integer.parseInt(request.getParameter("employeeId"));
			if (employeeService.updateEmployee(employeeId, name, designation, salary, dateOfBirth, phoneNumber,
					addresses)) {
				updateStatus = "Employee Id " + employeeId + " updated sucessfully";
			} 
		} else {
			employeeId = employeeService.createEmployee(employeeId, name, designation, salary, dateOfBirth, phoneNumber,
					addresses);
			updateStatus = "Employee Id " + employeeId + " created sucessfully";
		}

		request.setAttribute("updateStatus", updateStatus);
		RequestDispatcher rd = request.getRequestDispatcher("createEmployee.jsp");
	    rd.forward(request, response);

	}
	
	/**
	 * Gets address details from request
	 * @param request 
	 * @return one or two addresses
	 * @throws IOException, ServletException
	 */
	private List<List<String>> getAddressDetails(HttpServletRequest request) {
		List<List<String>> addresses = new ArrayList<List<String>>();
		List<String> address = new ArrayList<String>();
		List<String> address1 = new ArrayList<String>();
		address.add(request.getParameter("doorNoAndStreet"));
		address.add(request.getParameter("city"));
		address.add(request.getParameter("state"));
		address.add(request.getParameter("country"));
		address.add(request.getParameter("pincode"));
		address.add("permanant");
		addresses.add(address);
		if("" != request.getParameter("doorNoAndStreet1")) {
		    address1.add(request.getParameter("doorNoAndStreet1"));
		    address1.add(request.getParameter("city1"));
		    address1.add(request.getParameter("state1"));
		    address1.add(request.getParameter("country1"));
		    address1.add(request.getParameter("pincode1"));
		    address1.add("temporary");
		    addresses.add(address1);
		}
		return addresses;
	}
	
}
