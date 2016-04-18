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
<div style="margin-top: 60px"></div>

<div class="requestManagement">

    <c:set var='bookingRequestNumber' value="${bookingRequestInformation.get('bookingRequestNumber')}"/>
    <div class="requestManagement-number">
        Заявка на бронирование № ${bookingRequestNumber}
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
        <c:forEach items="${bookingRequestInformation.get('roomsInformation')}" var="roomInformation">
            <div class="requestManagement-info-content">
                    ${roomInformation.get('roomTypeDescription')}: ${roomInformation.get('roomsQuantity')} шт.
                <br>
            </div>
        </c:forEach>
    </div>

    <div class="requestManagement-bottom-border"></div>

    <div class="requestManagement-info-label">
        Автор заявки:
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

    <div class="requestManagement-bottom-border"></div>

    <%--@elvariable id="allowManagement" type="java.lang.Boolean"--%>
    <c:if test="${bookingRequestInformation.get('accepted')}">
        <div class="requestManagement-info-label">
            Статус заявки:
            <div class="requestManagement-info-content" style="color: #2D7635">
                подтверждена
            </div>
            <c:if test="${allowManagement}">
                <button onclick="window.location='/hotelBookingRequestList'" style="margin-left: 140px">Назад</button>
                <button onclick="window.location='/updateRequestStatus?number=${bookingRequestNumber}&accepted=false'">
                    Отклонить
                </button>
            </c:if>
            <c:if test="${not allowManagement}">
                <button onclick="window.location='/userBookingRequestList'" style="margin-left: 40px">Назад</button>
            </c:if>
        </div>
    </c:if>
    <c:if test="${bookingRequestInformation.get('rejected')}">
        <div class="requestManagement-info-label">
            Статус заявки:
            <div class="requestManagement-info-content" style="color: #c62122">
                отклонена
            </div>
            <c:if test="${allowManagement}">
                <button onclick="window.location='/hotelBookingRequestList'" style="margin-left: 140px">Назад</button>
                <button onclick="window.location='/updateRequestStatus?number=${bookingRequestNumber}&accepted=true'">
                    Подтвердить
                </button>
            </c:if>
            <c:if test="${not allowManagement}">
                <button onclick="window.location='/userBookingRequestList'" style="margin-left: 40px">Назад</button>
            </c:if>
        </div>
    </c:if>
    <c:if test="${not bookingRequestInformation.get('accepted') and not bookingRequestInformation.get('rejected')}">
        <div class="requestManagement-info-label">
            Статус заявки:
            <div class="requestManagement-info-content" style="color: #3f3f66">
                ожидает обработки
            </div>
            <c:if test="${allowManagement}">
                <button onclick="window.location='/hotelBookingRequestList'" style="margin-left: 40px">Назад</button>
                <button onclick="window.location='/updateRequestStatus?number=${bookingRequestNumber}&accepted=false'"
                        style="margin-left: 55px">
                    Отклонить
                </button>
                <button onclick="window.location='/updateRequestStatus?number=${bookingRequestNumber}&accepted=true'"
                        style="margin-left: 55px">
                    Подтвердить
                </button>
            </c:if>
            <c:if test="${not allowManagement}">
                <button onclick="window.location='/userBookingRequestList'" style="margin-left: 40px">Назад</button>
            </c:if>
        </div>
    </c:if>

</div>

</body>
</html>
