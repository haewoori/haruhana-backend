package hae.woori.onceaday.domain.study.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.external.UserGateway;
import hae.woori.onceaday.domain.study.dto.StudyCardCreateDto;
import hae.woori.onceaday.domain.study.mapper.StudyCardMapper;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import hae.woori.onceaday.persistence.repository.StudyCardDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyCardCreateService implements SimpleService<StudyCardCreateDto.RequestWrapper, StudyCardCreateDto.Response> {

    private final StudyCardMapper studyCardMapper;
    private final StudyCardDocumentRepository studyCardDocumentRepository;
    private final UserGateway userGateway;

    @Override
    public StudyCardCreateDto.Response run(StudyCardCreateDto.RequestWrapper request) {
        if (!userGateway.checkUserExistsById(request.userId())) {
            throw new IllegalArgumentException("User does not exist with userId: " + request.userId());
        }

        StudyCardDocument studyCardDocument = studyCardMapper.createRequestWrapperToStudyCardDocument(request);
        studyCardDocumentRepository.save(studyCardDocument);
        return new StudyCardCreateDto.Response();
    }
}
