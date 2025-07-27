package hae.woori.onceaday.persistence.repository;

import hae.woori.onceaday.persistence.document.EmojiDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmojiDocumentRepository extends MongoRepository<EmojiDocument, String> {
}
