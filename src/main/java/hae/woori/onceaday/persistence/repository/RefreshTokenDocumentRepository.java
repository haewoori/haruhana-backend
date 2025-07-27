package hae.woori.onceaday.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import hae.woori.onceaday.persistence.document.RefreshTokenDocument;

public interface RefreshTokenDocumentRepository extends MongoRepository<RefreshTokenDocument, String> {
}
