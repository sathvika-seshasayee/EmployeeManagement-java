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
<button onclick= "document.location.href='/'">Home</button>
<button onclick= "document.location.href='/employee'">Employee Home</button>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
 <div align="center">
      <h3>Employee Details</h3> 
      
		<button onclick = "document.location.href='/displayForUpdateEmployee/${employee.getId()}'">Update</button> 		
		<button onclick = "document.location.href='/deleteEmployee/${employee.getId()}'">Delete</button>
		<button onclick = "document.location.href='/displayForAssign/${employee.getId()}'">Assign/UnAssign</button>
		
		 <table>
		    <tr>
                <td>Id :</td>
                <td>${employee.getId()}</td>
            </tr>
            <tr>
                <td>Name :</td>
                <td>${employee.getName()}</td>
            </tr>
            <tr>
                <td>Designation :</td>
                <td>${employee.getDesignation()}</td>
            </tr>
 
            <tr>
                <td>Salary :</td>
                <td>${employee.getSalary()}</td>
            </tr>
            <tr>
                <td>Date of Birth :</td>
                <td>${employee.getDob()}</td>
            </tr>
            <tr>
                <td>Mobile Number :</td>
                <td>${employee.getPhoneNumber()}</td>
            </tr>
            </table>
            <p>Address Details<p>
            <table>
            <tr>
                <td>Address Type :</td>   
                <td>Permanent</td>
            </tr>
            <tr>
                <td>Apartment number, Street :</td>
                <td>${employee.getAddresses().get(0).getDoorNoAndStreet()}</td>
            </tr>
            <tr>
                <td>City :</td>
                <td>${employee.getAddresses().get(0).getCity()}</td>
            </tr>
            <tr>
                <td>State :</td>
                <td>${employee.getAddresses().get(0).getState()}</td>
            </tr>
            <tr>
                <td>Country :</td>
                <td>${employee.getAddresses().get(0).getCountry()}</td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td>${employee.getAddresses().get(0).getPincode()}</td>
            </tr>
            </table>
            <table>
           
            </table>
           <br/>
              <c:if test = "${employee.getAddresses().size() == 2}">
              <p>Address two: (optional)<p>
            <table>
             <tr>
                <td>Address Type :</td>   
                <td>Temporary</td>
            </tr>
            <tr>
                <td>Apartment number, Street :</td>
                <td>${employee.getAddresses().get(1).getDoorNoAndStreet()}</td>
            </tr>
            <tr>
                <td>City :</td>
                <td>${employee.getAddresses().get(1).getCity()}</td>
            </tr>
            <tr>
                <td>State :</td>
                <td>${employee.getAddresses().get(1).getState()}</td>
            </tr>
            <tr>
                <td>Country :</td>
                <td>${employee.getAddresses().get(1).getCountry()}</td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td>${employee.getAddresses().get(1).getPincode()}</td>
            </tr>
            
        </table>
        </c:if>
       <c:if test = "${!employee.getProjects().isEmpty()}">
          <span>Project Details</span>
          <table>
            <tr>
                <td>Projects Assigned    :</td>
                <td><c:forEach items="${employee.getProjects()}"  var = "project">
                ${project.id} : ${project.name}<br>
                </c:forEach>
                </td>
            </tr>
        </table>
        </c:if>	      
</div>
</body>
<c:if test = "${null != createStatus}">
<script>
alert("${createStatus}");
</script>
</c:if>

</html>