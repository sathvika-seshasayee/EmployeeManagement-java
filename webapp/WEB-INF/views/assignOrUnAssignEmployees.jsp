<%@ pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assign/Un Assign Employees</title>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
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
 <button onclick= "document.location.href='Index.jsp'">Home</button>
 <button onclick= "document.location.href='Project.jsp'">Project Home</button>
	<div align="center">	   
		     <c:if test = "${employeeDetails != null}">
		     <h3> Assign/ Un Assign Employees</h3>
		    <form name = "form" action = "projectController" method = "post">
		    <input type = hidden name = "method_name" value = "assignOrUnAssignEmployees"> 
		    <input type = hidden name = "projectId" value = "${projectId}"> 
		    Project Id :  ${projectId}
		   
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
					<th>Employees</th>
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
						<c:set var = "flag" value = "false"/> 				
						<c:forEach items = "${assignedEmployeeIds}"  var = "employeeId">
						<c:if test = "${employeeId == employee.get(0)}">
						<td><input type="checkbox" name="employeeIds" checked = "checked" value="${employee.get(0)}"></td>
						<c:set var = "flag" value = "true"/> 	
				
						</c:if>
					    </c:forEach>					
                        <c:if test = "${flag == false}">
						<td><input type="checkbox" name="employeeIds"  value="${employee.get(0)}"></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			<input type="submit" value="Submit" >
		</form>
		</c:if>
	</div>

</body>
</html>