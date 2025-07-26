package hae.woori.onceaday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/v1/global/noauth").permitAll()
				.requestMatchers("/api/v1/user/create").permitAll()
				//TODO: oauth 추가 후 permitAll 제거
				.requestMatchers("/**").permitAll() // 모든 요청을 허용 (개발 중에는 이렇게 설정할 수 있지만, 실제 서비스에서는 적절히 수정해야 함)
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults()); // 또는 formLogin().defaultSuccessUrl(...) 등

		return http.build();
	}
}
