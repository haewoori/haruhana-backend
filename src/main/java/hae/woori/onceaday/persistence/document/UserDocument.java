package hae.woori.onceaday.persistence.document;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;
    @NotNull
    @Field(value = "user_id")
    @Indexed(unique = true)
    private String userId;
    @NotNull
    @Field(value = "name")
    private String name;
    @Field(value = "nickname")
    private String nickname;
    @NotNull
    @Field(value = "gender")
    private int gender;
    @CreatedDate
    @Field(value = "created_time")
    private LocalDateTime createdTime;
}
