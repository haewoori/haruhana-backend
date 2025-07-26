package hae.woori.onceaday.domain.card;

import java.time.LocalDate;

import hae.woori.onceaday.domain.card.dto.EmojiAddDto;
import hae.woori.onceaday.domain.card.dto.EmojiDeleteDto;
import hae.woori.onceaday.domain.card.dto.MyCardDeleteDto;
import hae.woori.onceaday.domain.card.dto.MyCardSearchDto;
import hae.woori.onceaday.domain.card.service.CardSearchService;
import hae.woori.onceaday.domain.card.service.EmojiAddService;
import hae.woori.onceaday.domain.card.service.EmojiDeleteService;
import hae.woori.onceaday.domain.card.service.MyCardCreateService;
import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.domain.card.service.MyCardDeleteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

	private final CardSearchService cardSearchService;
	private final MyCardCreateService myCardCreateService;
	private final MyCardDeleteService myCardDeleteService;
	private final EmojiAddService emojiAddService;
	private final EmojiDeleteService emojiDeleteService;

	@GetMapping("/get")
	public MyCardSearchDto.Response searchCard(
		@RequestParam @Valid @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") String date,
			//TODO: OAuth 추가 후 access token이용
		@RequestParam String userId) {
		MyCardSearchDto.Request request = new MyCardSearchDto.Request(LocalDate.parse(date), userId); // Replace "defaultUserId" with actual user ID logic
		return cardSearchService.run(request);
	}

	@PostMapping("/create")
	public MyCardCreateDto.Response create(@Valid @RequestBody MyCardCreateDto.Request request) {
		return myCardCreateService.run(request);
	}

	@DeleteMapping("/delete/{cardId}")
	public MyCardDeleteDto.Response delete(@PathVariable String cardId, @RequestBody MyCardDeleteDto.Request request) {
		return myCardDeleteService.run(MyCardDeleteDto.requestWrapperFrom(request, cardId));
	}

	@PostMapping("/emoji/add")
	public EmojiAddDto.Response addEmoji(@Valid @RequestBody EmojiAddDto.Request request) {
		return emojiAddService.run(request);
	}

	@DeleteMapping("/emoji/delete")
	public EmojiDeleteDto.Response deleteEmoji(@Valid @RequestBody EmojiDeleteDto.Request request) {
		return emojiDeleteService.run(request);
	}
}
