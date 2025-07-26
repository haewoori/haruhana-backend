package hae.woori.onceaday.domain.card.dto;

public class MyCardDeleteDto {

    //TODO: Access Token이 있다면 그걸 기반으로 user 매핑을 하고, 저장해야 할 것.
    public record Request(
            String userId
    ) {}

    public static RequestWrapper requestWrapperFrom(MyCardDeleteDto.Request request, String cardId) {
        return new RequestWrapper(request.userId, cardId);
    }
    public record RequestWrapper(
            String userId,
            String cardId
    ) {}

    public record Response() {
    }
}
