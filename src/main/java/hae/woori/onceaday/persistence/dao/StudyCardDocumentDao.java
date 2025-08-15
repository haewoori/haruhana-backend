package hae.woori.onceaday.persistence.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import hae.woori.onceaday.persistence.document.StudyCardDocument;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyCardDocumentDao {

	private final MongoTemplate mongoTemplate;

	public Page<StudyCardDocument> findAllCards(Pageable pageable, Boolean available) {
		Pageable page = PageRequest.of(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			Sort.by(
				Sort.Order.desc("is_available"),
				Sort.Order.desc("created_time")
			)
		);

		Query query = new Query()
			.addCriteria(Criteria.where("is_public").is(true));

		if (available != null) {
			if(available) {
				query.addCriteria(Criteria.where("is_available").is(true));
			}
			else {
				query.addCriteria(Criteria.where("is_available").is(false));
			}
		}

		long total = mongoTemplate.count(query, StudyCardDocument.class);
		List<StudyCardDocument> content = mongoTemplate.find(
			query.with(page), StudyCardDocument.class
		);

		return new PageImpl<>(content, page, total);
	}
}
