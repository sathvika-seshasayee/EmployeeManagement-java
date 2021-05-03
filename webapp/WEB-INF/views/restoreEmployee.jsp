<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Restore Employee</title>

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
	<button onclick= "document.location='/'">Home</button>
    <button onclick= "document.location='/employee'">Employee Home</button>
	<div align="center">
	<h3> Deleted Employee Details</h3>
	<c:if test = "${employees.isEmpty()}">
	 No deleted employees to Display.
	</c:if>
	<c:if test = "${!employees.isEmpty()}">
		 <br>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Designation</th>
				<th>Salary</th>
				<th>Date of Birth</th>
				<th>Phone Number</th>
				<th>Addresses</th>
				<th>Projects</th>
				<th>Restore</th>
			</tr>

			<c:forEach items="${employees}" var="employee">
				<tr>
					<td>${employee.getId()}</td>
					<td>${employee.getName()}</td>
					<td>${employee.getDesignation()}</td>
					<td>${employee.getSalary()}</td>
					<td>${employee.getDob()}</td>
					<td>${employee.getPhoneNumber()}</td>
					<td><c:forEach items = "${employee.getAddresses()}" var = "address">
                    ${address.toString()} <br>
                    </c:forEach></td>
                    <td><c:forEach items = "${employee.getProjects()}" var = "project">
                     ${project.getId()}  :  ${project.getName()}  <br> 
                     </c:forEach></td> 
					<td><button onclick ="document.location.href='/restoreEmployee/${employee.getId()}'">Restore</button></td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
	</div>
	<c:if test =  "${null != restoreEmployeeStatus}">
	<script>
	alert("${restoreEmployeeStatus}")
	window.location = window.location.href = '/displayAllEmployees/true';
	</script>
	</c:if>
</body>
</html>