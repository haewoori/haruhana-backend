package hae.woori.onceaday.persistence.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Document(collection = "refreshtokens")
public class RefreshTokenDocument {

	@Id
	private String id;
	@Field(value = "user_id")
	private String userId;
	@Field(value = "refresh_token")
	private String refreshToken;
	@Field(value = "refresh_token_expire_in")
	private Date refreshTokenExpireIn;
}
