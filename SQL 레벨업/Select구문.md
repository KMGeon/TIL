# Select 구문

---

## 6강 select구문

- 데이터베이스의 검색이 핵심이다. 질의 + 추출
- select구문은 두 개의 부분으로 구성되어져 있다. 첫번째는 select뒤에 나열하고 이후 from 테이블을 붙여서 대상 테이블을 저장한다.

### where
- 앞에 from 테이블을 검색하면 where을 통해서 조건을 걸어서 검색할 수 있다.

| 연산자 |의미|
|----|--|
| =  |같다|
| <> |같지 않다|
| >= |크거나 같다|
| `>`  |크다|
| <= |작거나 같다|
| <  |작다|

- and를 통해서는 공통된 조건을 필터링 할 수 있고 or을 통해서 두개의 조건중 하나만 만족하면 검색할 수 있다.

### in으로 or조건 대체하기
- sql에서 and, or을 사용해 다양한 조건을 지정할 수 있다. 상황에 따라서 or조건을 많이 처리할 때 in으로 대체할 수 있다.

### null, is null
- null은 데이터가 없다는 의미이다. null은 데이터가 없다는 의미이기 때문에 =을 사용해서는 검색할 수 없다. 따라서 is null을 사용해서 검색해야 된다.

### group by
- group by는 특정 컬럼을 기준으로 그룹을 지어서 집계함수를 사용할 수 있다.
- 대표적인 집계 함수 : count, sum, avg, max, min

### having
- having은 group by와 함께 사용되는데 group by로 그룹을 지어서 집계함수를 사용하고 having으로 조건을 걸어서 검색할 수 있다.

### order by
- order by는 정렬을 할 수 있다. 기본적으로 오름차순으로 정렬을 한다. 내림차순으로 정렬하고 싶으면 desc를 사용하면 된다.

### 서브쿼리를 사용한 편리한 조건 지검
```sql
select name from Address where id in (select id from Member where age > 30);
```
- in을 통하여 매칭을 할 수 있다.

## 7강 조건 분기, 집합 연산, 윈도우 함수, 갱신

### sql과 조건 분기
- sql에서는 if문을 사용할 수 없다. 대신에 case문을 사용할 수 있다.

### case문
- case문은 조건 분기를 할 수 있는 구문이다. case문은 크게 2가지로 나뉜다. 단순 case문과 검색 case문이 있다.

### 단순 case문
- 단순 case문은 특정 컬럼을 기준으로 조건을 분기할 수 있다.
- 단순 case문은 case문 뒤에 컬럼을 지정하고 when절에 조건을 지정하고 then절에 조건이 만족할 때 출력할 값을 지정한다.
- else절은 조건이 만족하지 않을 때 출력할 값을 지정한다.
```sql
select 
    name,
    case age 
        when 10 then '10대' 
        when 20 then '20대' 
        else '기타' 
    end as age 
from Member;
```

### sql 집합 연산

### union, union all
- union(distinct)은 합집합을 의미한다. union all은 중복을 제거하지 않고 합집합을 의미한다.

### intersect
- 교집합을 구할 때 사용하는 연산자
```sql
select * from address
intersect
select * from address2;
```

### except
- 차집합을 구할 때 사용하는 연산자
```sql
select * from address
except
select * from address2;
```


### sql 윈도우 함수
- 윈도우 함수는 집계함수를 사용할 때 특정 컬럼을 기준으로 그룹을 지어서 집계함수를 사용할 수 있다.
- 윈도우 함수는 집계함수와 달리 그룹을 지어서 사용하지 않고 특정 컬럼을 기준으로 정렬을 하고 그룹을 지어서 집계함수를 사용할 수 있다.
