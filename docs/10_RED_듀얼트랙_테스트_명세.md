# Dual-Track RED 테스트 명세 — UI + Logic

| 항목 | 내용 |
|------|------|
| 문서명 | 10_RED_듀얼트랙_테스트_명세.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 방법론 | Dual-Track UI + Logic TDD — **RED만** (GREEN·REFACTOR·프로덕션 구현 금지) |
| 기준 코드 | `UnitConverter.java` (레거시), `com.unitconverter.*` (BCE 스텁) |
| 관련 | [07_test_plan.md](./07_test_plan.md), [09_defect_list.md](./09_defect_list.md) |

---

## 현재 코드 상태 (관찰)

| 구분 | 파일 | 상태 | RED 영향 |
|------|------|------|----------|
| 레거시 CLI | `UnitConverter.java` | 단일 `main()`, if-else 3분기, **예외 없음**, `Scanner` | Boundary E2E 미연결 |
| 파싱 | `LineParser.java:9` | `UnsupportedOperationException` | Track A 전건 **FAIL/ERROR** |
| 검증 | `InputValidator.java:16` | validate **no-op** | 음수·미등록 **통과** (FAIL) |
| 환산 | `ConversionEngine.java:19` | `return 0.0` | Track B 수치 전건 **FAIL** |
| 전체 변환 | `ConversionEngine.java:24` | `emptyList()` | convertAll **FAIL** |
| 레지스트리 | `UnitRegistry.java:15` | 시드 없음, register no-op | 등록·contains **FAIL** |
| 설정 | `ConfigurationLoader.java:13` | load → `IOException` | loadConfig **FAIL** |
| JSON 출력 | *(클래스 없음)* | `OutputRenderer` / JSON 미존재 | TC-A-RED-06 **컴파일 전 설계** |

**고정 비율 (Background):**

- BR-001: `1 meter = 3.28084 feet`
- BR-002: `1 meter = 1.09361 yard`
- BR-003: feet ↔ yard **meter 허브만**

---

# UI RED Tests — Test ID / Given/When/Then / Invariant

> **Track A — Boundary/UI** · 예외 타입: `IllegalArgumentException` (테스트 계약)  
> **Mock:** Domain `ConversionEngine` / `UseCase` — UI 테스트는 파싱·검증·포맷만 고정

---

## TC-A-RED-01 — 정상 입력 Happy Path

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_parseAndConvert_meterColonTwoFive_returnsConversionLines` |
| **대상 (GREEN 시)** | `LineParser` → `InputValidator` → `ConvertAllUseCase`(Mock) → `PlainOutputRenderer` |
| **레거시 참고** | `UnitConverter.java:8–27` (분기·출력만, 검증 없음) |

**Given**

- 입력 문자열: `"meter:2.5"`
- Background: registry에 `meter`, `feet`, `yard` 등록
- Mock UseCase: `convertAll("meter", 2.5)` → feet 행 포함 결과 반환

**When**

- Boundary가 한 줄 입력을 파싱·검증 후 변환 결과를 PLAIN 줄 목록으로 렌더

**Then**

- 예외 없음
- 최소 1줄에 `"2.5 meter = "` 접두 + `feet` 대상 포함
- (Domain 연동 IT) feet 환산 raw: `2.5 × 3.28084 = 8.202100` (ε Domain에서 검증)

**보호하는 계약**

| ID | 계약 |
|----|------|
| F-01 | `단위:값` 변환 요청 처리 |
| F-02 | 등록된 모든 단위로 출력 |
| AC-01 | `meter:2.5` → 3줄·오류 0 |
| 표현 | `{sourceAmount} {sourceUnit} = {targetAmount} {targetUnit}` |

**RED 실패 상태 (현재)**

- `LineParser.parseConversionLine` → `UnsupportedOperationException` (`LineParser.java:9`)
- **FAIL 유형:** ERROR (파싱 전 단계 중단)
- **DEF:** DEF-018

---

## TC-A-RED-02 — 콜론 없음 (잘못된 형식)

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_parse_missingColon_throwsIllegalArgumentException` |

**Given**

- 입력: `"meter2.5"` (`:` 없음)

**When**

- `LineParser.parseConversionLine(input)` 호출

**Then**

- `IllegalArgumentException` 발생
- 메시지에 `ERR-FMT-001` 또는 `Invalid input format` 포함
- 변환 결과 줄 **0줄**

**보호하는 계약**

| ID | 계약 |
|----|------|
| F-03 | 형식 검증 |
| GH-04 / AC-02 | `meter2.5` → ERR 1줄·변환 0 |
| NEG | 파싱 실패 시 환산 미호출 |

**RED 실패 상태 (현재)**

- 기대: `IllegalArgumentException`
- 실제: `UnsupportedOperationException` (RED 스텁)
- **FAIL 유형:** 잘못된 예외 타입
- **DEF:** DEF-012

---

## TC-A-RED-03 — 음수 값

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_validate_meterNegativeOne_throwsIllegalArgumentException` |

**Given**

- 입력: `"meter:-1.0"`
- NEG-02: 길이 ≥ 0

**When**

- 파싱 성공 후 `InputValidator.validate(parsed)` 호출

**Then**

- `IllegalArgumentException`
- 메시지: `ERR-VAL-002`, `Got -1` 포함
- `ConversionEngine.convert*` **호출 0회** (Mock verify)

**보호하는 계약**

| ID | 계약 |
|----|------|
| NEG-02 | `amount < 0` → ERR-VAL-002, 변환 0줄 |
| US-01 | 음수 AC |

**RED 실패 상태 (현재)**

- parse 단계에서 `UnsupportedOperationException` (선행 실패)
- `InputValidator` no-op이면 parse 구현 후에도 **예외 미발생** → FAIL
- **DEF:** DEF-013

---

## TC-A-RED-04 — 없는 단위

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_validate_unknownUnit_parsec_throwsIllegalArgumentException` |

**Given**

- 입력: `"parsec:1.0"`
- registry에 `parsec` 없음

**When**

- parse → `validate(parsed)`

**Then**

- `IllegalArgumentException`
- 메시지: `ERR-DOM-003`, `Unknown unit "parsec"` 포함
- 변환 0줄

**보호하는 계약**

| ID | 계약 |
|----|------|
| F-03 | 미등록 단위 거부 |
| GH-05 | `furlong:1` / `parsec:1` 동등 |

**RED 실패 상태 (현재)**

- parse: `UnsupportedOperationException`
- `contains()` 항상 `false` but validate no-op → **이중 FAIL**
- **DEF:** DEF-014

---

## TC-A-RED-05 — 원 입력 단위·값 보존 (출력 포맷)

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_renderPlain_meterTwoFive_preservesSourceOnEveryLine` |

**Given**

- 입력: `"meter:2.5"`
- Mock 변환 결과: meter/feet/yard 3행

**When**

- `PlainOutputRenderer.render(results, sourceAmount=2.5, sourceUnit=meter)`

**Then**

- 모든 줄이 `"2.5 meter = "` 로 시작 (좌변 대체 금지)
- 예: `"2.5 meter = 8.2 feet"` (PLAIN 1자리) 또는 raw `"8.202100"` (Domain assert 분리)
- **UI 계약:** 원문 `2.5`·`meter` 문자열 보존

**보호하는 계약**

| ID | 계약 |
|----|------|
| RG-03 / GH-08 | 좌변 = 사용자 원입력 |
| §6.1 PLAIN | `{sourceAmount} {sourceUnit} = ...` |

**RED 실패 상태 (현재)**

- `PlainOutputRenderer` **클래스 없음** → RED 명세만 존재, JUnit은 `@Disabled` 또는 TODO
- 파이프라인 선행(parse) 실패 시 연쇄 ERROR
- **DEF:** (신규) Renderer 미구현

---

## TC-A-RED-06 — JSON 출력 스키마

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_renderJson_meterTwoFive_matchesSchema` |

**Given**

- CLI 인자 또는 플래그: `--format=JSON`
- 입력: `"meter:2.5"`

**When**

- `JsonOutputRenderer.render(conversionResult)`

**Then**

- 유효 JSON 파싱 성공
- 필수 필드:
  - `source.unit` == `"meter"`
  - `source.amount` == `2.5`
  - `conversions[]` 길이 == registry.size
  - 각 항목 `{ "unit", "amount" }` 존재
- `source`는 원입력, `amount`는 1자리 half-up (PRD §6.2)

**보호하는 계약**

| ID | 계약 |
|----|------|
| F-07 | JSON 출력 포맷 |
| US-04 | `--format=JSON` |

**RED 실패 상태 (현재)**

- `JsonOutputRenderer` / `--format` 파싱 **미존재**
- RED: 테스트 클래스만 추가 시 **컴파일 불가** → 명세 RED, 구현 후 JUnit 활성화
- **우선순위:** P2 (Should-Have)

---

# Logic RED Tests — Test ID / Scenario / Invariant

> **Track B — Domain/Logic** · `ConversionEngine`, `UnitRegistry`, `ConfigurationLoader`  
> **Mock 없음** (Domain Track)

---

## TC-B-RED-01 — meter ↔ feet 정확도

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_convert_meterToFeet_twoPointFive_withinOneEMinusFive` |
| **대상** | `ConversionEngine.convert("meter", 2.5, "feet")` |
| **레거시 참고** | `UnitConverter.java:22` (`meterValue * 3.28084`) — 단일 경로 아님 |

**Scenario**

- 2.5 meter → feet
- BR-001: `1 meter = 3.28084 feet`
- 기대: `2.5 × 3.28084 = 8.202100`

**Invariant**

| ID | 내용 |
|----|------|
| INV-D1 | 환산은 `metersPerOne` 허브만 사용 |
| BR-001 | 비율 3.28084 |
| DT-02 | ε ≤ **1e-5** |

**RED 실패 상태 (현재)**

- **expected:** `8.202100` **actual:** `0.000000`
- 원인: `ConversionEngine.java:19` `return 0.0`
- **DEF:** DEF-001

---

## TC-B-RED-02 — meter ↔ yard 정확도

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_convert_meterToYard_oneMeter_returnsOnePointZeroNineThreeSixOne` |

**Scenario**

- 1.0 meter → yard
- BR-002: `1 meter = 1.09361 yard`

**Invariant**

| ID | 내용 |
|----|------|
| BR-002 | yard 비율 1.09361 |
| DT-03 | ε ≤ 1e-5 |

**RED 실패 상태 (현재)**

- **expected:** `1.093610` **actual:** `0.000000`
- **DEF:** DEF-002

---

## TC-B-RED-03 — convertAll 전 단위

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_convertAll_meterOne_returnsThreeResults` |

**Scenario**

- `convertAll("meter", 1.0)`
- 시드 3단위: meter, feet, yard

**Invariant**

| ID | 내용 |
|----|------|
| INV-D2 | 결과 행 수 = `registry.size()` |
| F-02 | 모든 등록 단위 출력 |
| UC-D01 | ConvertAll UseCase |

**RED 실패 상태 (현재)**

- **expected size:** `3` **actual:** `0` (empty list)
- `UnitRegistry.withDefaults()` 빈 map → GREEN 시 시드 필요
- **DEF:** DEF-006, DEF-023

---

## TC-B-RED-04 — registerUnit 후 변환

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_registerUnit_cubit_thenConvertToMeter` |

**Scenario**

- `registerUnit("cubit", 0.4572)` — `1 cubit = 0.4572 meter`
- `convert("cubit", 2.0, "meter")` → `0.914400`

**Invariant**

| ID | 내용 |
|----|------|
| INV-D3 | 등록 직후 동일 세션 변환 허용 |
| F-05 / US-06 | 동적 등록 |
| OCP | Engine 시그니처 불변 |

**RED 실패 상태 (현재)**

- `register()` no-op → `contains("cubit")` false
- `convert()` → `0.0` (또는 DOM-003 미발생)
- **expected:** `0.914400` **actual:** `0.000000`
- **DEF:** DEF-025, DEF-027, DEF-031

---

## TC-B-RED-05 — loadConfig 유효 JSON/YAML

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_loadConfig_validJson_appliesFeetRatio` |

**Scenario**

- path: `src/test/resources/units-valid.json` (또는 `.yaml`)
- 로드 후 `getMetersPerOne("feet")` ≈ `0.3048`
- `convert("meter", 1.0, "feet")` ≈ `3.28084`

**Invariant**

| ID | 내용 |
|----|------|
| F-06 | 설정 외부화 |
| §5.2 | baseUnit = meter |
| DATA-01 | valid fixture PASS |

**RED 실패 상태 (현재)**

- `ConfigurationLoader.load()` → `IOException: RED: implement`
- **DEF:** DEF-019, DEF-020

---

## TC-B-RED-06 — loadConfig 무효/없음 → 기본값

| 항목 | 내용 |
|------|------|
| **테스트 이름** | `test_loadConfig_missingFile_keepsDefaultRatios` |

**Scenario**

- path: 존재하지 않는 파일
- `loadWithFallback(path)`
- registry size = 3, BR-001/002 비율 유지

**Invariant**

| ID | 내용 |
|----|------|
| INV-D4 | 설정 실패 시 시드 fallback |
| ERR-DATA-007 | (load 시) 1줄 오류 — Boundary 책임 |
| WARN | stderr fallback 1줄 |

**RED 실패 상태 (현재)**

- fallback 후 **size 0** (빈 `withDefaults`)
- feet 비율 조회 시 DOM-003 또는 convert 0
- **DEF:** DEF-021, DEF-022, DEF-023

---

## RED 실행·실패 확인 절차 (구현 금지)

```bash
# Track B만
mvn test -Dtest=ConversionEngineNormalTest
# → 6 Failures, expected 8.2021 but was 0.0  (RED OK)

# Track A만
mvn test -Dtest=InputValidationExceptionTest
# → UnsupportedOperationException (RED OK)

# 전체
mvn test
# → BUILD FAILURE, Failures 26 + Errors 5
```

**RED 완료 기준 (US-07):**

- [ ] 위 명세 테스트가 **의도적으로 FAIL** 함을 로그·스크린샷 1건 기록
- [ ] assert 삭제·`@Disabled`로 GREEN 우회 **금지** (RG-05)
- [ ] [09_defect_list.md](./09_defect_list.md) DEF-ID와 TC-ID 매핑 유지

---

## TC-ID ↔ 기존 JUnit 매핑

| RED ID | 기존 테스트 클래스 | 메서드 |
|--------|-------------------|--------|
| TC-A-RED-01 | *(명세)* / IT 예정 | — |
| TC-A-RED-02 | `InputValidationExceptionTest` | `test_parse_missingColon_*` |
| TC-A-RED-03 | `InputValidationExceptionTest` | `test_parse_negativeAmount_*` |
| TC-A-RED-04 | `InputValidationExceptionTest` | `test_validate_unknownUnit_*` |
| TC-A-RED-05 | *(Renderer 미구현)* | — |
| TC-A-RED-06 | *(JSON Renderer 미구현)* | — |
| TC-B-RED-01 | `ConversionEngineNormalTest` | `test_meterToFeet_validInput_*` |
| TC-B-RED-02 | `ConversionEngineNormalTest` | `test_meterToYard_*` |
| TC-B-RED-03 | `ConversionEngineNormalTest` | `test_convertAll_*` |
| TC-B-RED-04 | `DynamicRegistrationTest` | `test_registerUnit_cubit_*` |
| TC-B-RED-05 | `ConfigurationLoaderTest` | `test_loadConfig_validJson_*` |
| TC-B-RED-06 | `ConfigurationLoaderTest` | `test_loadWithFallback_*` |

---

## 금지 사항 (본 문서 범위)

| 금지 | 이유 |
|------|------|
| 프로덕션 `convert()` 구현 | GREEN 단계 |
| `UnitConverter.java` if-else 리팩터 | REFACTOR 단계 |
| assert 완화·테스트 삭제 | RG-05 |
| TC-A-RED-06을 fake GREEN으로 통과 | 계약 미구현 |

---

*다음 단계: [08_red_단계_수정전략.md](./08_red_단계_수정전략.md) 에 따라 G-1→G-5 순 GREEN (별도 승인 후).*
