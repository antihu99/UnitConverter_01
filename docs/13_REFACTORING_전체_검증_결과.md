# REFACTORING 단계 — 전체 검증 결과

| 항목 | 내용 |
|------|------|
| 문서명 | 13_REFACTORING_전체_검증_결과.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 프로젝트 | UnitConverter_01 (Java 17, Maven, JUnit 5, JaCoCo) |
| Git 브랜치 | `refactoring` |
| Git 커밋 | `b4670ba` |
| TDD 단계 | **REFACTORING** (구조 개선·회귀 테스트·커버리지 검증) |
| 기준 PRD | [00_PRD.md](./00_PRD.md) §3.2, §4.3 |
| 리팩터 계획 | [12_리팩토링_계획서.md](./12_리팩토링_계획서.md) |

---

## 1. Executive Summary

| 검증 항목 | 결과 |
|-----------|------|
| `mvn test` (전체) | ✅ **BUILD SUCCESS** — **35** tests, 0 failures |
| `mvn test -Dgroups=golden_master` | ✅ **4** tests, 0 failures (출력 불변) |
| TC-A-01 ~ TC-A-07 | ✅ 전건 PASS |
| TC-B-01 ~ TC-B-07 | ✅ 전건 PASS |
| if-else 단위 환산 체인 제거 | ✅ `UnitRegistry` + `ConversionEngine` (Map 기반) |
| 매직 넘버 `3.28084` / `1.09361` 인라인 | ✅ `ConversionConstants`만 정의 (main 소스 0건) |
| Domain / Boundary 분리 | ✅ BCE 패키지 분리 + `ConvertLengthUseCase` (Control) |
| **Domain** 커버리지 (line) | ✅ **97%** (36/37) — 목표 ≥ 95% |
| **Boundary** 커버리지 (line) | ✅ **100%** (44/44) — 목표 ≥ 85% |

---

## 2. 테스트 실행 (`mvn test`)

### 2.1 실행 명령

```bash
cd d:\Vs_workplace\Java_project\UnitConverter_01
mvn test
mvn test -Dgroups=golden_master   # Golden Master만
```

### 2.2 결과 요약

| 항목 | 전체 (`mvn test`) | Golden Master (`-Dgroups=golden_master`) |
|------|-------------------|------------------------------------------|
| Tests run | **35** | **4** |
| Failures | **0** | **0** |
| Errors | **0** | **0** |
| Skipped | **0** | **0** |
| BUILD | **SUCCESS** | **SUCCESS** |

### 2.3 테스트 클래스별

| 클래스 | Run | Failures | 비고 |
|--------|-----|----------|------|
| `UnitConverterTest` | 14 | 0 | TC-A-01~07, TC-B-01~07 |
| `com.unitconverter.entity.DomainLogicRedTest` | 9 | 0 | Track B / Domain |
| `com.unitconverter.boundary.UiBoundaryRedTest` | 8 | 0 | Track A / Boundary |
| `GoldenMasterTest` | 4 | 0 | `@Tag("golden_master")` 회귀 |

### 2.4 Maven 로그 발췌 (2026-05-21)

```
[INFO] Tests run: 8,  Failures: 0  -- in com.unitconverter.boundary.UiBoundaryRedTest
[INFO] Tests run: 9,  Failures: 0  -- in com.unitconverter.entity.DomainLogicRedTest
[INFO] Tests run: 4,  Failures: 0  -- in GoldenMasterTest
[INFO] Tests run: 14, Failures: 0  -- in UnitConverterTest
[INFO] Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 3. TC별 통과 현황

### 3.1 Track A — UI / Boundary (TC-A-01 ~ 07)

| TC-ID | 설명 | 테스트 위치 | 결과 |
|-------|------|-------------|------|
| TC-A-01 | `meter:2.5` Happy path → 변환 결과 | `UnitConverterTest`, `UiBoundaryRedTest` | ✅ |
| TC-A-02 | `:` 없음 → `IllegalArgumentException` | `UnitConverterTest`, `UiBoundaryRedTest` | ✅ |
| TC-A-03 | `meter:-1.0` 음수 → 예외 | `UnitConverterTest`, `UiBoundaryRedTest` | ✅ |
| TC-A-04 | `parsec:1.0` 미등록 단위 → 예외 | `UnitConverterTest`, `UiBoundaryRedTest` | ✅ |
| TC-A-05 | `meter:abc` 숫자 파싱 실패 → 예외 | `UnitConverterTest`, `UiBoundaryRedTest` | ✅ |
| TC-A-06 | PLAIN 출력 좌변 원입력 보존 (`2.5 meter = 8.2 feet`) | `UnitConverterTest`, `UiBoundaryRedTest` | ✅ |
| TC-A-07 | `meter:0` 경계값(0) 유효 | `UnitConverterTest` | ✅ |

### 3.2 Track B — Domain / Logic (TC-B-01 ~ 07)

| TC-ID | 설명 | 테스트 위치 | 결과 |
|-------|------|-------------|------|
| TC-B-01 | `convert("meter", 2.5, "feet")` ≈ 8.20210 (ε 1e-5) | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |
| TC-B-02 | `convert("meter", 1.0, "yard")` ≈ 1.09361 | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |
| TC-B-03 | `convert("feet", 1.0, "meter")` ≈ 0.30480 (역변환) | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |
| TC-B-04 | `convertAll("meter", 1.0)` → 3건 | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |
| TC-B-05 | `registerUnit("cubit", 0.4572)` 후 변환 | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |
| TC-B-06 | `loadConfig` 유효 JSON → 비율 반영 | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |
| TC-B-07 | `loadConfig` 없는 경로 → 기본 비율 유지 | `UnitConverterTest`, `DomainLogicRedTest` | ✅ |

---

## 4. Golden Master 회귀 (`mvn test -Dgroups=golden_master`)

### 4.1 목적

REFACTORING 후 **PLAIN 출력 문자열**이 GREEN 기준선과 동일한지 확인 (`golden_master_expected.txt`).

### 4.2 시나리오

| GM-ID | 입력 | 기준 섹션 | 결과 |
|-------|------|-----------|------|
| GM-TC-01 | `meter:2.5` | `[meter:2.5]` | ✅ PASS |
| GM-TC-02 | `feet:1.0` | `[feet:1.0]` | ✅ PASS |
| GM-TC-03 | `yard:1.0` | `[yard:1.0]` | ✅ PASS |
| GM-TC-04 | `meter:0.0` | `[meter:0.0]` | ✅ PASS |

### 4.3 기준선 샘플 (`golden_master_expected.txt`)

```
[meter:2.5]
2.5 meter = 2.5 meter
2.5 meter = 8.2 feet
2.5 meter = 2.7 yard
```

> 반올림·PLAIN 포맷은 `RoundingPolicy` + `PlainOutputRenderer` 경유. 계약 변경 없음.

---

## 5. JaCoCo 커버리지 (`mvn jacoco:report`)

### 5.1 실행 명령

```bash
mvn test jacoco:report
# HTML: target/site/jacoco/index.html
# CSV:  target/site/jacoco/jacoco.csv
```

### 5.2 레이어별 Line 커버리지 (판정 기준)

| 레이어 | 패키지 | Line Covered | Line Missed | Line Cov. | 목표 | 판정 |
|--------|--------|--------------|-------------|-----------|------|------|
| **Domain** | `com.unitconverter.entity` | 36 | 1 | **97%** | ≥ 95% | ✅ |
| **Boundary — parser** | `com.unitconverter.boundary.parser` | 22 | 0 | **100%** | — | ✅ |
| **Boundary — output** | `com.unitconverter.boundary.output` | 22 | 0 | **100%** | — | ✅ |
| **Boundary 합산** | parser + output | 44 | 0 | **100%** | ≥ 85% | ✅ |
| Data | `com.unitconverter.data.config` | 17 | 4 | 81% | ≥ 80% (PRD) | ✅ |
| Control | `com.unitconverter.control` | 0 | 13 | 0%* | — | *유닛 미직접 호출 |
| Root App | `com.unitconverter` (`App`) | 0 | 8 | 0%* | — | *E2E 미실행 |

\* `ConvertLengthUseCase` / `App.main`은 통합 실행 테스트 없음. 동일 로직은 `UnitConverter` 파사드·`UnitConverterTest` / `UiBoundaryRedTest`로 검증됨.

### 5.3 Domain 클래스별 (line)

| 클래스 | Covered | Missed | Line Cov. |
|--------|---------|--------|-----------|
| `ConversionEngine` | 12 | 0 | 100% |
| `UnitRegistry` | 20 | 0 | 100% |
| `RoundingPolicy` | 1 | 0 | 100% |
| `ConversionResult` | 1 | 0 | 100% |
| `DomainException` | 3 | 1 | 75% |
| **합계** | **36** | **1** | **97%** |

### 5.4 Boundary 클래스별 (line)

| 클래스 | Covered | Missed | Line Cov. |
|--------|---------|--------|-----------|
| `LineParser` | 11 | 0 | 100% |
| `InputValidator` | 10 | 0 | 100% |
| `ParsedInput` | 1 | 0 | 100% |
| `PlainOutputRenderer` | 9 | 0 | 100% |
| `JsonOutputRenderer` | 13 | 0 | 100% |
| **합계** | **44** | **0** | **100%** |

### 5.5 Instruction 커버리지 (참고)

| 패키지 | Instruction Cov. |
|--------|------------------|
| `com.unitconverter.entity` | 98% |
| `com.unitconverter.boundary.parser` | 100% |
| `com.unitconverter.boundary.output` | 100% |
| `default` (`UnitConverter` 파사드) | 100% |
| 전체 (main 기준) | 86% |

---

## 6. REFACTORING 정적 검증 체크리스트

### 6.1 if-else 단위 환산 체인 제거 (`UnitRegistry` 교체)

| 검증 | 내용 | 판정 |
|------|------|------|
| 레거시 `UnitConverter` | if-else `meter`/`feet`/`yard` 환산 블록 **제거** — BCE 위임만 | ✅ |
| `UnitRegistry` | `LinkedHashMap<String, Double>` + `getMetersPerOne()` | ✅ |
| `ConversionEngine` | 미터 허브: `amountInMeters / targetMetersPerOne` (분기 없음) | ✅ |
| 단위별 if-else 체인 | `src/main/java` 프로덕션 코드 **0건** | ✅ |

> `UnitRegistry.getMetersPerOne`의 `if (!contains)`는 **예외 가드**이며, 단위별 환산 if-else 체인과 무관.

### 6.2 매직 넘버 `3.28084` / `1.09361` 인라인 금지

**검색:** `src/main/java/**/*.java`

| 파일 | 용도 | 판정 |
|------|------|------|
| `ConversionConstants.java` | `METER_TO_FEET`, `METER_TO_YARD`, `METERS_PER_*` 유도 | ✅ 단일 출처 |
| 그 외 `src/main/java` | 인라인 리터럴 **0건** | ✅ |

테스트 코드(`UnitConverterTest`, `DomainLogicRedTest`)의 `1.09361`, `3.28084`는 **assert 기대값** 전용.

### 6.3 Domain / Boundary 분리 (BCE)

| 레이어 | 패키지·클래스 | 책임 |
|--------|---------------|------|
| **Entity (Domain)** | `ConversionEngine`, `UnitRegistry`, `RoundingPolicy`, `ConversionConstants` | 변환·단위·반올림 |
| **Boundary** | `LineParser`, `InputValidator`, `PlainOutputRenderer`, `JsonOutputRenderer` | 파싱·검증·출력 |
| **Control** | `ConvertLengthUseCase` | 파싱→검증→변환→PLAIN 오케스트레이션 |
| **Data** | `ConfigurationLoader`, `UnitsConfigDto` | 외부 설정 로드 |
| **Facade** | `UnitConverter` (루트), `App.main` | 테스트/CLI 진입점 |

```
App.main → ConvertLengthUseCase → LineParser + InputValidator (boundary)
                                 → ConversionEngine + UnitRegistry (entity)
                                 → PlainOutputRenderer + RoundingPolicy
```

| 검증 | 판정 |
|------|------|
| 환산 수식이 Boundary에 없음 | ✅ |
| 파싱/출력이 Entity에 없음 | ✅ |
| `App.main`에 환산 if-else 없음 | ✅ |

---

## 7. REFACTORING 커밋 이력 (`refactoring` 브랜치, `A-01` 이후)

| 커밋 | 요약 |
|------|------|
| `07a83f0` | 순번2: `App.main` 출력 → `PlainOutputRenderer` |
| `8434a31` | 순번3: `renderPlain` / `PlainOutputRenderer` 위임 |
| `f34999a` | 순번1,6,4,5: 파사드 → BCE (`UnitRegistry`, `ConversionEngine`, …) |
| `216e13a` | 순번2: `ConvertLengthUseCase` + thin `App.main` |
| `ff813ca` | 순번7: `RoundingPolicy` 단일화 |
| `5a598f6` | Golden Master 테스트·스크립트 |
| `b4670ba` | `12_리팩토링_계획서.md` + README |

**PR:** [#6 REFACTORING 단계](https://github.com/antihu99/UnitConverter_01/pull/6) (`refactoring` → `A-01`)

---

## 8. GREEN 대비 변화 요약

| 항목 | GREEN ([11_GREEN_전체_검증_결과.md](./11_GREEN_전체_검증_결과.md)) | REFACTORING (본 문서) |
|------|---------------------------------------------------------------|------------------------|
| Tests run | 31 | **35** (+ Golden Master 4) |
| Domain line | 98% | **97%** (동일 목표 충족) |
| Boundary line | 100% | **100%** |
| 구조 | BCE GREEN | BCE + Control + 파사드 위임 |
| Golden Master | 없음 | **4건 PASS** |

---

## 9. 재현 절차 (검수자용)

```bash
# 1) 전체 테스트
mvn clean test

# 2) Golden Master만
mvn test -Dgroups=golden_master

# 3) 커버리지
mvn jacoco:report
# 브라우저: target/site/jacoco/index.html

# 4) 비율 인라인 검색 (PowerShell)
rg "3\.28084|1\.09361" --glob "*.java" src/main/java
```

**기대 결과:** tests 35/35 PASS, golden 4/4 PASS, entity line ≥ 95%, boundary line ≥ 85%, main 소스 인라인 비율 0건.

---

## 10. 미완료·후속

| 항목 | 상태 | 비고 |
|------|------|------|
| PR #6 머지 (`refactoring` → `A-01`) | ⏳ | 리뷰·머지 대기 |
| `App` / `ConvertLengthUseCase` E2E | ⏳ | JaCoCo line 0% — 기능은 파사드 테스트로 검증됨 |
| R-U2 ERR 메시지 상수화 | ⏭ 스킵 | 계약 변경 방지 ([12_리팩토링_계획서](./12_리팩토링_계획서.md)) |
| `.github/workflows/golden_master.yml` | ⏳ | workflow scope — 로컬 untracked |

---

## 11. 관련 문서

| 문서 | 용도 |
|------|------|
| [12_리팩토링_계획서.md](./12_리팩토링_계획서.md) | 리팩터 순번·냄새·완료 기준 |
| [11_GREEN_전체_검증_결과.md](./11_GREEN_전체_검증_결과.md) | GREEN 단계 기준선 |
| [07_test_plan.md](./07_test_plan.md) | TC·경계값 |
| [05_traceability_matrix.md](./05_traceability_matrix.md) | 요구사항 추적 |
| [../report/README.md](../report/README.md) | 단계별 작업 보고서 |

---

*검증 완료 — REFACTORING 단계 TC 전건 PASS, Golden Master PASS, Domain/Boundary 커버리지 목표 충족, if-else 환산 체인·매직 넘버 인라인 제거 확인.*
