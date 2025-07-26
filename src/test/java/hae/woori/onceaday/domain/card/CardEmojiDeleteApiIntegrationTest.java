package hae.woori.onceaday.domain.card;

import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.domain.card.dto.EmojiDeleteDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class CardEmojiDeleteApiIntegrationTest {
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

	@BeforeEach
	void setUp() {
		cardDocumentRepository.deleteAll();
		userDocumentRepository.deleteAll();
	}

	@Test
	@DisplayName("emojiRecord가 card에 존재할 때 정상적으로 삭제 및 DB 값 검증")
	void deleteEmoji_whenEmojiRecordExists_success() throws Exception {
		UserDocument user = UserDocument.builder()
			.userId("user123")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);
		EmojiDocument emojiDocument = EmojiDocument.builder()
			.emojiUrl("url1")
			.build();
		emojiDocument = emojiDocumentRepository.save(emojiDocument);
		EmojiRecord emojiRecord = new EmojiRecord(emojiDocument.getId(), "url1", "user123");
		CardDocument card = CardDocument.builder()
			.userId("user123")
			.content("카드 내용")
			.bgColor("#FFAA00")
			.emojiRecords(List.of(emojiRecord))
			.build();
		card = cardDocumentRepository.save(card);

		EmojiDeleteDto.Request request = new EmojiDeleteDto.Request("user123", card.getId());
		ResultActions result = mockMvc.perform(delete("/api/v1/card/emoji/delete")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isOk());
		CardDocument updated = cardDocumentRepository.findById(card.getId()).get();
		assertThat(updated.getEmojiRecords()).isEmpty();
	}

	@Test
	@DisplayName("card가 존재하지 않을 때 400 에러 및 DB 값 검증")
	void deleteEmoji_whenCardNotExists_fail() throws Exception {
		UserDocument user = UserDocument.builder()
			.userId("user123")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);
		EmojiDeleteDto.Request request = new EmojiDeleteDto.Request("user123", "notExistCardId");
		ResultActions result = mockMvc.perform(delete("/api/v1/card/emoji/delete")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isBadRequest());
		assertThat(cardDocumentRepository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("card에 있는 emojiRecord가 없을 때 로그가 잘 찍혔는지 확인 및 DB 값 검증")
	void deleteEmoji_whenEmojiRecordNotExists_logsAndNoChange(CapturedOutput output) throws Exception {
		UserDocument user = UserDocument.builder()
			.userId("user123")
			.name("홍길동")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		userDocumentRepository.save(user);
		EmojiDocument emojiDocument = EmojiDocument.builder()
			.emojiUrl("url1")
			.build();
		emojiDocument = emojiDocumentRepository.save(emojiDocument);
		CardDocument card = CardDocument.builder()
			.userId("user123")
			.content("카드 내용")
			.bgColor("#FFAA00")
			.emojiRecords(List.of(new EmojiRecord(emojiDocument.getId(), "url1", "otherUser")))
			.build();
		card = cardDocumentRepository.save(card);

		EmojiDeleteDto.Request request = new EmojiDeleteDto.Request("user123", card.getId());
		ResultActions result = mockMvc.perform(delete("/api/v1/card/emoji/delete")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isOk());
		CardDocument updated = cardDocumentRepository.findById(card.getId()).get();
		assertThat(updated.getEmojiRecords()).hasSize(1);
		assertThat(output.getOut()).contains("No emojiRecord found for userId: user123");
	}
}

