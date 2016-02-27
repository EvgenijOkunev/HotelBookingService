<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Users</title>
</head>
<body>

<h1>Users</h1>

<p>You have edited a user with id ${id} at <%= new java.util.Date() %>
</p>

<c:url var="mainUrl" value="/users/show-all"/>
<p>Return to <a href="${mainUrl}">Users List</a></p>

</body>
</html>