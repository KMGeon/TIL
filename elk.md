# ELK 로그 모니터링 설정

## Spring Boot 의존성
```gradle
implementation 'net.logstash.logback:logstash-logback-encoder:7.4'
```

## Docker Compose 설정
```yml
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.10.0
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
      - xpack.security.http.ssl.enabled=false
    ports:
      - "9200:9200"
    networks:
      - elastic-network

  logstash:
    image: docker.elastic.co/logstash/logstash:8.10.0
    container_name: logstash
    ports:
      - "5044:5044"
      - "9600:9600"
    volumes:
      - ./logstash.conf:/usr/share/logstash/pipeline/logstash.conf
    networks:
      - elastic-network
    depends_on:
      - elasticsearch

  kibana:
    image: kibana:8.10.1
    container_name: kibana
    ports:
      - "5601:5601"
    environment:
      - ELASTICSEARCH_HOSTS=http://elasticsearch:9200
    networks:
      - elastic-network
    depends_on:
      - elasticsearch

networks:
  elastic-network:
    driver: bridge
```

## Logstash 파이프라인 설정 (logstash.conf)
```ruby
input {
  beats {
    port => 5044
  }
  
  tcp {
    port => 5000  # 컨테이너 내부에서는 5000 포트 사용
    codec => json_lines
  }
}

filter {
  if [fields][logtype] == "application" {
    mutate {
      add_field => { "index_prefix" => "application-logs" }
    }
  } else {
    mutate {
      add_field => { "index_prefix" => "system-logs" }
    }
  }
  
  # 날짜 파싱
  date {
    match => [ "timestamp", "ISO8601" ]
  }
}

output {
  elasticsearch {
    hosts => ["http://elasticsearch:9200"]
    index => "%{[index_prefix]}-%{+YYYY.MM.dd}"
  }
  
  stdout {
    codec => rubydebug
  }
}
```

## Logstash 설정 (logstash.yml)
```yml
http.host: "0.0.0.0"
xpack.monitoring.enabled: false
xpack.monitoring.elasticsearch.hosts: ["http://elasticsearch:9200"]
```


```xml
<configuration>
    <property name="LOG_FILE" value="application.log"/>

    <!-- Logstash로 전송할 Appender -->
    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>localhost:5044</destination>  <!-- TCP 포트로 직접 연결 -->
        <encoder class="net.logstash.logback.encoder.LogstashEncoder" />
    </appender>

    <!-- 콘솔 출력 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 파일 출력 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>application.%d{yyyy-MM-dd_HH-mm}.log.gz</fileNamePattern>
            <maxHistory>5</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Logger 설정 -->
    <root level="info">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="LOGSTASH" />
        <!-- FILE appender 제거로 로컬 파일 생성 안함 -->
    </root>
</configuration>
```