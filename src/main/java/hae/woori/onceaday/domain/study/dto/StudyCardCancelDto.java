package hae.woori.onceaday.domain.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class StudyCardCancelDto {
    @Schema(description = "취소 요청")
    public record Request(String studyCardId) {}

    public record RequestWrapper(String studyCardId, String userId) {
        public static RequestWrapper of(Request req, String userId) {
            return new RequestWrapper(req.studyCardId(), userId);
        }
    }

    @Schema(description = "취소 결과")
    public record Response(
            boolean success,
            boolean participated
    ) {}
}
