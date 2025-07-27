package hae.woori.onceaday.domain.card.dto;

import java.time.LocalDate;
import java.util.List;

import hae.woori.onceaday.domain.card.vo.CardVo;
import io.swagger.v3.oas.annotations.media.Schema;

public class MyCardSearchDto {
    @Schema(name = "MyCardSearchDto.Request")
    public record Request(
            LocalDate date,
            String userId
    ) {
    }

    @Schema(name = "MyCardSearchDto.Response")
    public record Response(
            @Schema(description = "내 카드 리스트")
            List<CardVo> myCards,
            @Schema(description = "다른 사람 카드 리스트")
            List<CardVo> otherCards
    ) {
    }
}
