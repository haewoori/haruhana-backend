package hae.woori.onceaday.domain.card.dto;

public class EmojiAddDto {

	public record Request(
		//TODO: Access Token이 있다면 그걸 기반으로 user 매핑을 하고, 저장해야 할 것.
		String userId,
		String cardId,
		String emojiId
	) {

	}

	public record Response() {
		// Define output fields after creating a card
	}
}
