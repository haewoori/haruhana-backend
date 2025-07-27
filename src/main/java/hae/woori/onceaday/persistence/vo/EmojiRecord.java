package hae.woori.onceaday.persistence.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이모지 기록 단건 정보")
public record EmojiRecord(
    String emojiId,
    String image,
    String userId
) { }
