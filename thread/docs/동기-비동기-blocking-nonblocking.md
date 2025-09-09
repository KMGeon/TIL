# 동기/비동기 vs Blocking/Non-blocking

## 핵심 개념 정리

### 동기(Synchronous) vs 비동기(Asynchronous)
- **호출한 함수가 결과를 언제 받느냐**의 문제
- 동기: 호출 즉시 결과를 받음
- 비동기: 호출 후 나중에 결과를 받음

### Blocking vs Non-blocking
- **호출한 함수가 제어권을 언제 돌려주느냐**의 문제  
- Blocking: 작업이 완료될 때까지 제어권을 돌려주지 않음
- Non-blocking: 작업 완료 여부와 관계없이 바로 제어권을 돌려줌

---

## 4가지 조합 시각화

### 1. 동기 + Blocking

```
클라이언트                    서버
    |                        |
    | ---- 요청 보냄 ------>   |
    |                        | (처리중...)
    | (대기중...)             | 
    | (제어권 없음)            |
    |                        |
    | <---- 응답 받음 ------   | (처리 완료)
    | (제어권 복구)            |
    |                        |
```

**특징:**
- 요청 후 응답이 올 때까지 대기
- 제어권이 없어서 다른 일을 할 수 없음
- 일반적인 함수 호출 방식

```java
// 예시: 일반적인 HTTP 요청
String result = httpClient.get("https://api.com/data");
// 이 줄이 실행되기 전까지 위 요청이 완료되어야 함
System.out.println(result);
```

### 2. 비동기 + Non-blocking

```
클라이언트                    서버
    |                        |
    | ---- 요청 보냄 ------>   |
    | (즉시 제어권 돌아옴)      | (백그라운드 처리)
    |                        |
    | (다른 작업 수행)         |
    |                        |
    | <---- 콜백/알림 ------   | (처리 완료시)
    | (결과 처리)              |
```

**특징:**
- 요청 후 바로 제어권을 받아 다른 작업 가능
- 결과는 나중에 콜백이나 Future로 받음
- 가장 효율적인 방식

```java
// 예시: CompletableFuture
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return httpClient.get("https://api.com/data");
});

// 다른 작업 수행 가능
doOtherWork();

// 나중에 결과 처리
future.thenAccept(result -> System.out.println(result));
```

### 3. 동기 + Non-blocking

```
클라이언트                    서버
    |                        |
    | ---- 요청 보냄 ------>   |
    | <---- 즉시 리턴 ------   | (아직 준비 안됨)
    |                        |
    | ---- 다시 요청 ------>   |
    | <---- 즉시 리턴 ------   | (아직 준비 안됨)
    |                        |
    | ---- 다시 요청 ------>   |
    | <---- 결과 리턴 ------   | (준비됨!)
```

**특징:**
- 결과가 준비될 때까지 계속 확인(폴링)
- 제어권은 바로 돌아오지만 결과는 기다려야 함
- CPU 자원을 낭비할 수 있음

```java
// 예시: NIO의 폴링 방식
while (true) {
    String result = channel.tryRead();
    if (result != null) {
        System.out.println(result);
        break;
    }
    // 다른 작업 수행
    doOtherWork();
    Thread.sleep(100); // 잠깐 대기 후 다시 시도
}
```

### 4. 비동기 + Blocking

```
클라이언트                    서버
    |                        |
    | ---- 요청 보냄 ------>   |
    |                        | (백그라운드 처리)
    | (대기중...)             |
    | (제어권 없음)            |
    |                        |
    | <---- 알림 받음 ------   | (처리 완료)
    | (제어권 복구)            |
    |                        |
```

**특징:**
- 비동기로 요청했지만 결과를 기다리며 블록됨
- 비효율적인 패턴 (잘못된 설계)
- 비동기의 장점을 살리지 못함

```java
// 예시: 비동기 Future를 동기적으로 기다리는 경우
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return httpClient.get("https://api.com/data");
});

// 즉시 get()으로 결과를 기다림 (블록킹)
String result = future.get(); // 비동기의 의미가 없어짐
```

---

## 실제 시나리오 비교

### 카페 주문 예시

#### 동기 + Blocking (일반적인 카페)
```
고객: "아메리카노 하나 주세요"
     ↓
바리스타: "네, 잠깐만 기다리세요"
     ↓
고객: (카운터 앞에서 대기) ← 다른 일 못함
     ↓
바리스타: "아메리카노 나왔습니다"
     ↓  
고객: 커피 수령
```

#### 비동기 + Non-blocking (진동벨 카페)
```
고객: "아메리카노 하나 주세요"
     ↓
바리스타: "진동벨 드릴게요, 준비되면 알려드립니다"
     ↓
고객: 진동벨 받고 자리에 가서 다른 일 함 ← 자유롭게 활동
     ↓
진동벨: 삐삐삐~ (커피 완성 알림)
     ↓
고객: 커피 수령
```

### Java I/O 예시

#### 전통적인 I/O (Blocking)
```java
// 파일 읽기 - 완전히 읽을 때까지 대기
FileInputStream fis = new FileInputStream("large-file.txt");
byte[] data = fis.readAllBytes(); // 블록킹
processData(data);
```

```
Thread                       File System
  |                             |
  | ---- read() 요청 -------->   |
  | (대기...)                    | (디스크 읽기)
  | (CPU 놀고 있음)              |
  |                             |
  | <---- 데이터 리턴 ---------   | (완료)
  | processData() 실행           |
```

#### NIO (Non-blocking)
```java
// 채널 방식 - 준비될 때까지 폴링
FileChannel channel = FileChannel.open(path);
ByteBuffer buffer = ByteBuffer.allocate(1024);

while (channel.read(buffer) == 0) {
    // 데이터가 준비되지 않았으면 다른 작업 수행
    doOtherWork();
}
processData(buffer.array());
```

```
Thread                       File System
  |                             |
  | ---- read() 시도 -------->   |
  | <---- 0 리턴 (준비안됨) ---   | (디스크 읽기 중)
  | doOtherWork() 실행           |
  |                             |
  | ---- read() 재시도 ------>   |
  | <---- 데이터 리턴 ---------   | (완료)
  | processData() 실행           |
```

---

## Java에서의 실제 구현

### 1. Blocking I/O (전통적인 방식)
```java
public class BlockingExample {
    public void readFile() {
        try {
            // 파일이 완전히 읽힐 때까지 대기
            byte[] data = Files.readAllBytes(Paths.get("file.txt"));
            
            // 이 라인은 위 작업이 완료된 후에만 실행됨
            System.out.println("파일 크기: " + data.length);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 2. Non-blocking I/O (NIO)
```java
public class NonBlockingExample {
    public void readFileNonBlocking() {
        try {
            Path path = Paths.get("file.txt");
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            
            // 비동기 읽기 - 즉시 리턴
            Future<Integer> result = channel.read(buffer, 0);
            
            // 다른 작업 수행 가능
            System.out.println("파일 읽기 시작됨, 다른 작업 수행 중...");
            doOtherWork();
            
            // 결과 확인 (논블록킹 체크)
            while (!result.isDone()) {
                System.out.println("아직 읽기 중...");
                Thread.sleep(100);
                // 계속 다른 작업 가능
            }
            
            Integer bytesRead = result.get();
            System.out.println("읽은 바이트: " + bytesRead);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void doOtherWork() {
        // CPU를 활용한 다른 작업들
        for (int i = 0; i < 1000; i++) {
            Math.sqrt(i);
        }
    }
}
```

### 3. 완전 비동기 (콜백 방식)
```java
public class AsyncCallbackExample {
    public void readFileAsync() {
        try {
            Path path = Paths.get("file.txt");
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            
            // 콜백으로 완전 비동기 처리
            channel.read(buffer, 0, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("파일 읽기 완료: " + result + " bytes");
                    // 결과 처리 로직
                }
                
                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("파일 읽기 실패: " + exc.getMessage());
                }
            });
            
            // 메인 스레드는 즉시 다른 작업 수행
            System.out.println("파일 읽기 요청 완료, 메인 작업 계속 진행");
            doMainWork();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## 성능 특성 비교

### 처리량 (Throughput) 관점

```
동기 + Blocking
요청1: |████████| (8초)
요청2:         |████████| (8초)  
요청3:                 |████████| (8초)
총 시간: 24초, 처리량: 3요청/24초

비동기 + Non-blocking  
요청1: |████████| (8초)
요청2: |████████| (8초)
요청3: |████████| (8초)
총 시간: 8초, 처리량: 3요청/8초
```

### 자원 사용 관점

#### Blocking 방식
```
Thread Pool (크기: 200)
├── Thread-1: [BLOCKED] - DB 쿼리 대기중
├── Thread-2: [BLOCKED] - HTTP 응답 대기중  
├── Thread-3: [BLOCKED] - 파일 I/O 대기중
├── ...
└── Thread-200: [BLOCKED] - 네트워크 대기중

메모리 사용량: 200 * 1MB = 200MB (스레드 스택)
실제 CPU 사용률: 5% (대부분 대기상태)
```

#### Non-blocking 방식
```
Thread Pool (크기: 10)
├── Thread-1: [RUNNABLE] - 이벤트 루프 실행중
├── Thread-2: [RUNNABLE] - 콜백 처리중
├── Thread-3: [RUNNABLE] - 비즈니스 로직 실행중
├── ...
└── Thread-10: [RUNNABLE] - 요청 처리중

메모리 사용량: 10 * 1MB = 10MB
실제 CPU 사용률: 90% (효율적 활용)
```

---

## 언제 어떤 방식을 사용할까?

### 동기 + Blocking 👍
- **간단한 순차 작업**
- **디버깅이 쉬워야 하는 경우**
- **작은 규모 애플리케이션**

```java
// 설정 파일 읽기 (애플리케이션 시작시 한번)
Properties config = loadConfig("application.properties");
String dbUrl = config.getProperty("db.url");
```

### 비동기 + Non-blocking 👍
- **높은 동시성이 필요한 경우**
- **I/O 집약적인 작업**
- **마이크로서비스 아키텍처**

```java
// 여러 서비스 동시 호출
CompletableFuture<User> userFuture = userService.getUserAsync(userId);
CompletableFuture<Order> orderFuture = orderService.getOrdersAsync(userId);
CompletableFuture<Product> productFuture = productService.getProductsAsync();

// 모든 결과를 조합
CompletableFuture.allOf(userFuture, orderFuture, productFuture)
    .thenRun(() -> {
        // 모든 데이터가 준비되면 실행
        renderUserDashboard(userFuture.join(), orderFuture.join(), productFuture.join());
    });
```

### 동기 + Non-blocking 😐
- **폴링이 필요한 상황**
- **실시간성이 중요하지만 콜백을 쓸 수 없는 경우**

```java
// 게임 루프에서 키보드 입력 체크
while (gameRunning) {
    KeyEvent event = keyboard.pollEvent(); // non-blocking
    if (event != null) {
        handleKeyEvent(event);
    }
    
    updateGame();
    renderFrame();
    Thread.sleep(16); // 60 FPS
}
```

### 비동기 + Blocking 🚫
- **피해야 할 패턴**
- **비동기의 장점을 무효화**

```java
// 잘못된 예시 - 하지 말 것!
CompletableFuture<String> future = asyncService.getData();
String result = future.get(); // 비동기를 동기로 만듦
```

---

## 실전 예제: 웹 크롤러

### Blocking 방식 (전통적)
```java
public class BlockingWebCrawler {
    public List<String> crawlUrls(List<String> urls) {
        List<String> results = new ArrayList<>();
        
        for (String url : urls) {
            try {
                // 각 URL을 순차적으로 처리
                String content = httpClient.get(url); // 블로킹
                String processed = processContent(content);
                results.add(processed);
                
                System.out.println("처리 완료: " + url);
            } catch (Exception e) {
                System.out.println("실패: " + url);
            }
        }
        
        return results;
    }
}

// 100개 URL 처리시: 100 * 2초 = 200초
```

### Non-blocking 방식 (CompletableFuture)
```java
public class AsyncWebCrawler {
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    
    public CompletableFuture<List<String>> crawlUrlsAsync(List<String> urls) {
        List<CompletableFuture<String>> futures = urls.stream()
            .map(this::crawlSingleUrl)
            .collect(Collectors.toList());
        
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }
    
    private CompletableFuture<String> crawlSingleUrl(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String content = httpClient.get(url);
                String processed = processContent(content);
                System.out.println("처리 완료: " + url);
                return processed;
            } catch (Exception e) {
                System.out.println("실패: " + url);
                return null;
            }
        }, executor);
    }
}

// 100개 URL 처리시: max(2초) = 2초 (병렬 처리)
```

---

## 정리

| 구분 | 제어권 반환 | 결과 수신 | 특징 | 사용 사례 |
|------|-------------|-----------|------|-----------|
| **동기 + Blocking** | 작업 완료 후 | 즉시 | 가장 직관적 | 간단한 순차 처리 |
| **비동기 + Non-blocking** | 즉시 | 나중에 콜백 | 가장 효율적 | 고성능 서버 |
| **동기 + Non-blocking** | 즉시 | 폴링으로 확인 | 폴링 오버헤드 | 게임, 실시간 시스템 |
| **비동기 + Blocking** | 작업 완료 후 | 나중에 | 비효율적 패턴 | 피해야 할 패턴 |

**핵심 원칙:**
1. **I/O 집약적** → 비동기 + Non-blocking
2. **CPU 집약적** → 동기 + Blocking (적절한 스레드 풀)  
3. **간단한 로직** → 동기 + Blocking
4. **높은 동시성** → 비동기 + Non-blocking
