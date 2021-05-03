<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Restore Project</title>
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
	<button onclick= "document.location.href='Project.jsp'">Project Home</button>
	<div align="center">
		<h3> Deleted Projects </h3>
		<br /> <br />
        <c:if test = "${null != projects}">

			<table border="1" width="90%">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Details</th>
					<th>Client</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Restore</th>
				</tr>


				<c:forEach items="${projects}" var="project">
					<tr>
						<td>${project.get(0)}</td>
						<td>${project.get(1)}</td>
						<td>${project.get(2)}</td>
						<td>${project.get(3)}</td>
						<td>${project.get(4)}</td>
						<td>${project.get(5)}</td>
						<td><button onclick = "document.location.href = 'projectController?action=restoreProject&projectId=${project.get(0)}'">Restore</button></td>
					</tr>
				</c:forEach>
			</table>
			</c:if>
	</div>
	<c:if test = "${null != restoreProjectStatus}">
	<script>
	alert("${restoreProjectStatus}")
	window.location = window.location.href = 'projectController?action=displayProjects&isDeleted=true';
	</script>
	</c:if>
</body>

</html>