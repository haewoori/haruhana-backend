package hae.woori.onceaday.domain.study.service;

import com.mongodb.client.result.UpdateResult;
import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.study.dto.StudyCardCancelDto;
import hae.woori.onceaday.domain.study.external.StudyUserGateway;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import hae.woori.onceaday.persistence.repository.StudyCardDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyCardCancelService implements SimpleService<StudyCardCancelDto.RequestWrapper, StudyCardCancelDto.Response> {

    private final StudyCardDocumentRepository studyCardDocumentRepository;
    private final StudyUserGateway studyUserGateway;
    private final MongoTemplate mongoTemplate;

    @Override
    public StudyCardCancelDto.Response run(StudyCardCancelDto.RequestWrapper req) {
        if (!studyUserGateway.checkUserExistsById(req.userId())) {
            throw new ClientSideException("User does not exist: " + req.userId());
        }

        StudyCardDocument card = studyCardDocumentRepository.findById(req.cardId())
                .orElseThrow(() -> new ClientSideException("Study card not found: " + req.cardId()));

        // 미참가자일 시 성공으로 간주
        if (!card.hasParticipant(req.userId())) {
            return new StudyCardCancelDto.Response(true, false);
        }

        Query q = Query.query(Criteria.where("_id").is(req.cardId()));
        Update u = new Update().pull("participant_ids", req.userId());
        UpdateResult r = mongoTemplate.updateFirst(q, u, StudyCardDocument.class);

        boolean stillParticipated = studyCardDocumentRepository
                .findById(req.cardId()).map(c -> c.hasParticipant(req.userId())).orElse(false);

        return new StudyCardCancelDto.Response(r.getModifiedCount() >= 0, stillParticipated);
    }
}
