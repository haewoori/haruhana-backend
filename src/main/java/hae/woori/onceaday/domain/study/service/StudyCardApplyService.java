package hae.woori.onceaday.domain.study.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.study.dto.StudyCardApplyDto;
import hae.woori.onceaday.domain.study.external.StudyUserGateway;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import hae.woori.onceaday.persistence.repository.StudyCardDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyCardApplyService implements SimpleService<StudyCardApplyDto.RequestWrapper, StudyCardApplyDto.Response> {

    private final StudyCardDocumentRepository studyCardDocumentRepository;
    private final StudyUserGateway studyUserGateway;

    @Override
    @Transactional
    public StudyCardApplyDto.Response
    run(StudyCardApplyDto.RequestWrapper request) {
        // 사용자 존재 체크
        if (!studyUserGateway.checkUserExistsById(request.userId())) {
            throw new ClientSideException("User does not exist: " + request.userId());
        }

        // 카드 존재/상태 체크
        StudyCardDocument studyCardDocument = studyCardDocumentRepository.findById(request.studyCardId())
                .orElseThrow(() -> new ClientSideException("Study card not found: " + request.studyCardId()));

        // 참가자일 시 성공으로 간주 (idempotent)
        if (studyCardDocument.hasParticipant(request.userId())) {
            return new StudyCardApplyDto.Response(true, true);
        }

        if(!studyCardDocument.isAvailable()) {
            throw new ClientSideException("Study 모집인원이 모두 찼습니다: " + request.studyCardId());
        }

        studyCardDocument.getParticipantIds().add(request.userId());
        if(studyCardDocument.getParticipantIds().size() == studyCardDocument.getMaxParticipants()) {
            studyCardDocument.updateAvailableStatus(false); // 인원이 다 찼으므로 공개 상태 변경
        }

        studyCardDocumentRepository.save(studyCardDocument);

        return new StudyCardApplyDto.Response(true, true);
    }
}


