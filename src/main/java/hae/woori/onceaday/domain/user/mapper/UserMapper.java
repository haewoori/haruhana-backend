package hae.woori.onceaday.domain.user.mapper;

import hae.woori.onceaday.domain.user.dto.UserCreateDto;
import hae.woori.onceaday.domain.user.vo.KakaoUserVo;
import hae.woori.onceaday.persistence.document.UserDocument;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDocument createRequestToUserDocument(UserCreateDto.Request request);

	@Mapping(target = "email", source = "kakaoUserVo.email")
	@Mapping(target = "nickname", source = "kakaoUserVo.profile.nickname")
	@Mapping(target = "imageUrl", source = "kakaoUserVo.profile.profileImageUrl")
	UserDocument kakaoUserVoToUserDocument(KakaoUserVo kakaoUserVo);
}
