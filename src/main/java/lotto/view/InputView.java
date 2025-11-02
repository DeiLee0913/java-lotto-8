package lotto.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public final class InputView {
    private static final String PRICE_INPUT_PROMPT = "구입금액을 입력해주세요.";
    private static final String PRIZE_NUMBER_INPUT_PROMPT = "당첨 번호를 입력해주세요.";
    private static final String BOUNUS_NUMBER_INPUT_PROMPT = "보너스 번호를 입력해주세요.";

    private InputView() {
    }

    public static String getPurchaseAmount() {
        System.out.println(PRICE_INPUT_PROMPT);
        return readLine().trim();
    }

    public static String getPrizeNumbers() {
        System.out.println(PRIZE_NUMBER_INPUT_PROMPT);
        return readLine().trim();
    }

    public static String getBonusNumber() {
        System.out.println(BOUNUS_NUMBER_INPUT_PROMPT);
        return readLine().trim();
    }
}
