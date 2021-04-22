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
	<button onclick="document.location.href='Index.jsp'">Home</button>
	 <button onclick= "document.location.href='Employee.jsp'">Employee Home</button>
	<div align="center">
	<c:if test = "${employeeDetails != null}">
		<h3> Deleted Employee Details</h3>
		 <br>
	
		<table border="1" width="90%">
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

			<c:forEach items="${employeeDetails}" var="employee">
				<tr>
					<td>${employee.get(0)}</td>
					<td>${employee.get(1)}</td>
					<td>${employee.get(2)}</td>
					<td>${employee.get(3)}</td>
					<td>${employee.get(4)}</td>
					<td>${employee.get(5)}</td>
					<td>${employee.get(6)}</td>
					<td>${employee.get(7)}</td>
					<td><button onclick = "document.location.href = 'employeeController?action=restoreEmployee&employeeId=${employee.get(0)}'">Restore</button></td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
	</div>
	<c:if test =  "${null != restoreEmployeeStatus}">
	<script>
	alert("${restoreEmployeeStatus}")
	</script>
	</c:if>
</body>
</html>