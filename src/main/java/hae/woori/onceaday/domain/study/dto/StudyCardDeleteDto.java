package hae.woori.onceaday.domain.study.dto;

public class StudyCardDeleteDto {

	public record Request() {}
	public record Response() {}

	public static StudyCardDeleteDto.RequestWrapper toRequestWrapper(String studyCardId, String userId) {
		return new StudyCardDeleteDto.RequestWrapper(studyCardId, userId);
	}

	public record RequestWrapper(
		String cardId,
		String userId
	) {}
}
