<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="../../resources/styles/tableStyles.css" type="text/css"/>
    <title>Hotels Management</title>
</head>
<body>

<jsp:include page="topBar.jsp" />

<h1 class="hor-minimalist-b-title">Hotels</h1>

<c:url var="addUrl" value="/hotels/add"/>

<c:if test="${not empty hotels}">
    <table class="hor-minimalist-b">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Stars</th>
            <th>City</th>
            <th>Operations</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hotels}" var="hotel">
            <c:url var="editUrl" value="/hotels/edit?userId=${hotel.userId}"/>
            <c:url var="deleteUrl" value="/hotels/delete?userId=${hotel.userId}"/>
            <tr>
                <td><c:out value="${hotel.name}"/></td>
                <td><c:out value="${hotel.description}"/></td>
                <td><c:out value="${hotel.stars}"/></td>
                <td><c:out value="${hotel.city}"/></td>
                <td><a class="hor-minimalist-b-link" href="${addUrl}">Add</a> |
                    <a class="hor-minimalist-b-link" href="${editUrl}">Edit</a> |
                    <a class="hor-minimalist-b-link" href="${deleteUrl}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty hotels}">
    Вы еще не добавили ни одного отеля. <a href="${addUrl}">Можете сделать это прямо сейчас</a>
</c:if>

</body>
</html>
