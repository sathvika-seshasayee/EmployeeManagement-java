package com.ideas2it.employeemanagement.view;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;
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
   public void EmployeeOptions() throws ClassNotFoundException, SQLException {
        String option = "y";
        String optionQuestion = "What do you want to do today with the Employee Database?\n1. Create 2. Display" 
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
        System.out.print("Enter the " + option + " of the Employee  :  ");
        return scanner.nextLine();
    }

   /**
    * This method adds details of employee to Database.
    */
    private void createEmployee() throws ClassNotFoundException, SQLException {     
        String addressType; 
        boolean addressStatus;   
            String name = getEmployee("name");  
            String designation = getEmployee("designation");          
            long employeeSalary = getEmployeeSalary();
            long mobileNumber =  getEmployeePhoneNumber();                 
            Date date = getEmployeeDOB();
            int employeeID = controllerObj.createEmployee(name, designation, employeeSalary, 
                                                      date, mobileNumber);
            System.out.println("is your permenant address same as current address? y/n");
            boolean yesOrNo = ("y" == scanner.nextLine());
            int n = (false == yesOrNo) ? 1 :0;
            addressType = "permanant";
        for (int i = 0; i < n; i++ ) {
            System.out.println("Enter the " + addressType + " address");
            String address = getEmployee("address i.e. apartment number, street, area ");
            String city = getEmployee("city");
            String state = getEmployee("state");
            String country = getEmployee("country");
            System.out.print("Enter the pin code of address of the employee  :  ");
            int pinCode = scanner.nextInt();
            scanner.nextLine(); // change to skip and write comment explanation
            addressStatus = controllerObj.createAddress(employeeID, address, city, state, country, pinCode, addressType, yesOrNo);
            addressType = "temporary";
            System.out.println((false == addressStatus) ? "Address added" : "Address not added");
        }
           System.out.println("Employee created sucessfully\nEmployee ID is " + employeeID);                   
   }
  

   /**
    * This method deletes an employee from Database.
    */
    private void deleteEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter the id of employee");
        int employeeID = scanner.nextInt();
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
    private void updateEmployee() throws ClassNotFoundException, SQLException {
        String updateQuestion = "What do you want to update\n 1.Name 2.Designation 3.Salary 4. Date of Birth "
                                 + " 5. Phone Number 6. Address 7. Exit";
        System.out.println("Enter the ID of employee you want to update");
        int employeeID = scanner.nextInt();
        scanner.skip(Pattern.compile("[\r\n]{2}"));                                                      
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
                    System.out.println("Do you want to update the 1. permanant or\n 2. temporary address");
                    choice = scanner.nextInt(); 
                    scanner.skip(Pattern.compile("[\r\n]{2}"));                          
                    String address = getEmployee("address i.e. apartment number, street, area ");
                    String city = getEmployee("city");
                    String state = getEmployee("state");
                    String country = getEmployee("country");
                    System.out.print("enter the pincode : ");
                    int pinCode = scanner.nextInt();
                    scanner.skip(Pattern.compile("[\r\n]{2}"));
                    if (1 == choice) {
                        System.out.println("Is you permanant address same as temporary address? y/n");
                        String option = scanner.nextLine();
                        boolean yesOrNo = ("y" == option);
                        controllerObj.setAddress(employeeID, address,
                                city, state, country, pinCode, "permanant", yesOrNo);
// delete any temporary address if present
                    } else {
                        controllerObj.setAddress(employeeID, address,
                                city, state, country, pinCode, "temporary", false);
                    } 
                case 7:
                    Runtime.getRuntime().halt(0);
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }
            System.out.println("Updation was sucessfull");
        } else {
            System.out.println("Employee ID does not exist");
        }
    }

    /**
     * This method displays one employee details.
     */  
    private void displaySingleEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter the id of employee");
        int employeeID = scanner.nextInt();
        scanner.nextLine();                                     // change to skip
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
    private void displayAllEmployees() throws ClassNotFoundException, SQLException {
        String[] employeeDetails = controllerObj.displayAllEmployees();
        for(String i : employeeDetails ) {
            System.out.println(i);
        }
        System.out.println("------------------End of list---------------------------");
    }

    /**
     * This method gets and validates phone number.
     */
    private long getEmployeePhoneNumber() throws ClassNotFoundException, SQLException {
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
    private long getEmployeeSalary() throws ClassNotFoundException, SQLException {
        String salary = getEmployee("Salary");
        long employeeSalary = controllerObj.checkEmployeeSalary(salary);
        while(0 == controllerObj.checkEmployeeSalary(salary)) {
            System.out.println("Please enter numbers only");
            salary = getEmployee("Salary");
            employeeSalary = controllerObj.checkEmployeeSalary(salary);
        } 
        return employeeSalary;
    }

    /**
     * This method gets and validates date of birth.
     */
    private Date getEmployeeDOB() throws ClassNotFoundException, SQLException {
        String dob = getEmployee("date of birth (yyyy-MM-dd)");
        Date date = controllerObj.checkEmployeeDOB(dob);
        while(null == controllerObj.checkEmployeeDOB(dob)) {
            System.out.println("Please enter date of birth in right format yyyy-MM-dd");
            dob = getEmployee("date of birth (yyyy-MM-dd)");
            date = controllerObj.checkEmployeeDOB(dob);
        } 
        return date;
    }
} 

