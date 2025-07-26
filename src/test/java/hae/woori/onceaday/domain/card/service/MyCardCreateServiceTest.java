package hae.woori.onceaday.domain.card.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyCardCreateServiceTest {

    @Test
    @DisplayName("내 카드 생성 - 처음 생성 시")
    void myCardCreateBeginningTest() {
        // Given: 카드 정보가 없음

        // When: 실제 메서드를 호출합니다.
        // 예: myCardService.createMyCard(userId, cardContent);

        // Then: 결과를 검증합니다.
        // 예: 생성되었는지 확인
    }

    @Test
    @DisplayName("내 카드 생성 - 카드가 존재할 때")
    void myCardCreateExistTest() {
        // Given: 카드 정보 존재

        // When: 실제 메서드를 호출합니다.
        // 예: myCardService.createMyCard(userId, cardContent);

        // Then: 결과를 검증합니다.
        // 예: 존재하는 카드들 + 새로 생성된 카드가 모두 조회되는지 확인
    }
}
