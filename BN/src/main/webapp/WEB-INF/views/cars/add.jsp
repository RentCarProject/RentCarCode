<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>차량 추가</title>
</head>
<body>
    <h1>차량 추가</h1>
    <form action="${pageContext.request.contextPath}/cars/add" method="post">
        <div>
            <label for="name">차량 이름:</label>
            <input type="text" id="carname" name="carname" required>
        </div>
        <div>
            <label for="model">모델:</label>
            <input type="text" id="model" name="model" required>
        </div>
        <div>
            <label for="manufacturer">제조사:</label>
            <input type="text" id="manufacturer" name="manufacturer" required>
        </div>
        <div>
            <label for="year">연식:</label>
            <input type="number" id="year" name="year" required>
        </div>
        <div>
            <label for="licensePlate">차량 번호판:</label>
            <input type="text" id="licensePlate" name="licensePlate" required>
        </div>
        <div>
            <label for="available">대여 가능 여부:</label>
            <input type="checkbox" id="available" name="available">
        </div>
        <div>
            <button type="submit">차량 추가</button>
        </div>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/cars">차량 목록으로 돌아가기</a>
</body>
</html>