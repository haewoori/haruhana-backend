package hae.woori.onceaday.domain.study.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import hae.woori.onceaday.domain.study.vo.AvailabilityFilter;

import io.swagger.v3.oas.annotations.media.Schema;

public class StudyCardListDto {

	public record Request() {
	}

	@Schema(name = "StudyCardListDto.Response")
	public record Response(
		Page<StudyCardDto> result
	) {
	}

	public record RequestWrapper(
		AvailabilityFilter isAvailable,
		Pageable pageable,
		String userId
	) {
	}

	public static RequestWrapper requestWrapperFrom(AvailabilityFilter isAvailable, Pageable pageable, String userId) {
		return new RequestWrapper(isAvailable, pageable, userId);
	}
}
