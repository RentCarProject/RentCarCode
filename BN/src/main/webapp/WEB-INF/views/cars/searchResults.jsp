<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>차량 검색 결과</title>
</head>
<body>
    <h1>검색 결과</h1>
    <table border="1">
        <thead>
            <tr>
                <th>차량 이름</th>
                <th>모델</th>
                <th>제조사</th>
                <th>연도</th>
                <th>상세 보기</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="car" items="${cars}">
                <tr>
                    <td>${car.carname}</td>
                    <td>${car.model}</td>
                    <td>${car.manufacturer}</td>
                    <td>${car.year}</td>
                    <td><a href="${pageContext.request.contextPath}/cars/${car.carid}">상세보기</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
