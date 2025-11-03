package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoResultTest {

    @DisplayName("당첨 통계(Map)를 정확히 집계한다.")
    @Test
    void getPrizeCounts_Success() {
        // given
        Lotto winning = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningLotto winningLotto = new WinningLotto(winning, 7);

        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)), // 5등
                new Lotto(List.of(1, 2, 3, 11, 12, 13)), // 5등
                new Lotto(List.of(1, 2, 3, 4, 10, 11)), // 4등
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),  // 3등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),  // 2등
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 꽝
        );

        // when
        LottoResult result = new LottoResult(purchasedLottos, winningLotto);
        Map<Prize, Integer> prizeCounts = result.getPrizeCounts();

        // then
        assertThat(prizeCounts)
                .containsOnly(
                        entry(Prize.FIFTH, 2),
                        entry(Prize.FOURTH, 1),
                        entry(Prize.THIRD, 1),
                        entry(Prize.SECOND, 1)
                )
                .doesNotContainKey(Prize.FIRST) // 1등은 없어야 함
                .doesNotContainKey(Prize.MISS); // 꽝은 맵에 포함 안 함
    }

    @DisplayName("총 수익률을 소수점 둘째 자리에서 반올림하여 정확히 계산한다.")
    @Test
    void calculateProfitRate_Success() {
        // given (ApplicationTest 예시와 동일한 케이스)
        Lotto winning = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningLotto winningLotto = new WinningLotto(winning, 7);
        PurchaseAmount purchaseAmount = new PurchaseAmount(8000); // 8장 구매

        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(8, 21, 23, 41, 42, 43)), // 꽝
                new Lotto(List.of(3, 5, 11, 16, 32, 38)), // 꽝
                new Lotto(List.of(7, 11, 16, 35, 36, 44)), // 꽝
                new Lotto(List.of(1, 8, 11, 31, 41, 42)), // 꽝
                new Lotto(List.of(13, 14, 16, 38, 42, 45)), // 꽝
                new Lotto(List.of(7, 11, 30, 40, 42, 43)), // 꽝
                new Lotto(List.of(2, 13, 22, 32, 38, 45)), // 꽝
                new Lotto(List.of(1, 3, 5, 14, 22, 45))  // 5등 (1, 3, 5 일치)
        );

        // when
        LottoResult result = new LottoResult(purchasedLottos, winningLotto);

        // 5,000 (당첨금) / 8,000 (투자금) * 100 = 62.5
        double profitRate = result.calculateProfitRate(purchaseAmount);

        // then
        assertThat(profitRate).isEqualTo(62.5);
    }
}