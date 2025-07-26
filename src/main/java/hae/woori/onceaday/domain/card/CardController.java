package hae.woori.onceaday.domain.card;

import hae.woori.onceaday.domain.card.service.MyCardCreateService;
import hae.woori.onceaday.domain.card.dto.MyCardCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final MyCardCreateService myCardCreateService;

    @PostMapping("/create")
    public MyCardCreateDto.Response create(@Valid @RequestBody MyCardCreateDto.Request request) {
        return myCardCreateService.run(request);
    }

}
