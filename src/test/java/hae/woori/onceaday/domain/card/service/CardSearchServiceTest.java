package hae.woori.onceaday.domain.card.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardSearchServiceTest {

    @Test
    @DisplayName("내 카드 조회 - 카드가 1개 이상 존재할 때")
    void myCardExistTest() {
        // Given: 필요한 데이터나 상태를 설정합니다.
        // 카드 정보: 사용자 프로필 이미지 URL, 사용자 이름, 카드 ID, 내용, 행, 일자, 반응

        // When: 실제 메서드를 호출합니다.
        // 예: myCardService.getMyCard(userId);

        // Then: 결과를 검증합니다.
        // 예: assertNotNull(card);
        // assertEquals(expectedCardId, card.getId());
    }

    @Test
    @DisplayName("내 카드 조회 - 카드가 존재하지 않을 때")
    void myCardNotExistTest() {
        // Given: 데이터가 없습니다.

        // When: 실제 메서드를 호출합니다.
        // 예: myCardService.getMyCard(userId);

        // Then: 결과를 검증합니다.
        // 예: assertNull(card.getId());
    }

    @Test
    @DisplayName("내 카드 조회 - 존재하지 않는 계정일 때")
    void myCardUserNotExistTest() {
        // Given: 존재하지 않는 사용자 ID

        // When: 실제 메서드를 호출합니다.
        // 예: myCardService.getMyCard(nonExistentUserId);

        // Then: 결과를 검증합니다.
        // 예: assertThrows(UserNotFoundException.class, () -> {
        //     myCardService.getMyCard(nonExistentUserId);
        // });
    }
}
