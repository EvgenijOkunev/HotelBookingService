<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../../resources/styles/formStyles.css" rel="stylesheet" type="text/css"/>
    <title>Registration</title>
</head>
<body>
<div id="wrapper">

    <form name="login-form" class="login-form" action="" method="post">

        <div class="header">
            <h1>Регистрация</h1>
            <span>Для входа на сайт введите ваши регистрационные данные</span>
        </div>

        <div class="content">
            <label>
                <input name="firstName" type="text" class="input" placeholder="Имя" onfocus="this.value=''"/>
            </label>
            <label>
                <input name="lastName" type="text" class="not-first-input" placeholder="Фамилия"/>
            </label>
            <label>
                <input name="email" type="text" class="not-first-input" placeholder="Электронная почта"/>
            </label>
            <label>
                <input name="password" type="password" class="not-first-input" placeholder="Пароль"/>
            </label>
            <label>
                <span>Владец Отеля</span>
                <input name="hotelOwner" type="checkbox" class="not-first-input"/>
            </label>
        </div>

        <div class="footer">
            <input type="submit" name="submit" value="Регистрация" class="register"/>
        </div>

    </form>
</div>
</body>
</html>
