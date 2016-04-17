<%--@elvariable id="bookingRequestInformation" type="java.util.Map"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RequestManagement</title>
    <link rel="stylesheet" href="../../resources/styles/main.css">
</head>
<body>
<jsp:include page="topBar.jsp"/>
<p style="margin-top: 40px"></p>

<div class="requestManagement">

    <div class="requestManagement-number">
        Заявка на бронирование № ${bookingRequestInformation.get('bookingRequestNumber')}
        на сумму ${bookingRequestInformation.get('requestValue')}
    </div>

    <div class="requestManagement-info-label">
        Период:
        <div class="requestManagement-info-content">
            с ${bookingRequestInformation.get('arrivalDate')}
            по ${bookingRequestInformation.get('departureDate')}
            (ночей: ${bookingRequestInformation.get('nightsQuantity')})
        </div>
    </div>

    <div class="requestManagement-bottom-border"></div>

    <div class="requestManagement-info-label">
        Номера:
        <br>
        <c:forEach items="${bookingRequestInformation.get('roomsInformation')}" var="roomInformation">
            <div class="requestManagement-info-content">
                    ${roomInformation.get('roomTypeDescription')}: ${roomInformation.get('roomsQuantity')} шт.
                <br>
            </div>
        </c:forEach>
    </div>

    <div class="requestManagement-bottom-border"></div>

    <div class="requestManagement-info-label">
        Автор заявки: <br>

        <div class="requestManagement-info-content">
            Имя: ${bookingRequestInformation.get('guestName')}
        </div>
        <div class="requestManagement-info-content">
            Email: ${bookingRequestInformation.get('guestEmail')}
        </div>
        <div class="requestManagement-info-content">
            Номер телефона: ${bookingRequestInformation.get('guestPhoneNumber')}
        </div>
    </div>

</div>


</body>
</html>
