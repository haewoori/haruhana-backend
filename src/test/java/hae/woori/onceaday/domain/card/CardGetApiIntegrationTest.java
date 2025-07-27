package hae.woori.onceaday.domain.card;

import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.AccessTokenGenerator;
import hae.woori.onceaday.domain.card.dto.MyCardSearchDto;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({AccessTokenGenerator.class})
@SpringBootTest
@AutoConfigureMockMvc
class CardGetApiIntegrationTest {
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
		emojiDocumentRepository.deleteAll();
        cardDocumentRepository.deleteAll();
        userDocumentRepository.deleteAll();
    }

    @Test
    @DisplayName("userId가 생성했던 card와 그 외의 카드가 모두 있고, 조회")
    void getCard_whenUserAndOtherCardsExist_success() throws Exception {
        UserDocument user1 = UserDocument.builder()
                .email("tank3a@gmail.com")
                .name("홍길동")
                .gender(0)
                .build();
        user1 = userDocumentRepository.save(user1);
        UserDocument user2 = UserDocument.builder()
                .email("tank4a@gmail.com")
                .name("임꺽정")
                .gender(1)
                .build();
        user2 = userDocumentRepository.save(user2);
		EmojiDocument emojiDocument = EmojiDocument.builder()
			.emojiUrl("http://example.com/emoji.png")
			.build();
		emojiDocument = emojiDocumentRepository.save(emojiDocument);
        LocalDate date = LocalDate.of(2025, 7, 27);
        CardDocument card1 = CardDocument.builder()
                .userId(user1.getId())
                .content("내 카드")
                .bgColor("#FFAA00")
                .createTime(date.atStartOfDay())
				.emojiRecords(List.of(new EmojiRecord(emojiDocument.getId(), emojiDocument.getEmojiUrl(), "user123")))
                .build();
        CardDocument card2 = CardDocument.builder()
                .userId(user2.getId())
                .content("다른 카드")
                .bgColor("#00AAFF")
                .createTime(date.atStartOfDay())
                .build();
        cardDocumentRepository.saveAll(List.of(card1, card2));
        String accessToken = accessTokenGenerator.generateAccessToken(user1.getId());

        ResultActions result = mockMvc.perform(get("/api/v1/card/get")
                .param("date", "2025-07-27")
                .param("userId", "user123")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken));

        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        MyCardSearchDto.Response dto = objectMapper.readValue(response, MyCardSearchDto.Response.class);
        assertThat(dto.myCards()).hasSize(1);
        assertThat(dto.myCards().getFirst().content()).isEqualTo("내 카드");
        assertThat(dto.otherCards()).hasSize(1);
        assertThat(dto.otherCards().getFirst().content()).isEqualTo("다른 카드");
    }

    @Test
    @DisplayName("userId가 생성한 카드는 없고 그 외의 카드가 있고, 조회")
    void getCard_whenOnlyOtherCardsExist_success() throws Exception {
        UserDocument user1 = UserDocument.builder()
                .email("tank3a@gmail.com")
                .name("홍길동")
                .gender(0)
                .build();
        user1 = userDocumentRepository.save(user1);
        UserDocument user2 = UserDocument.builder()
                .email("tank4a@gmail.com")
                .name("임꺽정")
                .gender(1)
                .build();
        user2 = userDocumentRepository.save(user2);
        LocalDate date = LocalDate.of(2025, 7, 27);
        CardDocument card2 = CardDocument.builder()
                .userId(user2.getId())
                .content("다른 카드")
                .bgColor("#00AAFF")
                .createTime(date.atStartOfDay())
                .build();
        cardDocumentRepository.save(card2);
        String accessToken = accessTokenGenerator.generateAccessToken(user1.getId());

        ResultActions result = mockMvc.perform(get("/api/v1/card/get")
                .param("date", "2025-07-27")
                .param("userId", "user123")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        MyCardSearchDto.Response dto = objectMapper.readValue(response, MyCardSearchDto.Response.class);
        assertThat(dto.myCards()).isEmpty();
        assertThat(dto.otherCards()).hasSize(1);
        assertThat(dto.otherCards().getFirst().content()).isEqualTo("다른 카드");
    }

    @Test
    @DisplayName("특정 날짜에 카드가 하나도 없을 때 조회 결과가 모두 빈 배열")
    void getCard_whenNoCardsExist_success() throws Exception {
        UserDocument user1 = UserDocument.builder()
                .email("tank3a@gmail.com")
                .name("홍길동")
                .gender(0)
                .build();
        user1 = userDocumentRepository.save(user1);
        String accessToken = accessTokenGenerator.generateAccessToken(user1.getId());

        ResultActions result = mockMvc.perform(get("/api/v1/card/get")
                .param("date", "2025-07-27")
                .param("userId", "user123")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        MyCardSearchDto.Response dto = objectMapper.readValue(response, MyCardSearchDto.Response.class);
        assertThat(dto.myCards()).isEmpty();
        assertThat(dto.otherCards()).isEmpty();
    }

    @Test
    @DisplayName("날짜 형식이 잘못 왔을 때 400 에러")
    void getCard_whenDateFormatInvalid_fail() throws Exception {
        UserDocument user1 = UserDocument.builder()
                .email("tank3a@gmail.com")
                .name("홍길동")
                .gender(0)
                .build();
        user1 = userDocumentRepository.save(user1);
        String accessToken = accessTokenGenerator.generateAccessToken(user1.getId());

        ResultActions result = mockMvc.perform(get("/api/v1/card/get")
                .param("date", "2025/07/27")
                .param("userId", "user123")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }
}

