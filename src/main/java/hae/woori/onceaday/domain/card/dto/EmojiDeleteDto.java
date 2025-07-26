package hae.woori.onceaday.domain.card.dto;

public class EmojiDeleteDto {

	public record Request(
		//TODO: Access Token이 있다면 그걸 기반으로 user 매핑을 하고, 저장해야 할 것.
		String userId,
		String cardId
	) {

	}

	public record Response() {
	}
}
