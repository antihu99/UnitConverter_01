# UnitConverter — SPEC 단계 작업 보고서

| 항목 | 내용 |
|------|------|
| 문서명 | 01_SPEC_단계_작업보고서.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 프로젝트 | UnitConverter_01 (Java, 레거시 CLI → BCE 전환 실습) |
| Git 통합 | `A-01` ← PR #2 `A-01-0_SPEC` merge (`a8b81f3`) |
| TDD 단계 | **SPEC** (Phase 1–6, 구현·RED 이전) |
| 근거 대화 | [prompting/00_SPEC_PROMPT.md](../prompting/00_SPEC_PROMPT.md) (161 Turn) |

---

## 1. Executive Summary

본 보고서는 **SPEC 단계(Phase 1–6)** 에서 수행한 요구·설계·문서·Git 정리 작업을 [00_SPEC_PROMPT.md](../prompting/00_SPEC_PROMPT.md) 대화 기록을 바탕으로 정리한다.

| 항목 | 상태 |
|------|------|
| GitHub ↔ 로컬 연결 | ✅ 클론·`origin` 설정 |
| Phase 1 문제 정의·Mom Test | ✅ (대화·요구 정의서 반영) |
| Phase 2 BCE·Dual-Track 설계 | ✅ (계약·TC-ID·추적 매트릭스) |
| Phase 3 `.cursorrules` | ✅ TDD·레이어·금지 패턴 |
| Phase 4 Epic/Journey/Story/Gherkin | ✅ 요구 서술 패키지 |
| Phase 5 PRD | ✅ `docs/00_PRD.md` |
| Phase 6 README·To-Do | ✅ README 갱신, `03_To-Do.md` 등 |
| `A-01-0_SPEC` → `A-01` merge | ✅ PR #2 |
| 프로덕션 구현·RED 테스트 | ⏳ SPEC 이후 (RED 보고서 참조) |

**핵심 메시지:** SPEC 단계는 **코드 구현 전** 계약·문서·브랜치 기반을 고정했다. 구현·실패 테스트는 [02_RED_단계_작업보고서.md](./02_RED_단계_작업보고서.md) 이후 단계이다.

---

## 2. 작업 범위 (Phase 1–6)

[작업시나리오/전체 작업 시나리오.md](../작업시나리오/전체%20작업%20시나리오.md)의 PART 2 기준.

| Phase | 역할 | SPEC 단계 산출 (요약) |
|:-----:|------|----------------------|
| **1** | 문제·가정·Mom Test | 레거시 CLI 관찰, 5-Why, Invariant 5개, Mom Test 질문 10개 |
| **2** | BCE·계약·RED 목록 | Entity/Boundary/Data 설계, DT/BT/IT·DATA TC-ID, 추적 매트릭스 초안 |
| **3** | `.cursorrules` | 8개 최상위 키, `tdd_rules`, `file_structure`, FBD 금지 패턴 |
| **4** | Epic→Gherkin | Epic SC, Journey 7단계, US-01–07, Gherkin #1–8, Level 5 체크리스트 |
| **5** | PRD 확정 | `docs/00_PRD.md` — 계약·AC·회귀·JaCoCo 목표 |
| **6** | README·To-Do | README PRD 정합, `03_To-Do.md`, `04_README_review.md`, `05_traceability_matrix.md` |

**SPEC 단계 금지 사항 (준수):** Phase 4 완료 전 AR(추가 요구) **프로덕션 구현 금지**, BCE 설계·요구 서술은 **시그니처·계약 수준**만.

---

## 3. 대화 기반 주요 작업 (Turn 요약)

출처: `00_SPEC_PROMPT.md` (저장일 2026-05-21, 161 메시지 블록).

| 구간 | Turn | 작업 내용 |
|------|:----:|-----------|
| 환경 | 1–7 | 빈 폴더 → `git clone`, `origin` 연결, `main` @ `1aab698` |
| 기록 | 8–13 | `작업시나리오/00_git-github-로컬연결.md` 저장 |
| Phase 1 | 14–31 | 문제 정의(관찰·Why·Invariant), Mom Test 10문항 |
| Phase 2 | 32–51 | BCE + Dual-Track 설계 (Entity/Boundary/Data/통합·커버리지) |
| Phase 3 | 52–51 | `.cursorrules` (아키텍처·TDD·파일 트리·AI 행동 규칙) |
| Phase 4 | 52–58 | Epic, Journey, US, Gherkin #1–8, Level 5 체크리스트 |
| Phase 5 | 59–80 | PRD 3단계 작성·Phase 4 정합 검토 → `docs/00_PRD.md` |
| Phase 6 | 81–92 | README·요구사항 이관, To-Do·리뷰·추적 매트릭스 |
| Git | 81–92, 3547+ | `A-01-SPEC` / `A-01-0_SPEC` 푸시, PR #2 merge |
| 정리 | 150–161 | `docs/` 2자리 접두어, `00_SPEC_PROMPT.md` 아카이브, `git_prompt.md` |

---

## 4. 문서 산출물

### 4.1 docs/ (SPEC 시점 핵심)

| 순번 | 파일 | 용도 |
|:----:|------|------|
| 00 | [00_PRD.md](../docs/00_PRD.md) | 공식 PRD — 목표 G-01–G-06, AC, 회귀 RG |
| 01 | [01_UnitConverterRequirements.txt](../docs/01_UnitConverterRequirements.txt) | FR/BR/QR/AR ID 정의서 |
| 02 | [02_요구사항.md](../docs/02_요구사항.md) | 초기 README 요구사항 보관 |
| 03 | [03_To-Do.md](../docs/03_To-Do.md) | Must/Should/Nice-To-Do·Tech Debt |
| 04 | [04_README_review.md](../docs/04_README_review.md) | README ↔ PRD 정합 검토 |
| 05 | [05_traceability_matrix.md](../docs/05_traceability_matrix.md) | Concept→Test→Component 추적 |

*RED 이후 추가:* `06`–`10` (전략·test_plan·결함·RED 명세) — [02_RED_단계_작업보고서.md](./02_RED_단계_작업보고서.md) 참조.

### 4.2 기타 저장소 산출

| 경로 | 내용 |
|------|------|
| `.cursorrules` | BCE·TDD·금지 패턴·`file_structure` |
| `README.md` | Quick Start, 계약, BCE, 테스트·설정 요약 |
| `작업시나리오/00_git-github-로컬연결.md` | 최초 Git 연결 기록 |
| `작업시나리오/전체 작업 시나리오.md` | 6시간 Activities, Phase·브랜치 전략 |
| `prompting/00_SPEC_PROMPT.md` | SPEC 단계 Agent 대화 전체 (161 Turn) |
| `prompting/git_prompt.md` | 대화 중 사용한 git/gh 명령 모음 |
| `prompting/My_All_prompt.md` | 단계별 프롬프트 인덱스 |

### 4.3 레거시 코드 (SPEC 시점)

| 파일 | SPEC 시점 상태 |
|------|----------------|
| `UnitConverter.java` | 단일 `main`, if-else 3분기, `Scanner` 대화형 — **분석·계약 대상만** |
| BCE `src/main/java/...` | SPEC 단계 **미생성** (RED 이후 스캐폴드) |

---

## 5. Git · 브랜치 이력

### 5.1 SPEC 관련 커밋 (`A-01` 통합 이력)

```text
fd2d106  #1단계. README 및 PRD 작성
         → README, .cursorrules, docs/PRD·요구사항·UnitConverterRequirements, 작업시나리오
936f075  #1단계- Phase 6 . To-Do 리스트 문서화
         → docs 00–05 번호 정리, 03_To-Do, 04/05, prompting 아카이브
df08aa0  #1단계. 전체 작업 시나리오에 추천 브랜치 구조 추가
a8b81f3  Merge PR #2 (A-01-0_SPEC → A-01)   ← SPEC 단계 통합 완료
```

### 5.2 브랜치 전략 (SPEC 시점 설계)

```text
main
 └── A-01 (통합)
      ├── spec  → PR → A-01 merge 후 삭제  ✅ (A-01-0_SPEC 경로로 완료)
      ├── red   → …                         (RED 보고서 참조, 이후 삭제됨)
      ├── green → …                         (현재 작업 브랜치)
      └── refactoring (예정)
```

| 브랜치 | SPEC 단계 역할 | 비고 |
|--------|----------------|------|
| `A-01-0_SPEC` / `A-01-SPEC` | 문서·규칙 푸시 | PR #2로 `A-01` merge |
| `A-01` | 통합 기준선 | SPEC+RED 병합 HEAD `c18bf16` (현재) |
| `red` | — | RED 후 PR #3 merge·삭제 완료 |

---

## 6. 설계·계약 핵심 (Phase 2·4 요약)

### 6.1 Dual-Track

| Track | 책임 | 금지 |
|-------|------|------|
| **Domain** | 환산·Registry·UseCase | I/O, Scanner, JSON 파싱 |
| **Boundary** | 파싱·검증·렌더·ERR 문구 | 환산 상수 하드코딩 |

**의존성:** Boundary → Control → Entity · Data 구현은 Boundary가 인터페이스로만 참조.

### 6.2 환산·출력 계약 (고정)

| 항목 | 값 |
|------|-----|
| 입력 | `단위:값` (예: `meter:2.5`) |
| 비율 | 1 m = **3.28084** ft, 1 m = **1.09361** yd |
| PLAIN 예시 | `meter:2.5` → 8.2 feet, 2.7 yard (1자리 half-up) |
| 오류 | ERR-FMT/VAL/DOM/DATA 패턴 1줄, 변환 출력 0줄 |

### 6.3 측정 목표 (PRD G-01–G-06)

- Gherkin 8/8 · US AC 충족
- JaCoCo: entity+control line≥95%, boundary≥85%, data≥80%, 전체≥85%
- RED FAIL 기록 후 GREEN; `@Disabled`·assert 삭제 0건

---

## 7. SPEC 단계 완료 체크

| ☐ | 항목 | 증거 |
|:-:|------|------|
| ☑ | GitHub 연결 | Turn 1–7, `00_git-github-로컬연결.md` |
| ☑ | 문제 정의·Mom Test | Turn 14–31 |
| ☑ | BCE·Dual-Track 설계 문서 | Turn 32–51 (대화 본문) |
| ☑ | `.cursorrules` | `fd2d106` 커밋 |
| ☑ | Epic/Journey/US/Gherkin | Turn 52–58 |
| ☑ | PRD | `docs/00_PRD.md` |
| ☑ | README·To-Do·추적 | `936f075`, `03`–`05` docs |
| ☑ | `A-01` merge | `a8b81f3` |
| ☐ | 프로덕션 BCE 구현 | SPEC 범위 밖 → GREEN |

---

## 8. 리스크 · 이슈 (SPEC 시점)

| ID | 이슈 | 완화 |
|----|------|------|
| S-01 | `A-01-SPEC` 원격 삭제(gone) 이력 | `A-01-0_SPEC` 스쿼시 푸시로 PR #2 정리 |
| S-02 | docs 파일명 변경(00–05) | README·PRD 상호 링크 일괄 갱신 (Turn 150) |
| S-03 | PRD vs Gherkin 세부 불일치 | Turn 59–80 정합 검토·`04_README_review` |
| S-04 | SPEC·RED 동시 진행 위험 | 브랜치 분리(`red`→merge→삭제) |

---

## 9. 다음 단계 (SPEC 이후)

| 순서 | 단계 | 보고서 |
|:----:|------|--------|
| 1 | Dual-Track RED (실패 테스트 고정) | [02_RED_단계_작업보고서.md](./02_RED_단계_작업보고서.md) |
| 2 | GREEN 최소 구현 | `green` 브랜치 · [08_red_단계_수정전략.md](../docs/08_red_단계_수정전략.md) |
| 3 | Refactoring | 예정 |
| 4 | QA·커버리지·최종 보고 | PART 5 (예정) |

---

## 10. 결론

- **SPEC 단계 목표 달성:** Phase 1–6 산출물(문제 정의, BCE 설계, `.cursorrules`, Epic/Gherkin, PRD, README/To-Do)이 `A-01`에 통합되었다.
- **근거 아카이브:** 전 과정은 `prompting/00_SPEC_PROMPT.md` 161 Turn으로 재현·감사 가능하다.
- **다음 마일스톤:** RED는 완료·merge됨; 현재는 **`green`** 브랜치에서 GREEN 구현을 진행한다.

---

## 부록 A — 관련 링크

| 리소스 | 경로 |
|--------|------|
| SPEC 대화 전체 | [../prompting/00_SPEC_PROMPT.md](../prompting/00_SPEC_PROMPT.md) |
| Git 명령 모음 | [../prompting/git_prompt.md](../prompting/git_prompt.md) |
| 작업 시나리오 | [../작업시나리오/전체 작업 시나리오.md](../작업시나리오/전체%20작업%20시나리오.md) |
| RED 단계 보고 | [02_RED_단계_작업보고서.md](./02_RED_단계_작업보고서.md) |

---

*보고서 끝.*
