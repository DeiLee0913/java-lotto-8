package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Prize;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    private static final String PURCHASE_COUNT_MESSAGE = "%d개를 구매했습니다.%n";
    private static final String STATISTICS_HEADER = "%n당첨 통계%n---%n";

    private static final String RANK_RESULT_FORMAT = "%d개 일치 (%,d원) - %d개%n";
    private static final String SECOND_PLACE_FORMAT = "%d개 일치, 보너스 볼 일치 (%,d원) - %d개%n";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.%n";

    private OutputView() {
    }

    public static void printLottoCount(int count) {
        System.out.printf(PURCHASE_COUNT_MESSAGE, count);
    }

    public static void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            System.out.println(formatLottoNumbers(lotto.getNumbers()));
        }
        System.out.println();
    }

    private static String formatLottoNumbers(List<Integer> numbers) {
        // Lotto 클래스가 생성 시점에 이미 정렬했다고 가정하고 포맷팅
        return numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public static void printStatisticsHeader() {
        System.out.printf(STATISTICS_HEADER);
    }

    public static void printRankResults(Map<Prize, Integer> prizeCounts) {
        // 역순출력(5등 -> 1등)
        List<Prize> prizesToDisplay = List.of( // 4. 리스트 타입 및 변수명 변경
                Prize.FIFTH,
                Prize.FOURTH,
                Prize.THIRD,
                Prize.SECOND,
                Prize.FIRST
        );

        for (Prize prize : prizesToDisplay) { // 5. 루프 변수 타입 및 변수명 변경
            String format = (prize == Prize.SECOND) ? SECOND_PLACE_FORMAT : RANK_RESULT_FORMAT;

            System.out.printf(format,
                    prize.getMatchCount(),
                    prize.getPrizeMoney(),
                    prizeCounts.getOrDefault(prize, 0)
            );
        }
    }

    public static void printProfitRate(double profitRate) {
        System.out.printf(PROFIT_RATE_FORMAT, profitRate);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
