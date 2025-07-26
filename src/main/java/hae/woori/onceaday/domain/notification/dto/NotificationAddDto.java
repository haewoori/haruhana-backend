package hae.woori.onceaday.domain.notification.dto;

public class NotificationAddDto {

	public record Request(
		String message
	) {
	}

	public record Response() {
	}
}
