# 날짜 차이 계산

---

- 두개의 날짜의 차이를 계산하는 함수에 대해서 알아보자.
- 대표적으로 `TIMEDIFF`, `DATEDIFF`, `TIMESTAMPDIFF`가 있다.

```markdown
DATEDIFF() - 두 기간 사이의 일수 계산
TIMEDIFF() - 두 기간 사이의 시간 계산
PERIOD_DIFF() - 두 기간 사이의 개월 수 계산
TIMESTAMPDIFF() - 두 기간 사이의 시간 계산(단위 지정 가능)
```

## DATEDIFF 함수 

---

DATEDIFF 함수는 문자 그대로 두 날짜 간의 차이를 DATE 단위로 가지고 오는 역활을 한다.

```sql
DATEDIFF(날짜1, 날짜2)
        
SELECT DATEDIFF(NOW(), '2024-01-01') AS DIFF;
```
- 내부적으로 날짜1 - 날짜2의 결과를 가지고 오는데, 날짜1이 날짜2보다 미래의 날짜라면 양수, 과거의 날짜라면 음수를 반환한다.



## TIMEDIFF 함수

---

TIMEDIFF 함수는 두 날짜 간의 차이를 TIME 단위로 가지고 오는 역활을 한다.

```sql
SELECT TIMEDIFF('2024-01-03 12:00:00', '2024-01-03 11:00:00');
```
- 두 시간을 빼면 01:00:00이 나오는데, 이는 TIME 타입으로 반환된다.
- 그런데 `SELECT TIMEDIFF('2024-01-03 12:00:00', '2024-01-02 12:00:00');` 다음과 같이 쿼리를 날리면 0시간이 발생한다.
- 분명히 24시간을 기대했던 로직과 다르다. 이 부분을 더 찾아봐야된다.


## PERIOD_DIFF 함수

---

- 두 날짜 간의 차이를 MONTH 단위로 가지고 오는 역활을 한다.
```sql
SELECT PERIOD_DIFF('202202', '202112'); -- 2
```


## TIMESTAMPDIFF 함수

---

- 시간 또는 개월 수 등 여러 가지 형태의 계산을 할 수 있는 함수
- 다른 함수들과 시작일과 종료일의 순서가 반대
```sql
TIMESTAMPDIFF(unit, 시작일, 종료일)

SELECT TIMESTAMPDIFF(MONTH, '2021-02-01', '2022-03-01'); -- 13;
SELECT TIMESTAMPDIFF(YEAR, '2021-02-01', '2022-03-01'); -- 1;
```