package hae.woori.onceaday.domain.card.service;

import java.util.List;

import hae.woori.onceaday.domain.SimpleService;
import hae.woori.onceaday.domain.card.dto.MyCardSearchDto;
import hae.woori.onceaday.domain.card.mapper.CardMapper;
import hae.woori.onceaday.domain.card.vo.CardVo;
import hae.woori.onceaday.persistence.document.CardDocument;
import hae.woori.onceaday.persistence.repository.CardDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardSearchService implements SimpleService<MyCardSearchDto.Request, MyCardSearchDto.Response> {

    private final CardDocumentRepository cardDocumentRepository;
    private final CardMapper cardMapper;

    @Override
    public MyCardSearchDto.Response run(MyCardSearchDto.Request input) {
        List<CardDocument> cardDocumentList = cardDocumentRepository.findByCreateTimeGreaterThanEqualAndCreateTimeLessThan(
            input.date().atStartOfDay(), input.date().plusDays(1).atStartOfDay());

        List<CardDocument> myCardDocumentList = cardDocumentList.stream()
            .filter(cardDocument -> cardDocument.getUserId().equals(input.userId()))
            .toList();
        List<CardVo> myCards = cardMapper.cardDocumentsToCardVos(myCardDocumentList);
        List<CardDocument> otherCardDocumentList = cardDocumentList.stream().filter(cardDocument -> !cardDocumentList.contains(cardDocument))
            .toList();
        List<CardVo> otherCards = cardMapper.cardDocumentsToCardVos(otherCardDocumentList);
        // Implement the logic to search for cardDocumentList here
        return new MyCardSearchDto.Response(myCards, otherCards);
    }
}
