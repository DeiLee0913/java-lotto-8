package lotto.domain;

import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class WinningLottoTest {
        private Lotto winningNumbers;

        @BeforeEach
        void setUp() {
            winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        }

        @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
        @Test
        void createWinningLotto_ByDuplicateBonusNumber() {
            assertThatThrownBy(() -> new WinningLotto(winningNumbers, 6)) // 보너스 6 (중복)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_DUPLICATE_WITH_WINNING_NUMBERS.getMessage());
        }

        @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
        @Test
        void createWinningLotto_ByInvalidBonusRange() {
            assertThatThrownBy(() -> new WinningLotto(winningNumbers, 0)) // 1 미만
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_INVALID_RANGE.getMessage());

            assertThatThrownBy(() -> new WinningLotto(winningNumbers, 46)) // 45 초과
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.BONUS_INVALID_RANGE.getMessage());
        }

        @DisplayName("match 메서드 - 1등(6개 일치)을 정확히 반환한다.")
        @Test
        void match_FirstPrize() {
            WinningLotto winningLotto = new WinningLotto(winningNumbers, 7);
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            assertThat(winningLotto.match(userLotto)).isEqualTo(Prize.FIRST);
        }

        @DisplayName("match 메서드 - 2등(5개 + 보너스 일치)을 정확히 반환한다.")
        @Test
        void match_SecondPrize() {
            WinningLotto winningLotto = new WinningLotto(winningNumbers, 7);
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7)); // 5개 + 보너스 7
            assertThat(winningLotto.match(userLotto)).isEqualTo(Prize.SECOND);
        }

        @DisplayName("match 메서드 - 3등(5개 일치)을 정확히 반환한다.")
        @Test
        void match_ThirdPrize() {
            WinningLotto winningLotto = new WinningLotto(winningNumbers, 7);
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8)); // 5개 + 보너스(7) 아님
            assertThat(winningLotto.match(userLotto)).isEqualTo(Prize.THIRD);
        }
}
