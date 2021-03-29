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
    final static String deleteProjectQuery = "update project set is_deleted = "
            + " true where id = ? ";
    final static String updateProjectQuery = "update project set project_name = ?,"
            + " details = ?, start_date = ? , project_client = ? , target_date = ? "
            + " where id = ? ";
    final static String restoreDisplayQuery = "select project.id, "
            + " project.project_name, project.details, project.start_date, "
            + " project.project_client, project.target_date, "
            + " employee_project_junction.employee_id from project left "
            + " join employee_project_junction on project.id = "
            + " employee_project_junction.project_id where project.is_deleted "
            + " = true ;" ;
    final static String restoreProjectQuery = "update project set is_deleted = "
            + " false where id = ? ";

     /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean restoreProject(int projectId) {
        int rowsAffected = 0;
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        try {
            PreparedStatement prepareStatement = 
		        connection.prepareStatement(restoreProjectQuery);    
            prepareStatement.setInt(1, projectId);
            rowsAffected = prepareStatement.executeUpdate();
            prepareStatement.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.close();
            } catch(SQLException ex) {
                    System.out.println(e);
           }
        }
        return (rowsAffected == 1);
    }

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
             }catch(SQLException se){
                   System.out.println(e);
            }
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
    public boolean updateProject(ProjectModel projectModelObj) {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<EmployeeModel> employeeModelObjs = 
                    new ArrayList<EmployeeModel>();
        boolean updateStatus = false;   
        int rowsAffected = 0;                                              
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = 
                connection.prepareStatement(updateProjectQuery); 
            prepareStatement = setProject(prepareStatement, projectModelObj, 1);
            prepareStatement.setInt(6, projectModelObj.getId());
            rowsAffected = prepareStatement.executeUpdate();
            prepareStatement.close();
			System.out.println("inside dao");
            ArrayList<Integer> employeeIds = new ArrayList<Integer>();
            employeeModelObjs = projectModelObj.getEmployees();
			System.out.println(projectModelObj.getEmployees());
			System.out.println(employeeModelObjs);
            if((!employeeModelObjs.isEmpty()) && (1 == rowsAffected)) {
				for(EmployeeModel employeeObj : employeeModelObjs) {
					employeeIds.add(employeeObj.getId());
			    }
				System.out.println(employeeIds);
				String value = "";
				for(int i = 0; i < employeeIds.size() ; i++) {    
                    value = value + " (?,?) ";
                } 
				String updateEmployeesQuery = "insert into employee_project_junction(employee_id, project_id) "
                                		      + " values " + value ;
				System.out.println(updateEmployeesQuery);
                prepareStatement = connection.prepareStatement(updateEmployeesQuery);
				prepareStatement.setInt(1, projectModelObj.getId());
                int insertIndex = 1;
                for(EmployeeModel employeeObj : employeeModelObjs) {
                    prepareStatement.setInt(insertIndex++, employeeObj.getId());
                    prepareStatement.setInt(insertIndex++, projectModelObj.getId());
                } 
                prepareStatement.execute();
             }
             connection.commit();
             updateStatus = (1 == rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            try {
                connection.close();
            } catch(SQLException ex) {}
        } finally {
            return updateStatus;
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
            System.out.println(e);
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
        } catch(SQLException e) {
             System.out.println(e);
        }
        return projectModelObj;
    }

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public ArrayList<ProjectModel> getAllProjects(String option) {
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        Connection connection = dataBaseConnection.mysqlConnection();
        ArrayList<ProjectModel> projectModelObjs = new ArrayList<ProjectModel>();
        String query = ("deleted" == option) ? restoreDisplayQuery : getAllProjectsQuery;
        try {
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
               outer: do {
		    int projectId = resultSet.getInt(1);
                    ProjectModel projectModelObj = getProject(resultSet);
                    ArrayList<EmployeeModel> employeeModelObjs = new ArrayList<EmployeeModel>();
			while(projectId == resultSet.getInt(1)) {
                            if(0 != resultSet.getInt("employee_Id")){
                                EmployeeModel employeeModelObj = getEmployee(resultSet);
                                employeeModelObjs.add(employeeModelObj);
                            }
                            if (!resultSet.next()) {
                                projectModelObj.setEmployees(employeeModelObjs);
                                    projectModelObjs.add(projectModelObj);
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

    /**
  
     * {@inheritdoc}
    
     */    
    @Override
    public boolean deleteProject(int projectId) {  
        DataBaseConnection dataBaseConnection = 
	        DataBaseConnection.getInstance();
        boolean deletProjectStatus = false;
        String value = "";
        Connection connection = dataBaseConnection.mysqlConnection();
        String deleteEmployeesQuery = "delete from employee_project_junction "
		                              + " where employee_id in ( ? " + value + " ) and  project_id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = 
		        connection.prepareStatement(deleteProjectQuery);    
            prepareStatement.setInt(1, projectId);
            int rowsAffected = prepareStatement.executeUpdate();
            if(1 == rowsAffected) {
                ProjectModel projectModelObj = getOneProject(projectId);
                ArrayList<EmployeeModel> employeeModelObjs = projectModelObj.getEmployees();
                for (int i = 0; i < (employeeModelObjs.size() - 1) ; i++) {
                    value = value + ", ? ";
                }
                prepareStatement = 
		        connection.prepareStatement(deleteEmployeesQuery); 
                int insertIndex = 1;
            if(!employeeModelObjs.isEmpty()) {
                for (EmployeeModel employeeObj : employeeModelObjs) {
                      prepareStatement.setInt(insertIndex++, employeeObj.getId());
                }
                prepareStatement.setInt(insertIndex++, projectId);
                prepareStatement.executeUpdate();
            }
                connection.commit();
                deletProjectStatus = true;
            } else {
               connection.rollback();
            }
            prepareStatement.close();
        } catch (SQLException ex) {
              System.out.println(ex);
              deletProjectStatus = false;
        }
        return deletProjectStatus;                             
    }



}