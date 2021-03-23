package com.ideas2it.employeemanagement.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.List;  
import java.util.Map;                                                 
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
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
    String updateQuestion = "\n1. Name "
                                + "\n2. Designation \n3. Salary "
                                + "\n4. Date of Birth \n5. Phone Number "
                                + "\n6. Add Address "
                                + "\n7. Exit\n" ;
    EmployeeController controllerObj = new EmployeeController();

   /**
    * This method displays options that can be performed in employee Database.
    * @throws ClassNotFoundException, SQLException.
    */
    public void EmployeeOptions() throws ClassNotFoundException, SQLException {
        
        String option = "y";
        String optionQuestion = "What do you want to do today with the"
                                + " Employee Database?\n1. Create "
                                + "\n2. Display one Emlpoyee details "
                                + "\n3. Display All Employee "
                                + " details \n4. Update \n5. Delete Employee"
                                + "\n6. Restore Employee \n7. Delete Address \n8. Exit\n";
        while (true) {
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
                    deleteAddress();
                    break;
                case 8:
                    System.out.println("*****Thank You*****\n\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again");
                    break;
            }
        }
    }

    /**
     * This method deleted one address of an employee
     */
    private void deleteAddress() throws ClassNotFoundException, 
                                          SQLException {
        boolean deleteStatus = false;
        int employeeId = getEmployeeId();
        if (controllerObj.checkEmployeeID(employeeId)) {
            int addressId = singleEmployeeAddress(employeeId, "deleted");
            if(0 == addressId){
                deleteStatus = false;
             } else {
                deleteStatus = 
                        controllerObj.deleteSingleAddress(addressId);
             }
        
            if(deleteStatus) {
                System.out.println("Address deleted sucessfully");
            } else {
                System.out.println("Address not deleted");
            }

        } else {
            System.out.println("Employee Id does not exist");
        }
       
        }

    /**
     * This method restores one employee
     */ 
    private void restoreEmployee() throws ClassNotFoundException, 
                                          SQLException {
        EmployeeController controllerObj = new EmployeeController();
        int employeeId = getEmployeeId();
        if (!controllerObj.checkEmployeeID(employeeId)) {
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
     * @throws ClassNotFoundException, SQLException.
     */
    private String getEmployee(String option) {
        System.out.print("Enter the " + option + " of the Employee  :  ");
        return scanner.nextLine();
    }

   /**
    * This method adds details of employee to Database.
    * @throws ClassNotFoundException, SQLException.
    */
    private void createEmployee() throws ClassNotFoundException, SQLException {     
        EmployeeController controllerObj = new EmployeeController();  
        ArrayList<ArrayList<String>> addresses = 
                new ArrayList<ArrayList<String>>();
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
     * @throws ClassNotFoundException, SQLException.
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
     * @throws ClassNotFoundException, SQLException.
     */
    private String getPinCode() {
        EmployeeController controllerObj = new EmployeeController();
        String pinCode = getEmployee("pin code of residence");
        return controllerObj.checkPinCode(pinCode) ? pinCode : getPinCode();
    }
         
   /**
    * This method deletes an employee from Database.
    * @throws ClassNotFoundException, SQLException.
    */
    private void deleteEmployee() throws ClassNotFoundException, SQLException {
        int employeeID = getEmployeeId();
        EmployeeController controllerObj = new EmployeeController();
        if (controllerObj.checkEmployeeID(employeeID)) {
            controllerObj.deleteEmployee(employeeID);
            System.out.println("Employee deleted sucessfully"); 
        } else {
            System.out.println("Employee ID does not exist"); 
        }
    }

    /**
     * This method gets employee ID is present in database
     * @throws ClassNotFoundException, SQLException.
     */
    private int getEmployeeId() throws ClassNotFoundException, SQLException {
        int employeeId;
        System.out.print("Enter the ID of employee : ");
        employeeId = scanner.nextInt();             
        scanner.skip(Pattern.compile("[\r\n]{2}")); 
        return employeeId;
    }

    /**
     * This method updates employee details based on user's choice.
     * @throws ClassNotFoundException, SQLException.
     */
    private void updateEmployee() throws ClassNotFoundException, SQLException {
        int choice = 0;
        int addressId; 
        boolean updateStatus = true;
        ArrayList<ArrayList<String>> addresses = 
                new ArrayList<ArrayList<String>>();
        EmployeeController controllerObj = new EmployeeController();
        ArrayList<String> newAddress = new ArrayList<String>();
        String newName = "";
        String newDesignation = "";
        double newSalary = 0.0;
        Date newDob = null;
        addresses = null;
        long newPhoneNumber = 0;
        int employeeId = getEmployeeId();
        if (controllerObj.checkEmployeeID(employeeId)) {
            while(true) {
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
                        newAddress = null;
                        newAddress = getAddress();
                       addresses.add(newAddress);
                        break;
                    case 7:
                        updateStatus = controllerObj.updateEmployee(employeeId, newName, newDesignation, newSalary, newDob, newPhoneNumber, addresses);
                        if(updateStatus) {
                            System.out.println("\nUpdation was sucessfull\n");
                        } else {
                            System.out.println("\nUpdation was not sucessfull\n");
                        }
                        EmployeeOptions();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter again");
                        break;
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
     * @throws ClassNotFoundException, SQLException.
     */
    private int singleEmployeeAddress(int employeeId, String option) throws 
            ClassNotFoundException, SQLException {
        int addressId = 0;
        int updateOption ;                  // choice of address entered by user
        int addressNumber = 1;
        int[] addressIdArray;
        EmployeeController controllerObj = new EmployeeController();
        Map<Integer, String> employeeAddressDetails = 
                new TreeMap<Integer, String>(); 
        if(!((controllerObj.singleEmployeeAddress(employeeId, option)).isEmpty())){
        employeeAddressDetails = 
                controllerObj.singleEmployeeAddress(employeeId, option);
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
        System.out.print(addressId);
       } else {
       System.out.println("There are no addresses for this employee id");
       }
       return addressId;
    }

    /**
     * This method displays one employee details.
     * @throws ClassNotFoundException, SQLException.
     */  
    private void displaySingleEmployee() throws ClassNotFoundException,
                                                SQLException {
        EmployeeController controllerObj = new EmployeeController();
        int employeeID = getEmployeeId();                 
        if(controllerObj.checkEmployeeID(employeeID)) {
            String employeeDetails = 
                    controllerObj.displaySingleEmployee(employeeID);
            System.out.println(employeeDetails);
            System.out.println("----------------End------------------");
        } else {
            System.out.println("Employee ID does not exist");
        }
    }

   /**
    * This method displays all employees details.
    * @throws ClassNotFoundException, SQLException.
    */
    private void displayAllEmployees(String option) throws 
            ClassNotFoundException, SQLException {
        EmployeeController controllerObj = new EmployeeController();
        ArrayList<String> employeeDetails = 
                controllerObj.displayAllEmployees(option);
        Iterator<String> employee = employeeDetails.iterator();
        while(employee.hasNext()) { 
            System.out.println(employee.next());
        }
        System.out.println("--------------End of list---------------");
    }

    /**
     * This method gets and validates phone number.
     * @throws ClassNotFoundException, SQLException.
     */
    private long getEmployeePhoneNumber() throws ClassNotFoundException, 
                                                 SQLException {
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
     * @throws ClassNotFoundException, SQLException.
     */   
    private double getEmployeeSalary() throws ClassNotFoundException, 
                                              SQLException {
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
     * @throws ClassNotFoundException, SQLException.
     */
    private Date getEmployeeDOB() throws ClassNotFoundException, SQLException {
        EmployeeController controllerObj = new EmployeeController();
        String dob = getEmployee("date of birth (yyyy-MM-dd)");
        Date date = controllerObj.checkEmployeeDOB(dob);
        while(null == controllerObj.checkEmployeeDOB(dob)) {
            System.out.println("Please enter date of birth in right "
                               + "format yyyy-MM-dd");
            dob = getEmployee("date of birth (yyyy-MM-dd)");
            date = controllerObj.checkEmployeeDOB(dob);
        } 
        return date;
    }
} 