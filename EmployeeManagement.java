import java.util.Scanner;
import java.util.regex.Pattern;

import com.ideas2it.employeemanagement.employee.view.EmployeeView;
import com.ideas2it.employeemanagement.project.view.ProjectView;

/**
 * Performs create, read, update, delete operations on Employee Details 
 * 
 * @version 2.0 26 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeManagement { 
    static Scanner scanner = new Scanner(System.in);
    final static String optionsQuestion = "Do you want to look into \n1. Employee options"
                                              + "\n2. Project options \n3. Exit. ";
    public static void main(String[] args) {
        accessOptions();
    }

    private static void accessOptions() {
        EmployeeView viewObj = new EmployeeView();
        ProjectView projectViewObj = new ProjectView();
        System.out.println(optionsQuestion);
        int choice = scanner.nextInt();
        scanner.skip(Pattern.compile("[\r\n]{2}"));
 
        while(true) {     
            switch(choice) {
                case 1 :
                    viewObj.EmployeeOptions();
                    break;
                case 2 :
                    projectViewObj.ProjectOptions();
                    break;
                case 3 :
                    System.out.println("******Thank you******");
                    System.exit(0);
                default :
                    System.out.println("Invalid choice. Please try again");
            }
        }		
    }
}