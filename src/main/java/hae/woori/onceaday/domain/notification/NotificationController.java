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
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

	private final NotificationAddService notificationAddService;
	private final NotificationGetService notificationGetService;

	// 알림 추가 API
	    @PostMapping("/add")
	    public NotificationAddDto.Response addNotification(@RequestBody NotificationAddDto.Request request) {
	        return notificationAddService.run(request);
	    }

	    // 알림 전체 조회 API
	    @GetMapping("/get")
	    public NotificationGetDto.Response getNotifications() {
	        return notificationGetService.run(new NotificationGetDto.Request());
	    }
}
