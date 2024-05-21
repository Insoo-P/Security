<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
</head>
<body>
    <form method="post" action="/action/signUp">
        <input type="text" name="id" placeholder="아이디">
        <br/>
        <input type="password" name="pw" maxlength="32" placeholder="비밀번호">
        <br/>
        <button type="submit">회원가입</button>
    </form>
</body>
</html>
