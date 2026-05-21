# RED 단계 수정 전략 — 결함 분석 및 GREEN 전환

| 항목 | 내용 |
|------|------|
| 문서명 | 08_red_단계_수정전략.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 역할 | Java QA — 결함 분석·최소 수정·GREEN 검증 |
| 기준 실행 | `mvn test -Dtest=ConversionEngineNormalTest` (6 Failures) |
| 전체 실행 | `mvn test` (35 tests — 26 Failures, 5 Errors) |
| 관련 | [07_test_plan.md](./07_test_plan.md), [06_전략_RED.md](./06_전략_RED.md) |

---

## 0. 전제

- 테스트 기대값은 **계약 기준(정확)** — 테스트 수정 없이 **프로덕션 RED 스텁**을 GREEN 최소 구현으로 교체한다.
- `ConversionResult` 등 **record/기본 구조체 시그니처 변경 금지**.
- 레거시 `UnitConverter.java`의 if-else는 본 문서 **1차 범위 외**(Domain `ConversionEngine` 우선).

---

## 1) 테스트 실패 — 기대/실제 차이 요약

### 1.1 `ConversionEngineNormalTest` (6건 — 본 문서 1차 대상)

| # | 테스트 메서드 | 입력/조건 | 기대 (expected) | 실제 (actual) | 차이 요약 |
|---|---------------|-----------|-----------------|---------------|-----------|
| 1 | `test_meterToFeet_validInput_returnsConvertedFeet` | meter, 2.5 → feet | **8.20210** | **0.000000** | BR-001 미적용, 환산 0 |
| 2 | `test_meterToYard_oneMeter_returnsYardRatio` | meter, 1.0 → yard | **1.09361** | **0.000000** | BR-002 미적용 |
| 3 | `test_feetToMeter_oneFeet_returnsMeterInverse` | feet, 1.0 → meter | **0.30480** | **0.000000** | 역환산 미구현 |
| 4 | `test_meterToFeet_fiveMeters_returnsSixteenPointFour` | meter, 5.0 → feet | **16.4042** | **0.000000** | 비율×5 미적용 |
| 5 | `test_feetToYard_threeFeet_returnsYardViaMeterHub` | feet, 3.28084 → yard | **1.09361** | **0.000000** | meter 허브 미경유 |
| 6 | `test_convertAll_oneMeter_returnsAllRegisteredUnits` | convertAll, 1.0 | **size 3** | **size 0** | 빈 리스트 반환 |

**대표 메시지 (Surefire):**

```text
1 meter = 3.28084 feet ==> expected: <8.2021> but was: <0.0>
expected: <3> but was: <0>
```

### 1.2 전체 `mvn test` 추가 실패 패턴 (2차 GREEN)

| 유형 | 대표 메시지 | 근본 원인 |
|------|-------------|-----------|
| Boundary | `expected: <3.28E9> but was: <0.0>` | 동일 — `convert()` 스텁 |
| Parser | `UnsupportedOperationException: RED: implement LineParser` | `LineParser.java:9` |
| Config | `IOException: RED: implement ConfigurationLoader.load` | `ConfigurationLoader.java` |
| Registry | `expected: <true> but was: <false>` | `register()` no-op |

---

## 2) 버그 위치 특정

### 2.1 1차 (NormalTest 6건 직접 원인)

| 우선순위 | 파일 | 줄 | 코드 | 결함 |
|:--------:|------|-----|------|------|
| **P0** | `ConversionEngine.java` | **17–19** | `return 0.0;` | 환산 로직 전체 미구현 |
| **P0** | `ConversionEngine.java` | **22–24** | `return Collections.emptyList();` | `convertAll` 미구현 |
| **P1** | `UnitRegistry.java` | **15–17** | `withDefaults()` 빈 registry | 시드 meter/feet/yard 없음 → `getMetersPerOne` 실패 가능 |

```17:24:src/main/java/com/unitconverter/entity/ConversionEngine.java
    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        // RED: meter→feet 등 환산 미구현 (항상 0 반환 → assertion FAIL)
        return 0.0;
    }

    public List<ConversionResult> convertAll(String sourceUnit, double sourceAmount) {
        // RED: 전 단위 변환 미구현
        return Collections.emptyList();
```

```15:17:src/main/java/com/unitconverter/entity/UnitRegistry.java
    public static UnitRegistry withDefaults() {
        // RED: meter/feet/yard 시드 없음
        return new UnitRegistry();
```

### 2.2 2차 (전체 35건)

| 파일 | 줄 | 내용 |
|------|-----|------|
| `LineParser.java` | 8–9 | `UnsupportedOperationException` |
| `InputValidator.java` | 16–17 | validate no-op |
| `ConfigurationLoader.java` | 12–13 | `load()` 항상 IOException |
| `UnitRegistry.java` | 20–21, 24–25 | `register` no-op, `contains` 항상 false |

### 2.3 레거시 참고 (본 1차 범위 외)

| 파일 | 줄 | 비고 |
|------|-----|------|
| `UnitConverter.java` | 11–19 | if-else 분기 — CLI 레거시; Domain GREEN 후 위임 권장 |

---

## 3) 결함 심각도 분류 및 근거

### 3.1 `ConversionEngineNormalTest` 기준

| ID | 테스트 | 심각도 | 근거 |
|----|--------|--------|------|
| D-01 | meter→feet 2.5 | **Critical** | 핵심 변환 결과 완전 오류 (0 ≠ 8.2021), AC-01·BR-001 위반 |
| D-02 | meter→yard | **Critical** | 동일 — BR-002 미적용 |
| D-03 | feet→meter | **Critical** | 역환산 0 — 학습 목표 환산 경로 무효 |
| D-04 | meter 5.0→feet | **Critical** | README 예시 수준 결과 불가 |
| D-05 | feet→yard 허브 | **Critical** | BR-003(meter 경유) 검증 불가 |
| D-06 | convertAll size | **Critical** | F-02 “전 단위 출력” 기능 자체 0건 |

| 심각도 | 본 실패 목록 해당 | 설명 |
|--------|-------------------|------|
| **Critical** | 6/6 | 변환 결과·건수가 계약과 **정면 불일치** |
| Major | 0 | (1차) 오차 초과가 아니라 **미계산** |
| Minor | 0 | 출력 포맷(PLAIN 1자리) 검증 전 단계 |
| Info | 0 | 스타일·주석만의 문제 아님 |

### 3.2 전체 스위트 보조

| 심각도 | 건수(대략) | 예 |
|--------|------------|-----|
| Critical | 20+ | convert 0.0, empty list, load 실패 |
| Major | 0–2 | (GREEN 후) ε 초과만 해당 가능 |
| Minor | 0 | — |
| Info | 0 | RED 주석은 의도적 |

---

## 4) 최소 변경 수정 방안 (GREEN 통과 우선)

### 4.1 원칙

- **변경 최소화:** RED 스텁 2메서드 + `withDefaults()` 복원만으로 `ConversionEngineNormalTest` 6건 GREEN.
- **구조체 금지:** `ConversionResult` record 필드·생성자 변경 없음.
- **if-else:** Domain은 **meter 허브 공식** 1경로(분기 최소). 레거시 CLI if-else는 2차.

**환산 공식 (테스트와 동일):**

```text
amountInMeters = sourceAmount × metersPerOne(sourceUnit)
targetAmount   = amountInMeters / metersPerOne(targetUnit)
```

- BR-001: `1 meter = 3.28084 feet` → `METERS_PER_FEET = 1/3.28084`
- BR-002: `1 meter = 1.09361 yard` → `METERS_PER_YARD = 1/1.09361`

### 4.2 Diff 제안 — `UnitRegistry.java`

```diff
     public static UnitRegistry withDefaults() {
-        // RED: meter/feet/yard 시드 없음
-        return new UnitRegistry();
+        UnitRegistry registry = new UnitRegistry();
+        registry.register("meter", ConversionConstants.METERS_PER_METER);
+        registry.register("feet", ConversionConstants.METERS_PER_FEET);
+        registry.register("yard", ConversionConstants.METERS_PER_YARD);
+        return registry;
     }

     public void register(String unitName, double metersPerOne) {
-        // RED: 동적 등록 no-op
+        Objects.requireNonNull(unitName, "unitName");
+        if (unitName.isBlank()) {
+            throw new DomainException("ERR-DOM-005", "Unit name must not be blank.");
+        }
+        if (metersPerOne <= 0 || metersPerOne > 1_000_000) {
+            throw new DomainException("ERR-DOM-006", "metersPerOneUnit must be in (0, 1_000_000].");
+        }
+        if (metersPerOneUnit.containsKey(unitName)) {
+            throw new DomainException("ERR-DOM-004", "Unit already registered: " + unitName);
+        }
+        metersPerOneUnit.put(unitName, metersPerOne);
     }

     public boolean contains(String unitName) {
-        return false;
+        return metersPerOneUnit.containsKey(unitName);
     }
```

> `register`/`contains`는 1차 NormalTest에 필수는 아니나, 2차 `DynamicRegistrationTest` GREEN에 필요.

### 4.3 Diff 제안 — `ConversionEngine.java`

```diff
+import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;

     public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
-        return 0.0;
+        validateAmount(sourceAmount);
+        double amountInMeters = sourceAmount * registry.getMetersPerOne(sourceUnit);
+        double targetAmount = amountInMeters / registry.getMetersPerOne(targetUnit);
+        if (!Double.isFinite(targetAmount)) {
+            throw new DomainException("ERR-VAL-001", "Conversion result is not finite.");
+        }
+        return targetAmount;
     }

     public List<ConversionResult> convertAll(String sourceUnit, double sourceAmount) {
-        return Collections.emptyList();
+        validateAmount(sourceAmount);
+        List<ConversionResult> results = new ArrayList<>();
+        for (String targetUnit : registry.getAllUnitNames()) {
+            double targetAmount = convert(sourceUnit, sourceAmount, targetUnit);
+            results.add(new ConversionResult(sourceUnit, sourceAmount, targetUnit, targetAmount));
+        }
+        return results;
     }
+
+    private void validateAmount(double amount) {
+        if (!Double.isFinite(amount)) {
+            throw new DomainException("ERR-VAL-001", "Value must be a finite number.");
+        }
+        if (amount < 0) {
+            throw new DomainException("ERR-VAL-002", "Length must be non-negative. Got " + amount + ".");
+        }
+    }
```

**if-else 대안 (레거시 `UnitConverter.java`만 2차 적용 시):**

```java
// UnitConverter.java — 기존 if-else 유지, unknown unit만 추가
if (unit.equals("meter")) {
    meterValue = value;
} else if (unit.equals("feet")) {
    meterValue = value / 3.28084;  // 1 meter = 3.28084 feet
} else if (unit.equals("yard")) {
    meterValue = value / 1.09361;  // 1 meter = 1.09361 yard
} else {
    System.err.println("ERROR [ERR-DOM-003]: Unknown unit \"" + unit + "\".");
    return;
}
```

### 4.4 2차 GREEN 체크리스트 (전체 `mvn test`)

| 순서 | 파일 | 최소 작업 |
|:----:|------|-----------|
| 1 | `ConversionEngine` + `UnitRegistry` | §4.2–4.3 적용 |
| 2 | `LineParser` | `unit:value` 파싱, ERR-FMT/VAL |
| 3 | `InputValidator` | 음수·미등록 검증 |
| 4 | `ConfigurationLoader` | JSON/YAML load + fallback |

---

## 5) `mvn test` Green 확인 절차

### 5.1 1차 — NormalTest만 (Domain 핵심)

```bash
cd d:\Vs_workplace\Java_project\UnitConverter_01

# §4.2–4.3 적용 후
mvn test -Dtest=ConversionEngineNormalTest
```

**통과 기준:**

```text
Tests run: 6, Failures: 0, Errors: 0
BUILD SUCCESS
```

| TC | 검증 값 (ε=1e-5) |
|----|------------------|
| TC-B-01 | 2.5 m → **8.20210** ft |
| TC-B-02 | 1.0 m → **1.09361** yd |
| TC-B-03 | 1.0 ft → **0.30480** m |
| TC-B-04 | 5.0 m → **16.4042** ft |
| TC-B-05 | 3.28084 ft → **1.09361** yd |
| TC-B-06 | convertAll size = **3** |

### 5.2 2차 — Boundary + Domain

```bash
mvn test -Dtest=ConversionEngineNormalTest,ConversionEngineBoundaryTest
```

### 5.3 3차 — 전체 스위트

```bash
mvn clean test
```

**목표:**

```text
Tests run: 35, Failures: 0, Errors: 0
BUILD SUCCESS
```

### 5.4 커버리지 (선택)

```bash
mvn verify
# target/site/jacoco/index.html
```

| 레이어 | 목표 |
|--------|------|
| entity + control | Line ≥ 95% |
| boundary | Line ≥ 85% |

### 5.5 RED → GREEN 커밋 메시지 예

```text
feat(entity): GREEN DT-02–06 ConversionEngine meter hub (BR-001–003)

#GREEN TC-B-01: meter→feet 8.20210
#RED stub removed: convert() return 0.0
```

---

## 6. 수정 후 기대 결과 매핑

| 테스트 | 수정 파일 | GREEN 후 actual |
|--------|-----------|-----------------|
| `test_meterToFeet_validInput_*` | `ConversionEngine:17` | 8.20210 |
| `test_meterToYard_*` | 동일 | 1.09361 |
| `test_feetToMeter_*` | 동일 + `UnitRegistry.withDefaults` | 0.30480 |
| `test_convertAll_*` | `ConversionEngine:22` + registry size 3 | 3 results |

---

## 7. 관련 문서

| 문서 | 설명 |
|------|------|
| [07_test_plan.md](./07_test_plan.md) | TC·경계값·JaCoCo |
| [06_전략_RED.md](./06_전략_RED.md) | RED 브랜치·TDD 흐름 |
| [00_PRD.md](./00_PRD.md) | BR-001–003, F-01/F-02 |

---

*본 문서는 RED 실패 분석 및 GREEN 최소 diff 제안이다. 테스트 코드는 계약 기준으로 유지하고, 프로덕션 스텁만 교체한다.*
