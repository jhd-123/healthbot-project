<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/header.jsp" %>

<div class="container mt-5">
    <h3 class="mb-3">💬 헬스봇 챗봇</h3>
    <p>로그인된 사용자만 챗봇을 사용할 수 있어요!</p>
</div>

<!-- ✅ 카카오 챗봇 iframe -->
<iframe
    src="https://chatbot.kakao.com/web/6854d2bcb721652da7a8c842"
    style="position: fixed; bottom: 20px; right: 20px; width: 360px; height: 520px; border: none; z-index: 9999;">
</iframe>

<%@ include file="../common/footer.jsp" %>
