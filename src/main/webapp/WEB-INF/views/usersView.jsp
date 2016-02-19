<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>Persons</h1>

<c:url var="addUrl" value="/users/add"/>
<table style="border: 1px solid; width: 500px; text-align:center">
    <thead style="background:#fcf">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th colspan="3"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <c:url var="editUrl" value="/users/edit?id=${user.id}"/>
        <c:url var="deleteUrl" value="/users/delete?id=${user.id}"/>
        <tr>
            <td><c:out value="${user.firstName}"/></td>
            <td><c:out value="${user.lastName}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><a href="${editUrl}">Edit</a></td>
            <td><a href="${deleteUrl}">Delete</a></td>
            <td><a href="${addUrl}">Add</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty users}">
    There are currently no users in the list. <a href="${addUrl}">Add</a> a user.
</c:if>

</body>
</html>