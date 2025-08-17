package hae.woori.onceaday.domain.study.service;

import hae.woori.onceaday.domain.study.mapper.ParticipantResolver;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.study.dto.StudyCardDto;
import hae.woori.onceaday.domain.study.dto.StudyCardListDto;
import hae.woori.onceaday.domain.study.external.StudyUserGateway;
import hae.woori.onceaday.domain.study.mapper.StudyCardMapper;
import hae.woori.onceaday.domain.study.vo.StudyUserProfileVo;
import hae.woori.onceaday.persistence.dao.StudyCardDocumentDao;
import hae.woori.onceaday.persistence.document.StudyCardDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyCardListService implements SimpleService<StudyCardListDto.RequestWrapper, StudyCardListDto.Response> {

	private final StudyCardDocumentDao studyCardDocumentDao;
	private final StudyUserGateway studyUserGateway;
	private final StudyCardMapper studyCardMapper;
	private final ParticipantResolver participantResolver;

	@Override
	@Transactional(readOnly = true)
	public StudyCardListDto.Response run(StudyCardListDto.RequestWrapper request) {
		Page<StudyCardDocument> resultPage = studyCardDocumentDao.findAllCards(request.pageable(), request.isAvailable());

		Page<StudyCardDto> mappedResult = resultPage.map(document -> {
				StudyUserProfileVo userProfile = studyUserGateway.getUserProfileById(document.getUserId());
				return studyCardMapper.studyCardDocumentToStudyCardDto(document, request.userId(), userProfile, participantResolver);
			}
		);

		return new StudyCardListDto.Response(mappedResult);
	}
}
