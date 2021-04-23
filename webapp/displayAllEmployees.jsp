<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta charset="ISO-8859-1">
<title>Active Employee Details</title>
</head>
<style>
table {
  width: 90%;
}
th, td {
  text-align: center;
  padding: 8px;
}
tr {background-color: #FFFFFF;}
</style>
<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='Index.jsp'">Home</button>
 <button onclick= "document.location.href='Employee.jsp'">Employee Home</button>
<div align="center">
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
<c:if test = "${null != updateStatus}">
<script>
alert("${updateStatus}");
window.location = window.location.href = 'employeeController?action=displayEmployees&isDeleted=false';
</script>
</c:if>
<c:if test = "${null != employeeDetails}">
<h1> Active Employee Details </h1>
    <table>  
    <tr><th>Id</th><th>Name</th><th>Designation</th><th>Salary</th>  
    <th>Date of Birth</th><th>Phone Number</th><th>Addresses</th>
    <th>Projects</th><th>Edit</th><th>Delete</th><th>Assign Projects</th></tr> 


    <c:forEach  items = "${employeeDetails}" var = "employee"> 
     <tr>
       <td><button onclick= "document.location.href='employeeController?employeeId=${employee.get(0)}&action=displayEmployeeDetails'">${employee.get(0)}</button></td>
       <td>${employee.get(1)}</td> 
       <td>${employee.get(2)}</td> 
       <td>${employee.get(3)}</td> 
       <td>${employee.get(4)}</td> 
       <td>${employee.get(5)}</td> 
       <td>${employee.get(6)}</td> 
       <td>${employee.get(7)}</td> 
       <td><button onclick= "document.location.href='employeeController?employeeId=${employee.get(0)}&action=displayEmployeeforUpdate'">Update</button></td>
       <td><button onclick= "document.location.href='employeeController?employeeId=${employee.get(0)}&action=deleteEmployee'">Delete</button></td>
       <td><button onclick= "document.location.href='employeeController?employeeId=${employee.get(0)}&action=displayForAssign'">Assign/UnAssign</button></td>
    </tr>
</c:forEach>
</table>
</c:if>
</div>
<c:if test = "${null != deleted}">
<script>
alert("${deleted}");
window.location = window.location.href = 'employeeController?action=displayEmployees&isDeleted=false';
</script>
</c:if>
<c:if test = "${null != assignStatus}">
<script>
alert("${assignStatus}");
window.location = window.location.href = 'employeeController?action=displayEmployees&isDeleted=false';
</script>
</c:if>
</body>

</html>