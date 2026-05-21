# Git / GitHub 명령어 모음 (Agent 대화 추출)

| 항목 | 내용 |
|------|------|
| 출처 | [00_SPEC_PROMPT.md](./00_SPEC_PROMPT.md) · Cursor agent-transcripts |
| 저장일 | 2026-05-20 |
| 원격 저장소 | https://github.com/antihu99/UnitConverter_01.git |
| 로컬 경로 | `d:\Vs_workplace\Java_project\UnitConverter_01` |

> Agent가 실행·제안한 **git** / **gh** 명령만 정리했습니다. (`Get-ChildItem` 등 비-Git 명령은 제외)

---

## 1. GitHub 원격 ↔ 로컬 연결 (Turn 1~7)

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

## 2. PRD·README 업로드 — 브랜치 `A-01-SPEC` (Turn 75~81)

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

## 3. 나머지 파일 전체 업로드 — `A-01-SPEC` (Turn 82~87)

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

## 4. GitHub 브랜치 목록 조회 (Turn 88~94)

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

## 5. `A-01-0_SPEC`에 스쿼시 업로드 (Turn 95~100)

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

*상세 절차·터미널 출력은 [작업시나리오/00_git-github-로컬연결.md](../작업시나리오/00_git-github-로컬연결.md) 참고.*
