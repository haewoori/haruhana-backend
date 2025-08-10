package hae.woori.onceaday.domain.card.vo;

import java.util.List;

import hae.woori.onceaday.domain.card.dto.EmojiRecordDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "단일 카드 정보")
public record CardVo(
	String cardId,
	String content,
	CardUserProfileVo userProfile,
	List<EmojiRecordDto> emojiRecords,
	String bgColor,
	String createdAt
) {
}
