package hae.woori.onceaday.domain.card.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 프로필 정보")
public record UserProfileVo(
	String username,
	String profileImageUrl
) {
}
