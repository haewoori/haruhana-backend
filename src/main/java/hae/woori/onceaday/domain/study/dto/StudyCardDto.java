package hae.woori.onceaday.domain.study.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import hae.woori.onceaday.domain.study.vo.StudyUserProfileVo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudyCardDto {

	private String studyCardId;
	private String title;
	private String content;
	private StudyUserProfileVo userProfile;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private LocalDate dueDate;
	private boolean online;
	private boolean available;
	private boolean registered;
	private boolean isMine;
	List<String> participantIds;
	List<String> participantNames;
	boolean participated;

}
