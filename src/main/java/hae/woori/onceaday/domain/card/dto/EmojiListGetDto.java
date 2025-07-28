package hae.woori.onceaday.domain.card.dto;

import java.util.List;

import hae.woori.onceaday.domain.card.vo.EmojiVo;

public class EmojiListGetDto {

	public record Request(
	) {
	}
	public record Response(
		List<EmojiVo> emojiVoList
	) {
	}
}
