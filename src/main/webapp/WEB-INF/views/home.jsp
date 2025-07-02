<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>홈</title></head>
<body>
    <h2>🎉 로그인 성공!</h2>

    <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
            <p>닉네임: ${sessionScope.loginUser.nickname}</p>
            <p>이메일: ${sessionScope.loginUser.email}</p>
            <p>카카오 ID: ${sessionScope.loginUser.id}</p>
        </c:when>
        <c:otherwise>
            <p>로그인 정보를 찾을 수 없습니다.</p>
        </c:otherwise>
    </c:choose>

    <a href="/logout">로그아웃</a>
</body>
</html>
