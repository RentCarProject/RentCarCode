<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
     <script>
         // 페이지 로드 시 URL 파라미터에서 'error' 값을 확인
         window.onload = function() {
             const urlParams = new URLSearchParams(window.location.search);
             const errorMessage = urlParams.get('error');

             // 'error' 파라미터가 존재하면 alert로 메시지 표시
             if (errorMessage) {
                 alert("로그인 실패: " + decodeURIComponent(errorMessage));
             }
         }
     </script>
</head>
<body>
    <h2>로그인</h2>
    <form action="/login" method="POST">
        <label for="memberId">아이디:</label>
        <input type="text" id="memberId" name="memberId" required><br><br>

        <label for="memberPw">비밀번호:</label>
        <input type="password" id="memberPw" name="memberPw" required><br><br>

        <button type="submit">로그인</button>
    </form>

    <a href="/signup">회원가입</a> <!-- 회원가입 페이지로 갈 수 있는 링크 -->
</body>
</html>
