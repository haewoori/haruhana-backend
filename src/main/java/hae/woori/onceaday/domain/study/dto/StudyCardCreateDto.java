package hae.woori.onceaday.domain.study.dto;

import hae.woori.onceaday.persistence.vo.StudyCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class StudyCardCreateDto {
    @Schema(name = "StudyCardCreateDto.Request")
    public record Request(
        @Schema(description = "스터디 카드의 제목") String title,
        @Schema(description = "스터디 카드의 설명") String content,
        @Schema(description = "스터디 카드의 종료 날짜 (YYYY-MM-DD 형식)") LocalDate dueDate,
        @Schema(description = "스터디 카드의 카테고리(CERTIFICATE, HOBBY)") StudyCategory category,
        @Schema(description = "스터디 카드 온라인 여부") boolean isOnline
    ) {}

    public record RequestWrapper(
        String title,
        String content,
        LocalDate dueDate,
        StudyCategory category,
        boolean isOnline,
        String userId,
        boolean isAvailable
    ) {}

    public static RequestWrapper toRequestWrapper(Request request, String userId) {
        return new RequestWrapper(
            request.title(),
            request.content(),
            request.dueDate(),
            request.category(),
            request.isOnline(),
            userId,
            true
        );
    }

    @Schema(name = "StudyCardCreateDto.Response")
    public record Response() {}
}
