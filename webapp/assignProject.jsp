<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
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
<button onclick="document.location.href='Index.jsp'">Home</button>
<button onclick= "document.location.href='Employee.jsp'">Employee Home</button>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
<c:if test = "${null != assignStatus}">
<script>
alert("${assignStatus}");
</script>
</c:if>
	<c:if test="${projects != null}">
	<div align="center">
		<br /> <br />
		<form action="employeeController" method="post">
		<input type = "hidden" name = "employeeId" value = "${employeeId}">
		<input type = "hidden" name = "method_name" value = "updateAssignedProjects">
			
		    Employee Id :   ${employeeId}
			 <br>
			 <br>
			
				<table border="1" width="90%">
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
							<td>${project.get(0)}</td>
							<td>${project.get(1)}</td>
							<td>${project.get(2)}</td>
							<td>${project.get(3)}</td>
							<td>${project.get(4)}</td>
							<td>${project.get(5)}</td>
							<td>${project.get(6)}</td>
							<c:set var = "flag" value = "false"/>
							<c:forEach items = "${assignedProjects}" var = "projectId">
							
							<c:if test = "${projectId == project.get(0)}">
							<td><input type="checkbox" name="projectId"
								value="${project.get(0)}" checked></td>
							<c:set var = "flag" value = "true"/>
							</c:if>
							
							</c:forEach>
							<c:if test = "${flag == false}">
							<td><input type="checkbox" name="projectId"
								value="${project.get(0)}"></td>
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