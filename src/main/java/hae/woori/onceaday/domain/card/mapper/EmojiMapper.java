package hae.woori.onceaday.domain.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hae.woori.onceaday.domain.card.vo.EmojiVo;
import hae.woori.onceaday.persistence.document.EmojiDocument;

@Mapper(componentModel = "spring")
public interface EmojiMapper {

	@Mapping(target = "emojiId", source = "id")
	EmojiVo emojiDocumentToEmojiVo(EmojiDocument emojiDocument);
}
