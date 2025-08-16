package hae.woori.onceaday.domain.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class StudyCardApplyDto {
    @Schema(description = "신청 요청")
    public record Request(String cardId) {}

    public record RequestWrapper(String cardId, String userId) {
        public static RequestWrapper of(Request req, String userId) {
            return new RequestWrapper(req.cardId(), userId);
        }
    }

    @Schema(description = "신청 결과")
    public record Response(
            boolean success,
            boolean participated   // 현재 사용자가 참가 목록에 포함되었는지
    ) {}
}
