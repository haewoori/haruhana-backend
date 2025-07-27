package hae.woori.onceaday.domain.card;

import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.AccessTokenGenerator;
import hae.woori.onceaday.domain.card.dto.EmojiAddDto;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.document.EmojiDocument;
import hae.woori.onceaday.persistence.document.UserDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import hae.woori.onceaday.persistence.repository.EmojiDocumentRepository;
import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import hae.woori.onceaday.persistence.vo.EmojiRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({AccessTokenGenerator.class})
@SpringBootTest
@AutoConfigureMockMvc
class CardEmojiAddApiIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CardDocumentRepository cardDocumentRepository;
	@Autowired
	private UserDocumentRepository userDocumentRepository;
	@Autowired
	private EmojiDocumentRepository emojiDocumentRepository;
	@Autowired
	private AccessTokenGenerator accessTokenGenerator;

	@BeforeEach
	void setUp() {
		cardDocumentRepository.deleteAll();
		userDocumentRepository.deleteAll();
		emojiDocumentRepository.deleteAll();
	}

	@Test
	@DisplayName("카드에 처음 emoji를 추가하는 userId일 때 정상적으로 추가 및 DB 값 검증")
	void addEmoji_whenFirstTime_success() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());
		CardDocument card = CardDocument.builder().userId(user.getId()).content("카드 내용").bgColor("#FFAA00").build();
		card = cardDocumentRepository.save(card);
		EmojiDocument emoji = EmojiDocument.builder().emojiUrl("url1").build();
		emoji = emojiDocumentRepository.save(emoji);

		EmojiAddDto.Request request = new EmojiAddDto.Request(card.getId(), emoji.getId());
		ResultActions result = mockMvc.perform(post("/api/v1/card/emoji/add")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + accessToken)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isOk());
		CardDocument updated = cardDocumentRepository.findById(card.getId()).get();
		assertThat(updated.getEmojiRecords()).hasSize(1);
		EmojiRecord record = updated.getEmojiRecords().getFirst();

		EmojiRecord expected = new EmojiRecord(emoji.getId(), emoji.getEmojiUrl(), user.getId());
		assertThat(record).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	@DisplayName("카드에 이미 추가한적이 있는 userId일 때 기존 emoji가 교체됨 및 DB 값 검증")
	void addEmoji_whenAlreadyExists_replacesEmoji() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());

		EmojiDocument oldEmoji = EmojiDocument.builder().emojiUrl("oldUrl").build();
		oldEmoji = emojiDocumentRepository.save(oldEmoji);
		CardDocument card = CardDocument.builder()
			.userId(user.getId())
			.content("카드 내용")
			.bgColor("#FFAA00")
			.emojiRecords(List.of(new EmojiRecord(oldEmoji.getId(), oldEmoji.getEmojiUrl(), user.getId())))
			.build();
		card = cardDocumentRepository.save(card);
		EmojiDocument newEmoji = EmojiDocument.builder().emojiUrl("newUrl").build();
		newEmoji = emojiDocumentRepository.save(newEmoji);

		EmojiAddDto.Request request = new EmojiAddDto.Request(card.getId(), newEmoji.getId());
		ResultActions result = mockMvc.perform(post("/api/v1/card/emoji/add")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + accessToken)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isOk());
		CardDocument updated = cardDocumentRepository.findById(card.getId()).get();
		assertThat(updated.getEmojiRecords()).hasSize(1);
		EmojiRecord record = updated.getEmojiRecords().getFirst();

		EmojiRecord expected = new EmojiRecord(newEmoji.getId(), newEmoji.getEmojiUrl(), user.getId());
		assertThat(record).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	@DisplayName("카드가 존재하지 않을 때 400 에러 및 DB 값 검증")
	void addEmoji_whenCardNotExists_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());
		EmojiDocument emoji = EmojiDocument.builder().id("emoji1").emojiUrl("url1").build();
		emojiDocumentRepository.save(emoji);

		EmojiAddDto.Request request = new EmojiAddDto.Request("notExistCardId", "emoji1");
		ResultActions result = mockMvc.perform(post("/api/v1/card/emoji/add")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + accessToken)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("이모티콘이 존재하지 않을 때 400 에러 및 DB 값 검증")
	void addEmoji_whenEmojiNotExists_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());

		CardDocument card = CardDocument.builder().userId(user.getId()).content("카드 내용").bgColor("#FFAA00").build();
		card = cardDocumentRepository.save(card);

		EmojiAddDto.Request request = new EmojiAddDto.Request(card.getId(), "notExistEmojiId");
		ResultActions result = mockMvc.perform(post("/api/v1/card/emoji/add")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + accessToken)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		CardDocument updated = cardDocumentRepository.findById(card.getId()).get();
		assertThat(updated.getEmojiRecords()).isEmpty();
	}

	@Test
	@DisplayName("user가 존재하지 않을 때 400 에러 및 DB 값 검증")
	void addEmoji_whenUserNotExists_fail() throws Exception {
		CardDocument card = CardDocument.builder().userId("user123").content("카드 내용").bgColor("#FFAA00").build();
		card = cardDocumentRepository.save(card);
		EmojiDocument emoji = EmojiDocument.builder().id("emoji1").emojiUrl("url1").build();
		emojiDocumentRepository.save(emoji);
		String accessToken = accessTokenGenerator.generateAccessToken("invalid_user_id");


		EmojiAddDto.Request request = new EmojiAddDto.Request(card.getId(), "emoji1");
		ResultActions result = mockMvc.perform(post("/api/v1/card/emoji/add")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + accessToken)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		CardDocument updated = cardDocumentRepository.findById(card.getId()).get();
		assertThat(updated.getEmojiRecords()).isEmpty();
	}
}

