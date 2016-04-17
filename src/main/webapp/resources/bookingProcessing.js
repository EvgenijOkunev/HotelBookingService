window.onload = function () {
    resetBookingQuantity();
};

function increaseQty(elementId) {
    var element = document.getElementById(elementId);
    if (parseInt($(element).closest('tr')[0].cells[2].innerHTML) >= parseInt(element.value) + 1) {
        element.value++;
        processBookingRequestParameters();
    }
}

function subtractQty(elementId) {
    if (document.getElementById(elementId).value - 1 >= 0) {
        document.getElementById(elementId).value--;
        processBookingRequestParameters();
    }
}

function processBookingRequestParameters() {

    var arrivalDate = $("#arrivalDate").datepicker("getDate");
    var departureDate = $("#departureDate").datepicker("getDate");
    if (arrivalDate == null || departureDate == null) {
        return;
    }

    var nightsQty = getDaysQty(arrivalDate, departureDate);
    var roomsQty = 0;
    var value = 0;

    var elements = $(":input[id^='bookingQuantity']").get();
    for (var i = 0; i < elements.length; i++) {
        var thisRoomQty = parseInt(elements[i].value);
        roomsQty += thisRoomQty;
        value += thisRoomQty * nightsQty * parseInt($(elements[i]).closest('tr')[0].cells[4].innerHTML);
    }

    document.getElementById('bookingRequestNights').innerHTML = "Ночей: " + nightsQty;
    document.getElementById('bookingRequestRooms').innerHTML = "Номеров: " + roomsQty;
    document.getElementById('bookingRequestValue').innerHTML = "Стоимость: " + value;

}

function getFreeRoomsInformation(hotelId) {

    var arrivalDate = $("#arrivalDate").datepicker("getDate");
    var departureDate = $("#departureDate").datepicker("getDate");

    $.ajax({
        type: 'GET',
        url: '/hotels/getFreeRoomsInformation?arrivalDate=' + arrivalDate
        + '&departureDate=' + departureDate + '&hotelId=' + hotelId,
        dataType: 'json',
        async: true,
        success: function (result) {

            document.getElementById('hotelFreeRooms').innerHTML = 'Свободных номеров: ' + result.freeRoomsQuantity;

            if (result.roomsInformation.size == 0) {
                document.getElementById('freeRoomsTable').style.display = 'none';
                document.getElementById('bookingRequest').style.display = 'none';
            }
            else {

                var freeRoomsTable = document.getElementById('freeRoomsTable');
                freeRoomsTable.style.display = true;
                document.getElementById('bookingRequest').style.display = 'block';

                $("#freeRoomsTable").find("tr:gt(0)").remove();

                var roomsTableBody = freeRoomsTable.getElementsByTagName('TBODY')[0];
                var roomsInformation = result.roomsInformation;
                for (var i = 0; i < roomsInformation.length; i++) {

                    var roomsTableRow = document.createElement("TR");
                    roomsTableRow.id = roomsInformation[i].roomTypeId;
                    roomsTableBody.appendChild(roomsTableRow);

                    var roomsTableData = document.createElement("TD");
                    roomsTableData.innerHTML = roomsInformation[i].roomTypeName;
                    roomsTableRow.appendChild(roomsTableData);

                    roomsTableData = document.createElement("TD");
                    roomsTableData.innerHTML = roomsInformation[i].numberOfGuests;
                    roomsTableRow.appendChild(roomsTableData);

                    roomsTableData = document.createElement("TD");
                    roomsTableData.innerHTML = roomsInformation[i].roomQuantity;
                    roomsTableRow.appendChild(roomsTableData);

                    roomsTableData = document.createElement("TD");
                    roomsTableData.style.padding = '0';
                    roomsTableRow.appendChild(roomsTableData);

                    var mainDiv = document.createElement("DIV");
                    mainDiv.style.display = 'inline-block';
                    mainDiv.style.width = '50px';
                    mainDiv.style.height = '34px';
                    roomsTableData.appendChild(mainDiv);

                    var inputDiv = document.createElement("DIV");
                    inputDiv.style.display = 'inline-block';
                    inputDiv.style.width = '30px';
                    inputDiv.style.height = '34px';
                    mainDiv.appendChild(inputDiv);

                    var input = document.createElement("INPUT");
                    input.type = 'text';
                    input.id = 'bookingQuantity' + roomsInformation[i].roomTypeId;
                    input.pattern = '\d*';
                    input.disabled = true;
                    inputDiv.appendChild(input);

                    var buttonsDiv = document.createElement("DIV");
                    buttonsDiv.style.display = 'inline-block';
                    buttonsDiv.style.width = '20px';
                    buttonsDiv.style.height = '34px';
                    buttonsDiv.style.float = 'right';
                    mainDiv.appendChild(buttonsDiv);

                    var increaseInput = document.createElement("INPUT");
                    increaseInput.type = 'button';
                    increaseInput.id = roomsInformation[i].roomTypeId;
                    increaseInput.value = '+';
                    increaseInput.onclick = function () {
                        increaseQty('bookingQuantity' + this.id)
                    };
                    buttonsDiv.appendChild(increaseInput);

                    var subtractInput = document.createElement("INPUT");
                    subtractInput.type = 'button';
                    subtractInput.id = roomsInformation[i].roomTypeId;
                    subtractInput.value = '-';
                    subtractInput.onclick =
                        function () {
                            subtractQty('bookingQuantity' + this.id)
                        };
                    buttonsDiv.appendChild(subtractInput);

                    roomsTableData = document.createElement("TD");
                    roomsTableData.innerHTML = roomsInformation[i].pricePerNight;
                    roomsTableRow.appendChild(roomsTableData);

                }

                resetBookingQuantity();
                processBookingRequestParameters();
            }

        }

    });

}

function getDaysQty(firstDate, secondDate) {
    var oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
    return Math.round(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
}

function checkDates(dateChanged) {

    var arrivalDateElement = $("#arrivalDate");
    var departureDateElement = $("#departureDate");
    var arrivalDate = arrivalDateElement.datepicker("getDate");
    var departureDate = departureDateElement.datepicker("getDate");
    var dateTmp;

    if (arrivalDate >= departureDate) {
        if (dateChanged == 'arrivalDate') {
            dateTmp = arrivalDate;
            dateTmp.setDate(dateTmp.getDate() + 1);
            departureDateElement.datepicker('setDate', dateTmp);
        }
        else {
            dateTmp = departureDate;
            dateTmp.setDate(dateTmp.getDate() - 1);
            arrivalDateElement.datepicker('setDate', dateTmp);
        }
    }

}

function resetBookingQuantity() {
    var elements = $(":input[id^='bookingQuantity']").get();
    for (var i = 0; i < elements.length; i++) {
        elements[i].value = 0
    }
}

function show(state) {
    document.getElementById('bookingRequestConfirm').style.display = state;
    document.getElementById('wrap').style.display = state;
}

function bookingRequestConfirmation(object) {

    document.getElementById('requestConfirmButton').onclick = function () {
        processBookingRequest(object.id);
    };

    var arrivalDate = $("#arrivalDate").datepicker("getDate");
    var departureDate = $("#departureDate").datepicker("getDate");
    var options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };

    var requestConfirmRooms = document.getElementById('requestConfirmRooms');
    requestConfirmRooms.innerHTML = document.getElementById('bookingRequestRooms').innerHTML;
    document.getElementById('requestConfirmPeriod').innerHTML = 'Период: ' + arrivalDate.toLocaleString("ru", options) + ' - ' + departureDate.toLocaleString("ru", options);
    document.getElementById('requestConfirmValue').innerHTML = document.getElementById('bookingRequestValue').innerHTML;
    document.getElementById('errorTextRequestConfirm').innerHTML = '';

    if (requestConfirmRooms.innerHTML == 'Номеров: 0') {
        requestConfirmRooms.style.color = '#ea3428';
    }
    else {
        requestConfirmRooms.style.color = '#3f3f66';
    }

    show('block');
}

function processBookingRequest(hotelId) {

    if (checkRequestFieldsContent()) {
        $.ajax({
            type: 'POST',
            url: '/processBookingRequest',
            data: prepareBookingRequestParameters(hotelId),
            dataType: 'json',
            async: true,
            success: function () {
                show('none');
                processBookingRequestParameters();
            },
            error: function () {
                document.getElementById('errorTextRequestConfirm').innerHTML = 'Извините, но на данный момент нет необходимого количества свободных номеров';
            }
        });
    }

}

function prepareBookingRequestParameters(hotelId) {

    var rooms = [];
    var elements = $(":input[id^='bookingQuantity']").get();
    for (var i = 0; i < elements.length; i++) {
        if (parseInt(elements[i].value) > 0) {
            rooms.push({
                roomTypeId: $(elements[i]).closest('tr')[0].id,
                roomsQuantity: elements[i].value
            });
        }
    }

    return {
        hotelId: hotelId,
        arrivalDate: $("#arrivalDate").datepicker("getDate"),
        departureDate: $("#departureDate").datepicker("getDate"),
        userName: document.getElementById('userName').value,
        userEmail: document.getElementById('userEmail').value,
        userPhoneNumber: document.getElementById('userPhoneNumber').value,
        rooms: JSON.stringify(rooms)
    }

}

function checkRequestFieldsContent() {

    var userName = document.getElementById('userName').value;
    var userEmail = document.getElementById('userEmail').value;
    var userPhoneNumber = document.getElementById('userPhoneNumber').value;
    var requestConfirmRooms = document.getElementById('requestConfirmRooms');
    var errorText = document.getElementById('errorTextRequestConfirm');

    if (userName.length == 0 || userEmail.length == 0 || userPhoneNumber.length == 0) {
        errorText.innerHTML = 'Для оформления заявки необходимо заполнить все поля';
        return false;
    }

    if (requestConfirmRooms.innerHTML == 'Номеров: 0') {
        errorText.innerHTML = 'Для оформления заявки необходимо забронировать хотя бы один номер';
        return false;
    }

    return true;

}

jQuery(function ($) {
    $("#userPhoneNumber").mask("+99 (999) 999-99-99");
});