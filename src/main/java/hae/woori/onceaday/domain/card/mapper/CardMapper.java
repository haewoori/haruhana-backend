package hae.woori.onceaday.domain.card.mapper;

import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.persistence.document.CardDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDocument createRequestToCardDocument(MyCardCreateDto.Request request);
}
