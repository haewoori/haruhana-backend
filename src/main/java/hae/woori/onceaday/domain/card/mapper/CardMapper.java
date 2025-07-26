package hae.woori.onceaday.domain.card.mapper;

import java.util.List;

import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.domain.card.vo.CardVo;
import hae.woori.onceaday.domain.card.vo.UserProfileVo;
import hae.woori.onceaday.persistence.document.CardDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDocument createRequestToCardDocument(MyCardCreateDto.Request request);

    List<CardVo> cardDocumentsToCardVos(List<CardDocument> cardDocuments);

    @Mapping(target = "cardId", source = "id")
    @Mapping(target = "createdAt", source = "createTime")
    CardVo cardDocumentsToCardVo(CardDocument cardDocument, UserProfileVo userProfile);
}
