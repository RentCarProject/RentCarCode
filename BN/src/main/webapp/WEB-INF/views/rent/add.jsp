<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>차량 렌트</h1>
	<form action="${pageContext.request.contextPath}/rent/add" method="post">
	    <div>
            <label>USERNAME : </label>
            <input type="text" name="memberId" />
	    </div>
        <div>
            <label>CARNAME : </label>
            <input type="text" name="carname" />
        </div>
        <div>
            <input type="submit" value="렌트" />
        </div>



	</form>
</body>
</html>