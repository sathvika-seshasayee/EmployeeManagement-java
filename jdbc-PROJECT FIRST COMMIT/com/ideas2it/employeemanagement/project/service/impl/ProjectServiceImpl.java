package com.ideas2it.employeemanagement.project.service.impl;

import java.sql.Date;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.Set;

import java.util.HashSet;

import com.ideas2it.employeemanagement.project.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagement.project.model.ProjectModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.project.service.ProjectService;
import com.ideas2it.employeemanagement.employee.service.impl.EmployeeServiceImpl;

/**
 * Contains logics behind displayed outputs.
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectServiceImpl implements ProjectService {
    EmployeeServiceImpl employeeServiceObj = new EmployeeServiceImpl();
    ProjectDaoImpl projectDao = new ProjectDaoImpl();

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkProjectId(int projectId) {
        return projectDao.checkProjectId(projectId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteProject(int projectId) {
        return projectDao.deleteProject(projectId);
    }


    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public Date validateDate(String date) {
        Date projectDate = null;
        boolean isDate = false;
        try {
            isDate = Pattern.matches("(?:1?[9]|(2)[0])[0-9][0-9][-](?:0?[1-9]|(1)[02])[-](?:[012]?[0-9]|(3)[01])", date);
            projectDate = isDate ? Date.valueOf(date) : null;
            } catch (IllegalArgumentException e) {}
        return projectDate;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean updateProject(int projectId, String name,
                                String details, Date startDate, String client, 
                                Date targetDate, ArrayList<Integer> employees) {
        ProjectModel projectModelObj = projectDao.getOneProject(projectId);
        ArrayList<EmployeeModel> employeeModelObjs = new ArrayList<EmployeeModel>();
        if("" != name) { 
            projectModelObj.setName(name);
        }

        if("" != details) { 
            projectModelObj.setDetails(details);
        }

        if(null != startDate) { 
            projectModelObj.setStartDate(startDate);
        }
        
        if(null != targetDate) { 
            projectModelObj.setStartDate(startDate);
        }

        if("" != client) { 
            projectModelObj.setDetails(client);
        }

        if(!employees.isEmpty()) {
            employeeModelObjs = checkDuplicateEmployees(projectModelObj.getEmployees(), employees);
            projectModelObj.setEmployees(employeeModelObjs);
        } else { 
		    ArrayList<Integer> employeeIds = new ArrayList<Integer> ();
		    for (EmployeeModel employeeModelObj : projectModelObj.getEmployees()) {
				employeeIds.add(employeeModelObj.getId());
			}
		    employeeModelObjs = checkDuplicateEmployees(projectModelObj.getEmployees(), employeeIds);
            projectModelObj.setEmployees(employeeModelObjs);
		}
        return projectDao.updateProject(projectModelObj);
        }  
		
    public ArrayList<String> getAllEmployees(String option) {
		return employeeServiceObj.getAllEmployees(option);
	}

    public ArrayList<EmployeeModel> checkDuplicateEmployees(ArrayList<EmployeeModel> employeeModelObjs,
                                                  ArrayList<Integer> employees) {
        ArrayList<Integer> employeeIds = new ArrayList<Integer>();
        for(EmployeeModel employeeModelObj : employeeModelObjs) {
            employeeIds.add(employeeModelObj.getId());
        }
        if(!employeeModelObjs.isEmpty()) {
            Set<Integer> IdSetOne = new HashSet<Integer>(employees);
            Set<Integer> IdSetTwo = new HashSet<Integer>(employeeIds);
            IdSetOne.removeAll(IdSetTwo);
            employees = new ArrayList<Integer>(IdSetOne);
        } 
        return employeeServiceObj.getSetOfEmployees(employees);
    }

   /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean restoreProject(int projectId) {
        return projectDao.restoreProject(projectId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkEmployeeId(int employeeId) {
        return employeeServiceObj.checkEmployeeID(employeeId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createProject(String name, String details, Date startDate,
                      String client, Date targetDate, ArrayList<Integer> employeeIds) {
        ArrayList<EmployeeModel> employees = employeeServiceObj.getSetOfEmployees(employeeIds);
        ProjectModel projectModelObj = new ProjectModel(name, details, startDate,
                                                        client, targetDate, employees);
        return projectDao.createProject(projectModelObj);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public String getOneProject(int projectId) {
        ProjectModel projectModelObj = projectDao.getOneProject(projectId);
        ArrayList<EmployeeModel> employeeModelObjects = projectModelObj.getEmployees();
        ArrayList<Integer> employeeIds = new ArrayList<Integer>();
        String employees = "";
        for(EmployeeModel employee : employeeModelObjects) {
            employeeIds.add(employee.getId());
        }
        ArrayList<EmployeeModel> employeeObjects = 
                employeeServiceObj.getSetOfEmployees(employeeIds);
        projectModelObj.setEmployees(employeeObjects);
        if (!employeeObjects.isEmpty()) {
            for (EmployeeModel employeeObj : employeeObjects) {
                employees = employees + employeeObj.toString();
            }
		}
        return (projectModelObj.toString() + employees);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<String> getAllProjects(String option) {
        String line = "------------------------\n";
        ArrayList<ProjectModel> projects = projectDao.getAllProjects(option);
        ArrayList<String> projectDetails = new ArrayList<String>();
        ArrayList<Integer> employeeIds = new ArrayList<Integer>();
        Map<Integer, EmployeeModel> employee = new HashMap<Integer, EmployeeModel>();
        ArrayList<EmployeeModel> allEmployees = employeeServiceObj.getAllEmployeesModel();
        ArrayList<String> projectString = 
                  new ArrayList<String>();
		
        for(EmployeeModel i : allEmployees) {
            employee.put(i.getId(), i);
        }
        for(int i = 0; i < projects.size(); i++) {
            ProjectModel project = projects.get(i);
            ArrayList<EmployeeModel> employeeModelObjects = 
                                     project.getEmployees();
            String employeeStrings = "" ;
            for(EmployeeModel singleEmployee : employeeModelObjects) {
				int employeeId = singleEmployee.getId();
				EmployeeModel employeeModel = (employee.get(employeeId));
                employeeStrings = employeeStrings + employeeModel.toString();
            }
            projectString.add(project.toString() + employeeStrings + line) ;       
        }      	
        return projectString;
    }
    


}
