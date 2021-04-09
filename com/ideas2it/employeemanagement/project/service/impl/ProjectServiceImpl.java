package com.ideas2it.employeemanagement.project.service.impl;

import java.sql.Date;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Set;

import java.util.HashSet;

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
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkProjectId(int projectId, boolean isDeleted) {
        Project project = new Project(projectId);
        boolean projectIsPresent = false;

        if(null != project) {
            projectIsPresent = isDeleted 
                               ? (true == project.getIsDeleted())
                               : (false == project.getIsDeleted());
        }
        return projectIsPresent;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteProject(int projectId) {
        Project project = new Project(projectId);
        project.setIsDeleted(true);
        return projectDao.updateProject(project);
    }


    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public Date validateDate(String date) {
        Date projectDate = null;
        try {  
            projectDate = Pattern.matches("(?:1?[9]|(2)[0])[0-9][0-9][-]"
                    + "(?:0?[1-9]|(1)[02])[-](?:[012]?[0-9]|(3)[01])", date) 
                    ? Date.valueOf(date) : null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return projectDate;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean updateProject(int projectId, String name,
                                String details, Date startDate, String client, 
                                Date targetDate, List<Integer> employeeIds) {
        Project project = projectDao.getOneProject(projectId);
        List<Employee> employees = new ArrayList<Employee>();
        List<Employee> newEmployees = new ArrayList<Employee>();

        if("" != name) { 
            project.setName(name);
        }

        if("" != details) { 
            project.setDetails(details);
        }

        if(null != startDate) { 
            project.setStartDate(startDate);
        }
        
        if(null != targetDate) { 
            project.setStartDate(startDate);
        }

        if("" != client) { 
            project.setDetails(client);
        }

        if(!employeeIds.isEmpty() && (project.getEmployees()).isEmpty()) {
	    project.setEmployees(employeeService.getSetOfEmployees(employeeIds));
	} else if(!employeeIds.isEmpty()) {
             employees = checkDuplicateEmployees(project.getEmployees(), employeeIds);
             employees.addAll(employeeService.getSetOfEmployees(employeeIds));
             project.setEmployees(employees);
        } 
        return projectDao.updateProject(project);
        }  

    public boolean unAssignEmployees(List<Integer> employeeIds, int projectId) {
        Project project = projectDao.getOneProject(projectId);
        Set<Employee> employeeSet = new HashSet<Employee>();
        if(null != project.getEmployees()) {
            for(Employee employee : project.getEmployees()) {
                for(Integer employeeId : employeeIds) {
                    if(employeeId == employee.getId()) {
                        employeeSet.add(employee);
                    }
                }
            }
         }
         List<Employee> employees = new ArrayList<Employee>(employeeSet);
         List<Employee> assignedEmployees = project.getEmployees();
         assignedEmployees.removeAll(employees);
         project.setEmployees(assignedEmployees);
         return projectDao.updateProject(project);
    }
	
    /**
  
     * {@inheritdoc}
    
     */    
    @Override	
    public String getAllEmployees(boolean isDeleted) {
	return employeeService.getAllEmployees(isDeleted);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public List<Employee> checkDuplicateEmployees(List<Employee> employees,
                                                  List<Integer> newAssignees) {
        List<Integer> employeeIds = new ArrayList<Integer>();

	    if(!employees.isEmpty()) {

                for(Employee employee : employees) {
                    employeeIds.add(employee.getId());
                }
            Set<Integer> idSetOne = new HashSet<Integer>(newAssignees);
            Set<Integer> idSetTwo = new HashSet<Integer>(employeeIds);
            idSetOne.removeAll(idSetTwo);
            employeeIds = new ArrayList<Integer>(idSetOne);
        } 
        return employeeService.getSetOfEmployees(employeeIds);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public List<Project> getSetOfProjects(List<Integer> projectIds) {
        return projectDao.getSetOfProjects(projectIds);
    }
        

   /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean restoreProject(int projectId) {
        Project project = new Project(projectId);
        project.setIsDeleted(false);
        return projectDao.updateProject(project);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkEmployeeId(int employeeId) {
        return employeeService.checkEmployeeID(employeeId, false);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createProject(String name, String details, Date startDate,
                      String client, Date targetDate, List<Integer> employeeIds) {
        List<Employee> employees = new ArrayList<Employee>();

        if(!employeeIds.isEmpty()) {
            employees = employeeService.getSetOfEmployees(employeeIds);
        }
        Project project = new Project(name, details, startDate,
                                                        client, targetDate, employees);
        return projectDao.createProject(project);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public String getSetOfEmployees(int projectId) {
        Project project = projectDao.getOneProject(projectId);
        List<Employee> employees = project.getEmployees();
        String employeeDetails = "";

        for (Employee employee : employees) {
                employeeDetails = employeeDetails + employee.toString();
        }
        return employeeDetails;
    }

    public Project getSingleProject(int projectId) {
        return projectDao.getOneProject(projectId);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public String getOneProject(int projectId) {
        Project project = projectDao.getOneProject(projectId);
        String projectDetails = "";
        projectDetails += project.toString();

	if(!project.getEmployees().isEmpty()) {
            projectDetails += "\n Employees assigned   :  ";

            for(Employee employee : project.getEmployees()) {
                projectDetails += String.valueOf(employee.getId()) + "  ";
            }
            projectDetails += "\n";
        }
        return projectDetails;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public String getOnlyProjects(boolean isDeleted) {
        List<Project> projects = projectDao.getAllProjects(isDeleted);
        String projectString = "";

        for(Project project : projects) {
            projectString = projectString + project.toString();
        }  
        return projectString;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public List<String> getAllProjects(boolean isDeleted) {
        String line = "------------------------\n";
        List<Project> projects = projectDao.getAllProjects(isDeleted);
        List<String> projectDetails = new ArrayList<String>();

	for(Project project : projects) {
            List<Employee> employees = project.getEmployees();
            List<Integer> employeeIds = new ArrayList<Integer>();
            String employeeStrings = "" ;

            if(null != employees) {

                for(Employee singleEmployee : employees) {
                    employeeIds.add(singleEmployee.getId());
                }
                employees = employeeService.getSetOfEmployees(employeeIds);

                for(Employee singleEmployee : employees) {
                    employeeStrings = employeeStrings + singleEmployee.toString();
                }
            }
            projectDetails.add(project.toString() + employeeStrings + line) ;       
        }      	
        return projectDetails;
    }
}