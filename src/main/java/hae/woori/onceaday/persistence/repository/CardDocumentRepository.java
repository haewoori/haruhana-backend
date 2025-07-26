package hae.woori.onceaday.persistence.repository;

import hae.woori.onceaday.persistence.document.CardDocument;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardDocumentRepository extends MongoRepository<CardDocument, String> {
}
