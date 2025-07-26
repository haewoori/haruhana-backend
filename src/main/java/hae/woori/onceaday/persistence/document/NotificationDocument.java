package hae.woori.onceaday.persistence.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Document(collection = "notifications")
public class NotificationDocument {

	@Id
	private String id;
	@Field(value = "message")
	private String message;
	@Field(value = "created_time")
	@CreatedDate
	private LocalDateTime createdTime;

}
