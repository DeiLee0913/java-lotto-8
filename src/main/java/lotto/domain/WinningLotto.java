package lotto.domain;

import lotto.exception.ErrorMessage;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        validate(winningNumbers, bonusNumber);

        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public Prize match(Lotto lotto) {
        int matchCount = (int) winningNumbers.getNumbers().stream()
                .filter(lotto::contains)
                .count();

        boolean bonusMatch = lotto.contains(bonusNumber);

        return Prize.valueOf(matchCount, bonusMatch);
    }

    private void validate(Lotto winningNumbers, int bonusNumber) {
        // 보너스 번호가 1~45 범위를 벗어나는지 검증
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_INVALID_RANGE.getMessage());        }

        // 보너스 번호가 기본 당첨 번호와 중복되는지 검증
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_DUPLICATE_WITH_WINNING_NUMBERS.getMessage());
        }
    }
}
