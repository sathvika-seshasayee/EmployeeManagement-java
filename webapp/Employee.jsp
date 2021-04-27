<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<head>
<title>Employee Management System</title>
</head>

<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='Index.jsp'">Home</button>
<h3 style = "text-align:center">Employee </h3>
<div align="center">
   
               <button onclick = "document.location.href = 'createEmployee.jsp'">Create Employee</button>
               
               <button onclick = "document.location.href = 'employeeController?action=displayEmployees&isDeleted=false'">Display Active Employees</button>
               </br>
               </br> 
           
               <button onclick = "document.location.href='employeeController?action=displayEmployees&isDeleted=true'">Restore Employee</button>
            
</div>         
</body>


</html>