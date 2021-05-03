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
  width: 100%;
}
th, td {
  text-align: center;
  padding: 8px;
}
tr {background-color: #FFFFFF;}
</style>
<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='/'">Home</button>
<button onclick= "document.location.href='/employee'">Employee Home</button>
<div align="center">
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
<c:if test = "${null != updateStatus}">
<script>
alert("${updateStatus}");
window.location = window.location.href = 'displayAllEmployees';
</script>
</c:if>
<h1> Active Employee Details </h1>
<c:if test = "${employees.isEmpty()}">
<h3>No active employees to display.</h3>
</c:if>
<c:if test = "${!employees.isEmpty()}">
    <table>  
    <tr><th>Id</th><th>Name</th><th>Designation</th><th>Salary</th>  
    <th>Date of Birth</th><th>Phone Number</th><th>Addresses</th>
    <th>Projects</th><th>Edit</th><th>Delete</th><th>Assign Projects</th></tr> 
    <c:forEach  items = "${employees}" var = "employee"> 
     <tr>
       <td><button onclick= "document.location.href='/displayEmployee/${employee.getId()}'">${employee.getId()}</button></td>
       <td>${employee.getName()}</td> 
       <td>${employee.getDesignation()}</td> 
       <td>${employee.getSalary()}</td> 
       <td>${employee.getDob()}</td> 
       <td>${employee.getPhoneNumber()}</td> 
       <td><c:forEach items = "${employee.getAddresses()}" var = "address">
       ${address.toString()} <br>
       </c:forEach></td>
       <td><c:forEach items = "${employee.getProjects()}" var = "project">
       ${project.getId()} : ${project.getName()} <br>
       </c:forEach></td> 
       <td><button onclick= "document.location.href='/displayForUpdateEmployee/${employee.getId()}'">Update</button></td>
       <td><button onclick= "document.location.href='/deleteEmployee/${employee.getId()}'">Delete</button></td>
       <td><button onclick= "document.location.href='/displayForAssign/${employee.getId()}'">Assign/UnAssign</button></td>
    </tr>
</c:forEach>
</table>
</c:if>
</div>
<c:if test = "${null != deleted}">
<script>
alert("${deleted}");
document.location = document.location.href = '/displayAllEmployees/false';
</script>
</c:if>
<c:if test = "${null != assignStatus}">
<script>
alert("${assignStatus}");
document.location = document.location.href = '/displayAllEmployees/false';
</script>
</c:if>
</body>

</html>