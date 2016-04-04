var searchResults = [];

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

            clearSearchResults();

            var mainBody = document.getElementById('mainBody');
            var hotelsInformation = result["hotelsInformation"];

            for (var i = 0; i < hotelsInformation.length; i++) {

                var hotelInformationDIV = document.createElement("DIV");
                hotelInformationDIV.className = 'searchResult';

                var hotelName = document.createElement("SPAN");
                hotelName.innerHTML = 'Отель "' + hotelsInformation[i].hotelName + '"';
                hotelName.style.fontSize = '20px';
                var hotelNameDiv = document.createElement("DIV");
                hotelNameDiv.style.width = '320px';
                hotelNameDiv.style.display = 'inline-block';
                hotelNameDiv.appendChild(hotelName);
                hotelInformationDIV.appendChild(hotelNameDiv);

                var hotelStars = document.createElement("SPAN");
                hotelStars.innerHTML = 'Количество звезд: ' + hotelsInformation[i].hotelStars;
                hotelStars.style.fontSize = '15px';
                var hotelStarsDiv = document.createElement("DIV");
                hotelStarsDiv.style.width = '190px';
                hotelStarsDiv.style.display = 'inline-block';
                hotelStarsDiv.appendChild(hotelStars);
                hotelInformationDIV.appendChild(hotelStarsDiv);

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
                hotelInformationDIV.appendChild(hotelRoomsDiv);

                var hotelPriceDiv = document.createElement("DIV");
                hotelPriceDiv.style.width = '180px';
                hotelPriceDiv.style.marginLeft = '10px';
                hotelPriceDiv.style.marginTop = '5px';
                hotelPriceDiv.style.float = 'right';

                var hotelPrice = document.createElement("SPAN");
                hotelPrice.innerHTML = 'Цена за 1 ночь';
                hotelPrice.style.fontSize = '15px';
                hotelPrice.style.display = 'inline-block';
                hotelPrice.style.width = '150px';
                hotelPrice.style.textAlign = 'right';
                hotelPriceDiv.appendChild(hotelPrice);

                hotelPrice = document.createElement("SPAN");
                hotelPrice.innerHTML = 'от ' + hotelsInformation[i].cheapestRoom;
                hotelPrice.style.fontSize = '15px';
                hotelPrice.style.display = 'inline-block';
                hotelPriceDiv.style.marginTop = '5px';
                hotelPrice.style.width = '150px';
                hotelPrice.style.textAlign = 'right';
                hotelPriceDiv.appendChild(hotelPrice);

                hotelPrice = document.createElement("SPAN");
                hotelPrice.innerHTML = 'до ' + hotelsInformation[i].mostExpansiveRoom;
                hotelPrice.style.fontSize = '15px';
                hotelPrice.style.display = 'inline-block';
                hotelPrice.style.width = '150px';
                hotelPrice.style.textAlign = 'right';
                hotelPriceDiv.appendChild(hotelPrice);

                var chooseRoom = document.createElement("INPUT");
                chooseRoom.id = hotelsInformation[i].hotelId;
                chooseRoom.type = 'button';
                chooseRoom.name = 'chooseRoom';
                chooseRoom.value= 'Выбрать номер';
                chooseRoom.className = 'input';
                chooseRoom.onclick = function () {
                    chooseRoomProcessing(this.id, arrivalDate, departureDate);
                };
                hotelPriceDiv.appendChild(chooseRoom);

                hotelInformationDIV.appendChild(hotelPriceDiv);

                var hotelDescription = document.createElement("SPAN");
                hotelDescription.innerHTML = hotelsInformation[i].hotelDescription;
                hotelDescription.style.fontSize = '13px';
                hotelDescription.style.marginLeft = '0';
                hotelDescription.style.whiteSpace = 'pre-line';
                var hotelDescriptionDiv = document.createElement("DIV");
                hotelDescriptionDiv.style.width = '710px';
                hotelDescriptionDiv.style.marginTop = '15px';
                hotelDescriptionDiv.style.paddingLeft = '15px';
                hotelDescriptionDiv.style.textAlign = 'justify';
                hotelDescriptionDiv.appendChild(hotelDescription);
                hotelInformationDIV.appendChild(hotelDescriptionDiv);

                mainBody.appendChild(hotelInformationDIV);
                searchResults.push(hotelInformationDIV);

                //var td2 = document.createElement("TD");
                //
                //var freeRooms = document.createElement("SPAN");
                //freeRooms.innerHTML = 'Детальная информация о свободных номерах: ';
                //freeRooms.style.fontSize = '15px';
                //var freeRoomsDiv = document.createElement("DIV");
                //freeRoomsDiv.style.width = '400px';
                //freeRoomsDiv.style.marginTop = '5px';
                //freeRoomsDiv.appendChild(freeRooms);
                //td2.appendChild(freeRoomsDiv);
                //
                //var rooms = hotelsInformation[i].rooms;
                //
                //if (rooms.length > 0) {
                //    var roomsTable = document.createElement("TABLE");
                //    roomsTable.style.width = 'auto';
                //    roomsTable.style.marginTop = '10px';
                //    roomsTable.style.marginLeft = '20px';
                //    roomsTable.className = 'freeRoms';
                //
                //    var roomsTableHead = document.createElement("THEAD");
                //    roomsTable.appendChild(roomsTableHead);
                //
                //    var roomsTableRow = document.createElement("TR");
                //    roomsTableHead.appendChild(roomsTableRow);
                //
                //    var roomsTableHeader = document.createElement("TH");
                //    roomsTableHeader.innerHTML = 'Тип номера';
                //    roomsTableRow.appendChild(roomsTableHeader);
                //
                //    roomsTableHeader = document.createElement("TH");
                //    roomsTableHeader.innerHTML = 'Количество';
                //    roomsTableRow.appendChild(roomsTableHeader);
                //
                //    roomsTableHeader = document.createElement("TH");
                //    roomsTableHeader.innerHTML = 'Цена';
                //    roomsTableRow.appendChild(roomsTableHeader);
                //
                //
                //    var roomsTableBody = document.createElement("TBODY");
                //    roomsTable.appendChild(roomsTableBody);
                //
                //    for (var j = 0; j < rooms.length; j++) {
                //        if (rooms[j].quantity > 0) {
                //            roomsTableRow = document.createElement("TR");
                //            roomsTableBody.appendChild(roomsTableRow);
                //
                //            var roomsTableData = document.createElement("TD");
                //            roomsTableData.style.width = '80px';
                //            roomsTableData.innerHTML = rooms[j].roomTypeName;
                //            roomsTableRow.appendChild(roomsTableData);
                //
                //            roomsTableData = document.createElement("TD");
                //            roomsTableData.style.width = '50px';
                //            roomsTableData.innerHTML = rooms[j].quantity;
                //            roomsTableRow.appendChild(roomsTableData);
                //
                //            roomsTableData = document.createElement("TD");
                //            roomsTableData.style.width = '50px';
                //            roomsTableData.innerHTML = rooms[j].price;
                //            roomsTableRow.appendChild(roomsTableData);
                //        }
                //    }
                //
                //    td2.appendChild(roomsTable);
                //}
                //
                //row.appendChild(td2);
            }
        }
    });

}

function clearSearchResults() {

    var mainBody = document.getElementById('mainBody');

    for (var i = 0; i < searchResults.length; i++) {
        mainBody.removeChild(searchResults[i]);
    }

    searchResults = [];

}

function chooseRoomProcessing(hotelId) {
    var arrivalDate = $("#arrivalDate").datepicker("getDate");
    var departureDate = $("#departureDate").datepicker("getDate");
    alert(hotelId + ' ' + arrivalDate + ' ' + departureDate);
}
