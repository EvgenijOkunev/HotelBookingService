function addNewHotel() {

    var name = document.getElementById('name').value;
    var description = document.getElementById('description').value;
    var stars = document.getElementById('stars').value;
    var city = document.getElementById('city').value;

    var rooms = [];
    var table = document.getElementById('rooms');
    for (var r = 1; r < table.rows.length; r++) {
        if (table.rows[r].cells[1].childNodes[0].value != '') {
            rooms.push({
                roomType: table.rows[r].id,
                roomsQuantity: table.rows[r].cells[1].childNodes[0].value,
                numberOfGuests: table.rows[r].cells[2].childNodes[0].value,
                pricePerNight: table.rows[r].cells[3].childNodes[0].value
            });
        }
    }

    $.ajax({
        type: 'POST',
        url: '/hotels/add',
        data: {
            name: name,
            description: description,
            stars: stars,
            city: city,
            rooms: JSON.stringify(rooms)
        },
        dataType: 'json',
        async: true,
        success: function () {
            location.href = '/hotels/management';
        }
    });

}
