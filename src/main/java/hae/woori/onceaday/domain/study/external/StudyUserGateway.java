package hae.woori.onceaday.domain.study.external;

import org.springframework.stereotype.Component;

import hae.woori.onceaday.domain.study.vo.StudyUserProfileVo;
import hae.woori.onceaday.persistence.document.UserDocument;
import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyUserGateway {

	private final UserDocumentRepository userDocumentRepository;

	public boolean checkUserExistsById(String userId) {
		return userDocumentRepository.existsById(userId);
	}

	public StudyUserProfileVo getUserProfileById(String userId) {
		UserDocument document = userDocumentRepository.findById(userId).orElse(null);
		if (document == null) {
			//TODO: default image로 변경
			return new StudyUserProfileVo(null, "default_image");
		}

		return new StudyUserProfileVo(document.getNickname(), document.getImageUrl());
	}
}