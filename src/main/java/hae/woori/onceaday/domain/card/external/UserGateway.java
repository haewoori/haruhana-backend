package hae.woori.onceaday.domain.card.external;

import hae.woori.onceaday.domain.card.vo.CardUserProfileVo;
import hae.woori.onceaday.persistence.document.UserDocument;
import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGateway {

	private final UserDocumentRepository userDocumentRepository;

	public boolean checkUserExistsById(String userId) {
		return userDocumentRepository.existsById(userId);
	}

	public CardUserProfileVo getUserProfileById(String userId) {
		UserDocument document = userDocumentRepository.findById(userId).orElse(null);
		if (document == null) {
			//TODO: default image로 변경
			return new CardUserProfileVo(null, "default_image");
		}

		return new CardUserProfileVo(document.getNickname(), document.getImageUrl());
	}
}