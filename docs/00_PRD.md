# PRD — 확장 가능한 Java 단위 변환 학습 시스템

| 항목 | 내용 |
|------|------|
| 문서명 | 00_PRD.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-20 |
| 기준 산출물 | Phase 4 Epic · User Journey · US-01–07 · Gherkin #1–8 · Level 5 체크리스트 |
| 대상 | UnitConverter_01 저장소 (레거시 CLI → BCE 구조) |
| Java | 17 이상 |

---

# 1. 프로젝트 개요

## 1.1 한 줄 목적문 (What / Who / Why)

| 요소 | 내용 |
|------|------|
| **What** | `단위:값` 형식의 길이 입력을 받아 **등록된 모든 단위**로 변환 결과를 출력하는 Java CLI 학습 시스템 |
| **Who** | Java·클린 아키텍처·TDD를 학습하는 실습 참가자(6시간 Activities) |
| **Why** | 환산 알고리즘 숙달이 아니라 **계약 정의 → 테스트 고정 → BCE 레이어 분리 → 회귀 보호** 역량을 측정 가능하게 습득하기 위해 |

## 1.2 배경 및 문제 정의 (관찰 관점)

저장소에는 **입력 파싱·단위 분기·미터 환산·다단위 출력**이 한 진입점에 결합된 레거시 CLI가 존재한다. README는 meter/feet/yard 변환, OCP/SRP, 입력 검증, 테스트, 설정 외부화·동적 등록·다중 출력 포맷을 요구한다.

관찰상 **비율(3.28084, 1.09361)과 출력 예시(8.2 feet, 2.7 yard)는 문서에 있으나**, 오류 시 동작·반올림·좌변 표현·미등록 단위·설정 실패 시 정책은 코드와 문서 간 **계약이 분리되지 않았다**. 단위 추가 시 변경 범위가 예측 불가하여 FR-004(확장 시 변경 최소화)와 구조가 불일치한다.

## 1.3 목표 (측정 가능) — Phase 4 Epic SC 기반

| ID | 목표 | 통과 측정 |
|----|------|-----------|
| **G-01** | README 비율·예시와 일치하는 환산 결과 제공 | Gherkin #1–3 IT PASS; DT-02–05 GREEN; `meter:2.5` → 8.2 ft, 2.7 yd (1자리) |
| **G-02** | 입력·오류·출력 **문자열 계약** 검증 | GH-04–07 각 ERR 1줄·변환 0줄; US-01 AC 전부 충족 |
| **G-03** | BCE 레이어 분리 및 의존성 방향 준수 | entity→boundary import 0건; boundary 환산 상수(3.28084, 1.09361) 0건 |
| **G-04** | JaCoCo 커버리지 목표 달성 | entity+control line≥95%; boundary line≥85%; data line≥80%; 전체 line≥85% |
| **G-05** | 추가 요구 각 1건 이상 IT 증거 | US-04·05·06 AC 전부 충족 |
| **G-06** | TDD·회귀 게이트 준수 | RED FAIL 확인 기록; DT-02–05·IT-OK-01 필수 PASS; @Disabled·assert 삭제 0건 |

## 1.4 비목표 (Non-Goal) — 경계

| ID | 비목표 | 경계 (하지 않음) |
|----|--------|------------------|
| **NG-01** | Web UI·REST API·다국어 UI | 콘솔 stdin/stdout만; HTTP·브라우저 UI 없음 |
| **NG-02** | 온도·무게 등 비길이 단위 | 길이 고정 비율 도메인만 |
| **NG-03** | 프로덕션 배포·외부 CI SaaS·성능 벤치마크 | 로컬 테스트·저장소 산출물이 완료 기준 |
| **NG-04** | 알고리즘·성능 최적화 경쟁 | 정확한 **계약·구조·테스트**가 평가 축 |

---

# 2. 사용자 및 이해관계자

## 2.1 타깃 사용자 (페르소나 1개)

| 항목 | 내용 |
|------|------|
| **페르소나** | Java/클린 아키텍처 **학습자** (6시간 워크숍) |
| **상황** | 레거시 단일 CLI를 BCE + JUnit 5로 리팩터 |
| **기술 수준** | Java·기본 JUnit 가능; BCE·계약 TDD는 학습 중 |
| **동기** | 계약·TC-ID·커버리지 증거로 발표·회고 |
| **성공 정의** | Gherkin 8/8 PASS · US AC 전부 충족 · G-04·G-06 달성 · 레이어·계약 설명 가능 |

### 이해관계자 (저장소 범위)

| 이해관계자 | 관심 | 기대 산출 |
|------------|------|-----------|
| 학습자 | 실습 완료·회고 | TC·JaCoCo·Gherkin 매핑표 |
| 강사/리뷰어 | 평가·정합성 | Epic SC·US AC·ERR 패턴 일치 |
| 생성형 AI (보조) | 코드 제안 | PRD·Gherkin·`.cursorrules` 준수 |

## 2.2 주요 사용 시나리오 (Phase 4 Journey 기반)

| ID | Journey 단계 | 시나리오 | 측정 가능 완료 신호 |
|----|--------------|----------|---------------------|
| **S-01** | 1–2 Awareness·Entry | README·레거시 실행 후 **계약 표**(비율·NEG·표현·ERR) 작성 | Gherkin Background·NEG-01–03 문서 존재 |
| **S-02** | 3–4 Domain·TDD | DT RED→GREEN; ConversionEngine 단일 경로 | DT-02–05 GREEN; US-03 AC 충족 |
| **S-03** | 5–7 Boundary·Data·Outcome | BT/IT·설정·포맷·회귀 게이트 | GH-01–08 8/8 PASS; G-04·G-05·G-06; 회고 md 1건 |

---

# 3. 기능 요구사항

*기준: Phase 4 US-01–07 · Gherkin Background · NEG 정책 · 표현 계약*

## 3.1 핵심 기능 목록 (우선순위)

| ID | 기능 | 우선순위 | Story |
|----|------|----------|-------|
| **F-01** | `단위:값` 변환 요청 처리 | **필수** | US-03 |
| **F-02** | 등록된 **모든** 단위로 결과 출력 | **필수** | US-03, US-04 |
| **F-03** | 입력 검증(형식·숫자·음수·미등록 단위) | **필수** | US-01 |
| **F-04** | UnitRegistry 시드·OCP 확장 | **필수** | US-02 |
| **F-08** | TDD·회귀 게이트 준수 | **필수** | US-07 |
| **F-05** | 동적 단위 등록 | **권장** | US-06 |
| **F-06** | JSON 설정 로드·실패 fallback | **권장** | US-05 |
| **F-07** | 출력 포맷 PLAIN/JSON/CSV/TABLE | **선택** | US-04 |

## 3.2 기능별 입·출력 계약 (문자열)

### F-01 · F-02 변환 (PLAIN 기본) — 필수

| 구분 | 계약 |
|------|------|
| **입력** | 1줄: `{unitName}:{amount}` · `unitName` = `[a-z][a-z0-9_]{0,31}` · `amount` = 유한 실수로 파싱 가능한 토큰 |
| **성공 출력** | 줄 수 = registry 등록 단위 수 |
| **한 줄 형식** | `{sourceAmount} {sourceUnit} = {targetAmount} {targetUnit}` |
| **source 보존** | `sourceAmount`·`sourceUnit`은 **사용자 원입력** (환산값으로 좌변 대체 금지) |
| **targetAmount** | 소수점 **1자리**, half-up (예: `2.5 meter = 8.2 feet`, `2.7 yard`) |
| **실패** | 변환 줄 **0줄** + 오류 **정확히 1줄** |

**검증 예시 (US-03 / GH-01–03):**

| 입력 | 기대 (PLAIN 일부) |
|------|-------------------|
| `meter:2.5` | `2.5 meter = 8.2 feet`, `2.7 yard`, `2.5 meter` |
| `feet:3.28084` | 좌변 `3.28084 feet` 유지; `1.0 meter` |
| `yard:1.09361` | 좌변 `1.09361 yard` 유지; `1.0 meter`, `3.3 feet` |

### F-03 입력 검증 — 필수

| 조건 | 코드 | 1줄 메시지 (요지) |
|------|------|-------------------|
| 형식 위반 (콜론 없음 등) | ERR-FMT-001 | `ERROR [ERR-FMT-001]: Invalid input format. Expected "unit:value" or "1 unit = X meter". Input="{line}"` |
| 숫자 파싱 실패 (`2.5.3`, `abc`) | ERR-VAL-001 | `ERROR [ERR-VAL-001]: Value must be a finite number. Input="{line}"` |
| 음수 (NEG-02) | ERR-VAL-002 | `ERROR [ERR-VAL-002]: Length must be non-negative. Got {amount}.` |
| 미등록 단위 | ERR-DOM-003 | `ERROR [ERR-DOM-003]: Unknown unit "{unit}".` |
| 잘못된 출력 포맷 플래그 | ERR-FMT-002 | `ERROR [ERR-FMT-002]: Invalid output format "{format}". Allowed: PLAIN, JSON, CSV, TABLE.` |

**US-01 / Gherkin #4–7:** 각 케이스 ERR 1줄, 변환 0줄.

### F-04 UnitRegistry — 필수

| 구분 | 계약 |
|------|------|
| **시드** | 시작 시 `meter`, `feet`, `yard` 존재 |
| **확장** | 새 단위 = register만; ConvertAll 시그니처 변경 없음 |
| **중복 등록** | ERR-DOM-004 1줄 |
| **잘못된 이름/비율** | ERR-DOM-005 / ERR-DOM-006 |

### F-05 동적 등록 — 권장

| 구분 | 계약 |
|------|------|
| **입력** | `1 {unitName} = {ratio} meter` · `ratio` > 0 |
| **성공** | `REGISTERED: {unitName} (1 {unitName} = {ratio} meter)` 1줄 |
| **즉시성** | 등록 직후 동일 세션에서 `{unitName}:값` 변환 허용 |
| **등록 전** | `cubit:1` (미등록) → ERR-DOM-003 |
| **예시** | `1 cubit = 0.4572 meter` → `cubit:2` → meter 환산 0.9144 (출력 0.9) |

### F-06 설정 로드 — 권장

| 구분 | 계약 |
|------|------|
| **형식** | JSON (§5.2 스키마) |
| **실패** | ERR-DATA-007 1줄 |
| **fallback** | 기본 시드 3종 + stderr `WARN: using default unit registry` 1줄 |
| **충돌** | 파일·시드 동일 name → **파일 항목 우선** |
| **fallback 후** | `meter:1` 변환 성공 (Background 비율 일치) |

### F-07 출력 포맷 — 선택

| 포맷 | 트리거 | 계약 |
|------|--------|------|
| PLAIN | 기본 | §F-01·F-02 |
| JSON | `--format=JSON` | `{"source":{"unit","amount"},"conversions":[{"unit","amount"},...]}` · source=원입력 |
| CSV | `--format=CSV` | 헤더 `source_unit,source_amount,target_unit,target_amount` · 모든 행 source 동일 |
| TABLE | `--format=TABLE` | 3열: 원입력(≥12자), targetUnit(≥12), targetAmount(≥12, 1자리) |

### F-08 TDD·회귀 — 필수

| 구분 | 계약 |
|------|------|
| **순서** | RED(테스트 FAIL 확인) → GREEN(TC-ID 1–3개) → REFACTOR(전체 GREEN 후) |
| **게이트** | DT-02–05·IT-OK-01 실패 시 작업 중단 |
| **금지** | assert 완화·삭제·@Disabled로 GREEN 달성 |

## 3.3 제약 사항 (Gherkin Background와 일치)

### Background Given (고정 전제)

| 항목 | 규칙 |
|------|------|
| **meter ↔ feet** | 1 meter = 3.28084 feet |
| **meter ↔ yard** | 1 meter = 1.09361 yard |
| **feet ↔ yard** | **meter 경유만** (직접 상수 금지) |
| **시드 단위** | `meter`, `feet`, `yard` registry 등록 |
| **반올림** | 출력 1자리 half-up |
| **표현 계약** | `{sourceAmount} {sourceUnit} = {targetAmount} {targetUnit}` (source = 원입력) |

### 입력 형식

- 변환: `{unitName}:{amount}`
- 등록: `1 {unitName} = {ratio} meter`

### 음수 정책 (NEG — Gherkin #7 선행)

| ID | 규칙 |
|----|------|
| **NEG-01** | 길이 값은 **유한 수이며 ≥ 0** |
| **NEG-02** | `amount < 0` → ERR-VAL-002 1줄, 변환 0줄, 환산 미호출 |
| **NEG-03** | `amount = 0` → 유효; 모든 target `0`, 좌변 `0 {unit}` 보존 |

### 미지원·파싱

- registry 미포함 단위 → ERR-DOM-003
- `meter:2.5.3`, `meter:abc` → ERR-VAL-001

---

# 4. 비기능 요구사항

## 4.1 기술 스택

| 항목 | 값 |
|------|-----|
| 언어 | Java 17 이상 |
| 빌드 | Maven 또는 Gradle |
| 테스트 | JUnit 5 (Jupiter), AAA |
| 포맷 | Google Java Format |

## 4.2 아키텍처 원칙

| 원칙 | 요구 |
|------|------|
| **SRP** | 파싱·검증·환산·출력·설정 로드 한 클래스 결합 금지 |
| **OCP** | 새 단위 = register; 새 포맷 = OutputRenderer 추가 |
| **BCE** | boundary / control / entity / data 분리 |
| **의존성** | boundary → control → entity; boundary → data → entity; **역방향 금지** |
| **Dual-Track** | Domain TC Mock 없음; Boundary TC UseCase Mock |

## 4.3 테스트 커버리지 목표

| 레이어 | Line | Branch |
|--------|------|--------|
| entity + control | ≥ 95% | ≥ 90% |
| boundary | ≥ 85% | ≥ 80% |
| data | ≥ 80% | ≥ 75% |
| **전체** | ≥ 85% | — |

## 4.4 확장성 원칙

| 규칙 | 검증 |
|------|------|
| 새 단위 시 ConvertAll 시그니처 불변 | cubit 등록 IT 1건 |
| 비율 변경은 Constants 또는 설정 파일만 | boundary 리터럴 0건 |
| 새 출력 = Renderer 추가만 | PLAIN TC 회귀 PASS |

---

# 5. 데이터 요구사항

## 5.1 단위 비율 상수 (meter 허브)

| 단위 | metersPerOneUnit | README 등가 |
|------|------------------|-------------|
| meter | 1.0 | 기준 |
| feet | 1 / 3.28084 | 1 m = 3.28084 ft |
| yard | 1 / 1.09361 | 1 m = 1.09361 yd |

**환산 (테스트 가능):**

- `amountInMeters = sourceAmount × metersPerOneUnit(sourceUnit)`
- `targetAmount = amountInMeters / metersPerOneUnit(targetUnit)` → 1자리 half-up

## 5.2 설정 외부화 (JSON)

```json
{
  "baseUnit": "meter",
  "units": [
    { "name": "feet", "metersPerOneUnit": 0.3048 },
    { "name": "yard", "metersPerOneUnit": 0.9144 }
  ]
}
```

| 규칙 | 내용 |
|------|------|
| baseUnit | `"meter"`만 허용, 아니면 ERR-DATA-007 |
| metersPerOneUnit | 0 < value ≤ 1_000_000 |
| 테스트 fixture | `src/test/resources/units-valid.json`, `units-invalid.json` |

## 5.3 동적 단위 등록 계약

| 항목 | 값 |
|------|-----|
| 문법 | `1 {unitName} = {ratio} meter` |
| 의미 | 1 `{unitName}` = `{ratio}` meter |
| 예시 | `1 cubit = 0.4572 meter` |

---

# 6. 출력 요구사항

## 6.1 콘솔 기본 (PLAIN)

| 필드 | 규칙 |
|------|------|
| 줄 수 | registry 크기와 동일 |
| 형식 | `{sourceAmount} {sourceUnit} = {targetAmount} {targetUnit}` |
| source | 원입력 보존 |
| targetAmount | 1자리 half-up |

## 6.2 JSON (확장)

| 필드 | 타입 | 규칙 |
|------|------|------|
| source.unit | string | 원입력 단위 |
| source.amount | number | 원입력 값 |
| conversions[].unit | string | 대상 단위 |
| conversions[].amount | number | 1자리 반올림 |
| conversions.length | — | registry 크기 |

## 6.3 CSV (확장)

| 행 | 내용 |
|----|------|
| 헤더 | `source_unit,source_amount,target_unit,target_amount` |
| 데이터 | target마다 1행, source_* 동일 |
| 인코딩 | UTF-8 |

## 6.4 TABLE (확장)

| 열 | 최소 너비 | 내용 |
|----|-----------|------|
| 1 | 12 | `{sourceAmount} {sourceUnit}` |
| 2 | 12 | targetUnit |
| 3 | 12 | targetAmount (1자리) |

---

# 7. 성공 지표

## 7.1 인수 기준 (Phase 4 Story AC 정합)

| ☐ | ID | 인수 기준 | 검증 | Story / Gherkin |
|---|-----|-----------|------|-----------------|
| ☐ | AC-01 | `meter:2.5` → 3줄, 8.2 ft·2.7 yd·2.5 m, 오류 0 | IT / GH-01 | US-03 |
| ☐ | AC-02 | `meter2.5`, `furlong:1`, `meter:-1`, `meter:2.5.3` 각 ERR 1줄·변환 0 | BT/IT | US-01, GH-04–07 |
| ☐ | AC-03 | `feet:10` 모든 줄 `10 feet =` 접두 | IT / GH-08 | US-04 |
| ☐ | AC-04 | cubit 등록 후 `cubit:2` meter≈0.9; 등록 전 DOM-003 | IT | US-06 |
| ☐ | AC-05 | invalid JSON → DATA-007; fallback 후 `meter:1` OK | DATA/IT | US-05 |
| ☐ | AC-06 | JaCoCo G-04 수치 충족 | 리포트 | G-04 |
| ☐ | AC-07 | Gherkin #1–8 → IT 8/8 PASS | IT | GH-01–08 |
| ☐ | AC-08 | RED FAIL 기록·REFACTOR 전 전체 GREEN | 로그 | US-07, G-06 |

## 7.2 회귀 보호 규칙

| ID | 규칙 | 위반 시 |
|----|------|---------|
| **RG-01** | Background 비율 변경 시 DT-02–05·GH-01–03 동시 갱신 | merge 차단 |
| **RG-02** | ERR-* 패턴 변경 시 BT golden 동시 갱신 | merge 차단 |
| **RG-03** | 표현 계약 변경 시 GH-08·US-04 AC 동시 갱신 | merge 차단 |
| **RG-04** | NEG-02 변경 시 GH-07·US-01 동시 갱신 | merge 차단 |
| **RG-05** | assert 삭제·@Disabled 금지 | 인수 거부 |
| **RG-06** | REFACTOR는 전체 TC GREEN 후만 | 작업 중단 |

---

# 8. 용어 정의 (Glossary)

| 용어 | 정의 |
|------|------|
| **단위 (Unit)** | registry에 등록된 길이 이름 (`meter`, `feet`, `yard`, …) |
| **metersPerOneUnit** | 1 해당 단위 = X meter; 환산 기준 |
| **미터 허브** | 모든 환산이 meter 경유로 수행되는 규칙 |
| **UnitRegistry** | 단위명 ↔ metersPerOneUnit 매핑 |
| **표현 계약** | 출력 좌변 = 사용자 원입력 |
| **변환 줄** | PLAIN 성공 시 1줄 출력 |
| **ERR 코드** | ERR-FMT/VAL/DOM/DATA-* 1줄 오류 계약 |
| **TC-ID** | DT / BT / DATA / IT 테스트 추적 ID |
| **BCE** | boundary · control · entity · data |
| **Dual-Track TDD** | Domain RED→GREEN 후 Boundary/Data 계약 테스트 |
| **Fallback** | 설정 로드 실패 시 기본 시드 3단위 사용 |

---

# 부록 A — Phase 4 Gherkin 시나리오 매핑

| # | 시나리오 | PRD |
|---|----------|-----|
| GH-01 | meter:2.5 happy path | F-01, AC-01 |
| GH-02 | feet:3.28084 | F-01, §3.2 예시 |
| GH-03 | yard:1.09361 | F-01, §3.2 예시 |
| GH-04 | meter2.5 format | F-03, ERR-FMT-001 |
| GH-05 | furlong:1 unknown | F-03, ERR-DOM-003 |
| GH-06 | meter:2.5.3 parse | F-03, ERR-VAL-001 |
| GH-07 | meter:-1 negative | NEG-02, ERR-VAL-002 |
| GH-08 | feet:10 source preserve | 표현 계약, AC-03 |

---

# 부록 B — Phase 4 추적 매트릭스

| PRD | Phase 4 |
|-----|---------|
| §1.3 G-01–06 | Epic SC |
| §2.2 S-01–03 | Journey 7단계 |
| §3 | US-01–07 |
| §3.3 NEG | NEG-01–03, GH-07 |
| §7 | Level 5 체크리스트 |

---

# 부록 C — Phase 6 (README) 이관 권장 항목

README 갱신 시 아래를 PRD에서 반드시 이관할 것:

- NEG-01–03 및 ERR-VAL-002
- 표현 계약 (좌변 = 원입력)
- ERR-* 코드 표 (1줄 규칙)
- 반올림 1자리 half-up
- feet↔yard meter 경유 + Engine 단일 경로
- JaCoCo 목표·TC-ID (DT/BT/IT)
- JSON 스키마·ERR-DATA-007·fallback
- 동적 등록 문법·REGISTERED ACK
- BCE 패키지·의존성 방향
- 비목표 NG-01–04
- 회귀 RG-01–06

---

*본 PRD는 구현 코드·클래스 설계·빌드 스크립트를 포함하지 않는다.*
