<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../resources/styles/jquery.fancybox.css" type="text/css"/>
    <link rel="stylesheet" href="../../resources/styles/main.css" type="text/css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/photos.js"></script>
    <script type="text/javascript" src="../../resources/jquery.fancybox.pack.js"></script>
</head>
<body>
<jsp:include page="topBar.jsp"/>
<div style="margin-top: 60px"></div>

<%--@elvariable id="hotel" type="com.geekhub.model.Hotel"--%>
<%--@elvariable id="photos" type="java.util.List"--%>
<%--@elvariable id="uploadFilesPath" type="java.lang.String"--%>
<%--@elvariable id="mainPhoto" type="com.geekhub.model.Photo"--%>

<script type="text/javascript">
    $(document).ready(function () {
        $(".fancybox").fancybox({
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });
    });
</script>

<div class="photosEdit-main-div">

    <div class="photosEdit-label">
        Фотографии отеля ${hotel.getName()}
    </div>

    <div class="photosEdit-border-bottom"></div>

    <c:set var="dir" value="../../uploadFiles/"/>

    <div class="photosEdit-type-label">
        Главная фотография. Отображается при поиске отелей.
    </div>

    <c:if test="${empty mainPhoto}">
        <c:set var="buttonValue" value="Добавить фотографию"/>
    </c:if>
    <c:if test="${not empty mainPhoto}">
        <c:set var="buttonValue" value="Заменить фотографию"/>
    </c:if>

    <div style="display: inline-block">
        <input type="button" id="${hotel.getHotelId()}" value="${buttonValue}" class="photosEdit-add_button"
               onclick="uploadMainPhoto(this);"/>
    </div>

    <div class="photosEdit-uploaded-photos">
        <c:if test="${not empty mainPhoto}">
            <div class="photosEdit-img-container">
                <a href="${dir.concat(mainPhoto.getFileName())}" class="fancybox">
                    <img src="${dir.concat(mainPhoto.getFileName())}" class="hotel-photo" style="position: relative">
                </a>
                <img src="../../resources/images/blackCross.png" title="Удалить фотографию"
                     class="hotel-photo-delete" onclick="deletePhoto(this, ${mainPhoto.getPhotoId()})">
            </div>
        </c:if>
    </div>

    <div class="photosEdit-border-bottom"></div>

    <div class="photosEdit-type-label">
        Дополнительные фотографии. Отображаются при детальном просмотре отеля.
    </div>

    <div style="display: inline-block">
        <input type="button" id="${hotel.getHotelId()}" value="Добавить фотографии" class="photosEdit-add_button"
               onclick="uploadPhotos(this);"/>
    </div>

    <div class="photosEdit-uploaded-photos">
        <c:forEach items="${photos}" var="photo">
            <div class="photosEdit-img-container">
                <a href="${dir.concat(photo.getFileName())}" class="fancybox" rel="group">
                    <img src="${dir.concat(photo.getFileName())}" class="hotel-photo" style="position: relative">
                </a>
                <img src="../../resources/images/blackCross.png" title="Удалить фотографию"
                     class="hotel-photo-delete" onclick="deletePhoto(this, ${photo.getPhotoId()})">
            </div>
        </c:forEach>
    </div>

</div>

<div onclick="show('none')" id="wrap"></div>

<div id="uploadPhotos-div">
    <form id="uploadPhotos-form" method="post" action="/photos/upload?hotelId=${hotel.getHotelId()}&mainPhoto=false"
          enctype="multipart/form-data">
        <div>
            <div class="photosEdit-label" style="padding-top: 0">Выберите фотографии для загрузки</div>
            <div style="display: inline-block; float: right">
                <img src="../../resources/images/w18h181338911473cross.png" style="cursor: pointer"
                     onclick="showUploadPhotos('none')">
            </div>
        </div>
    </form>
</div>

<div id="uploadMainPhoto-div">
    <form id="uploadMainPhoto-form" method="post" action="/photos/upload?hotelId=${hotel.getHotelId()}&mainPhoto=true"
          enctype="multipart/form-data">
        <div>
            <div class="photosEdit-label" style="padding-top: 0">Выберите фотографию для загрузки</div>
            <div style="display: inline-block; float: right">
                <img src="../../resources/images/w18h181338911473cross.png" style="cursor: pointer"
                     onclick="showUploadMainPhoto('none')">
            </div>
        </div>
    </form>
</div>


</body>
</html>
