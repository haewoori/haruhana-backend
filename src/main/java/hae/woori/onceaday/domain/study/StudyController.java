package hae.woori.onceaday.domain.study;

import hae.woori.onceaday.domain.study.dto.*;
import hae.woori.onceaday.domain.study.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
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
    private final StudyCardApplyService studyCardApplyService;
    private final StudyCardCancelService studyCardCancelService;

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
        @RequestParam(name = "available", defaultValue = "false") boolean isAvailable,
        @Schema(description = "page만 처리 가능") @PageableDefault(size = 1) Pageable pageable,
        Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        log.info("User ID {} requesting study card list", userId);
        return studyCardListService.run(StudyCardListDto.requestWrapperFrom(isAvailable, pageable, userId));
    }

    @DeleteMapping("/delete/{studyCardId}")
    public StudyCardDeleteDto.Response delete(@PathVariable String studyCardId, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        log.info("User ID {} deleting study card with ID: {}", userId, studyCardId);
        return studyCardDeleteService.run(StudyCardDeleteDto.toRequestWrapper(studyCardId, userId));
    }

	//TODO: 4. 스터디 신청 기능
    @PostMapping("/apply")
    @Operation(description = "스터디에 신청(참가)합니다.")
    public StudyCardApplyDto.Response apply(
            @Valid @RequestBody StudyCardApplyDto.Request request,
            Authentication authentication
    ) {
        String userId = (String) authentication.getPrincipal();
        log.info("User {} apply card {}", userId, request.studyCardId());
        return studyCardApplyService.run(StudyCardApplyDto.RequestWrapper.of(request, userId));
    }

	//TODO: 5. 스터디 신청 취소 기능
    @PostMapping("/cancel")
    @Operation(description = "스터디 신청(참가)을 취소합니다.")
    public StudyCardCancelDto.Response cancel(
            @Valid @RequestBody StudyCardCancelDto.Request request,
            Authentication authentication
    ) {
        String userId = (String) authentication.getPrincipal();
        log.info("User {} cancel card {}", userId, request.studyCardId());
        return studyCardCancelService.run(StudyCardCancelDto.RequestWrapper.of(request, userId));
    }


}
