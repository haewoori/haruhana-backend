package hae.woori.onceaday.domain.study.mapper;

import hae.woori.onceaday.domain.study.external.StudyUserGateway;
import hae.woori.onceaday.domain.study.vo.StudyUserProfileVo;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParticipantResolver {

    private final StudyUserGateway studyUserGateway;

    public ParticipantResolver(StudyUserGateway studyUserGateway) {
        this.studyUserGateway = studyUserGateway;
    }

    /**
     * userIds 리스트를 받아서 id → username 매핑을 생성
     */
    public Map<String, String> resolveNames(List<String> userIds) {
        Map<String, String> idToName = new LinkedHashMap<>();
        if (userIds == null) return idToName;

        for (String userId : userIds) {
            StudyUserProfileVo profile = studyUserGateway.getUserProfileById(userId);
            if (profile != null) {
                idToName.put(userId, profile.username());
            } else {
                idToName.put(userId, null);
            }
        }
        return idToName;
    }
}
