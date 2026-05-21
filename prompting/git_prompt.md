# Git / GitHub 명령어 모음 (Agent 대화 추출)

| 항목 | 내용 |
|------|------|
| 출처 | [00_SPEC_PROMPT.md](./00_SPEC_PROMPT.md), [02_RED_대화_전체.md](./02_RED_대화_전체.md) · Cursor agent-transcripts |
| 저장일 | 2026-05-20 (Spec) · 2026-05-21 (RED) |
| 원격 저장소 | https://github.com/antihu99/UnitConverter_01.git |
| 로컬 경로 | `d:\Vs_workplace\Java_project\UnitConverter_01` |

> Agent가 실행·제안한 **git** / **gh** 명령만 정리했습니다. (`Get-ChildItem` 등 비-Git 명령은 제외)

---

## 1. GitHub 원격 ↔ 로컬 연결 (Turn 1–7)

### 사전 확인

```powershell
Set-Location "d:\Vs_workplace\Java_project\UnitConverter_01"
git status 2>&1
git remote -v 2>&1
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01" && git status 2>&1; git remote -v 2>&1
```

### 클론

```powershell
Set-Location "d:\Vs_workplace\Java_project\UnitConverter_01"
git clone https://github.com/antihu99/UnitConverter_01.git .
```

### 연결 확인

```powershell
Set-Location "d:\Vs_workplace\Java_project\UnitConverter_01"
git remote -v
git branch -vv
git log --oneline -3
```

### 일반 워크플로 (Agent 안내)

```powershell
git pull          # 원격 변경사항 받기
git add .
git commit -m "메시지"
git push          # 원격에 올리기
```

---

## 2. PRD·README 업로드 — 브랜치 `A-01-SPEC` (Turn 75–81)

### 상태 확인

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01" && git status && git remote -v && git branch -a && git log -3 --oneline
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git status; git remote -v; git branch -a; git log -3 --oneline
```

### 스테이징·커밋·푸시

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git add README.md docs/; git status; git diff --cached --stat
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git commit -m "#1단계. PRD 및 README 파일 작성"; git push -u origin A-01-SPEC
```

### 커밋 확인

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git status; git log -1 --format="%h %s"
```

---

## 3. 나머지 파일 전체 업로드 — `A-01-SPEC` (Turn 82–87)

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git status; git ls-files --others --exclude-standard
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git add -A; git status; git diff --cached --stat
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git commit -m "docs: .cursorrules 및 작업시나리오 추가"; git push origin A-01-SPEC
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git status; git ls-tree -r --name-only HEAD
```

---

## 4. GitHub 브랜치 목록 조회 (Turn 88–94)

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git fetch origin; git branch -a; git ls-remote --heads origin
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git ls-remote --heads https://github.com/antihu99/UnitConverter_01.git
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git rev-parse origin/A-01-SPEC 2>$null; git log origin/A-01-SPEC -1 --oneline 2>$null
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git fetch origin --prune; git branch -r -v; git ls-remote --heads origin
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git branch -v
```

### `A-01-SPEC` 재푸시 (Agent 제안)

```bash
git push -u origin A-01-SPEC
```

---

## 5. `A-01-0_SPEC`에 스쿼시 업로드 (Turn 95–100)

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git status; git branch -v
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git checkout -B A-01-0_SPEC origin/A-01-0_SPEC; git reset --hard A-01-SPEC; git reset --soft 1aab698; git status; git diff --cached --stat
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git commit -m "#1단계. README 및 PRD 작성"; git push origin A-01-0_SPEC
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; git ls-remote --heads origin; git log -1 --oneline A-01-0_SPEC
```

---

## 6. GitHub CLI (`gh`) — 브랜치 API 조회 (Turn 91)

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"; gh api repos/antihu99/UnitConverter_01/branches --jq ".[].name"
```

---

## 7. 참고 — GitHub 웹 URL (명령 아님)

| 용도 | URL |
|------|-----|
| 저장소 | https://github.com/antihu99/UnitConverter_01.git |
| 브랜치 목록 | https://github.com/antihu99/UnitConverter_01/branches |
| PR 생성 (`A-01-SPEC`) | https://github.com/antihu99/UnitConverter_01/pull/new/A-01-SPEC |
| 트리 (`A-01-0_SPEC`) | https://github.com/antihu99/UnitConverter_01/tree/A-01-0_SPEC |
| 트리 (`A-01-SPEC`, 당시) | https://github.com/antihu99/UnitConverter_01/tree/A-01-SPEC |

---

## 8. 커밋 메시지 요약 (대화에서 사용된 것)

| 메시지 | 브랜치 |
|--------|--------|
| `#1단계. PRD 및 README 파일 작성` | `A-01-SPEC` |
| `docs: .cursorrules 및 작업시나리오 추가` | `A-01-SPEC` |
| `#1단계. README 및 PRD 작성` | `A-01-0_SPEC` (스쿼시 1커밋, `fd2d106`) |

---

## 9. RED 단계 — `A-01` 동기화·`red` 브랜치 (2026-05-21)

> GitHub에서 `A-01-0_SPEC` → `A-01` PR merge(#2) 후, 로컬·`red` 작업 브랜치 정리.

### 9.1 원격 반영 후 `A-01` 로컬 동기화

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git fetch origin --prune
git branch -r
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git checkout A-01
git pull origin A-01
```

### 9.2 merge 완료된 로컬 브랜치 삭제

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git branch -d A-01-0_SPEC
```

### 9.3 stash 복원 (작업시나리오 수정분)

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git stash push -m "temp: before merge A-01-0_SPEC into A-01" -- "작업시나리오/전체 작업 시나리오.md"
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git checkout A-01-0_SPEC
git stash pop
```

### 9.4 현재 브랜치·상태 확인

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git branch --show-current
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git status -sb
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git branch -vv
```

### 9.5 작업시나리오 수정 — `A-01-0_SPEC` push (merge 전 시점)

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git add "작업시나리오/전체 작업 시나리오.md"
git commit -m "#1단계. 전체 작업 시나리오에 추천 브랜치 구조 추가"
git push origin A-01-0_SPEC
```

### 9.6 `red` 브랜치 생성·GitHub 동기화

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git checkout A-01
git pull origin A-01
git checkout -b red
git push -u origin red
```

```powershell
cd "d:\Vs_workplace\Java_project\UnitConverter_01"
git status -sb
git branch -vv
```

### 9.7 RED 단계 참고 (GitHub 웹 — PR merge)

| 항목 | 내용 |
|------|------|
| PR | Merge pull request #2 — `A-01-0_SPEC` → `A-01` |
| merge 후 커밋 | `a8b81f3` (로컬 `A-01` pull 기준) |
| 삭제된 원격 브랜치 | `origin/A-01-0_SPEC` (`git fetch --prune` 후 반영) |
| 현재 작업 브랜치 | `red` (`origin/red` 추적) |

---

## 10. RED 단계 커밋 메시지 (추가)

| 메시지 | 브랜치 | 비고 |
|--------|--------|------|
| `#1단계. 전체 작업 시나리오에 추천 브랜치 구조 추가` | `A-01-0_SPEC` | merge 전 push |
| Merge pull request #2 from antihu99/A-01-0_SPEC | `A-01` | GitHub PR merge |

---

*상세 절차·터미널 출력은 [작업시나리오/00_git-github-로컬연결.md](../작업시나리오/00_git-github-로컬연결.md) · RED 대화 [02_RED_대화_전체.md](./02_RED_대화_전체.md) 참고.*
---

## 11. GREEN 단계 — 브랜치·커밋·푸시 (2026-05-21)

> GREEN 세션 Agent 실행 `git` 명령만 (중복 제거).

### 11.1

```powershell
git branch --show-current
```

### 11.2

```powershell
git status -sb && git remote -v && git branch -vv
```

### 11.3

```powershell
git fetch origin 2>&1
```

### 11.4

```powershell
git status -sb; git remote -v; git branch -vv
```

### 11.5

```powershell
git log --oneline -3 HEAD; git log --oneline -3 origin/red 2>&1
```

### 11.6

```powershell
git status; git rev-list --left-right --count origin/red...red
```

### 11.7

```powershell
git pull origin red; git push -u origin red
```

### 11.8

```powershell
git status -sb; git branch -vv
```

### 11.9

```powershell
git fetch origin --prune; git branch -a -vv
```

### 11.10

```powershell
git status -sb; git log --oneline -5 HEAD; Write-Host "--- origin/red ---"; git log --oneline -5 origin/red 2>&1; Write-Host "--- origin/main ---"; git log --oneline -5 origin/main 2>&1
```

### 11.11

```powershell
git fetch origin --prune
```

### 11.12

```powershell
git branch --show-current; git status -sb
```

### 11.13

```powershell
git rev-list --left-right --count origin/red...red; git log --oneline -3 red; git log --oneline -3 origin/red
```

### 11.14

```powershell
git branch -a -vv
```

### 11.15

```powershell
git status; git ls-remote --heads origin
```

### 11.16

```powershell
git log --oneline --graph -10 origin/A-01; Write-Host "---"; git log --oneline --graph -10 origin/red; Write-Host "---"; git merge-base origin/A-01 origin/red
```

### 11.17

```powershell
git fetch origin --prune; git reset --hard origin/red; git clean -fd; git status -sb; git log --oneline -1 HEAD; git log --oneline -1 origin/red
```

### 11.18

```powershell
git fetch origin --prune; git checkout A-01; git reset --hard origin/A-01; git clean -fd; git status -sb; git log --oneline -3 HEAD
```

### 11.19

```powershell
git branch -u origin/A-01 A-01; git branch -vv
```

### 11.20

```powershell
git branch --show-current; git branch -a | Select-String "red"
```

### 11.21

```powershell
git branch -d red; git push origin --delete red
```

### 11.22

```powershell
git fetch origin --prune; git branch -a
```

### 11.23

```powershell
git branch -a | Select-String -Pattern "red" -CaseSensitive:$false; git ls-remote --heads origin red 2>&1
```

### 11.24

```powershell
git branch --show-current; git status -sb; git branch -vv; git ls-remote --heads origin
```

### 11.25

```powershell
git branch --show-current; git branch -d A-01-SPEC; git branch -a
```

### 11.26

```powershell
git branch -D A-01-SPEC; git branch -a
```

### 11.27

```powershell
git fetch origin; git checkout A-01; git pull origin A-01; git checkout -b green; git push -u origin green; git branch -vv; git status -sb
```

### 11.28

```powershell
git log --oneline --graph -15 A-01
```

### 11.29

```powershell
git branch --show-current; git log --oneline a8b81f3^..a8b81f3; git show --stat --oneline fd2d106; git show --stat --oneline 936f075; git show --stat --oneline df08aa0
```

### 11.30

```powershell
git status -sb; git branch --show-current
```

### 11.31

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-B-01 GREEN 완료 feat(green): implement convert meter to feet (delta 1e-5)"; git push origin green; git status -sb
```

### 11.32

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-A-02 GREEN 완료 feat(green): validate missing colon"; git push origin green; git log -1 --oneline; git status -sb
```

### 11.33

```powershell
git push origin green; git status -sb; git log -2 --oneline
```

### 11.34

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-B-02 GREEN 완료 feat(green): implement convert meter to yard (delta 1e-5)"; git push origin green; git log -1 --oneline; git status -sb
```

### 11.35

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-A-03 GREEN 완료 feat(green): validate negative value"; git push origin green; git log -1 --oneline; git status -sb
```

### 11.36

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-B-03 GREEN 완료 feat(green): feet to meter reverse"; git push origin green; git log -1 --oneline
```

### 11.37

```powershell
git status -sb; git log -3 --oneline; git log origin/green -1 --oneline 2>$null
```

### 11.38

```powershell
git push origin green; git status -sb
```

### 11.39

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-A-04 GREEN 완료 feat(green): validate unknown unit"; git push origin green
```

### 11.40

```powershell
git push origin green
```

### 11.41

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-B-04~05 GREEN 완료 feat(green): convertAll and registerUnit"
```

### 11.42

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-A-01,06,07 GREEN 완료 feat(green): boundary happy path"
```

### 11.43

```powershell
git add src/main/java/UnitConverter.java src/test/java/UnitConverterTest.java; git commit -m "TC-B-06~07 GREEN 완료 feat(green): loadConfig with fallback"; git log -6 --oneline; git status -sb
```

### 11.44

```powershell
git show --stat HEAD; git show --stat 045508c
```

### 11.45

```powershell
git diff src/main/java/UnitConverter.java; git log -1 --name-only 045508c
```

### 11.46

```powershell
git show HEAD:src/main/java/UnitConverter.java | Select-String -Pattern "loadConfig"
```

### 11.47

```powershell
git push origin green; git log origin/green -5 --oneline; git status -sb
```

### 11.48

```powershell
git log -1 --oneline; git branch --show-current
```

---

## 12. REFACTORING 단계 — 브랜치·푸시·PR (2026-05-21)

> REFACTORING 세션 Agent 실행 `git` / `gh` 명령만 (`mvn` 제외).

### 12.1

```powershell
git checkout A-01
git pull origin A-01
```

### 12.2

```powershell
git checkout -b refactoring
```

### 12.3

```powershell
git push -u origin refactoring
```

### 12.4

```powershell
git fetch origin --prune
git branch -vv
git status -sb
```

### 12.5

```powershell
git rev-parse HEAD
git rev-parse origin/refactoring
git rev-parse origin/A-01
```

### 12.6

```powershell
git rev-list --left-right --count origin/refactoring...refactoring
```

### 12.7

```powershell
git push origin refactoring
```

### 12.8

```powershell
git log --oneline A-01..HEAD
```

### 12.9

```powershell
git diff A-01...refactoring --stat
```

### 12.10

```powershell
gh pr create --base A-01 --head refactoring --title "REFACTORING 단계" --body "..."
```

### 12.11 REFACTORING 커밋 메시지 (참고)

| 커밋 | 메시지 (요약) |
|------|----------------|
| `07a83f0` | `refactor(boundary): 순번2 R-U3 App.main 출력을 PlainOutputRenderer로 위임` |
| `8434a31` | `refactor(boundary): 순번3 R-U3 renderPlain PlainOutputRenderer 위임` |
| `f34999a` | `refactor(domain): 순번1,6 R-L1 R-L4 파사드 BCE 위임` |
| `216e13a` | `refactor(control): 순번2 ConvertLengthUseCase 추출` |
| `ff813ca` | `refactor(domain): 순번7 RoundingPolicy` |
| `5a598f6` | `test: Golden Master 회귀 테스트 및 생성 스크립트` |
| `b4670ba` | `docs: 12_리팩토링_계획서 및 README Golden Master 섹션` |
