# 테스트 계획서 — UnitConverter (Java)

| 항목 | 내용 |
|------|------|
| 문서명 | 07_test_plan.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 역할 | 시니어 QA 리드 관점 |
| 기준 문서 | [README.md](../README.md), [00_PRD.md](./00_PRD.md), [02_요구사항.md](./02_요구사항.md), [03_To-Do.md](./03_To-Do.md) |
| 기술 스택 | Java 17+, Maven 3.8+ / Gradle 8+, JUnit 5 (Jupiter) |
| 앵커 예제 | **meter → feet 변환** (`meter:2.5` → `2.5 meter = 8.2 feet`) |

---

## 1. 목적 및 범위

### 1.1 목적

본 계획서는 샘플 예제 **meter → feet 변환**을 중심으로, Dual-Track TDD(BCE) 구조에서 **JUnit 5 단위·통합 테스트**의 범위, 우선순위, 경계값·예외 시나리오, JaCoCo 커버리지 측정 전략을 정의한다.

### 1.2 범위 (In Scope)

| 범위 | 내용 |
|------|------|
| **핵심** | `단위:값` 변환, meter 허브 환산, PLAIN 출력, 입력 검증 |
| **앵커** | `meter:2.5` → feet 8.2 (half-up 1자리), Domain raw 8.2021 (ε≤1e-4) |
| **레이어** | entity, control, boundary, data, integration(E2E) |
| **도구** | JUnit 5, JaCoCo, (선택) ArchUnit |

### 1.3 범위 외 (Out of Scope)

- Web UI / REST API
- 비길이 단위
- 성능·부하 벤치마크 (별도 NFR)
- Cucumber 러너 연동 (선택 Nice-to-Have)

---

## 2. 앵커 예제 (테스트 기준 시나리오)

| 항목 | 값 |
|------|-----|
| **기능** | meter → feet 변환 (전 단위 출력의 일부) |
| **요구 추적** | 기본 요구 1·4 (`docs/02_요구사항.md`), BR-001, PRD F-01/F-02, G-01, AC-01, GH-01, US-03 |
| **입력** | `meter:2.5` |
| **기대 (PLAIN)** | `2.5 meter = 8.2 feet` (및 meter 2.5, yard 2.7 — 총 3줄) |
| **기대 (Domain raw)** | `2.5 × 3.28084 = 8.2021` (ε ≤ 1e-4) |
| **비즈니스 규칙** | 1 meter = 3.28084 feet; feet↔yard는 **meter 경유만** (BR-003) |

---

## 3. JUnit 5 단위 테스트 — 범위 및 우선순위

### 3.1 테스트 레이어 구조 (Dual-Track)

| 트랙 | 패키지 | Mock 정책 | TC 접두 |
|------|--------|-----------|---------|
| **Domain** | `entity`, `control` | Mock 없음 | DT-* |
| **Boundary** | `boundary` | UseCase Mock | BT-* |
| **Data** | `data` | fixture 파일 | DATA-* |
| **Integration** | `integration` | 실제 wiring | IT-* |

### 3.2 우선순위 정의

| 우선순위 | 의미 | 실행 시점 |
|----------|------|-----------|
| **P0** | 릴리스 차단·회귀 게이트 | 매 커밋 / PR |
| **P1** | Must-Have 완료에 필수 | RED→GREEN 각 단계 |
| **P2** | Should-Have·품질 강화 | GREEN 안정 후 |
| **P3** | Nice-to-Have | v2.0 |

### 3.3 단위 테스트 범위 매트릭스

| ID | 레이어 | 대상 컴포넌트 | 검증 내용 | 우선순위 |
|----|--------|---------------|-----------|----------|
| **DT-01** | Domain | `UnitRegistry` | 시드 3단위(meter/feet/yard) 등록 | P0 |
| **DT-02** | Domain | `ConversionEngine` | meter→feet, BR-001 비율 (앵커) | P0 |
| **DT-03** | Domain | `ConversionEngine` | meter→yard, BR-002 | P0 |
| **DT-04** | Domain | `ConversionEngine` | feet→meter 역환산 | P1 |
| **DT-05** | Domain | `ConversionEngine` | feet→yard meter 경유 (직접 상수 금지) | P0 |
| **DT-06** | Domain | `ConvertAllUnitsUseCase` | 출력 행 수 = registry.size | P0 |
| **DT-07–12** | Domain | Registry/Engine edge | 0값, 미등록 단위 Domain 예외 | P1 |
| **BT-01** | Boundary | `LineParser` | `meter:2.5` 정상 파싱 | P0 |
| **BT-02–05** | Boundary | `Validator` | 형식·숫자·음수·미등록 | P0 |
| **BT-06–08** | Boundary | `DomainErrorMapper` | ERR-* golden 문자열 1줄 | P0 |
| **BT-09–10** | Boundary | `OutputRenderer` | PLAIN 형식·좌변 보존·1자리 | P0 |
| **DATA-01–06** | Data | `ConfigurationLoader` | JSON valid/invalid, fallback | P2 |
| **IT-OK-01** | Integration | E2E CLI | `meter:2.5` 3줄·8.2 ft | P0 |
| **IT-FAIL-01–05** | Integration | E2E | ERR 1줄·변환 0줄 | P0 |

### 3.4 앵커 예제 기반 실행 순서 (TDD)

```text
1. DT-02 RED  → meter→feet FAIL 확인 → 최소 Engine GREEN
2. DT-06 RED  → ConvertAll 행 수 FAIL → UseCase GREEN
3. BT-01 RED  → 파싱 FAIL → Parser GREEN
4. BT-09 RED  → PLAIN 8.2 feet FAIL → Renderer GREEN
5. IT-OK-01   → E2E (Domain·Boundary GREEN 후)
```

### 3.5 JUnit 5 작성 규칙

| 규칙 | 내용 |
|------|------|
| 구조 | **AAA** (Arrange · Act · Assert) |
| 표시명 | `@DisplayName`에 TC-ID·입력·기대 요약 |
| 단언 | Domain: `assertEquals(expected, actual, 1e-4)` / Boundary: golden `assertEquals` 전체 문자열 |
| 금지 | `@Disabled`, assert 삭제·완화로 GREEN (PRD RG-05) |
| 파라미터화 | 경계값 표는 `@ParameterizedTest` + `@CsvSource` 권장 |

---

## 4. 경계값 케이스 목록

앵커 `meter:2.5`를 기준으로, **동일 파이프라인(파싱→검증→환산→출력)**에 대한 경계·오류 케이스이다.

| # | 케이스 ID | 입력 | 기대 동작 | 검증 레이어 | 우선순위 |
|---|-----------|------|-----------|-------------|----------|
| 1 | **BV-01 영값** | `meter:0` | 변환 성공; `0 meter = 0.0 feet` (및 yard/meter 0); 오류 0줄 | DT-07, IT | P0 |
| 2 | **BV-02 매우 큰 수** | `meter:1.0E308` 또는 `meter:999999999` | 유한 수 파싱 시 환산 수행 또는 `Infinity`/`ERR` 정책 문서화; **오버플로·Infinity 출력 금지** 검증 | DT, BT | P1 |
| 3 | **BV-03 음수** | `meter:-1` | ERR-VAL-002 1줄; **변환 0줄**; Engine 미호출 | BT-07, IT-FAIL | P0 |
| 4 | **BV-04 소수 파싱 실패** | `meter:abc` | ERR-VAL-001 1줄; 변환 0줄 | BT-04, IT | P0 |
| 5 | **BV-05 형식 오류** | `meter2.5` (콜론 없음) | ERR-FMT-001 1줄; 변환 0줄 | BT-02, IT-FAIL | P0 |
| 6 | **BV-06 없는 단위** | `parsec:1.0` | ERR-DOM-003 1줄; 변환 0줄 | BT-05, IT-FAIL | P0 |

### 4.1 BV-01 상세 (영값 — 앵커 연계)

| 항목 | 값 |
|------|-----|
| 입력 | `meter:0` |
| Domain | `convert(meter,0,feet) == 0.0` |
| PLAIN | `0 meter = 0.0 feet` |
| 정책 | NEG-03: 0은 유효, 음수만 거부 |

### 4.2 BV-02 상세 (매우 큰 수 — 오버플로 위험)

| 시나리오 | 입력 예 | 기대 |
|----------|---------|------|
| 상한 근접 | `meter:1.0E100` | `double` 유한 범위 내이면 환산 또는 명시적 실패 |
| 실무 상한 | `meter:999999999` | 결과가 `Double.POSITIVE_INFINITY`가 **아님** |
| 실패 정책 (권장) | 파싱은 성공하나 결과 비유한 | ERR-VAL-001 또는 Domain 예외로 **1줄 오류** (구현 시 PRD에 추가 기록) |

> **QA 메모:** PRD NEG-01은 “유한 수”만 허용. `1.0E308`은 JVM에서 유한일 수 있으나 곱셈 시 overflow 가능 — **RED 단계에서 FAIL 시나리오를 먼저 고정**한다.

### 4.3 BV-03–06 오류 메시지 Golden (1줄)

| 코드 | 입력 | 메시지 패턴 (요지) |
|------|------|-------------------|
| ERR-VAL-002 | `meter:-1` | `ERROR [ERR-VAL-002]: Length must be non-negative. Got -1.` |
| ERR-VAL-001 | `meter:abc` | `ERROR [ERR-VAL-001]: Value must be a finite number.` |
| ERR-FMT-001 | `meter2.5` | `ERROR [ERR-FMT-001]: Invalid input format.` |
| ERR-DOM-003 | `parsec:1.0` | `ERROR [ERR-DOM-003]: Unknown unit "parsec".` |

**공통 실패 규칙:** 변환 결과 줄 **0줄**, 오류 줄 **정확히 1줄**.

---

## 5. 예외·특이 케이스 목록

| # | ID | 시나리오 | 입력/조건 | 기대 | 레이어 |
|---|-----|----------|-----------|------|--------|
| 1 | **EX-01** | 이중 소수점 | `meter:2.5.3` | ERR-VAL-001, 변환 0줄 | BT, IT |
| 2 | **EX-02** | 빈 입력 | `` 또는 공백만 | ERR-FMT-001 또는 VAL-001 (구현 계약 고정) | BT |
| 3 | **EX-03** | 좌변 보존 | `meter:2.5` | 모든 줄 `2.5 meter =` 접두 (GH-08) | BT-09, IT |
| 4 | **EX-04** | 반올림 half-up | `meter:2.5` | feet **8.2** (raw 8.2021 ≠ 8.202100 출력) | DT-02, BT-09 |
| 5 | **EX-05** | feet 직접→yard 금지 | feet→yard 경로 | meter 허브 1회 이상 (ArchUnit/코드 리뷰) | DT-05, 정적 |
| 6 | **EX-06** | 미등록 후 등록 | `parsec:1` → 등록 → `parsec:1` | 1차 DOM-003; 2차 성공 (US-06, P2) | IT |
| 7 | **EX-07** | 설정 실패 fallback | invalid `units.json` | ERR-DATA-007 + WARN + `meter:1` OK | DATA, IT |
| 8 | **EX-08** | 동시 실패 금지 | `meter:-1` | ERR 1줄만 (복수 ERR 출력 금지) | BT, IT |

---

## 6. 커버리지 목표

PRD §4.3 및 README §테스트 실행 기준.

| 레이어 (JaCoCo package) | Line | Branch | 게이트 |
|-------------------------|------|--------|--------|
| **entity + control (Domain)** | **≥ 95%** | **≥ 90%** | P0 — 미달 시 PR 거부 |
| **boundary** | **≥ 85%** | **≥ 80%** | P0 |
| **data** | ≥ 80% | ≥ 75% | P1 |
| **전체 (project)** | **≥ 85%** | — | P0 |

### 6.1 앵커 예제와 커버리지 매핑

| 컴포넌트 | 커버를 올리는 TC |
|----------|------------------|
| `ConversionEngine` | DT-02, DT-03, DT-05, BV-01, BV-02 |
| `ConvertAllUnitsUseCase` | DT-06, IT-OK-01 |
| `LineParser` / `Validator` | BT-01–05, BV-03–06 |
| `DomainErrorMapper` | BT-06–08, BV-03–06 |
| `PlainOutputRenderer` | BT-09, EX-03, EX-04 |

### 6.2 회귀 게이트 (커버리지 외 필수 PASS)

- DT-02 – DT-05
- IT-OK-01 (`meter:2.5`)
- Gherkin #1 – #8 대응 IT (AC-07)

---

## 7. JaCoCo 측정 전략

### 7.1 Maven 설정 (권장)

`pom.xml`에 `jacoco-maven-plugin` 설정 후:

```bash
# 단위·통합 테스트 + 커버리지 수집
mvn clean verify

# 리포트만 재생성 (이미 실행된 경우)
mvn jacoco:report
```

### 7.2 리포트 확인 경로

| 산출물 | 경로 |
|--------|------|
| HTML 리포트 (프로젝트 루트 기준) | `target/site/jacoco/index.html` |
| XML (CI 연동) | `target/site/jacoco/jacoco.xml` |
| 실행 데이터 | `target/jacoco.exec` |

브라우저에서 `target/site/jacoco/index.html` → 패키지별 **Line / Branch** 확인 → `com.unitconverter.entity`, `control`, `boundary` 필터.

### 7.3 Gradle (대안)

```bash
./gradlew test jacocoTestReport
# 리포트: build/reports/jacoco/test/html/index.html
```

### 7.4 패키지별 커버리지 확인 절차 (QA 체크리스트)

| ☐ | 단계 |
|:-:|------|
| ☐ | `mvn clean verify` exit 0 |
| ☐ | `target/site/jacoco/index.html` 열기 |
| ☐ | entity+control 합산 Line ≥ 95%, Branch ≥ 90% |
| ☐ | boundary Line ≥ 85%, Branch ≥ 80% |
| ☐ | 앵커 미커버: `ConversionEngine.convertToFeet` 분기 0% 여부 확인 |
| ☐ | 리포트 스크린샷 또는 수치를 회고·PR에 첨부 (AC-06) |

### 7.5 pom.xml 최소 설정 가이드 (참고)

```xml
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.12</version>
  <executions>
    <execution>
      <goals><goal>prepare-agent</goal></goals>
    </execution>
    <execution>
      <id>report</id>
      <phase>verify</phase>
      <goals><goal>report</goal></goals>
    </execution>
  </executions>
</plugin>
```

> BCE 패키지가 준비되면 `includes`/`excludes`로 `App` wiring·generated 코드 제외 검토.

---

## 8. 테스트 데이터·Fixture

| 파일 | 용도 |
|------|------|
| `src/test/resources/units-valid.json` | DATA-01 정상 로드 |
| `src/test/resources/units-invalid.json` | DATA-02 ERR-DATA-007 |
| Golden ERR 문자열 | BT-06–08 상수 클래스 또는 `.txt` fixture |

---

## 9. 일정·단계 매핑 (Git 브랜치)

| Git 브랜치 | 테스트 활동 |
|------------|-------------|
| **red** | DT-02, BT-01–05 RED 작성, BV-01–06 FAIL 확인 |
| **green** | 앵커·경계값 GREEN, IT-OK-01 |
| **refactoring** | 중복 제거, JaCoCo 재측정, 회귀 전체 PASS |
| **A-01** | PR merge, 커버리지 게이트 최종 확인 |

---

## 10. 인수 기준 요약 (앵커 + 경계)

| ☐ | ID | 인수 조건 |
|---|-----|-----------|
| ☐ | TP-01 | `meter:2.5` → `2.5 meter = 8.2 feet` (IT-OK-01) |
| ☐ | TP-02 | BV-01 `meter:0` → 0.0 feet |
| ☐ | TP-03 | BV-03–06 각 ERR 1줄·변환 0줄 |
| ☐ | TP-04 | Domain Line ≥ 95%, Boundary Line ≥ 85% |
| ☐ | TP-05 | `mvn verify` + `target/site/jacoco/index.html` 증빙 |

---

## 11. 관련 문서

| 문서 | 설명 |
|------|------|
| [00_PRD.md](./00_PRD.md) | 계약·AC·커버리지·회귀 규칙 |
| [03_To-Do.md](./03_To-Do.md) | 구현·검증 작업 항목 |
| [05_traceability_matrix.md](./05_traceability_matrix.md) | US × TC 추적 |
| [06_전략_RED.md](./06_전략_RED.md) | RED 단계 Git·TDD 전략 |
| [07_test_plan.md](./07_test_plan.md) | 본 문서 — 테스트 계획 |

---

*본 문서는 구현 코드 없이 테스트 계획만 정의한다. 계약 변경 시 PRD → 07_test_plan.md → TC 순으로 동기화한다.*
