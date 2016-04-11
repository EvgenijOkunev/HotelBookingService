function checkUserFieldsContent(firstName, lastName, email, phoneNumber, password, passwordConfirm) {

    var errorText = document.getElementById('errorTextUserManagement');

    if (firstName.length == 0 || lastName.length == 0 || email.length == 0 ||
        phoneNumber.length == 0 || password.length == 0 || passwordConfirm.length == 0) {
        errorText.innerHTML = 'Все поля обязательны для заполнения';
        return false;
    }

    if (email.indexOf("@") < 1 || email.indexOf(".") < 1) {
        errorText.innerHTML = 'Email введен не верно';
    }

    if (phoneNumber.length < 19) {
        errorText.innerHTML = 'Номер телефона введен неправильно';
        return false;
    }

    if (password.length < 6) {
        errorText.innerHTML = 'Пароль должен состоять минимум из шести символов';
        return false;
    }

    if (password != passwordConfirm) {
        errorText.innerHTML = 'Пароли не совпадают';
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
            document.getElementById('errorTextUserManagement').innerHTML = 'Неправильный email и/или пароль';
        }
    });

}

function userProfileValidate() {

    var firstName = document.getElementById('firstName').value;
    var lastName = document.getElementById('lastName').value;
    var email = document.getElementById('email').value;
    var phoneNumber = document.getElementById('phoneNumber').value;
    var password = document.getElementById('password').value;
    var passwordConfirm = document.getElementById('passwordConfirm').value;

    return checkUserFieldsContent(firstName, lastName, email, phoneNumber, password, passwordConfirm);

}

function registrationValidate() {

    var firstName = document.getElementById('firstName').value;
    var lastName = document.getElementById('lastName').value;
    var email = document.getElementById('email').value;
    var phoneNumber = document.getElementById('phoneNumber').value;
    var password = document.getElementById('password').value;
    var passwordConfirm = document.getElementById('passwordConfirm').value;
    var hotelOwner = document.getElementById('hotelOwner').checked;
    var errorText = document.getElementById('errorTextUserManagement');

    if (checkUserFieldsContent(firstName, lastName, email, phoneNumber, password, passwordConfirm)) {

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
                        phoneNumber: phoneNumber,
                        password: password,
                        hotelOwner: hotelOwner
                    },
                    dataType: 'json',
                    async: true,
                    success: function () {
                        location.href = "/login";
                    }
                });
            },
            error: function () {
                errorText.innerHTML = 'Пользователь с таким email уже зарегистрирован';
            }
        });

    }
    else {
        return false;
    }

}

jQuery(function ($) {
    $("#phoneNumber").mask("+99 (999) 999-99-99");
});