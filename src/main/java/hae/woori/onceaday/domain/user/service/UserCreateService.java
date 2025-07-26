package hae.woori.onceaday.domain.user.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.user.dto.UserCreateDto;
import hae.woori.onceaday.domain.user.mapper.UserMapper;
import hae.woori.onceaday.persistence.UserDocumentRepository;
import hae.woori.onceaday.persistence.document.UserDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreateService implements SimpleService<UserCreateDto.Request, UserCreateDto.Response> {

    private final UserMapper userMapper;
    private final UserDocumentRepository userDocumentRepository;

    @Override
    public UserCreateDto.Response run(UserCreateDto.Request request) {
        UserDocument document = userMapper.createRequestToUserDocument(request);
        try {
            userDocumentRepository.save(document);
        } catch (DuplicateKeyException e) {
            log.error("User already exists: {}", e.getMessage());
            throw new RuntimeException("User already exists");
        }
        return new UserCreateDto.Response();
    }
}
