package hae.woori.onceaday.persistence.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import hae.woori.onceaday.persistence.vo.StudyCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "study_cards")
public class StudyCardDocument {

	@Id
	private String id;
	@Field("title")
	private String title;
	@Field("content")
	private String content;
	@Field("user_id")
	private String userId;

	@CreatedDate
	@Field("created_time")
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Field("updated_time")
	private LocalDateTime updatedTime;

	@Field("due_date")
	private String dueDate;
	@Field("category")
	private StudyCategory category;
	//true면 온라인 스터디, false면 오프라인 스터디
	@Field("is_online")
	private boolean isOnline;
	//true면 모집중/모집완료. false면 삭제 등으로 비공개
	@Field("is_public")
	private boolean isPublic;

}
