# 00. GitHub 원격 저장소와 로컬 연결

- **작업일**: 2026-05-20
- **프로젝트**: UnitConverter_01
- **로컬 경로**: `d:\Vs_workplace\Java_project\UnitConverter_01`
- **원격 저장소**: https://github.com/antihu99/UnitConverter_01.git

---

## 1. 사용자 요청

> https://github.com/antihu99/UnitConverter_01.git 와 local 을 연결해줘

---

## 2. 사전 상태 확인

### 2.1 Git 저장소 여부

```powershell
Set-Location "d:\Vs_workplace\Java_project\UnitConverter_01"
git status
```

**결과**

```
fatal: not a git repository (or any of the parent directories): .git
```

→ 로컬 폴더는 Git 저장소가 아님.

### 2.2 로컬 폴더 내용

```powershell
Get-ChildItem -Force
```

**결과**

- (출력 없음) → 폴더가 비어 있음.

---

## 3. 수행 작업

로컬 폴더가 비어 있고 Git 초기화가 되어 있지 않아, **원격 저장소를 현재 폴더에 클론**하는 방식으로 연결함.

```powershell
Set-Location "d:\Vs_workplace\Java_project\UnitConverter_01"
git clone https://github.com/antihu99/UnitConverter_01.git .
```

**결과**

```
Cloning into '.'...
```

→ 클론 성공.

---

## 4. 연결 결과 확인

```powershell
git remote -v
git branch -vv
git log --oneline -3
Get-ChildItem
```

### 4.1 원격(remote)

| 이름 | URL |
|------|-----|
| origin (fetch) | https://github.com/antihu99/UnitConverter_01.git |
| origin (push)  | https://github.com/antihu99/UnitConverter_01.git |

### 4.2 브랜치

| 브랜치 | 추적 | 최신 커밋 |
|--------|------|-----------|
| main | origin/main | 1aab698 |

### 4.3 커밋 이력 (최근 3건)

```
1aab698 #0_Main Branch 초기화
22f718e Initial commit
```

### 4.4 로컬 파일

| 파일 | 설명 |
|------|------|
| README.md | 프로젝트 요구사항 및 활동 안내 |
| UnitConverter.java | 기본 단위 변환 코드 |
| unit-converter.jpg | README용 이미지 |

---

## 5. 이후 사용 명령 (참고)

```powershell
git pull          # 원격 변경사항 받기
git add .
git commit -m "메시지"
git push          # 원격에 올리기
```

> `git push` 시 GitHub 인증(토큰 또는 SSH)이 필요할 수 있음.

---

## 6. 터미널 대화 기록 (Cursor Agent)

### Turn 1

**User**

```
https://github.com/antihu99/UnitConverter_01.git 와 local 을 연결해줘
```

**Agent 요약**

- 로컬 폴더가 비어 있고 `.git`이 없음을 확인.
- `git clone https://github.com/antihu99/UnitConverter_01.git .` 실행.
- `origin` remote 및 `main` 브랜치(`origin/main` 추적) 설정 완료.

### Turn 2

**User**

```
terminal 에서 대화한 내용을 작업시나리오 폴더에 00 으로 시작하는 파일로 저장해줘
```

**Agent 요약**

- `작업시나리오/00_git-github-로컬연결.md` 파일로 본 대화 및 터미널 작업 내역 저장.

---

## 7. 작업 결과

| 항목 | 상태 |
|------|------|
| 로컬 ↔ GitHub 연결 | 완료 |
| 원격 이름 | `origin` |
| 기본 브랜치 | `main` |
| 작업시나리오 문서화 | 완료 (본 파일) |
