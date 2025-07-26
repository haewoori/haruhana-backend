package hae.woori.onceaday.domain.card.dto;

import java.time.LocalDate;
import java.util.List;

import hae.woori.onceaday.domain.card.vo.CardVo;

public class MyCardSearchDto {

    public record Request(
            LocalDate date,
            String userId
    ) {
    }

    public record Response(
            List<CardVo> myCards,
            List<CardVo> otherCards
    ) {
    }
}
