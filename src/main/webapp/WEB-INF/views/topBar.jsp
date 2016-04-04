<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/main.css" type="text/css"/>
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
            <a href="<c:url value="/users/editUserProfile"/>">
                <strong>профиль</strong>
            </a>
        </span>
        <span class="right">
            <a href="<c:url value="/booking/management"/>">
                <strong>заявки на бронирование</strong>
            </a>
        </span>
        <c:if test="${sessionScope.user.getHotelOwner()}">
            <span class="right">
                <a href="<c:url value="/hotels/management"/>">
                    <strong>управление отелями</strong>
                </a>
            </span>
        </c:if>
    </c:if>
    <span class="left" style="padding-left: 30px">
        <a href="<c:url value="/"/>">
            <strong>На главную</strong>
        </a>
    </span>
</div>
</body>
</html>