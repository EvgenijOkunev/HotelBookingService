<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
    <title>Users</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>
<h1 class="hor-minimalist-b-title">Users</h1>

<c:url var="addUrl" value="/users/add"/>

<c:if test="${not empty users}">
    <table class="hor-minimalist-b">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Password</th>
            <th>Hotel Owner</th>
            <th>Operations</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <c:url var="editUrl" value="/users/edit?userId=${user.userId}"/>
            <c:url var="deleteUrl" value="/users/delete?userId=${user.userId}"/>
            <tr>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td><c:if test="${user.hotelOwner}"> &#10004</c:if></td>
                <td><a class="hor-minimalist-b-link" href="${addUrl}">Add</a> |
                    <a class="hor-minimalist-b-link" href="${editUrl}">Edit</a> |
                    <a class="hor-minimalist-b-link" href="${deleteUrl}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty users}">
    There are currently no users in the list. <a href="${addUrl}">Add</a> a user.
</c:if>

</body>
</html>