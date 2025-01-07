<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="/favicon.ico" type="image/x-icon">
<meta charset="UTF-8">
<title>대여목록</title>
</head>
<body>
    <h1>대여목록</h1>
    <form action="${pageContext.request.contextPath}/rent/carList" method="post">
        <div>
            <label>USERNAME : </label>
            <input type="text" name="memberId" />
        </div>
        <div>
            <input type="submit" value="렌트 조회">
        </div>
    </form>

    <table border="1px solid">
        <thead>
            <tr>
                <th>차량명</th>
                <th>이름</th>
                <th>반납일</th>
            </tr>
        </thead>
        <tbody>
           <c:forEach var="rent" items="${rentList}">
               <tr>
                   <td onClick="openPopUp()">${rent[0]}
                   <td>${rent[1]}
                   <td>${rent[2]}
               </tr>
           </c:forEach>
        </tbody>
    </table>

    <script type="text/javascript" >
        function openPopUp(){
            window.open("/cars/details?rentId=${rent[0]}", "newWindow", "width=500, height=300");
        }
    </script>
</body>
</html>
