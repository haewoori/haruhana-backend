package hae.woori.onceaday.domain.user.dto;

import hae.woori.onceaday.auth.vo.TokenVo;

public class KakaoOAuthDto {

	public record Request(
		String authCode
	) {
	}

	public record Response(
		TokenVo tokens
	) {
	}
}
