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
	<h1>렌트 반납</h1>
	<form action="${pageContext.request.contextPath}/rent/carReturn" method="post">
	    <div>
            <label>RENTID : </label>
            <input type="text" name="rentId" />
	    </div>
        <div>
            <input type="submit" value="반납" />
        </div>



	</form>
</body>
</html>