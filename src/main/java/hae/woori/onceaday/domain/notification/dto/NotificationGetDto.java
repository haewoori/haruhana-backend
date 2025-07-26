package hae.woori.onceaday.domain.notification.dto;

public class NotificationGetDto {

	public record Request(
	) {
	}

	public record Response(
		String message
	) {
	}
}
