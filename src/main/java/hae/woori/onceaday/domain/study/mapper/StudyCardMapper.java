package hae.woori.onceaday.domain.study.mapper;

import hae.woori.onceaday.domain.study.dto.StudyCardCreateDto;
import hae.woori.onceaday.domain.study.dto.StudyCardDto;
import hae.woori.onceaday.domain.study.vo.StudyUserProfileVo;
import hae.woori.onceaday.persistence.document.StudyCardDocument;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface StudyCardMapper {
    StudyCardDocument createRequestWrapperToStudyCardDocument(StudyCardCreateDto.RequestWrapper request);

    @Mapping(target = "studyCardId", source = "studyCardDocument.id")
    @Mapping(target = "isMine",
            expression = "java(studyCardDocument.getUserId().equals(userId))")
    StudyCardDto studyCardDocumentToStudyCardDto(StudyCardDocument studyCardDocument, @Context String userId, @Context
        StudyUserProfileVo userProfile, @Context ParticipantResolver participantResolver);

    @AfterMapping
    default void fillParticipants(@MappingTarget StudyCardDto dto,
                                  StudyCardDocument doc,
                                  @Context String userId,
                                  @Context ParticipantResolver resolver) {

        List<String> ids = Optional.ofNullable(doc.getParticipantIds()).orElseGet(List::of);
        dto.setParticipantIds(ids);

        // 참여 여부
        dto.setParticipated(userId != null && ids.contains(userId));

        // ID → 이름 변환 (원본 ID 순서 유지)
        Map<String, String> idToName = resolver.resolveNames(ids);
        List<String> names = ids.stream()
                .map(idToName::get)
                .filter(Objects::nonNull)
                .toList();
        dto.setParticipantNames(names);
    }


    @AfterMapping
    default void setIsPublic(@MappingTarget StudyCardDocument studyCardDocument) {
        studyCardDocument.updatePublicStatus(true);
    }
}
