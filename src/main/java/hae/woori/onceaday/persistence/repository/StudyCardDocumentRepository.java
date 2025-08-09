package hae.woori.onceaday.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import hae.woori.onceaday.persistence.document.StudyCardDocument;

public interface StudyCardDocumentRepository extends MongoRepository<StudyCardDocument, String> {
}
