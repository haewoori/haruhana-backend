package hae.woori.onceaday.domain.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MyCardCreateDto {

	@Schema(name = "MyCardCreateDto.Request")
	public record Request(
		@Schema(description = "60자 이하의 내용") @Size(max = 60) String content,
		@Schema(description = "#FFFFFF 포맷에 맞는 카드 배경 색상") @Pattern(regexp = "#([0-9a-fA-F]{6})") String bgColor
	) {
	}

	public record RequestWrapper(
		String content,
		String bgColor,
		String userId
	) {}

	public static RequestWrapper toRequestWrapper(Request request, String userId) {
		return new RequestWrapper(request.content(), request.bgColor(), userId);
	}

	@Schema(name = "MyCardCreateDto.Response")
	public record Response() {
		// Define output fields after creating a card
	}
}
