<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../../resources/styles/loginForm.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/usersValidation.js"></script>
    <script type="text/javascript" src="../../resources/jquery.mask.js"></script>
    <title>Login</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>
<div id="wrapper">

    <form name="login-form" class="login-form" action="" method="post" onsubmit="loginValidate(); return false">

        <div class="header">
            <h1>Авторизация</h1>
            <p>Для входа введите ваши регистрационные данные</p>
        </div>

        <div class="content">
            <label>
                <input id = "email" name="email" type="text" class="input" placeholder="Email"/>
            </label>
            <p></p>
            <label>
                <input id = "password" name="password" type="password" class="input" placeholder="Пароль"/>
            </label>
            <div style="height: 22px"><span id="errorTextUserManagement"></span></div>
        </div>

        <div class="footer">
            <input type="submit" name="login" value="Войти" class="button"/>
        </div>

    </form>
</div>
</body>
</html>
