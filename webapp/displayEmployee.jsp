<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Display Single Employee</title>
</head>
<style>
table {
  width: 70%;
}
th, td {
  text-align: center;
  padding: 8px;
}
tr {background-color: #FFFFFF;}
</style>
<body style="background-color:#f2d9d9">
<button onclick= "document.location.href='Index.jsp'">Home</button>
<button onclick= "document.location.href='Employee.jsp'">Employee Home</button>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
 <div align="center">
      <h3>Employee Details</h3> 
      
		<button onclick = "document.location.href='employeeController?employeeId=${employee.get(0)}&action=displayEmployeeforUpdate'">Update</button> 		
		<button onclick = "document.location.href='employeeController?employeeId=${employee.get(0)}&action=deleteEmployee'">Delete</button>
		<button onclick = "document.location.href='employeeController?employeeId=${employee.get(0)}&action=displayForAssign'">Assign/UnAssign</button>
		

		 <table cellpadding="3pt">
		    <tr>
                <td>Id :</td>
                <td>${employee.get(0)}</td>
            </tr>
            <tr>
                <td>Name :</td>
                <td>${employee.get(1)}</td>
            </tr>
            <tr>
                <td>Designation :</td>
                <td>${employee.get(2)}</td>
            </tr>
 
            <tr>
                <td>Salary :</td>
                <td>${employee.get(3)}</td>
            </tr>
            <tr>
                <td>Date of Birth (yyyy-MM-dd) :</td>
                <td>${employee.get(4)}</td>
            </tr>
            <tr>
                <td>Mobile Number :</td>
                <td>${employee.get(5)}</td>
            </tr>
            </table>
            <table>
            <p>Address Details<p>
            <tr>
                <td>Address Type :</td>   
                <td>Permanant</td>
            </tr>
            <tr>
                <td>Apartment number, Street :</td>
                <td>${employee.get(7)}</td>
            </tr>
            <tr>
                <td>City :</td>
                <td>${employee.get(8)}</td>
            </tr>
            <tr>
                <td>State :</td>
                <td>${employee.get(9)}</td>
            </tr>
            <tr>
                <td>Country :</td>
                <td>${employee.get(10)}</td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td>${employee.get(11)}</td>
            </tr>
            </table>
            <table>
             <c:if test = "${employee.size() == 13}" >
             <p>Project Details<p>
            <tr>
                <td>Projects Assigned :</td>
                <td>${employee.get(12)}</td>
            </tr>
            </c:if>
            </table>
           <br/>
              <c:if test = "${employee.size() == 19}" >
            <table>
             <p>Address two: (optional)<p>
             <tr>
                <td>Address Type :</td>   
                <td>${employee.get(12)}</td>
            </tr>
            <tr>
                <td>Apartment number, Street :</td>
                <td>${employee.get(13)}</td>
            </tr>
            <tr>
                <td>City :</td>
                <td>${employee.get(14)}</td>
            </tr>
            <tr>
                <td>State :</td>
                <td>${employee.get(15)}</td>
            </tr>
            <tr>
                <td>Country :</td>
                <td>${employee.get(16)}</td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td>${employee.get(17)}</td>
            </tr>
            
        </table>
        <table>
        <p>Project Details<p>
            <tr>
                <td>Projects Assigned    :</td>
                <td>${employee.get(18)}</td>
            </tr>
        </table>
        </c:if>	
        
</div>
</body>
</html>