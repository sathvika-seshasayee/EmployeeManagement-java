<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display Single Project</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
</head>
<style>
table {
  width: 80%;
}
th, td {
  text-align: center;
  padding: 8px;
}
tr {background-color: #FFFFFF;}
</style>
<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='Index.jsp'">Home</button>
<button onclick= "document.location.href='Project.jsp'">Project Home</button>
<div align="center">

    <h3>Project Details</h3>
<button onclick = "document.location.href='projectController?action=displayProjectForUpdate&projectId=${projectDetails.get(0)}'">Update</button>
<button onclick = "document.location.href='deleteProject.jsp?id=${project.get(0)}'">Delete</button>
<br>
<br>
<table border="1" width="90%">  
    <tr><th>Id</th><th>Name</th><th>Details</th><th>Client</th>  
    <th>Start Date</th><th>End Date</th>
 
<tr>
  <c:forEach items = "${projectDetails}" var = "project">
      <td>${project} </td> 
</c:forEach>

</tr>
</table>

</br>
</br>
<h3> Employees Assigned</h3>
<button onclick = "document.location.href='projectController?action=displayEmployeesForAssign&projectId=${projectDetails.get(0)}'">Assign/ Un Assign Employee</button>
 <br><br>
 <table border="1" width="90%">  
    <tr><th>Employee Id</th><th>Name</th><th>Designation</th><th>Salary</th>  
    <th>Date of Birth</th><th>Phone Number</th>
    </tr> 

<c:forEach  items = "${employeeDetails}" var = "employee"> 
     <tr>
       <td>${employee.get(0)}</td> 
       <td>${employee.get(1)}</td> 
       <td>${employee.get(2)}</td> 
       <td>${employee.get(3)}</td> 
       <td>${employee.get(4)}</td> 
       <td>${employee.get(5)}</td> 
    </tr>
</c:forEach>
</table>

</div>
</body>
</html>