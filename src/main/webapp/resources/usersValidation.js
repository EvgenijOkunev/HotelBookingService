function checkFieldsContent(firstName, lastName, email, password) {

    var errorText = document.getElementById('errorText');

    if (firstName.length == 0 || lastName.length == 0 || email.length == 0 || password.length == 0) {
        errorText.innerHTML = 'Все поля обязательны для заполнения';
        return false;
    }

    //else {
    //    at = email.indexOf("@");
    //    dot = email.indexOf(".");
    //    //Если поле не содержит эти символы знач email введен не верно
    //    if (at < 1 || dot < 1) {
    //        document.getElementById('emailMessage').innerHTML = '*email введен не верно';
    //        thereWasSomeErrors = true;
    //    }
    //}

    if (password.length < 6) {
        errorText.innerHTML = 'пароль должен состоять минимум из шести символов';
        return false;
    }

    return true;

}

function clearWarningText(elementID) {
    document.getElementById(elementID).innerHTML = "";
}

function loginValidate() {

    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    $.ajax({
        type: 'POST',
        url: '/users/userLoginProcessing',
        data: {
            email: email,
            password: password
        },
        dataType: 'json',
        async: true,
        success: function () {
            location.href = "/";
        },
        error: function () {
            document.getElementById('errorText').innerHTML = 'Неправильный email и/или пароль';
        }
    });

}

function registrationValidate() {

    var firstName = document.getElementById('firstName').value;
    var lastName = document.getElementById('lastName').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    var hotelOwner = document.getElementById('hotelOwner').value;

    if (checkFieldsContent(firstName, lastName, email, password)) {

        $.ajax({
            type: 'POST',
            url: '/users/checkEmailUnique',
            data: {
                email: email
            },
            dataType: 'json',
            async: true,
            success: function () {
                $.ajax({
                    type: 'POST',
                    url: '/users/userRegistrationProcessing',
                    data: {
                        firstName: firstName,
                        lastName: lastName,
                        email: email,
                        password: password,
                        hotelOwner: hotelOwner
                    },
                    dataType: 'json',
                    async: true,
                    success: function () {
                        location.href = "/users/show-all";
                    }
                });
            },
            error: function () {
                errorText.innerHTML = 'пользователь с таким email уже зарегистрирован';
            }
        });

    }

}