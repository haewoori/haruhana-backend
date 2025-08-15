package hae.woori.onceaday.domain.study;

import hae.woori.onceaday.domain.study.dto.StudyCardCreateDto;
import hae.woori.onceaday.domain.study.dto.StudyCardDeleteDto;
import hae.woori.onceaday.domain.study.dto.StudyCardListDto;
import hae.woori.onceaday.domain.study.service.StudyCardCreateService;
import hae.woori.onceaday.domain.study.service.StudyCardDeleteService;
import hae.woori.onceaday.domain.study.service.StudyCardListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/study")
@RequiredArgsConstructor
@Slf4j
public class StudyController {
    private final StudyCardCreateService studyCardCreateService;
    private final StudyCardDeleteService studyCardDeleteService;
    private final StudyCardListService studyCardListService;

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

    @Operation(description = "스터디 카드 목록을 조회합니다. 페이지네이션 기반으로 동작합니다. 모집중인 스터디가 상단에 최신순으로 보입니다.")
    @GetMapping("/list")
    public StudyCardListDto.Response getList(
        @Schema(description = "파라미터를 주지 않으면 전체 조회. true/false를 주면 모집중/모집완료만 확인 가능")
        @Nullable @RequestParam(name = "available") Boolean isAvailable,
        @Schema(description = "page만 처리 가능") @PageableDefault(size = 20) Pageable pageable,
        Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        log.info("User ID {} requesting study card list", userId);
        return studyCardListService.run(StudyCardListDto.requestWrapperFrom(isAvailable, pageable, userId));
    }

    @DeleteMapping("/delete/{cardId}")
    public StudyCardDeleteDto.Response delete(@PathVariable String cardId, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        log.info("User ID {} deleting study card with ID: {}", userId, cardId);
        return studyCardDeleteService.run(StudyCardDeleteDto.toRequestWrapper(cardId, userId));
    }

	//TODO: 4. 스터디 신청 기능

	//TODO: 5. 스터디 신청 취소 기능

}
