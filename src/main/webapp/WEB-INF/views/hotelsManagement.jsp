<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
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
                <c:url var="deleteUrl" value="/hotels/delete?hotelId=${hotel.hotelId}"/>
                <tr>
                    <td><c:out value="${hotel.name}"/></td>
                    <td style="text-align: justify"><c:out
                            value="${hotel.getStringDescription()}"/></td>
                    <td><c:out value="${hotel.stars}"/></td>
                    <td style="min-width: 100px"><c:out value="${hotel.city.name}"/></td>
                    <td>
                        <a class="hor-minimalist-b-link" href="${editUrl}">Редактировать</a>
                        <p></p>
                        <a class="hor-minimalist-b-link" href="${deleteUrl}">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

</div>

</body>
</html>
