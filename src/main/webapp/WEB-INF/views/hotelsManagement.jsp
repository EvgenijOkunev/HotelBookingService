<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/hotelsManagement.js"></script>
    <title>Hotels Management</title>
</head>
<body>

<jsp:include page="topBar.jsp"/>

<div style="width: 90%">

    <div>
        <span class="hor-minimalist-b-title">Отели</span>
        <input type="button" name="addHotel" value="Добавить отель" class="input-button"
               onclick="window.location='/hotels/add'"/>
    </div>

    <%--@elvariable id="hotels" type="java.util.list"--%>
    <c:if test="${not empty hotels}">
        <table class="hor-minimalist-b">
            <thead>
            <tr>
                <th>Название</th>
                <th>Описание</th>
                <th>Количество звезд</th>
                <th>Город</th>
                <th>Операции</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${hotels}" var="hotel">
                <c:url var="editUrl" value="/hotels/edit?hotelId=${hotel.hotelId}"/>
                <c:url var="photoUrl" value="/photos/edit?hotelId=${hotel.hotelId}"/>
                <c:url var="deleteUrl" value="/hotels/delete?hotelId=${hotel.hotelId}"/>
                <tr>
                    <td><c:out value="${hotel.getName()}"/></td>
                    <td style="text-align: justify; white-space: pre-line;"><c:out
                            value="${hotel.getStringDescription()}"/></td>
                    <td><c:out value="${hotel.getStars()}"/></td>
                    <td style="min-width: 100px"><c:out value="${hotel.getCity().getName()}"/></td>
                    <td>
                        <a class="hor-minimalist-b-link" href="${editUrl}">Редактировать</a>

                        <p></p>
                        <a class="hor-minimalist-b-link" href="${photoUrl}">Фотографии</a>

                        <p></p>
                        <input id="${hotel.getHotelId()}" type="button" class="hor-minimalist-b-button" value="Удалить"
                               onclick="deleteHotel_js(this);"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

</div>

<div onclick="show('none')" id="wrap"></div>

<div id="deleteConfirm">
    <div id="deleteConfirmLabel">Вы действительно хотите удалить выбранный отель?</div>
    <input type="button" name="Cancel" value="Отмена" class="input-button"
           style="width: 100px; margin-top: 25px; margin-left: 48px; margin-right: 25px;"
           onclick="show('none')"/>
    <input id="deleteHotelButton" type="button" name="deleteHotel" value="Да, удалить" class="input-button"
           style="width: 100px; margin-top: 25px; margin-left: 15px; margin-right: 0"/>
</div>

</body>
</html>
