<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit user</title>
</head>
<body>

<h1>Edit User</h1>
<c:url var="saveUrl" value="/users/edit?userId=${userAttribute.userId}"/>
<form:form modelAttribute="userAttribute" method="POST" action="${saveUrl}" onsubmit="return validate()">
    <table>
        <tr>
            <td><label>First Name:</label></td>
            <td><form:input id="userFirstName" path="firstName" onkeypress="clearWarningText('firstNameMessage')"/></td>
            <td><span style="color:red" id="firstNameMessage"></span></td>
        </tr>

        <tr>
            <td><label>Last Name</label></td>
            <td><form:input id="userLastName" path="lastName" onkeypress="clearWarningText('lastNameMessage')"/></td>
            <td><span style="color:red" id="lastNameMessage"></span></td>
        </tr>

        <tr>
            <td><label>Email</label></td>
            <td><form:input id="userEmail" path="email" onkeypress="clearWarningText('emailMessage')"/></td>
            <td><span style="color:red" id="emailMessage"></span></td>
        </tr>

        <tr>
            <td><label>Password</label></td>
            <td><form:input id="userPassword" path="password" onkeypress="clearWarningText('passwordMessage')"/></td>
            <td><span style="color:red" id="passwordMessage"></span></td>
        </tr>

        <tr>
            <td><label>Hotel owner</label></td>
            <td><form:checkbox id="userPassword" path="hotelOwner"/></td>
        </tr>
    </table>

    <input type="submit" value="Save"/>
</form:form>

<script type="text/javascript" src="../../resources/userAttributesValidation.js"></script>
</body>
</html>