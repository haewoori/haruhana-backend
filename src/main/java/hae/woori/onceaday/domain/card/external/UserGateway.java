package hae.woori.onceaday.domain.card.external;

import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGateway {

	private final UserDocumentRepository userDocumentRepository;

	public boolean checkUserExistsById(String userId) {
		return userDocumentRepository.existsByUserId(userId);
	}
}
