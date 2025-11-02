package lotto.domain;

import lotto.exception.ErrorMessage;

import java.util.*;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        // 외부 리스트의 수정을 막기 위해 새 리스트 생성 및 정렬
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        this.numbers = Collections.unmodifiableList(sortedNumbers); // 불변 리스트로 생성
    }

    public List<Integer> getNumbers() {
        return numbers; // 불변 리스트로 생성했으므로 방어적 복사 불필요
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public int counteMatchingNumber(List<Integer> winningNumbers) {
        return (int) winningNumbers.stream()
                .filter(numbers::contains)
                .count();
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateDuplicate(numbers);
        validateInRange(numbers);
    }

    // 리스트의 크기가 6인지 검증
    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_INVALID_SIZE.getMessage());
        }
    }

    // 중복된 번호가 있는지 검증
    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (numbers.size() != uniqueNumbers.size()) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_DUPLICATE_NUMBER.getMessage());
        }
    }

    // 로또 번호가 1에서 45 사이의 값인지 검증
    private void validateInRange(List<Integer> numbers) {
        if (numbers.stream().anyMatch(number -> number < 1 || number > 45)) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_INVALID_RANGE.getMessage());
        }
    }
}