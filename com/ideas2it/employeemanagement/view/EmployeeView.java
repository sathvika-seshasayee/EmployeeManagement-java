package com.ideas2it.employeemanagement.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.List;                                                   
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

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
                                 + " one Emlpoyee details 3. Display All Employee details 4. Update 5. Delete "
                                 + "6. Restore Employee 7.Exit\n";
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
                    displayAllEmployees("active");
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    displayAllEmployees("deleted");
                    restoreEmployee();
                    break;
                case 7:
                    Runtime.getRuntime().halt(0);
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }

            System.out.println("Do you want to use the Database again? y/n ");
            option = scanner.nextLine(); 
        } while (option.equals("y"));
    }
     
    private void restoreEmployee() throws ClassNotFoundException, SQLException  {
        System.out.println("Enter id of Employee");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        if (controllerObj.checkEmployeeID(employeeId)) {
           boolean restoreEmployeeStatus = controllerObj.restoreEmployee(employeeId);
           if(!restoreEmployeeStatus) {
               System.out.println("Employee not restored");
           } else {
               System.out.println("Employee restored sucessfully");
           }
        } else {
            System.out.println("Employee Id does not exist");
        }
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
        ArrayList<ArrayList<String>> addresses = new ArrayList<ArrayList<String>>();
        ArrayList<String> address = new ArrayList<String>();
        String name = getEmployee("name");  
        String designation = getEmployee("designation");          
        double employeeSalary = getEmployeeSalary();
        long mobileNumber =  getEmployeePhoneNumber();                 
        Date date = getEmployeeDOB();
        System.out.println("Do you want to enter the address ? y/n ");
        if ((scanner.nextLine()).equals("y")) {
        System.out.print ("How many addresses do you want to enter including your " 
                               + "current address, permanant address and other addresses? :  ");
        int n = scanner.nextInt();
        scanner.skip(Pattern.compile("[\r\n]{2}")); 
        int addressIndex = 0; 
        addressType = "permanant";
        for (int i = 0; i < n; i++ ) {
            System.out.println("\nEnter the " + addressType + " address");
            address = getAddress(address); 
            address.add(addressType);  
            addressType = "temporary"; 
            addresses.add(address);
        }  
        } else {
            for (int i = 0; i < 5 ; i++ ) {
            address.add(null);
         }
            address.add("permanant");
            addresses.add(address);
        }                 
        int employeeId = controllerObj.createEmployee(name, designation, employeeSalary, date, mobileNumber, addresses);
       
        if(0 == employeeId){
            System.out.println("Employee not added, please try again");
        } else {
            System.out.println("Employee created sucessfully\nEmployee ID is " + employeeId);   
        }                
    }

    private ArrayList<String> getAddress(ArrayList<String> address) {
            address.add(getEmployee("address i.e. apartment number, street, area "));
            address.add(getEmployee("city"));
            address.add(getEmployee("state"));
            address.add(getEmployee("country"));
            address.add(getPinCode());
        return address;
    }

    private String getPinCode() {
        String pinCode = getEmployee("pin code of residence");
        return controllerObj.checkPinCode(pinCode) ? pinCode : getPinCode();
    }
         
   /**
    * This method deletes an employee from Database.
    */
    private void deleteEmployee() throws ClassNotFoundException, SQLException {
        System.out.println("Enter the id of employee");
        int employeeID = scanner.nextInt();
        scanner.skip(Pattern.compile("[\r\n]{2}"));
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
                                 + " 5. Phone Number 6. Update existing Address 7. Delete address 8. Add Address" 
                                 + " 9. Exit";
        
        int updateOption ;
        int addressnumber = 1;
        String addressType = "temporary";
        
        Map<Integer, String> employeeAddressDetails = new TreeMap<Integer, String>();
        int choice = 0;
          System.out.println("Enter the ID of employee you want to update");
        int employeeId = scanner.nextInt();             
        scanner.skip(Pattern.compile("[\r\n]{2}"));      
         
                                          
        if (controllerObj.checkEmployeeID(employeeId)) {
             System.out.println(updateQuestion);
           choice = scanner.nextInt();
           scanner.skip(Pattern.compile("[\r\n]{2}"));
            
            ArrayList<String> address = new ArrayList<String>();
            

            switch (choice) {
                case 1:
                    controllerObj.setEmployeeName(getEmployee("name"), employeeId);     
                    break;
                case 2:
                    controllerObj.setEmployeeDesignation(getEmployee("designation"), employeeId);
                    break;
                case 3:
                    controllerObj.setEmployeeSalary(getEmployeeSalary(), employeeId);
                    break;
                case 4:
                    controllerObj.setEmployeeDOB(getEmployeeDOB(), employeeId);
                    break;
                case 5:
                    controllerObj.setEmployeePhoneNumber(getEmployeePhoneNumber(), employeeId);
                    break;
                case 6:
                    employeeAddressDetails = controllerObj.singleEmployeeAddress(employeeId);
                    int addressNumber = 1;
                
                    System.out.println("Enter the address option you want to update e.g. 1, 2, etc");
                    int[]  addressIdArray= new int[employeeAddressDetails.size() + 1];
                    for( Map.Entry<Integer, String> entry : employeeAddressDetails.entrySet() ){
                        System.out.println( addressNumber + ".  " + entry.getValue());
                        addressIdArray[addressNumber] = entry.getKey();
                        addressNumber++;
                    }
               
                    updateOption = scanner.nextInt();    // choice of address
                    scanner.skip(Pattern.compile("[\r\n]{2}"));    
                    int addressId = addressIdArray[updateOption];
                    address = getAddress(address);    
                                   
                    controllerObj.setAddress(addressId, address);          
                    break;
                case 7:
                    employeeAddressDetails = controllerObj.singleEmployeeAddress(employeeId);
                    addressNumber = 1;
                    System.out.println("Enter the address option you want to update e.g. 1, 2, etc");
                    addressIdArray= new int[employeeAddressDetails.size() + 1];
                    for( Map.Entry<Integer, String> entry : employeeAddressDetails.entrySet() ){
                        System.out.println( addressNumber + ".  " + entry.getValue());
                        addressIdArray[addressNumber] = entry.getKey();
                        addressNumber++;
                    }
                     updateOption = scanner.nextInt();    // choice of address
                    scanner.skip(Pattern.compile("[\r\n]{2}"));    
                    addressId = addressIdArray[updateOption];

                    controllerObj.deleteSingleAddress(addressId);
                    break;
                case 8:
                    addressType = "temporary";
                    address = getAddress(address);
                    address.add(addressType);
            
                    controllerObj.addAddress(employeeId, address);
                    break;
                case 9:
                    EmployeeOptions();
                    break;
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
        System.out.print("Enter the id of employee  :  ");
        int employeeID = scanner.nextInt();
        scanner.nextLine();                                     // change to skip
        if(controllerObj.checkEmployeeID(employeeID)) {
            String employeeDetails = controllerObj.displaySingleEmployee(employeeID);
            System.out.println(employeeDetails);
            System.out.println("----------------End------------------");
        } else {
            System.out.println("Employee ID does not exist");
        }
    }

   /**
    * This method displays all employees details.
    */
    private void displayAllEmployees(String option) throws ClassNotFoundException, SQLException {
        ArrayList<String> employeeDetails = controllerObj.displayAllEmployees(option);
        Iterator<String> employee = employeeDetails.iterator();
        while(employee.hasNext()) { 
            System.out.println(employee.next());
        }
        System.out.println("--------------End of list---------------");
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
    private double getEmployeeSalary() throws ClassNotFoundException, SQLException {
        String salary = getEmployee("Salary");
        double employeeSalary = controllerObj.checkEmployeeSalary(salary);
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

