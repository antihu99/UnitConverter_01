# UnitConverter — RED 단계 작업 보고서

| 항목 | 내용 |
|------|------|
| 문서명 | 01_RED_단계_작업보고서.md |
| 버전 | 1.0 |
| 작성일 | 2026-05-21 |
| 프로젝트 | UnitConverter_01 (Java 17, Maven, JUnit 5) |
| Git 브랜치 | `red` |
| TDD 단계 | **RED** (Dual-Track UI + Logic) |
| 작성 | QA / TDD 실습 보고 |

---

## 1. Executive Summary

본 보고서는 **Dual-Track TDD RED 단계**까지의 산출물·테스트·결함·다음 조치를 정리한다.

| 항목 | 상태 |
|------|------|
| Spec 단계 (Phase 1–6) | ✅ 완료 (`A-01` merge) |
| RED 테스트 스켈레톤 | ✅ 12건 등록 (`fail("RED")`) |
| `mvn test` | ❌ **BUILD FAILURE** (의도적 RED) |
| GREEN 구현 | ⏳ 미착수 |
| 결함 문서 | ✅ DEF-001–031 Open |

**핵심 메시지:** 테스트는 계약을 고정했고, 프로덕션은 RED 스텁 상태이므로 **실패가 정상**이다. GREEN은 [08_red_단계_수정전략.md](../docs/08_red_단계_수정전략.md) 순서로 진행한다.

---

## 2. 프로젝트 개요

### 2.1 목적

- 레거시 `UnitConverter.java`(단일 `main`, if-else)를 **BCE + JUnit 5 + TDD** 구조로 전환
- 계약: `단위:값` 입력 → 등록 단위 전량 변환 → PLAIN/JSON 출력
- 비율: **1 meter = 3.28084 feet**, **1 meter = 1.09361 yard**

### 2.2 기술 스택

| 항목 | 버전/도구 |
|------|-----------|
| Java | 17+ |
| 빌드 | Maven (`pom.xml`) |
| 테스트 | JUnit 5 (Jupiter) |
| 커버리지 | JaCoCo 0.8.12 |
| 설정 | Jackson JSON/YAML |

### 2.3 브랜치 전략

```text
main → A-01 (통합) ← spec ✅ merged
              └── red ← 현재 작업 (RED)
              └── green (예정)
              └── refactoring (예정)
```

---

## 3. 현재 코드 상태

### 3.1 레거시

| 파일 | 상태 |
|------|------|
| `UnitConverter.java` | 단일 `main()`, if-else 3분기, **예외 처리 없음**, `Scanner` 대화형 |

### 3.2 BCE 구조 (RED 스텁)

| 레이어 | 패키지 | RED 상태 |
|--------|--------|----------|
| entity | `ConversionEngine`, `UnitRegistry` | `convert()` → 0.0, `convertAll()` → empty |
| boundary | `LineParser`, `InputValidator` | parse 미구현 / validate no-op |
| data | `ConfigurationLoader` | `load()` IOException |
| — | `PlainOutputRenderer`, `JsonOutputRenderer` | **미존재** |

### 3.3 디렉터리 요약

```text
src/main/java/com/unitconverter/   ← BCE 스텁
src/test/java/.../               ← RED 스켈레톤 2 클래스
UnitConverter.java                 ← 레거시 (루트)
docs/00–10                         ← 요구·계획·결함·RED 명세
report/01_*                        ← 본 보고서
```

---

## 4. Dual-Track RED 테스트

### 4.1 테스트 클래스

| Track | 클래스 | 파일 | 건수 |
|-------|--------|------|:----:|
| **A — UI/Boundary** | `UiBoundaryRedTest` | `src/test/java/.../boundary/` | 6 |
| **B — Domain/Logic** | `DomainLogicRedTest` | `src/test/java/.../entity/` | 6 |

**RED 원칙:** 메서드 본문 `fail("RED");` 만 — assert·Mock·프로덕션 호출 없음.

### 4.2 Track A — UI / Boundary

| TC-ID | 메서드 | 보호 계약 |
|-------|--------|-----------|
| TC-A-RED-01 | `parseAndConvert_meterColonTwoFive_...` | F-01, F-02, AC-01 |
| TC-A-RED-02 | `parse_missingColon_...` | ERR-FMT-001 |
| TC-A-RED-03 | `validate_meterNegativeOne_...` | ERR-VAL-002, NEG-02 |
| TC-A-RED-04 | `validate_unknownUnit_parsec_...` | ERR-DOM-003 |
| TC-A-RED-05 | `renderPlain_...preservesSource` | RG-03, GH-08 |
| TC-A-RED-06 | `renderJson_...matchesSchema` | F-07 JSON |

### 4.3 Track B — Domain / Logic

| TC-ID | 메서드 | 보호 Invariant |
|-------|--------|----------------|
| TC-B-RED-01 | `convert_meterToFeet_returnsCorrectRatio` | BR-001, ε≤1e-5 |
| TC-B-RED-02 | `convert_meterToYard_returnsCorrectRatio` | BR-002 |
| TC-B-RED-03 | `convertAll_meterOne_returnsAllRegisteredUnits` | INV-D2, F-02 |
| TC-B-RED-04 | `registerUnit_cubit_thenConvertToMeter` | INV-D3, F-05 |
| TC-B-RED-05 | `loadConfig_validFile_appliesRatios` | F-06, DATA-01 |
| TC-B-RED-06 | `loadConfig_missingFile_keepsDefaultRatios` | INV-D4, fallback |

상세 Given/When/Then: [10_RED_듀얼트랙_테스트_명세.md](../docs/10_RED_듀얼트랙_테스트_명세.md)

---

## 5. 테스트 실행 결과

### 5.1 최신 실행 (`mvn test`)

| 항목 | 결과 |
|------|------|
| Tests run | **12** |
| Failures | **12** |
| Errors | 0 |
| Skipped | 0 |
| BUILD | **FAILURE** |
| 실패 메시지 | `AssertionFailedError: RED` (전건 동일) |

### 5.2 클래스별

| 클래스 | Run | Failures |
|--------|-----|----------|
| `UiBoundaryRedTest` | 6 | 6 |
| `DomainLogicRedTest` | 6 | 6 |

### 5.3 RED 완료 증거 (체크)

| ☐ | 항목 |
|:-:|------|
| ☑ | `mvn test` BUILD FAILURE 확인 |
| ☑ | 실패 원인 `RED` 문자열 (스켈레톤) |
| ☐ | RED FAIL 로그 스크린샷/파일 첨부 (US-07) |
| ☐ | `@Disabled` / assert 삭제 0건 |

**재현 명령:**

```bash
cd d:\Vs_workplace\Java_project\UnitConverter_01
mvn test
```

**리포트 경로:** `target/surefire-reports/`

---

## 6. 결함 요약

전체 목록: [09_defect_list.md](../docs/09_defect_list.md)

| Severity | Open | 대표 |
|----------|------|------|
| Critical | 31 | DEF-001 meter→feet: 8.202100 vs 0.000000 |
| Major | 0 | — |
| Minor | 0 | — |

### 6.1 수정 우선순위 (GREEN 그룹)

| 순서 | 그룹 | 파일 | 영향 |
|:----:|------|------|------|
| 1 | G-1 | `ConversionEngine.java` | DEF-001–011, 환산 핵심 |
| 2 | G-2 | `UnitRegistry.java` | 시드·등록 |
| 3 | G-3 | `LineParser.java` | Track A 파싱 |
| 4 | G-4 | `InputValidator.java` | 음수·미등록 |
| 5 | G-5 | `ConfigurationLoader.java` | JSON/YAML |

Diff: [08_red_단계_수정전략.md](../docs/08_red_단계_수정전략.md) §4

---

## 7. 문서 산출물

### 7.1 docs/

| 번호 | 문서 | 용도 |
|:----:|------|------|
| 00 | PRD | 계약·AC |
| 03 | To-Do | 구현 작업 |
| 07 | test_plan | 테스트 계획 |
| 08 | red_단계_수정전략 | GREEN diff |
| 09 | defect_list | 결함 31건 |
| 10 | RED_듀얼트랙_테스트_명세 | TC Given/When/Then |

### 7.2 report/ (본 폴더)

| 번호 | 문서 |
|:----:|------|
| 01 | 본 보고서 — RED 단계 작업 종합 |

### 7.3 README 체크리스트

- [x] RED To-Do 리스트 섹션
- [x] `09_defect_list.md` 연결
- [ ] 모든 결함 수정 후 회귀 PASS

---

## 8. 리스크 및 이슈

| ID | 이슈 | 영향 | 완화 |
|----|------|------|------|
| R-01 | 레거시·BCE 이중 구현 | 혼선 | GREEN 후 `App` 위임 |
| R-02 | JSON Renderer 없음 | TC-A-RED-06 지연 | P2, PLAIN 우선 |
| R-03 | defect_list가 이전 35테스트 기준 | 수치 불일치 | 스켈레톤 12건 기준 재매핑 권장 |
| R-04 | PLAIN 1자리 vs raw 8.202100 | assert 혼동 | Domain ε / Boundary 포맷 분리 |

---

## 9. 다음 단계 (GREEN)

| 순서 | 작업 | 완료 기준 |
|:----:|------|-----------|
| 1 | G-1 `ConversionEngine` + `UnitRegistry.withDefaults` | `DomainLogicRedTest` TC-B-RED-01–03 GREEN |
| 2 | G-3–4 `LineParser` + `InputValidator` | `UiBoundaryRedTest` TC-A-RED-01–04 GREEN |
| 3 | G-5 `ConfigurationLoader` | TC-B-RED-05–06 GREEN |
| 4 | 스켈레톤 → 실제 assert 교체 | `fail("RED")` 제거 |
| 5 | `mvn test` 12/12 PASS | BUILD SUCCESS |
| 6 | `mvn verify` + JaCoCo | Domain ≥95%, Boundary ≥85% |
| 7 | PR `red` → `A-01` | 리뷰 10+ |

**금지:** RED 단계에서 GREEN·REFACTOR 동시 진행, assert 삭제로 통과.

---

## 10. 결론

- **RED 단계 목표 달성:** Dual-Track 테스트 12건이 `fail("RED")`로 계약을 고정했고, `mvn test` 실패는 TDD RED 원칙에 부합한다.
- **다음 마일스톤:** [08_red_단계_수정전략.md](../docs/08_red_단계_수정전략.md)에 따라 Domain 환산(G-1)부터 GREEN한다.
- **보고 시점 브랜치:** `red` @ `a8b81f3` (A-01-0_SPEC merge 기준)

---

## 부록 A — 관련 링크

| 리소스 | 경로 |
|--------|------|
| 저장소 README | [../README.md](../README.md) |
| 작업 시나리오 | [../작업시나리오/전체 작업 시나리오.md](../작업시나리오/전체%20작업%20시나리오.md) |
| Surefire | `target/surefire-reports/` |
| JaCoCo (verify 후) | `target/site/jacoco/index.html` |

---

*보고서 끝.*
