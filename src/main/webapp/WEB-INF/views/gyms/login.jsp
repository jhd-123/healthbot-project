<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>카카오 로그인</title>
</head>
<body>

<h2>헬스봇 로그인</h2>
<p>카카오 계정으로 간편 로그인해보세요.</p>

<!-- ✅ 카카오 로그인 버튼 -->
<a href="javascript:kakaoLogin();">
    <img src="https://developers.kakao.com/assets/img/about/logos/kakaologin/kr/kakao_account_login_btn_medium_wide.png"
         alt="카카오 로그인 버튼" />
</a>

<!-- ✅ 디버깅: login.jsp 렌더링 확인 -->
<script>
    console.log("🔥 이건 진짜 login.jsp입니다.");

    function kakaoLogin() {
        const REST_API_KEY = '290ec4c3477f4e0f7ac02a0b468ddafa';
        const REDIRECT_URI = 'https://b27b-210-183-163-229.ngrok-free.app/oauth/kakao/callback';




        const kakaoAuthUrl =
            "https://kauth.kakao.com/oauth/authorize?response_type=code"
            + "&client_id=" + REST_API_KEY
            + "&redirect_uri=" + REDIRECT_URI;

        // ✅ 디버깅 로그
        console.log("✅ REST_API_KEY 값:", REST_API_KEY);
        console.log("✅ REDIRECT_URI 값:", REDIRECT_URI);
        console.log("➡️ 팝업 URL:", kakaoAuthUrl);

        const popup = window.open(kakaoAuthUrl, "_blank", "width=500,height=600");

        if (!popup) {
            alert("❌ 팝업이 차단되었습니다. 브라우저에서 팝업 허용을 해주세요.");
            console.error("🚫 팝업 차단됨!");
        }
    }
</script>

</body>
</html>
