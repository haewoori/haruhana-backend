package hae.woori.onceaday.domain.card.mapper;

import java.util.List;

import hae.woori.onceaday.domain.card.dto.EmojiRecordDto;
import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.domain.card.vo.CardUserProfileVo;
import hae.woori.onceaday.domain.card.vo.CardVo;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.vo.EmojiRecord;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDocument createRequestWrapperToCardDocument(MyCardCreateDto.RequestWrapper request);

    List<CardVo> cardDocumentsToCardVos(List<CardDocument> cardDocuments);

    @Mapping(target = "cardId", source = "cardDocument.id")
    @Mapping(target = "createdAt", source = "cardDocument.createTime")
    CardVo cardDocumentsToCardVo(CardDocument cardDocument, CardUserProfileVo userProfile, @Context String userId);

    List<EmojiRecordDto> emojiRecordListToEmojiRecordDtoList(List<EmojiRecord> emojiRecord, @Context String userId);

    @Mapping(target = "isMine", expression = "java(isMine(emojiRecord, userId))")
    EmojiRecordDto emojiRecordToEmojiRecordDto(EmojiRecord emojiRecord, @Context String userId);

    default boolean isMine(EmojiRecord record, String userId) {
        return record.userId().equals(userId);
    }
}
