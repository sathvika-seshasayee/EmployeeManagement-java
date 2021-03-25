package com.ideas2it.employeemanagement.project.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.project.dao.ProjectDao;
import com.ideas2it.employeemanagement.project.model.ProjectModel;
import com.ideas2it.employeemanagement.employee.model.EmployeeModel;
import com.ideas2it.employeemanagement.sessionfactory.DataBaseConnection;

/**
 * Creates, reads, deletes, updates employee records from database
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectDaoImpl implements ProjectDao {
    final static String createProjectQuery = " insert into project(project_name, details, start_date, "
                + " project_client, target_date) values(?, ?, ?, ?, ?)";
    final static String checkProjectIdQuery =  "select id from project where "
                                               + "id = ? and is_deleted "
                                               + "= false ";
    final static String getOneProjectQuery = "select project.id, "
            + " project.project_name, project.details, project.start_date, "
            + " project.project_client, project.target_date, "
            + " employee_project_junction.employee_id from project left join "
            + " employee_project_junction on project.id = "
            + " employee_project_junction.project_id where project.id = ? "
            + " and project.is_deleted = false order by "
            + " employee_project_junction.employee_id; ";
    final static String getAllProjectsQuery = "select project.id, "
            + " project.project_name, project.details, project.start_date, "
            + " project.project_client, project.target_date, "
            + " employee_project_junction.employee_id from project left "
            + " join employee_project_junction on project.id = "
            + " employee_project_junction.project_id where project.is_deleted "
            + " = false ;" ;

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public int createProject(ProjectModel projectModelObj) {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        int projectId = 0;
        try {
            PreparedStatement prepareStatement = 
                    connection.prepareStatement(createProjectQuery, 
                            PreparedStatement.RETURN_GENERATED_KEYS);
            prepareStatement = setProject(prepareStatement, projectModelObj, 1);
            int rowsAffected = prepareStatement.executeUpdate();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();

            if((1 == rowsAffected) && (resultSet.next())) {
                projectId = resultSet.getInt(1);
	    }
            prepareStatement.close();
            resultSet.close();
            connection.close();
        } catch(SQLException e) {
            try{
                if(connection!=null)
                    connection.close();
             }catch(SQLException se){}
        }
        return projectId;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public PreparedStatement setProject(PreparedStatement prepareStatement, 
                                        ProjectModel projectModelObj, 
                                        int insertIndex) {
        try {
            prepareStatement.setString(insertIndex++, projectModelObj.getName());
            prepareStatement.setString(insertIndex++, projectModelObj.getDetails());
            prepareStatement.setDate(insertIndex++, projectModelObj.getStartDate());
            prepareStatement.setString(insertIndex++, projectModelObj.getClient());
            prepareStatement.setDate(insertIndex++, projectModelObj.getTargetDate());
        } catch(SQLException e) {
        } finally {
            return prepareStatement;
        }
    }    

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean checkProjectId(int projectId) {
        boolean checkIdStatus = false;
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        try {
            PreparedStatement prepareStatement = 
                    connection.prepareStatement(checkProjectIdQuery); 
            prepareStatement.setInt(1, projectId);
            ResultSet resultSet = prepareStatement.executeQuery();
            checkIdStatus = resultSet.next();
            prepareStatement.close();
            resultSet.close();
            connection.close();
        } catch(SQLException e) {
            try{
                if(connection!=null)
                    connection.close();
             }catch(SQLException se){}
        }
        return checkIdStatus;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ProjectModel getOneProject(int projectId) {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        ProjectModel projectModelObj = null;
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<EmployeeModel> employeeModelObjs = 
                new ArrayList<EmployeeModel>();
        EmployeeModel employeeModelObj;
        try {
            PreparedStatement prepareStatement = 
                    connection.prepareStatement(getOneProjectQuery);
            prepareStatement.setInt(1, projectId);
            ResultSet resultSet = prepareStatement.executeQuery();
            while(resultSet.next()) {
                projectModelObj = getProject(resultSet);
                employeeModelObj = getEmployee(resultSet);
                employeeModelObjs.add(employeeModelObj);    
                projectModelObj.setEmployees(employeeModelObjs);
            }
        } catch(SQLException e) {}
        return projectModelObj;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<ProjectModel> getAllProjects() {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<ProjectModel> projectModelObjs = new ArrayList<ProjectModel>();
        try {
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllProjectsQuery);
            if(resultSet.next()) {
               outer: do {
		    int projectId = resultSet.getInt(1);
                    ProjectModel projectModelObj = getProject(resultSet);
                    ArrayList<EmployeeModel> employeeModelObjs = new ArrayList<EmployeeModel>();
					while(projectId == resultSet.getInt(1)) {
                            EmployeeModel employeeModelObj = getEmployee(resultSet);
                            employeeModelObjs.add(employeeModelObj);
                        if (!resultSet.next()) {
                            break outer;
                        }
                    } 
                    projectModelObj.setEmployees(employeeModelObjs);
                    projectModelObjs.add(projectModelObj);
                } while(true);
            } else {
                projectModelObjs = null;
            }
            resultSet.close();
            connection.close();
        } catch(SQLException e) {
          e.printStackTrace();
            try{
                if(connection!=null)
                    connection.close();
             }catch(SQLException se){}
        }
        return projectModelObjs;
    }
    
    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public EmployeeModel getEmployee(ResultSet resultSet) {
        EmployeeModel employeeModelObj = null;
        try {
            employeeModelObj =
                    new EmployeeModel(resultSet.getInt("employee_id"));
        } catch (SQLException e) {}
        return employeeModelObj;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ProjectModel getProject(ResultSet resultSet) {
        ProjectModel projectModelObj = null;
        try {
            projectModelObj = 
                    new ProjectModel(resultSet.getString("project_name"),
                                     resultSet.getString("details"),
                                     resultSet.getDate("start_date"),
                                     resultSet.getString("project_client"),
                                     resultSet.getDate("target_date"));
            projectModelObj.setId(resultSet.getInt("id"));
        } catch (SQLException e) {}
        return projectModelObj;
    }
        



}






