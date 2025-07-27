package hae.woori.onceaday.domain.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MyCardCreateDto {

	@Schema(name = "MyCardCreateDto.Request")
	public record Request(
		//TODO: Access Token이 있다면 그걸 기반으로 user 매핑을 하고, 저장해야 할 것.
		String userId,
		@Schema(description = "60자 이하의 내용") @Size(max = 60) String content,
		@Schema(description = "#FFFFFF 포맷에 맞는 카드 배경 색상") @Pattern(regexp = "#([0-9a-fA-F]{6})") String bgColor
	) {

	}

	@Schema(name = "MyCardCreateDto.Response")
	public record Response() {
		// Define output fields after creating a card
	}
}
