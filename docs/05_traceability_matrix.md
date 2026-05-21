# 추적 매트릭스 — Phase 4 User Stories × Phase 6 To-Do

| 항목 | 내용 |
|------|------|
| 작성일 | 2026-05-20 |
| Story 출처 | Phase 4 Level 3 User Stories (Epic/Journey/Gherkin 패키지) |
| To-Do 출처 | [03_To-Do.md](./03_To-Do.md) (Phase 6) |
| PRD 출처 | [00_PRD.md](./00_PRD.md) (Phase 5) |

**To-Do 약어:** 필수=M-01~M-15 · 권장=S-01~S-09 · 선택=N-01~N-06 · 회귀=R-01~R-09 · 완료=D-01~D-08

---

## 추적 매트릭스

| Story ID | Story 제목 | 연관 To-Do | PRD 항목 | 상태 | 완료 기준 |
|----------|------------|------------|----------|------|-----------|
| **US-01** | 입력 검증 | M-07, M-08, M-11, M-12, M-13, R-01, R-03, R-04 | F-03, NEG-01~03, AC-02, G-02, GH-04~07 | 대기 | **학습자:** `meter2.5`→FMT-001, `furlong:1`→DOM-003, `meter:-1`→VAL-002, `meter:2.5.3`/`meter:abc`→VAL-001 각 1줄·변환 0줄 BT/IT PASS; **강사:** US-01 AC 4항 ☑ |
| **US-02** | 레지스트리·OCP | M-02, M-03, M-05, S-01, S-02, M-15 | F-04, §5.1, AC-04(등록 연동), G-03 | 대기 | **학습자:** DT-01 size=3; cubit 등록 후 registry+1·ConvertAll 시그니처 불변·변환 성공; 중복→DOM-004; **리뷰어:** main/boundary if-else 확장 0 |
| **US-03** | 환산 정확도 | M-04, M-05, M-06, M-10, M-12, M-13, R-01, R-02 | F-01, F-02, BR-001~003, AC-01, G-01, GH-01~03 | 대기 | **학습자:** DT-02~05 GREEN(ε≤1e-4); IT `meter:2.5`→8.2 ft·2.7 yd·2.5 m; GH-02/03 IT PASS; **강사:** US-03 AC 4항 ☑ |
| **US-04** | 출력 포맷 | M-06, S-07, M-12, M-13, R-03 | F-02, F-07, §6.1~6.4, AC-03, GH-08 | 대기 | **학습자:** PLAIN `feet:10` 좌변 보존; `--format=JSON` IT 파싱 성공; **선택:** N-01 CSV·N-02 TABLE; **강사:** ERR-FMT-002 1줄 IT(권장 To-Do 보강 전 GH/AC로 검증) |
| **US-05** | 설정 로드 실패 | S-03, S-04, S-05, S-06, S-08, R-01 | F-06, §5.2, AC-05, G-05, AR-001 | 대기 | **학습자:** DATA-01 INV-D2; invalid→DATA-007 1줄; stderr WARN+fallback; `meter:1` 성공; IT-FAIL-04 PASS; **강사:** US-05 AC 4항 ☑ |
| **US-06** | 동적 단위 등록 | S-01, S-02, S-06, S-08, M-12 | F-05, §5.3, AC-04, AR-002 | 대기 | **학습자:** `1 cubit = 0.4572 meter`→REGISTERED ACK; `cubit:2`→meter≈0.9; 등록 전 `cubit:1`→DOM-003; IT-OK-02 PASS; **리뷰어:** 등록=boundary·환산=entity만(레이어 체크) |
| **US-07** | TDD·회귀 | M-10, M-11, S-09, M-13, M-14, R-01~R-07, R-09 | F-08, AC-06~08, G-04, G-06, §7.2 RG-01~06 | 대기 | **학습자:** RED FAIL 로그 1건; REFACTOR 전후 전체 GREEN; DT-02~05·IT-OK-01 필수 PASS; @Disabled 0; JaCoCo §4.3; **강사:** AC-08·G-06 ☑ |

---

## Story 없음 · PRD/인프라만 연결 (매트릭스 보조)

| 구분 | 연관 To-Do | PRD 항목 | 상태 | 완료 기준 |
|------|------------|----------|------|-----------|
| 인프라·구조 | M-01, M-02, M-09 | §4.1, §4.2, G-03 | 대기 | **학습자:** `mvn test` exit 0; **리뷰어:** BCE 트리·레거시 main 환산 0줄 |
| 정적 아키텍처 | M-15, R-09 | G-03, FBD-006, RG-05 | 대기 | **리뷰어:** entity→boundary 0; boundary 비율 리터럴 0 |
| 문서·Spec (M0) | D-01~D-08 | 부록 B, Phase 4 산출물 | **완료** | **학습자:** `A-01-0_SPEC` PRD·README·Epic/Gherkin 존재 |
| v2.0 후보 | N-01~N-06 | F-07, AR-001, NG-03 | 보류 | PRD 선택·비목표 범위; v1.0 차단 아님 |
| 기술 부채 | TD-01~TD-06 (To-Do §Tech Debt) | §4.2, §1.2 | 대기 | M-01·M-09 등 필수 항목과 동시 해소 |

---

## To-Do ↔ Story 역매핑 (요약)

| To-Do ID | 작업 요약 | Story | PRD |
|----------|-----------|-------|-----|
| M-01 | Maven/Gradle·JaCoCo 스캐폴드 | — | §4.1, G-04 |
| M-02 | BCE 패키지·App wiring | US-02 | §4.2, F-04, G-03 |
| M-03 | UnitRegistry 시드 3종 | US-02 | F-04, §5.1 |
| M-04 | ConversionEngine 미터 허브 | US-03 | F-01, G-01 |
| M-05 | ConvertAllUnitsUseCase | US-02, US-03 | F-01, F-02 |
| M-06 | PLAIN·표현·half-up | US-03, US-04 | F-02, AC-03, GH-08 |
| M-07 | 입력 파싱·검증 | US-01 | F-03, NEG |
| M-08 | DomainErrorMapper golden | US-01 | F-03, RG-02, AC-02 |
| M-09 | 레거시→App 위임 | — | §4.2 SRP |
| M-10 | Domain TC RED→GREEN | US-07 | F-08, AC-08 |
| M-11 | Boundary TC Mock | US-01, US-07 | F-08, G-02 |
| M-12 | IT E2E OK/FAIL 최소 | US-01, US-03 | AC-01, AC-02 |
| M-13 | Gherkin 8/8 IT | US-01, US-03, US-07 | AC-07, GH-01~08 |
| M-14 | JaCoCo 목표 | US-07 | §4.3, AC-06 |
| M-15 | ArchUnit·정적 검사 | US-02 | G-03, RG-05 |
| S-01 | RegisterUnitUseCase | US-02, US-06 | F-05, AR-002 |
| S-02 | ERR-DOM-004~006 | US-02, US-06 | F-05 |
| S-03 | JSON 로드·Repository | US-05 | F-06, §5.2 |
| S-04 | invalid JSON·fallback | US-05 | F-06, AC-05 |
| S-05 | DATA TC 01~06 | US-05 | F-06, G-05 |
| S-06 | IT-OK-02·IT-FAIL-04 | US-05, US-06 | F-05, F-06 |
| S-07 | JSON OutputRenderer | US-04 | F-07 |
| S-08 | register·설정 GH IT | US-05, US-06 | 부록 A |
| S-09 | REFACTOR·GREEN 유지 | US-07 | F-08, RG-06 |
| N-01~N-06 | CSV/TABLE/YAML/CI 등 | US-04(부분) | F-07, AR-001, NG-03 |
| R-01~R-09 | 회귀 체크리스트 | US-01, US-07 | §7.2, AC-06~08 |
| D-01~D-08 | Spec·문서 완료 | Phase 4 Epic | 부록 B |

---

## 누락된 To-Do (Story는 있으나 To-Do에 명시·전담 항목 없음)

- **US-01 · ERR-VAL-001** — `meter:2.5.3`, `meter:abc` 전담 To-Do 없음(M-07 예시에 미포함; GH-06·AC-02는 M-13·M-12에만 간접 의존)
- **US-01 · 콜론 없음 `meter`** — Story AC 1항; To-Do는 `meter2.5`만 명시(FMT-001 동일 계열이나 입력 샘플 불일치)
- **US-01 · exit ≠ 0** — 프로세스 종료 코드 검증 To-Do·IT 기준 없음
- **US-02 · programmatic `register(cubit)` DT** — 시드(M-03) 외 API 등록 단위 TC 전담 항목 없음(S-01은 CLI 등록 라인 중심)
- **US-02 · ConvertAll 시그니처 불변 ArchUnit** — OCP 검증이 서술·리뷰에만 있고 To-Do ID 없음
- **US-03 · GH-02/03 전담** — `feet:3.28084`, `yard:1.09361` E2E가 M-13 묶음에만 있음(실패 시 원인 분리 어려움)
- **US-03 · `meter:1` ε 검증** — Story AC는 `meter:1`; To-Do·IT 예시는 `meter:2.5` 위주
- **US-04 · ERR-FMT-002** — 잘못된 `--format=` 전담 To-Do·BT 없음(Story AC 4항; PRD F-03/F-07)
- **US-04 · CSV/TABLE AC** — Story AC 3항; To-Do는 N-01·N-02 **선택**만( v1.0에서 US-04 완전 충족 경로 없음)
- **US-05 · stderr WARN 1줄** — fallback 정책은 S-04에 포함되나 **WARN 문구 golden** 전담 BT/IT 없음
- **US-05 · 파일·시드 이름 충돌 시 파일 우선** — PRD F-06 규칙; To-Do·Story AC 모두 미명시
- **US-06 · 레이어 분리 체크리스트** — “등록=boundary, 환산=engine만” Story AC; ArchUnit/리뷰 To-Do 없음
- **US-06 · 등록 전 `cubit:1`→DOM-003** — S-01 완료 기준에 cubit:2만 명시(등록 전 거부 IT 전담 없음)
- **US-07 · DT-02~05 실패 시 작업 중단** — Story AC 3항; R-02 회귀 gate에만 있고 **개발 중단 프로세스** To-Do 없음
- **US-07 · AC-08 RED 증거 산출물** — M-10 “로그 1건” 수준; 스크린샷·회고 md 등 제출 형식 To-Do 없음

---

## 고아 To-Do (Story·PRD User Story 맥락 없이 To-Do만 존재)

- **M-01** Maven/Gradle 스캐폴드 — PRD §4.1 인프라; US-01~07 직접 Story 없음
- **M-09** 레거시 `UnitConverter.java` 제거 — Tech Debt·§4.2 SRP; Story ID 미매핑
- **M-15** ArchUnit 정적 검사 — G-03·FBD; US-02 AC “리뷰어 확인”과만 느슨 연결
- **TD-01~TD-06** (To-Do §기술 부채) — 메타 작업; 단일 Story 소유 없음(여러 Story 해소의 선행 조건)
- **R-08** README·PRD 정합 — Phase 6 문서 동기화; 기능 Story 아님(부록 C)
- **N-03** YAML 설정 파서 — PRD F-06은 JSON만; Story 없음·범위 초과 위험
- **N-04** `saveAll()` 런타임 저장 — PRD “선택”; Story·AC 없음
- **N-05** Cucumber/Gherkin 실행기 — AC-07은 IT 8/8로 충족 가능; 별도 Story 없음
- **N-06** GitHub Actions — PRD NG-03 비목표; Story 없음
- **D-01~D-08** (Done) — Phase 4 **문서** 산출 완료; 구현 Story와 별 트랙(M0)

---

## 권장 보강 (누락·고아 해소 — 코드 아님, To-Do 문서만)

| 제안 ID | 제안 작업 | 연결 Story | PRD |
|---------|-----------|------------|-----|
| T-01 | BT/IT ERR-VAL-001 (`meter:2.5.3`, `meter:abc`) | US-01 | F-03, GH-06, AC-02 |
| T-02 | BT ERR-FMT-002 (`--format=INVALID`) | US-04 | F-03, F-07 |
| T-03 | IT GH-02·GH-03 전담 또는 IT-ID 분리 | US-03 | GH-02, GH-03, AC-01 |
| T-04 | ArchUnit ConvertAll 시그니처·레이어(등록/환산) | US-02, US-06 | F-04, F-05, G-03 |
| T-05 | IT 등록 전 `cubit:1`·WARN stderr golden | US-06, US-05 | AC-04, AC-05, F-06 |
| T-06 | AC-08 RED 증거 템플릿(로그/스크린샷 경로) | US-07 | AC-08, G-06 |

---

*구현 코드 없음. Story 완료 시 해당 행 `상태`를 **완료**로, To-Do `[x]` 및 [03_To-Do.md](./03_To-Do.md) Done 섹션으로 이관.*
