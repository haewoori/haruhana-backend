package hae.woori.onceaday.domain.study.service;

import org.springframework.stereotype.Component;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.study.dto.StudyCardDeleteDto;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import hae.woori.onceaday.persistence.repository.StudyCardDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudyCardDeleteService
	implements SimpleService<StudyCardDeleteDto.RequestWrapper, StudyCardDeleteDto.Response> {

	private final StudyCardDocumentRepository studyCardDocumentRepository;

	@Override
	public StudyCardDeleteDto.Response run(StudyCardDeleteDto.RequestWrapper request) {
		StudyCardDocument studyCardDocument = studyCardDocumentRepository.findById(request.cardId())
			.orElseThrow(() -> new ClientSideException("Study card not found with id: " + request.cardId()));

		if (!studyCardDocument.getUserId().equals(request.userId())) {
			throw new ClientSideException(
				"Invalid user trying to delete Study card. UserID: " + request.userId() + ", CardID: "
					+ request.cardId());
		}

		studyCardDocument.updatePublicStatus(false);
		studyCardDocumentRepository.save(studyCardDocument);
		return new StudyCardDeleteDto.Response();
	}
}
