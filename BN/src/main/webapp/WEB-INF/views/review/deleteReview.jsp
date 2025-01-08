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
	<h1>리뷰</h1>
	<form action="${pageContext.request.contextPath}/review/deleteReview" method="post">

	        <div>
	            <input type="test" name="reviewNumber" />
	        </div>
            <div>
	            <input type="submit" value="리뷰삭제" />
	        </div>



	</form>
</body>
</html>