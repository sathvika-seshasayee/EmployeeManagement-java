import java.sql.SQLException;

import com.ideas2it.employeemanagement.view.EmployeeView;

/**
 * Performs create, read, update, delete operations on Employee Details 
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
public class EmployeeManagement { 
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        EmployeeView viewObj = new EmployeeView();
        viewObj.EmployeeOptions();
    }
}