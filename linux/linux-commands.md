# 🎯 Linux 명령어 완전 정리 가이드

제공하신 문서의 핵심 내용을 체계적으로 정리했습니다.

---

## 📋 목차

1. [grep 명령어](#1-grep-명령어)
2. [find 명령어](#2-find-명령어)
3. [awk 명령어](#3-awk-명령어)
4. [sed 명령어](#4-sed-명령어)
5. [xargs 명령어](#5-xargs-명령어)
6. [명령어 조합 패턴](#6-명령어-조합-패턴)
7. [필수 보조 명령어](#7-필수-보조-명령어)
8. [고급 활용 패턴](#8-고급-활용-패턴)
9. [문제 해결 가이드](#9-문제-해결-가이드)
10. [보안 및 주의사항](#10-보안-및-주의사항)
11. [치트 시트](#11-치트-시트)

---

## 1. grep 명령어

### 기본 문법

```bash
grep [옵션] "검색어" 파일명
```

### 주요 옵션

| 옵션 | 의미 | 예시 |
| --- | --- | --- |
| `-i` | 대소문자 무시 | `grep -i "error" file.log` |
| `-n` | 라인 번호 표시 | `grep -n "error" file.log` |
| `-A 5` | 매칭 후 5줄 출력 | `grep -A 5 "error" file.log` |
| `-B 5` | 매칭 전 5줄 출력 | `grep -B 5 "error" file.log` |
| `-C 5` | 전후 5줄 출력 | `grep -C 5 "error" file.log` |
| `--color=auto` | 매칭 텍스트 색상 표시 | `grep --color=auto "error" file.log` |

### 추가 주요 옵션

| 옵션 | 의미 | 예시 |
| --- | --- | --- |
| `-r` / `-R` | 하위 디렉토리 재귀 검색 | `grep -r "error" ./logs` |
| `-v` | 매칭 제외 (역검색) | `grep -v "DEBUG" app.log` |
| `-c` | 매칭된 라인 수만 출력 | `grep -c "ERROR" app.log` |
| `-l` | 매칭된 파일명만 출력 | `grep -l "error" *.log` |
| `-w` | 단어 단위로 매칭 | `grep -w "app" log.txt` |
| `-o` | 매칭된 부분만 출력 | `grep -o "[0-9]\+" data.txt` |
| `-P` | Perl 정규식 사용 | `grep -P "\d{3}-\d{4}" phone.txt` |
| `-F` | 고정 문자열 검색 (빠름) | `grep -F "user@" emails.txt` |
| `-E` | 확장 정규식 사용 | `grep -E "error\|exception" log.txt` |

### 실무 예시

```bash
# ERROR 로그 전후 5줄씩 확인
grep -i "error" -A 5 -B 5 -n --color=auto application.log

# 여러 키워드 동시 검색
grep -iE "error|exception|fatal" application.log

# 특정 시간대만 검색
grep "2025-09-30 14:" application.log

# 재귀 검색 (모든 하위 디렉토리)
grep -r "TODO" ./src

# 특정 파일 타입만 검색
grep -r --include="*.java" "class.*Exception" ./src

# 특정 디렉토리 제외
grep -r --exclude-dir={node_modules,dist,build} "import" .

# 파일명만 출력
grep -rl "error" ./logs

# 매칭 개수만 확인
grep -c "ERROR" application.log

# DEBUG 로그 제외
grep -v "DEBUG" application.log
```

### 정규식 패턴 예시

```bash
# IP 주소 찾기
grep -oP '\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}' access.log

# 이메일 주소 추출
grep -oE '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}' file.txt

# URL 패턴 매칭
grep -oP 'https?://[^\s]+' log.txt

# 전화번호 패턴
grep -P '\d{3}-\d{3,4}-\d{4}' contacts.txt

# 날짜 패턴 (YYYY-MM-DD)
grep -P '\d{4}-\d{2}-\d{2}' log.txt
```

### 자주 하는 실수

```bash
# ❌ 잘못된 예: 정규식 메타문자 이스케이프 누락
grep "192.168.1.1" log.txt  # 점(.)이 모든 문자와 매칭됨

# ✅ 올바른 예
grep "192\.168\.1\.1" log.txt
# 또는
grep -F "192.168.1.1" log.txt  # 고정 문자열로 검색
```

### 성능 팁

- `-F` 옵션: 정규식이 필요 없으면 고정 문자열 검색으로 속도 향상
- `-l` 옵션: 파일명만 필요하면 첫 매칭 후 다음 파일로 이동
- `--include` / `--exclude`: 불필요한 파일 제외로 성능 향상

```bash
# 빠른 검색: .log 파일만 대상
grep -r --include="*.log" "error" ./logs

# node_modules 제외
grep -r --exclude-dir="node_modules" "import" .
```

---

## 2. find 명령어

### 기본 문법

```bash
find [경로] [옵션] [조건]
```

### 주요 옵션

| 옵션 | 의미 | 예시 |
| --- | --- | --- |
| `-type f` | 파일만 검색 | `find . -type f` |
| `-type d` | 디렉토리만 검색 | `find . -type d` |
| `-name "*.log"` | 이름 패턴 (대소문자 구분) | `find . -name "*.log"` |
| `-iname "*.log"` | 이름 패턴 (대소문자 무시) | `find . -iname "*.LOG"` |
| `-mtime -7` | 7일 이내 수정 | `find . -mtime -7` |
| `-mtime +30` | 30일 이전 수정 | `find . -mtime +30` |
| `-size +10M` | 10MB 이상 | `find . -size +10M` |
| `-maxdepth 2` | 깊이 제한 | `find . -maxdepth 2` |

### 추가 주요 옵션

| 옵션 | 의미 | 예시 |
| --- | --- | --- |
| `-exec 명령 {} \;` | 각 파일마다 명령 실행 | `find . -name "*.log" -exec gzip {} \;` |
| `-exec 명령 {} +` | 여러 파일 묶어서 실행 | `find . -name "*.log" -exec gzip {} +` |
| `-user username` | 특정 사용자 소유 파일 | `find . -user nginx` |
| `-perm 644` | 특정 권한의 파일 | `find . -perm 644` |
| `-empty` | 빈 파일/디렉토리 | `find . -type f -empty` |
| `-newer file` | 특정 파일보다 최신 | `find . -newer reference.txt` |
| `-amin -30` | 30분 이내 접근 | `find . -amin -30` |
| `-ctime +7` | 7일 이전 생성 | `find . -ctime +7` |
| `-delete` | 찾은 파일 삭제 | `find . -name "*.tmp" -delete` |

### -exec vs xargs 비교

```bash
# -exec 방식 (각 파일마다 명령 실행, 느림)
find . -name "*.log" -exec grep "error" {} \;

# -exec + 방식 (여러 파일 묶어서 실행, 빠름)
find . -name "*.log" -exec grep "error" {} +

# xargs 방식 (가장 유연, 빠름)
find . -name "*.log" | xargs grep "error"

# 공백 포함 파일명 안전 처리
find . -name "*.log" -print0 | xargs -0 grep "error"
```

### 실무 예시

```bash
# 특정 파일 찾아서 grep
find . -name "*.log" | xargs grep "error"

# 오래된 로그 파일 삭제
find ./logs -name "*.log" -mtime +30 -delete

# 큰 파일 찾기
find . -type f -size +100M

# 특정 폴더 제외
find . -name "*.js" -not -path "*/node_modules/*"

# 빈 디렉토리 찾아서 삭제
find . -type d -empty -delete

# 특정 권한의 파일 찾아서 수정
find . -type f -perm 777 -exec chmod 644 {} +

# 최근 24시간 내 변경된 설정 파일 백업
find /etc -name "*.conf" -mtime -1 -exec cp {} /backup/ \;

# 용량이 0인 로그 파일 찾기
find /var/log -type f -size 0 -name "*.log"
```

### 복합 조건 검색

```bash
# AND 조건: .log 파일이면서 10MB 이상
find . -name "*.log" -and -size +10M

# OR 조건: .log 또는 .txt 파일
find . \( -name "*.log" -or -name "*.txt" \)

# NOT 조건: .git 디렉토리 제외
find . -type f -not -path "*/.git/*"

# 복합 조건: 7일 이내 수정된 .java 파일 중 100줄 이상
find . -name "*.java" -mtime -7 -exec wc -l {} \; | awk '$1 > 100'
```

### 시간 옵션 정확한 이해

```bash
# -mtime +7  : 정확히 7일(7*24시간) 이전에 수정된 파일
# -mtime -7  : 7일 이내에 수정된 파일
# -mtime 7   : 정확히 7일 전에 수정된 파일

# 더 정밀한 시간 검색 (분 단위)
find . -mmin -30  # 30분 이내 수정

# 특정 파일보다 최신 파일 찾기
touch -t 202501010000 reference
find . -newer reference
```

### 자주 하는 실수

```bash
# ❌ 잘못된 예: -delete 사용 시 조건 확인 안 함
find . -delete -name "*.tmp"  # 모든 파일 삭제 위험!

# ✅ 올바른 예: 조건을 먼저, -delete는 마지막
find . -name "*.tmp" -delete

# ❌ 잘못된 예: -exec 세미콜론 이스케이프 누락
find . -name "*.log" -exec rm {} ;

# ✅ 올바른 예
find . -name "*.log" -exec rm {} \;
```

---

## 3. awk 명령어

### 기본 개념

- **목적**: 컬럼 기반 데이터 처리 (추출, 계산, 집계)
- **특징**: 공백/탭으로 자동 컬럼 분리

### 기본 문법

```bash
awk '패턴 { 액션 }' 파일명
```

### 내장 변수

| 변수 | 의미 | 예시 |
| --- | --- | --- |
| `$0` | 전체 라인 | `awk '{print $0}'` |
| `$1, $2, $3` | 1, 2, 3번째 컬럼 | `awk '{print $1, $3}'` |
| `$NF` | 마지막 컬럼 | `awk '{print $NF}'` |
| `NR` | 현재 라인 번호 | `awk '{print NR, $0}'` |
| `NF` | 현재 라인의 컬럼 개수 | `awk '{print NF}'` |
| `FS` | 필드 구분자 (입력) | `awk 'BEGIN {FS=","}'` |
| `OFS` | 필드 구분자 (출력) | `awk 'BEGIN {OFS="\t"}'` |
| `RS` | 레코드 구분자 (입력) | `awk 'BEGIN {RS=";"}'` |
| `ORS` | 레코드 구분자 (출력) | `awk 'BEGIN {ORS="\n\n"}'` |

### BEGIN/END 블록

```bash
# BEGIN: 파일 처리 전 실행
awk 'BEGIN {print "=== 로그 분석 시작 ==="} {print $0}' log.txt

# END: 파일 처리 후 실행
awk '{sum+=$3} END {print "총합:", sum}' data.txt

# BEGIN과 END 함께 사용
awk 'BEGIN {count=0} /ERROR/ {count++} END {print "에러 수:", count}' app.log

# 구분자 설정
awk 'BEGIN {FS=":"; OFS="\t"} {print $1, $3}' /etc/passwd
```

### 패턴 매칭

```bash
# 정규식 패턴 매칭
awk '/^ERROR/ {print $0}' log.txt

# 특정 컬럼에 패턴 매칭
awk '$3 ~ /ERROR|FATAL/ {print $1, $3}' log.txt

# 패턴 제외 (NOT)
awk '$3 !~ /DEBUG/ {print $0}' log.txt

# 범위 패턴: START부터 END까지
awk '/START/,/END/ {print $0}' log.txt

# 라인 번호 범위
awk 'NR >= 10 && NR <= 20' file.txt
```

### 비교 연산자

| 연산자 | 의미 | 예시 |
| --- | --- | --- |
| `==` | 같음 | `awk '$3 == "ERROR"'` |
| `!=` | 다름 | `awk '$3 != "DEBUG"'` |
| `>` | 크다 | `awk '$5 > 1000'` |
| `>=` | 크거나 같다 | `awk '$5 >= 500'` |
| `<` | 작다 | `awk '$4 < 100'` |
| `<=` | 작거나 같다 | `awk '$4 <= 50'` |
| `~` | 정규식 매칭 | `awk '$3 ~ /ERROR/'` |
| `!~` | 정규식 불일치 | `awk '$3 !~ /DEBUG/'` |

### 문자열 함수

```bash
# length: 문자열 길이
awk '{if (length($0) > 100) print $0}' log.txt

# substr: 부분 문자열 추출
awk '{print substr($1, 1, 10)}' log.txt  # 첫 10자만

# split: 문자열 분리
awk '{split($5, arr, ":"); print arr[1]}' log.txt

# gsub: 전역 치환 (문자열 내에서)
awk '{gsub(/ERROR/, "CRITICAL"); print}' log.txt

# sub: 첫 번째만 치환
awk '{sub(/ERROR/, "CRITICAL"); print}' log.txt

# tolower/toupper: 대소문자 변환
awk '{print tolower($3)}' log.txt
awk '{print toupper($3)}' log.txt

# index: 문자열 위치 찾기
awk '{print index($0, "error")}' log.txt

# match: 정규식 매칭 위치
awk '{if (match($0, /[0-9]+/)) print substr($0, RSTART, RLENGTH)}' log.txt
```

### 배열 활용

```bash
# 그룹별 집계
awk '{count[$3]++} END {for (key in count) print key, count[key]}' log.txt

# 그룹별 합계
awk '{sum[$2]+=$5} END {for (key in sum) print key, sum[key]}' sales.txt

# 중복 제거
awk '!seen[$1]++' data.txt

# 가장 많이 발생한 에러 찾기
awk '/ERROR/ {count[$5]++} END {max=0; for(k in count) if(count[k]>max) {max=count[k]; key=k}; print key, max}' log.txt

# 배열 정렬 (값 기준)
awk '{count[$1]++} END {for (k in count) print count[k], k}' data.txt | sort -rn
```

### 복잡한 조건 처리

```bash
# if-else 문
awk '{if ($5 > 1000) print $1, "HIGH"; else print $1, "LOW"}' data.txt

# 다중 조건
awk '{
  if ($5 > 1000) level="HIGH"
  else if ($5 > 500) level="MEDIUM"
  else level="LOW"
  print $1, level
}' data.txt

# 논리 연산자
awk '$3 == "ERROR" && $5 > 500 {print $0}' log.txt
awk '$3 == "ERROR" || $3 == "FATAL" {print $0}' log.txt

# 복합 조건
awk 'NR > 1 && $3 == "ERROR" && $5 >= 500' log.txt
```

### 포맷팅 출력

```bash
# printf로 정렬된 출력
awk '{printf "%-20s %10s %8d\n", $1, $2, $3}' data.txt

# 소수점 제어
awk '{printf "%.2f\n", $5/1000}' data.txt

# 구분자 지정하여 출력
awk 'BEGIN {OFS=","} {print $1, $2, $3}' data.txt > output.csv

# 천 단위 구분 (숫자 포맷)
awk '{printf "%'\''d\n", $1}' numbers.txt
```

### 실무 예시

```bash
# 특정 컬럼만 추출
awk '{print $1, $3, $7}' access.log

# 조건 필터링
awk '$3 == "ERROR"' application.log

# ERROR이면서 500 에러
awk '$3 == "ERROR" && $7 == 500' access.log

# 합계 계산
awk '{sum += $5} END {print "Total:", sum}' data.txt

# 평균 계산
awk '{sum += $5; count++} END {print "Average:", sum/count}' data.txt

# 그룹별 카운트
awk '{count[$3]++} END {for (k in count) print k, count[k]}' log.txt

# CSV 처리
awk -F',' '{print $2, $4}' users.csv

# 시간대별 트래픽 분석
awk '{hour=substr($4,13,2); traffic[hour]+=$10} END {for (h in traffic) printf "%s시: %d MB\n", h, traffic[h]/1024/1024}' access.log

# 응답 시간 통계
awk '{sum+=$7; count++; if($7>max) max=$7; if(min=="" || $7<min) min=$7} END {print "평균:", sum/count, "최대:", max, "최소:", min}' response.log

# 상위 10개 IP 추출
awk '{count[$1]++} END {for (ip in count) print count[ip], ip}' access.log | sort -rn | head -10

# CSV 데이터 피벗
awk -F',' '{sales[$1]+=$3} END {for (product in sales) print product, sales[product]}' sales.csv
```

---

## 4. sed 명령어

### 기본 개념

- **목적**: 텍스트 편집 (치환, 삭제, 추가)
- **특징**: 스트림 편집기 (원본 유지)

### 기본 문법

```bash
sed 's/찾을내용/바꿀내용/플래그' 파일명
```

### 주요 명령어

| 명령 | 의미 | 예시 |
| --- | --- | --- |
| `s` | substitute (치환) | `sed 's/ERROR/CRITICAL/g'` |
| `d` | delete (삭제) | `sed '/DEBUG/d'` |
| `p` | print (출력) | `sed -n '/ERROR/p'` |
| `i` | insert (앞에 추가) | `sed '/ERROR/i\=== 경고 ==='` |
| `a` | append (뒤에 추가) | `sed '/ERROR/a\=== 확인 ==='` |
| `c` | change (라인 변경) | `sed '/ERROR/c\CRITICAL ERROR'` |
| `y` | 문자 변환 (tr과 유사) | `sed 'y/abc/ABC/'` |

### 플래그

| 플래그 | 의미 | 예시 |
| --- | --- | --- |
| `g` | 모든 매칭 변경 | `sed 's/error/ERROR/g'` |
| `I` | 대소문자 무시 | `sed 's/error/ERROR/I'` |
| `2` | n번째만 변경 | `sed 's/error/ERROR/2'` |
| `p` | 변경된 라인만 출력 | `sed -n 's/error/ERROR/p'` |

### 주소 범위 지정

```bash
# 특정 라인만 처리
sed '5s/old/new/' file.txt  # 5번째 라인만

# 라인 범위
sed '10,20s/old/new/' file.txt  # 10~20번째 라인

# 패턴부터 패턴까지
sed '/START/,/END/s/old/new/' file.txt

# 패턴부터 N줄까지
sed '/ERROR/,+5s/old/new/' file.txt  # ERROR부터 5줄

# 마지막 라인
sed '$s/old/new/' file.txt

# 특정 라인 제외
sed '5!s/old/new/' file.txt  # 5번째 라인 제외
```

### 고급 옵션

```bash
# -n: 자동 출력 억제 (p와 함께 사용)
sed -n '/ERROR/p' log.txt  # ERROR만 출력 (grep과 유사)

# -i: 파일 직접 수정
sed -i 's/old/new/g' file.txt  # 원본 변경

# -i.bak: 백업 생성하며 수정
sed -i.bak 's/old/new/g' file.txt  # file.txt.bak 생성

# -e: 여러 명령 실행
sed -e 's/ERROR/CRITICAL/g' -e 's/WARN/WARNING/g' file.log

# -f: 스크립트 파일 사용
sed -f script.sed file.txt

# -r 또는 -E: 확장 정규식
sed -E 's/(error|warning)/ALERT/g' file.txt
```

### 구분자 변경

```bash
# 기본 구분자: /
sed 's/old/new/g' file.txt

# 다른 구분자 사용 (URL 처리 시 유용)
sed 's|http://|https://|g' file.txt
sed 's#/old/path#/new/path#g' file.txt
sed 's@old@new@g' file.txt
```

### 실무 예시

```bash
# 문자열 치환
sed 's/ERROR/CRITICAL/g' application.log

# 라인 삭제
sed '/DEBUG/d' application.log

# IP 마스킹
sed 's/[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}/XXX.XXX.XXX.XXX/g' access.log

# 파일 직접 수정 (백업 생성)
sed -i.bak 's/localhost/192.168.1.100/g' config.properties

# 여러 명령 조합
sed -e 's/ERROR/CRITICAL/g' -e 's/WARN/WARNING/g' file.log

# 빈 라인 삭제
sed '/^$/d' file.txt

# 주석 라인 삭제
sed '/^#/d' file.txt

# 라인 앞에 텍스트 추가
sed 's/^/PREFIX: /' file.txt

# 라인 끝에 텍스트 추가
sed 's/$/ SUFFIX/' file.txt

# 특정 문자열 포함 라인만 출력
sed -n '/ERROR/p' log.txt

# 특정 문자열 포함 라인 삭제
sed '/DEBUG/d' log.txt

# 대소문자 변환 (첫 글자만)
sed 's/\b\(.\)/\U\1/g' file.txt
```

### 복잡한 치환 예시

```bash
# 괄호 안 내용 추출
sed 's/.*(\(.*\)).*/\1/' file.txt

# 날짜 형식 변환 (YYYY-MM-DD -> DD/MM/YYYY)
sed 's/\([0-9]\{4\}\)-\([0-9]\{2\}\)-\([0-9]\{2\}\)/\3\/\2\/\1/' dates.txt

# 전화번호 형식 변경
sed 's/\([0-9]\{3\}\)\([0-9]\{4\}\)\([0-9]\{4\}\)/\1-\2-\3/' phones.txt

# HTML 태그 제거
sed 's/<[^>]*>//g' file.html

# 연속된 공백을 하나로
sed 's/  */ /g' file.txt

# 앞뒤 공백 제거
sed 's/^[ \t]*//;s/[ \t]*$//' file.txt
```

---

## 5. xargs 명령어

### 기본 개념

- **목적**: stdin을 명령어의 인자로 변환
- **특징**: 여러 개를 묶어서 효율적으로 처리

### 기본 문법

```bash
명령어1 | xargs 명령어2
```

### 주요 옵션

| 옵션 | 의미 | 예시 |
| --- | --- | --- |
| `-n 2` | 한 번에 2개씩 처리 | `xargs -n 2 echo` |
| `-I {}` | 플레이스홀더 사용 | `xargs -I {} cp {} {}.bak` |
| `-p` | 실행 전 확인 | `xargs -p rm` |
| `-t` | 실행 명령어 출력 | `xargs -t grep "error"` |
| `-0` | null 구분자 (공백 파일명) | `find . -print0 \| xargs -0 grep` |
| `-P 4` | 병렬 실행 (4개 동시) | `xargs -P 4 gzip` |
| `-L 1` | 한 줄씩 처리 | `xargs -L 1 echo` |
| `-d '\n'` | 구분자 지정 | `xargs -d '\n' rm` |

### 실무 예시

```bash
# 파일 검색 후 grep
find . -name "*.log" | xargs grep "error"

# 파일 삭제
find . -name "*.tmp" | xargs rm

# 파일 백업
find . -name "*.conf" | xargs -I {} cp {} {}.bak

# 병렬 압축
find . -name "*.log" | xargs -P 4 gzip

# 프로세스 종료
ps -ef | grep java | awk '{print $2}' | xargs kill

# 파일명에 공백이 있을 때
find . -name "*.log" -print0 | xargs -0 grep "error"

# 한 번에 N개씩 처리
echo {1..10} | xargs -n 2 echo

# 실행 전 확인
find . -name "*.tmp" | xargs -p rm

# 명령어 확인
find . -name "*.log" | xargs -t wc -l
```

### 고급 활용

```bash
# 파일 내용을 인자로 전달
cat files.txt | xargs cat

# 여러 디렉토리 동시 생성
echo "dir1 dir2 dir3" | xargs mkdir

# 파일 이름 변경 (일괄)
find . -name "*.txt" | xargs -I {} mv {} {}.bak

# 파일 권한 변경
find . -name "*.sh" | xargs chmod +x

# 여러 파일 동시 검색
cat filelist.txt | xargs -I {} grep "error" {}

# 병렬 다운로드
cat urls.txt | xargs -P 5 -I {} wget {}

# 디렉토리별 용량 확인
find . -maxdepth 1 -type d | xargs du -sh
```

### xargs vs -exec 비교

```bash
# find -exec: 각 파일마다 실행 (느림)
find . -name "*.log" -exec grep "error" {} \;

# find -exec +: 여러 파일 묶어서 (빠름)
find . -name "*.log" -exec grep "error" {} +

# xargs: 가장 유연하고 빠름
find . -name "*.log" | xargs grep "error"

# 성능 비교 (1000개 파일 기준)
# -exec \;  : 1000번 실행 (10초)
# -exec +   : 1-2번 실행 (1초)
# xargs     : 1-2번 실행 (1초)
```

---

## 6. 명령어 조합 패턴

### sed vs awk 선택 가이드

| 작업 | 선택 | 이유 |
| --- | --- | --- |
| 문자열 치환 | **sed** | 간단하고 빠름 |
| 컬럼 추출 | **awk** | 컬럼 자동 분리 |
| 숫자 계산 | **awk** | 계산 기능 내장 |
| 데이터 집계 | **awk** | 배열/반복문 지원 |
| 정규식 치환 | **sed** | 치환 전문 |
| 복잡한 조건 | **awk** | 프로그래밍 가능 |

### 실무 파이프라인 예시

```bash
# 패턴 1: find + xargs + grep
find . -name "*.log" | xargs grep -i "error" -A 5 -B 5

# 패턴 2: grep + awk (필터링 + 추출)
grep "ERROR" application.log | awk '{print $1, $2, $4}'

# 패턴 3: ps + grep + awk + xargs (프로세스 종료)
ps -ef | grep java | awk '{print $2}' | xargs kill

# 패턴 4: find + sed (파일 내용 일괄 수정)
find . -name "*.properties" | xargs sed -i 's/port=8080/port=9090/g'

# 패턴 5: awk + sed (데이터 추출 + 포맷 변경)
awk '$3 == "ERROR" {print $1, $4}' log.txt | sed 's/-/\//g'

# 패턴 6: grep + sort + uniq (중복 제거 집계)
grep "ERROR" log.txt | sort | uniq -c | sort -rn

# 패턴 7: find + tar (파일 백업)
find . -name "*.conf" | tar -czf config_backup.tar.gz -T -

# 패턴 8: awk + sort (정렬 후 출력)
awk '{print $3, $1}' data.txt | sort -k1 -n
```

### 성능 비교

| 방법 | 100개 파일 | 명령 실행 횟수 | 효율 |
| --- | --- | --- | --- |
| `find -exec {} \;` | 10초 | 100번 | ⭐ |
| `find -exec {} +` | 1초 | 1-2번 | ⭐⭐⭐⭐⭐ |
| `find \| xargs` | 1초 | 1-2번 | ⭐⭐⭐⭐⭐ |

---

## 7. 필수 보조 명령어

### cut - 간단한 컬럼 추출

```bash
# 기본 문법
cut -d'구분자' -f필드번호 파일명

# 콜론 구분자로 1, 3번째 필드 추출
cut -d':' -f1,3 /etc/passwd

# 범위 지정
cut -d',' -f1-3 data.csv  # 1~3번째 필드

# 탭 구분자 (기본값)
cut -f1,3 data.tsv

# 문자 위치로 추출 (1-10번째 문자)
cut -c1-10 file.txt

# 특정 위치부터 끝까지
cut -c5- file.txt

# awk vs cut 선택
# cut: 간단한 필드 추출 (빠름, 조건 처리 불가)
# awk: 복잡한 처리 (느림, 조건/계산 가능)

# 실무 예시
# /etc/passwd에서 사용자명과 홈 디렉토리만
cut -d':' -f1,6 /etc/passwd

# CSV에서 2번째 컬럼만
cut -d',' -f2 data.csv

# 로그에서 첫 20자 (타임스탬프)만
cut -c1-20 application.log
```

### sort - 정렬

```bash
# 기본 정렬 (사전순)
sort file.txt

# 역순 정렬
sort -r file.txt

# 숫자 정렬
sort -n numbers.txt

# 숫자 역순
sort -rn numbers.txt

# 특정 컬럼 기준 정렬
sort -t':' -k3 -n /etc/passwd  # 3번째 필드 숫자 정렬

# 중복 제거하며 정렬
sort -u file.txt

# 여러 키로 정렬 (2번째 내림차순, 3번째 오름차순)
sort -k2 -rn -k3 data.txt

# 대소문자 무시
sort -f file.txt

# 사람이 읽기 쉬운 숫자 정렬 (1K, 1M 등)
du -h | sort -h

# 월 이름 정렬
sort -M months.txt

# 실무 예시
# 파일 크기순 정렬
ls -lh | sort -k5 -h

# IP 주소 정렬
sort -t. -k1,1n -k2,2n -k3,3n -k4,4n ip_list.txt

# 로그 파일 시간순 정렬
sort -t' ' -k1,1 -k2,2 application.log
```

### uniq - 중복 제거 및 카운팅

```bash
# 중복 제거 (정렬된 입력 필요!)
sort file.txt | uniq

# 중복 횟수 카운트
sort file.txt | uniq -c

# 중복된 라인만 출력
sort file.txt | uniq -d

# 유일한 라인만 출력
sort file.txt | uniq -u

# 대소문자 무시
sort file.txt | uniq -i

# 특정 필드만 비교
uniq -f 2 file.txt  # 앞 2개 필드 무시

# 특정 문자 수 무시
uniq -s 5 file.txt  # 앞 5자 무시

# 실무 예시
# 가장 많이 발생한 에러
grep "ERROR" app.log | awk '{print $5}' | sort | uniq -c | sort -rn | head -10

# 중복 IP 찾기
awk '{print $1}' access.log | sort | uniq -c | sort -rn

# 유일한 사용자 수
cut -d',' -f1 users.csv | sort | uniq | wc -l
```

### head / tail - 상위/하위 라인

```bash
# 처음 10줄
head file.txt
head -n 20 file.txt  # 20줄

# 마지막 10줄
tail file.txt
tail -n 50 file.txt  # 50줄

# 실시간 로그 모니터링
tail -f application.log

# 여러 파일 동시 모니터링
tail -f app1.log app2.log

# 처음 10줄 제외한 나머지
tail -n +11 file.txt

# 마지막 5줄 제외한 나머지
head -n -5 file.txt

# 실시간 모니터링 + grep
tail -f application.log | grep --line-buffered "ERROR"

# 최근 N 바이트만
tail -c 1024 file.txt  # 마지막 1KB

# 실무 예시
# 가장 큰 파일 10개
du -h | sort -rh | head -10

# 가장 작은 파일 5개
du -h | sort -h | head -5

# 로그 마지막 100줄에서 에러 찾기
tail -n 100 app.log | grep "ERROR"
```

### tr - 문자 변환/삭제

```bash
# 소문자를 대문자로
echo "hello" | tr 'a-z' 'A-Z'

# 대문자를 소문자로
echo "HELLO" | tr 'A-Z' 'a-z'

# 공백을 줄바꿈으로
echo "a b c" | tr ' ' '\n'

# 줄바꿈을 공백으로
cat file.txt | tr '\n' ' '

# 문자 삭제
echo "hello123" | tr -d '0-9'  # hello

# 연속된 문자 압축
echo "hello    world" | tr -s ' '  # hello world

# 보완 집합 (complement)
echo "hello123" | tr -cd '0-9\n'  # 123 (숫자만 남김)

# 실무 예시
# DOS 줄바꿈을 Unix로 변환
tr -d '\r' < dosfile.txt > unixfile.txt

# 모든 숫자를 X로
echo "Phone: 010-1234-5678" | tr '0-9' 'X'

# 특수문자 제거
echo "Hello, World!" | tr -d '[:punct:]'

# 연속된 공백을 하나로
cat file.txt | tr -s ' '
```

### wc - 카운트

```bash
# 라인, 단어, 바이트 수
wc file.txt

# 라인 수만
wc -l file.txt

# 단어 수만
wc -w file.txt

# 바이트 수만
wc -c file.txt

# 문자 수만 (멀티바이트 고려)
wc -m file.txt

# 여러 파일 합계
wc -l *.log

# 실무 예시
# 디렉토리별 파일 개수
find . -type f | wc -l

# 코드 라인 수
find . -name "*.java" | xargs wc -l

# 에러 발생 횟수
grep "ERROR" application.log | wc -l

# 고유한 IP 개수
awk '{print $1}' access.log | sort -u | wc -l
```

### tee - 출력 분기

```bash
# 화면과 파일에 동시 출력
command | tee output.txt

# 파일에 추가 (append)
command | tee -a output.txt

# 여러 파일에 동시 저장
command | tee file1.txt file2.txt

# 중간 결과 저장하며 파이프라인 계속
cat data.txt | tee intermediate.txt | grep "error" | tee final.txt

# 실무 예시
# 로그 보면서 저장
tail -f app.log | grep "ERROR" | tee error.log

# 빌드 로그 저장
make 2>&1 | tee build.log

# 여러 곳에 동시 저장
cat data.txt | tee backup1.txt backup2.txt > /dev/null

# sudo와 함께 사용 (권한 문제 해결)
echo "content" | sudo tee /etc/config.conf
```

---

## 8. 고급 활용 패턴

### 로그 분석 워크플로우

```bash
# 1단계: 에러 로그 추출 및 저장
grep -iE "error|exception|fatal" app.log > errors.txt

# 2단계: 시간대별 에러 분석
awk '{print substr($1,12,2)}' errors.txt | sort | uniq -c

# 3단계: 가장 많이 발생한 에러 유형
awk '{print $5}' errors.txt | sort | uniq -c | sort -rn | head -10

# 4단계: 특정 에러의 전후 컨텍스트 확인
grep -A 5 -B 5 "NullPointerException" app.log | less

# 5단계: 일별 에러 트렌드
awk '{print substr($1,1,10)}' errors.txt | sort | uniq -c

# 완전한 파이프라인
cat app.log | \
  grep -iE "error|exception|fatal" | \
  awk '{print substr($1,1,10), $5}' | \
  sort | \
  uniq -c | \
  sort -rn | \
  head -20
```

### 시스템 모니터링

```bash
# 디스크 사용량 상위 10개 디렉토리
du -h --max-depth=1 | sort -hr | head -10

# 큰 파일 찾아서 리스트 생성
find / -type f -size +1G -exec ls -lh {} \; 2>/dev/null | \
  awk '{print $5, $9}' | \
  sort -hr > large_files.txt

# 프로세스 메모리 사용량 상위 10개
ps aux | sort -rn -k4 | head -10

# CPU 사용량 상위 10개
ps aux | sort -rn -k3 | head -10

# 특정 포트 사용 프로세스
netstat -tlnp 2>/dev/null | grep :8080 | awk '{print $7}' | cut -d'/' -f1 | xargs ps -p

# 좀비 프로세스 찾기
ps aux | awk '$8=="Z" {print $0}'

# 디스크 I/O 많은 프로세스
iotop -b -n 1 | head -20

# 네트워크 연결 상태 확인
netstat -an | awk '{print $6}' | sort | uniq -c | sort -rn
```

### 데이터 정제 파이프라인

```bash
# CSV 데이터 클리닝
cat data.csv | \
  sed 's/\r$//' | \                    # Windows 줄바꿈 제거
  awk -F',' 'NF==5' | \                # 5개 컬럼만 추출
  sed 's/"//g' | \                      # 따옴표 제거
  awk -F',' '$3 > 0 {print $0}' | \    # 3번째 컬럼이 양수인 것만
  sort -t',' -k2 > cleaned.csv         # 2번째 컬럼으로 정렬

# 중복 제거 및 병합
cat file1.txt file2.txt file3.txt | sort | uniq > merged.txt

# 두 파일의 공통 라인 찾기
sort file1.txt > sorted1.txt
sort file2.txt > sorted2.txt
comm -12 sorted1.txt sorted2.txt

# 파일 비교 (차이점만)
diff file1.txt file2.txt

# 두 파일에서 중복 제거
cat file1.txt file2.txt | sort | uniq -u
```

### 텍스트 변환

```bash
# JSON 로그를 CSV로 변환 (jq 없이)
cat app.log | \
  sed 's/.*"timestamp":"\([^"]*\)".*"level":"\([^"]*\)".*"message":"\([^"]*\)".*/\1,\2,\3/' > output.csv

# URL에서 파라미터 추출
grep -oP 'user_id=\K[0-9]+' access.log | sort | uniq

# IP 주소만 추출하여 지역별 카운트 (IP 첫 옥텟 기준)
grep -oP '\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}' access.log | \
  awk -F'.' '{print $1"."$2}' | \
  sort | uniq -c | sort -rn

# 파일에서 이메일 주소만 추출
grep -oE '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}' file.txt | sort -u

# HTML에서 링크 추출
grep -oP 'href="\K[^"]+' index.html

# 로그에서 응답 시간 통계
awk '{sum+=$NF; count++; if($NF>max) max=$NF; if(min=="" || $NF<min) min=$NF}
     END {print "평균:", sum/count, "최소:", min, "최대:", max}' response.log
```

### 백업 및 아카이브

```bash
# 최근 변경된 파일만 백업
find . -mtime -1 -type f | tar -czf backup.tar.gz -T -

# 큰 파일 제외하고 백업
find . -type f -size -10M | tar -czf small_files.tar.gz -T -

# 날짜별 백업
tar -czf backup_$(date +%Y%m%d).tar.gz /path/to/backup

# 증분 백업 (변경된 파일만)
find . -newer last_backup.timestamp -type f | tar -czf incremental.tar.gz -T -
touch last_backup.timestamp
```

---

## 9. 문제 해결 가이드

### 일반적인 문제와 해결법

#### 문제 1: 명령어가 아무 결과도 출력하지 않음

```bash
# 체크리스트:
# 1. 파일이 존재하는가?
ls -la file.txt

# 2. 파일에 내용이 있는가?
wc -l file.txt
cat file.txt

# 3. 검색 패턴이 정확한가?
grep -i "pattern" file.txt  # 대소문자 무시

# 4. 정규식 특수문자를 이스케이프했는가?
grep "192\.168\.1\.1" file.txt  # 점을 이스케이프

# 5. 파일 인코딩 문제?
file file.txt  # 인코딩 확인
iconv -f EUC-KR -t UTF-8 file.txt > converted.txt

# 6. 줄바꿈 문자 문제 (Windows vs Unix)
file file.txt  # CRLF 확인
dos2unix file.txt  # 또는
sed -i 's/\r$//' file.txt
```

#### 문제 2: find 결과가 너무 많음

```bash
# 해결법:
# 1. maxdepth로 깊이 제한
find . -maxdepth 2 -name "*.log"

# 2. 특정 디렉토리 제외
find . -name "*.log" -not -path "*/node_modules/*" -not -path "*/.git/*"

# 3. 시간으로 범위 제한
find . -name "*.log" -mtime -7  # 최근 7일만

# 4. 크기로 제한
find . -name "*.log" -size +1M  # 1MB 이상만

# 5. 결과 개수 제한
find . -name "*.log" | head -100
```

#### 문제 3: xargs에서 "Argument list too long" 에러

```bash
# 해결법:
# 1. -n 옵션으로 배치 크기 제한
find . -name "*.log" | xargs -n 100 grep "error"

# 2. -L 옵션으로 라인 단위 처리
find . -name "*.log" | xargs -L 1 grep "error"

# 3. -exec + 사용
find . -name "*.log" -exec grep "error" {} +

# 4. while 루프 사용
find . -name "*.log" | while read file; do grep "error" "$file"; done
```

#### 문제 4: 파일명에 공백이 있어서 오류 발생

```bash
# 해결법:
# 1. find -print0과 xargs -0 조합 (가장 안전)
find . -name "*.log" -print0 | xargs -0 grep "error"

# 2. -exec 사용
find . -name "*.log" -exec grep "error" {} +

# 3. while read 사용
find . -name "*.log" | while read -r file; do
  grep "error" "$file"
done

# 4. IFS 설정
IFS=$'\n'
for file in $(find . -name "*.log"); do
  grep "error" "$file"
done
```

#### 문제 5: sed가 파일을 변경하지 않음

```bash
# 원인: -i 옵션 없이 실행
# 해결법:
# 1. -i 옵션 사용
sed -i 's/old/new/g' file.txt

# 2. 리다이렉션 사용 (주의: 원본 파일이 비워질 수 있음)
sed 's/old/new/g' file.txt > temp && mv temp file.txt

# 3. 백업 생성하며 수정
sed -i.bak 's/old/new/g' file.txt
```

#### 문제 6: grep이 바이너리 파일을 검색함

```bash
# 해결법:
# 1. 텍스트 파일만 검색
grep -I "pattern" *

# 2. 바이너리 파일 제외
grep -r --binary-files=without-match "pattern" .

# 3. 특정 파일 타입만
grep -r --include="*.txt" "pattern" .
```

### 성능 최적화 팁

```bash
# ❌ 느린 방법: 각 파일마다 grep 실행
find . -name "*.log" -exec grep "error" {} \;

# ✅ 빠른 방법: 여러 파일 묶어서 실행
find . -name "*.log" -exec grep "error" {} +
# 또는
find . -name "*.log" | xargs grep "error"

# ❌ 느린 방법: awk로 간단한 필터링
awk -F',' '{print $1, $3}' large_file.csv

# ✅ 빠른 방법: cut 사용
cut -d',' -f1,3 large_file.csv

# ❌ 느린 방법: grep 정규식
grep "[0-9]\+" file.txt

# ✅ 빠른 방법: -F로 고정 문자열 검색
grep -F "12345" file.txt

# ❌ 느린 방법: 불필요한 cat
cat file.txt | grep "error"

# ✅ 빠른 방법: 직접 파일 지정
grep "error" file.txt

# ❌ 느린 방법: 여러 grep 파이프
grep "error" file.txt | grep "critical" | grep "database"

# ✅ 빠른 방법: 한 번에 검색
grep "error.*critical.*database" file.txt
# 또는
awk '/error/ && /critical/ && /database/' file.txt
```

### 디버깅 기법

```bash
# 1. 단계별로 파이프라인 실행
cat file.txt | grep "error"  # 1단계
cat file.txt | grep "error" | awk '{print $1}'  # 2단계
cat file.txt | grep "error" | awk '{print $1}' | sort  # 3단계

# 2. 중간 결과 저장
cat file.txt | grep "error" | tee step1.txt | \
  awk '{print $1}' | tee step2.txt | \
  sort > final.txt

# 3. 명령어 확인 (dry-run)
find . -name "*.tmp" | xargs echo rm  # rm은 실행 안 됨

# 4. 개수 확인
find . -name "*.tmp" | wc -l  # 몇 개 파일이 영향받는지

# 5. 샘플 데이터로 테스트
head -100 large_file.txt | grep "pattern"

# 6. set -x로 디버깅 (스크립트에서)
set -x  # 실행되는 명령어 출력
# ... 명령어들 ...
set +x  # 디버깅 모드 종료
```

---

## 10. 보안 및 주의사항

### 위험한 명령어 조합

```bash
# ⚠️ 위험: -delete를 조건 앞에 사용
find . -delete -name "*.tmp"  # 모든 파일이 삭제될 수 있음!

# ✅ 안전: 조건을 먼저, -delete는 마지막
find . -name "*.tmp" -delete

# ⚠️ 위험: sed -i 백업 없이 사용
sed -i 's/old/new/g' important.conf  # 원본 손실!

# ✅ 안전: 백업 파일 생성
sed -i.bak 's/old/new/g' important.conf

# ⚠️ 위험: xargs rm 확인 없이 사용
find . -name "*.tmp" | xargs rm  # 실수하면 복구 불가!

# ✅ 안전: -p 옵션으로 확인
find . -name "*.tmp" | xargs -p rm

# ⚠️ 위험: 파일명에 공백이 있을 때
find . -name "*.log" | xargs rm  # 파일명 분리되어 다른 파일 삭제 가능

# ✅ 안전: -print0 과 -0 사용
find . -name "*.log" -print0 | xargs -0 rm

# ⚠️ 위험: 와일드카드와 rm 조합
rm -rf /var/log/*.log  # 패턴이 매칭 안 되면 전체 삭제 위험

# ✅ 안전: 먼저 확인
ls /var/log/*.log
rm /var/log/*.log

# ⚠️ 위험: sudo와 리다이렉션
sudo echo "content" > /etc/config  # 권한 에러!

# ✅ 안전: tee 사용
echo "content" | sudo tee /etc/config
```

### 명령어 실행 전 확인하기

```bash
# 1. 테스트 실행: echo로 확인
find . -name "*.tmp" | xargs echo rm  # rm은 실행 안 됨

# 2. 카운트 확인
find . -name "*.tmp" | wc -l  # 삭제될 파일 개수 확인

# 3. 리스트 확인
find . -name "*.tmp" > files_to_delete.txt
cat files_to_delete.txt  # 목록 확인 후
cat files_to_delete.txt | xargs rm  # 삭제 실행

# 4. 조건 검증
find . -name "*.tmp" -mtime +30  # 먼저 조건만 확인
find . -name "*.tmp" -mtime +30 -delete  # 확인 후 삭제

# 5. 샘플로 테스트
head -10 file.txt | sed 's/old/new/g'  # 결과 확인
sed -i.bak 's/old/new/g' file.txt  # 전체 적용

# 6. -p 옵션으로 대화형 확인
find . -name "*.tmp" | xargs -p rm
```

### 권한 관련 주의사항

```bash
# ⚠️ sudo와 와일드카드 조합 주의
sudo rm -rf /var/log/*.log  # /var/log 전체가 삭제될 위험

# ✅ 안전한 방법: 먼저 확인
ls -la /var/log/*.log
sudo rm /var/log/*.log

# ⚠️ find로 권한 확인 안 하고 변경
find . -type f -exec chmod 777 {} +  # 모든 파일 777 위험!

# ✅ 안전한 방법: 현재 권한 확인 후 작업
find . -type f -perm 777  # 777 권한 파일 찾기
find . -type f -perm 777 -exec chmod 644 {} +  # 권한 수정

# 중요 파일 보호
# /etc, /boot, /sys, /proc 등은 절대 rm -rf 하지 말 것
# 루트 디렉토리 (/) 절대 삭제 금지
```

### 데이터 손실 방지

```bash
# 1. 백업 먼저
cp file.txt file.txt.backup
sed -i 's/old/new/g' file.txt

# 2. 버전 관리
git commit -am "Before modification"
# ... 작업 ...
git diff  # 변경사항 확인

# 3. 테스트 파일로 먼저 실행
cp production.conf test.conf
sed -i 's/old/new/g' test.conf
diff production.conf test.conf  # 차이 확인

# 4. 중요 파일은 --backup 옵션
sed -i.backup 's/old/new/g' important.conf

# 5. 복구 가능성 확보
tar -czf backup_$(date +%Y%m%d_%H%M%S).tar.gz /path/to/files
# ... 작업 ...
```

### 정규식 주의사항

```bash
# ⚠️ Greedy 매칭 주의
echo "a <tag>content</tag> b" | sed 's/<.*>//'  # 전체 삭제됨

# ✅ Non-greedy 사용
echo "a <tag>content</tag> b" | sed 's/<[^>]*>//'

# ⚠️ 특수문자 이스케이프 누락
grep "192.168.1.1" file.txt  # 점이 모든 문자와 매칭

# ✅ 올바른 이스케이프
grep "192\.168\.1\.1" file.txt

# ⚠️ 앵커 누락으로 의도하지 않은 매칭
grep "test" file.txt  # "testing", "contest"도 매칭

# ✅ 단어 단위 매칭
grep -w "test" file.txt
# 또는
grep "\btest\b" file.txt
```

---

## 11. 치트 시트

### 한 줄로 보는 명령어 선택

```bash
파일 찾기          → find
텍스트 검색        → grep
문자열 치환        → sed
컬럼 추출/계산     → awk (복잡) / cut (간단)
정렬              → sort
중복 제거         → uniq (정렬 후) / awk '!seen[$0]++'
상위/하위 N개     → head / tail
문자 변환         → tr
파일 결합         → cat
결과 분기         → tee
배치 처리         → xargs
카운트            → wc
```

### 실무 필수 조합 Top 10

```bash
# 1. 로그에서 에러 찾아 전후 컨텍스트와 함께 확인
grep -i "error" -C 5 -n --color=auto app.log | less

# 2. 가장 많이 발생한 에러 Top 10
grep "ERROR" app.log | awk '{print $5}' | sort | uniq -c | sort -rn | head -10

# 3. 오래된 로그 파일 찾아서 압축
find ./logs -name "*.log" -mtime +30 -exec gzip {} \;

# 4. 디스크 사용량 상위 10개
du -h --max-depth=1 | sort -hr | head -10

# 5. 특정 프로세스 모두 종료
ps -ef | grep java | grep myapp | awk '{print $2}' | xargs kill

# 6. 설정 파일 일괄 변경
find . -name "*.properties" -exec sed -i.bak 's/old_value/new_value/g' {} +

# 7. 로그 실시간 모니터링 (에러만)
tail -f app.log | grep --line-buffered "ERROR"

# 8. CSV 데이터 특정 컬럼 합계
awk -F',' '{sum+=$3} END {print sum}' sales.csv

# 9. 중복 라인 제거
sort file.txt | uniq > unique.txt

# 10. 여러 로그 파일에서 시간순 정렬
cat *.log | sort -k1 > merged_sorted.log
```

### 명령어별 핵심 옵션 요약

#### grep

```bash
-i      # 대소문자 무시
-r      # 재귀 검색
-n      # 라인 번호
-v      # 제외
-l      # 파일명만
-c      # 개수만
-A 5    # 후 5줄
-B 5    # 전 5줄
-C 5    # 전후 5줄
-w      # 단어 단위
-o      # 매칭 부분만
-E      # 확장 정규식
-F      # 고정 문자열
-P      # Perl 정규식
```

#### find

```bash
-name "*.log"       # 이름 패턴
-iname "*.LOG"      # 대소문자 무시
-type f             # 파일만
-type d             # 디렉토리만
-mtime -7           # 7일 이내
-mtime +30          # 30일 이전
-size +10M          # 10MB 이상
-maxdepth 2         # 깊이 2까지
-user nginx         # 소유자
-perm 644           # 권한
-empty              # 빈 파일
-exec cmd {} \;     # 각각 실행
-exec cmd {} +      # 묶어서 실행
-delete             # 삭제
```

#### awk

```bash
-F','               # 구분자
'{print $1, $3}'    # 1, 3번째 컬럼
'/pattern/'         # 패턴 매칭
'$3 == "ERROR"'     # 조건
'$5 > 100'          # 숫자 비교
'{sum+=$5}'         # 합계
'END {print sum}'   # 종료 시
'BEGIN {FS=","}'    # 시작 시
'!seen[$1]++'       # 중복 제거
'{count[$3]++}'     # 그룹별 카운트
```

#### sed

```bash
's/old/new/'        # 치환 (첫 번째)
's/old/new/g'       # 치환 (모두)
's/old/new/I'       # 대소문자 무시
'/pattern/d'        # 삭제
'/pattern/p'        # 출력
-n                  # 자동 출력 억제
-i                  # 파일 수정
-i.bak              # 백업 생성
-e 'cmd1' -e 'cmd2' # 여러 명령
'10,20s/old/new/'   # 라인 범위
```

#### xargs

```bash
-n 2                # 2개씩
-I {}               # 플레이스홀더
-p                  # 확인
-t                  # 명령어 출력
-0                  # null 구분
-P 4                # 병렬 4개
-L 1                # 한 줄씩
```

### 자주 쓰는 패턴 모음

```bash
# 파일 찾아서 검색
find . -name "*.log" | xargs grep "error"

# 프로세스 종료
ps -ef | grep java | awk '{print $2}' | xargs kill

# 중복 제거 집계
sort file.txt | uniq -c | sort -rn

# 컬럼 추출 후 계산
awk '{sum+=$5} END {print sum}' data.txt

# 파일 백업
find . -name "*.conf" | xargs -I {} cp {} {}.bak

# 로그 실시간 모니터링
tail -f app.log | grep --line-buffered "ERROR"

# 시간순 정렬
cat *.log | sort -k1 > merged.log

# 디스크 사용량 확인
du -h --max-depth=1 | sort -hr | head -10

# 가장 많이 발생한 항목
awk '{print $1}' data.txt | sort | uniq -c | sort -rn | head -10

# CSV 데이터 처리
awk -F',' '{print $1, $3}' data.csv
```

### 정규식 패턴 모음

```bash
# IP 주소
grep -oP '\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}' file.txt

# 이메일
grep -oE '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}' file.txt

# URL
grep -oP 'https?://[^\s]+' file.txt

# 전화번호 (한국)
grep -P '0\d{1,2}-\d{3,4}-\d{4}' file.txt

# 날짜 (YYYY-MM-DD)
grep -P '\d{4}-\d{2}-\d{2}' file.txt

# 시간 (HH:MM:SS)
grep -P '\d{2}:\d{2}:\d{2}' file.txt

# 숫자만
grep -oP '\d+' file.txt

# 알파벳만
grep -oP '[a-zA-Z]+' file.txt
```

### 성능 최적화 체크리스트

```bash
✅ xargs 또는 -exec + 사용 (-exec \; 대신)
✅ grep -F 사용 (정규식 불필요 시)
✅ awk 대신 cut 사용 (단순 컬럼 추출)
✅ 불필요한 cat 제거
✅ --include/--exclude로 파일 제한
✅ -maxdepth로 검색 깊이 제한
✅ 파이프 최소화
✅ 병렬 처리 활용 (xargs -P)
```

---

## 💡 핵심 정리

### 명령어 선택 플로우차트

```
질문: 무엇을 하고 싶은가?

├─ 텍스트 찾기 → grep
├─ 파일 찾기 → find
├─ 문자열 바꾸기 → sed
├─ 컬럼 추출/계산 → awk (복잡) / cut (간단)
├─ 정렬 → sort
├─ 중복 제거 → uniq
└─ 여러 개 처리 → xargs
```

### 학습 로드맵

1. **기초 (1주)**
   - grep 기본 옵션 (-i, -n, -r, -v)
   - find 파일 검색 (-name, -type)
   - 파이프 개념 이해

2. **중급 (2-3주)**
   - awk 컬럼 추출 및 계산
   - sed 문자열 치환
   - xargs 기본 사용
   - sort, uniq 조합

3. **고급 (4주 이상)**
   - 정규식 패턴
   - awk 배열 및 함수
   - sed 고급 명령
   - 복잡한 파이프라인
   - 성능 최적화

### 실무 투입 체크리스트

```bash
□ grep으로 로그에서 에러 찾기
□ find로 특정 파일 검색
□ awk로 컬럼 추출 및 집계
□ sed로 문자열 일괄 치환
□ sort | uniq로 중복 제거
□ 파이프라인 3단계 이상 구성
□ xargs로 배치 처리
□ 정규식 기본 패턴 작성
□ 성능 고려한 명령어 선택
□ 안전하게 파일 수정 (백업)
```

---