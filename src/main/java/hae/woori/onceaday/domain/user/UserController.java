package hae.woori.onceaday.domain.user;

import hae.woori.onceaday.domain.user.dto.UserCreateDto;
import hae.woori.onceaday.domain.user.service.UserCreateService;
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

	@PostMapping("/create")
	public UserCreateDto.Response createUser(@RequestBody @Valid UserCreateDto.Request request) {
		log.info("Hello");
		return userCreateService.run(request);
	}
}
