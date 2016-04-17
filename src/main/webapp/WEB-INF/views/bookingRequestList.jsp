<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BookingRequestList</title>
    <link rel="stylesheet" href="../../resources/styles/main.css">
</head>
<body>
<jsp:include page="topBar.jsp"/>
<p style="margin-top: 40px"></p>

<%--@elvariable id="bookingRequestList" type="java.util.List"--%>
<c:forEach items="${bookingRequestList}" var="hotelRequests">
    <div class="hotelRequests">

        <div class="hotelRequests-hotelName">
            Отель ${hotelRequests.get("hotel").getName()}
        </div>

        <c:forEach items="${hotelRequests.get('bookingRequestsInfo')}" var="bookingRequestInfo">
            <c:url var="requestManagement"
                   value="/bookingRequestManagement?number=${bookingRequestInfo.get('bookingRequestNumber')}"/>
            <div class="hotelRequestsList">
                <a href="${requestManagement}">Заявка на бронирование
                    № ${bookingRequestInfo.get('bookingRequestNumber')} на
                    период с ${bookingRequestInfo.get('arrivalDate')} по ${bookingRequestInfo.get('departureDate')}
                </a>
            </div>
            <div class="hotelRequestStatus">
                    ${bookingRequestInfo.get('status')}
            </div>
        </c:forEach>

    </div>
</c:forEach>

</body>
</html>
