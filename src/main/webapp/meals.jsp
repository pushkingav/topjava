<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<style>
    table {
        color: white;
        text-align: center;
    }
    td {
        padding: 5px;
    }
</style>
<table>
    <tr style="background: darkgray"><td>Дата</td><td>Наименование</td><td>Калории</td></tr>

    <c:forEach items="${requestScope.mealsList}" var="meal">
        <c:set var="clr" value="${meal.exceed eq true ? 'red' : 'green'}"/>
        <tr style="background: ${clr}">
            <td>
                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meal.dateTime}" var="parsedDate" />
                <fmt:formatDate var="formattedDate" value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" />
                <c:out value="${formattedDate}"/>
            </td>
            <td>
                <c:out value="${meal.description}" />
            </td>
            <td>
                <c:out value="${meal.calories}" />
            </td>
        </tr>
    </c:forEach>
</table>
</body>

</html>