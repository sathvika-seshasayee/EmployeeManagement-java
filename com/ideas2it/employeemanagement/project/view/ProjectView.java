package com.ideas2it.employeemanagement.project.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.Iterator;

import com.ideas2it.employeemanagement.project.controller.ProjectController;

/**
 * Gets input from user and displays results.
 * 
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class ProjectView {
    static Scanner scanner = new Scanner(System.in);
    ProjectController controllerObj = new ProjectController();
    static final String ProjectOptionsQuestion = "What do you want to do today with the"
                                + " Project Database?\n1. Create Project"
                                + "\n2. Display one Project details "
                                + "\n3. Display All Projects "
                                + " details \n4. Update Employee \n5. Delete Employee"
                                + "\n6. Restore Project \n7. Exit\n";

   /**
    * This method displays options that can be performed with Project Database.
    */
    public void ProjectOptions() {
        String option = "y"; 

        while (true) {
            System.out.println(ProjectOptionsQuestion);
            int choice = scanner.nextInt();
            scanner.skip(Pattern.compile("[\n\r]{2}"));    

            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2:
                    displayOneProject();  
                    break;   
                case 3:
                    displayAllProjects();
                    break;
                case 4:
                    updateProject();
                    break;
                case 5:
                    deleteProject();
                    break;
                case 6:
                    restoreProject();
                    break;
                case 7:
                    System.out.println("*****Thank You*****\n\n");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }      
        }
    }

   /**
    * This method adds details of employee to Database.
    */
    public void createProject() {
        String name = getProject("name");
        String details = getProject("details (in one line)");
        Date startDate = getDate("start");
        String client = getProject("client");
        Date targetDate = getDate("target");
        int projectId = controllerObj.createProject(name, details, startDate, client, targetDate);
        if (0 != projectId) {
            System.out.println("Project created sucessfully \nID of project is " + projectId);
        } else {
            System.out.println("Project was not created. Please try again.");
        }
    }

    /**
     * This method displays one project.
     */
    public void displayOneProject() {
        int projectId = getProjectId();
        if(controllerObj.checkProjectId(projectId)) {
            System.out.println(controllerObj.displayOneProject(projectId));
            System.out.println("----------------End------------------");
        } else {
            System.out.println("Project Id does not exist");
        }
    }

    /**
     * This method gets id of project from user.
     */
    public int getProjectId() {
        System.out.print("Enter the project id :  ");
        int projectId = scanner.nextInt();
        scanner.skip(Pattern.compile("[\n\r]{2}"));
        return projectId;
    }  

    /**
     * This method displays all the projects.
     */
    public void displayAllProjects() {
        ArrayList<String> projectDetails = 
                controllerObj.displayAllProjects();
        if (!projectDetails.isEmpty()) {
            Iterator<String> project = projectDetails.iterator();
            while(project.hasNext()) { 
                System.out.println(project.next());
            }
            System.out.println("---------End of list---------");
        } else {
            System.out.println("No project records to display");
        }
    }

    public void updateProject() {
    }

    public void deleteProject() {
    }
   
    public void restoreProject() {
    }

    /** 
     * Gets date from user and validates it
     * @param option specifies option of date to
     * get from user.
     * @return date in correct format
     */
    public Date getDate(String option) {
        String date = getProject(option + " date (yyyy-MM-dd)");
        Date projectDate = controllerObj.validateDate(date);
        while(null == projectDate) {
            System.out.println("Please enter date in right "
                               + "format yyyy-MM-dd");
            date = getProject(option + "date (yyyy-MM-dd)");
            projectDate = controllerObj.validateDate(date);
        } 
        return projectDate;
    }

    /**
     * This method gets any one detail from user
     * @param option specfies detail to get from user
     * @return detail entered by user
     */
    public String getProject(String option) {
       System.out.print("Enter the " + option + " of the project  :  ");
       return scanner.nextLine();
    }


}

