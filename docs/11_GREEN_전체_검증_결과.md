# GREEN 단계 — 전체 검증 결과

| 항목 | 내용 |
|------|------|
| 문서명 | 11_GREEN_전체_검증_결과.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 프로젝트 | UnitConverter_01 (Java 17, Maven, JUnit 5) |
| Git 브랜치 | `green` |
| TDD 단계 | **GREEN** (전 TC 통과·커버리지·정적 검증) |
| 기준 PRD | [00_PRD.md](./00_PRD.md) §3.2, §4.3 |

---

## 1. Executive Summary

| 검증 항목 | 결과 |
|-----------|------|
| `mvn test` (전체) | ✅ **BUILD SUCCESS** — 31 tests, 0 failures |
| TC-A-01 ~ TC-A-07 | ✅ 전건 PASS |
| TC-B-01 ~ TC-B-07 | ✅ 전건 PASS |
| Domain Logic 커버리지 (line) | ✅ **98%** (목표 ≥ 95%) |
| Boundary Layer 커버리지 (line) | ✅ **100%** (목표 ≥ 85%) |
| 비율 상수 인라인 금지 | ✅ `ConversionConstants`만 사용 |
| `main()` 변환 로직 분리 | ✅ Domain(`ConversionEngine`) 위임 |

---

## 2. 테스트 실행 (`mvn test`)

### 2.1 실행 명령

```bash
cd d:\Vs_workplace\Java_project\UnitConverter_01
mvn clean test
```

### 2.2 결과 요약

| 항목 | 값 |
|------|-----|
| Tests run | **31** |
| Failures | **0** |
| Errors | **0** |
| Skipped | **0** |
| BUILD | **SUCCESS** |

### 2.3 테스트 클래스별

| 클래스 | Run | Failures | 비고 |
|--------|-----|----------|------|
| `UnitConverterTest` | 14 | 0 | TC-A-01~07, TC-B-01~07 |
| `com.unitconverter.entity.DomainLogicRedTest` | 9 | 0 | Track B / Domain GREEN |
| `com.unitconverter.boundary.UiBoundaryRedTest` | 8 | 0 | Track A / Boundary GREEN |

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

## 4. JaCoCo 커버리지 (`mvn jacoco:report`)

### 4.1 실행 명령

```bash
mvn clean test jacoco:report
# 리포트: target/site/jacoco/index.html
```

### 4.2 패키지별 Line 커버리지

| 레이어 | 패키지 | Line Cov. | 목표 | 판정 |
|--------|--------|-----------|------|------|
| **Domain Logic** | `com.unitconverter.entity` | **98%** (35/36) | ≥ 95% | ✅ |
| **Boundary — parser** | `com.unitconverter.boundary.parser` | **100%** (22/22) | — | ✅ |
| **Boundary — output** | `com.unitconverter.boundary.output` | **100%** (23/23) | — | ✅ |
| **Boundary 합산** | parser + output | **100%** (45/45) | ≥ 85% | ✅ |
| Data | `com.unitconverter.data.config` | 85% | ≥ 80% (PRD) | ✅ |
| Control (App) | `com.unitconverter` | 0%* | — | *E2E 미실행 |

\* `App.main()`은 통합 실행 테스트 미포함. CLI 오케스트레이션만 담당.

### 4.3 Instruction 커버리지 (참고)

| 패키지 | Instruction Cov. |
|--------|------------------|
| `com.unitconverter.entity` | 98% |
| `com.unitconverter.boundary.parser` | 100% |
| `com.unitconverter.boundary.output` | 100% |
| 전체 프로젝트 | 86% |

---

## 5. 정적 검증 체크리스트

### 5.1 비율 상수 인라인 금지

**검색:** 프로덕션 `*.java`에서 `3.28084`, `1.09361` 리터럴

| 파일 | 용도 | 판정 |
|------|------|------|
| `ConversionConstants.java` | `METER_TO_FEET`, `METER_TO_YARD` 정의 | ✅ 허용 (단일 출처) |
| `UnitConverter.java` (루트 레거시) | `App.main()` 위임만 — 비율 없음 | ✅ |
| `src/main/java/**` | 인라인 리터럴 **0건** | ✅ |

테스트 코드의 기대값(`8.20210`, `1.09361` 등)은 assert용으로만 사용.

### 5.2 `main()` 변환 로직 분리

| 파일 | `main()` 역할 | 판정 |
|------|---------------|------|
| `UnitConverter.java` (루트) | `com.unitconverter.App.main(args)` 위임만 | ✅ |
| `com.unitconverter.App` | 파싱·검증·`ConversionEngine` 호출·출력 (환산 수식 없음) | ✅ |
| `ConversionEngine` (entity) | 미터 허브 환산 — **Domain** | ✅ |

레거시 루트 `UnitConverter.java`의 if-else 환산 블록은 **제거**됨.

### 5.3 BCE GREEN 구현 요약

| 레이어 | 주요 클래스 | 상태 |
|--------|-------------|------|
| entity | `ConversionEngine`, `UnitRegistry`, `ConversionConstants` | GREEN |
| boundary.parser | `LineParser`, `InputValidator`, `ParsedInput` | GREEN |
| boundary.output | `PlainOutputRenderer`, `JsonOutputRenderer` | GREEN |
| data.config | `ConfigurationLoader`, `UnitsConfigDto` | GREEN |

---

## 6. GREEN 커밋 이력 (`green` 브랜치)

| 순번 | 커밋 메시지 (요약) |
|:----:|-------------------|
| 1 | TC-B-01 GREEN — meter→feet |
| 2 | TC-A-02 GREEN — missing colon |
| 3 | TC-B-02 GREEN — meter→yard |
| 4 | TC-A-03 GREEN — negative value |
| 5 | TC-B-03 GREEN — feet→meter reverse |
| 6 | TC-A-04 GREEN — unknown unit |
| 7 | TC-B-04~05 GREEN — convertAll, registerUnit |
| 8 | TC-A-01,06,07 GREEN — boundary happy path |
| 9 | TC-B-06~07 GREEN — loadConfig with fallback |

> BCE 전체 GREEN·TC-A-05·커버리지 보강 테스트는 로컬 검증 완료. Git 커밋은 별도 push 예정일 수 있음.

---

## 7. 재현 절차 (검수자용)

```bash
# 1) 전체 테스트
mvn clean test

# 2) 커버리지 리포트
mvn jacoco:report
# 브라우저: target/site/jacoco/index.html

# 3) 비율 인라인 검색 (PowerShell)
rg "3\.28084|1\.09361" --glob "*.java" src/main/java
```

**기대 결과:** tests 31/31 PASS, entity line ≥ 95%, boundary line ≥ 85%, main 소스 인라인 비율 0건.

---

## 8. 미완료·후속 (GREEN 이후)

| 항목 | 상태 | 비고 |
|------|------|------|
| REFACTORING 단계 | ⏳ | 구조 개선·중복 제거 |
| `App` E2E / IT 테스트 | ⏳ | `App.main` 커버리지 0% |
| `09_defect_list.md` DEF Open | ⏳ | GREEN 후 회귀·결함 Close |
| PR `green` → `A-01` | ⏳ | 리뷰 10+ |

---

## 9. 관련 문서

| 문서 | 용도 |
|------|------|
| [00_PRD.md](./00_PRD.md) | 계약·커버리지 목표 |
| [07_test_plan.md](./07_test_plan.md) | TC·경계값 |
| [08_red_단계_수정전략.md](./08_red_단계_수정전략.md) | GREEN diff 가이드 |
| [09_defect_list.md](./09_defect_list.md) | 결함 추적 |
| [10_RED_듀얼트랙_테스트_명세.md](./10_RED_듀얼트랙_테스트_명세.md) | RED 명세 |
| [../report/README.md](../report/README.md) | 단계별 작업 보고서 |

---

*검증 완료 — GREEN 단계 TC 전건 PASS, Domain/Boundary 커버리지 목표 충족.*
