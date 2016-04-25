<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <link href="../../resources/styles/registrationForm.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../../resources/jquery.js"></script>
    <script type="text/javascript" src="../../resources/usersValidation.js"></script>
    <script type="text/javascript" src="../../resources/jquery.mask.js"></script>
    <title>Профиль</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>

<div id="wrapper">

    <c:url var="saveUrl" value="/users/editUserProfile?userId=${userAttribute.userId}"/>
    <form:form modelAttribute="userAttribute" method="POST" action="${saveUrl}" class="registration-form"
               onsubmit="return userProfileValidate()">
        <div class="header">
            <h1>Ваш профиль</h1>

            <p>Здесь Вы можете отредактировать данные свего профиля</p>
        </div>

        <div class="content">
            <label>
                <form:input id="firstName" path="firstName" type="text" class="input" placeholder="Имя"/>
            </label>
            <label>
                <form:input id="lastName" path="lastName" type="text" class="input" placeholder="Фамилия"/>
            </label>
            <label>
                <form:input id="email" path="email" type="email" class="input" placeholder="Электронная почта"/>
            </label>
            <label>
                <form:input id="phoneNumber" path="phoneNumber" type="text" class="input" placeholder="Номер телефона"/>
            </label>
            <label>
                <form:input id="password" path="password" type="password" class="input" placeholder="Пароль"/>
            </label>
            <label>
                <form:input id="passwordConfirm" path="" type="password" class="input"
                            placeholder="Подтвердите пароль"/>
            </label>

            <div style="float: right">
                <label id="hotelOwnerLabel"> Владец отеля
                    <form:checkbox id="hotelOwner" path="hotelOwner"/>
                </label>
            </div>
            <div style="clear: right; padding-top: 8px;  padding-bottom: 8px; height: 22px"><span
                    id="errorTextUserManagement"></span>
            </div>
        </div>

        <div class="footer">
            <input type="submit" name="editProfile" value="Сохранить" class="button"/>
        </div>

    </form:form>

</div>

</body>
</html>