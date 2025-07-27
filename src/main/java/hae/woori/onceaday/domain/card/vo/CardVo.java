package hae.woori.onceaday.domain.card.vo;

import java.util.List;

import hae.woori.onceaday.persistence.vo.EmojiRecord;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "단일 카드 정보")
public record CardVo(
	String cardId,
	String content,
	UserProfileVo userProfile,
	List<EmojiRecord> emojiRecords,
	String bgColor,
	String createdAt
) {
}
