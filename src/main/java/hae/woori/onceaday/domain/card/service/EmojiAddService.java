package hae.woori.onceaday.domain.card.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.EmojiAddDto;
import hae.woori.onceaday.domain.card.external.UserGateway;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.document.EmojiDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import hae.woori.onceaday.persistence.repository.EmojiDocumentRepository;
import hae.woori.onceaday.persistence.vo.EmojiRecord;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmojiAddService implements SimpleService<EmojiAddDto.Request, EmojiAddDto.Response> {

	private final CardDocumentRepository cardDocumentRepository;
	private final EmojiDocumentRepository emojiDocumentRepository;
	private final UserGateway userGateway;

	@Override
	public EmojiAddDto.Response run(EmojiAddDto.Request input) {
		CardDocument cardDocument = cardDocumentRepository.findById(input.cardId())
			.orElseThrow(() -> new ClientSideException("Card not found with id: " + input.cardId()));
		if (!userGateway.checkUserExistsById(input.userId())) {
			throw new ClientSideException("User does not exist with userId: " + input.userId());
		}

		EmojiDocument emojiDocument = emojiDocumentRepository.findById(input.emojiId())
			.orElseThrow(() -> new ClientSideException("Emoji does not exist with emojiId: " + input.emojiId()));

		EmojiRecord recordToAdd = new EmojiRecord(input.emojiId(), emojiDocument.getEmojiUrl(), input.userId());
		cardDocument.getEmojiRecords()
			.stream()
			.filter(emojiRecord -> emojiRecord.userId().equals(input.userId()))
			.findFirst()
			.ifPresentOrElse(originalRecord -> {
				cardDocument.getEmojiRecords().remove(originalRecord);
				cardDocument.getEmojiRecords().add(recordToAdd);
			}, () -> cardDocument.getEmojiRecords().add(recordToAdd));

		cardDocumentRepository.save(cardDocument);
		return new EmojiAddDto.Response();
	}
}
