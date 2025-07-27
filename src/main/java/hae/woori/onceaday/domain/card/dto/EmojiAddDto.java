package hae.woori.onceaday.domain.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmojiAddDto {

	@Schema(name = "EmojiAddDto.Request")
	public record Request(
		String cardId,
		String emojiId
	) {
	}

	public record RequestWrapper(
		String userId,
		String cardId,
		String emojiId
	) {}
	public static RequestWrapper toRequestWrapper(String userId, Request request) {
		return new RequestWrapper(userId, request.cardId(), request.emojiId());
	}

	@Schema(name = "EmojiAddDto.Response")
	public record Response() {
	}
}
