package hae.woori.onceaday.domain.notification.service;

import org.springframework.stereotype.Service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.notification.dto.NotificationGetDto;
import hae.woori.onceaday.persistence.document.NotificationDocument;
import hae.woori.onceaday.persistence.repository.NotificationDocumentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationGetService implements SimpleService<NotificationGetDto.Request, NotificationGetDto.Response> {

	private final NotificationDocumentRepository notificationDocumentRepository;

	@Override
	public NotificationGetDto.Response run(NotificationGetDto.Request input) {
		NotificationDocument document = notificationDocumentRepository.findFirstByOrderByCreatedTimeDesc()
			.orElse(NotificationDocument.builder().build());
		return new NotificationGetDto.Response(document.getMessage());
	}
}
