<<<<<<< HEAD
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
        ArrayList<Integer> Ids = new ArrayList<Integer>();
        if("" != name) {
            projectModelObj.setName(name);
        }
        
        if("" != details) {
            projectModelObj.setDetails(details);
        }
 
        if("" != client) {
            projectModelObj.setDetails(client);
        }
    
        if(null != startDate) {
            projectModelObj.setStartDate(startDate);
        }
    
        if(null != targetDate) {
            projectModelObj.setTargetDate(targetDate);
        }

        if(!employees.isEmpty()) {
            employees = checkForDuplicates(employees, projectModelObj.getEmployees());
            projectModelObj.setEmployees(employeeServiceObj.getSetOfEmployees(employees));
        }
        return projectDao.updateProject(projectModelObj);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<Integer> checkForDuplicates(ArrayList<Integer> employees, 
                                      ArrayList<EmployeeModel> employeeModelObjs) {
        Set<Integer> idSetOne = new HashSet<Integer>(employees);
        ArrayList<Integer> employeeIds = getEmployeeIds(employeeModelObjs);
        Set<Integer> idSetTwo = new HashSet<Integer>(employeeIds);
        if(!idSetTwo.isEmpty()) {
            idSetOne.removeAll(idSetTwo);
        }
        employeeIds = new ArrayList<Integer>(idSetOne);
        return employeeIds;
    }
           
    public ArrayList<Integer> getEmployeeIds(ArrayList<EmployeeModel> employeeModelObjs) {
        ArrayList<Integer> employeeIds = new ArrayList<Integer>();
        for(EmployeeModel employeeModel : employeeModelObjs) {
            employeeIds.add(employeeModel.getId());
        }
        return employeeIds;
    }
                 
    public boolean checkEmployeeId(int employeeId) {
        return employeeServiceObj.checkEmployeeID(employeeId);
    }
        
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createProject(String name, String details, Date startDate,
                      String client, Date targetDate) {
        ProjectModel projectModelObj = new ProjectModel(name, details, startDate,
                                                        client, targetDate);
        return projectDao.createProject(projectModelObj);
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
    public boolean restoreProject(int projectId) {
        return projectDao.restoreProject(projectId);
    }
       
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public String getOneProject(int projectId) {
        ProjectModel projectModelObj = projectDao.getOneProject(projectId);
        ArrayList<EmployeeModel> employeeModelObjects = projectModelObj.getEmployees();
        ArrayList<Integer> employeeIds = getEmployeeIds(employeeModelObjects);
        ArrayList<EmployeeModel> employeeObjects = 
                employeeServiceObj.getSetOfEmployees(employeeIds);
        projectModelObj.setEmployees(employeeObjects);
        String employee = "";
        for(EmployeeModel employeeObj : employeeObjects) {
            employee = employee + employeeObj.toString();
        }
        return (projectModelObj.toString() + employee);
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<String> getAllProjects(String option) {
        String line = "------------------------\n";
        ArrayList<ProjectModel> projects = projectDao.getAllProjects(option);
        ArrayList<Integer> employeeIds = new ArrayList<Integer>();
        Map<Integer, EmployeeModel> employee = new HashMap<Integer, EmployeeModel>();
        ArrayList<EmployeeModel> allEmployees = employeeServiceObj.getAllEmployeesModel();
        ArrayList<String> projectString = 
                  new ArrayList<String>();
        for(EmployeeModel employeeModelObj : allEmployees) {
            employee.put(employeeModelObj.getId(), employeeModelObj);
        }
        for(ProjectModel project : projects) {
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






=======
package com.ideas2it.employeemanagement.project.service.impl;

import java.sql.Date;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
    public boolean updateProject(projectId, newName,
                                newDetails, newStartDate, newClient, newTargetDate, employees) {
        ProjectModel employeeModelObj = employeeDao.getSingleEmployee(employeeId);
        employeeModelObj.setId(employeeId);
        if("" != newName) {
        

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createProject(String name, String details, Date startDate,
                      String client, Date targetDate) {
        ProjectModel projectModelObj = new ProjectModel(name, details, startDate,
                                                        client, targetDate);
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
        for(EmployeeModel employee : employeeModelObjects) {
            employeeIds.add(employee.getId());
        }
        ArrayList<EmployeeModel> employeeObjects = 
                employeeServiceObj.getSetOfEmployees(employeeIds);
        projectModelObj.setEmployees(employeeObjects);
        return projectModelObj.toString();
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<String> getAllProjects() {
        String line = "------------------------\n";
        ArrayList<ProjectModel> projects = projectDao.getAllProjects();
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
			System.out.println(employee.get(3));
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






>>>>>>> 665f890bb8669ac259479e01d25d579ed7507da3
