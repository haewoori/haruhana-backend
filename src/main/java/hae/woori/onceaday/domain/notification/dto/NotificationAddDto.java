package hae.woori.onceaday.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class NotificationAddDto {
	@Schema(name = "NotificationAddDto.Request")
	public record Request(
		String message
	) {
	}
	@Schema(name = "NotificationAddDto.Response")
	public record Response() {
	}
}
