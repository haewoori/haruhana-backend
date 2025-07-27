package hae.woori.onceaday.domain.card;

import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.document.UserDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import hae.woori.onceaday.persistence.repository.UserDocumentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardCreateApiIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CardDocumentRepository cardDocumentRepository;
	@Autowired
	private UserDocumentRepository userDocumentRepository;

	@BeforeEach
	void setUp() {
		cardDocumentRepository.deleteAll();
		userDocumentRepository.deleteAll();
	}

	@Test
	@DisplayName("userId가 UserDocument에 존재할 때 카드 정상 생성 및 DB 값 검증")
	void createCard_whenUserExists_success() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.nickname("test123")
			.imageUrl("http://example.com/image.jpg")
			.gender(1)
			.build();
		user = userDocumentRepository.save(user);

		MyCardCreateDto.Request request = new MyCardCreateDto.Request(
			"카드 내용입니다.",
			"#FFAA00"
		);

		ResultActions result = mockMvc.perform(post("/api/v1/card/create")
			.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + user.getId())
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isOk());
		List<CardDocument> cards = cardDocumentRepository.findAll();
		assertThat(cards).hasSize(1);

		CardDocument expected = CardDocument.builder()
			.bgColor("#FFAA00")
			.content("카드 내용입니다.")
			.userId("user123")
			.build();
		assertThat(cards.getFirst()).usingRecursiveComparison()
			.ignoringFields("id", "createTime", "emojiRecords")
			.isEqualTo(expected);
		assertThat(cards.getFirst().getEmojiRecords()).isEmpty();
	}

	@Test
	@DisplayName("userId가 UserDocument에 존재하지 않을 때 400 에러 및 DB 저장 실패")
	void createCard_whenUserNotExists_fail() throws Exception {
		MyCardCreateDto.Request request = new MyCardCreateDto.Request(
			"카드 내용입니다.",
			"#FFAA00"
		);

		ResultActions result = mockMvc.perform(post("/api/v1/card/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("bgColor가 정규식에 맞지 않을 때 400 에러 및 DB 저장 실패")
	void createCard_whenBgColorInvalid_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);

		MyCardCreateDto.Request request = new MyCardCreateDto.Request(
			"카드 내용입니다.",
			"123456"
		);

		ResultActions result = mockMvc.perform(post("/api/v1/card/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("content가 60자를 초과할 때 400 에러 및 DB 저장 실패")
	void createCard_whenContentTooLong_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);

		MyCardCreateDto.Request request = new MyCardCreateDto.Request(
			"a".repeat(61),
			"#FFAA00"
		);

		ResultActions result = mockMvc.perform(post("/api/v1/card/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}
}

