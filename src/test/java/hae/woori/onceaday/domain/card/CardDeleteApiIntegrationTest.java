package hae.woori.onceaday.domain.card;

import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.domain.card.dto.MyCardDeleteDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardDeleteApiIntegrationTest {
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
	@DisplayName("userId, cardId에 매핑되는 card가 존재할 때 삭제 성공 및 DB 값 검증")
	void deleteCard_whenUserAndCardExists_success() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("user123")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);
		CardDocument card = CardDocument.builder()
			.userId("user123")
			.content("카드 내용")
			.bgColor("#FFAA00")
			.build();
		card = cardDocumentRepository.save(card);

		MyCardDeleteDto.Request request = new MyCardDeleteDto.Request("user123");
		ResultActions result = mockMvc.perform(delete("/api/v1/card/delete/{cardId}", card.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isOk());
		assertThat(cardDocumentRepository.findById(card.getId())).isEmpty();
	}

	@Test
	@DisplayName("cardId가 존재하지 않을 때 400 에러 및 DB 값 검증")
	void deleteCard_whenCardIdNotExists_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("user123")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);
		MyCardDeleteDto.Request request = new MyCardDeleteDto.Request("user123");
		ResultActions result = mockMvc.perform(delete("/api/v1/card/delete/{cardId}", "notExistId")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("발견된 카드id가 userId와 일치하지 않을 때 400 에러 및 DB 값 검증")
	void deleteCard_whenUserIdMismatch_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("user123")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);
		CardDocument card = CardDocument.builder()
			.userId("user123")
			.content("카드 내용")
			.bgColor("#FFAA00")
			.build();
		card = cardDocumentRepository.save(card);

		MyCardDeleteDto.Request request = new MyCardDeleteDto.Request("otherUser");
		ResultActions result = mockMvc.perform(delete("/api/v1/card/delete/{cardId}", card.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findById(card.getId())).isPresent();
	}
}

