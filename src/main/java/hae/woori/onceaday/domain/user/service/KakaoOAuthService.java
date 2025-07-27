package hae.woori.onceaday.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hae.woori.onceaday.auth.vo.TokenVo;
import hae.woori.onceaday.domain.user.dto.KakaoOAuthDto;
import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.user.mapper.UserMapper;
import hae.woori.onceaday.domain.user.vo.KakaoUserVo;
import hae.woori.onceaday.persistence.document.UserDocument;
import hae.woori.onceaday.persistence.repository.UserDocumentRepository;
import hae.woori.onceaday.auth.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService implements SimpleService<KakaoOAuthDto.Request, KakaoOAuthDto.Response> {

	private final WebClient webClient;
	private final ObjectMapper objectMapper;
	private final UserDocumentRepository userDocumentRepository;
	private final JwtUtil jwtUtil;
	private final UserMapper userMapper;

	@Value("${oauth.kakao.authorization-url}")
	private String kakaoAuthorizationUrl;
	@Value("${oauth.kakao.client-id}")
	private String kakaoClientId;
	@Value("${oauth.kakao.client-secret}")
	private String kakaoClientSecret;

	@Override
	public KakaoOAuthDto.Response run(KakaoOAuthDto.Request input) {
		String kakaoAccessToken = getAccessToken(input.authCode());
		KakaoUserVo userInfo = getUserInfo(kakaoAccessToken);
		UserDocument userDocument = userDocumentRepository.findByEmail((userInfo.email()))
			.orElseGet(() -> {
				UserDocument document = userMapper.kakaoUserVoToUserDocument(userInfo);
				return userDocumentRepository.save(document);
			});


		String userId = userDocument.getId();
		TokenVo tokens = jwtUtil.generateToken(userId);

		return new KakaoOAuthDto.Response(tokens);
	}

	private String getAccessToken(String authCode) {
		String response = webClient.post()
			.uri(kakaoAuthorizationUrl)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.bodyValue("grant_type=authorization_code" +
				"&client_id=" + kakaoClientId +
				"&redirect_uri=http://localhost:8080/api/v1/user/oauth/kakao" +
				"&code=" + authCode +
				"&client_secret=" + kakaoClientSecret)
			.retrieve()
			.bodyToMono(JsonNode.class)
			.block()
			.get("access_token")
			.asText();

		return response;
	}

	private KakaoUserVo getUserInfo(String accessToken) {
		JsonNode response = webClient.get()
			.uri("https://kapi.kakao.com/v2/user/me")
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(JsonNode.class)
			.block();

		String accountInfoString = response.get("kakao_account").toString();
		try {
			return objectMapper.readValue(accountInfoString, KakaoUserVo.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
