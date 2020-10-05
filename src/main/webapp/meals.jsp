<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<div class="title">
    <p><h2>Meals</h2>
</div>
<table style="border: 1px solid black; border-spacing: 5px">
    <thead>
        <tr>
            <th>№</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
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
        </tr>
        <c:set var="i" value="${i + 1}"/>
    </c:forEach>

    </tbody>
    <tfoot>

    </tfoot>
</table>

</body>
</html>