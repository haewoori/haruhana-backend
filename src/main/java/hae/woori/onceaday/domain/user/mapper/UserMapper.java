package hae.woori.onceaday.domain.user.mapper;

import hae.woori.onceaday.domain.user.dto.UserCreateDto;
import hae.woori.onceaday.persistence.document.UserDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDocument createRequestToUserDocument(UserCreateDto.Request request);
}
