<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>차량 삭제</title>
</head>
<body>
   <h1>차량 삭제</h1>
<form action="${pageContext.request.contextPath}/cars/delete/${car.carid}" method="post">
        <div>
            <label>licensePlate : </label>
            <input type="text" name="licensePlate" />
        </div>
        <input type="submit" value="삭제" />
   </form>
</body>
</html>
