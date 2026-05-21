# README vs PRD 검토 보고서

| 항목 | 내용 |
|------|------|
| 검토일 | 2026-05-20 |
| 대상 | [README.md](../README.md) vs [00_PRD.md](./00_PRD.md) (Phase 5) |
| 목적 | 정합성·범위·계약·커버리지 비교 (수정 없음) |

---

## 1) PRD에 있으나 README에 빠진 항목

- **§1.3 측정 목표 G-01–G-06** ID·통과 조건(예: G-05 US-04–06 AC, G-06 RED FAIL 기록)
- **§1.4 비목표 NG-01–NG-04** ID 매핑
- **§2.1 페르소나·이해관계자**(강사/리뷰어·AI) 및 성공 정의 문장
- **§2.2 시나리오 S-01–S-03**(Awareness → Outcome) Journey 단계표
- **§3.1 기능 ID·우선순위 표**(F-01–F-08, 필수/권장/선택 — README는 서술만, F-08 미명시)
- **§3.2 `unitName` 정규식** `[a-z][a-z0-9_]{0,31}`
- **§3.2 검증 예시** `feet:3.28084`·`yard:1.09361` 기대 출력(1.0 m, 3.3 ft 등)
- **ERR-DOM-005 / ERR-DOM-006**(잘못된 등록 이름·비율) 코드·계약
- **ERR-VAL-001 / ERR-FMT-002 / ERR-DATA-007** 전체 1줄 golden 문구(README는 코드명만)
- **F-05 등록 `ratio > 0`** 제약
- **§7.1 인수 기준 AC-01–AC-08** 체크리스트·Story/Gherkin 매핑
- **§7.2 RG 위반 시 조치**(merge 차단·인수 거부·작업 중단)
- **§4.1 Google Java Format** 포맷 규칙
- **§4.2 control → boundary 금지** — README 표에 있으나, **entity I/O 금지(FBD-007)** 등 `.cursorrules` 금지 패턴은 PRD 본문에만 암시
- **§4.4 확장성 검증 표**(ConvertAll 시그니처 불변·Renderer 추가만)
- **§6.2 JSON** `conversions.length` = registry 크기 규칙
- **§6.3 CSV UTF-8** 인코딩
- **부록 A** Gherkin #1–8 ↔ PRD 기능 매핑 전체표
- **부록 B** Phase 4 추적 매트릭스(Epic/Journey/US/Level5)
- **§8 Glossary** 용어 정의(TC-ID, Dual-Track, Fallback 등)
- **TC-ID 카탈로그**(DT/BT/DATA/IT·IT-FAIL-* — README는 DT-02–05·IT-OK-01만 명시)
- **US-01–US-07** User Story ID
- **AC-08 / G-06** RED 단계 FAIL 로그·증거 요구

---

## 2) README에 있으나 PRD에 없는 항목 (범위 초과 위험)

- **`unit-converter.jpg`** 이미지·경로 (PRD 비기능·산출물 범위 밖)
- **레거시 `javac` / `java UnitConverter`** 실행 절차(상세 Quick Start)
- **목표 mainClass** `com.unitconverter.App`, `mvn exec:java` / `gradlew run` (구현·빌드 스크립트 수준)
- **Mermaid 구체 클래스명**(ConsoleBoundary, LineParser, RegisterUnitUseCase 등) — PRD는 “클래스 설계 미포함”
- **MIT License** 및 학습용 fork 문구
- **기여: 커밋 메시지 컨벤션** (`feat(scope): DT-xx`)
- **기여: “테스트 없는 PR 거부”** 정책(PRD §7.2보다 강한 운영 규칙)
- **워크숍 6시간 Activities 표**(0.5h–1h 분할) — PRD Journey(S-01–03)와 구조·세분화 상이
- **`config/units.json`** 런타임 경로(PRD는 §5.2 fixture·classpath 중심)
- **YAML** 목차·“선택 파서” 언급 — PRD F-06·§5.2는 **JSON만** 명시(v2 확장으로 읽히면 범위 확장)
- **`docs/02_요구사항.md`** 링크(Phase 6 이관 대상 외 레거시 문서)
- **`.cursorrules` / `UnitConverterRequirements.txt`** 개발 규칙 링크(PRD는 AI 이해관계자만 언급)
- **관련 문서 표** 구성(PRD 부록 C 권장 목록과 항목 집합 다름)
- **예시 입출력 `meter:5.0`** — PRD 표준 예시는 `meter:2.5`(AC-01); 계약 충돌은 아니나 **검증 기준 예시 불일치** 위험

---

## 3) 입출력 계약 불일치

- **실질적 문구·비율·NEG·PLAIN/JSON/CSV/TABLE 형식·REGISTERED ACK·fallback WARN** — PRD §3–§6와 README **일치**(현재 문서 기준 **명백한 상충 없음**)
- **부분 불일치(완전성·우선순위 해석):**
  - PRD **F-07 = 선택**인데 README는 JSON/CSV/TABLE을 **동등한 사용자 문서 섹션**으로 전개 → v1.0 필수 범위로 오해 가능(문구 자체는 “선택” 1줄 있음)
  - PRD **GH-02/03** 검증 예시·수치가 README Quick Start·예시에 **미반영** → 구현·IT가 README만 보면 **feet/yard 경계 케이스 누락** 가능(계약 모순은 아님)
  - README **ERR-VAL-001·FMT-002·DATA-007** golden 미기재 vs PRD §3.2(VAL-001/FMT-002는 문구 있음) → **테스트 스냅샷 기준 문서 불일치** 위험
  - README **NEG-03** “target 0”만 서술, PRD “좌변 `0 {unit}` 보존” — **미세 표현 계약** README에 약함(상충은 아님)

---

## 4) 테스트 커버리지 목표 불일치

- **JaCoCo Line/Branch 수치**(entity+control ≥95/≥90, boundary ≥85/≥80, data ≥80/≥75, 전체 line ≥85) — **README §테스트 실행 = PRD §4.3 = `.cursorrules`**, **수치 불일치 없음**
- **PRD G-04 문구**는 line만 열거, **branch 목표는 §4.3에만 존재** — README는 branch 포함으로 **PRD 본문 G-04와 완전 동형은 아니나 README↔§4.3는 일치**
- **회귀 게이트** DT-02–05·IT-OK-01·GH 8/8 — README·PRD §7.2·G-06 **일치**

---

## 요약

| 구분 | 판정 |
|------|------|
| 핵심 계약(비율·NEG·PLAIN·시드 3단위) | **정합** |
| JaCoCo 수치 | **정합** |
| README 완전성 | PRD 대비 **AC·G·US·TC-ID·ERR golden·Gherkin 매핑** 다수 누락 |
| 범위 해석 | README **구현·운영·YAML·레거시 실행** 항목은 PRD 범위 밖 또는 v2 후보 |

동기화 권장 순서: **PRD → README → Gherkin/TC** ([README.md](../README.md) §PRD와의 연결)
