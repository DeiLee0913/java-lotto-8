package lotto.domain;

public enum Prize {
    FIRST(6, 2_000_000_000L),  // 1등
    SECOND(5, 30_000_000L),   // 2등 (보너스 필요)
    THIRD(5, 1_500_000L),    // 3등 (보너스 불필요)
    FOURTH(4, 50_000L),     // 4등
    FIFTH(3, 5_000L),      // 5등
    MISS(0, 0L);           // 꽝

    private final int matchCount;
    private final long prizeMoney;

    Prize(int matchCount, long prizeMoney) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
    }

    // 일치하는 번호 개수와 보너스 볼 일치 여부를 받아 당첨 등수(Prize)를 반환
    public static Prize valueOf(int matchCount, boolean bonusMatch) {
        if (matchCount == 6) {
            return FIRST;
        }
        if (matchCount == 5 && bonusMatch) {
            return SECOND;
        }
        if (matchCount == 5) {
            return THIRD;
        }
        if (matchCount == 4) {
            return FOURTH;
        }
        if (matchCount == 3) {
            return FIFTH;
        }
        return MISS;
    }

    // OutputView에서 "N개 일치"를 출력하기 위해 사용
    public int getMatchCount() {
        return matchCount;
    }

    // OutputView에서 "(N원)"을 출력하고 수익률을 계산하기 위해 사용
    public long getPrizeMoney() {
        return prizeMoney;
    }
}