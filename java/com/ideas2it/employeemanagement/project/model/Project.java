package com.ideas2it.employeemanagement.project.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.ideas2it.employeemanagement.employee.model.Employee;
/**
 * Plain old java object for Project.
 * Contains details of project.
 * @version 1.0 24 Mar 2021
 * @author Sathvika Seshasayee
 */
public class Project {
    private int id;
    private String name;
    private String details;
    private Date startDate;
    private String client;
    private Date targetDate;
    private boolean isDeleted;
    private List<Employee> employees;

    public Project () {};

    public Project (int id) {
        this.id = id;
    }
    
    public Project (String name, String details, Date startDate, String client, Date targetDate) {
        this.name = name;
        this.details = details;
        this.startDate = startDate;
        this.client = client;
        this.targetDate = targetDate;
    }

    public Project (String name, String details, Date startDate, 
                        String client, Date targetDate, List<Employee> employees) {
        this.name = name;
        this.details = details;
        this.startDate = startDate;
        this.client = client;
        this.targetDate = targetDate;
        this.employees = employees;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return this.id;
    }  

    public void setId(int Id) {
        this.id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }
  
    public void setDetails(String details) {
        this.details = details;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }    

    public String getName() {
        return this.name;
    }

    public String getDetails() {
        return this.details;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public String getClient() {
        return this.client;
    }

    public Date getTargetDate() {
        return this.targetDate;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public String toString() {
             
        return  id 
               + ";" + name
               + ";" + details
               + ";" + client
               + ";" + startDate
               + ";" + targetDate  ;
    }

}