package lotto.exception;

public enum ErrorMessage {
    // [ERROR] 접두사는 OutputView가 붙여주므로 순수 메시지만 작성
    INVALID_PURCHASE_UNIT("구입 금액은 1,000원 단위여야 합니다."),
    INVALID_PURCHASE_AMOUNT("구입 금액은 1,000원 이상이어야 합니다."),

    LOTTO_INVALID_SIZE("로또 번호는 6개여야 합니다."),
    LOTTO_DUPLICATE_NUMBER("로또 번호는 중복될 수 없습니다."),
    LOTTO_INVALID_RANGE("로또 번호는 1에서 45 사이의 정수입니다."),

    BONUS_DUPLICATE_WITH_WINNING_NUMBERS("보너스 번호는 당첨 번호와 중복될 수 없습니다."),
    BONUS_INVALID_RANGE("보너스 번호는 1에서 45 사이의 정수입니다."),

    INPUT_NOT_NUMERIC("입력은 숫자여야 합니다."),
    INPUT_IS_EMPTY("입력이 비어있습니다."),
    INPUT_INVALID_WINNING_NUMBERS_FORMAT("당첨 번호 형식(쉼표 6개)이 올바르지 않습니다.");

   private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
