<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
</head>
<body>
    <h2>회원가입</h2>
    <form action="/api/users/signup" method="post">
        <label for="memberId">아이디:</label><br>
        <input type="text" id="memberId" name="memberId" required><br><br>

        <label for="memberPw">비밀번호:</label><br>
        <input type="password" id="memberPw" name="memberPw" required><br><br>

        <label for="memberNm">이름:</label><br>
        <input type="text" id="memberNm" name="memberNm" required><br><br>

        <label for="memberEmail">이메일:</label><br>
        <input type="email" id="memberEmail" name="memberEmail" required><br><br>

        <label for="role">권한:</label><br>
        <input type="text" id="role" name="role" required><br><br>

        <button type="submit">회원가입</button>
    </form>
</body>
</html>
