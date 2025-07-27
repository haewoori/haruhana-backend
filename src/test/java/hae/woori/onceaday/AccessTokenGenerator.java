package hae.woori.onceaday;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@TestComponent
public class AccessTokenGenerator {

	@Value("${jwt.expiration.access-token}")
	private Long accessTokenExpirePeriod;
	@Value("${jwt.secret-key}")
	private String secretKey;

	public String generateAccessToken(String userId) {
		Date now = new Date();
		Date accessTokenExpireTime = new Date(now.getTime() + accessTokenExpirePeriod);
		Key key = getSigningKey();

		return Jwts.builder()
			.subject(userId)
			.issuedAt(now)
			.expiration(accessTokenExpireTime)
			.signWith(key)
			.compact();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
