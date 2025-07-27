package hae.woori.onceaday.persistence.repository;

import java.util.Optional;

import hae.woori.onceaday.persistence.document.UserDocument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {
	Optional<UserDocument> findByEmail(String email);
}
