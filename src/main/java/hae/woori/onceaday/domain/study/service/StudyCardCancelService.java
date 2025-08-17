package hae.woori.onceaday.domain.study.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.study.dto.StudyCardCancelDto;
import hae.woori.onceaday.domain.study.external.StudyUserGateway;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import hae.woori.onceaday.persistence.repository.StudyCardDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyCardCancelService implements SimpleService<StudyCardCancelDto.RequestWrapper, StudyCardCancelDto.Response> {

    private final StudyCardDocumentRepository studyCardDocumentRepository;
    private final StudyUserGateway studyUserGateway;

    @Override
    @Transactional
    public StudyCardCancelDto.Response run(StudyCardCancelDto.RequestWrapper request) {
        if (!studyUserGateway.checkUserExistsById(request.userId())) {
            throw new ClientSideException("User does not exist: " + request.userId());
        }

        StudyCardDocument studyCardDocument = studyCardDocumentRepository.findById(request.studyCardId())
                .orElseThrow(() -> new ClientSideException("Study card not found: " + request.studyCardId()));

        // 미참가자일 시 성공으로 간주
        if (!studyCardDocument.hasParticipant(request.userId())) {
            return new StudyCardCancelDto.Response(true, false);
        }

        studyCardDocument.getParticipantIds().remove(request.userId());
        if(!studyCardDocument.isAvailable() && studyCardDocument.getParticipantIds().size() < studyCardDocument.getMaxParticipants()) {
            studyCardDocument.updateAvailableStatus(true);
        }

        studyCardDocumentRepository.save(studyCardDocument);

        return new StudyCardCancelDto.Response(true, false);
    }
}
