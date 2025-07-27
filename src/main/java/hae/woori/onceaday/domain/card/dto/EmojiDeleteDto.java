package hae.woori.onceaday.domain.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmojiDeleteDto {

	@Schema(name = "EmojiDeleteDto.Request")
	public record Request(
		String cardId
	) { }

	public record RequestWrapper(
		String userId,
		String cardId
	) {}
	public static RequestWrapper toRequestWrapper(String userId, Request request) {
		return new RequestWrapper(userId, request.cardId());
	}

	@Schema(name = "EmojiDeleteDto.Response")
	public record Response() {
	}
}
