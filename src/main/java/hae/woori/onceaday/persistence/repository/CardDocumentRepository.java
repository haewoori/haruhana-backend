package hae.woori.onceaday.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import hae.woori.onceaday.persistence.document.CardDocument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardDocumentRepository extends MongoRepository<CardDocument, String> {

	List<CardDocument> findByCreateTimeGreaterThanEqualAndCreateTimeLessThan(LocalDateTime start, LocalDateTime end);

}
