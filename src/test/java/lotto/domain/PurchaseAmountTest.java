package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import lotto.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class PurchaseAmountTest {
    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1500, 2100, 10001})
    void createPurchaseAmount_ByInvalidUnit(int amount) {
        assertThatThrownBy(() -> new PurchaseAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_PURCHASE_UNIT.getMessage());
    }
    @DisplayName("구입 금액이 1,000원 미만이면 예외가 발생한다.")
    @Test
    void createPurchaseAmount_ByLessThanMinAmount() {
        assertThatThrownBy(() -> new PurchaseAmount(500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @DisplayName("유효한 구입 금액이면 객체가 정상 생성된다.")
    @Test
    void createPurchaseAmount_Success() {
        assertThatCode(() -> new PurchaseAmount(8000))
                .doesNotThrowAnyException();
    }

    @DisplayName("구입 금액으로 로또 개수를 정확히 계산한다.")
    @Test
    void getLottoCount_Success() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(8000);
        assertThat(purchaseAmount.getLottoCount()).isEqualTo(8);
    }

}
