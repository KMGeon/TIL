# Thread-Safe Collections

## 목차
1. [컬렉션의 동시성 문제](#1-컬렉션의-동시성-문제)
2. [synchronized 래퍼를 이용한 해결](#2-synchronized-래퍼를-이용한-해결)
3. [java.util.concurrent 패키지](#3-javautilconcurrent-패키지)
4. [주요 Thread-Safe 컬렉션들](#4-주요-thread-safe-컬렉션들)
5. [성능 비교 및 선택 가이드](#5-성능-비교-및-선택-가이드)
6. [베스트 프랙티스](#6-베스트-프랙티스)

---

## 1. 컬렉션의 동시성 문제

### 일반 컬렉션의 Thread Safety 문제

일반적인 Java 컬렉션(ArrayList, HashMap, HashSet 등)은 **thread-safe하지 않습니다**. 멀티스레드 환경에서 동시에 접근할 때 다음과 같은 문제가 발생할 수 있습니다:

#### 1.1 데이터 손실 (Data Loss)

```java
// ArrayList에 대한 동시 접근
List<Integer> list = new ArrayList<>();

// Thread 1
for (int i = 0; i < 1000; i++) {
    list.add(i);  // 데이터 손실 가능
}

// Thread 2  
for (int i = 1000; i < 2000; i++) {
    list.add(i);  // 데이터 손실 가능
}
```

**내부 동작:**
```
ArrayList 내부 배열 크기 조정 과정에서 데이터 손실

Thread 1                    Thread 2
┌─────────────────────┐    ┌─────────────────────┐
│ size = 5, add(100)  │    │ size = 5, add(200)  │
│ elementData[5]=100  │    │ elementData[5]=200  │  ← 덮어씀
│ size = 6            │    │ size = 6            │
└─────────────────────┘    └─────────────────────┘
                     
최종 결과: size=6이지만 실제로는 하나의 값만 저장됨
```

#### 1.2 무한 루프 (Infinite Loop)

HashMap에서 동시 수정 시 링크드 리스트가 순환 구조를 형성할 수 있습니다:

```java
Map<String, String> map = new HashMap<>();

// 동시에 많은 요소를 추가하면서 해시 충돌 발생
// rehashing 과정에서 링크드 리스트의 순환 참조 생성
```

**문제 발생 시나리오:**
```
HashMap rehashing 과정에서 순환 참조

정상 상태:           문제 상태:
A → B → null        A ←→ B (순환)

Thread 1: rehashing 중 A의 next를 B로 설정
Thread 2: 동시에 B의 next를 A로 설정
결과: get() 호출 시 무한 루프 발생
```

#### 1.3 ConcurrentModificationException

Iterator 사용 중 컬렉션이 수정되면 예외가 발생합니다:

```java
List<Integer> list = new ArrayList<>();
list.addAll(Arrays.asList(1, 2, 3, 4, 5));

// Thread 1: 반복
for (Integer item : list) {
    System.out.println(item);
    // Thread 2가 이 시점에 list 수정하면 예외 발생
}

// Thread 2: 수정
list.add(6);  // ConcurrentModificationException 발생 가능
```

---

## 2. synchronized 래퍼를 이용한 해결

### 2.1 Collections.synchronizedXXX() 사용

Java는 기존 컬렉션을 thread-safe하게 만드는 synchronized 래퍼를 제공합니다:

```java
// synchronized 래퍼 생성
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
```

### 2.2 내부 동작 원리

```java
// Collections.synchronizedList 내부 구현 (단순화)
static class SynchronizedList<E> implements List<E> {
    final List<E> list;
    final Object mutex;
    
    SynchronizedList(List<E> list) {
        this.list = list;
        this.mutex = this;
    }
    
    public boolean add(E e) {
        synchronized (mutex) {
            return list.add(e);
        }
    }
    
    public E get(int index) {
        synchronized (mutex) {
            return list.get(index);
        }
    }
    
    // 모든 메서드가 synchronized
}
```

### 2.3 Iterator 사용 시 주의사항

synchronized 래퍼도 Iterator 사용 시 외부 동기화가 필요합니다:

```java
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());

// 잘못된 예 - 여전히 ConcurrentModificationException 가능
for (Integer item : syncList) {
    System.out.println(item);
}

// 올바른 예 - 명시적 동기화
synchronized (syncList) {
    for (Integer item : syncList) {
        System.out.println(item);
    }
}
```

### 2.4 synchronized 래퍼의 한계

```
성능 한계:
┌──────────────────────────────────────┐
│ Thread 1  │ Thread 2  │ Thread 3   │
├─────────────────────────────────────┤
│ add() 대기 │ get() 대기 │ size() 대기 │
│           │           │            │
│     모든 작업이 순차적으로 실행      │
│        → 동시성 활용 불가          │
└──────────────────────────────────────┘
```

**문제점:**
- **성능 저하**: 모든 작업이 단일 락으로 직렬화
- **읽기 작업도 블로킹**: get(), size() 등도 동기화됨
- **복합 연산 문제**: 여러 메서드 호출이 원자적이지 않음

---

## 3. java.util.concurrent 패키지

Java 5부터 도입된 `java.util.concurrent` 패키지는 고성능 thread-safe 컬렉션을 제공합니다.

### 3.1 주요 설계 원칙

#### Lock-Free 알고리즘
```java
// CAS (Compare-And-Swap) 기반 구현
public boolean compareAndSet(int expect, int update) {
    // 하드웨어 수준의 원자적 연산
    // expect 값과 현재 값이 같으면 update로 변경
}
```

#### 세밀한 락킹 (Fine-grained Locking)
```
HashMap vs ConcurrentHashMap:

HashMap (synchronized):           ConcurrentHashMap:
┌─────────────────────┐          ┌─────┬─────┬─────┬─────┐
│   전체 테이블        │          │seg 0│seg 1│seg 2│seg 3│
│      하나의 락       │          │ 락0 │ 락1 │ 락2 │ 락3 │
└─────────────────────┘          └─────┴─────┴─────┴─────┘
     모든 작업 직렬화                각 세그먼트 독립적 락
```

#### 읽기 최적화
```java
// 읽기 작업은 락 없이 수행
public V get(Object key) {
    // volatile 읽기로 최신 값 보장
    // 락 없이 빠른 접근
}
```

### 3.2 Memory Consistency 보장

```java
// happens-before 관계 보장
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Thread 1
map.put("key", 42);        // 쓰기 작업

// Thread 2  
Integer value = map.get("key");  // 42를 정확히 읽음 (memory visibility 보장)
```

---

## 4. 주요 Thread-Safe 컬렉션들

### 4.1 ConcurrentHashMap

가장 널리 사용되는 thread-safe Map 구현체입니다.

#### 내부 구조 (Java 8+)

```
ConcurrentHashMap 내부 구조:

Bucket Array:
┌─────┬─────┬─────┬─────┐
│ [0] │ [1] │ [2] │ [3] │
└──┬──┴──┬──┴──┬──┴──┬──┘
   │     │     │     │
   ▼     ▼     ▼     ▼
  Node  Node  TreeNode  null
   │     │      │
   ▼     ▼      ▼
  Node  null   TreeNode (Red-Black Tree)
   │            │
   ▼            ▼
  null       TreeNode
```

#### 주요 메서드와 특징

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// 기본 연산 - 모두 thread-safe
map.put("key1", 100);
Integer value = map.get("key1");
map.remove("key1");

// 원자적 연산들
map.putIfAbsent("key2", 200);           // 키가 없을 때만 추가
map.replace("key2", 200, 300);          // 기존 값이 200이면 300으로 변경
map.compute("key3", (k, v) -> v == null ? 1 : v + 1);  // 원자적 계산

// 벌크 연산 (병렬 처리)
map.forEach(parallelismThreshold, (k, v) -> System.out.println(k + ":" + v));
map.search(parallelismThreshold, (k, v) -> v > 100 ? k : null);
```

#### 성능 특징

```
동시성 레벨별 성능:

읽기 작업:     ████████████████████ (락 없이 수행)
쓰기 작업:     ████████████████     (세그먼트별 락)
복합 연산:     ███████████████████  (CAS 기반)
Iterator:      ████████████████████ (weakly consistent)
```

### 4.2 CopyOnWriteArrayList

읽기가 많고 쓰기가 적은 경우에 최적화된 List입니다.

#### 동작 원리

```java
public class CopyOnWriteArrayList<E> implements List<E> {
    private volatile Object[] array;
    
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            // 배열 전체를 복사하여 새로운 배열 생성
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);  // volatile 쓰기
            return true;
        } finally {
            lock.unlock();
        }
    }
    
    public E get(int index) {
        return (E) getArray()[index];  // 락 없이 읽기
    }
}
```

#### 메모리 사용 패턴

```
CopyOnWriteArrayList 쓰기 과정:

초기 상태:          add("D") 후:
┌─────────────┐    ┌─────────────┐    ┌───────────────────┐
│ [A] [B] [C] │ →  │ [A] [B] [C] │    │ [A] [B] [C] [D] │
└─────────────┘    └─────────────┘    └───────────────────┘
  기존 배열           기존 배열            새 배열
  (읽기 중)          (참조 유지)         (새 참조 설정)

Iterator는 스냅샷을 보므로 ConcurrentModificationException 없음
```

#### 사용 시나리오

```java
// 적합한 경우: 읽기 95%, 쓰기 5%
CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();

// 리스너 등록 (드물게 발생)
listeners.add(new MyEventListener());

// 이벤트 발생 시 (자주 발생)
for (EventListener listener : listeners) {  // 락 없이 안전한 반복
    listener.onEvent(event);
}
```

### 4.3 ConcurrentLinkedQueue

Lock-free 알고리즘을 사용하는 무한 큐입니다.

#### 내부 구조

```
ConcurrentLinkedQueue 구조:

head                    tail
 │                       │
 ▼                       ▼
┌────┐    ┌────┐    ┌────┐    ┌────┐
│ A  │───▶│ B  │───▶│ C  │───▶│null│
└────┘    └────┘    └────┘    └────┘

offer() 과정 (CAS 사용):
1. tail의 next가 null인지 확인
2. CAS로 원자적으로 새 노드 추가
3. tail 포인터 업데이트 시도
```

#### CAS 기반 구현

```java
public boolean offer(E e) {
    checkNotNull(e);
    final Node<E> newNode = new Node<E>(e);

    for (Node<E> t = tail, p = t;;) {
        Node<E> q = p.next;
        if (q == null) {
            // CAS로 새 노드 추가 시도
            if (p.casNext(null, newNode)) {
                // 성공 시 tail 업데이트
                if (p != t) {
                    casTail(t, newNode);
                }
                return true;
            }
        } else if (p == q) {
            // 재시작
            p = (t != (t = tail)) ? t : head;
        } else {
            // tail 따라잡기
            p = (p != t && t != (t = tail)) ? t : q;
        }
    }
}
```

### 4.4 BlockingQueue 계열

Producer-Consumer 패턴에 최적화된 큐들입니다.

#### ArrayBlockingQueue
```java
// 고정 크기 큐
BlockingQueue<Task> queue = new ArrayBlockingQueue<>(100);

// Producer
queue.put(task);  // 큐가 가득 차면 대기

// Consumer  
Task task = queue.take();  // 큐가 비어있으면 대기
```

#### LinkedBlockingQueue
```java
// 가변 크기 큐 (옵션으로 최대 크기 제한 가능)
BlockingQueue<String> queue = new LinkedBlockingQueue<>();

// Non-blocking 연산
boolean success = queue.offer("item");  // 즉시 반환
String item = queue.poll();             // 즉시 반환 (null 가능)

// Timeout 지원
boolean added = queue.offer("item", 1, TimeUnit.SECONDS);
String retrieved = queue.poll(1, TimeUnit.SECONDS);
```

#### PriorityBlockingQueue
```java
// 우선순위 큐
PriorityBlockingQueue<Task> pQueue = new PriorityBlockingQueue<>();

// Task는 Comparable 구현하거나 Comparator 제공
class Task implements Comparable<Task> {
    int priority;
    
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }
}
```

---

## 5. 성능 비교 및 선택 가이드

### 5.1 성능 벤치마크

```
읽기 성능 (ops/sec, 높을수록 좋음):
ConcurrentHashMap    ████████████████████ 10M
CopyOnWriteArrayList ████████████████████ 10M (작은 크기)
synchronized Map     ████████             4M
Vector               ███████              3.5M

쓰기 성능 (ops/sec):
ConcurrentHashMap    ████████████████     8M
synchronized Map     ████████             4M
CopyOnWriteArrayList ███                  1.5M (복사 비용)
Vector               ███████              3.5M

메모리 사용량:
Vector               █                    1x (기준)
synchronized Map     █                    1.1x
ConcurrentHashMap    ██                   2x (내부 구조)
CopyOnWriteArrayList █████                5x (쓰기 시 복사)
```

### 5.2 선택 기준표

| 컬렉션 타입 | 읽기/쓰기 비율 | 동시성 수준 | 메모리 | 적합한 사용 케이스 |
|------------|----------------|-------------|--------|-------------------|
| **ConcurrentHashMap** | 균형 (60:40~80:20) | 높음 | 중간 | 일반적인 캐시, 설정 저장소 |
| **CopyOnWriteArrayList** | 읽기 중심 (95:5+) | 높음 | 높음 | 이벤트 리스너, 설정 리스트 |
| **ConcurrentLinkedQueue** | 쓰기 중심 | 매우 높음 | 낮음 | 작업 큐, 메시지 전달 |
| **ArrayBlockingQueue** | Producer-Consumer | 중간 | 낮음 | 고정 크기 버퍼 |
| **synchronized Collection** | 저동시성 | 낮음 | 낮음 | 레거시 시스템 |

### 5.3 실제 사용 사례별 추천

#### 웹 애플리케이션 캐시
```java
// 세션 캐시 - 읽기/쓰기 균형
ConcurrentHashMap<String, UserSession> sessionCache = new ConcurrentHashMap<>();

// 설정 캐시 - 읽기 중심
CopyOnWriteArrayList<ConfigItem> configCache = new CopyOnWriteArrayList<>();
```

#### 작업 처리 시스템
```java
// 작업 큐 - Producer-Consumer 패턴
BlockingQueue<WorkItem> workQueue = new LinkedBlockingQueue<>(1000);

// 결과 수집 - 높은 동시성
ConcurrentLinkedQueue<Result> results = new ConcurrentLinkedQueue<>();
```

#### 이벤트 처리 시스템
```java
// 이벤트 리스너 - 읽기 중심
CopyOnWriteArrayList<EventListener> listeners = new CopyOnWriteArrayList<>();

// 이벤트 큐 - 빠른 추가/제거
ConcurrentLinkedQueue<Event> eventQueue = new ConcurrentLinkedQueue<>();
```

---

## 6. 베스트 프랙티스

### 6.1 올바른 컬렉션 선택

#### 읽기/쓰기 패턴 분석
```java
// ❌ 잘못된 선택
// 쓰기가 빈번한데 CopyOnWriteArrayList 사용
CopyOnWriteArrayList<LogEntry> logs = new CopyOnWriteArrayList<>();
for (int i = 0; i < 10000; i++) {
    logs.add(new LogEntry());  // 매번 전체 배열 복사!
}

// ✅ 올바른 선택  
// 쓰기가 빈번하면 ConcurrentLinkedQueue
ConcurrentLinkedQueue<LogEntry> logs = new ConcurrentLinkedQueue<>();
for (int i = 0; i < 10000; i++) {
    logs.offer(new LogEntry());  // Lock-free로 빠른 추가
}
```

### 6.2 초기 용량 설정

```java
// ConcurrentHashMap 초기 용량 설정
int expectedSize = 1000;
int initialCapacity = (int) (expectedSize / 0.75f) + 1;  // load factor 고려
ConcurrentHashMap<String, Data> map = new ConcurrentHashMap<>(initialCapacity);

// ArrayBlockingQueue 적절한 크기 설정
BlockingQueue<Task> queue = new ArrayBlockingQueue<>(
    Runtime.getRuntime().availableProcessors() * 2  // CPU 코어 수의 2배
);
```

### 6.3 Iterator vs Stream vs forEach

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// ✅ forEach 사용 - weakly consistent, 예외 안전
map.forEach((key, value) -> {
    System.out.println(key + ": " + value);
});

// ✅ Stream 사용 - 병렬 처리 가능
map.entrySet().parallelStream()
   .filter(entry -> entry.getValue() > 100)
   .forEach(entry -> process(entry));

// ⚠️ Iterator 사용 - weakly consistent이지만 외부 수정에 주의
Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, Integer> entry = it.next();
    // 다른 스레드가 map을 수정해도 예외 없음 (스냅샷 기반)
}
```

### 6.4 복합 연산 처리

```java
ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

// ❌ Race condition 가능
AtomicInteger counter = counters.get("key");
if (counter == null) {
    counter = new AtomicInteger(0);
    counters.put("key", counter);  // 다른 스레드가 이미 put했을 수 있음
}
counter.incrementAndGet();

// ✅ 원자적 연산 사용
counters.computeIfAbsent("key", k -> new AtomicInteger(0))
        .incrementAndGet();

// ✅ 더 간단한 방법
counters.compute("key", (k, v) -> new AtomicInteger(v == null ? 1 : v.get() + 1));
```

### 6.5 메모리 누수 방지

```java
// ❌ Strong reference로 인한 메모리 누수
ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
cache.put("key", heavyObject);  // GC되지 않음

// ✅ WeakReference 사용
ConcurrentHashMap<String, WeakReference<Object>> cache = new ConcurrentHashMap<>();
cache.put("key", new WeakReference<>(heavyObject));

// ✅ 또는 Caffeine 같은 캐시 라이브러리 사용
Cache<String, Object> cache = Caffeine.newBuilder()
    .maximumSize(1000)
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .build();
```

### 6.6 성능 모니터링

```java
// ConcurrentHashMap 통계 수집 (Java 8+)
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

// 매핑 수
long mappingCount = map.mappingCount();

// 키 검색 (병렬)
String result = map.search(1, (key, value) -> 
    key.startsWith("prefix") ? key : null);

// 통계 정보 출력
System.out.println("Size: " + map.size());
System.out.println("Mapping count: " + mappingCount);
```

### 6.7 예외 처리

```java
BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

// Producer
try {
    queue.put(task);  // InterruptedException 가능
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();  // 인터럽트 상태 복원
    return;
}

// Consumer with timeout
try {
    Task task = queue.poll(1, TimeUnit.SECONDS);
    if (task != null) {
        process(task);
    } else {
        // 타임아웃 처리
        handleTimeout();
    }
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    return;
}
```

---

## 결론

Thread-safe 컬렉션 선택 시 고려사항:

1. **동시성 요구사항**: 동시 접근하는 스레드 수
2. **읽기/쓰기 패턴**: 읽기 중심인지 쓰기 중심인지
3. **성능 요구사항**: 처리량 vs 지연시간
4. **메모리 제약**: 사용 가능한 메모리 크기
5. **일관성 요구사항**: Strong vs Weak consistency

적절한 컬렉션 선택과 올바른 사용법을 통해 멀티스레드 환경에서 안전하고 효율적인 애플리케이션을 구현할 수 있습니다.