package lotto.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    // 통계 결과 저장용 불변 맵
    private final Map<Prize, Integer> prizeCounts;

    public LottoResult(List<Lotto> purchasedLottos, WinningLotto winningLotto) {
        Map<Prize, Integer> counts = new EnumMap<>(Prize.class);

        for(Lotto lotto : purchasedLottos) {
            Prize prize = winningLotto.match(lotto);

            if(prize != Prize.MISS) {
                counts.put(prize, counts.getOrDefault(prize, 0) + 1);
            }
        }
        this.prizeCounts = Collections.unmodifiableMap(counts);
    }

    public Map<Prize, Integer> getPrizeCounts() {
        return prizeCounts;
    }

    public double calculateProfitRate(PurchaseAmount purchaseAmount) {
        long totalSpent = purchaseAmount.getAmount();

        // (방어 코드) 0으로 나누는 것을 방지
        if (totalSpent == 0) {
            return 0.0;
        }

        // 5. 총 상금 계산
        long totalWinnings = 0;
        for (Map.Entry<Prize, Integer> entry : prizeCounts.entrySet()) {
            Prize prize = entry.getKey();
            int count = entry.getValue();
            totalWinnings += prize.getPrizeMoney() * count;
        }

        // 6. 수익률 계산 (e.g., (5000 / 8000.0) * 100 = 62.5)
        return (double) totalWinnings / totalSpent * 100.0;
    }
}
