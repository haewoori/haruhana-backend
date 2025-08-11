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

@Mapper(componentModel = "spring")
public interface StudyCardMapper {
    StudyCardDocument createRequestWrapperToStudyCardDocument(StudyCardCreateDto.RequestWrapper request);

    @Mapping(target = "studyCardId", source = "studyCardDocument.id")
    StudyCardDto studyCardDocumentToStudyCardDto(StudyCardDocument studyCardDocument, @Context String userId, @Context
        StudyUserProfileVo userProfile);

    @AfterMapping
    default void isRegistered(@MappingTarget StudyCardDto studyCardDto, StudyCardDocument studyCardDocument, @Context String userId, @Context StudyUserProfileVo userProfile) {
        //TODO: 여기에 AND절로 신청내역을 보고 있는지도 확인해야함
        studyCardDto.setRegistered(studyCardDocument.getUserId().equals(userId));
        studyCardDto.setUserProfile(userProfile);
    }

    @AfterMapping
    default void setIsPublic(@MappingTarget StudyCardDocument studyCardDocument) {
        studyCardDocument.updatePublicStatus(true);
    }
}
