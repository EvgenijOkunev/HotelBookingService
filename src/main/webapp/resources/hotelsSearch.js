function searchSuitableHotels() {
    var arrivalDate = $("#arrivalDate").datepicker("getDate");
    var departureDate = $("#departureDate").datepicker("getDate");
    var city = $("#city").val();

    $.ajax({
        type: 'GET',
        url: '/hotels/getSuitableHotels?arrivalDate=' + arrivalDate
        + '&departureDate=' + departureDate + '&city=' + city,
        dataType: 'json',
        async: true,
        success: function (result) {
            var tbody = document.getElementById('hotels-search-result').getElementsByTagName('TBODY')[0];
            var hotelsInformation = result["hotelsInformation"];
            for (var i = 0; i < hotelsInformation.length; i++) {
                var row = document.createElement("TR");
                tbody.appendChild(row);

                var td1 = document.createElement("TD");
                td1.style.width = '820px';

                var hotelName = document.createElement("SPAN");
                hotelName.innerHTML = 'Отель "' + hotelsInformation[i].hotelName + '"';
                hotelName.style.fontSize = '20px';
                var hotelNameDiv = document.createElement("DIV");
                hotelNameDiv.style.width = '400px';
                hotelNameDiv.style.display = 'inline-block';
                hotelNameDiv.appendChild(hotelName);
                td1.appendChild(hotelNameDiv);

                var hotelStars = document.createElement("SPAN");
                hotelStars.innerHTML = 'Количество звезд: ' + hotelsInformation[i].hotelStars;
                hotelStars.style.fontSize = '15px';
                var hotelStarsDiv = document.createElement("DIV");
                hotelStarsDiv.style.width = '200px';
                hotelStarsDiv.style.display = 'inline-block';
                hotelStarsDiv.appendChild(hotelStars);
                td1.appendChild(hotelStarsDiv);

                var hotelRooms = document.createElement("SPAN");
                hotelRooms.innerHTML = 'Свободных номеров: ' + hotelsInformation[i].roomsQuantity;
                hotelRooms.style.fontSize = '15px';
                hotelRooms.style.display = 'inline-block';
                hotelRooms.style.width = '200px';
                hotelRooms.style.textAlign = 'right';
                var hotelRoomsDiv = document.createElement("DIV");
                hotelRoomsDiv.style.width = '200px';
                hotelRoomsDiv.style.display = 'inline-block';
                hotelRoomsDiv.appendChild(hotelRooms);
                td1.appendChild(hotelRoomsDiv);

                var hotelDescription = document.createElement("SPAN");
                hotelDescription.innerHTML = hotelsInformation[i].hotelDescription;
                hotelDescription.style.fontSize = '13px';
                hotelDescription.style.marginLeft = '0';
                hotelDescription.style.whiteSpace = 'pre-line';
                var hotelDescriptionDiv = document.createElement("DIV");
                hotelDescriptionDiv.style.width = '800px';
                hotelDescriptionDiv.style.marginTop = '15px';
                hotelDescriptionDiv.style.paddingLeft = '15px';
                hotelDescriptionDiv.style.textAlign = 'justify';
                hotelDescriptionDiv.appendChild(hotelDescription);
                td1.appendChild(hotelDescriptionDiv);

                row.appendChild(td1);

                var td2 = document.createElement("TD");

                var freeRooms = document.createElement("SPAN");
                freeRooms.innerHTML = 'Детальная информация о свободных номерах: ';
                freeRooms.style.fontSize = '15px';
                var freeRoomsDiv = document.createElement("DIV");
                freeRoomsDiv.style.width = '400px';
                freeRoomsDiv.style.marginTop = '5px';
                freeRoomsDiv.appendChild(freeRooms);
                td2.appendChild(freeRoomsDiv);

                var rooms = hotelsInformation[i].rooms;

                if (rooms.length > 0) {
                    var roomsTable = document.createElement("TABLE");
                    roomsTable.style.width = 'auto';
                    roomsTable.style.marginTop = '10px';
                    roomsTable.style.marginLeft = '20px';
                    roomsTable.className = 'freeRoms';

                    var roomsTableHead = document.createElement("THEAD");
                    roomsTable.appendChild(roomsTableHead);

                    var roomsTableRow = document.createElement("TR");
                    roomsTableHead.appendChild(roomsTableRow);

                    var roomsTableHeader = document.createElement("TH");
                    roomsTableHeader.innerHTML = 'Тип номера';
                    roomsTableRow.appendChild(roomsTableHeader);

                    roomsTableHeader = document.createElement("TH");
                    roomsTableHeader.innerHTML = 'Количество';
                    roomsTableRow.appendChild(roomsTableHeader);

                    roomsTableHeader = document.createElement("TH");
                    roomsTableHeader.innerHTML = 'Цена';
                    roomsTableRow.appendChild(roomsTableHeader);


                    var roomsTableBody = document.createElement("TBODY");
                    roomsTable.appendChild(roomsTableBody);

                    for (var j = 0; j < rooms.length; j++) {
                        if (rooms[j].quantity > 0) {
                            roomsTableRow = document.createElement("TR");
                            roomsTableBody.appendChild(roomsTableRow);

                            var roomsTableData = document.createElement("TD");
                            roomsTableData.style.width = '80px';
                            roomsTableData.innerHTML = rooms[j].roomTypeName;
                            roomsTableRow.appendChild(roomsTableData);

                            roomsTableData = document.createElement("TD");
                            roomsTableData.style.width = '50px';
                            roomsTableData.innerHTML = rooms[j].quantity;
                            roomsTableRow.appendChild(roomsTableData);

                            roomsTableData = document.createElement("TD");
                            roomsTableData.style.width = '50px';
                            roomsTableData.innerHTML = rooms[j].price;
                            roomsTableRow.appendChild(roomsTableData);
                        }
                    }

                    td2.appendChild(roomsTable);
                }

                row.appendChild(td2);
            }
        }
    });

}
