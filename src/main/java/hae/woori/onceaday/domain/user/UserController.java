package hae.woori.onceaday.domain.user;

import hae.woori.onceaday.domain.user.dto.UserCreateDto;
import hae.woori.onceaday.domain.user.service.UserCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserCreateService userCreateService;

	@Operation(description = "사용자 생성 API",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "사용자 생성 성공",
				content = @Content(
					schema = @Schema(implementation = UserCreateDto.Response.class)
				)
			)
		})
	@PostMapping("/create")
	public UserCreateDto.Response createUser(@RequestBody @Valid UserCreateDto.Request request) {
		log.info("Trying to create user with request: {}", request);
		return userCreateService.run(request);
	}
}
