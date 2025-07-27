package hae.woori.onceaday.auth.vo;

import java.util.Date;

public record TokenVo(
	String accessToken,
	String refreshToken,
	Date refreshTokenExpireIn
) {
}
