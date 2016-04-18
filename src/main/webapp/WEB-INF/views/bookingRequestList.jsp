<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BookingRequestList</title>
    <link rel="stylesheet" href="../../resources/styles/main.css">
</head>
<body>
<jsp:include page="topBar.jsp"/>
<div style="margin-top: 60px"></div>

<%--@elvariable id="bookingRequestList" type="java.util.List"--%>
<c:forEach items="${bookingRequestList}" var="hotelRequests">
    <c:if test="${hotelRequests.get('bookingRequestsInfo').size() > 0}">
        <div class="hotelRequests">

            <div class="hotelRequests-hotelName">
                Отель ${hotelRequests.get("hotel").getName()}
            </div>

            <c:forEach items="${hotelRequests.get('bookingRequestsInfo')}" var="bookingRequestInfo">
                <c:if test="${allowManagement}">
                    <c:url var="requestManagement"
                           value="/hotelBookingRequestManagement?number=${bookingRequestInfo.get('bookingRequestNumber')}"/>
                </c:if>
                <c:if test="${ not allowManagement}">
                    <c:url var="requestManagement"
                           value="/userBookingRequestManagement?number=${bookingRequestInfo.get('bookingRequestNumber')}"/>
                </c:if>
                <div class="hotelRequestsList">
                    <a href="${requestManagement}">Заявка на бронирование
                        № ${bookingRequestInfo.get('bookingRequestNumber')} на
                        период с ${bookingRequestInfo.get('arrivalDate')} по ${bookingRequestInfo.get('departureDate')}
                    </a>
                </div>
                <c:if test="${bookingRequestInfo.get('status') eq 'подтверждена'}">
                    <c:set var="color" value="#2D7635"/>
                </c:if>
                <c:if test="${bookingRequestInfo.get('status') eq 'отклонена'}">
                    <c:set var="color" value="#c62122"/>
                </c:if>
                <c:if test="${bookingRequestInfo.get('status') eq 'ожидает обработки'}">
                    <c:set var="color" value="#3f3f66"/>
                </c:if>
                <div class="hotelRequestStatus" style="color: ${color}">
                        ${bookingRequestInfo.get('status')}
                </div>
            </c:forEach>

        </div>
    </c:if>
</c:forEach>

</body>
</html>
