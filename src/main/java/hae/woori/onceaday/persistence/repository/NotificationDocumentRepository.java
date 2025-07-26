package hae.woori.onceaday.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import hae.woori.onceaday.persistence.document.NotificationDocument;

public interface NotificationDocumentRepository extends MongoRepository<NotificationDocument, String> {

	Optional<NotificationDocument> findFirstByOrderByCreatedTimeDesc();
}
