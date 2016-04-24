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

                var hotelLink = document.createElement('A');
                hotelLink.href = '/hotels/getDetailedInformation?arrivalDate=' + arrivalDate
                    + '&departureDate=' + departureDate + '&hotelId=' + hotelsInformation[i].hotelId;
                hotelLink.innerHTML = 'Отель "' + hotelsInformation[i].hotelName + '"';
                hotelLink.style.fontSize = '20px';
                hotelLink.style.color = '#9b302c';
                hotelLink.style.textDecoration = 'none';

                var hotelNameDiv = document.createElement("DIV");
                hotelNameDiv.style.marginLeft = '15px';
                hotelNameDiv.style.width = '320px';
                hotelNameDiv.style.display = 'inline-block';
                hotelNameDiv.appendChild(hotelLink);
                hotelInformationDIV.appendChild(hotelNameDiv);

                var hotelStarsDiv = document.createElement("DIV");
                hotelStarsDiv.style.width = '190px';
                hotelStarsDiv.style.display = 'inline-block';
                hotelStarsDiv.style.marginTop = '4px';
                hotelStarsDiv.style.verticalAlign = 'top';
                hotelStarsDiv.style.fontSize = '15px';
                hotelStarsDiv.innerHTML = 'Количество звезд: ' + hotelsInformation[i].hotelStars;
                hotelInformationDIV.appendChild(hotelStarsDiv);

                var hotelRoomsDiv = document.createElement("DIV");
                hotelRoomsDiv.style.width = '200px';
                hotelRoomsDiv.style.display = 'inline-block';
                hotelRoomsDiv.style.marginTop = '4px';
                hotelRoomsDiv.style.verticalAlign = 'top';
                hotelRoomsDiv.style.fontSize = '15px';
                hotelRoomsDiv.style.width = '200px';
                hotelRoomsDiv.style.textAlign = 'right';
                hotelRoomsDiv.innerHTML = 'Свободных номеров: ' + hotelsInformation[i].roomsQuantity;
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
                chooseRoom.value = 'Выбрать номер';
                chooseRoom.className = 'input';
                chooseRoom.onclick = function () {
                    chooseRoomProcessing(this.id, arrivalDate, departureDate);
                };
                hotelPriceDiv.appendChild(chooseRoom);

                hotelInformationDIV.appendChild(hotelPriceDiv);

                var underline = document.createElement("DIV");
                underline.style.width = '710px';
                underline.style.marginLeft = '15px';
                underline.style.borderBottom = '1px solid #bec8d2';
                hotelInformationDIV.appendChild(underline);

                var hotelPhotoDiv = document.createElement("DIV");
                hotelPhotoDiv.style.width = '200px';
                hotelPhotoDiv.style.minHeight = 'auto';
                hotelPhotoDiv.style.float = 'left';
                hotelPhotoDiv.style.marginTop = '20px';
                hotelPhotoDiv.style.marginLeft = '10px';
                hotelPhotoDiv.style.display = 'inline-block';
                hotelPhotoDiv.style.flexGrow = '0';
                hotelPhotoDiv.style.flexShrink = '0';
                hotelInformationDIV.appendChild(hotelPhotoDiv);

                var hotelPhotoLink = document.createElement('A');
                hotelPhotoLink.href = '/hotels/getDetailedInformation?arrivalDate=' + arrivalDate
                    + '&departureDate=' + departureDate + '&hotelId=' + hotelsInformation[i].hotelId;
                hotelPhotoDiv.appendChild(hotelPhotoLink);

                var hotelPhotoImage = document.createElement('IMG');
                hotelPhotoImage.src = hotelsInformation[i].hotelMainPhoto;
                hotelPhotoImage.style.backgroundImage = hotelsInformation[i].hotelMainPhoto;
                hotelPhotoImage.style.backgroundSize = 'cover';
                hotelPhotoImage.style.backgroundPosition = 'center center';
                hotelPhotoImage.style.maxWidth = '100%';
                hotelPhotoLink.appendChild(hotelPhotoImage);

                var hotelDescriptionDiv = document.createElement("DIV");
                hotelDescriptionDiv.style.display = 'inline-block';
                hotelDescriptionDiv.style.width = '500px';
                hotelDescriptionDiv.style.marginTop = '8px';
                hotelDescriptionDiv.style.paddingTop = '7px';
                hotelDescriptionDiv.style.marginLeft = '15px';
                hotelDescriptionDiv.style.textAlign = 'justify';
                hotelDescriptionDiv.style.fontSize = '13px';
                hotelDescriptionDiv.style.whiteSpace = 'pre-line';
                hotelDescriptionDiv.innerHTML = hotelsInformation[i].hotelDescription;
                hotelInformationDIV.appendChild(hotelDescriptionDiv);

                mainBody.appendChild(hotelInformationDIV);
                searchResults.push(hotelInformationDIV);

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
    location.href = '/hotels/getDetailedInformation?arrivalDate=' + arrivalDate
        + '&departureDate=' + departureDate + '&hotelId=' + hotelId;
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