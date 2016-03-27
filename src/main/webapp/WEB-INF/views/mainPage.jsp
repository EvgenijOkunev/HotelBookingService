<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" href="../../resources/styles/main.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="../../resources/hotelsSearch.js"></script>
</head>
<body>
<jsp:include page="topBar.jsp"/>
<jsp:include page="searchBar.jsp"/>

<table id="hotels-search-result" class="searchResult">
    <tbody>
    </tbody>
</table>

</html>