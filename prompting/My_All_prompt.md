# My_All_prompt

사용자(User)가 Agent에게 입력한 프롬프트만 추출한 목록입니다.

| No | 역할 | 내용 |
|:---:|------|------|
| 1 | 사용자 | https://github.com/antihu99/UnitConverter_01.git  와 local 을 연결해줘 |
| 2 | 사용자 | terminal 에서 대화한 내용을 작업시나리오 폴더에 00 으로 시작하는 파일로 저장해줘 |
| 3 | 사용자 | @UnitConverter.java <br>🔲 문제 정의 전문가 — Java<br>당신은 문제 정의 전문가입니다.<br>워크스페이스: unitconverter_java<br>⚠️ 코드 작성·클래스 설계·알고리즘·빌드 스크립트는 하지 마십시오.<br><br>STEP 1 — Observation<br>1) 지금 우리가 해결하려는 "상황"을 한 문단으로 관찰 관점에서 서술하라.<br>2) 왜 길이(meter/feet/yard) 변환 문제를 다루는가?<br>3) OCP/SRP, 입력 검증, 설정 외부화, 동적 단위 등록, 출력 포맷과 연결해<br>   이 문제가 어떤 학습 맥락에서 등장하는가?<br><br>STEP 2 — Why #1: 정확한 변환 결과가 왜 중요한가?<br>STEP 3 — Why #2: 수동 계산이 아닌 프로그램(+테스트)인 이유?<br>STEP 4 — Why #3: 계약을 먼저 고정하려는 이유?<br><br>STEP 5 — 문제 정의<br>1) 표면 문제 1–2문장<br>2) 개선된 정의 3–7문장<br>3) 핵심 Invariant 5개 이내 (측정·검증 가능한 문장)<br>4) 훈련하려는 사고 능력 3개<br><br>출력: 구조화된 Markdown만. 코드·파일 경로 금지. |
| 4 | 사용자 | @README.md 파일을 읽어서, 요구사항정의서를 UnitConverterRequirements.txt 파일명으로 docs 폴더에 만들어줘 |
| 5 | 사용자 | @작업시나리오/전체 작업 시나리오.md @docs/01_UnitConverterRequirements.txt 를 참고하여, 프로젝트 루트의 .cursorrules로 작성해줘 |
| 6 | 사용자 | 🔲 문제 정의 전문가 — Java<br>당신은 문제 정의 전문가입니다.<br>워크스페이스: @UnitConverter.java <br>⚠️ 코드 작성·클래스 설계·알고리즘·빌드 스크립트는 하지 마십시오.<br><br>STEP 1 — Observation<br>1) 지금 우리가 해결하려는 "상황"을 한 문단으로 관찰 관점에서 서술하라.<br>2) 왜 길이(meter/feet/yard) 변환 문제를 다루는가?<br>3) OCP/SRP, 입력 검증, 설정 외부화, 동적 단위 등록, 출력 포맷과 연결해<br>   이 문제가 어떤 학습 맥락에서 등장하는가?<br><br>STEP 2 — Why #1: 정확한 변환 결과가 왜 중요한가?<br>STEP 3 — Why #2: 수동 계산이 아닌 프로그램(+테스트)인 이유?<br>STEP 4 — Why #3: 계약을 먼저 고정하려는 이유?<br><br>STEP 5 — 문제 정의<br>1) 표면 문제 1–2문장<br>2) 개선된 정의 3–7문장<br>3) 핵심 Invariant 5개 이내 (측정·검증 가능한 문장)<br>4) 훈련하려는 사고 능력 3개<br><br>출력: 구조화된 Markdown만. 코드·파일 경로 금지. |
| 7 | 사용자 | 🔲 Mom Test 질문<br>unit_converter_java 프로젝트를 "고객 인터뷰" 관점에서 준비한다.<br>제품이 아니라 학습자/사용자의 행동을 드러내는 Mom Test 질문 10개.<br>각 질문: (1) 과거 구체 경험 (2) 실제 행동 (3) 돈/시간/리스크 연결.<br>금지: 기능 나열형 질문만으로 끝내기.<br>마지막에 "진짜 위험 가정" 3개를 bullet로.<br>구현·코드 금지. |
| 8 | 사용자 | 🔲 BCE 설계 전문가 — Java<br>Dual-Track(UI/경계 vs Domain) + BCE 관점 설계 전문가로 행동하라.<br>대상: @UnitConverter.java <br>제약: 구현 코드 작성 금지. 설계·계약·테스트 목록·통합 계획만.<br><br># 1) Entity(Domain) 설계<br>  1.1 개념 목록과 SRP<br>  1.2 Invariants<br>  1.3 유스케이스<br>  1.4 Domain API (시그니처 수준, 본문 X) + 실패 조건<br>  1.5 Domain 단위 테스트 설계 (RED 우선)<br><br># 2) Boundary 설계<br>  2.1 시나리오: 입력→검증→실행→출력<br>  2.2 외부 계약: Input / Output / Error schema<br>  2.3 Boundary 계약 테스트 (Domain Mock 가정)<br>  2.4 에러 메시지 규칙 (문구 패턴 고정)<br><br># 3) Data 설계<br>  3.1 목적<br>  3.2 인터페이스 계약 (이름만 제안)<br>  3.3 InMemory vs File(JSON/YAML) 비교 + 추천 1개와 이유<br>  3.4 Data 레이어 테스트<br><br># 4) Integration & Verification<br>  4.1 의존성 방향 포함 흐름<br>  4.2 통합 테스트 시나리오 (정상 2+, 실패 3+)<br>  4.3 회귀 보호 규칙<br>  4.4 커버리지 목표 (Domain / Boundary / Data 수치)<br>  4.5 Traceability Matrix: Concept→Rule→UseCase→Contract→Test→Component<br><br>모호어 금지. 모든 규칙은 테스트로 검증 가능하게. |
| 9 | 사용자 | @UnitConverter.java  .cursorrules YAML 뼈대.<br>최상위 키만, 값은 비움:<br>project, code_style, architecture, tdd_rules, testing, forbidden, file_structure, ai_behavior<br>각 키 앞에 80자 폭의 # 구분선 주석.<br>YAML만 출력. 설명 없음. |
| 10 | 사용자 | 위 .cursorrules의 tdd_rules 블록만 채워라.<br>Java 단위 변환기 기준:<br>red_phase / green_phase / refactor_phase 각각에<br>description, rules(목록), must_not(목록)<br>YAML만 출력. |
| 11 | 사용자 | 🔲 검토<br>완성한 .cursorrules 전체를 검토하라. 수정 제안 금지.<br>보고만:<br>1) YAML 문법 오류 가능성<br>2) 누락된 필수 섹션<br>3) tdd_rules vs forbidden 충돌<br>4) Cursor가 지키기 어려운 ai_behavior 규칙<br>bullet만. |
| 12 | 사용자 | 🔲 완성 YAML<br>프로젝트 루트 .cursorrules에 넣을 완성 YAML.<br>Java Java 17 이상 가정.<br>code_style: Google Java Format, 네이밍·패키지/모듈 분리<br>architecture: boundary/control/entity 정의 + 의존성 방향<br>testing: JUnit 5 고정, AAA, fixture 규칙, 커버리지 기준<br>forbidden: System.out 디버그 남발 / 매직 넘버 하드코딩 / 광범위 catch(Exception) 무시 / public 필드 남용 / God class (Java 특화 5개 이상)<br>  (각각 pattern / reason / alternative)<br>file_structure: boundary/ control/ entity/ tests/ 트리 주석<br>ai_behavior: 레이어 위반 금지, 테스트 먼저, 리팩터는 green 이후<br>설명 없이 YAML 전체만. |
| 13 | 사용자 | 요구사항 서술 패키지 — Java<br>@UnitConverter.java 를 위한 요구사항 서술 패키지. 구현·코드 금지.<br><br>Level 1 Epic<br>  제목: "확장 가능한 Java 단위 변환 학습 시스템"<br>  목적 4줄, 성공 기준 측정 가능하게 (커버리지, 계약 테스트, 회귀 정책)<br><br>Level 2 User Journey (1개 정본)<br>  Persona: Java/클린 아키텍처 학습자<br>  단계 5–7: 문제 인식→계약 정의→도메인 분리→Dual-Track TDD→회귀 보호<br>  각 단계 Pain / Opportunity 1줄<br><br>Level 3 User Stories (최소 6개)<br>  입력 검증 / 레지스트리·OCP / 환산 정확도 / 출력 포맷 / 설정 로드 실패 / 동적 단위 등록<br>  각 Story에 Acceptance Criteria는 체크 가능한 bullet<br><br>Level 4 Gherkin Feature 1개<br>  Background: README meter↔feet↔yard 비율을 Given으로 고정<br>  Scenario: happy path meter 입력<br>  Scenario: 잘못된 형식<br>  Scenario: unknown unit<br>  Scenario: 설정 파일 형식 오류<br><br>Level 5 체크리스트<br>  이 저장소 범위 항목만<br>  Epic→Journey→Story→Gherkin 정합성 체크박스 표<br><br>Markdown 표와 체크리스트 적극 사용. |
| 14 | 사용자 | @UnitConverter.java 사용자 여정을 스토리보드 형식으로.<br>Stage: Awareness / Entry / Action / Validation / Outcome<br>각 Stage마다 Action, Thinking, Emotion, Pain, Opportunity 1줄 이상.<br>목표: 알고리즘이 아닌 계약·테스트·레이어 분리 학습임을 명시.<br>코드 금지. |
| 15 | 사용자 | README의 meter↔feet↔yard 비율을 전제로 Gherkin 시나리오 8개.<br>Given-When-Then은 영어 키워드 유지.<br>반드시 포함:<br>  - 소수 파싱 실패<br>  - 음수 입력 정책 (정책 문장 먼저 고정 후 시나리오)<br>  - 출력에 원 입력 단위/값 보존 (표현 계약)<br>구현 금지. |
| 16 | 사용자 | 🔲 체크리스트 정화<br>Epic/Journey/Story/Gherkin과 대조해 Level 5 완성도 체크리스트.<br>저장소 범위 밖 항목 금지.<br>각 항목: "누가 무엇을 어떻게 검증하면 통과인지" 한 줄.<br>표: 영역 / 검증 방법 / 통과 기준 / 추적 ID(Story 번호)<br>코드 금지. |
| 17 | 사용자 | 🔲 PRD 전체 패키지 — Java<br>당신은 제품 요구사항 문서(PRD) 전문가입니다.<br>워크스페이스: @UnitConverter.java <br>⚠️ 코드·클래스 설계·빌드 스크립트는 작성하지 마십시오. 문서만.<br>전제: Phase 4에서 완성된 Epic/User Stories/Gherkin을 기반으로 작성하라.<br><br># 1. 프로젝트 개요<br>  1.1 한 줄 목적문 (What / Who / Why)<br>  1.2 배경 및 문제 정의 (관찰 관점)<br>  1.3 목표 3–5개 (측정 가능하게)<br>  1.4 비목표(Non-Goal) 2–3개<br><br># 2. 사용자 및 이해관계자<br>  2.1 타깃 사용자 (페르소나 1개)<br>  2.2 주요 사용 시나리오 2–3개 (Phase 4 Journey 기반)<br><br># 3. 기능 요구사항<br>  3.1 핵심 기능 목록 (우선순위: 필수/권장/선택 구분)<br>  3.2 기능별 입·출력 계약 (문자열 계약 수준, 코드 X)<br>  3.3 제약 사항 (입력 형식, 허용 단위, 음수 정책, 미지원 단위 처리)<br>  → Phase 4 Gherkin의 Background Given과 반드시 일치시킬 것<br><br># 4. 비기능 요구사항<br>  4.1 기술 스택: Java 17, Gradle/Maven, JUnit 5<br>  4.2 아키텍처 원칙 (OCP/SRP, BCE 레이어, 의존성 방향)<br>  4.3 테스트 커버리지 목표 (Domain/Boundary/Data 수치)<br>  4.4 확장성 원칙 (새 단위 추가 시 기존 코드 변경 최소화)<br><br># 5. 데이터 요구사항<br>  5.1 단위 비율 상수 (meter 허브 기준)<br>  5.2 설정 외부화 방식 (JSON/YAML 또는 메모리 기본값)<br>  5.3 동적 단위 등록 계약 (register: 형식 명시)<br><br># 6. 출력 요구사항<br>  6.1 콘솔 기본 포맷 (필드·순서·소수 자릿수 정책)<br>  6.2 JSON 출력 스키마 (확장 시)<br>  6.3 CSV 출력 스키마 (확장 시)<br>  6.4 표(Table) 출력 스키마 (확장 시)<br><br># 7. 성공 지표<br>  7.1 인수 기준 3–5개 (체크박스, 테스트 가능하게)<br>       → Phase 4 Story의 Acceptance Criteria와 일치 여부 확인<br>  7.2 회귀 보호 규칙 (계약 변경 금지 정책)<br><br># 8. 용어 정의 (Glossary)<br>  주요 도메인 용어 5개 이상<br><br>출력: 구조화된 Markdown만. 모호어("적절히") 금지.<br>모든 계약·기준은 테스트 가능한 문장으로 작성. |
| 18 | 사용자 | 🔲 1단계 — 개요·목표만<br>Phase 4 Epic을 기반으로 PRD의 1–2번 섹션(개요, 사용자)만 작성하라.<br>목표는 측정 가능해야 하고, 비목표는 경계를 명확히 하라.<br>구현 금지.<br><br>🔲 2단계 — 기능 요구사항만<br>Phase 4 User Stories를 기반으로 기능 요구사항(섹션 3)을 작성하라.<br>입출력 계약은 문자열 수준으로만 정의하고, 코드는 작성하지 말 것.<br>우선순위(필수/권장/선택)를 반드시 구분하라.<br><br>🔲 3단계 — PRD ↔ Phase 4 정합성 검토<br>완성된 PRD와 Phase 4(Epic/Story/Gherkin/체크리스트)를 비교하라.<br>보고만 (수정 금지):<br>1) Phase 4 Story에 있으나 PRD에 빠진 기능<br>2) PRD에 있으나 Phase 4 Gherkin과 계약이 불일치하는 항목<br>3) PRD → Phase 6(README) 이관 시 누락 위험 항목<br>bullet만. 코드 금지. |
| 19 | 사용자 | 위 내용 기준으로 PRD 파일을 만들어줘 |
| 20 | 사용자 | README.md 생성 — Java<br>당신은 오픈소스 문서화 전문가입니다.<br>워크스페이스: c:\DEV\unit_converter_java<br>전제: Phase 5 PRD를 기반으로 작성하라.<br>⚠️ 코드 구현 추가 금지. 문서만 작성하라.<br><br># 프로젝트 이름<br>한 줄 설명 (무엇을, 누구를 위해) — PRD 1.1 목적문 활용<br><br>## 목차<br>자동 링크 포함.<br><br>## 개요 (Overview)<br>- 이 프로젝트가 해결하는 문제 (PRD 1.2 배경 기반)<br>- 주요 학습 목표 (OCP/SRP, BCE, TDD)<br>- PRD와의 연결 (한 줄 요약 + PRD 문서 링크)<br><br>## 빠른 시작 (Quick Start)<br>- 사전 조건 (Java 버전, 빌드 도구)<br>- 빌드 & 실행 명령 (셸 블록 포함)<br>- 예시 입출력 (meter:5.0 → 결과)<br><br>## 지원 단위 및 비율<br>표 형식: 단위명 / 식별자 / meter 기준 비율 / 출처<br>(PRD 5.1 단위 비율 상수 기반)<br><br>## 입력 형식 계약<br>- 정상: 단위:값 예시 3개<br>- 비정상: 오류 케이스 3개 + 에러 메시지 패턴<br>(PRD 3.3 제약 사항 기반)<br><br>## 아키텍처<br>- BCE 레이어 다이어그램 (Mermaid 또는 ASCII)<br>- 의존성 방향 설명<br>- 새 단위 추가 방법 (단계별, 코드 최소화)<br><br>## 테스트 실행<br>- 테스트 프레임워크: JUnit 5<br>- 명령: 셸 블록<br>- 커버리지 목표 (PRD 4.3 기반)<br><br>## 설정 파일 (JSON/YAML)<br>- 위치 및 형식 예시<br>- 동적 단위 등록 예시 (PRD 5.3 기반)<br><br>## 출력 포맷<br>콘솔 / JSON / CSV 각 예시 블록 포함. (PRD 6.1–6.3 기반)<br><br>## 기여 가이드 (Contributing)<br>- 계약 변경 금지 원칙<br>- 테스트 없는 PR 거부 정책<br>- 커밋 메시지 컨벤션<br><br>## 라이선스<br>MIT (학습용).<br><br>Markdown만 출력. 코드 구현 추가 금지.<br>표·코드 블록·Mermaid 다이어그램을 적극 활용하라. |
| 21 | 사용자 | README.md 생성 — Java<br>당신은 오픈소스 문서화 전문가입니다.<br>워크스페이스: @UnitConverter.java <br>전제: Phase 5 PRD를 기반으로 작성하라.<br>⚠️ 코드 구현 추가 금지. 문서만 작성하라.<br><br># 프로젝트 이름<br>한 줄 설명 (무엇을, 누구를 위해) — PRD 1.1 목적문 활용<br><br>## 목차<br>자동 링크 포함.<br><br>## 개요 (Overview)<br>- 이 프로젝트가 해결하는 문제 (PRD 1.2 배경 기반)<br>- 주요 학습 목표 (OCP/SRP, BCE, TDD)<br>- PRD와의 연결 (한 줄 요약 + PRD 문서 링크)<br><br>## 빠른 시작 (Quick Start)<br>- 사전 조건 (Java 버전, 빌드 도구)<br>- 빌드 & 실행 명령 (셸 블록 포함)<br>- 예시 입출력 (meter:5.0 → 결과)<br><br>## 지원 단위 및 비율<br>표 형식: 단위명 / 식별자 / meter 기준 비율 / 출처<br>(PRD 5.1 단위 비율 상수 기반)<br><br>## 입력 형식 계약<br>- 정상: 단위:값 예시 3개<br>- 비정상: 오류 케이스 3개 + 에러 메시지 패턴<br>(PRD 3.3 제약 사항 기반)<br><br>## 아키텍처<br>- BCE 레이어 다이어그램 (Mermaid 또는 ASCII)<br>- 의존성 방향 설명<br>- 새 단위 추가 방법 (단계별, 코드 최소화)<br><br>## 테스트 실행<br>- 테스트 프레임워크: JUnit 5<br>- 명령: 셸 블록<br>- 커버리지 목표 (PRD 4.3 기반)<br><br>## 설정 파일 (JSON/YAML)<br>- 위치 및 형식 예시<br>- 동적 단위 등록 예시 (PRD 5.3 기반)<br><br>## 출력 포맷<br>콘솔 / JSON / CSV 각 예시 블록 포함. (PRD 6.1–6.3 기반)<br><br>## 기여 가이드 (Contributing)<br>- 계약 변경 금지 원칙<br>- 테스트 없는 PR 거부 정책<br>- 커밋 메시지 컨벤션<br><br>## 라이선스<br>MIT (학습용).<br><br>Markdown만 출력. 코드 구현 추가 금지.<br>표·코드 블록·Mermaid 다이어그램을 적극 활용하라. |
| 22 | 사용자 | 위 내용을 기준으로 @README.md 파일을 만들어줘, 기존 @README.md 파일은 docs 폴더 밑에 요구사항 으로 이름을 변경해서 저장해줘 |
| 23 | 사용자 | 지금까지 작업한 파일을 GitHub에 업로드 해줘 "#1단계. PRD 및 README 파일 작성" 이라고 COMMENT 해줘 |
| 24 | 사용자 | 모든 파일을 업로드 해주세요 |
| 25 | 사용자 | GITHUB 에 있는 브랜치 목록을 보여줘 |
| 26 | 사용자 | GITHUB 의 A-01-0_SPEC 브랜치에 모든 파일을 업로드 해줘 COMMENT 는 " #1단계. README 및 PRD 작성" |
| 27 | 사용자 | prompting 폴더에 agent 와 대화한 전체를 파일로 저장해줘.<br>그리고 그중에서 내가 입력한 prompt 는 "My_All_prompt" 파일로 만들어줘, 대화의 내용은 표 형식으로 역할과 내용을 구별해줘 |
| 28 | 사용자 | 워크스페이스: @UnitConverter.java · Phase 5 PRD 기반 To-Do 리스트 작성 (Must/Should/Nice/Tech Debt/Done/회귀/마일스톤). 코드 금지. |
| 29 | 사용자 | 🔲 README vs PRD 검토 — 4항목 bullet 보고만, 수정 금지. |
| 30 | 사용자 | 위내용을 README_review 파일로 만들어줘 |
| 31 | 사용자 | 🔲 추적 매트릭스 — Phase 4 US × Phase 6 To-Do, 누락/고아 To-Do bullet. 코드 금지. |
| 32 | 사용자 | docs 폴더 파일 생성 순서대로 2자리 숫자 접두어로 파일명 변경 |
| 33 | 사용자 | agent 대화 중 github 명령어만 뽑아 git_prompt 파일로 |
| 34 | 사용자 | @Agent_대화_전체.md → 2자리+SPEC_PROMPT 파일명, 대화 전체 덮어쓰기 |
| 35 | 사용자 | @My_All_prompt.md 마지막에 프롬프트 흐름 요약 (의도·참조 문서 명시) |
| 36 | 사용자 | github 의 A-01-0_SPEC 브랜치를 A-01 로 merge 후 삭제했습니다. 현재 작업중인 브랜치를 알려주세요 |
| 37 | 사용자 | 위 git 을 실행해주세요 |
| 38 | 사용자 | 현재 브랜치를 알려줘 |
| 39 | 사용자 | 수정된 파일을 git 에 upload 해줘 |
| 40 | 사용자 | 작업 브랜치가 어디인가요? |
| 41 | 사용자 | a-01 브랜치에서 red 브랜치를 만들어주세요, 그리고 github 와 동기화 해주세요 |
| 42 | 사용자 | @UnitConverter.java 프로젝트를 구현하기 위한 깃 브랜치 전략을 알려줘. 개발방법론은 Dual-Track TDD (RED/GREEN/Refactoring) |
| 43 | 사용자 | @UnitConverter.java … 위 내용을 ##_전략_RED 파일로 만들어줘 |
| 44 | 사용자 | 해당 파일을 docs 폴더의 파일 순차적으로 번호를 매겨서 이름을 변경하고 docs 로 옮겨줘 |
| 45 | 사용자 | @README.md … "## RED 단계 To-Do 리스트" 섹션만 새로 삽입 |
| 46 | 사용자 | @README.md @docs/00_PRD.md 참고하여 테스트 플랜 작성을 위한 샘플 예제 1개를 선택해줘 (코드 작성 금지) |
| 47 | 사용자 | [P] 시니어 QA … test_plan.md 로 저장 (docs) |
| 48 | 사용자 | 산출물은 docs 폴더에 순차적 번호 (test_plan → 07_test_plan) |
| 49 | 사용자 | [P] 디버깅 QA … (mvn test 실패 로그 붙여넣을 것) — 로그 미첨부 |
| 50 | 사용자 | mvn test가 Red 가 되게 테스트 코드를 다시 작성해줘 |
| 51 | 사용자 | mvn test 실패 로그를 보여줘 |
| 52 | 사용자 | mvn test -Dtest=ConversionEngineNormalTest |
| 53 | 사용자 | [P] QA … defect_list.md + 수정전략 docs/red_단계 수정전략 |
| 54 | 사용자 | 발견된 결함을 docs/defect_list.md에 정리 |
| 55 | 사용자 | Dual-Track RED 테스트 명세 (구현/GREEN/REFACTOR 금지) |
| 56 | 사용자 | JUnit 5 테스트 스켈레톤 … 메서드 본문 fail("RED") 한 줄만 |
| 57 | 사용자 | report 폴더에 보고서 작성 |
| 58 | 사용자 | 지금까지 agent 대화를 prompting 폴더에 … My_All_prompt 추가 … 취소선 ~~ 오류 개선 모든 문서 수정 |
| 59 | 사용자 | git 의 현재 브랜치를 보여줘 |
| 60 | 사용자 | github 와 동기화가 안된거 같아 다시 동기화를 진행해줘 |
| 61 | 사용자 | 현재 브랜치를 알려줘 |
| 62 | 사용자 | 원격 기준으로 동기화를 원했는데, local 기준으로 동기화가 된거 같아요. |
| 63 | 사용자 | 원격 기준으로 로컬을 동기화 해주세요 |
| 64 | 사용자 | 네.. A-01 브랜치 기준으로 맞춰주세요 |
| 65 | 사용자 | 그럼 현재 작업 브랜치는 어디인가요? |
| 66 | 사용자 | RED 브랜치는 MERGE 후 삭제되어야해 |
| 67 | 사용자 | 그럼 RED 브랜치는 원격과 로컬에 모두 없나요? |
| 68 | 사용자 | 로컬과 원격의 작업 브랜치는 어디인가요? |
| 69 | 사용자 | 로컬 A-01-SPEC 브랜치도 로컬에서 삭제해주세요 |
| 70 | 사용자 | A-01 브랜치에서 GREEN 브랜치를 추가해 주세요 원격과 로컬 모두 생성해주세요 |
| 71 | 사용자 | 혹시... PROMPTING 폴더에 있는 @prompting/00_SPEC_PROMPT.md  를 보고,  당시 작업했던 내용을 보고, 보고서를 REPROT 폴더에 만들어주세요. REPORT 폴더의 파일명 순서를 다시 정리해주세요 |
| 72 | 사용자 | 현재 작업 BRANCH 는 어디인가요? |
| 73 | 사용자 | 🔲 GREEN 첫 번째 AC — Java<br>[단계] TDD GREEN만 수행한다. REFACTOR는 하지 않는다.<br>프로젝트 내 JAVA 파일<br><br>대상 테스트: TC-B-01<br>  파일: src/test/java/UnitConverterTest.java<br>  내용: assertEquals(8.20210, converter.convert("meter", 2.5, "feet"), 1e-5);<br><br>PRD §3.2 비즈니스 로직:<br>  1 meter = 3.28084 feet<br>  1 meter = 1.09361 yard<br>  (비율은 상수로 추출 — 인라인 하드코딩 금지)<br><br>할 일:<br>1. mvn test 실행 → TC-B-01 FAIL 확인<br>2. convert() 함수 최소 구현 (meter→feet 분기만)<br>3. mvn test 재실행 → TC-B-01 PASS 확인<br><br>금지:<br>- 다른 TC (TC-B-02~07, TC-A-01~07) 동시 구현<br>- REFACTOR (이름 정리, 구조 분리) 이번 커밋에… |
| 74 | 사용자 | 진행 |
| 75 | 사용자 | 로컬과 원격에  COMMIT 진행해주세요, 제안한 COMMIT 메세지 앞에 "TC-B-01 GREEN 완료" 도 붙여주세요 |
| 76 | 사용자 | [단계] TDD GREEN만 수행한다. REFACTOR는 하지 않는다.<br>프로젝트내 JAVA 파일<br><br>대상 테스트:  TC-A-02<br>  파일: src/test/java/UnitConverterTest.java<br>  내용: assertEquals(8.20210, converter.convert("meter", 2.5, "feet"), 1e-5);<br><br>PRD §3.2 비즈니스 로직:<br>  1 meter = 3.28084 feet<br>  1 meter = 1.09361 yard<br>  (비율은 상수로 추출 — 인라인 하드코딩 금지)<br><br>할 일:<br>1. mvn test 실행 → TC-A-02 FAIL 확인<br>2. convert() 함수 최소 구현 (meter→feet 분기만)<br>3. mvn test 재실행 →TC-A-02 PASS 확인<br><br>금지:<br>- 다른 TC 동시 구현<br>- REFACTOR (이름 정리, 구조 분리) 이번 커밋에서 금지<br>- 비율 3.28084 인라인 사용 금지 (상수 정의 후 사용)<br><br>산출물:<br>- … |
| 77 | 사용자 | 네.. 커밋 진행해주세요 |
| 78 | 사용자 | [단계] TDD GREEN만 수행한다. REFACTOR는 하지 않는다.<br>프로젝트내 JAVA 파일<br><br>대상 테스트:  TC-B-02 <br><br>PRD §3.2 비즈니스 로직:<br>  1 meter = 3.28084 feet<br>  1 meter = 1.09361 yard<br>  (비율은 상수로 추출 — 인라인 하드코딩 금지)<br><br>할 일:<br>1. mvn test 실행 → TC-B-02 FAIL 확인<br>2. convert() 함수 최소 구현 convert("meter",v,"yard") 추가<br>3. mvn test 재실행 →TC-B-02 PASS 확인<br><br>금지:<br>- 다른 TC 동시 구현<br>- REFACTOR (이름 정리, 구조 분리) 이번 커밋에서 금지<br>- 비율 3.28084 인라인 사용 금지 (상수 정의 후 사용)<br><br>산출물: |
| 79 | 사용자 | 네 모두 진행해주세요 |
| 80 | 사용자 | [단계] TDD GREEN만 수행한다. REFACTOR는 하지 않는다.<br>프로젝트내 JAVA 파일<br><br>대상 테스트:    TC-A-03<br><br>PRD §3.2 비즈니스 로직:<br>  1 meter = 3.28084 feet<br>  1 meter = 1.09361 yard<br>  (비율은 상수로 추출 — 인라인 하드코딩 금지)<br><br>할 일:<br>1. mvn test 실행 → TC-A-03	 FAIL 확인<br>2. 음수 입력 해결<br>3. mvn test 재실행 →TC-A-03	 PASS 확인<br><br>금지:<br>- 다른 TC 동시 구현<br>- REFACTOR (이름 정리, 구조 분리) 이번 커밋에서 금지<br>- 비율 3.28084 인라인 사용 금지 (상수 정의 후 사용)<br><br>산출물:<br>- 해당 TC가 통과하는 최소 프로덕션 코드<br>- 커밋 메시지 제안: "TC-A-03	음수 입력 → 예외 반환	feat(green): validate negative value" |
| 81 | 사용자 | 모두 진행하세요 |
| 82 | 사용자 | [단계] TDD GREEN만 수행한다. REFACTOR는 하지 않는다.<br>프로젝트내 JAVA 파일<br><br>대상 테스트:   TC-B-03<br><br>PRD §3.2 비즈니스 로직:<br>  1 meter = 3.28084 feet<br>  1 meter = 1.09361 yard<br>  (비율은 상수로 추출 — 인라인 하드코딩 금지)<br><br>할 일:<br>1. mvn test 실행 → TC-B-03	 FAIL 확인<br>2. convert("feet",v,"meter") 역변환  해결<br>3. mvn test 재실행 →TC-B-03	 PASS 확인<br><br>금지:<br>- 다른 TC 동시 구현<br>- REFACTOR (이름 정리, 구조 분리) 이번 커밋에서 금지<br>- 비율 3.28084 인라인 사용 금지 (상수 정의 후 사용)<br><br>산출물:<br>- 해당 TC가 통과하는 최소 프로덕션 코드<br>- 커밋 메시지 제안: "TC-B-03 GREEN feat(green): feet to meter reverse" |
| 83 | 사용자 | 네 진행하세요 |
| 84 | 사용자 | TC-B-03 GREEN 완료 feat(green): feet to meter reverse 에 대한 COMMIT 이 진행중인가요? |
| 85 | 사용자 | [단계] TDD GREEN만 수행한다. REFACTOR는 하지 않는다.<br>프로젝트내 JAVA 파일<br><br>대상 테스트:   각 단계별 TC <br><br>PRD §3.2 비즈니스 로직:<br>  1 meter = 3.28084 feet<br>  1 meter = 1.09361 yard<br>  (비율은 상수로 추출 — 인라인 하드코딩 금지)<br><br>할 일:<br>1. mvn test 실행 → 각 단계별 TC  FAIL 확인 후 해결<br>3. mvn test 재실행 → 각 단계별 TC PASS 확인<br><br>산출물:<br>- 해당 TC가 통과하는 최소 프로덕션 코드<br><br>GITHUB 동기화 진행<br>각 단게별로  GITHUB 커밋  및 푸시 진행<br>각 단계별 커밋 메시지 는  아래 표를 참고하여, TC 번호 제안된 커밋 메세지로 작성함.<br><br>커밋 순서	Track	대상 TC	구현 내용	커밋 메시지<br>6번째	Track A	TC-A-04	없는 단위 → 예외 반환	feat(green): validate unknown unit<br>7번째	Track B	TC-B-04~0… |
| 86 | 사용자 | 모든 TC(TC-A-01~07, TC-B-01~07)가 통과한 후 전체 테스트를 실행하라.<br><br>mvn test # 또는: gradle test<br><br>커버리지 측정:<br>mvn jacoco:report # target/site/jacoco/index.html<br><br>확인 항목:<br>- 모든 TC PASS (0 failures)<br>- Domain Logic 커버리지 ≥ 95%<br>- Boundary Layer 커버리지 ≥ 85%<br>- 비율 상수 3.28084 / 1.09361이 인라인으로 남아 있지 않음<br>- main() 함수에 변환 로직이 남아 있지 않음 (Domain으로 분리됨) |
| 87 | 사용자 | 전체 검증 결과를 DOCS 폴더에 저장해줘 |
| 88 | 사용자 | AGENT와의 대화를 prompting 폴더에 저장해주고, 내가 입력한 prompt 는 @prompting/My_All_prompt.md  에 덧붙여 주고, @prompting/git_prompt.md  파일에는 git 명령어만 저장해줘. 마지막으로 report 폴더에 보고서를 작성해줘 |
---

## 프롬프트 흐름 요약

**목적:** 6시간 실습 전 **구현 없이** 계약·요구·문서·추적 체계를 고정한 뒤, RED→GREEN 구현 단계로 넘어가기 위한 프롬프트 순서 기록.

```mermaid
flowchart LR
  P0[0 인프라] --> P1[1 문제정의]
  P1 --> P2[2 설계·규칙]
  P2 --> P3[3 Phase4 요구]
  P3 --> P4[4 PRD]
  P4 --> P5[5 README]
  P5 --> P6[6 GitHub]
  P6 --> P7[7 추적·검토]
  P7 --> P8[8 아카이브]
```

### 단계별 흐름

| 단계 | No | 의도 | 주요 참조 문서 | 산출물 |
|------|:---:|------|----------------|--------|
| **0. 인프라·기록** | 1–2 | GitHub 연결·터미널 작업 이력 보존 | — | `작업시나리오/00_git-github-로컬연결.md` |
| **1. 문제·요구 초안** | 3–4, 6–7 | 레거시 코드 관찰 → 문제 정의·Mom Test·요구 ID 정의 | `UnitConverter.java`, `README.md` | (대화 산출) · `docs/01_UnitConverterRequirements.txt` |
| **2. 설계·AI 규칙** | 5, 8–12 | BCE·Dual-Track 설계, Cursor TDD/레이어 규칙 고정 | `01_UnitConverterRequirements.txt`, `작업시나리오/전체 작업 시나리오.md` | `.cursorrules` |
| **3. Phase 4 요구 패키지** | 13–16 | Epic→Journey→Story→Gherkin→체크리스트 정합 | `README.md`, 비율 Background | (대화 산출: US-01–07, GH #1–8) |
| **4. Phase 5 PRD** | 17–19 | 단일 진실원(PRD) 확정·Phase 4 정합 검토 | Phase 4 산출물 | `docs/00_PRD.md` |
| **5. Phase 6 README** | 20–22 | 실습자용 요약 문서, PRD 동기화 | `docs/00_PRD.md` | `README.md`, `docs/02_요구사항.md` |
| **6. GitHub 업로드** | 23–26 | Spec 단계 산출물 원격 보관·브랜치 정리 | 로컬 docs·rules | `origin/A-01-0_SPEC` (`#1단계. README 및 PRD 작성`) |
| **7. 구현 준비·검토** | 28–31 | To-Do·README↔PRD 검토·US↔To-Do 추적 | `00_PRD.md`, `03_To-Do.md` | `03_To-Do.md`, `04_README_review.md`, `05_traceability_matrix.md` |
| **8. 문서·명령 아카이브** | 27, 32–35 | 대화·Git 명령·프롬프트 목록 보존·파일명 규칙 통일 | `00_SPEC_PROMPT.md`(원본 transcript) | `00_SPEC_PROMPT.md`, `git_prompt.md`, `My_All_prompt.md`, `report/01_SPEC` |
| **9. RED·TDD** | 36–58 | 브랜치 red, RED 스텁·테스트·결함·보고 | `08_red_단계_수정전략`, `09_defect_list` | `06`–`10` docs, `report/02`, BCE 스텁, `UiBoundaryRedTest` |
| **10. GREEN·검증** | 59–88 | `green` 브랜치·TC별 최소 구현·전체 검증·아카이브 | `00_PRD.md` §3.2, `08_red_단계_수정전략` | `UnitConverter.java`, `docs/11_GREEN_전체_검증_결과.md`, `report/03` |

### No별 한 줄 의도·참조

| No | 의도 | 참조 |
|:---:|------|------|
| 1 | 원격 저장소와 로컬 워크스페이스 연결 | `https://github.com/antihu99/UnitConverter_01.git` |
| 2 | Git 연결 과정을 실습 시나리오로 문서화 | Turn 1 대화 |
| 3, 6 | 문제 정의(관찰·Why×3·Invariant) — 구현 전 사고 고정 | `@UnitConverter.java` |
| 4 | README → 요구 ID 정의서 추출 | `@README.md` |
| 5 | 실습 시나리오·요구서 → AI 코딩 규칙 | `@작업시나리오/전체 작업 시나리오.md`, `@docs/01_UnitConverterRequirements.txt` |
| 7 | 학습자 행동 기반 인터뷰 질문(Mom Test) | 프로젝트 맥락 |
| 8 | BCE·계약·TC 설계(코드 없음) | `@UnitConverter.java` |
| 9–12 | `.cursorrules` 골격 → TDD → 검토 → 완성 YAML | 루트 `.cursorrules` |
| 13 | Epic/Journey/Story/Gherkin/체크리스트 패키지 | `@UnitConverter.java` |
| 14 | 사용자 여정 스토리보드(계약·TDD 학습 강조) | `@UnitConverter.java` |
| 15 | Gherkin 8시나리오(NEG·표현 계약 포함) | `README` 비율 |
| 16 | Level 5 체크리스트 정화·추적 ID | Phase 4 산출물 |
| 17 | PRD 섹션별 분할 작성 + 정합성 검토 | Phase 4 |
| 18 | (17에 포함) PRD §1–2, §3, 정합 리포트 | Phase 4 |
| 19 | PRD 파일 생성 | 위 PRD 초안 |
| 20–21 | README 템플릿(Phase 5 PRD 기반) | `docs/00_PRD.md` |
| 22 | README 갱신 + 구 README 이관 | `@README.md` → `docs/02_요구사항.md` |
| 23–24 | GitHub 커밋·전체 파일 푸시 | 로컬 변경분 |
| 25 | 원격 브랜치 목록 확인 | `origin` |
| 26 | `A-01-0_SPEC`에 스쿼시 업로드 | 전체 Spec 산출물 |
| 27 | Agent 대화·사용자 프롬프트 아카이브 | agent-transcripts |
| 28 | v1.0 구현 To-Do( PRD F/AC/RG 기반) | `docs/00_PRD.md` §3, §7 |
| 29 | README↔PRD 갭 분석(보고만) | `README.md`, `00_PRD.md` |
| 30 | 검토 결과 파일화 | No.29 산출 |
| 31 | US↔To-Do 추적 매트릭스·누락/고아 | Phase 4 US, `03_To-Do.md`, `00_PRD.md` |
| 32 | docs 파일명 00–05 통일 | `docs/` 생성 순 |
| 33 | 대화에서 git/gh 명령만 추출 | `00_SPEC_PROMPT.md`, `00_git-github-로컬연결.md` |
| 34 | 전체 대화 덮어쓰기 + `00_SPEC_PROMPT.md` | `Agent_대화_전체.md` → rename |
| 35 | 본 파일 흐름 요약 추가 | `My_All_prompt.md` |
| 36–41 | A-01 merge·stash·red 브랜치 | GitHub `A-01`, `origin/red` |
| 42–44 | Git/TDD 전략 문서 | `docs/06_전략_RED.md` |
| 45–48 | test_plan·README RED To-Do | `07_test_plan.md`, `README.md` |
| 49–54 | RED 테스트·결함 문서 | `09_defect_list`, `08_red_단계_수정전략` |
| 55–56 | Dual-Track RED 명세·JUnit 스켈레톤 | `10_RED_듀얼트랙_테스트_명세`, `fail("RED")` |
| 57–58 | RED 보고·prompting 아카이브 | `report/02`, `02_RED_대화_전체.md` |
| 59–68 | Git 동기화·`green` 생성·SPEC 보고 재정리 | `origin/A-01`, `report/01_SPEC` |
| 69–77 | TC-B-01~03, TC-A-02~03 단계별 GREEN·커밋 | `UnitConverterTest.java`, `UnitConverter.java` |
| 78–85 | TC 일괄 GREEN·푸시·전체 `mvn test`·JaCoCo | `docs/11_GREEN_전체_검증_결과.md` |
| 86–88 | GREEN 대화·프롬프트·git·보고 아카이브 | `03_GREEN_대화_전체.md`, `git_prompt.md` §11 |

### 동기화 규칙 (프롬프트 흐름에서 반복된 전제)

| 순서 | 규칙 |
|------|------|
| 1 | **계약 변경:** `00_PRD.md` → `README.md` → Gherkin/TC |
| 2 | **구현 전 문서:** Phase 4 → Phase 5 PRD → Phase 6 README → Phase 6 To-Do |
| 3 | **코드 금지 구간:** No.3–22, 28–31, 29(보고만) — 설계·문서·추적만 |
| 4 | **회귀 기준:** PRD §7.2 RG-01–06, Gherkin #1–8, DT-02–05, IT-OK-01 |

### 현재 문서 맵 (참조용)

| 경로 | 역할 |
|------|------|
| `docs/00_PRD.md` | 계약·AC·회귀 **단일 진실원** |
| `docs/01_UnitConverterRequirements.txt` | FR/BR/QR/AR ID |
| `docs/02_요구사항.md` | 초기 README·Activities 원문 |
| `docs/03_To-Do.md` | v1.0 구현 작업 목록 |
| `docs/04_README_review.md` | README vs PRD 검토 |
| `docs/05_traceability_matrix.md` | US-01–07 ↔ To-Do |
| `README.md` | 실습자용 요약 |
| `.cursorrules` | AI·TDD·BCE·커버리지 |
| `작업시나리오/00_git-github-로컬연결.md` | Git 연결 절차 |
| `작업시나리오/전체 작업 시나리오.md` | 6h Activities·브랜치 전략 |
| `prompting/00_SPEC_PROMPT.md` | Agent 전체 대화 (Spec) |
| `prompting/02_RED_대화_전체.md` | Agent 대화 (RED) |
| `prompting/03_GREEN_대화_전체.md` | Agent 대화 (GREEN) |
| `prompting/git_prompt.md` | Git/GitHub 명령 모음 |
| `prompting/My_All_prompt.md` | 사용자 프롬프트만 (본 파일) |
| `docs/11_GREEN_전체_검증_결과.md` | GREEN 전 TC·커버리지 검증 |
| `report/03_GREEN_단계_작업보고서.md` | GREEN 단계 보고 |

### Markdown 표기 (GitHub 취소선 방지)

| 사용 | 금지 | 이유 |
|------|------|------|
| 범위 `DT-02 – DT-05` | `DT-02 ~ DT-05` | `~` 가 취소선으로 렌더될 수 있음 |
| 범위 `DEF-001–031` | `DEF-001–031` | 동일 |
| 의도적 삭제선 | (남용 금지) | `~~텍스트~~` 만 취소선 |

---

*총 사용자 프롬프트: **88개** (No.1–88). Spec 1–35 · RED 36–58 · GREEN 59–88. GREEN 후 `mvn test` 31건 PASS.*