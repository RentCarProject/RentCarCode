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
	<h1>리뷰 작성</h1>
	<form action="${pageContext.request.contextPath}/review/updateReview" method="post">
        <div>
            <label>번호 : </label>
            <input type="text" name="reviewNumber" />
        </div>
        <div>
            <label>제목 : </label>
            <input type="text" name="title" />
        </div>
        <div>
            <label>내용 : </label>
            <input type="text" name="content" />
        </div>
        <div>
            <label>별점 : </label>
            <input type="number" name="rating" />
        </div>
        <div>
            <input type="submit" value="리뷰수정" />
        </div>

	</form>
</body>
</html>