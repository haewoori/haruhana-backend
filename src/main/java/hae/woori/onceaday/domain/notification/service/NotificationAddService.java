package hae.woori.onceaday.domain.notification.service;

import org.springframework.stereotype.Service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.notification.dto.NotificationAddDto;
import hae.woori.onceaday.persistence.document.NotificationDocument;
import hae.woori.onceaday.persistence.repository.NotificationDocumentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationAddService implements SimpleService<NotificationAddDto.Request, NotificationAddDto.Response> {

	private final NotificationDocumentRepository notificationDocumentRepository;

	@Override
	public NotificationAddDto.Response run(NotificationAddDto.Request input) {
		NotificationDocument document = NotificationDocument.builder()
			.message(input.message())
			.build();

		notificationDocumentRepository.save(document);
		return new NotificationAddDto.Response();
	}
}
