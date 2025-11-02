# java-lotto-precourse

## Controller ( LottoGameController )
[ ] run() 메서드에서 게임의 전체 로직(구매 -> 당첨 입력 -> 결과 발표)을 순서대로 호출
[ ] InputView를 호출하여 raw string (e.g., "8000", "1,2,3,4,5,6") 입력을 받음
[ ] 입력받은 raw string을 Validator를 통해 1차 검증
[ ] 검증된 string을 Integer, List<Integer> 등으로 파싱(변환)
[ ] 변환된 데이터를 Model 객체(e.g., PurchaseAmount, WinningLotto)의 생성자에 전달하여 객체 생성을 "요청"
[ ] PurchaseAmount로부터 구매할 로또 개수를 받아, LottoMachine(로또 생성기)에 로또 생성을 "요청"
[ ] OutputView를 호출하여 구매한 로otto 목록을 "전달" (출력 요청)
[ ] LottoResult 객체를 생성하여 통계 및 수익률 계산을 "요청"
[ ] LottoResult로부터 통계 결과(Map)와 수익률(double)을 받아 OutputView에 "전달" (출력 요청)
[ ] Model 또는 View에서 IllegalArgumentException 발생 시, try-catch로 잡아서 OutputView.printError()를 호출하고, 해당 입력 단계부터 다시 시도하도록 제어

## Model Package
### PurchaseAmount
[ ] 생성자에서 int 금액을 받음
[ ] 1,000원 미만인지 검증 -> 예외 발생 가능
[ ] 1,000원 단위로 나누어 떨어지는지 검증 -> 예외 발생 가능
[ ] getLottoCount(): 구입한 로또 개수 (amount / 1000)를 반환

### Lotto (로또 1장)
[ ] 생성자에서 List<Integer>를 받음
[ ] 생성자에서 List<Integer>를 오름차순으로 정렬하여 final 필드에 저장
[ ] getNumbers(): View가 출력할 수 있도록 불변(Unmodifiable) 리스트 반환
[ ] contains(int number): 특정 번호를 포함하고 있는지 여부 반환 (당첨 비교 시 사용)
// 로또 추첨번호가 아닌 로또 당첨번호를 위한 검증
[ ] numbers.size() != 6: 리스트의 크기가 6이 아닌지 검증
[ ] numbers에 중복된 숫자가 있는지 검증 (e.g., [1, 2, 3, 4, 5, 5])
[ ] numbers의 숫자가 1~45 범위를 벗어나는지 검증 (e.g., [0, 1, 2, 3, 4, 46])

### LottoMachine (로또 생성기) - 신규 제안
[ ] generate(int count): count 개수만큼 로또를 생성
[ ] camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange(1, 45, 6) API를 사용하여 Lotto 객체를 생성

### WinningLotto (당첨 번호)
[ ] 생성자에서 당첨 번호와 보너스 번호를 받음
// 보너스 번호가 아닌 기본 당첨 번호는 로또에서 검증하므로 이 클래스에서는 보너스 번호만 검증
[ ] 보너스 번호가 1~45 범위를 벗어나는지 검증 -> 예외 발생 가능
[ ] 보너스 번호가 당첨 번호 6개와 중복되는지 검증 -> 예외 발생 가능
[ ] match(Lotto lotto): 구매한 로또 1장과 비교하여 당첨 등급(Prize)을 반환

### Prize (당첨 등급 - Enum)
[ ] FIRST, SECOND, THIRD, FOURTH, FIFTH, MISS(꽝) 상수 정의
[ ] 각 상수는 일치 개수와 상금(long)을 멤버로 가짐
[ ] static valueOf(int matchCount, boolean bonusMatch): 일치 개수와 보너스 여부로 Prize 등급을 찾아 반환
[ ] getMatchCount(), getPrizeMoney() Getter 제공

### LottoResult (통계 결과)
[ ] 생성자에서 **구매한 로또 목록(List<Lotto>)**과 **당첨 번호(WinningLotto)**를 받음
[ ] 생성자 내부에서 모든 로또를 순회하며 winningLotto.match()를 호출
[ ] 당첨 통계를 집계하여 Map<Prize, Integer> 형태로 저장
[ ] getPrizeCounts(): 집계된 Map을 반환
[ ] calculateProfitRate(PurchaseAmount amount): 총 상금과 구입 금액을 비교하여 수익률 (double)을 계산 (소수점 반올림 로직 포함)

## View Package
### InputView
[ ] getPurchaseAmount(): "구입금액을 입력해 주세요." 출력 및 Console.readLine() 반환
[ ] getPrizeNumbers(): "당첨 번호를 입력해 주세요." 출력 및 Console.readLine() 반환
[ ] getBonusNumber(): "보너스 번호를 입력해 주세요." 출력 및 Console.readLine() 반환

### OutputView
[ ] printLottoCount(): "%d개를 구매했습니다.%n" 형식으로 출력
[ ] printLottos(List<Lotto> lottos): 전달받은 Lotto 목록을 순회
[ ] formatLottoNumbers(List<Integer> numbers): lotto.getNumbers()로 받은 리스트를 "[1, 2, 3, 4, 5, 6]" 형식의 문자열로 포맷팅
[ ] printStatisticsHeader(): "당첨 통계\n---" 출력
[ ] printRankResults(Map<Prize, Integer> prizeCounts): Prize Enum을 FIFTH~FIRST 순서로 순회하며 정해진 형식으로 통계 출력
[ ] printProfitRate(double profitRate): "총 수익률은 %.1f%%입니다.%n" 형식으로 수익률 출력
[ ] printError(String message): [ERROR] 메시지 출력

## Validation
### CommonValidatior
[ ] validateIsNumeric(String input): 입력이 숫자인지 검증
[ ] validateIsNotEmpty(String input): 입력이 null이거나 빈 문자열("")인지 검증
[ ] validateIsNumeric(String input): 문자열이 숫자로만 구성되었는지 검증 (e.g., "8000원", "abc")

### LottoInputValidator
[ ] validatePrizeNumbersFormat(String input): 쉼표(,)로 나눴을 때 정확히 6개인지 **'개수'**를 검증
[ ] validateAllNumericInList(String... numbers): 쉼표로 분리된 값들이 모두 숫자인지 검증 (e.g., "1,2,3,a,5,6")

## 구현 파일 구조(예상)
src/main/java/lotto/
│
├── Application.java
│
├── controller/
│   └── LottoGameController.java
│
├── domain
│   ├── Lotto.java          
│   ├── PurchaseAmount.java 
│   ├── WinningLotto.java   
│   ├── LottoMachine.java          
│   ├── Prize.java          
│   └── LottoResult.java  
│
├── view/
│   ├── InputView.java  
│   └── OutputView.java 
│
└── validation
    └── InputValidator.java 