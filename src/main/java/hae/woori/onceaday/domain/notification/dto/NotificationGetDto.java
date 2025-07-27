package hae.woori.onceaday.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class NotificationGetDto {
	@Schema(name = "NotificationGetDto.Request")
	public record Request(
	) {
	}
	@Schema(name = "NotificationGetDto.Response")
	public record Response(
		String message
	) {
	}
}
