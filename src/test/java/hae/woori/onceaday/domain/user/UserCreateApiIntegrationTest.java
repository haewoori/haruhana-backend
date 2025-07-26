package hae.woori.onceaday.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.domain.user.dto.UserCreateDto;
import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import hae.woori.onceaday.persistence.document.UserDocument;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserCreateApiIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserDocumentRepository userDocumentRepository;

	@BeforeEach
	void setUp() {
		userDocumentRepository.deleteAll();
	}

	@Test
	@DisplayName("사용자 정상 생성")
	void createUser_success() throws Exception {
		UserCreateDto.Request request = new UserCreateDto.Request("tank3a@gmail.com", "김종원", "http://example.com/image.jpg", "탱크3세", 0);

		ResultActions result = mockMvc.perform(post("/api/v1/user/create").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		UserDocument expected = UserDocument.builder()
			.userId("tank3a@gmail.com")
			.name("김종원")
			.nickname("탱크3세")
			.imageUrl("http://example.com/image.jpg")
			.gender(0)
			.build();
		result.andExpect(status().isOk());
		assertThat(userDocumentRepository.findAll()).hasSize(1);
		assertThat(userDocumentRepository.findAll().getFirst()).usingRecursiveComparison()
			.ignoringFields("id", "createdTime")
			.isEqualTo(expected);
	}

	@Test
	@DisplayName("중복 사용자 생성 시 예외 발생")
	void createUser_duplicate() throws Exception {
		// 먼저 사용자 생성
		UserDocument user = UserDocument.builder()
			.userId("tank3a@gmail.com")
			.name("김종원")
			.imageUrl("http://example.com/image.jpg")
			.nickname("탱크3세")
			.gender(0)
			.build();
		userDocumentRepository.save(user);

		UserCreateDto.Request request = new UserCreateDto.Request("tank3a@gmail.com", "김종원", "http://example.com/image.jpg", "탱크3세", 0);

		ResultActions result = mockMvc.perform(post("/api/v1/user/create").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		result.andExpect(status().isInternalServerError());
		assertThat(userDocumentRepository.findAll()).hasSize(1);
	}
}

