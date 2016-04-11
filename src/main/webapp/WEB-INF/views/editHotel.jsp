<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/dataForm.css" type="text/css"/>
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/hotelsManagement.js"></script>
    <title>Edit Hotel</title>
</head>
<body>
<jsp:include page="topBar.jsp"/>

<div id="wrapper">

    <div class="header">
        <span>Измените необходимую информацию об отеле</span>
    </div>

    <form name="editHotel" action="" method="post" class="data-form">

        <%--@elvariable id="hotel" type="com.geekhub.model.Hotel"--%>
        <div class="content">

            <label> Название
                <input id="name" name="name" type="text" class="input" value="${hotel.name}"/>
            </label>

            <label> Описание
                <textarea id="description" name="description" style="height: 150px" class="input">${hotel.getStringDescription()}</textarea>
            </label>

            <label> Количество звезд
                <select id="stars">
                    <c:forEach begin="1" end="5" varStatus="loop">
                        <c:if test="${loop.current.toString() != hotel.stars.toString()}">
                            <option value="${loop.current}">${loop.current}</option>
                        </c:if>
                        <c:if test="${loop.current.toString() == hotel.stars.toString()}">
                            <option selected="selected" value="${loop.current}">${loop.current}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </label>


            <label> Город
                <select id="city">
                    <%--@elvariable id="cities" type="java.util.List"--%>
                    <c:forEach items="${cities}" var="city">
                        <c:if test="${city.cityId != hotel.city.cityId}">
                            <option value="${city.cityId}">${city.name}</option>
                        </c:if>
                        <c:if test="${city.cityId == hotel.city.cityId}">
                            <option selected="selected" value="${city.cityId}">${city.name}</option>
                        </c:if>
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
                <%--@elvariable id="rooms" type="java.util.List"--%>
                <c:forEach items="${rooms}" var="room">
                    <tr id="${room.get("roomsTypeId")}">
                        <td><c:out value="${room.get(\"roomsTypeName\")}"/></td>
                        <td><input type="text" name="roomsQuantity" pattern="\d*"
                                   value="${room.get("roomsQuantity")}" title=""/></td>
                        <td><input type="text" name="numberOfGuests" pattern="\d*"
                                   value="${room.get("numberOfGuests")}" title=""/></td>
                        <td><input type="text" name="pricePerNight" pattern="\d*"
                                   value="${room.get("pricePerNight")}" title=""/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div style="padding-top: 8px;  padding-bottom: 8px; height: 22px"><span id="errorTextHotelManagement"></span></div>

            <div style="overflow: auto;">
                <input type="button" name="Cancel" value="Отмена" class="input"
                       onclick="window.location='/hotels/management'"/>
                <input type="button" name="addHotel" value="Сохранить" class="input" onclick="editHotel_js('${hotel.hotelId.toString()}')"/>
            </div>

        </div>

    </form>

</div>
</body>
</html>
