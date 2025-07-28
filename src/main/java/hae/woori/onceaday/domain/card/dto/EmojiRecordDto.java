package hae.woori.onceaday.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "이모지 기록 단건 정보")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmojiRecordDto {
	@NotNull
	private String emojiId;
	@NotNull
	private String image;
	@NotNull
	private String userId;
	private Boolean isMine;
}