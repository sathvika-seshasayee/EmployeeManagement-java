package com.ideas2it.employeemanagement.view;

import java.util.Scanner;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.ideas2it.employeemanagement.controller.EmployeeController;

/**
 * Gets input from user and displays results.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeView {
    Scanner scanner = new Scanner(System.in);
    EmployeeController controllerObj = new EmployeeController();

   /**
    * This method displays options for performing in employee Database.
    */
   public void EmployeeOptions() {
        String option = "y";
        String optionQuestion = "What do you want to do today with the Employee Database?\n 1. Create 2. Display" 
                                 + " one Emlpoyee details 3. Display All Employee details 4. Update 5. Delete 6.Exit\n";
        do {
            System.out.println(optionQuestion);
            int choice = scanner.nextInt();
            scanner.skip(Pattern.compile("[\n\r]{2}"));          

            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    displaySingleEmployee();  
                    break;   
                case 3:
                     displayAllEmployees();
                     break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    Runtime.getRuntime().halt(0);
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }

            System.out.println("Do you want to use the Database again? y/n ");
            option = scanner.nextLine(); 
        } while (option.equals("y"));
    }
     
    /**
     * This method gets input from user based on option.
     * @params option is the input required from user i.e.name, designation etc..
     */
    private String getEmployee(String option) {
        System.out.println("Enter the " + option + " of the Employee");
        return scanner.nextLine();
    }

   /**
    * This method adds details of employee to Database.
    */
    private void createEmployee() {
        String employeeID = getEmployee("ID");
        boolean createStatus;
        if (!controllerObj.checkEmployeeID(employeeID)) {                     
            String name = getEmployee("name");  
            String designation = getEmployee("designation");          
            int employeeSalary = getEmployeeSalary();
            long mobileNumber =  getEmployeePhoneNumber();                 
            Date date = getEmployeeDOB();
            createStatus = controllerObj.createEmployee(employeeID, name, designation, employeeSalary, date, mobileNumber);
            System.out.println(createStatus ? "Employee created sucessfully" : "Please try again");                   
        } else {
            System.out.println("Employee ID already exists");
        }
    }

   /**
    * This method deletes an employee from Database.
    */
    private void deleteEmployee() {
        String employeeID = getEmployee("ID");
        if (controllerObj.checkEmployeeID(employeeID)) {
            controllerObj.deleteEmployee(employeeID);
            System.out.println("Employee deleted sucessfully"); 
        } else {
            System.out.println("Employee ID does not exist"); 
        }
    }

    /**
     * This method updates employee details based on user's choice.
     */
    private void updateEmployee() {
        String updateQuestion = "What do you want to update\n 1.Name 2.Designation 3.Salary 4. Date of Birth "
                                 + " 5. Phone Number 6.Exit";
        String option;
        String employeeID = getEmployee("ID");
        if (controllerObj.checkEmployeeID(employeeID)) {
            System.out.println(updateQuestion);
            int choice = scanner.nextInt();
            scanner.skip(Pattern.compile("[\r\n]{2}"));

            switch (choice) {
                case 1:
                    controllerObj.setEmployeeName(getEmployee("name"), employeeID);     
                    break;
                case 2:
                    controllerObj.setEmployeeDesignation(getEmployee("designation"), employeeID);
                    break;
                case 3:
                    controllerObj.setEmployeeSalary(getEmployeeSalary(), employeeID);
                    break;
                case 4:
                    controllerObj.setEmployeeDOB(getEmployeeDOB(), employeeID);
                    break;
                case 5:
                    controllerObj.setEmployeePhoneNumber(getEmployeePhoneNumber(), employeeID);
                    break;
                case 6:
                    Runtime.getRuntime().halt(0);
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }
            System.out.println("Updation was sucessfull");
        } else {
           System.out.println("Employee ID already exists");
        }
    }

    /**
     * This method gets and validates date of birth.
     */
    private Date getEmployeeDOB() {
        String dob = getEmployee("date of birth (dd/MM/yyyy)");
        Date date = controllerObj.checkEmployeeDOB(dob);
        while(null == controllerObj.checkEmployeeDOB(dob)) {
            System.out.println("Please enter date of birth in right format dd/MM/yyyy");
            dob = getEmployee("date of birth (dd/MM/yyyy)");
            date = controllerObj.checkEmployeeDOB(dob);
        } 
        return date;
    }

    /**
     * This method gets and validates phone number.
     */
    private long getEmployeePhoneNumber() {
        String phoneNumber = getEmployee("phone number");
        long mobileNumber = controllerObj.checkEmployeePhoneNumber(phoneNumber);
        while(0 == controllerObj.checkEmployeePhoneNumber(phoneNumber)) {
            System.out.println("Please enter correct number");
            phoneNumber = getEmployee("phone number");
            mobileNumber = controllerObj.checkEmployeePhoneNumber(phoneNumber);
        } 
        return mobileNumber;
    }
     
    /**
     * This method gets and validates salary.
     */   
    private int getEmployeeSalary() {
        String salary = getEmployee("Salary");
        int employeeSalary = controllerObj.checkEmployeeSalary(salary);
        while(0 == controllerObj.checkEmployeeSalary(salary)) {
            System.out.println("Please enter numbers only");
            salary = getEmployee("Salary");
            employeeSalary = controllerObj.checkEmployeeSalary(salary);
        } 
        return employeeSalary;
    }

    /**
     * This method displays one employee details.
     */  
    private void displaySingleEmployee() {
        String employeeID = getEmployee("ID");
        if(controllerObj.checkEmployeeID(employeeID)) {
            String employeeDetails = controllerObj.displaySingleEmployee(employeeID);
            System.out.println(employeeDetails);
        } else {
            System.out.println("Employee ID does not exist");
        }
    }

    /**
    * This method displays all employees details.
    */
    private void displayAllEmployees() {
        String[] employeeDetails = controllerObj.displayAllEmployees();
        for(String i : employeeDetails ) {
            System.out.println(i);
        }
        System.out.println("------------------End of list---------------------------");
    }
} 