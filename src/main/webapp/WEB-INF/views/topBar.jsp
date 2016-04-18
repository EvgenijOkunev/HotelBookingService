<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/main.css" type="text/css"/>
</head>
<body>
<div class="topBar">
    <ul class="menu">
        <c:if test="${empty sessionScope.user}">
            <li style="margin-right: 200px"><a href="/registration">Зарегистрироваться</a></li>
            <li style="margin-right: 30px"><a href="/login">Войти</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <li style="margin-right: 200px"><a>${sessionScope.user.getFirstName()}</a>
                <ul class="submenu">
                    <li><a href="/users/editUserProfile">Профиль</a></li>
                    <li><a href="/userBookingRequestList">Заявки на бронирование</a></li>
                    <li><a href="/logout">Выйти</a></li>
                </ul>
            </li>
        </c:if>
        <c:if test="${sessionScope.user.getHotelOwner()}">
            <li style="margin-right: 50px"><a>Мои отели</a>
                <ul class="submenu">
                    <li><a href="/hotels/management">Управление отелями</a></li>
                    <li><a href="/hotelBookingRequestList">Заявки на бронирование</a></li>
                    <li><a href=#>Свободные номера</a></li>
                </ul>
            </li>
        </c:if>
        <li style="float: left; margin-left: 100px"><a href="/">На главную</a></li>
    </ul>
</div>
</body>
</html>