<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../../resources/styles/loginForm.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/usersValidation.js"></script>
    <title>Login</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>
<div id="wrapper">

    <form name="login-form" class="login-form" action="" method="post">

        <div class="header">
            <h1>Авторизация</h1>
            <span>Для входа на сайт введите ваши регистрационные данные</span>
        </div>

        <div class="content">
            <label>
                <input id = "email" name="email" type="text" class="input" placeholder="Email"/>
            </label>
            <label>
                <input id = "password" name="password" type="password" class="input" placeholder="Пароль"/>
            </label>
            <div style="height: 22px"><span id="errorText"></span></div>
        </div>

        <div class="footer">
            <input type="button" name="login" value="ВОЙТИ" class="button" onclick="loginValidate()"/>
        </div>

    </form>
</div>
</body>
</html>
