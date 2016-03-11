<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/main.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<div class="topBar">
    <c:if test="${empty sessionScope.user}">
        <span class="right">
            <a href="<c:url value="/registration"/>">
                <strong>зарегистрироваться</strong>
            </a>
        </span>
        <span class="right">
            <a href="<c:url value="/login"/>">
                <strong>войти</strong>
            </a>
        </span>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <span class="right">
            <a href="<c:url value="/logout"/>">
                <strong>выйти</strong>
            </a>
        </span>
        <span class="right">
            <a href="<c:url value="/"/>">
                <strong>${sessionScope.user.getFirstName()}</strong>
            </a>
        </span>

    </c:if>
</div>
</body>
</html>