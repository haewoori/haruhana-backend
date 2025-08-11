package hae.woori.onceaday.domain.card;

import java.time.LocalDate;

import hae.woori.onceaday.domain.card.dto.EmojiAddDto;
import hae.woori.onceaday.domain.card.dto.EmojiDataAddDto;
import hae.woori.onceaday.domain.card.dto.EmojiDeleteDto;
import hae.woori.onceaday.domain.card.dto.EmojiListGetDto;
import hae.woori.onceaday.domain.card.dto.MyCardDeleteDto;
import hae.woori.onceaday.domain.card.dto.MyCardSearchDto;
import hae.woori.onceaday.domain.card.service.CardSearchService;
import hae.woori.onceaday.domain.card.service.EmojiAddService;
import hae.woori.onceaday.domain.card.service.EmojiDataAddService;
import hae.woori.onceaday.domain.card.service.EmojiDeleteService;
import hae.woori.onceaday.domain.card.service.EmojiListGetService;
import hae.woori.onceaday.domain.card.service.MyCardCreateService;
import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.domain.card.service.MyCardDeleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

	private final CardSearchService cardSearchService;
	private final MyCardCreateService myCardCreateService;
	private final MyCardDeleteService myCardDeleteService;
	private final EmojiAddService emojiAddService;
	private final EmojiDeleteService emojiDeleteService;
	private final EmojiListGetService emojiListGetService;
	private final EmojiDataAddService emojiDataAddService;

	@GetMapping("/get")
	@Operation(description = "특정 날짜에 해당하는 내 카드와 다른 사람 카드 리스트를 조회합니다.",
	responses = {
		@ApiResponse(
			responseCode = "200",
			description = "카드 리스트 조회 성공",
			content = @Content(
				schema = @Schema(implementation = MyCardSearchDto.Response.class)
			)
		)
	})
	public MyCardSearchDto.Response searchCard(
		@RequestParam @Valid @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") String date, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		log.info("User ID {} trying to search cards for date {}", userId, date);
		MyCardSearchDto.Request request = new MyCardSearchDto.Request(LocalDate.parse(date), userId); // Replace "defaultUserId" with actual user ID logic
		return cardSearchService.run(request);
	}

	@PostMapping("/create")
	public MyCardCreateDto.Response create(@Schema @Valid @RequestBody MyCardCreateDto.Request request, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		log.info("User ID {} creating card with request: {}", userId, request);
		return myCardCreateService.run(MyCardCreateDto.toRequestWrapper(request, userId));
	}

	@DeleteMapping("/delete/{cardId}")
	public MyCardDeleteDto.Response delete(@PathVariable String cardId, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		return myCardDeleteService.run(MyCardDeleteDto.requestWrapperFrom(userId, cardId));
	}

	@GetMapping("/emoji/data/list")
	public EmojiListGetDto.Response emojiList() {
		return emojiListGetService.run(new EmojiListGetDto.Request());
	}

	@Operation(description = "이모지 데이터를 추가합니다.")
	@PostMapping("/emoji/data/add")
	public EmojiDataAddDto.Response emojiDataAdd(@RequestBody EmojiDataAddDto.Request request, Authentication authentication) {
		return emojiDataAddService.run(request);
	}

	@Operation(description = "카드에 이모지를 추가합니다.")
	@PostMapping("/emoji/add")
	public EmojiAddDto.Response addEmoji(@Valid @RequestBody EmojiAddDto.Request request, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		return emojiAddService.run(EmojiAddDto.toRequestWrapper(userId, request));
	}

	@DeleteMapping("/emoji/delete")
	public EmojiDeleteDto.Response deleteEmoji(@Valid @RequestBody EmojiDeleteDto.Request request, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		return emojiDeleteService.run(EmojiDeleteDto.toRequestWrapper(userId, request));
	}
}
