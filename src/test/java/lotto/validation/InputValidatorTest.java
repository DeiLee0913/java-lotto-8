package lotto.validation;

import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class InputValidatorTest {
    @DisplayName("입력이 null이거나 빈 값이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void validateIsNotEmpty_ByEmptyInput(String input) {
        assertThatThrownBy(() -> InputValidator.validatePurchaseAmount(input)) // validatePurchaseAmount가 내부적으로 호출
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INPUT_IS_EMPTY.getMessage());
    }

    @DisplayName("입력이 숫자가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1000j", "abc", " 1000"}) // trim()을 안 하므로 " 1000"도 실패
    void validateIsNumeric_ByNonNumericInput(String input) {
        assertThatThrownBy(() -> InputValidator.validatePurchaseAmount(input)) // validatePurchaseAmount가 내부적으로 호출
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INPUT_NOT_NUMERIC.getMessage());
    }

    @DisplayName("당첨 번호 형식이 6개가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
    void validatePrizeNumbers_ByInvalidCount(String input) {
        assertThatThrownBy(() -> InputValidator.validatePrizeNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INPUT_INVALID_WINNING_NUMBERS_FORMAT.getMessage());
    }

    @DisplayName("당첨 번호에 숫자가 아닌 값이 포함되면 예외가 발생한다.")
    @Test
    void validatePrizeNumbers_ByNonNumeric() {
        assertThatThrownBy(() -> InputValidator.validatePrizeNumbers("1,2,3,a,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INPUT_NOT_NUMERIC.getMessage());
    }

    @DisplayName("당첨 번호에 빈 값이 포함되면 예외가 발생한다.")
    @Test
    void validatePrizeNumbers_ByEmptyValue() {
        assertThatThrownBy(() -> InputValidator.validatePrizeNumbers("1,2,,4,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INPUT_IS_EMPTY.getMessage());
    }
}
