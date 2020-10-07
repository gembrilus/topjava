<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h3><a href="index.html">На главную</a></h3>
<h3><a href="meal">Добавить еду</a></h3>
<hr>
<div class="title">
    <p><h2>Meals</h2>
</div>
<table style="border: 1px solid black; border-spacing: 5px">
    <thead>
        <tr>
            <th>№</th>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Действия</th>
        </tr>
    </thead>
    <tbody>

    <c:set var="color" value="green" scope="page"/>
    <c:set var="i" value="1" scope="page"/>
    <c:forEach items="${requestScope.meals}" var="meal">
        <c:if test="${meal.excess}">
            <c:set var="color" value="red"/>
        </c:if>
        <c:if test="${!meal.excess}">
            <c:set var="color" value="green"/>
        </c:if>
        <tr style="color: ${color}; text-align: end">
            <td>${i}</td>
            <td>${meal.dateTime.toLocalDate()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <button type="submit"><a href="edit?date=<c:out value="${meal.dateTime}"/>">Редактировать</a></button>
                <button type="submit"><a href="delete?date=<c:out value="${meal.dateTime}"/>">Удалить</a></button>
            </td>
        </tr>
        <c:set var="i" value="${i + 1}"/>
    </c:forEach>

    </tbody>
    <tfoot>

    </tfoot>
</table>

</body>
</html>