<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/header.jsp" %>

<!-- ✅ 로그아웃 버튼 (화면 오른쪽 위에 고정) -->
<div style="position: absolute; top: 100px; right: 40px; z-index: 9999;">
    <a href="javascript:kakaoLogout();">
        <button style="padding: 10px 20px; background-color: #FEE500; border: none; border-radius: 4px; font-weight: bold;">
            카카오 로그아웃
        </button>
    </a>
</div>

<div class="container mt-5">
    <h3 class="mb-3">💬 헬스봇 챗봇</h3>
    <p>로그인된 사용자만 챗봇을 사용할 수 있어요!</p>
</div>

<!-- ✅ 카카오 챗봇 iframe -->
<iframe
    src="https://chatbot.kakao.com/web/6854d2bcb721652da7a8c842"
    style="position: fixed; bottom: 20px; right: 20px; width: 360px; height: 520px; border: none; z-index: 1000;">
</iframe>

<!-- ✅ 로그아웃 스크립트 추가 -->
<script>
    function kakaoLogout() {
        const REST_API_KEY = '258fb141165b1f58e8cb1847169c112f';
        const LOGOUT_REDIRECT_URI = 'http://localhost:8080/login';  // 로그아웃 후 돌아올 곳

        const logoutUrl = "https://kauth.kakao.com/oauth/logout"
            + "?client_id=" + REST_API_KEY
            + "&logout_redirect_uri=" + LOGOUT_REDIRECT_URI;

        console.log("🚪 로그아웃 URL:", logoutUrl);
        window.location.href = logoutUrl;
    }
</script>

<%@ include file="../common/footer.jsp" %>
