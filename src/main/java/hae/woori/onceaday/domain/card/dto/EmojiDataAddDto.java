package hae.woori.onceaday.domain.card.dto;

import hae.woori.onceaday.domain.card.vo.EmojiVo;

public class EmojiDataAddDto {

	public record Request(
		String emoji
	) {
	}
	public record Response(
		String emojiId,
		String emoji
	) {
	}
}
