# 결함 목록 (Defect List) — UnitConverter RED 단계

| 항목 | 내용 |
|------|------|
| 문서명 | 09_defect_list.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 기준 실행 | `mvn test` (35 tests — Failures 26, Errors 5) |
| 상태 | **Open** (GREEN 미적용) |
| 관련 | [08_red_단계_수정전략.md](./08_red_단계_수정전략.md), [07_test_plan.md](./07_test_plan.md) |

---

## 요약

| Severity | Open | 비고 |
|----------|------|------|
| Critical | 31 | 환산·파싱·설정·등록 핵심 기능 미구현(RED 스텁) |
| Major | 0 | — |
| Minor | 0 | — |
| Info | 0 | — |

**대표 재현:** `mvn test` 또는 `mvn test -Dtest=ConversionEngineNormalTest`

---

## 결함 테이블

### A. Domain — 정상 변환 (`ConversionEngineNormalTest`)

| ID | Severity | 변환 타입 | 재현 절차 | 기대값 | 실제값 | 근본 원인 | 수정 요약 |
|----|----------|-----------|-----------|--------|--------|-----------|-----------|
| DEF-001 | Critical | meter→feet | `ConversionEngine.convert("meter", 2.5, "feet")` 또는 입력 `meter:2.5` | 8.202100 | 0.000000 | `ConversionEngine.java:19` RED 스텁 `return 0.0` — BR-001(1 m = 3.28084 ft) 미적용 | meter 허브 공식: `amount × METERS_PER(source) / METERS_PER(target)` 구현 |
| DEF-002 | Critical | meter→yard | `convert("meter", 1.0, "yard")` | 1.093610 | 0.000000 | 동일 — `return 0.0` — BR-002 미적용 | DEF-001과 동일 `convert()` 구현 |
| DEF-003 | Critical | feet→meter | `convert("feet", 1.0, "meter")` | 0.304800 | 0.000000 | 동일 — 역환산 미구현 | `1 feet = 1/3.28084 meter` 허브 경유 |
| DEF-004 | Critical | meter→feet | `convert("meter", 5.0, "feet")` | 16.404200 | 0.000000 | 동일 — `return 0.0` | DEF-001과 동일 |
| DEF-005 | Critical | feet→yard | `convert("feet", 3.28084, "yard")` | 1.093610 | 0.000000 | 동일 — BR-003 meter 허브 미경유 | feet→meter→yard 단일 `convert()` 경로 |
| DEF-006 | Critical | convertAll | `convertAll("meter", 1.0)` | 결과 3건 | 0건 (empty) | `ConversionEngine.java:24` `Collections.emptyList()` | registry 단위 순회하며 `ConversionResult` 리스트 생성 |

### B. Domain — 경계값 (`ConversionEngineBoundaryTest`)

| ID | Severity | 변환 타입 | 재현 절차 | 기대값 | 실제값 | 근본 원인 | 수정 요약 |
|----|----------|-----------|-----------|--------|--------|-----------|-----------|
| DEF-007 | Critical | meter→feet (대수) | `convert("meter", 999999999, "feet")` | ≈3.28×10⁹ (유한) | 0.000000 | DEF-001 동일 스텁 | `convert()` GREEN |
| DEF-008 | Critical | meter→feet (소수6) | `convert("meter", 1.123456, "feet")` | ≈3.685879 | 0.000000 | DEF-001 동일 | `convert()` GREEN |
| DEF-009 | Critical | meter→feet (소수) | `convert("meter", 1e-6, "feet")` | > 0 (비영) | 0.000000 | DEF-001 동일 | `convert()` GREEN |
| DEF-010 | Critical | meter→feet (대수) | `convert("meter", 1e100, "feet")` | > 0, 유한 | 0.000000 | DEF-001 동일 | `convert()` GREEN |
| DEF-011 | Critical | convertAll | `convertAll("meter", 0.0)` | size 3 | size 0 | DEF-006 동일 | `convertAll()` GREEN |

> BV-01 (`meter:0` → 0 ft): RED 스텁이 0을 반환해 **우연 통과** — 결함 아님(회귀 시 재검증).

### C. Boundary — 파싱·검증 (`InputValidationExceptionTest`)

| ID | Severity | 변환 타입 | 재현 절차 | 기대값 | 실제값 | 근본 원인 | 수정 요약 |
|----|----------|-----------|-----------|--------|--------|-----------|-----------|
| DEF-012 | Critical | 입력 파싱 | `LineParser.parse("meter2.5")` | `IllegalArgumentException` ERR-FMT-001 | `UnsupportedOperationException` | `LineParser.java:9` RED 미구현 | `unit:value` 분할·형식 검증 구현 |
| DEF-013 | Critical | 음수 검증 | parse `meter:-1.0` 후 `validate()` | `IllegalArgumentException` ERR-VAL-002 | `UnsupportedOperationException` (parse 선실패) | DEF-012 + `InputValidator` no-op | Parser + `amount < 0` 검증 |
| DEF-014 | Critical | 미등록 단위 | parse `parsec:1.0` 후 `validate()` | ERR-DOM-003 | `UnsupportedOperationException` | DEF-012 + `contains()` 항상 false | Parser + registry `contains` |
| DEF-015 | Critical | 숫자 파싱 | `parse("meter:abc")` | ERR-VAL-001 | `UnsupportedOperationException` | DEF-012 | `Double.parseDouble` 실패 매핑 |
| DEF-016 | Critical | 숫자 파싱 | `parse("meter:2.5.3")` | ERR-VAL-001 | `UnsupportedOperationException` | DEF-012 | 이중 소수 거부 |
| DEF-017 | Critical | 빈 입력 | `parse("   ")` | ERR-FMT-001 | `UnsupportedOperationException` | DEF-012 | blank 입력 거부 |
| DEF-018 | Critical | 정상 파싱 | `parse("meter:2.5")` | `ParsedInput(meter, 2.5)` | 예외 발생 | DEF-012 | Happy path 파서 구현 |

### D. Data — 설정 로드 (`ConfigurationLoaderTest`)

| ID | Severity | 변환 타입 | 재현 절차 | 기대값 | 실제값 | 근본 원인 | 수정 요약 |
|----|----------|-----------|-----------|--------|--------|-----------|-----------|
| DEF-019 | Critical | JSON 로드 | `load(units-valid.json)` | registry size 3, feet 0.3048 | IOException | `ConfigurationLoader.java:13` RED throw | Jackson JSON → `UnitRegistry` |
| DEF-020 | Critical | YAML 로드 | `load(units-valid.yaml)` | registry size 3 | IOException | DEF-019 동일 | YAML mapper + 동일 `toRegistry` |
| DEF-021 | Critical | fallback | `loadWithFallback(units-invalid.json)` | size 3 (시드 fallback) | size 0 | `withDefaults()` 빈 registry | invalid 시 `UnitRegistry.withDefaults()` 채우기 |
| DEF-022 | Critical | fallback | `loadWithFallback(missing.json)` | size 3 | size 0 | DEF-021 동일 | missing 파일 fallback |
| DEF-023 | Critical | 기본 시드 | `loadDefaults()` | size 3 | size 0 | `UnitRegistry.withDefaults()` 빈 반환 | meter/feet/yard 시드 등록 |
| DEF-024 | Critical | meter→feet | load JSON 후 `convert("meter", 2.5, "feet")` | 8.202100 | IOException / 0 | DEF-019 + DEF-001 | Config + Engine 연동 |

### E. Domain — 동적 등록 (`DynamicRegistrationTest`)

| ID | Severity | 변환 타입 | 재현 절차 | 기대값 | 실제값 | 근본 원인 | 수정 요약 |
|----|----------|-----------|-----------|--------|--------|-----------|-----------|
| DEF-025 | Critical | cubit→meter | `register("cubit", 0.4572)` 후 `convert("cubit", 2, "meter")` | 0.914400 | 0.000000 | `register()` no-op + `convert()` 스텁 | `register` 구현 + `convert()` |
| DEF-026 | Critical | cubit→feet | 등록 후 `convert("cubit", 2, "feet")` | ≈3.000000 | 0.000000 | DEF-025 동일 | DEF-025 동일 |
| DEF-027 | Critical | 미등록 | `convert("cubit", 1, "meter")` (등록 전) | `DomainException` ERR-DOM-003 | 예외 없음 (0 반환) | `convert()` 예외 없이 0 반환 | unknown unit 시 `getMetersPerOne` 예외 |
| DEF-028 | Critical | 중복 등록 | `register("cubit")` 2회 | ERR-DOM-004 | 예외 없음 | `register()` no-op | 중복 키 검사 |
| DEF-029 | Critical | 잘못된 비율 | `register("bad", 0.0)` | ERR-DOM-006 | 예외 없음 | `register()` no-op | ratio > 0 검증 |
| DEF-030 | Critical | convertAll | cubit 등록 후 `convertAll("meter", 1)` | size 4 | size 0 | DEF-006 + DEF-025 | registry + convertAll |
| DEF-031 | Critical | contains | `register("cubit")` 후 `contains("cubit")` | true | false | `UnitRegistry.contains()` 항상 false | map `containsKey` |

---

## 근본 원인 그룹 (수정 우선순위)

| 그룹 | 파일 | 영향 DEF | 1차 수정 |
|------|------|----------|----------|
| **G-1** | `ConversionEngine.java` | DEF-001–011, 024–027, 030 | `convert` / `convertAll` meter 허브 |
| **G-2** | `UnitRegistry.java` | DEF-006, 021–023, 025–031 | `withDefaults`, `register`, `contains` |
| **G-3** | `LineParser.java` | DEF-012–018 | `parseConversionLine` |
| **G-4** | `InputValidator.java` | DEF-013–014 | `validate` 음수·미등록 |
| **G-5** | `ConfigurationLoader.java` | DEF-019–024 | JSON/YAML load + fallback |

상세 diff: [08_red_단계_수정전략.md](./08_red_단계_수정전략.md) §4.

---

## 검증·종료 기준

| ☐ | 항목 |
|:-:|------|
| ☐ | G-1 적용 후 `mvn test -Dtest=ConversionEngineNormalTest` BUILD SUCCESS |
| ☐ | G-1–G-5 적용 후 `mvn test` 35/35 PASS |
| ☐ | 본 문서 결함 상태 **Closed** 로 갱신 |
| ☐ | README 결함 체크리스트 2항목 완료 |

---

## 변경 이력

| 날짜 | 변경 |
|------|------|
| 2026-05-21 | RED `mvn test` 기준 DEF-001–031 등록 (Open) |

---

*파일 별칭: `defect_list.md` → 본 문서 `09_defect_list.md` (docs 순번 규칙).*
