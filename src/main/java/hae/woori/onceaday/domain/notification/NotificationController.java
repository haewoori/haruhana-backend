package hae.woori.onceaday.domain.notification;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hae.woori.onceaday.domain.notification.dto.NotificationAddDto;
import hae.woori.onceaday.domain.notification.dto.NotificationGetDto;
import hae.woori.onceaday.domain.notification.service.NotificationAddService;
import hae.woori.onceaday.domain.notification.service.NotificationGetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

	private final NotificationAddService notificationAddService;
	private final NotificationGetService notificationGetService;

	@Operation(description = "알림을 추가합니다. 가장 마지막에 추가된 알림이 보이고, 빈 문자열을 보내면 알림이 사라집니다")
	@PostMapping("/add")
	public NotificationAddDto.Response addNotification(@RequestBody NotificationAddDto.Request request) {
		return notificationAddService.run(request);
	}

	@Operation(description = "알림을 적을 메세지 제공. 빈 문자열이면 알림창을 지워야함")
	@GetMapping("/get")
	public NotificationGetDto.Response getNotifications() {
		return notificationGetService.run(new NotificationGetDto.Request());
	}
}
