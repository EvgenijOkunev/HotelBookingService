
$(window).resize(function () {
    $(".file_upload input").triggerHandler("change");
});

function show(state) {
    showUploadPhotos(state);
    showPhotosView(state);
}

function showUploadPhotos(state) {
    document.getElementById('uploadPhotos-div').style.display = state;
    document.getElementById('wrap').style.display = state;
}

function showPhotosView(state) {
    document.getElementById('viewPhotos-div').style.display = state;
    document.getElementById('wrap').style.display = state;
}

function uploadPhotos(object) {
    deleteAllFileUploadBlock();
    addFileUploadBlock();
    showUploadPhotos('block');
}

function addFileUploadBlock() {

    var form = document.getElementById('uploadPhotos-form');

    var label = document.createElement('LABEL');
    label.className = 'file_upload';
    form.appendChild(label);

    var span = document.createElement('SPAN');
    span.className = 'button';
    span.innerHTML = 'Выбрать';
    label.appendChild(span);

    var mark = document.createElement('MARK');
    mark.innerHTML = 'Фотография не выбрана';
    label.appendChild(mark);

    var input = document.createElement('INPUT');
    label.appendChild(input);
    input.type = 'file';
    input.accept='image/*';

    var file_api = ( window.File && window.FileReader && window.FileList && window.Blob ) ? true : false;
    $(input).change(function () {
        var wrapper = $(this.parentElement),
            inp = wrapper.find("input"),
            btn = wrapper.find(".button"),
            lbl = wrapper.find("mark");

        var file_name;
        if (file_api && inp[0].files[0])
            file_name = inp[0].files[0].name;
        else
            file_name = inp.val().replace("C:\\fakepath\\", '');

        if (!file_name.length)
            return;

        if (lbl.is(":visible")) {
            lbl.text(file_name);
            btn.text("Выбрать");
        } else
            btn.text(file_name);
        addFileUploadBlock();
    }).change();

    var submit = document.getElementById('submitButton');
    if (submit != null) {
        submit.parentNode.removeChild(submit);
    }
    appendSubmitButton(form);

}

function deleteAllFileUploadBlock() {
    var fileUploadBlocks = $(".file_upload");
    for (var i = 0; i < fileUploadBlocks.length; i++) {
        fileUploadBlocks[i].parentNode.removeChild(fileUploadBlocks[i]);
    }
}

function appendSubmitButton(form) {

    var submit = document.createElement('INPUT');

    submit.type = 'button';
    submit.id = 'submitButton';
    submit.value = 'Загрузить фотографии';
    submit.className = 'photosEdit-add_button';
    submit.style.float = 'right';

    form.appendChild(submit);
    submit.style.position = 'absolute';
    submit.style.bottom = '15px';
    submit.style.right = '15px';

    submit.onclick = function () {

        var formData = new FormData();

        var fileUploadBlocks = $(".file_upload");
        for (var i = 0; i < fileUploadBlocks.length; i++) {
            var file = $(fileUploadBlocks[i]).find("input")[0].files[0];
            if (file != null) {
                formData.append("myFile" + i, file);
            }
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", form.action);
        xhr.send(formData);
        xhr.onload = function () {
            showUploadPhotos('none');
            location.reload();
        }

    }

}

function deletePhoto(object, photoId) {

    var photo = object;
    var photoDelete = $(photo).closest('img')[0];
    var photoDiv = $(photo).closest('div')[0];

    $.ajax({
        type: 'GET',
        url: '/photos/delete?photoId=' + photoId,
        async: true,
        success: function () {
            photoDiv.parentNode.removeChild(photoDiv);
        }
    });

}

