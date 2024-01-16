## 8강 union을 사용한 쓸데없이 긴 표현

---

- union을 사용한 조건 분기는 초보자가 좋아하는 기술이다. 일반적으로 조건 분기는 where구만 조금씩 다른 여러 개의 select구문을 합쳐서 복수의 조건에 일치하는 하나의 결과 집합을 얻을 때 사용한다.
- 하지만 이 경우 성능적인 측면에서 단점을 가지고 있다. 
  - 하나의 sql구문처럼 보이지만 여러 개의 select 구문이 실행되어서 i/o비용이 발생

### 2. where 구에서 조건 분기를 하는 사람은 초보자
- where절에서 조건 분기를 처리할 수 있지만 select에서 최적화를 할 수 있다.

```sql
select item_name, year
    case when year <=2021 then price_tax_ex
    where year >= 2002 then price_tax in end as price
                                         from items;
```

## 9강 집계와 조건 분기
