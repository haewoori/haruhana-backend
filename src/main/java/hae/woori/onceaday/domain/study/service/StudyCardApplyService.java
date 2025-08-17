package hae.woori.onceaday.domain.study.service;

import com.mongodb.client.result.UpdateResult;
import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.study.dto.StudyCardApplyDto;
import hae.woori.onceaday.domain.study.external.StudyUserGateway;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import hae.woori.onceaday.persistence.repository.StudyCardDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyCardApplyService implements SimpleService<StudyCardApplyDto.RequestWrapper, StudyCardApplyDto.Response> {

    private final StudyCardDocumentRepository studyCardDocumentRepository;
    private final StudyUserGateway studyUserGateway;
    private final MongoTemplate mongoTemplate;

    @Override
    public StudyCardApplyDto.Response run(StudyCardApplyDto.RequestWrapper req) {
        // 사용자 존재 체크
        if (!studyUserGateway.checkUserExistsById(req.userId())) {
            throw new ClientSideException("User does not exist: " + req.userId());
        }

        // 카드 존재/상태 체크
        StudyCardDocument card = studyCardDocumentRepository.findById(req.studyCardId())
                .orElseThrow(() -> new ClientSideException("Study card not found: " + req.studyCardId()));

        // 참가자일 시 성공으로 간주 (idempotent)
        if (card.hasParticipant(req.userId())) {
            return new StudyCardApplyDto.Response(true, true);
        }

        Query q = Query.query(Criteria.where("_id").is(req.studyCardId()));
        Update u = new Update().addToSet("participant_ids", req.userId());
        UpdateResult r = mongoTemplate.updateFirst(q, u, StudyCardDocument.class);

        boolean participated = r.getModifiedCount() > 0 || studyCardDocumentRepository
                .findById(req.studyCardId()).map(c -> c.hasParticipant(req.userId())).orElse(false);

        return new StudyCardApplyDto.Response(true, participated);
    }
}


