<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project</title>
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
<br><br>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
<c:if test = "${null != createStatus}">
<script>
alert("${createStatus}");
</script>
</c:if>

<c:if test = "${null == updateStatus}">
<form action = "projectController" method = post>
   
    <h3>Project Details</h3>

    <input type="hidden" name="method_name" value="createProject"/>
    <input type="hidden" name="projectId" value="${project.get(0)}"/>
    <table>
            <c:if test = "${null != project}">
            <tr>
                <td>Project Id :</td>
                <td>${project.get(0)}</td>
            </tr>
            </c:if>
            <tr>
                <td>Name :</td>
                <td><input type="text" name="name"  value = "${project.get(1)}" required/></td>
            </tr>
            <tr>
                <td>Details :</td>
                <td><input type="text" name="details"  value = "${project.get(2)}" required/></td>
            </tr>
            <tr>
                <td>Client :</td>
                <td><input type="text" name="client"  value = "${project.get(3)}" required/></td>
            </tr>
            <tr>
                <td>Start Date :</td>
                <td><input type="date" name="startDate"  value = "${project.get(4)}" required/></td>
            </tr>
             <tr>
                <td>End Date :</td>
                <td><input type="date" name="endDate"  value = "${project.get(5)}" required/></td>
            </tr>
            </table>
      <input type="submit" value="submit" />
    </form>
 </c:if>    
    </div>

</body>
</html>