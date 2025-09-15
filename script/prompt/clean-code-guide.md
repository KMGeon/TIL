# Clean Code Guide

## 변수명과 함수명 규칙

### 변수명 (Variable Names)
- **의도를 분명히 하는 이름 사용**
  ```java
  // 나쁜 예
  int d; // elapsed time in days
  
  // 좋은 예
  int elapsedTimeInDays;
  ```

- **검색 가능한 이름 사용**
  ```java
  // 나쁜 예
  for (int i = 0; i < 7; i++) {
      // 7이 무엇을 의미하는지 불명확
  }
  
  // 좋은 예
  private static final int DAYS_IN_WEEK = 7;
  for (int i = 0; i < DAYS_IN_WEEK; i++) {
      // 의미가 명확
  }
  ```

- **한 글자 변수명 지양 (반복문 인덱스 제외)**
  ```java
  // 나쁜 예
  String n = user.getName();
  
  // 좋은 예
  String userName = user.getName();
  ```

### 함수명 (Function Names)
- **동사나 동사구 사용**
  ```java
  // 좋은 예
  public void saveUser(User user) { }
  public boolean isValidEmail(String email) { }
  public String getUserName(Long userId) { }
  ```

- **함수명으로 기능 명확히 표현**
  ```java
  // 나쁜 예
  public void process() { }
  
  // 좋은 예
  public void processUserRegistration() { }
  ```

- **Boolean 반환 함수는 is, has, can 등으로 시작**
  ```java
  public boolean isActive() { }
  public boolean hasPermission() { }
  public boolean canAccess() { }
  ```

### 네이밍 컨벤션
- **camelCase 사용 (Java 표준)**
  ```java
  String firstName;
  void calculateTotalAmount();
  ```

- **상수는 UPPER_CASE_WITH_UNDERSCORES**
  ```java
  public static final int MAX_RETRY_COUNT = 3;
  public static final String DEFAULT_ENCODING = "UTF-8";
  ```

- **패키지명은 소문자**
  ```java
  package com.company.project.util;
  ```

- **클래스명은 PascalCase**
  ```java
  public class UserService { }
  public class OrderRepository { }
  ```

## 리팩토링과 함수 추출 고려사항

### 함수 추출의 장점과 단점

#### 장점
- 코드의 재사용성 증가
- 단일 책임 원칙 준수
- 가독성 향상 (적절히 사용될 때)
- 테스트 용이성 (각 함수별 독립 테스트 가능)

#### 단점과 주의사항
- **테스트의 블랙박스화**: 과도한 함수 추출로 테스트에서 내부 로직이 보이지 않음
- **가독성 저하**: 너무 작은 함수들로 분할하면 오히려 전체 흐름 파악이 어려움
- **성능 오버헤드**: 불필요한 함수 호출 스택 증가

### 함수 추출 시 고려사항

#### 1. 추출해야 할 때
```java
// 복잡한 비즈니스 로직이 섞여 있을 때
public void processOrder(Order order) {
    // 검증 로직 (추출 대상)
    if (order.getAmount() <= 0) {
        throw new IllegalArgumentException("Amount must be positive");
    }
    if (order.getCustomerId() == null) {
        throw new IllegalArgumentException("Customer ID is required");
    }
    
    // 계산 로직 (추출 대상)
    double tax = order.getAmount() * 0.1;
    double shipping = calculateShipping(order);
    double total = order.getAmount() + tax + shipping;
    
    // 저장 로직 (추출 대상)
    order.setTotal(total);
    orderRepository.save(order);
    auditService.logOrderCreation(order);
}

// 리팩토링 후
public void processOrder(Order order) {
    validateOrder(order);
    double total = calculateOrderTotal(order);
    saveOrderWithAudit(order, total);
}
```

#### 2. 추출하지 말아야 할 때
```java
// 나쁜 예: 과도한 함수 분할
public void processUser(User user) {
    setUserName(user);
    setUserEmail(user);
    setUserAge(user);
}

private void setUserName(User user) {
    user.setName(user.getName().trim());
}

private void setUserEmail(User user) {
    user.setEmail(user.getEmail().toLowerCase());
}

// 좋은 예: 관련된 로직을 하나로 묶기
public void processUser(User user) {
    user.setName(user.getName().trim());
    user.setEmail(user.getEmail().toLowerCase());
    user.setAge(calculateAge(user.getBirthDate()));
}
```

### 리팩토링 가이드라인

#### 함수 추출 기준
1. **함수가 20줄을 초과할 때** (경험적 기준)
2. **서로 다른 추상화 수준의 코드가 섞여 있을 때**
3. **재사용 가능한 로직일 때**
4. **복잡한 조건문이나 반복문을 포함할 때**

#### 테스트 고려사항
```java
// 함수 추출 전 - 테스트하기 어려움
public void complexProcess(Data data) {
    // 복잡한 로직 A
    // 복잡한 로직 B  
    // 복잡한 로직 C
}

// 함수 추출 후 - 각 단계별 테스트 가능
public void complexProcess(Data data) {
    Data processedA = processStepA(data);
    Data processedB = processStepB(processedA);
    processStepC(processedB);
}

// 단위 테스트에서 각 단계별 검증 가능
@Test
void testProcessStepA() {
    // processStepA만 독립적으로 테스트
}
```

#### 가독성을 위한 원칙
1. **함수명이 구현보다 의도를 나타내야 함**
2. **한 함수는 한 가지 일만 해야 함**
3. **함수의 추상화 수준을 일관되게 유지**
4. **매개변수는 3개 이하로 제한** (가능한 경우)

### 실용적 접근법

#### 점진적 리팩토링
```java
// 1단계: 기존 코드
public void handleUserRegistration(RegistrationRequest request) {
    // 30줄의 복잡한 로직
}

// 2단계: 큰 단위로 먼저 분리
public void handleUserRegistration(RegistrationRequest request) {
    validateRegistration(request);
    User user = createUser(request);
    sendWelcomeEmail(user);
}

// 3단계: 필요시 더 세분화
private void validateRegistration(RegistrationRequest request) {
    validateEmail(request.getEmail());
    validatePassword(request.getPassword());
    // 추가 검증 로직
}
```

## 결론

함수 추출은 코드의 품질을 향상시키는 중요한 도구이지만, **무작정 작게 나누는 것이 항상 좋은 것은 아닙니다**. 

### 핵심 원칙
1. **가독성과 테스트 용이성의 균형** 유지
2. **비즈니스 로직의 응집성** 고려
3. **팀의 코딩 스타일과 프로젝트 특성** 반영
4. **점진적 개선**을 통한 안정적인 리팩토링

함수 추출 시에는 항상 "이 변경이 코드를 더 이해하기 쉽게 만드는가?"와 "테스트가 더 명확해지는가?"를 자문해보고 결정하는 것이 중요합니다.