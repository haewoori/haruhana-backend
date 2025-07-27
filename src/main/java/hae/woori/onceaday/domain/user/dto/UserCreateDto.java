package hae.woori.onceaday.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class UserCreateDto {
	@Schema(name = "UserCreateDto.Request")
	public record Request(
		@NotNull String email,
		@NotNull String imageUrl,
		@NotNull String nickname,
		Integer gender
	) {
	}
	@Schema(name = "UserCreateDto.Response")
	public record Response() {
	}
}
