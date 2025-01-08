<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>차량 목록</title>
</head>
<body>
    <h1>차량 목록</h1>
    <!-- 차량 추가 버튼 -->
    <a href="${pageContext.request.contextPath}/cars/add">차량 추가</a>

    <table border="1">
        <thead>
            <tr>
                <th>차량 이름</th>
                <th>모델</th>
                <th>제조사</th>
                <th>연도</th>
                <th>상세 보기</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <!-- 차량 목록 출력 -->
            <c:forEach var="car" items="${cars}">
                <tr>
                    <td>${car.carname}</td>
                    <td>${car.model}</td>
                    <td>${car.manufacturer}</td>
                    <td>${car.year}</td>
                    <td><a href="${pageContext.request.contextPath}/cars/details/${car.carid}">상세보기</a></td>
                    <td><a href="${pageContext.request.contextPath}/cars/edit/${car.carid}">수정</a></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cars/delete/${car.carid}" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                            <button type="submit">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
