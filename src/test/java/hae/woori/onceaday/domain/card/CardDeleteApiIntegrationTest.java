package hae.woori.onceaday.domain.card;

import hae.woori.onceaday.AccessTokenGenerator;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({AccessTokenGenerator.class})
@SpringBootTest
@AutoConfigureMockMvc
class CardDeleteApiIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CardDocumentRepository cardDocumentRepository;
	@Autowired
	private UserDocumentRepository userDocumentRepository;
	@Autowired
	private AccessTokenGenerator accessTokenGenerator;

	@BeforeEach
	void setUp() {
		cardDocumentRepository.deleteAll();
		userDocumentRepository.deleteAll();
	}

	@Test
	@DisplayName("userId, cardId에 매핑되는 card가 존재할 때 삭제 성공 및 DB 값 검증")
	void deleteCard_whenUserAndCardExists_success() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());

		CardDocument card = CardDocument.builder().userId(user.getId()).content("카드 내용").bgColor("#FFAA00").build();
		card = cardDocumentRepository.save(card);

		ResultActions result = mockMvc.perform(
			delete("/api/v1/card/delete/{cardId}", card.getId())
				.header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isOk());
		assertThat(cardDocumentRepository.findById(card.getId())).isEmpty();
	}

	@Test
	@DisplayName("cardId가 존재하지 않을 때 400 에러 및 DB 값 검증")
	void deleteCard_whenCardIdNotExists_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());

		ResultActions result = mockMvc.perform(delete("/api/v1/card/delete/{cardId}", "notExistId")
			.header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("발견된 카드id가 userId와 일치하지 않을 때 400 에러 및 DB 값 검증")
	void deleteCard_whenUserIdMismatch_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.email("tank3a@gmail.com")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		user = userDocumentRepository.save(user);
		String accessToken = accessTokenGenerator.generateAccessToken(user.getId());
		CardDocument card = CardDocument.builder().userId("different-user-id").content("카드 내용").bgColor("#FFAA00").build();
		card = cardDocumentRepository.save(card);

		ResultActions result = mockMvc.perform(delete("/api/v1/card/delete/{cardId}", card.getId())
			.header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findById(card.getId())).isPresent();
	}
}

