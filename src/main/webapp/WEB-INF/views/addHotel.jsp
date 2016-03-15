<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/dataForm.css" type="text/css"/>
    <title>Add Hotel</title>
</head>
<body>
<form name="addHotel" action="" method="post" class="data-form">
    <div class="content">
        <label> Название
            <input id="name" name="name" type="text" class="input"/>
        </label>
        <label> Описание
            <input id="description" name="description" type="text" class="input"/>
        </label>
        <label> Количество звезд
            <select id="stars">
                <option selected = "selected">Выберите количество звезд</option>
                <c:forEach begin="1" end="5" varStatus="loop">
                    <option value="${loop.current}">${loop.current}</option>
                </c:forEach>
            </select>
        </label>
        <label> Город
            <select id="city">
                <option selected = "selected">Выберите город</option>
                <c:forEach items="${cities}" var="city">
                    <option value="${city.cityId}">${city.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
</form>
</body>
</html>
