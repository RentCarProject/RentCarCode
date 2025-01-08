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
	<form action="${pageContext.request.contextPath}/review/reviewMain" method="post">

    <table border="1">
        <thead>
            <tr>
                <th> 번호 </th>
                <th>제목</th>
                <th> - </th>
                <th>평점</th>
                <th>게시일</th>
                <th>작성자</th>
                <th>이미지</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="review" items="${rList}">
                <tr>
                    <td>${review.reviewNumber}</td>
                    <td onClick="location.href='/review/reviewRead';">${review.title}</td>
                    <td>${review.content}</td>
                    <td>${review.rating}</td>
                    <td>${review.date}</td>
                    <td>${review.user.memberId}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

	<div>
        <button type="button" onClick="location.href='/review/addReview'">리뷰 작성</button>
    </div>



	</form>
</body>
</html>