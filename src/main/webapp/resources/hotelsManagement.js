function addNewHotel() {

    if (checkHotelFieldsContent()) {
        $.ajax({
            type: 'POST',
            url: '/hotels/add',
            data: prepareHotelInformation(1),
            dataType: 'json',
            async: true,
            success: function () {
                location.href = '/hotels/management';
            }
        });
    }

}

function editHotel_js(hotelId) {

    if (checkHotelFieldsContent()) {
        $.ajax({
            type: 'POST',
            url: '/hotels/edit?hotelId=' + hotelId.toString(),
            data: prepareHotelInformation(0),
            dataType: 'json',
            async: true,
            success: function () {
                location.href = '/hotels/management';
            }
        });
    }

}

function deleteHotel_js(object) {
    document.getElementById('deleteHotelButton').onclick = function () {
        location.href = '/hotels/delete?hotelId=' + object.id;
    };
    show('block');
}

function prepareHotelInformation(childNodeIndex) {

    var name = document.getElementById('name').value;
    var description = document.getElementById('description').value;
    var stars = document.getElementById('stars').value;
    var city = document.getElementById('city').value;

    var rooms = [];
    var table = document.getElementById('rooms');
    for (var r = 1; r < table.rows.length; r++) {
        if (table.rows[r].cells[1].childNodes[childNodeIndex].value != '') {
            rooms.push({
                roomType: table.rows[r].id,
                roomsQuantity: table.rows[r].cells[1].childNodes[childNodeIndex].value,
                numberOfGuests: table.rows[r].cells[2].childNodes[childNodeIndex].value,
                pricePerNight: table.rows[r].cells[3].childNodes[childNodeIndex].value
            });
        }
    }

    return {
        name: name,
        description: description,
        stars: stars,
        city: city,
        rooms: JSON.stringify(rooms)
    };

}

function checkHotelFieldsContent() {

    var errorText = document.getElementById('errorTextHotelManagement');

    var name = document.getElementById('name').value;
    var description = document.getElementById('description').value;
    var stars = document.getElementById('stars').value;
    var city = document.getElementById('city').value;

    var emptyFields = [];

    if (name.length == 0) {
        emptyFields.push("название")
    }
    if (description.length == 0) {
        emptyFields.push("описание")
    }
    if (stars == 0) {
        emptyFields.push("количество звезд")
    }
    if (city == 0) {
        emptyFields.push("город")
    }

    if (emptyFields.length > 0) {
        emptyFields.length == 1 ? errorText.innerHTML = 'Поле ' : errorText.innerHTML = 'Поля ';
        for (var i = 0; i < emptyFields.length; i++) {
            errorText.innerHTML += emptyFields[i];
            if (i < emptyFields.length - 1) {
                errorText.innerHTML += ',';
            }
            errorText.innerHTML += ' ';
        }
        emptyFields.length == 1 ? errorText.innerHTML += 'обязательно для заполнения' : errorText.innerHTML += 'обязательны для заполнения';
        return false;
    }
    else {
        return true;
    }

}

function show(state) {
    document.getElementById('deleteConfirm').style.display = state;
    document.getElementById('wrap').style.display = state;
}
