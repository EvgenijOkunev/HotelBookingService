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
            var hotelsInformation = result["hotelsInformation"];
            for (var i = 0; i < hotelsInformation.length; i++) {

            }
        }
    });

}
