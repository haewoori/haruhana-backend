package hae.woori.onceaday.domain.study.mapper;

import hae.woori.onceaday.domain.study.dto.StudyCardCreateDto;
import hae.woori.onceaday.persistence.document.StudyCardDocument;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudyCardMapper {
    StudyCardDocument createRequestWrapperToStudyCardDocument(StudyCardCreateDto.RequestWrapper request);

    @AfterMapping
    default void setIsPublic(@MappingTarget StudyCardDocument studyCardDocument) {
        studyCardDocument.updatePublicStatus(true);
    }
}
