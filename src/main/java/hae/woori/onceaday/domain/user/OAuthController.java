package hae.woori.onceaday.domain.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hae.woori.onceaday.domain.user.dto.KakaoOAuthDto;
import hae.woori.onceaday.domain.user.service.KakaoOAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuthController {

	private final KakaoOAuthService kakaoOAuthService;

	@Value("${oauth.redirect-uri}")
	private String clientRedirectUri;

	@GetMapping("/oauth/kakao")
	public void getAuthKakao(@RequestParam String code, HttpServletResponse response) throws IOException {
		KakaoOAuthDto.Response responseData = kakaoOAuthService.run(new KakaoOAuthDto.Request(code));

		Cookie cookie = new Cookie("refreshToken", responseData.tokens().refreshToken());
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge((int)(responseData.tokens().refreshTokenExpireIn().getTime() - System.currentTimeMillis()) / 1000);

		response.addCookie(cookie);
		response.sendRedirect(clientRedirectUri + "?token=" + responseData.tokens().accessToken());
	}
}
