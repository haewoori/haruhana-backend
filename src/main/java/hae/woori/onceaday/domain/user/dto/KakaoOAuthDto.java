package hae.woori.onceaday.domain.user.dto;

import hae.woori.onceaday.auth.vo.TokenVo;
import io.swagger.v3.oas.annotations.media.Schema;

public class KakaoOAuthDto {
	@Schema(name = "KakaoOAuthDto.Request")
	public record Request(
		String authCode
	) {
	}
	@Schema(name = "KakaoOAuthDto.Response")
	public record Response(
		TokenVo tokens
	) {
	}
}
