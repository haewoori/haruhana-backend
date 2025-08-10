package hae.woori.onceaday.domain.study;

import hae.woori.onceaday.domain.study.dto.StudyCardCreateDto;
import hae.woori.onceaday.domain.study.service.StudyCardCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/study")
@RequiredArgsConstructor
@Slf4j
public class StudyController {
    private final StudyCardCreateService studyCardCreateService;

    @PostMapping("/create")
    @Operation(description = "스터디 카드를 생성합니다. 요청 본문에 UserID와 신청 정보를 포함해야 합니다.",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 카드 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = StudyCardCreateDto.Response.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            )
    })
    public StudyCardCreateDto.Response create(@Schema @Valid @RequestBody StudyCardCreateDto.Request request, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        log.info("User ID {} creating study card with request: {}", userId, request);
        return studyCardCreateService.run(StudyCardCreateDto.toRequestWrapper(request, userId));
    }

	//TODO: 2. 스터디 카드 조회 기능. 페이지네이션 이용

	//TODO: 3. 카드 삭제 기능

	//TODO: 4. 스터디 신청 기능

	//TODO: 5. 스터디 신청 취소 기능

}
