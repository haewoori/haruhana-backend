package hae.woori.onceaday.domain.card.service;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.MyCardDeleteDto;
import hae.woori.onceaday.exception.ClientSideException;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyCardDeleteService implements SimpleService<MyCardDeleteDto.RequestWrapper, MyCardDeleteDto.Response> {

    private final CardDocumentRepository cardDocumentRepository;

    @Override
    public MyCardDeleteDto.Response run(MyCardDeleteDto.RequestWrapper request) {
        CardDocument cardDocument = cardDocumentRepository.findById(request.cardId())
                .orElseThrow(() -> new ClientSideException("Card not found with ID: " + request.cardId()));

        if(!cardDocument.getUserId().equals(request.userId())) {
            log.error("Wrong user trying to delete card: {}", request.userId());
            throw new ClientSideException("Wrong user ID");
        }

        cardDocumentRepository.delete(cardDocument);
        return new MyCardDeleteDto.Response();
    }
}
