package com.ideas2it.employeemanagement.employee.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.List;  
import java.util.Map;                                                 
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.TreeMap;

import com.ideas2it.employeemanagement.employee.controller.EmployeeController;

/**
 * Gets input from user and displays results.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeView {
    static Scanner scanner = new Scanner(System.in);
    final static String updateQuestion = "\n1. Name "
                                + "\n2. Designation \n3. Salary "
                                + "\n4. Date of Birth \n5. Phone Number "
                                + "\n6. Add Address \n7. Delete one Address \n8. Assign Project"
                                + "\n9. Un Assign Project \n10. Exit\n" ;
    final static String employeeOptionsQuestion = "What do you want to do today with the"
                                + " Employee Database?\n1. Create Employee"
                                + "\n2. Display one Emlpoyee details "
                                + "\n3. Display All Employee "
                                + " details \n4. Update Employee \n5. Delete Employee"
                                + "\n6. Restore Employee "
                                + "\n7. Exit\n";
    EmployeeController controllerObj = new EmployeeController();

   
    /**
     * This method restores one employee
     */ 
    private void restoreEmployee() {
        EmployeeController controllerObj = new EmployeeController();
        int employeeId = getEmployeeId();
        if (controllerObj.checkEmployeeID(employeeId, true)) {
            boolean restoreEmployeeStatus = 
                    controllerObj.restoreEmployee(employeeId);
            if(!restoreEmployeeStatus) {
                System.out.println("Employee not in database");
            } else {
                System.out.println("Employee restored sucessfully\n\n");
            }
        } else {
            System.out.println("Employee Id does not exist");
        }
    }

    /**
     * This method gets input from user based on option.
     * @params option is the input required from user
     * i.e.name, designation etc
     */
    private String getEmployee(String option) {
        System.out.print("Enter the " + option + " of the Employee  :  ");
        return scanner.nextLine();
    }

   /**
    * This method adds details of employee to Database.
    */
    private void createEmployee() {     
        List<List<String>> addresses 
                = new ArrayList<List<String>>();
        ArrayList<String> address = new ArrayList<String>();
        String name = getEmployee("name");  
        String designation = getEmployee("designation");          
        double employeeSalary = getEmployeeSalary();
        long mobileNumber =  getEmployeePhoneNumber();                 
        Date date = getEmployeeDOB();
        System.out.println("Do you want to enter the address ? y/n ");
        if ((scanner.nextLine()).equals("y")) {
            System.out.print ("How many addresses do you want to enter" 
                              + " including your permanant address and "
                              + "other addresses? :  ");
            int n = scanner.nextInt();
            scanner.skip(Pattern.compile("[\r\n]{2}")); 
            for (int i = 0; i < n; i++ ) {
                System.out.println("\nEnter address no. " + (i+1));
                address = getAddress(); 
                addresses.add(address);
            }  
        } else {
             addresses = null;
        }                
        int employeeId = controllerObj.createEmployee(name, designation, 
                employeeSalary, date, mobileNumber, addresses);
        if(0 == employeeId){
            System.out.println("Employee not added, please try again");
        } else {
            System.out.println("Employee created sucessfully\nEmployee ID is "
                               + employeeId);   
        }                
    }

    /**
     * This method gets address details from the user.
     */
    private ArrayList<String> getAddress() {
        ArrayList<String> address = new ArrayList<String>();
        address.add(getEmployee("address i.e. apartment number, "
                                + "street, area "));
        address.add(getEmployee("city"));
        address.add(getEmployee("state"));
        address.add(getEmployee("country"));
        address.add(getPinCode());
        address.add(getAddressType());
        return address;
    }

    private String getAddressType() {
        String addressType;
        System.out.println("Enter the type of address 1. Permananant 2. Temporary");
        int option = scanner.nextInt();
        scanner.skip(Pattern.compile("[\r\n]{2}"));
        if(1 == option) {
            addressType = "permanant";
        } else if (2 == option){
            addressType = "temporary";
        } else {
            System.out.println("Invalid option. Please enter again");
            addressType = getAddressType();
        }
        return addressType;
    }

    /**
     * This method gets and vaidates pincode.
     */
    private String getPinCode() {
        EmployeeController controllerObj = new EmployeeController();
        String pinCode = getEmployee("pin code of residence");
        return controllerObj.checkPinCode(pinCode) ? pinCode : getPinCode();
    }
         
   /**
    * This method deletes an employee from Database.
    */
    private void deleteEmployee() {
        int employeeID = getEmployeeId();
        EmployeeController controllerObj = new EmployeeController();
        if (controllerObj.checkEmployeeID(employeeID, false)) {
            controllerObj.deleteEmployee(employeeID);
            System.out.println("Employee deleted sucessfully"); 
        } else {
            System.out.println("Employee ID does not exist"); 
        }
    }

    /**
     * This method gets employee ID is present in database
     */
    private int getEmployeeId() {
        int employeeId;
        System.out.print("Enter the ID of employee : ");
        employeeId = scanner.nextInt();             
        scanner.skip(Pattern.compile("[\r\n]{2}")); 
        return employeeId;
    }

    /**
     * This method updates employee details based on user's choice.
     */
    private void updateEmployee() {
        int choice = 0;
        boolean updateStatus = false;
        List<List<String>> addresses 
                 = new ArrayList<List<String>>();
        int projectId = 0; 
        int project = 0;
        EmployeeController controllerObj = new EmployeeController();
        ArrayList<String> newAddress = new ArrayList<String>();
        String newName = "";
        String newDesignation = "";
        double newSalary = 0.0;
        Date newDob = null;
        long newPhoneNumber = 0;
        int addressId = 0;
        int employeeId = getEmployeeId();
        if (controllerObj.checkEmployeeID(employeeId, false)) {
            while(10 != choice) {
                System.out.println(updateQuestion);
                choice = scanner.nextInt();
                scanner.skip(Pattern.compile("[\r\n]{2}"));  
            
                switch (choice) {
                    case 1:
                        newName = getEmployee("name");   
                        break;
                    case 2:
                        newDesignation = getEmployee("designation");
                        break;
                    case 3:
                        newSalary = getEmployeeSalary();
                        break;
                    case 4:
                        newDob = getEmployeeDOB();
                        break;
                    case 5:
                        newPhoneNumber = getEmployeePhoneNumber();
                        break;
                    case 6:
                        newAddress = getAddress();
                        addresses.add(newAddress);
                        break;
                    case 7: 
                        addressId = singleEmployeeAddress(employeeId);
                        break;
                    case 8:
                        projectId = getProjectId(employeeId);
                        break;
                    case 9:
                        project = getProjectId(employeeId);
                        if(project != 0) {
                            controllerObj.unAssignProject(project, employeeId);
                        }
                        break;
                    case 10:
                        updateStatus = controllerObj.updateEmployee(employeeId, newName,
                                newDesignation, newSalary, newDob, newPhoneNumber, addresses, addressId, projectId);
                        if(updateStatus) {
                            System.out.println("\nUpdation was sucessfull\n");
                        } else {
                            System.out.println("\nUpdation was not sucessfull\n");
                        }
                        EmployeeOptions();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter again");
                   }
            }
        } else {
        System.out.println("Employee Id does not exist");
        }
    }
        
    /**
     * This method displays single employee addresses 
     * @return address id corresponding to user's selection
     * @param employeeId id of employee
     * @return address id.
     */
    private int singleEmployeeAddress(int employeeId) {
        int addressId = 0;
        int updateOption;                  // choice of address entered by user
        int addressNumber = 1;
        int[] addressIdArray;
        EmployeeController controllerObj = new EmployeeController();
        Map<Integer, String> employeeAddressDetails = 
                new TreeMap<Integer, String>(); 
        if(!((controllerObj.singleEmployeeAddress(employeeId)).isEmpty())){
        employeeAddressDetails = 
                controllerObj.singleEmployeeAddress(employeeId);
        System.out.println("Enter the address option you want to "
                           + " update e.g. 1, 2, etc");
        addressIdArray = new int[employeeAddressDetails.size() + 1];
    
        for( Map.Entry<Integer, String> addresses 
                : employeeAddressDetails.entrySet()){
             System.out.println(addressNumber + ".  " + addresses.getValue());
             addressIdArray[addressNumber] = addresses.getKey();
             addressNumber++;
        }          
        updateOption = scanner.nextInt();   
        scanner.skip(Pattern.compile("[\r\n]{2}"));    
        addressId = addressIdArray[updateOption];
       } else {
       System.out.println("There are no addresses for this employee id");
       }
       return addressId;
    }

    /**
     * This method displays one employee details.
     */  
    private void displaySingleEmployee() {
        EmployeeController controllerObj = new EmployeeController();
        int employeeID = getEmployeeId();                 
        if(controllerObj.checkEmployeeID(employeeID, false)) {
            System.out.println(controllerObj.displaySingleEmployee(employeeID));
            System.out.println("----------------End------------------");
        } else {
            System.out.println("Employee ID does not exist");
        }
    }

   /**
    * This method displays all employees details.
    */
    private boolean displayAllEmployees(boolean isDeleted) {
        boolean displayAllStatus = false;
        EmployeeController controllerObj = new EmployeeController();
        String employeeDetails = 
                controllerObj.displayAllEmployees(isDeleted);
        if(employeeDetails != null) {
            displayAllStatus = true;
            System.out.println(employeeDetails);
            System.out.println("--------------End of list---------------");
        } else {
          System.out.println("No employee records to display");
        }
        return displayAllStatus;
    }

    /**
     * This method gets and validates phone number.
     */
    private long getEmployeePhoneNumber() {
        EmployeeController controllerObj = new EmployeeController();
        String phoneNumber = getEmployee("phone number");
        long mobileNumber = 
                controllerObj.checkEmployeePhoneNumber(phoneNumber);
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
    private double getEmployeeSalary() {
        EmployeeController controllerObj = new EmployeeController();
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
    private Date getEmployeeDOB() {
        EmployeeController controllerObj = new EmployeeController();
        String dob = getEmployee("date of birth (yyyy-MM-dd)");
        Date date = controllerObj.checkEmployeeDOB(dob);
        while(null == date) {
            System.out.println("Please enter date of birth in right "
                               + "format yyyy-MM-dd");
            dob = getEmployee("date of birth (yyyy-MM-dd)");
            date = controllerObj.checkEmployeeDOB(dob);
        } 
        return date;
    }

    private int getProjectId(int employeeId) {
        int projectId = 0;
        String option = "";
        List<Integer> projectIds = new ArrayList<Integer>();
                System.out.print("Enter the id of the project  :  ");
                projectId = scanner.nextInt();
                scanner.skip(Pattern.compile("[\r\n]{2}")); 
                if(!controllerObj.checkProjectId(projectId)) {
                    System.out.print("Project id does not exist");
                    projectId = 0;
                } 
        return projectId;
    }

    private boolean displayAllProjects(boolean isDeleted) {
        boolean displayAllStatus = false;
        String projectDetails = 
                controllerObj.displayAllProjects(isDeleted);
        if(projectDetails != "") {
            displayAllStatus = true;
            System.out.println(projectDetails);
            System.out.println("--------------End of list---------------");
        } else {
          System.out.println("No Projetcs records to display");
        }
        return displayAllStatus;
    }         
        

   /**
    * This method displays options that can be performed with employee Database.
    */
    public void EmployeeOptions() {
        String option = "y"; 
        int choice = 0;

        while (7 != choice) {
            System.out.println(employeeOptionsQuestion);
            choice = scanner.nextInt();
            scanner.skip(Pattern.compile("[\n\r]{2}"));          

            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    displaySingleEmployee();  
                    break;   
                case 3:
                    displayAllEmployees(false);
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    if(displayAllEmployees(true)) {
                        restoreEmployee();
                    }
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }
        }
    }

}