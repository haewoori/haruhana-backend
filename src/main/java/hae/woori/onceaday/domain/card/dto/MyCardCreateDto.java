package hae.woori.onceaday.domain.card.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MyCardCreateDto {

    public record Request(
            //TODO: Access Token이 있다면 그걸 기반으로 user 매핑을 하고, 저장해야 할 것.
            String userId,
            @Size(max = 60) String content,
            @Pattern(regexp = "#([0-9a-fA-F]{6})") String bgColor
    ) {

    }

    public record Response() {
        // Define output fields after creating a card
    }
}
