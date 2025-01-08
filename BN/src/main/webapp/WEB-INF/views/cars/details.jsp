<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>차량 상세</title>
</head>
<body>
    <h1>${car.name} - ${car.model}</h1>
    <p>제조사: ${car.manufacturer}</p>
    <p>연도: ${car.year}</p>
    <p>차량 번호판: ${car.licensePlate}</p>
    <p>대여 가능 여부: ${car.available ? '가능' : '불가'}</p>
    <a href="${pageContext.request.contextPath}/cars/edit/${car.carid}">수정</a>
    <form action="${pageContext.request.contextPath}/cars/details/${car.carid}" method="post">
        <button type="submit">삭제</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/cars">목록으로</a>
</body>
</html>
