package hae.woori.onceaday.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import hae.woori.onceaday.persistence.document.CardDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CardDocumentRepository extends MongoRepository<CardDocument, String> {

	@Query("{ 'createTime': { $gte: ?0, $lt: ?1 } }")
	List<CardDocument> findByCreateTimeBetween(LocalDateTime start, LocalDateTime end);
}
