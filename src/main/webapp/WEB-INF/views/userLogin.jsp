<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../../resources/styles/formStyles.css" rel="stylesheet" type="text/css"/>
    <title>Login</title>
</head>
<body>
<div id="wrapper">

    <form name="login-form" class="login-form" action="" method="post">

        <div class="header">
            <h1>Авторизация</h1>
            <span>Для входа на сайт введите ваши регистрационные данные</span>
        </div>

        <div class="content">
            <label>
                <input name="username" type="text" class="input username" placeholder="Email" onfocus="this.value=''"/>
            </label>
            <label>
                <input name="password" type="password" class="input password" placeholder="Пароль" onfocus="this.value=''"/>
            </label>
        </div>

        <div class="footer">
            <input type="submit" name="submit" value="ВОЙТИ" class="button"/>
        </div>

    </form>
</div>
</body>
</html>
