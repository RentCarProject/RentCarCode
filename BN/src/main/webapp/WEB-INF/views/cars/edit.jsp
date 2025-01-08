<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>차량 수정</title>
</head>
<body>
    <h1>차량 수정</h1>
    <c:out value="${car.licensePlate}" />

<form action="${pageContext.request.contextPath}/cars/edit/${carid}" method="post">
        <div>
            <label for="carname">차량 이름</label>
            <input type="text" id="carname" name="carname" value="${car.carname}" required />
        </div>
        <div>
            <label for="model">모델</label>
            <input type="text" id="model" name="model" value="${car.model}" required />
        </div>
        <div>
            <label for="manufacturer">제조사</label>
            <input type="text" id="manufacturer" name="manufacturer" value="${car.manufacturer}" required />
        </div>
        <div>
            <label for="year">연도</label>
            <input type="text" id="year" name="year" value="${car.year}" required />
        </div>

        <div>
            <input type="text" id="licensePlate" name="licensePlate" value="${car.licensePlate}" required hidden />
         </div>

        <div>
            <input type="submit" value="수정 완료" />
        </div>
    </form>
    <a href="${pageContext.request.contextPath}/cars">차량 목록으로 돌아가기</a>
</body>
</html>
