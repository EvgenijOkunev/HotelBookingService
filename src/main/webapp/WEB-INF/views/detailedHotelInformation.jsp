<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="freeRooms" type="java.util.List"--%>
<%--@elvariable id="hotel" type="com.geekhub.model.Hotel"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/main.css" type="text/css"/>
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
    <link rel="stylesheet" href="../../resources/styles/jquery-ui.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script src="../../resources/datepicker.js"></script>
    <script src="../../resources/bookingProcessing.js"></script>
    <script type="text/javascript" src="../../resources/jquery.mask.js"></script>
    <title>Detailed information</title>
</head>

<script>
    $(function () {
        var arrivalDate = $("#arrivalDate");
        arrivalDate.datepicker({
            showOtherMonths: false,
            selectOtherMonths: true,
            monthNames: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"],
            dayNamesMin: ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"],
            minDate: 0,
            dateFormat: "dd.mm.yy",
            onClose: function () {
                checkDates('arrivalDate');
                processBookingRequestParameters();
            }
        });
        var queryDate = '${arrivalDateString}';
        var dateParts = queryDate.match(/(\d+)/g);
        var realDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
        arrivalDate.datepicker("setDate", realDate);
    });
</script>

<script>
    $(function () {
        var departureDate = $("#departureDate");
        departureDate.datepicker({
            showOtherMonths: false,
            selectOtherMonths: true,
            monthNames: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            dayNames: ["Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"],
            dayNamesMin: ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"],
            minDate: 1,
            dateFormat: "dd.mm.yy",
            onClose: function () {
                checkDates('departureDate');
                processBookingRequestParameters();
            }
        });
        var queryDate = '${departureDateString}';
        var dateParts = queryDate.match(/(\d+)/g);
        var realDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
        departureDate.datepicker("setDate", realDate);

    });
</script>

<body>

<jsp:include page="topBar.jsp"/>

<div class="searchResult-hotelInformation" style="margin-top: 60px">
    <div class="searchResult-hotelName">Отель "${hotel.getName()}"</div>
    <div class="searchResult-hotelStars">Количество звезд: ${hotel.getStars()}</div>
    <div class="searchResult-hotelCity">Город: ${hotel.getCity().getName()}</div>
    <div id="hotelFreeRooms" class="searchResult-hotelFreeRooms">Свободных номеров: ${freeRoomsQuantity}</div>
    <div class="searchResult-hotelDescription">${hotel.getStringDescription()}</div>
</div>

<div class="searchBar" style="margin-top: 20px">
    <span style="color: #9b302c;">Бронирование</span>
    <span>дата заезда:</span>
    <input type="text" id="arrivalDate" style="width: 100px" title="">
    <span>дата отъезда:</span>
    <input type="text" id="departureDate" style="width: 100px" title="">
    <input type="button" name="Search" value="Проверить доступность номеров" class="input"
           style="width: 220px; margin-left: 62px"
           onclick="getFreeRoomsInformation(${hotel.getHotelId()})"/>
</div>

<div class="searchResult-hotelInformation" style="margin-top: 10px;">
    <c:if test="${not empty freeRooms}">

        <div style="display:inline-block;">
            <table id="freeRoomsTable" class="hor-minimalist-c">
                <thead>
                <tr>
                    <th>Тип номера</th>
                    <th>Количество гостей</th>
                    <th>Свободно</th>
                    <th>Забронировать</th>
                    <th>Цена за ночь</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${freeRooms}" var="freeRoom">
                    <tr id="${freeRoom.get('roomTypeId')}">
                        <td><c:out value="${freeRoom.get('roomTypeName')}"/></td>
                        <td><c:out value="${freeRoom.get('numberOfGuests')}"/></td>
                        <td><c:out value="${freeRoom.get('roomQuantity')}"/></td>
                        <td style="padding: 0;">
                            <div style="display:inline-block; width: 50px; height: 34px">
                                <div style="display:inline-block; width: 30px; height: 34px">
                                    <input type="text" id="bookingQuantity${freeRoom.get('roomTypeId')}" pattern="\d*"
                                           title="" disabled/>
                                </div>
                                <div style="display:inline-block; width: 20px; height: 34px; float: right">
                                    <input type='button' id="${freeRoom.get('roomTypeId')}" name='add'
                                           onclick='increaseQty("bookingQuantity" + this.id);' value='+'/>
                                    <input type='button' id="${freeRoom.get('roomTypeId')}" name='subtract'
                                           onclick='subtractQty("bookingQuantity" + this.id);' value='-'/>
                                </div>
                            </div>
                        </td>
                        <td><c:out value="${freeRoom.get('pricePerNight')}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div id="bookingRequest">
            <div style="font-size: 15px">Заявка на бронирование</div>
            <div id="bookingRequestNights" style="font-size: 13px">Ночей: ${nightsQuantity}</div>
            <div id="bookingRequestRooms" style="font-size: 13px">Номеров: 0</div>
            <div id="bookingRequestValue" style="font-size: 13px">Стоимость: 0</div>
            <input type="button" id="${hotel.getHotelId()}" value="Оформить заявку"
                   onclick="bookingRequestConfirmation(this);"/>
        </div>

    </c:if>
</div>

<div onclick="show('none')" id="wrap"></div>

<div id="bookingRequestConfirm">
    <div class="bookingRequestLabel">Перед подтверждением, пожалуйста, проверьте корректность заявки</div>
    <div class="bookingRequestDetails">Отель: ${hotel.getName()}</div>
    <div class="bookingRequestDetails" id="requestConfirmPeriod"></div>
    <div class="bookingRequestDetails" id="requestConfirmRooms"></div>
    <div class="bookingRequestDetails" id="requestConfirmValue"
         style="border-bottom: 1px solid #3f3f66; padding-bottom: 10px; margin-bottom: 10px;"></div>

    <div class="bookingRequestDetails" style="width: 60px; display: inline-block; margin-top: 10px">Имя</div>
    <input id="userName" type="text" title="" value="${sessionScope.user.getFirstName()}"/>

    <div class="bookingRequestDetails" style="width: 60px; display: inline-block">Email</div>
    <input id="userEmail" type="text" title="" value="${sessionScope.user.getEmail()}"/>

    <div class="bookingRequestDetails" style="width: 60px; display: inline-block">Телефон</div>
    <input id="userPhoneNumber" type="text" title="" value="${sessionScope.user.getPhoneNumber()}"/>

    <div id="errorTextRequestConfirm"></div>

    <input id="requestConfirmButton" type="button" name="requestConfirm" value="Подтвердить заявку" class="input-button"
           style="width: 150px; margin-top: 15px; margin-right: 10px"/>
    <input type="button" name="Cancel" value="Отмена" class="input-button"
           style="width: 100px; margin-top: 15px; margin-right: 25px" onclick="show('none')"/>
</div>

</body>
</html>
