<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>메인 페이지</title>
</head>
<body>
    <h1>환영합니다! 메인 페이지입니다.</h1>
    <p>여기서는 사용자에게 다양한 서비스를 제공합니다.</p>

    <!-- 로그인된 사용자 표시 -->
    <p>로그인된 사용자: ${sessionScope.loggedInUser}</p>

    <!-- 로그아웃 버튼 -->
    <form action="/logout" method="post">
        <button type="submit">로그아웃</button>
    </form>
</body>
</html>
