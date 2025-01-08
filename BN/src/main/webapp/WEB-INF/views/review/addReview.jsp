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
	<form action="${pageContext.request.contextPath}/review/addReview" method="post">
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
            <label>작성자 : </label>
            <input type="text" name="memberId" />
        </div>
        <div>
            <label>이미지 : </label>
            <input type="file" accept="image/*" />
        </div>
        <div>
            <input type="submit" value="리뷰작성" />
        </div>






	</form>
</body>
</html>