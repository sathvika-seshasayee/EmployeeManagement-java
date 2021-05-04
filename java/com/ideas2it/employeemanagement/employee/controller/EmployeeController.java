package com.ideas2it.employeemanagement.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ideas2it.CustomException.EmployeeManagementException;
import com.ideas2it.CustomLogger.EmployeeManagementLogger;
import com.ideas2it.employeemanagement.employee.dao.EmployeeDao;
import com.ideas2it.employeemanagement.employee.model.Employee;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.project.model.Project;

/**
 * This class acts as servlet for employee.
 * 
 * @version 1.0 03 Mar 2021
 * @author Sathvika Seshasayee
 */
@Controller
public class EmployeeController {

    final EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeController.class);

    ApplicationContext container = new ClassPathXmlApplicationContext("classpath:beans.xml");
    private EmployeeService employeeService = container.getBean(EmployeeService.class);
//    
//    private EmployeeManagementLogger logger;
//
//    private EmployeeController(EmployeeManagementLogger logger) {
//        this.logger = logger;
//        logger.setLogger(EmployeeController.class);
//    }
    
    /**
     * Returns to dispatcher servlet
     * 
     * @return String index
     */
    @GetMapping("/")
    public String getIndexPage() {
        return "Index";
    }

    /**
     * Returns to dispatcher servlet
     * 
     * @return to String employee
     */
    @GetMapping("/employee")
    public String getEmployeePage() {
        return "Employee";
    }

    /**
     * Returns to new create employee page
     * 
     * @return to create form page
     */
    @GetMapping("/createEmployeeForm")
    public String createEmployeeForm(Model model) {

        Employee employee = new Employee();
        model.addAttribute(employee);
        return "createEmployee";
    }

    /**
     * Updates assigned projects for an employee
     * 
     * @param employeeId id of employee
     * @param projectId  ids of projects
     * @return ModelAndView object
     */
    @PostMapping("/updateAssignedProjects")
    private ModelAndView updateAssignedProjects(@RequestParam int employeeId,
            @RequestParam(required = false) String[] projectId) {
        List<Integer> projectIds = new ArrayList<Integer>();
        ModelAndView modelAndView = new ModelAndView();

        try {
        if (null != projectId) {
            for (String project : projectId) {
                    projectIds.add(Integer.parseInt(project));
            }
        }
            employeeService.updateAssignedProjects(employeeId, projectIds);
            modelAndView.addObject("assignStatus",
                    "Assigned projects updated for employee Id " + employeeId + " sucessfully");
        } catch (EmployeeManagementException | NumberFormatException e) {
            logger.logError(e);
            logger.logError("LOGGING TESTING");
            modelAndView.addObject("assignStatus", e.getMessage());
        }
        modelAndView.setViewName("displayAllEmployees");
        return modelAndView;
    }

    /**
     * Displays employee details for assigning projects
     * 
     * @param employeeId id of employee
     * @return ModelAndView object
     */
    @GetMapping("/displayForAssign/{employeeId}")
    private ModelAndView displayForAssign(@PathVariable int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Project> allProjects = employeeService.getAllProjects(false);
            List<String> assignedProjects = employeeService.getProjectsAssigned(employeeId);
            if (!allProjects.isEmpty()) {
                modelAndView.addObject("projects", allProjects);
                modelAndView.addObject("assignedProjects", assignedProjects);
            } else {
                modelAndView.addObject("displayStatus", "No active projects present.");
            }
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("displayStatus", e.getMessage());
        }
        modelAndView.setViewName("assignProject");
        return modelAndView;
    }

    /**
     * Restores an employee
     * 
     * @param employeeId id of employee
     * @return ModelAndView object
     */
    @GetMapping("/restoreEmployee/{employeeId}")
    public ModelAndView restoreEmployee(@PathVariable int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = displayActiveEmployees(true);
        try {
            employeeService.restoreEmployee(employeeId);
            modelAndView.addObject("restoreEmployeeStatus", "Employee Id " + employeeId + " restored sucessfully.");
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("restoreEmployeeStatus", e.getMessage());
        }
        return modelAndView;
    }

    /**
     * Soft deletes an employee
     * 
     * @param employeeId id of employee
     * @return ModelAndView object
     */
    @GetMapping("/deleteEmployee/{employeeId}")
    public ModelAndView deleteEmployee(@PathVariable int employeeId) {
        String deleteStatus = "";
        try {
            employeeService.deleteEmployee(employeeId);
            deleteStatus = "Employee Id " + employeeId + " deleted sucessfully";
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            deleteStatus = "Employee Id " + employeeId + " not deleted.";
        }
        return new ModelAndView("displayAllEmployees", "deleted", deleteStatus);
    }

    /**
     * Displays employees active/deleted
     * 
     * @param isDeleted delete status of employee
     * @return ModelAndView object
     */
    @GetMapping("/displayAllEmployees/{isDeleted}")
    public ModelAndView displayEmployees(@PathVariable boolean isDeleted) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("employees", employeeService.getAllEmployees(isDeleted));
        } catch (EmployeeManagementException e) {
            logger.logError(e);
        }
        if (isDeleted) {
            modelAndView.setViewName("restoreEmployee");
        } else {
            modelAndView.setViewName("displayAllEmployees");
        }
        return modelAndView;
    }

    /**
     * Display one employee details for display only
     * 
     * @param employeeId id of employee
     * @return ModelAndView object
     */
    @GetMapping("/displayEmployee/{employeeId}")
    private ModelAndView displayEmployeeDetails(@PathVariable int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("employee", employeeService.getEmployee(employeeId));
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("displayStatus", e.getMessage());
        }
        modelAndView.setViewName("displayEmployee");
        return modelAndView;
    }

    /**
     * Create employee
     * 
     * @param employee   employee details
     * @param addAddress user choice to add or not add address
     * @return ModelAndView object
     */
    @RequestMapping("/createOrUpdateEmployee")
    private ModelAndView createEmployee(@ModelAttribute("employee") Employee employee,
            @RequestParam("addAddress") String addAddress) {
        System.out.println("INSIDE CREATE");
        System.out.println(employee);
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (0 != employee.getId()) {
                employeeService.updateEmployee(setNewAddresses(employee, addAddress));
                modelAndView.addObject("updateStatus", "Employee Id " + employee.getId() + " updated Sucessfully.");
                modelAndView = displayActiveEmployees(false);
            } else {
                int employeeId = employeeService.insertEmployee(setNewAddresses(employee, addAddress));
                modelAndView.addObject("createStatus", "Employee Id " + employeeId + " created Sucessfully.");
                modelAndView.setViewName("displayEmployee");
            }
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("createStatus", "Employee not created or updated.");
        }
        return modelAndView;
    }

    /**
     * Sets address to an employee
     * 
     * @param employee
     * @param addAddress
     * @return ModelAndView object
     */
    private Employee setNewAddresses(Employee employee, String addAddress) {
        try {
            employee = employeeService.setNewAddresses(employee, addAddress);
        } catch (EmployeeManagementException e) {
            logger.logError(e);
        }
        return employee;
    }

    /**
     * Display employee for update.
     * 
     * @param employeeId
     * @return ModelAndView object
     */
    @GetMapping("/displayForUpdateEmployee/{employeeId}")
    private ModelAndView displayEmployeeforUpdate(@PathVariable int employeeId) {
        System.out.println("INSIDE DISPLAY FOR UPDATE");
        Employee employee = new Employee();
        ModelAndView modelAndView = new ModelAndView();
        try {
            employee = employeeService.getEmployee(employeeId);
            modelAndView.addObject("employee", employee);
            modelAndView.addObject("permanantAddress", (employee.getAddresses()).get(0));
            if (2 == employee.getAddresses().size()) {
                modelAndView.addObject("temporaryAddress", employee.getAddresses().get(1));
            }
            modelAndView.addObject("projects", employee.getProjects());
        } catch (EmployeeManagementException e) {
            logger.logError(e);
            modelAndView.addObject("displayStatus", "Employee Id " + employeeId + " could not be displayed.");
        }
        modelAndView.setViewName("createEmployee");
        return modelAndView;
    }

    /**
     * Displays active employees
     * 
     * @param isDeleted
     * @return ModelAndView object
     */
    public ModelAndView displayActiveEmployees(boolean isDeleted) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("employees", employeeService.getAllEmployees(isDeleted));
        } catch (EmployeeManagementException e) {
            logger.logError(e);
        }
        if (true == isDeleted) {
            modelAndView.setViewName("restoreEmployee");
        } else {
            modelAndView.setViewName("displayAllEmployees");
        }
        return modelAndView;
    }
}