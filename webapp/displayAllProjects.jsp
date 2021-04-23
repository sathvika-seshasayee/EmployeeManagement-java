<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<style>
table {
  width: 95%;
}
th, td {
  text-align: center;
  padding: 8px;
}
tr {background-color: #FFFFFF;}
</style>
<meta charset="ISO-8859-1">
<title>Active Projects  Details</title>
</head>
<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='Index.jsp'">Home</button>
<button onclick= "document.location.href='Project.jsp'">Project Home</button>
<div align="center">

<c:if test = "${null != projects }">
<h2> Active Project Details </h2>
<br/>
    <table>  
    <tr><th>Id</th><th>Name</th><th>Details</th><th>Client</th>  
    <th>Start Date</th><th>End Date</th>
    <th>Employees </th><th>Edit</th><th>Delete</th><th>Assign Employees</th></tr> 


    <c:forEach  items = "${projects}" var = "project"> 
     <tr>
       <td><button onclick = "document.location.href = 'projectController?action=displayProject&projectId=${project.get(0)}'">${project.get(0)}</button></td> 
       <td>${project.get(1)}</td> 
       <td>${project.get(2)}</td> 
       <td>${project.get(3)}</td> 
       <td>${project.get(4)}</td> 
       <td>${project.get(5)}</td> 
       <td>${project.get(6)}</td> 
       <td><button onclick = "document.location.href='projectController?action=displayProjectForUpdate&projectId=${project.get(0)}'">Update </button>
       <td><button onclick = "document.location.href='projectController?action=deleteProject&projectId=${project.get(0)}'">Delete</button></td>
       <td><button onclick = "document.location.href='projectController?action=displayEmployeesForAssign&projectId=${project.get(0)}'">Assign Employees</button></td>
    </tr>
</c:forEach>
</table>
</c:if>
</div>
<c:if test = "${null != deleteProjectStatus}">
<script>
alert("${deleteProjectStatus}")
// function autoRefresh() {
    window.location = window.location.href = 'projectController?action=displayProjects';
// }
// setInterval('autoRefresh()', 3);
</script>
</c:if>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}")
</script>
</c:if>
<c:if test = "${null != updateStatus}">
<script>
alert("${updateStatus}");
window.location = window.location.href = 'projectController?action=displayProjects';
</script>
</c:if>
<c:if test = "${null != assignOrUnAssignStatus}">
<script>
alert("${assignOrUnAssignStatus}");
window.location = window.location.href = 'projectController?action=displayProjects';
</script>
</c:if>
</body>

</html>