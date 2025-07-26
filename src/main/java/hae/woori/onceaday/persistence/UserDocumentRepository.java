package hae.woori.onceaday.persistence;

import hae.woori.onceaday.persistence.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {
}
