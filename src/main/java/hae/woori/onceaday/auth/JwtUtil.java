package hae.woori.onceaday.auth;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hae.woori.onceaday.auth.vo.TokenVo;
import hae.woori.onceaday.persistence.document.RefreshTokenDocument;
import hae.woori.onceaday.persistence.repository.RefreshTokenDocumentRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final RefreshTokenDocumentRepository refreshTokenDocumentRepository;

	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.expiration.access-token}")
	private Long accessTokenExpirePeriod;
	@Value("${jwt.expiration.refresh-token}")
	private Long refreshTokenExpirePeriod;

	public TokenVo generateToken(String userId) {
		Date now = new Date();
		Date accessTokenExpireTime = new Date(now.getTime() + accessTokenExpirePeriod);
		Date refreshTokenExpireTime = new Date(now.getTime() + refreshTokenExpirePeriod);
		String accessToken = generateAccessToken(userId, now, accessTokenExpireTime);
		String refreshToken = generateRefreshToken(userId, now, refreshTokenExpireTime);

		saveRefreshToken(userId, refreshToken, refreshTokenExpireTime);
		return new TokenVo(accessToken, refreshToken, refreshTokenExpireTime);
	}

	public boolean validate(String token) {
		try {
			Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getUserId(String token) {
		return
			Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	private String generateRefreshToken(String userId, Date now, Date expirationAt) {
		Key key = getSigningKey();

		return Jwts.builder()
			.subject(userId)
			.issuedAt(now)
			.expiration(expirationAt)
			.signWith(key)
			.compact();
	}

	private String generateAccessToken(String userId, Date now, Date expirationAt) {
		Key key = getSigningKey();

		return Jwts.builder()
			.subject(userId)
			.issuedAt(now)
			.expiration(expirationAt)
			.signWith(key)
			.compact();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private void saveRefreshToken(String userId, String refreshToken, Date refreshTokenExpireIn) {
		refreshTokenDocumentRepository.save(RefreshTokenDocument.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.refreshTokenExpireIn(refreshTokenExpireIn)
			.build());
	}
}
