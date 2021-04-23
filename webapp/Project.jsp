<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<head>
<title>Employee Management System</title>

<style>
.button {
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  background-color: #008CBA;
}

</style>
</head>

<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='Index.jsp'">Home</button>
<div align="center">
    <h3 style = "text-align:center"> Project </h3>
   
            <button onclick="document.location.href='createProject.jsp'">Create Project</button>
            
            <button onclick="document.location.href='projectController?action=displayProjects'">Display Active Projects</button>
            
            <br>
            <br>
            
            <button onclick="document.location.href='projectController?action=displayProjects&isDeleted=true'">Restore Project</button>
           
</div>  
</body>
</html>