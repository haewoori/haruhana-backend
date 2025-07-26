package hae.woori.onceaday.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public class UserCreateDto {

    public record Request(
        @NotNull String userId,
        @NotNull String name,
        String nickname,
        Integer gender
    ) {}
    public record Response() {}
}
