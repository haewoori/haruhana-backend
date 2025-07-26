package hae.woori.onceaday.domain.card;

import hae.woori.onceaday.domain.card.dto.EmojiAddDto;
import hae.woori.onceaday.domain.card.dto.MyCardDeleteDto;
import hae.woori.onceaday.domain.card.service.EmojiAddService;
import hae.woori.onceaday.domain.card.service.MyCardCreateService;
import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.domain.card.service.MyCardDeleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

	private final MyCardCreateService myCardCreateService;
	private final MyCardDeleteService myCardDeleteService;
	private final EmojiAddService emojiAddService;

	@PostMapping("/create")
	public MyCardCreateDto.Response create(@Valid @RequestBody MyCardCreateDto.Request request) {
		return myCardCreateService.run(request);
	}

	@DeleteMapping("/delete/{cardId}")
	public MyCardDeleteDto.Response delete(@RequestParam String cardId, @RequestBody MyCardDeleteDto.Request request) {
		return myCardDeleteService.run(MyCardDeleteDto.requestWrapperFrom(request, cardId));
	}

	@PostMapping("/emoji/add")
	public EmojiAddDto.Response addEmoji(@Valid @RequestBody EmojiAddDto.Request request) {
		return emojiAddService.run(request);
	}
}
