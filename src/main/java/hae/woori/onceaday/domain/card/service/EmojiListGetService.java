package hae.woori.onceaday.domain.card.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.EmojiListGetDto;
import hae.woori.onceaday.domain.card.mapper.EmojiMapper;
import hae.woori.onceaday.domain.card.vo.EmojiVo;
import hae.woori.onceaday.persistence.repository.EmojiDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmojiListGetService implements SimpleService<EmojiListGetDto.Request, EmojiListGetDto.Response> {

	private final EmojiDocumentRepository emojiDocumentRepository;
	private final EmojiMapper emojiMapper;

	@Override
	public EmojiListGetDto.Response run(EmojiListGetDto.Request input) {
		List<EmojiVo> list = emojiDocumentRepository.findAll()
			.stream().map(emojiMapper::emojiDocumentToEmojiVo)
			.toList();
		return new EmojiListGetDto.Response(list);

	}
}
