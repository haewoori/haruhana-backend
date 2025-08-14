package hae.woori.onceaday.persistence.dao;

import java.util.List;

import hae.woori.onceaday.domain.study.vo.AvailabilityFilter;
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

	public Page<StudyCardDocument> findAllCards(Pageable pageable, AvailabilityFilter availability) {
		Query query = new Query()
				.addCriteria(Criteria.where("is_public").is(true));

		Sort sort;

		switch (availability) {
			case ANY -> {
				// 모집 여부 무시 + 최신순
				sort = Sort.by(Sort.Order.desc("created_time"));
				// 필터 추가 없음
			}
			case OPEN -> {
				// 모집 중만 + 최신순
				query.addCriteria(Criteria.where("is_available").is(true));
				sort = Sort.by(Sort.Order.desc("created_time"));
				// (모두 true라 is_available desc 정렬은 의미 없으므로 생략)
			}
			case CLOSED -> {
				// 모집 완료만 + 최신순
				query.addCriteria(Criteria.where("is_available").is(false));
				sort = Sort.by(Sort.Order.desc("created_time"));
			}
			default -> throw new IllegalArgumentException("Unknown availability: " + availability);
		}

		Pageable page = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				sort
		);

		long total = mongoTemplate.count(query, StudyCardDocument.class);
		List<StudyCardDocument> content = mongoTemplate.find(query.with(page), StudyCardDocument.class);

		return new PageImpl<>(content, page, total);
	}
}
