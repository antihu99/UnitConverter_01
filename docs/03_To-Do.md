# To-Do 리스트 — UnitConverter (Java)

**기준:** [00_PRD.md](./00_PRD.md) §3 기능 요구 · §7.1 인수 기준 · §7.2 회귀 보호  
**대상:** `UnitConverter.java` → BCE 구조 v1.0  
**완료 정의:** 학습자가 TC 실행·JaCoCo·IT 결과로 **측정 가능**하게 확인했을 때

---

## 🔴 필수 (Must-Have) — v1.0 릴리스 차단 항목

| ☐ | 작업 설명 | 연관 PRD | 완료 기준 (누가 · 무엇을 · 어떻게 검증) |
|:-:|-----------|----------|----------------------------------------|
| ☐ | Maven/Gradle 빌드·JUnit 5·JaCoCo 스캐폴드 생성 (`src/main`, `src/test`) | §4.1, G-04 | **학습자**가 `mvn test` / `gradle test` exit 0; `pom.xml` 또는 `build.gradle` 존재 |
| ☐ | BCE 패키지 트리 생성 (`boundary`, `control`, `entity`, `data`, `App` wiring) | §4.2, G-03, F-04 | **리뷰어**가 `.cursorrules` `file_structure`와 실제 디렉터리 일치 확인 |
| ☐ | `UnitRegistry.withDefaults()` 시드 3단위 (meter/feet/yard) | F-04, §5.1, BR-001–003 | **학습자**가 DT-01 실행 → size=3 PASS |
| ☐ | `ConversionEngine` 미터 허브 단일 환산 경로 구현 | F-01, INV-D1, G-01 | **학습자**가 DT-02–DT-05 GREEN; feet↔yard 미터 경유 ε≤1e-4 |
| ☐ | `ConvertAllUnitsUseCase` — 전 등록 단위 변환 | F-01, F-02, UC-D01 | **학습자**가 DT-06(행 수=registry.size) PASS |
| ☐ | PLAIN 출력·표현 계약(좌변=원입력)·1자리 half-up | F-02, §6.1, GH-08, AC-03 | **학습자**가 IT `feet:10` → 모든 줄 `10 feet =` 접두; `meter:2.5` → 8.2/2.7 |
| ☐ | 입력 파싱·검증 (형식/숫자/음수/미등록) | F-03, NEG-01–03, US-01 | **학습자**가 BT/IT: `meter2.5`→FMT-001, `meter:-1`→VAL-002, `furlong:1`→DOM-003, 각 변환 0줄 |
| ☐ | `DomainErrorMapper` ERR-* 1줄 패턴 1:1 | F-03, RG-02, AC-02 | **학습자**가 BT-06–08 golden 문자열 일치 assert PASS |
| ☐ | 레거시 `UnitConverter.java` → `App` 위임 또는 제거 | §4.2 SRP, Tech Debt | **리뷰어**가 루트 main에 환산 if-else 0줄 확인 |
| ☐ | Domain TC RED→GREEN (DT-01–12) | F-08, US-07, G-06 | **학습자**가 RED FAIL 로그 1건 이상 후 DT-02–05 GREEN; @Disabled 0건 |
| ☐ | Boundary TC (BT-01–10) Mock UseCase | F-08, G-02 | **학습자**가 `mvn test` boundary 패키지 BT 전부 PASS |
| ☐ | 통합 IT E2E (IT-OK-01, IT-FAIL-01–03 최소) | AC-01, AC-02, GH-01,04,05,07 | **학습자**가 `meter:2.5` E2E 3줄·오류 0; 실패 3종 ERR 1줄씩 PASS |
| ☐ | Gherkin #1–8 대응 IT 8/8 | AC-07, GH-01–08 | **학습자**가 IT 스위트 8시나리오 실패 0건 |
| ☐ | JaCoCo 커버리지 목표 | §4.3, G-04, AC-06 | **학습자**가 리포트: entity+control line≥95%, boundary≥85%, data≥80%, 전체≥85% |
| ☐ | ArchUnit/정적: entity→boundary import 0, boundary 비율 상수 0 | G-03, RG-05, FBD-006 | **리뷰어**가 검사 실행 결과 위반 0건 |

---

## 🟡 권장 (Should-Have) — 품질 향상 항목

| ☐ | 작업 설명 | 연관 PRD | 완료 기준 (누가 · 무엇을 · 어떻게 검증) |
|:-:|-----------|----------|----------------------------------------|
| ☐ | `RegisterUnitUseCase` + 등록 라인 파서 | F-05, US-06, AR-002 | **학습자**가 `1 cubit = 0.4572 meter` → ACK 1줄; `cubit:2` → meter≈0.9 (AC-04) |
| ☐ | 중복·잘못된 등록 ERR-DOM-004–006 | F-05, IT-FAIL-05 | **학습자**가 cubit 2회 등록 → 2번째 DOM-004 1줄 |
| ☐ | JSON `units-valid.json` 로드 + `UnitRatioRepository` | F-06, AR-001, §5.2 | **학습자**가 DATA-01 PASS; 로드 후 INV-D2 수치 일치 |
| ☐ | invalid JSON → ERR-DATA-007 + 시드 fallback | F-06, US-05, GH 설정 시나리오, AC-05 | **학습자**가 DATA-02/IT: invalid→007 1줄; fallback 후 `meter:1` 성공 |
| ☐ | DATA TC (DATA-01–06) | F-06, G-05 | **학습자**가 data 패키지 TC 전부 PASS |
| ☐ | IT-OK-02 (등록 후 E2E), IT-FAIL-04 | F-05, F-06 | **학습자**가 등록→변환 IT·설정 실패 IT PASS |
| ☐ | `--format=JSON` OutputRenderer | F-07, US-04 | **학습자**가 IT: JSON 파싱 성공, `source`+`conversions[]` 존재 |
| ☐ | Gherkin register·설정 시나리오 IT 추가 | 부록 A, US-05–06 | **학습자**가 GH 매핑표 미충족 0건 |
| ☐ | REFACTOR 단계: 중복 제거·전체 GREEN 유지 | F-08, RG-06, US-07 | **학습자**가 REFACTOR 전후 `mvn test` 실패 0; DT-02–05 수치 동일 |

---

## 🟢 선택 (Nice-to-Have) — v2.0 후보

| ☐ | 작업 설명 | 기대 가치 한 줄 |
|:-:|-----------|-----------------|
| ☐ | `--format=CSV` OutputRenderer | F-07 | 스프레드시트·파이프라인 연동용 기계 가독 출력 |
| ☐ | `--format=TABLE` 고정폭 출력 | F-07 | 터미널 가독성·데모용 정렬 표 |
| ☐ | YAML 설정 파서 | AR-001 | JSON 외 설정 포맷 선택지 확대 |
| ☐ | `UnitRatioRepository.saveAll()` 런타임 저장 | F-06 선택 | 동적 등록 영속화(실습 범위 밖이면 생략 가능) |
| ☐ | Cucumber/Gherkin 실행기 연동 | GH-01–08 | AC와 실행 테스트 1:1 자동화 |
| ☐ | GitHub Actions 로컬 대체 스크립트 | NG-03 | PR마다 DT-02–05·IT-OK-01 자동 gate |

---

## 🔵 기술 부채 (Tech Debt)

| ☐ | 문제 설명 | 발생 원인 | 해결 방향 |
|:-:|-----------|-----------|-----------|
| ☐ | 단일 `main`에 파싱·분기·환산·출력 결합 | 레거시 실습 시작 코드 | `App` wiring만 남기고 BCE로 이전 (필수 항목과 연동) |
| ☐ | 비율·오류·반올림이 코드/문서에 분산 | 초기 README만 존재 | PRD·README·ERR golden·`ConversionConstants` 단일 진실원 |
| ☐ | 테스트·소스 트리 없음 | Phase 1–6 문서 단계만 완료 | Maven/Gradle + `src/test` 스캐폴드 (필수 1번) |
| ☐ | boundary에 환산 상수 유입 위험 | 레거시 복사 시 | Engine만 비율 보유; ArchUnit FBD-006 |
| ☐ | Gherkin 일부(포맷·등록) IT 미구현 | Phase 4 GH 8개 중 일부 PRD만 존재 | 권장/선택 IT로 보강 |
| ☐ | `A-01-SPEC` vs `main` 브랜치 분기 | 단계별 브랜치 전략 | RED/GREEN/Refactoring 브랜치로 구현 흐름 분리 |

---

## ✅ 완료 항목 (Done)

| ☑ | 완료 내용 | 완료일 | 관련 커밋/PR 메시지 |
|:-:|-----------|--------|---------------------|
| [x] | Phase 4 요구 서술 패키지 (Epic/Journey/Story/Gherkin/체크리스트) | 2026-05-20 | (대화 산출물) |
| [x] | `docs/00_PRD.md` Phase 5 PRD 작성 | 2026-05-20 | `#1단계. README 및 PRD 작성` (A-01-0_SPEC) |
| [x] | `README.md` PRD 기반 갱신 + `docs/02_요구사항.md` 이관 | 2026-05-20 | 동일 |
| [x] | `docs/01_UnitConverterRequirements.txt` 요구 ID 정의서 | 2026-05-20 | 동일 |
| [x] | `.cursorrules` BCE·TDD·forbidden·커버리지 규칙 | 2026-05-20 | 동일 |
| [x] | `작업시나리오/` Git 연결·전체 시나리오 문서 | 2026-05-20 | 동일 |
| [x] | `prompting/00_SPEC_PROMPT.md`, `My_All_prompt.md` | 2026-05-21 | (로컬) |
| [x] | GitHub `A-01-0_SPEC` 브랜치 문서 업로드 | 2026-05-20 | `#1단계. README 및 PRD 작성` |

---

## 📋 회귀 방지 체크리스트 (PRD §7.2 기반)

**배포(v1.0 제출/발표) 전 — 학습자 + 리뷰어 공동 확인**

| ☐ | 확인 항목 | PRD | 통과 조건 |
|:-:|-----------|-----|-----------|
| ☐ | **계약 테스트** | RG-01–04, AC-01–07 | `mvn test` / `gradle test` 실패 0; GH 대응 IT 8/8 |
| ☐ | **필수 회귀 gate** | RG-01, G-06 | DT-02–05, IT-OK-01 필수 PASS |
| ☐ | **ERR golden** | RG-02 | ERR-* 패턴 변경 시 BT 스냅샷 동시 갱신됨 |
| ☐ | **표현·NEG 계약** | RG-03, RG-04 | GH-08, GH-07, US-01 AC 충족 |
| ☐ | **커버리지 목표** | §4.3, G-04 | JaCoCo: entity+control≥95%, boundary≥85%, data≥80%, 전체≥85% |
| ☐ | **테스트 품질** | RG-05, F-08 | assert 삭제·@Disabled 0건 |
| ☐ | **REFACTOR 게이트** | RG-06 | REFACTOR 전 전체 GREEN 스냅샷 존재 |
| ☐ | **README·PRD 정합** | 부록 C, Phase 6 | README에 NEG·ERR·BCE·JaCoCo·회귀(RG) 반영됨 |
| ☐ | **레이어 규칙** | G-03, FBD-006/007 | entity I/O 0; boundary 3.28084/1.09361 리터럴 0 |

---

## 🗓️ 마일스톤

| 마일스톤 | 포함 항목 (PRD) | 목표일 | 상태 | 완료 판정자 |
|----------|-----------------|--------|------|-------------|
| **M0 — Spec·문서** | PRD, README, 요구사항, .cursorrules, Epic/Gherkin | 2026-05-20 | **완료** | 학습자: `A-01-0_SPEC` push 확인 |
| **M1 — RED (Domain)** | F-04, F-01, DT-01–12 RED | +0.5일 | 대기 | 학습자: DT FAIL 로그 + 테스트 클래스 존재 |
| **M2 — GREEN Core** | F-01–03, F-08, AC-01–03, DT/BT GREEN, IT-OK-01 | +2.5일 | 대기 | 학습자: DT-02–05·IT-OK-01 PASS |
| **M3 — GREEN 확장** | F-05, F-06, AC-04–05, DATA/BT/IT | +2일 | 대기 | 학습자: US-05·06 AC ☑ |
| **M4 — v1.0 릴리스** | AC-06–08, G-04–06, GH 8/8, 회귀 체크리스트 | +0.5일 | 대기 | 강사: 인수 기준 8/8 + JaCoCo + 회고 md |
| **M5 — v2.0 후보** | F-07 CSV/TABLE, Cucumber, CI 스크립트 | TBD | 보류 | — |

---

*본 문서는 구현 코드 없이 작업 추적만 정의한다. 항목 완료 시 `[ ]` → `[x]` 및 Done 섹션으로 이관.*
