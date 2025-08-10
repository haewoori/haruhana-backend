package hae.woori.onceaday.domain.study.external;

import org.springframework.stereotype.Component;

import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyUserGateway {

	private final UserDocumentRepository userDocumentRepository;

	public boolean checkUserExistsById(String userId) {
		return userDocumentRepository.existsById(userId);
	}
}