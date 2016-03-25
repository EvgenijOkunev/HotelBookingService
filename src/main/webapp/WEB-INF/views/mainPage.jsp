<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" href="../../resources/styles/main.css" type="text/css"/>
    <link rel="stylesheet" href="../../resources/styles/jquery-ui.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script type="text/javascript" src="../../resources/hotelsSearch.js"></script>
</head>
<body>
<jsp:include page="topBar.jsp"/>

<script>
    $(function () {
        $("#arrivalDate").datepicker({
            showOtherMonths: false,
            selectOtherMonths: true,
            monthNames: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"],
            dayNamesMin: ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"],
            minDate: 0,
            dateFormat: "dd.mm.yy"
        });
        $("#arrivalDate").datepicker("setDate", "0");

    });
</script>

<script>
    $(function () {
        $("#departureDate").datepicker({
            showOtherMonths: false,
            selectOtherMonths: true,
            monthNames: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"],
            dayNamesMin: ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"],
            minDate: 1,
            dateFormat: "dd.mm.yy"
        });
        $("#departureDate").datepicker("setDate", "+1");

    });
</script>

<div class="searchBar">
    <span>Дата заезда:</span>
    <input type="text" id="arrivalDate" style="width: 100px">
    <span>Дата отъезда:</span>
    <input type="text" id="departureDate" style="width: 100px">
    <span>Город:</span>
    <select id="city" style="width: 150px">
        <option selected="selected" value="0">Выберите город</option>
        <%--@elvariable id="cities" type="java.util.List"--%>
        <c:forEach items="${cities}" var="city">
            <option value="${city.cityId}">${city.name}</option>
        </c:forEach>
    </select>
    <input type="button" name="Search" value="Найти" class="input" onclick="searchSuitableHotels()"/>
</div>

</html>