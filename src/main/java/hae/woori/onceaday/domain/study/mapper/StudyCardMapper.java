package hae.woori.onceaday.domain.study.mapper;

import hae.woori.onceaday.domain.study.dto.StudyCardCreateDto;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyCardMapper {
    StudyCardDocument createRequestWrapperToStudyCardDocument(StudyCardCreateDto.RequestWrapper request);
}
