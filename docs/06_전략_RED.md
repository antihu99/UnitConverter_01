# Git 브랜치 전략 — UnitConverter.java (Dual-Track TDD)

**대상:** `UnitConverter.java` → BCE 구조 v1.0  
**방법론:** Dual-Track TDD  
**기준 문서:** `작업시나리오/전체 작업 시나리오.md`, `docs/00_PRD.md`, `docs/03_To-Do.md`, `.cursorrules`

---

## 1. 브랜치 계층 (트렁크 + 단계 브랜치)

```text
main                          ← 최종 릴리스 (v1.0 완료 후만 merge)
 └── A-01                     ← 팀 통합 브랜치 (Spec·RED·GREEN·Refactor 누적)
      ├── spec                ← [완료] Phase 1–6 문서·계약 (A-01-0_SPEC → PR merge 후 삭제)
      ├── red                 ← [현재] Dual-Track RED
      ├── green               ← [다음] 최소 구현으로 테스트 통과
      ├── refactoring         ← [그다음] 구조 개선 (계약·GREEN 유지)
      └── feature/…           ← (선택) 단일 기능 스파이크, 완료 후 A-01으로 PR
```

| 브랜치 | 역할 | `UnitConverter.java` 관점 |
|--------|------|---------------------------|
| **main** | 안정 기준선 | 레거시 `UnitConverter.java` 또는 릴리스 태그만 유지 |
| **A-01** | A팀 01번 **통합선** | Spec 산출물 + 각 단계 merge 결과가 쌓임 |
| **spec** | 구현 금지, 문서·계약 고정 | 분석·PRD·BCE·Gherkin·To-Do만 |
| **red** | **실패하는 테스트**만 추가 | `src/test`에 DT/BT/DATA RED, 프로덕션은 스텁·최소 또는 없음 |
| **green** | RED를 **통과**시키는 최소 코드 | BCE 패키지 + Engine/Registry/Boundary wiring |
| **refactoring** | **동작·계약 불변** 구조 정리 | 중복 제거, 패키지 정리, 레거시 `main` 위임/제거 |

**최종:** `A-01` → `main` 릴리스 PR 1회 (To-Do Must-Have 전부 충족 후).

---

## 2. 개발 단계 순서 (Dual-Track TDD)

| 순서 | 단계 | Git 브랜치 | 내용 |
|:----:|------|------------|------|
| 1 | Spec | `spec` → `A-01` | Phase 1–6: 문제정의·BCE·PRD·README·To-Do (코드 구현 금지) |
| 2 | **Dual-Track UI + Logic TDD (RED)** | **`red`** | 실패하는 테스트 작성 (Domain + Boundary/Data 트랙) |
| 3 | **GREEN** | `green` | 테스트 통과를 위한 최소 구현 |
| 4 | **Dual-Track Refactoring** | `refactoring` | 외부 계약 불변, GREEN 유지, 구조만 개선 |
| 5 | 릴리스 | `A-01` → `main` | v1.0 인수 기준 충족 후 merge |

---

## 3. 현재 저장소 상태 (기준 시점)

| 항목 | 상태 |
|------|------|
| 통합 브랜치 | `A-01` (Spec PR #2 merge 완료) |
| 작업 브랜치 | **`red`** (`origin/red` 추적) |
| 레거시 코드 | 루트 `UnitConverter.java` (단일 `main`, if-else 환산) |
| 구현 트리 | `src/main` BCE 스캐폴드 없음 → **RED부터** 시작 |

---

## 4. Dual-Track TDD × Git 단계 매핑

개발 방법론은 **브랜치 1개 = TDD 단계 1개**이고, **트랙 2개(Domain / Boundary·UI)**는 **같은 브랜치 안**에서 순서만 나눕니다.

### 4-1. `red` — Dual-Track UI + Logic TDD (RED)

**목표:** 테스트가 **의도적으로 FAIL**인 상태를 만든다.

| 트랙 | 내용 | Git에 올리는 것 |
|------|------|-----------------|
| **Domain Track** | DT-01–12 (Registry, Engine, UseCase) | `src/test/.../entity`, `control` 테스트 |
| **Boundary Track** | BT-01–10 (입력 파싱, ERR 매핑, 출력) | Domain **Mock** 가정 boundary 테스트 |
| **Data Track** | DATA-01–06 (JSON 로드 등) | fixture·실패 시나리오 테스트 |

**규칙 (`.cursorrules` / PRD F-08):**

- RED 실행 후 **FAIL 로그 1건 이상** 기록 후 커밋
- assert 완화·`@Disabled`·선제 구현 금지
- 커밋 단위: **TC-ID 1–3개** (예: `#RED DT-02–04: …`)

**PR:** `red` → `A-01` (리뷰 10개 이상 목표) → merge 후 **`red` 브랜치 삭제**

```powershell
# red 작업 (이미 생성된 경우)
git checkout red
git pull origin red
```

---

### 4-2. `green` — GREEN 단계

**시작:** `A-01`에서 `green` 생성 (RED PR merge 이후).

```powershell
git checkout A-01
git pull origin A-01
git checkout -b green
git push -u origin green
```

**목표:** RED 테스트를 **최소 구현**으로 통과.

| 순서 | 구현 범위 |
|:----:|-----------|
| 1 | Maven/Gradle + `src/main` BCE 패키지 |
| 2 | Domain → Control (Engine 단일 환산 경로) |
| 3 | Boundary (파싱·ERR-DOM/FMT/VAL) |
| 4 | Data (JSON, fallback) |
| 5 | 통합 IT (IT-OK-01, IT-FAIL-01–03 등) — **단위 TC GREEN 후** |

**규칙:**

- GREEN 단계에서 **리팩터 금지** (이름 변경·패키지 이동은 `refactoring`으로)
- TC 1–3개씩 GREEN → 커밋 → 해당 TC-ID **전체 재실행**

**PR:** `green` → `A-01` → merge 후 `green` 삭제

---

### 4-3. `refactoring` — Dual-Track Refactoring

**시작:** `A-01` 최신 기준으로 `refactoring` 생성.

```powershell
git checkout A-01
git pull origin A-01
git checkout -b refactoring
git push -u origin refactoring
```

**목표:** **외부 계약·수치·ERR 문자열 불변**, 테스트 **전부 GREEN** 유지.

| 작업 예시 | To-Do 연관 |
|-----------|------------|
| 레거시 `UnitConverter.java` → `App` 위임 또는 제거 | Tech Debt |
| 중복 상수 → `ConversionConstants` | RG-02 |
| ArchUnit·패키지 경계 정리 | G-03 |
| JaCoCo 목표 달성 점검 | G-04 |

**게이트:** REFACTOR 전 `mvn test` 실패 0, DT-02–05 수치 동일.

**PR:** `refactoring` → `A-01` → merge 후 `refactoring` 삭제

---

## 5. 권장 작업 흐름

```text
[완료] spec ──PR──► A-01
[진행] red  ──PR──► A-01  (현재)
[예정] green ──PR──► A-01
[예정] refactoring ──PR──► A-01
[최종] A-01 ──릴리스 PR──► main
```

1. **`red`에서 작업** — Maven/JUnit 스캐폴드 + DT/BT RED 추가 (FAIL 확인)
2. push → GitHub PR `red` → `A-01`
3. 리뷰·merge → 로컬 `A-01` pull → `red` 삭제
4. `green` 생성 → 동일 패턴
5. `refactoring` 생성 → 동일 패턴
6. `A-01` → `main` 릴리스

---

## 6. PR·커밋 컨벤션

| 단계 | PR 제목 예 | 커밋 메시지 예 |
|------|------------|----------------|
| RED | `#2단계 RED: Domain DT-01–03` | `#RED DT-02: meter hub conversion fails as expected` |
| GREEN | `#2단계 GREEN: ConversionEngine minimal` | `#GREEN DT-02–05: engine passes registry tests` |
| REFACTOR | `#2단계 REFACTOR: extract App wiring` | `#REFACTOR: delegate legacy UnitConverter to App` |

시나리오의 **「PR 내 리뷰 10개 이상」**은 각 단계 PR(`red` / `green` / `refactoring`)마다 적용.

---

## 7. Dual-Track을 Git에 어떻게 쪼개지 않나

| 구분 | Git 브랜치 | 코드/테스트 |
|------|------------|-------------|
| **단계** | `red` / `green` / `refactoring` | TDD 사이클 단위 |
| **트랙** | (브랜치 분리 없음) | Domain RED → Boundary RED(Mock) → Data RED |
| **통합** | `green` 후반·`refactoring` | IT-E2E, JaCoCo, ArchUnit |

트랙별로 브랜치를 나누면 PR·merge가 과도해지므로, **단계 브랜치만** 사용한다.

---

## 8. RED 단계 체크리스트 (시작 시)

| ☐ | 항목 |
|:-:|------|
| ☐ | `red` 브랜치 체크아웃, `origin/red` 동기화 |
| ☐ | Maven/Gradle + JUnit 5 + JaCoCo 스캐폴드 |
| ☐ | BCE 패키지 트리 (`entity`, `control`, `boundary`, `data`) — 테스트만 또는 빈 스텁 |
| ☐ | DT-01 RED 작성 → **FAIL** 확인 → 커밋 |
| ☐ | BT-01 RED (Domain Mock) → **FAIL** 확인 → 커밋 |
| ☐ | RED FAIL 로그 1건 이상 보관 (US-07) |
| ☐ | PR `red` → `A-01` 생성, 리뷰 10+ |

---

## 9. 참고 링크

| 문서 | 경로 |
|------|------|
| Git 브랜치 전략 (본 문서) | `docs/06_전략_RED.md` |
| 전체 Activities·브랜치 순서 | `작업시나리오/전체 작업 시나리오.md` |
| To-Do (Must/Should) | `docs/03_To-Do.md` |
| 추적성 매트릭스 | `docs/05_traceability_matrix.md` |
| TDD·BCE 규칙 | `.cursorrules` |
| PRD | `docs/00_PRD.md` |
| 테스트 계획 | `docs/07_test_plan.md` |

---

*작성 기준: Dual-Track TDD — RED → GREEN → Refactoring. 저장소 브랜치명 `A-01`(통합), `red`(작업 중).*
