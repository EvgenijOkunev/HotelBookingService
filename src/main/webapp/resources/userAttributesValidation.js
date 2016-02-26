function validate() {

    // Считаем значения из полей userFirstName, userLastName и userEmail в переменные
    var firstName = document.getElementById('userFirstName').value;
    var lastName = document.getElementById('userLastName').value;
    var email = document.getElementById('userEmail').value;
    var password = document.getElementById('userPassword').value;

    var thereWasSomeErrors = false;

    // Если поле firstName пустое выведем сообщение и предотвратим отправку формы
    if (firstName.length == 0) {
        document.getElementById('firstNameMessage').innerHTML = '*данное поле обязательно для заполнения';
        thereWasSomeErrors = true;
    }

    // Если поле lastName пустое выведем сообщение и предотвратим отправку формы
    if (lastName.length == 0) {
        document.getElementById('lastNameMessage').innerHTML = '*данное поле обязательно для заполнения';
        thereWasSomeErrors = true;
    }

    // Если поле email пустое выведем сообщение и предотвратим отправку формы
    if (email.length == 0) {
        document.getElementById('emailMessage').innerHTML = '*данное поле обязательно для заполнения';
        thereWasSomeErrors = true;
    }
    else {
        at = email.indexOf("@");
        dot = email.indexOf(".");
        //Если поле не содержит эти символы знач email введен не верно
        if (at < 1 || dot < 1) {
            document.getElementById('emailMessage').innerHTML = '*email введен не верно';
            thereWasSomeErrors = true;
        }
    }

    // Если поле password пустое или содрежит менее 6 символов, то предотвратим отправку формы
    if (password.length < 6) {
        document.getElementById('passwordMessage').innerHTML = (password.length == 0 ? '*данное поле обязательно для заполнения' : '*пароль должен состоять минимум из шести символов');
        thereWasSomeErrors = true;
    }

    return !thereWasSomeErrors;

}

function clearWarningText(elementID) {
    document.getElementById(elementID).innerHTML = "";
}
