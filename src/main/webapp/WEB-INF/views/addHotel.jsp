<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/dataForm.css" type="text/css"/>
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/hotelsManagement.js"></script>
    <title>Add Hotel</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>

<div id="wrapper">

    <div class="header">
        <span>Для добавления отеля заполните все необходимые данные</span>
    </div>

    <form name="addHotel" action="" method="post" class="data-form">

        <div class="content">

            <label> Название
                <input id="name" name="name" type="text" class="input"/>
            </label>

            <label> Описание
                <textarea id="description" name="description" style="height: 150px" class="input"></textarea>
            </label>

            <label> Количество звезд
                <select id="stars">
                    <option selected="selected" value="0">Выберите количество звезд</option>
                    <c:forEach begin="1" end="5" varStatus="loop">
                        <option value="${loop.current}">${loop.current}</option>
                    </c:forEach>
                </select>
            </label>

            <label> Город
                <select id="city">
                    <option selected="selected" value="0">Выберите город</option>
                    <%--@elvariable id="cities" type="java.util.List"--%>
                    <c:forEach items="${cities}" var="city">
                        <option value="${city.cityId}">${city.name}</option>
                    </c:forEach>
                </select>
            </label>

            <label> Информация о номерах</label>
            <table id="rooms" class="hor-minimalist-a">
                <thead>
                <tr>
                    <th>Тип номера</th>
                    <th>Количество номеров</th>
                    <th>Количество гостей</th>
                    <th>Цена за ночь</th>
                </tr>
                </thead>
                <tbody>
                <%--@elvariable id="roomTypes" type="java.util.List"--%>
                <c:forEach items="${roomTypes}" var="roomType">
                    <tr id="${roomType.roomTypeId}">
                        <td><c:out value="${roomType.description}"/></td>
                        <td>
                            <input type="text" name="roomsQuantity" pattern="\d*" value="" title=""/>
                        </td>
                        <td>
                            <input type="text" name="numberOfGuests" pattern="\d*" value="" title=""/>
                        </td>
                        <td>
                            <input type="text" name="pricePerNight" pattern="\d*" value="" title=""/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div style="padding-top: 8px;  padding-bottom: 8px; height: 22px"><span id="errorText"></span></div>

            <div style="overflow: auto;">
                <input type="button" name="Cancel" value="Отмена" class="input"
                       onclick="window.location='/hotels/management'"/>
                <input type="button" name="addHotel" value="Сохранить" class="input" onclick="addNewHotel()"/>
            </div>

        </div>

    </form>

</div>
</body>
</html>
