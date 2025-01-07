<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>차량 검색 결과</title>
</head>
<body>

    <h1>차량 검색</h1>
<form action="${pageContext.request.contextPath}/cars/searchResults" method="post">
    <label>차량 이름: </label><input type="text" name="carName">
    <label>모델: </label><input type="text" name="model">
    <label>제조사: </label><input type="text" name="manufacturer">
    <label>연도: </label><input type="number" name="year">
    <button type="submit">검색</button>
</form>

    <h2>검색된 차량</h2>
    <table>
        <thead>
            <tr>
                <th>차량 이름</th>
                <th>모델</th>
                <th>제조사</th>
                <th>연도</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="car" items="${cars}" method="post">
                <tr>
                    <td>${car.carName}</td>
                    <td>${car.model}</td>
                    <td>${car.manufacturer}</td>
                    <td>${car.year}</td>
                    <td>${car.dailyRate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
