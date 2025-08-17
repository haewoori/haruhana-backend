package hae.woori.onceaday.domain.study.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import hae.woori.onceaday.domain.study.vo.StudyUserProfileVo;
import hae.woori.onceaday.persistence.vo.StudyCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "스터디 카드의 상세 정보")
public class StudyCardDto {

	private String studyCardId;
	private String title;
	private String content;
	private StudyUserProfileVo userProfile;
	private int maxParticipants;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private StudyCategory category;
	private LocalDate dueDate;
	private boolean online;
	@Schema(description = "모집중/모집완료에 대한 구분. false면 모집완료")
	private boolean available;
	private boolean isMine;
	List<String> participantIds;
	@Schema(description = "참여자 이름 목록. ID 순서와 동일하게 유지됨")
	List<String> participantNames;
	boolean participated;

}
