package hae.woori.onceaday.domain.card.service;

import org.springframework.stereotype.Service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.EmojiDataAddDto;
import hae.woori.onceaday.persistence.document.EmojiDocument;
import hae.woori.onceaday.persistence.repository.EmojiDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmojiDataAddService implements SimpleService<EmojiDataAddDto.Request, EmojiDataAddDto.Response> {

	private final EmojiDocumentRepository emojiDocumentRepository;

	@Override
	public EmojiDataAddDto.Response run(EmojiDataAddDto.Request input) {
		EmojiDocument document = EmojiDocument.builder()
			.emoji(input.emoji())
			.build();

		document = emojiDocumentRepository.save(document);

		return new EmojiDataAddDto.Response(document.getId(), document.getEmoji());
	}
}
