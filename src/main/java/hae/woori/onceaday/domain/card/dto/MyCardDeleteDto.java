package hae.woori.onceaday.domain.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MyCardDeleteDto {

	@Schema(name = "MyCardDeleteDto.Request")
	public record Request(
	) {
	}

	public static RequestWrapper requestWrapperFrom(String userId, String cardId) {
		return new RequestWrapper(userId, cardId);
	}

	public record RequestWrapper(
		String userId,
		String cardId
	) {
	}
	@Schema(name = "MyCardDeleteDto.Response")
	public record Response() {
	}
}
