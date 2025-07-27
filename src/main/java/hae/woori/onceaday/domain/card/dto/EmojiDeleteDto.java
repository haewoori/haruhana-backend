package hae.woori.onceaday.domain.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmojiDeleteDto {

	@Schema(name = "EmojiDeleteDto.Request")
	public record Request(
		//TODO: Access Token이 있다면 그걸 기반으로 user 매핑을 하고, 저장해야 할 것.
		String userId,
		String cardId
	) {

	}
	@Schema(name = "EmojiDeleteDto.Response")
	public record Response() {
	}
}
