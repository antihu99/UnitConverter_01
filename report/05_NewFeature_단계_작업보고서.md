# UnitConverter — New Feature 단계 작업 보고서

| 항목 | 내용 |
|------|------|
| 문서명 | 05_NewFeature_단계_작업보고서.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 프로젝트 | UnitConverter_01 (Java 17, Maven, JUnit 5) |
| Git 통합 | `feature` ← `A-01` (`a756dc0`, PR #6 REFACTORING merge 후) |
| TDD 단계 | **New Feature** (추가 요구사항·브랜치·문서 아카이브) |
| 선행 보고 | [04_REFACTORING_단계_작업보고서.md](./04_REFACTORING_단계_작업보고서.md) |
| 근거 대화 | [prompting/06_NewFeature_대화_전체.md](../prompting/06_NewFeature_대화_전체.md) |

---

## 1. Executive Summary

본 보고서는 **REFACTORING 완료 후** New Feature 단계에서 수행한 Git 정리·브랜치 생성·문서 아카이브·PR 준비를 정리한다.

| 항목 | 상태 |
|------|------|
| REFACTORING → `A-01` | ✅ PR #6 MERGED (`a756dc0`) |
| `main` 퀴즈 원본 업로드 | ✅ `원본_Cursor AI_퀴즈 - 문제.docx` (`51ad552`) |
| 로컬 ↔ 원격 동기화 | ✅ `A-01`·`main` 일치 |
| `feature` 브랜치 | ✅ `A-01`에서 생성, `origin/feature` 추적 |
| prompting·report 아카이브 | ✅ 본 커밋 |
| PR `feature` → `A-01` | ✅ **New Feature 단계 PR** |

**핵심 메시지:** 추가 요구사항(Activities §4) 구현 전, 통합 브랜치 `A-01` 기준으로 `feature` 작업 브랜치와 프롬프트·보고 아카이브를 확정했다.

---

## 2. 브랜치 전략 (New Feature 시점)

```text
main (퀴즈 원본 docx)
 └── A-01 (통합) ← refactoring PR#6
      └── feature ← New Feature 작업 (현재)
```

| 브랜치 | HEAD | 비고 |
|--------|------|------|
| `A-01` | `a756dc0` | REFACTORING 반영 통합 |
| `feature` | `a756dc0` + docs | PR 대상 |
| `main` | `51ad552` | `원본_` 퀴즈만 추가 |
| `refactoring` | — | 머지 후 원격·로컬 삭제됨 |

---

## 3. 작업 범위

### 3.1 Git·동기화 (No.107–112)

| 작업 | 결과 |
|------|------|
| `A-01` 로컬 = `origin/A-01` | ✅ `reset --hard` |
| `main` / `A-01` 동기화 | ✅ ahead/behind 0 |
| stale `refactoring` 로컬 삭제 | ✅ 원격 prune 반영 |
| Golden Master workflow 푸시 | ⏳ PAT `workflow` scope 필요 (미원격) |

### 3.2 `feature` 브랜치 (No.113)

```powershell
git checkout A-01
git pull origin A-01
git checkout -b feature
git push -u origin feature
```

### 3.3 문서 아카이브 (No.114)

| 경로 | 설명 |
|------|------|
| `prompting/06_NewFeature_대화_전체.md` | Agent 대화 |
| `prompting/My_All_prompt.md` | No.107–114 |
| `prompting/git_prompt.md` | §13 New Feature |
| `report/05_NewFeature_단계_작업보고서.md` | 본 문서 |

---

## 4. 코드·테스트 기준선 (A-01 / feature 공통)

| 항목 | 값 |
|------|-----|
| `mvn test` | 35 tests, 0 failures |
| Golden Master | 4 tests PASS |
| BCE | `ConvertLengthUseCase`, `UnitRegistry`, `ConversionEngine` |
| 상세 검증 | [docs/13_REFACTORING_전체_검증_결과.md](../docs/13_REFACTORING_전체_검증_결과.md) |

---

## 5. 다음 단계 (추가 요구사항 구현)

| 순서 | 작업 | 비고 |
|------|------|------|
| 1 | PR 리뷰·`feature` → `A-01` merge | 본 PR |
| 2 | Activities §4 추가 요구 3건 + TC | `feature`에서 TDD |
| 3 | `mvn test` + Golden Master 회귀 | 계약 불변 |
| 4 | `feature` 브랜치 삭제 | merge 후 |
| 5 | 최종 `A-01` → `main` 릴리스 PR | 시나리오 최종 단계 |

---

## 6. 참고 링크

| 문서 | 링크 |
|------|------|
| 작업 시나리오 | [../작업시나리오/전체 작업 시나리오.md](../작업시나리오/전체 작업 시나리오.md) |
| REFACTORING 보고 | [04_REFACTORING_단계_작업보고서.md](./04_REFACTORING_단계_작업보고서.md) |
| Git 명령 | [../prompting/git_prompt.md](../prompting/git_prompt.md) §13 |

---

*본 보고서는 New Feature 세션(2026-05-21) 브랜치·아카이브 기준이다.*
