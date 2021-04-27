<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee</title><%@ page import="java.util.List" %>
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
<div align="center">
<c:if test = "${null != createStatus}">
<script>
alert("${createStatus}");
</script>
</c:if>
<c:if test = "${null != displayStatus}">
<script>
alert("${displayStatus}");
</script>
</c:if>
<form name = "createEmployee" action = "employeeController"  method = post>
    <h3>Employee Details</h3>
    <input type="hidden" name="method_name" value="createEmployee"/>
    <input type="hidden" name="employeeId" value="${employee.get(0)}"/>
    <table>
            <c:if test = "${null != employee}">
            <tr>
                <td>Employee Id :</td>
                <td>${employee.get(0)}</td>
            </tr>
            </c:if>
            <tr>
                <td>Name :</td>
                <td><input type="text" name="name" value = "${employee.get(1)}" required = "required field"/></td>
            </tr>
            <tr>
                <td>Designation :</td>
                <td><input type="text" name="designation"  value = "${employee.get(2)}" required = "required field"/></td>
            </tr>
 
            <tr>
                <td>Salary :</td>
                <td><input type="number" name="salary"  value = "${employee.get(3)}" required = "required field" placeholder = "numbers only"/></td>
            </tr>
            <tr>
                <td>Date of Birth (yyyy-MM-dd) :</td>
                <td><input type="date" name="dateOfBirth"  value = "${employee.get(4)}" required = "required field"/></td>
            </tr>
            <tr>
                <td>Mobile Number :</td>
                <td><input type="text" name="mobileNumber"  pattern="[6789][0-9]{9}" value = "${employee.get(5)}" required = "required field" placeholder = "10 digit mobile number"/></td>
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
                <td><input type="text" name="doorNoAndStreet" value = "${employee.get(7)}" required = "required field"/></td>
            </tr>
            <tr>
                <td>City :</td>
                <td><input type="text" name="city" size="30" value = "${employee.get(8)}" required = "required field"/></td>
            </tr>
            <tr>
                <td>State :</td>
                <td><input type="text" name="state" size="30" value = "${employee.get(9)}" required = "required field"/></td>
            </tr>
            <tr>
                <td>Country :</td>
                <td><input type="text" name="country" size="30" value = "${employee.get(10)}" required = "required field"/></td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td><input type="text" pattern="[0-9]{6}" name="pincode" size="30" value = "${employee.get(11)}" required = "required field" placeholder = "6 digits pin code"/></td>
            </tr>
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
                <td><input type="text" name="doorNoAndStreet1" value = "${employee.get(13)}" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>City :</td>
                <td><input type="text" name="city1" size="30" value = "${employee.get(14)}" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>State :</td>
                <td><input type="text" name="state1" size="30" value = "${employee.get(15)}" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>Country :</td>
                <td><input type="text" name="country1" size="30" value = "${employee.get(16)}" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td><input type="text" pattern="[0-9]{6}" name="pincode1" size="30" value = "${employee.get(17)}" placeholder = "option address : 6 digit pin code"/></td>
            </tr>
     
        </table>
        </c:if>
             <c:if test = "${employee.size() != 19}" >
              <table>
             <p>Address two: (optional)<p>
             <tr>
                <td>Address Type :</td>   
                <td>Temporary</td>
            </tr>
            <tr>
                <td>Apartment number, Street :</td>
                <td><input type="text" name="doorNoAndStreet1" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>City :</td>
                <td><input type="text" name="city1" size="30" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>State :</td>
                <td><input type="text" name="state1" size="30" placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>Country :</td>
                <td><input type="text" name="country1" size="30"  placeholder = "option address"/></td>
            </tr>
            <tr>
                <td>Pin Code :</td>
                <td><input type="text" pattern="[0-9]{6}" name="pincode1" size="30"  placeholder = "option address : 6 digit pin code"/></td>
            </tr>
        </table>
              </c:if>
            
        <input type="submit" value="submit" />
  
    </form>
    </div>

</body>
</html>