package hae.woori.onceaday.domain.card.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import hae.woori.onceaday.domain.card.external.CardUserGateway;
import hae.woori.onceaday.domain.card.mapper.CardMapper;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyCardCreateService implements SimpleService<MyCardCreateDto.RequestWrapper, MyCardCreateDto.Response> {

	private final CardMapper cardMapper;
	private final CardDocumentRepository cardDocumentRepository;
	private final CardUserGateway cardUserGateway;

	@Override
	public MyCardCreateDto.Response run(MyCardCreateDto.RequestWrapper request) {
		if (!cardUserGateway.checkUserExistsById(request.userId())) {
			throw new ClientSideException("User does not exist with userId: " + request.userId());
		}
		CardDocument cardDocument = cardMapper.createRequestWrapperToCardDocument(request);
		cardDocumentRepository.save(cardDocument);
		return new MyCardCreateDto.Response();
	}
}
