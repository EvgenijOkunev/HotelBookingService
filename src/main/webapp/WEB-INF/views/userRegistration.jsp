<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../../resources/styles/registrationForm.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/usersValidation.js"></script>
    <script type="text/javascript" src="../../resources/jquery.mask.js"></script>
    <title>Registration</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>

<div id="wrapper">

    <form name="login-form" class="registration-form" action="" method="post">

        <div class="header">
            <h1>Регистрация</h1>

            <p>Для регистраци введите все необходимые данные</p>
        </div>

        <div class="content">
            <label>
                <input id="firstName" name="firstName" type="text" class="input" placeholder="Имя"/>
            </label>
            <label>
                <input id="lastName" name="lastName" type="text" class="input" placeholder="Фамилия"/>
            </label>
            <label>
                <input id="email" name="email" type="email" class="input" placeholder="Электронная почта"/>
            </label>
            <label>
                <input id="phoneNumber" name="phoneNumber" type="text" class="input" placeholder="Номер телефона"/>
            </label>
            <label>
                <input id="password" name="password" type="password" class="input" placeholder="Пароль"/>
            </label>
            <label>
                <input id="passwordConfirm" name="passwordConfirm" type="password" class="input"
                       placeholder="Подтвердите пароль"/>
            </label>

            <div style="float: right">
                <label id="hotelOwnerLabel"> Владец отеля
                    <input id="hotelOwner" name="hotelOwner" type="checkbox"/>
                </label>
            </div>
            <div style="clear: right; padding-top: 8px;  padding-bottom: 8px; height: 22px"><span id="errorText"></span>
            </div>
        </div>

        <div class="footer">
            <input type="button" name="registration" value="Регистрация" class="button"
                   onclick="registrationValidate()"/>
        </div>

    </form>
</div>
</body>
</html>
