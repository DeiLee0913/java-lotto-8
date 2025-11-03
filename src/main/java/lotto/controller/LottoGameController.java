package lotto.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.LottoResult;
import lotto.domain.PurchaseAmount;
import lotto.domain.WinningLotto;
import lotto.validation.InputValidator;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoGameController {

    private static final String DELIMITER = ",";

    public void run() {
        PurchaseAmount purchaseAmount = getValidPurchaseAmount();
        int lottoCount = purchaseAmount.getLottoCount();
        OutputView.printLottoCount(lottoCount);

        List<Lotto> purchasedLottos = LottoMachine.generateLottos(lottoCount);
        OutputView.printLottos(purchasedLottos);

        WinningLotto winningLotto = getValidWinningLotto();

        LottoResult lottoResult = new LottoResult(purchasedLottos, winningLotto);

        OutputView.printStatisticsHeader();
        OutputView.printRankResults(lottoResult.getPrizeCounts());

        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);
        OutputView.printProfitRate(profitRate);
    }

    private PurchaseAmount getValidPurchaseAmount() {
        while (true) {
            try {
                String input = InputView.getPurchaseAmount();
                InputValidator.validatePurchaseAmount(input);
                int amount = Integer.parseInt(input);
                return new PurchaseAmount(amount);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private WinningLotto getValidWinningLotto() {
        Lotto winningNumbers = getValidWinningNumbers();
        while (true) {
            try {
                String input = InputView.getBonusNumber();
                InputValidator.validateBonusNumber(input);
                int bonusNumber = Integer.parseInt(input);
                return new WinningLotto(winningNumbers, bonusNumber);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private Lotto getValidWinningNumbers() {
        while (true) {
            try {
                String input = InputView.getPrizeNumbers();
                InputValidator.validatePrizeNumbers(input);
                List<Integer> numbers = parseNumbers(input);
                return new Lotto(numbers);

            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private List<Integer> parseNumbers(String input) {
        return Arrays.stream(input.split(DELIMITER))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}