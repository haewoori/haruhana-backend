package hae.woori.onceaday.persistence.document;

import lombok.Builder;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "emoji")
public class EmojiDocument {

	@Id
	private String id;
	private String emojiUrl;
}
