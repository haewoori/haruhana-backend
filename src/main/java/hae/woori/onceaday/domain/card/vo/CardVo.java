package hae.woori.onceaday.domain.card.vo;

import java.util.List;

import hae.woori.onceaday.persistence.vo.EmojiRecord;

public record CardVo(
	String cardId,
	String content,
	UserProfileVo userProfile,
	List<EmojiRecord> emojiRecords,
	String bgColor,
	String createdAt
) {
}
