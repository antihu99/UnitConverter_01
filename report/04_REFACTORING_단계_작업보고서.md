# UnitConverter — REFACTORING 단계 작업 보고서

| 항목 | 내용 |
|------|------|
| 문서명 | 04_REFACTORING_단계_작업보고서.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 프로젝트 | UnitConverter_01 (Java 17, Maven, JUnit 5, JaCoCo) |
| Git 통합 | `refactoring` ← `A-01` (`12d85b1`, PR #5 GREEN merge 후) |
| TDD 단계 | **REFACTORING** (구조 개선·Golden Master·회귀 유지) |
| 선행 보고 | [03_GREEN_단계_작업보고서.md](./03_GREEN_단계_작업보고서.md) |
| 근거 대화 | [prompting/05_REFACTORING_대화_전체.md](../prompting/05_REFACTORING_대화_전체.md) (18 Turn) |

---

## 1. Executive Summary

본 보고서는 **REFACTORING 단계**에서 수행한 분석·계획·BCE 구조 정리·Golden Master·전체 검증·PR 생성을 정리한다.

| 항목 | 상태 |
|------|------|
| GREEN merge (`A-01`) | ✅ PR #5 (`12d85b1`) |
| `refactoring` 브랜치 | ✅ `A-01`에서 생성, `origin/refactoring` 동기화 |
| 리팩터 계획서 | ✅ [docs/12_리팩토링_계획서.md](../docs/12_리팩토링_계획서.md) |
| BCE·Control 분리 | ✅ `ConvertLengthUseCase`, 파사드 위임 |
| Golden Master | ✅ 4 시나리오, `mvn test -Dgroups=golden_master` PASS |
| `mvn test` | ✅ **35 tests, 0 failures** |
| TC-A-01~07 / TC-B-01~07 | ✅ 전건 PASS |
| Domain line coverage | ✅ **97%** (목표 ≥ 95%) |
| Boundary line coverage | ✅ **100%** (목표 ≥ 85%) |
| PR `refactoring` → `A-01` | ✅ [#6 REFACTORING 단계](https://github.com/antihu99/UnitConverter_01/pull/6) (머지 대기) |

**핵심 메시지:** GREEN에서 충족한 계약을 유지한 채 중복·혼재 로직을 BCE로 위임했고, Golden Master로 PLAIN 출력 불변을 고정했다. 상세 수치는 [docs/13_REFACTORING_전체_검증_결과.md](../docs/13_REFACTORING_전체_검증_결과.md)를 참조한다.

---

## 2. 프로젝트 개요

### 2.1 목적

- GREEN 파사드·`App.main`의 **Entity/Control/Boundary 혼재** 해소
- if-else 단위 환산 → `UnitRegistry` + `ConversionEngine` (이미 부분 완료 → 파사드 중복 제거)
- Refactoring 전 **Golden Master** 회귀 안전장치 구축
- 계약·테스트 불변 하에 구조만 개선

### 2.2 브랜치 전략 (REFACTORING 시점)

```text
main → A-01 (통합) ← green PR#5
              └── refactoring ← REFACTORING (현재, PR #6)
```

| 브랜치 | 상태 |
|--------|------|
| `green` | 삭제됨 (PR #5 merge 후) |
| `A-01` | GREEN 반영 (`12d85b1`) |
| `refactoring` | 작업·푸시 완료 (`b4670ba`) |

---

## 3. 작업 범위

### 3.1 사전 분석·문서 (No.89–99)

| 작업 | 산출물 |
|------|--------|
| Golden Master 기준·테스트 설계 | `golden_master_expected.txt`, `GoldenMasterTest.java`, 스크립트 |
| README Golden Master 섹션 | `README.md` |
| 코드 스멜·ECB 분석 (코드 미수정) | 대화 산출 → 계획서 입력 |
| 리팩토링 계획서 | [docs/12_리팩토링_계획서.md](../docs/12_리팩토링_계획서.md) |

### 3.2 REFACTORING 커밋 (`refactoring` 브랜치)

| 순번 | 커밋 | 요약 |
|:----:|------|------|
| 1 | `07a83f0` | `App.main` 출력 → `PlainOutputRenderer` |
| 2 | `8434a31` | `renderPlain` / `PlainOutputRenderer` 위임 |
| 3 | `f34999a` | 파사드 → BCE (`UnitRegistry`, `ConversionEngine`, `LineParser`, `ConfigurationLoader`) |
| 4 | `216e13a` | `ConvertLengthUseCase` 추출, thin `App.main` |
| 5 | `ff813ca` | `RoundingPolicy` 반올림 정책 단일화 |
| 6 | `5a598f6` | Golden Master 테스트·생성 스크립트·`pom.xml` groups |
| 7 | `b4670ba` | `12_리팩토링_계획서.md` + README |

### 3.3 BCE 구조 (REFACTORING 후)

| 레이어 | 주요 클래스 | 역할 |
|--------|-------------|------|
| entity | `ConversionEngine`, `UnitRegistry`, `RoundingPolicy`, `ConversionConstants` | 변환·단위·반올림 |
| boundary.parser | `LineParser`, `InputValidator` | 파싱·검증 |
| boundary.output | `PlainOutputRenderer`, `JsonOutputRenderer` | PLAIN/JSON 출력 |
| control | `ConvertLengthUseCase` | 파싱→검증→변환→PLAIN 오케스트레이션 |
| data.config | `ConfigurationLoader` | 외부 설정 |
| 파사드 | `UnitConverter` (루트) | 테스트 API — BCE 위임만 |
| CLI | `App.main` | `ConvertLengthUseCase` 호출만 |

```text
App.main → ConvertLengthUseCase
         → LineParser + InputValidator (boundary)
         → ConversionEngine + UnitRegistry (entity)
         → PlainOutputRenderer + RoundingPolicy
```

### 3.4 스킵·미완료

| 항목 | 사유 |
|------|------|
| R-U2 ERR 메시지 상수화 | 계약(문자열) 변경 방지 |
| `.github/workflows/golden_master.yml` | PAT `workflow` scope 부족 — 로컬만 존재 |
| `App` / `ConvertLengthUseCase` JaCoCo | E2E 유닛 미포함 (파사드 테스트로 기능 검증) |

---

## 4. 테스트·검증

### 4.1 전체 테스트

```bash
mvn test
mvn test -Dgroups=golden_master
```

| 항목 | 전체 | Golden Master |
|------|------|---------------|
| Tests run | **35** | **4** |
| Failures | **0** | **0** |
| BUILD | SUCCESS | SUCCESS |

| 테스트 클래스 | 건수 |
|---------------|------|
| `UnitConverterTest` | 14 |
| `DomainLogicRedTest` | 9 |
| `UiBoundaryRedTest` | 8 |
| `GoldenMasterTest` | 4 |

### 4.2 커버리지 (`mvn jacoco:report`)

| 영역 | Line % | 목표 | 결과 |
|------|--------|------|------|
| `com.unitconverter.entity` | **97%** | ≥ 95% | ✅ |
| `boundary.parser` + `boundary.output` | **100%** | ≥ 85% | ✅ |

### 4.3 정적·구조 검증

| 확인 항목 | 결과 |
|-----------|------|
| if-else 단위 환산 체인 | ✅ `UnitRegistry` Map 기반 |
| `3.28084` / `1.09361` 인라인 (main) | ✅ `ConversionConstants`만 |
| Domain / Boundary 분리 | ✅ |
| Golden Master 출력 불변 | ✅ 4 시나리오 |

상세: [docs/13_REFACTORING_전체_검증_결과.md](../docs/13_REFACTORING_전체_검증_결과.md).

---

## 5. Git·PR

| 작업 | 결과 |
|------|------|
| `refactoring` 생성·푸시 | ✅ `origin/refactoring` @ `b4670ba` |
| 로컬·원격 동기화 | ✅ ahead/behind 0 |
| PR #6 | ✅ [REFACTORING 단계](https://github.com/antihu99/UnitConverter_01/pull/6) (`refactoring` → `A-01`) |

---

## 6. 산출물·문서

| 경로 | 설명 |
|------|------|
| `src/main/java/com/unitconverter/control/ConvertLengthUseCase.java` | Control 유스케이스 |
| `src/main/java/com/unitconverter/entity/RoundingPolicy.java` | 반올림 정책 |
| `src/main/java/UnitConverter.java` | 얇은 파사드 |
| `src/test/java/GoldenMasterTest.java` | Golden Master 4건 |
| `docs/12_리팩토링_계획서.md` | 리팩터 계획 |
| `docs/13_REFACTORING_전체_검증_결과.md` | 검증 결과서 |
| `prompting/05_REFACTORING_대화_전체.md` | Agent 대화 (18 Turn) |
| `prompting/My_All_prompt.md` | 사용자 프롬프트 No.89–106 |
| `prompting/git_prompt.md` | §12 REFACTORING `git` 명령 |
| `report/04_REFACTORING_단계_작업보고서.md` | 본 문서 |

---

## 7. 결함·다음 단계

### 7.1 결함 목록

[docs/09_defect_list.md](../docs/09_defect_list.md) DEF 항목은 REFACTORING 회귀 후 **Closed** 일괄 갱신 권장.

### 7.2 다음 단계

| 순서 | 작업 |
|------|------|
| 1 | PR #6 리뷰·`refactoring` → `A-01` merge |
| 2 | `refactoring` 브랜치 삭제 (시나리오 기준) |
| 3 | README Golden Master 체크박스 `[x]` 반영 |
| 4 | (선택) `App` E2E 테스트로 Control 커버리지 보강 |
| 5 | CI `golden_master.yml` — workflow scope 확보 후 푸시 |

---

## 8. 참고 링크

| 문서 | 링크 |
|------|------|
| 리팩터 계획 | [docs/12_리팩토링_계획서.md](../docs/12_리팩토링_계획서.md) |
| REFACTORING 검증 | [docs/13_REFACTORING_전체_검증_결과.md](../docs/13_REFACTORING_전체_검증_결과.md) |
| GREEN 검증 | [docs/11_GREEN_전체_검증_결과.md](../docs/11_GREEN_전체_검증_결과.md) |
| Git 명령 | [prompting/git_prompt.md](../prompting/git_prompt.md) §12 |

---

*본 보고서는 REFACTORING 세션(2026-05-21) 종료 시점 기준이다.*
