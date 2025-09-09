# 🧵 Java Thread 학습 정리

> **Java 멀티스레드 프로그래밍 학습 내용 정리**

---

## 📖 소개

Java 멀티스레드 프로그래밍을 공부하면서 정리한 내용들입니다. 기초 개념부터 고급 패턴까지 실제 코드와 함께 정리했습니다.

### 📝 정리한 내용
- Java Thread 생명주기와 상태 관리
- 메모리 가시성과 volatile 키워드
- Thread Join과 동기화 메커니즘  
- 인터럽트 처리와 스레드 종료 방법
- 동시성 컬렉션과 동기화 패턴
- Executor 프레임워크와 Future를 활용한 비동기 처리
- ReentrantLock과 Condition을 활용한 락 제어

---

## 📚 정리한 문서들

### 🔰 **기초 스레드 관리**

#### 1. [🧬 Thread 생명주기](docs/Thread_LifeCycle.md)
> **Java Thread의 상태와 전환 과정**

- **Thread State 상세 분석**: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
- **실전 시나리오별 상태 전환**: 코드 예제와 함께 실제 동작 이해
- **RUNNABLE 상태 심화**: Running vs Ready 내부 상태 분석
- **일시 중지 상태 비교**: BLOCKED vs WAITING vs TIMED_WAITING 차이점
- **메서드별 상태 변화**: join(), sleep(), wait() 등 메서드 영향 분석
- **성능 고려사항**: 상태 전환 비용과 최적화 방법

#### 2. [🔗 Thread Join 완전 가이드](docs/Join.md)
> **스레드 간 동기화와 완료 대기의 모든 것**

- **Join 개념과 동작 원리**: 내부 메커니즘과 wait/notify 구조
- **실전 시나리오별 활용**: 순차 실행, 병렬 작업 결과 수집, 타임아웃 처리
- **다른 동기화 메커니즘 비교**: CountDownLatch, Future, CompletableFuture와 비교
- **주의사항과 함정**: 데드락 위험과 인터럽트 처리
- **고급 패턴**: 조건부 Join, 진행률 모니터링, Graceful Shutdown
- **성능 최적화**: 효율적인 Join 사용법과 대안 방법

#### 3. [⚡ Thread 인터럽트 완전 가이드](docs/인터럽트.md)
> **스레드 중단과 인터럽트 처리의 모든 것**

- **변수 vs 인터럽트 중단 비교**: 즉시 반응과 지연 반응 차이
- **인터럽트 동작 메커니즘**: 플래그 설정과 예외 발생 시점
- **isInterrupted() vs interrupted()**: 상태 확인과 리셋의 차이
- **Thread.yield() 활용**: CPU 효율 개선과 스레드 양보
- **실행 시나리오 분석**: 타임라인별 상태 변화와 성능 비교
- **베스트 프랙티스**: 안전한 인터럽트 처리와 조합 사용법

### 🧠 **메모리 모델과 가시성**

#### 4. [🧠 Memory Visibility & Volatile 완전 가이드](docs/volatile.md)
> **메모리 가시성 문제와 volatile 키워드의 완전한 이해**

- **메모리 가시성 개념**: CPU 캐시와 메인 메모리 구조
- **가시성 문제 실제 분석**: 문제 발생 조건과 해결 과정
- **volatile 동작 원리**: 캐시 우회와 메인 메모리 직접 접근
- **Java Memory Model**: happens-before 규칙과 메모리 순서 보장
- **사용 가이드라인**: 적절한 사용 시나리오와 한계

### 🔐 **고급 동기화와 락**

#### 5. [🔒 Wait-Notify 메커니즘 완전 가이드](docs/wait-notify.md)
> **객체 모니터를 활용한 스레드 간 통신과 동기화**

- **Wait-Notify 동작 원리**: Object 모니터와 Entry Set, Wait Set 구조
- **상태 전환 메커니즘**: WAITING → BLOCKED → RUNNABLE 과정 분석
- **Producer-Consumer 패턴**: 생산자-소비자 문제 해결 방법
- **notify() vs notifyAll()**: 선택 기준과 성능 비교
- **데드락과 기아 상태**: 문제점과 해결 방안

#### 6. [🔐 Concurrent Lock 완전 가이드](docs/concurrentLock.md)
> **ReentrantLock을 활용한 고급 락 제어**

- **synchronized vs ReentrantLock**: 차이점과 선택 기준
- **공정한 락(Fair Lock)**: FIFO 순서 보장과 성능 트레이드오프
- **타임아웃 락**: tryLock()을 활용한 데드락 방지
- **인터럽트 가능한 락**: 응답성 향상과 취소 가능한 작업
- **락 상태 모니터링**: 디버깅과 성능 분석 도구
- **베스트 프랙티스**: 안전한 락 사용과 리소스 관리

#### 7. [🎯 Lock & Condition 고급 패턴](docs/LockCondition.md)
> **ReentrantLock과 Condition을 활용한 정교한 스레드 제어**

- **Condition의 필요성**: synchronized + wait/notify의 한계점
- **Multiple Condition**: 조건별 대기 큐 분리와 활용
- **BlockingQueue 구현**: 생산자-소비자 패턴의 완전한 구현

### 📦 **Thread-Safe 컬렉션**

#### 8. [🛡️ Thread-Safe Collections 완전 가이드](docs/threadSafeCollections.md)
> **동시성 환경에서 안전한 컬렉션 사용법**

- **컬렉션 동시성 문제**: 데이터 손실, 무한 루프, ConcurrentModificationException
- **synchronized 래퍼**: Collections.synchronizedXXX()의 사용법과 한계
- **java.util.concurrent 패키지**: Lock-free 알고리즘과 세밀한 락킹
- **주요 컬렉션 상세**:
  - **ConcurrentHashMap**: 세그먼트 기반 동시성과 성능 특징
  - **CopyOnWriteArrayList**: 읽기 중심 환경에서의 최적화
  - **ConcurrentLinkedQueue**: Lock-free 큐와 CAS 알고리즘
  - **BlockingQueue 계열**: Producer-Consumer 패턴 구현체들
- **성능 비교와 선택 가이드**: 사용 시나리오별 최적 컬렉션 선택
- **베스트 프랙티스**: 올바른 사용법과 성능 최적화

### ⚡ **비동기 처리와 Executor**

#### 9. [🚀 Executor Framework 완전 가이드](docs/Executor.md)
> **스레드 풀과 작업 관리의 현대적 접근법**

- **직접 스레드 생성의 문제점**: 성능, 관리, 인터페이스의 한계
- **Executor 프레임워크**: 스레드 풀과 작업 분리의 장점
- **ExecutorService 구현체들**:
  - **ThreadPoolExecutor**: 코어/최대 스레드, 큐 전략, 거부 정책
  - **FixedThreadPool**: 고정 크기 스레드 풀
  - **CachedThreadPool**: 동적 스레드 관리
  - **SingleThreadExecutor**: 순차 실행 보장
- **작업 제출과 관리**: execute() vs submit(), shutdown() vs shutdownNow()

#### 10. [🔮 Future & CompletableFuture 완전 가이드](docs/Future.md)
> **비동기 작업의 결과 처리와 조합**

- **Runnable vs Callable**: 반환값과 예외 처리의 차이
- **Future 동작 원리**: FutureTask와 내부 상태 관리
- **Future 활용 패턴**: 병렬 처리와 성능 최적화
- **Future 주의사항**: 올바른 사용법과 흔한 실수들
- **Future API 완전 분석**: cancel(), get(), isDone() 등 메서드 상세
- **작업 컬렉션 처리**: invokeAll(), invokeAny() 활용법
- **Future vs CompletableFuture**: 차이점과 발전 방향

---

## 📋 핵심 내용 흐름 요약

### 1️⃣ **Thread의 기초와 한계**

**Thread 생명주기**부터 시작합니다. Thread는 `NEW → RUNNABLE → TERMINATED` 과정을 거치지만, 중간에 `BLOCKED`, `WAITING`, `TIMED_WAITING` 상태로 일시 중지될 수 있습니다.

**Thread 직접 사용의 문제점:**
- Thread 생성 비용이 높음 (메모리, CPU 오버헤드)
- 무제한 Thread 생성 시 시스템 리소스 고갈
- Runnable은 반환값이 없고 예외 처리 어려움

### 2️⃣ **메모리 가시성 문제의 발견**

멀티스레드 환경에서 **한 Thread가 변경한 값이 다른 Thread에게 즉시 보이지 않는 문제**가 발생합니다. 이는 CPU 캐시 때문입니다.

```java
// Thread1에서 변경
volatile boolean flag = true;  // volatile 없으면 Thread2가 못 봄

// Thread2에서 확인  
while(flag) { ... }  // 무한루프 가능성
```

**해결책:** `volatile` 키워드로 메인 메모리 직접 접근

### 3️⃣ **Thread 간 협력의 필요성: Join**

**Join**을 통해 Thread 완료를 기다릴 수 있습니다. 이는 내부적으로 `wait-notify` 메커니즘을 사용합니다.

```java
// 단순한 완료 대기
thread.join();  // Thread 완료까지 WAITING 상태

// 타임아웃이 있는 대기  
thread.join(1000);  // 1초까지만 대기, TIMED_WAITING 상태
```

**Join의 한계:** 단순한 완료 대기만 가능, 복잡한 조건부 대기는 어려움

### 4️⃣ **안전한 Thread 종료: 인터럽트**

**변수 기반 중단**의 문제점을 발견합니다:

```java
volatile boolean flag = true;
while(flag) {
    sleep(3000);  // flag=false로 해도 최대 3초 지연
}
```

**인터럽트 기반 해결:**
```java
try {
    while(true) {
        work();
        sleep(1000);  // InterruptedException 발생 시 즉시 중단
    }
} catch(InterruptedException e) {
    // 즉시 반응하여 종료
}
```

**핵심:** `interrupt()`는 WAITING/TIMED_WAITING 상태의 Thread를 즉시 깨워 빠른 종료 가능

### 5️⃣ **동기화 문제와 Wait-Notify**

**synchronized**만으로는 협력적 대기가 어렵습니다. 락을 잡고 있으면서 조건을 기다리면 **무한 대기**에 빠집니다.

```java
public synchronized void put() {
    while(queue.isFull()) {
        // 락을 잡고 있어서 소비자가 못 들어옴 → 데드락
    }
}
```

**해결책:** `wait()`로 락을 해제하며 대기, `notify()`로 깨우기

```java
while(queue.isFull()) {
    wait();  // 락 해제하고 WAITING 상태로
}
// notify()로 다른 Thread 깨우기
```

### 6️⃣ **synchronized의 한계와 ReentrantLock**

`synchronized`의 문제점들이 드러납니다:
- **락 대기 시간 제한 불가** - 무한 대기 위험
- **공정하지 않은 락** - 먼저 온 Thread가 먼저 락을 얻는다는 보장 없음
- **인터럽트 불가** - 락 대기 중 interrupt() 무시

**ReentrantLock으로 해결:**
```java
ReentrantLock lock = new ReentrantLock(true);  // 공정한 락

// 타임아웃 있는 락 시도
if(lock.tryLock(500, TimeUnit.MILLISECONDS)) {
    try {
        // 임계영역
    } finally {
        lock.unlock();  // 반드시 unlock
    }
} else {
    // 락 획득 실패 처리
}
```

**장점:** 타임아웃 지원, 공정성 보장, 인터럽트 응답 가능

### 7️⃣ **더 정교한 제어: Lock & Condition**

`synchronized + wait/notify`는 모든 대기 Thread를 하나의 큐에서 관리합니다. **조건별로 분리된 대기**가 필요할 때 한계가 있습니다.

**ReentrantLock + Condition**으로 해결:
```java
Condition notFull = lock.newCondition();   // 큐가 찰 때 대기
Condition notEmpty = lock.newCondition();  // 큐가 빌 때 대기

// 조건별로 깨우기 가능
notFull.signal();   // 큐가 찬 상태를 기다리는 Thread만 깨움
notEmpty.signal();  // 큐가 빈 상태를 기다리는 Thread만 깨움
```

**핵심:** 조건별로 분리된 대기 큐 → 더 효율적인 Thread 협력

### 8️⃣ **Thread-Safe한 자료구조의 필요**

일반 컬렉션(`ArrayList`, `HashMap`)은 멀티스레드 환경에서 **데이터 손실, 무한루프, ConcurrentModificationException** 등의 문제가 발생합니다.

**진화 과정:**
1. `Collections.synchronizedList()` - 모든 메서드를 synchronized로 래핑 (성능 저하)
2. `ConcurrentHashMap` - 세그먼트별 락으로 동시성 향상
3. `CopyOnWriteArrayList` - 읽기는 락 없이, 쓰기 시에만 복사
4. `BlockingQueue` - 생산자-소비자 패턴에 최적화

### 9️⃣ **Thread 관리의 현대적 해법: Executor**

Thread를 직접 생성하는 대신 **Thread Pool**을 사용합니다:

```java
// 기존 방식의 문제
new Thread(task).start();  // 매번 생성 비용
new Thread(task2).start(); // 무제한 생성 위험

// Executor 사용
ExecutorService executor = Executors.newFixedThreadPool(4);
executor.submit(task);   // Thread 재사용
executor.submit(task2);  // 개수 제한
```

**장점:** Thread 생성 비용 절약, 개수 제한, 생명주기 관리

### 🔟 **비동기 작업의 결과 처리: Future**

Runnable은 반환값이 없어서 결과를 받기 어려웠습니다. **Callable + Future**로 해결:

```java
// 기존 방식 - 결과 받기 복잡
class ResultHolder { volatile int result; }
ResultHolder holder = new ResultHolder();
Thread t = new Thread(() -> holder.result = calculate());
t.start(); t.join();
int result = holder.result;

// Future 방식 - 간단
Future<Integer> future = executor.submit(() -> calculate());
int result = future.get();  // 완료 대기 + 결과 받기
```

**Future의 핵심:** "먼저 모든 작업을 제출하고, 나중에 결과를 받기" - 병렬 처리 성능 향상

### 🔄 **전체 흐름**

1. **Thread 생명주기** → 기본 상태 이해, 직접 사용의 한계 발견
2. **volatile** → 메모리 가시성 문제 해결  
3. **join** → 단순한 Thread 완료 대기
4. **인터럽트** → 안전하고 빠른 Thread 종료 방법
5. **wait-notify** → synchronized 내에서 조건부 대기로 협력적 동기화
6. **ReentrantLock** → synchronized의 한계 극복 (타임아웃, 공정성, 인터럽트)
7. **Lock-Condition** → 조건별 분리된 대기 큐로 더 정교한 제어
8. **Thread-Safe Collections** → 안전한 자료구조로 동시성 문제 해결
9. **Executor** → Thread Pool로 현대적 Thread 관리
10. **Future** → 비동기 결과 처리의 완성

**핵심:** 각 단계는 이전 단계의 문제점을 해결하며 발전합니다. Thread의 기본 한계부터 시작해서 최종적으로는 안전하고 효율적인 멀티스레드 프로그래밍의 완전한 해법에 도달합니다.
