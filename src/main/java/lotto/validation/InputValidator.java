package lotto.validation;

import lotto.exception.ErrorMessage;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");
    private static final String DELIMITER = ",";

    private InputValidator() {
    }

    public static void validatePurchaseAmount(String input) {
        validateIsNotEmpty(input);
        validateIsNumeric(input);
    }

    public static void validatePrizeNumbers(String input) {
        validateIsNotEmpty(input); // "1,2,3,4,5,6"

        String[] numbers = input.split(DELIMITER);
        if (numbers.length != 6) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_INVALID_WINNING_NUMBERS_FORMAT.getMessage());
        }

        for (String number : numbers) {
            validateIsNotEmpty(number); // "1,2,,4,5,6" 같은 케이스 방지
            validateIsNumeric(number.trim()); // "1, 2, a, 4, 5, 6" 같은 케이스 방지
        }
    }

    public static void validateBonusNumber(String input) {
        validateIsNotEmpty(input);
        validateIsNumeric(input);
    }

    // 빈 입력인지 검증
    private static void validateIsNotEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_IS_EMPTY.getMessage());
        }
    }

    // 입력이 숫자인지 검증
    private static void validateIsNumeric(String input) {
        if (!NUMERIC_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_NOT_NUMERIC.getMessage());
        }
    }
}
