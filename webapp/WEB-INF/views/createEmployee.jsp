<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>Employee</title><%@ page import="java.util.List"%>
</head>

<style>
table {
	width: 70%;
}

th, td {
	text-align: center;
	padding: 8px;
}

tr {
	background-color: #FFFFFF;
}
</style>
<body style="background-color: #f2d9d9">
	<button onclick="document.location.href='/'">Home</button>
	<button onclick="document.location.href='/employee'">Employee Home</button>
	<div align="center">
		<form:form method="POST" action="/createOrUpdateEmployee" modelAttribute="employee">
			<h3>Employee Details</h3>
			<table>
				<c:if test="${0 != employee.id}">
					<tr>
						<td>Employee Id : </td>
						<td>${employee.getId()}</td>
					</tr>
					<form:hidden path="id" required="required field" value="${employee.getId()}"/>
				</c:if>
				<tr>
					<td><form:label path="name">Name : </form:label></td>
					<td><form:input path="name" required="required field"
							value="${employee.name}" /></td>
				</tr>
				<tr>
					<td><form:label path="designation">Designation : </form:label></td>
					<td><form:input path="designation" required="required field"
							value="${employee.getDesignation()}" /></td>
				</tr>
				<tr>
					<td><form:label path="salary">Salary : </form:label></td>
					<td><form:input path="salary" required="required field"
							value="${employee.getSalary()}" /></td>
				</tr>
				<tr>
					<td><form:label path="dob" type="date">Date of Birth (yyyy-MM-dd) :</form:label></td>
					<td><form:input path="dob" type="date"
							required="required field" value="${employee.getDob()}" /></td>
				</tr>
				<tr>
					<td><form:label path="phoneNumber">Mobile Number : </form:label></td>
					<td><form:input path="phoneNumber" required="required field"
							value="${employee.getPhoneNumber()}" pattern="[6789][0-9]{9}"/></td>
				</tr>
			</table>
			<p>Address Details</p>
			<table>
				<tr>
					<td>Address Type :</td> <td>Permanent</td>
				</tr>
				<form:hidden path="addresses[0].isPermanantAddress" value="true" />
				<form:hidden path="addresses[0].id"
					value="${permanantAddress.getId()}" />
				<tr>
					<td><form:label path="addresses[0].doorNoAndStreet">Apartment number and street :</form:label></td>
					<td><form:input path="addresses[0].doorNoAndStreet"
							id="doorNo" value="${permanantAddress.getDoorNoAndStreet()}"
							required="required field" /></td>
				</tr>
				<tr>
					<td><form:label path="addresses[0].city">City :</form:label></td>
					<td><form:input path="addresses[0].city"
							required="required field" value="${permanantAddress.getCity()}" /></td>
				</tr>
				<tr>
					<td><form:label path="addresses[0].state">State :</form:label></td>
					<td><form:input path="addresses[0].state"
							required="required field" value="${permanantAddress.getState()}" /></td>
				</tr>
				<tr>
					<td><form:label path="addresses[0].country">Country :</form:label></td>
					<td><form:input path="addresses[0].country"
							required="required field"
							value="${permanantAddress.getCountry()}" /></td>
				</tr>
				<tr>
					<td><form:label path="addresses[0].pincode">Pin code :</form:label></td>
					<td><form:input path="addresses[0].pincode"
							required="required field"
							value="${permanantAddress.getPincode()}"  pattern="[0-9]{6}"/></td>
				</tr>
			</table>
			<br>
			<c:if test="${null != temporaryAddress}">
		  <tr><td>  Is permanent address same as temporary :  </td>
		  <td><br> Yes :<input
					name="addAddress" type="radio" onClick = "removeRequired()" value="yes" required="required" /> <br>
		    No : <input name="addAddress" type="radio" onClick = "addRequired()" value="no" checked /></td> </tr>
			</c:if>
			<c:if test="${null == temporaryAddress}">
		<tr> <td> Is permanent address same as temporary : <td>
		  <td> <br>  Yes :<input name="addAddress" type="radio" value="yes" checked required="required" onClick = "removeRequired()" /> <br>
		             No : <input name="addAddress" type="radio" value="no" onClick = "addRequired()"/> </td></tr>
			</c:if>
			<div id="temporaryAddress">
				<table>
					<tr>
						<td>Address Type :</td> <td>Temporary</td>
					</tr>
					<form:hidden path="addresses[0].isPermanantAddress" value="false" />
					<form:hidden path="addresses[1].id"
						value="${temporaryAddress.getId()}" />

					<tr>
						<td><form:label path="addresses[1].doorNoAndStreet">Apartment number and street :</form:label></td>
						<td><form:input path="addresses[1].doorNoAndStreet"
								value="${temporaryAddress.getDoorNoAndStreet()}" id = "doorNoAndStreet"/></td>
					</tr>
					<tr>
						<td><form:label path="addresses[1].city">City :</form:label></td>
						<td><form:input path="addresses[1].city"
								value="${temporaryAddress.getCity()}"  id = "city"/></td>
					</tr>
					<tr>
						<td><form:label path="addresses[1].state">State :</form:label></td>
						<td><form:input path="addresses[1].state"
								value="${temporaryAddress.getState()}" id = "state" /></td>
					</tr>
					<tr>
						<td><form:label path="addresses[1].country">Country :</form:label></td>
						<td><form:input path="addresses[1].country"
								value="${temporaryAddress.getCountry()}"  id = "country"/></td>
					</tr>
					<tr>
						<td><form:label path="addresses[1].pincode">Pin code :</form:label></td>
						<td><form:input path="addresses[1].pincode"
								value="${temporaryAddress.getPincode()}"  id = "pincode" pattern="[0-9]{6}"/></td>
					</tr>
				</table>
			</div>
			<br>
			<input type="submit" value="Submit" />
		</form:form>
	</div>

	<c:if test="${null != createStatus}">
		<script>
			alert("${createStatus}");
		</script>
	</c:if>
	<c:if test="${null != displayStatus}">
		<script>
			alert("${displayStatus}");
		</script>
	</c:if>
	<c:if test="${null == temporaryAddress}">
		<script>
			$('#temporaryAddress').hide();
		</script>
	</c:if>
</body>

<script type="text/javascript">
	    function removeRequired() {
	    	document.getElementById("temporaryAddress").style.display ='none';
	    	document.getElementById("doorNoAndStreet").removeAttribute("required");
	    	document.getElementById("city").removeAttribute("required");
	    	document.getElementById("state").removeAttribute("required");
	    	document.getElementById("country").removeAttribute("required");
	    	document.getElementById("pincode").removeAttribute("required");
	    }
	    
	    function addRequired() {
	    	document.getElementById("temporaryAddress").style.display ='block';
	    	document.getElementById("doorNoAndStreet").setAttribute("required","required");
	    	document.getElementById("city").setAttribute("required","required");
	    	document.getElementById("state").setAttribute("required","required");
	    	document.getElementById("country").setAttribute("required","required");
	    	document.getElementById("pincode").setAttribute("required","required");
	    }
	</script>
</html>