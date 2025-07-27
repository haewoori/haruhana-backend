package hae.woori.onceaday.domain.card.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.EmojiDeleteDto;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import hae.woori.onceaday.persistence.vo.EmojiRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmojiDeleteService implements SimpleService<EmojiDeleteDto.RequestWrapper, EmojiDeleteDto.Response> {

	private final CardDocumentRepository cardDocumentRepository;

	@Override
	public EmojiDeleteDto.Response run(EmojiDeleteDto.RequestWrapper request) {
		CardDocument cardDocument = cardDocumentRepository.findById(request.cardId())
			.orElseThrow(() -> new ClientSideException("Card not found with id: " + request.cardId()));

		List<EmojiRecord> matchedRecords = cardDocument.getEmojiRecords()
			.stream()
			.filter(emojiRecord -> emojiRecord.userId().equals(request.userId()))
			.toList();

		if (matchedRecords.isEmpty()) {
			log.warn("No emojiRecord found for userId: {}", request.userId());
		} else if (matchedRecords.size() > 1) {
			log.warn("Multiple emojiRecords found for userId: {} (count: {})", request.userId(), matchedRecords.size());
		}

		matchedRecords.forEach(cardDocument.getEmojiRecords()::remove);
		cardDocumentRepository.save(cardDocument);
		return new EmojiDeleteDto.Response();
	}
}
