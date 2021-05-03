<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Assign Project</title>
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
<button onclick= "document.location='/'">Home</button>
 <button onclick= "document.location='/employee'">Employee Home</button>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
	<c:if test="${projects != null}">
	<div align="center">
		<br /> <br />
		<form action="/updateAssignedProjects" method="post">
		<input type = "hidden" name = "employeeId" value = "${employeeId}">
			
		    Employee Id :   ${employeeId}
			 <br>
			 <br>
			
				<table border="1" >
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Details</th>
						<th>Client</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Employees Assigned</th>
						<th>Project assign/un assign</th>
					</tr>

					<c:forEach items="${projects}" var="project">
						<tr>
							<td>${project.getId()}</td>
							<td>${project.getName()}</td>
							<td>${project.getDetails()}</td>
							<td>${project.getClient()}</td>
							<td>${project.getStartDate()}</td>
							<td>${project.getTargetDate()}</td>
							<td><c:forEach items = "${project.getEmployees()}" var = "employee">
							 ${employee.getId()} : ${employee.getName()} <br>
							</c:forEach></td>
							<c:set var = "flag" value = "false"/>
							<c:forEach items = "${assignedProjects}" var = "projectId">
							
							<c:if test = "${projectId == project.getId()}">
							<td><input type="checkbox" name="projectId"
								value="${project.getId()}" checked></td>
							<c:set var = "flag" value = "true"/>
							</c:if>
							
							</c:forEach>
							<c:if test = "${flag == false}">
							<td><input type="checkbox" name="projectId"
								value="${project.getId()}"></td>
							</c:if>
							
						</tr>
					</c:forEach>
				</table>			
			<input type="submit" value="Submit">
		</form>
	</div>
</c:if>

</body>
</html>