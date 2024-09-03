### [ [BUG] java.lang.StackOverflowError when method has more then 500 lines of code ]

### [Lombok 관련 링크](https://github.com/projectlombok/lombok/issues/1118)

[ 문제 발생 ]
- Jenkins에 배포를 하는 도중에 다음과 같은 에러가 발생

```shell
Caused by: java.lang.StackOverflowError
        at lombok.javac.JavacAST.buildStatementOrExpression(JavacAST.java:384)
        at lombok.javac.JavacAST.buildTree(JavacAST.java:196)
        at lombok.javac.JavacAST.buildTree(JavacAST.java:1)
        at lombok.core.AST.buildWithField0(AST.java:394)
        at lombok.core.AST.buildWithField(AST.java:292)
        at lombok.javac.JavacAST.drill(JavacAST.java:421)
        at lombok.javac.JavacAST.buildStatementOrExpression(JavacAST.java:387)
        at lombok.javac.JavacAST.buildTree(JavacAST.java:196)
        at lombok.javac.JavacAST.buildTree(JavacAST.java:1)
        at lombok.core.AST.buildWithField0(AST.java:394)
        at lombok.core.AST.buildWithField(AST.java:292)
        at lombok.javac.JavacAST.drill(JavacAST.java:421)
        at lombok.javac.JavacAST.buildStatementOrExpression(JavacAST.java:387)
        ...
```

- 처음에 메모리가 부족해서 그런줄 알고 `free -h`를 통해서 메모리, `ps aux | awk '{sum += $4} END {print "Total CPU Usage:", sum "%"}'`를 통해서 CPU를 측정함
- free -h 결과 t2.small을 사용하고 있어서 1.5에서 1.3을 사용하고 있었고 잔여 300이 남아있었음, CPU는 빌드를 하는 도중에는 90%까지 올라가서 단순히 스펙의 사양이 문제라고 생각함


[ 문제 고민 ]
- 일단 처음에 생각한 Spec의 문제라고 생각해서 t2.medium으로 증가
  - 하지만 똑같은 문제가 발생 > Jenkins를 배포하면서 CPU, 메모리 체크를 하였을 때 Spec의 문제가 아니라고 판단함

- 관련 이슈를 더 찾아보니 메모리 문제가 아니면 순환참조에 의해서 StackOverflow가 발생할 수 있다고 하여 Lombok을 사용하는 기능에서
- @Data, @ToString, @EqualsAndHashCode를 전체 바꿈
  - 그렇게 하여도 똑같은 문제라고 판단


[ 문제 해결 ]
- 롬북 깃허브에 들어가서 관련 이슈가 있는지 찾아봄
  - 나와 비슷한 문제가 발생한 사람들의 PR을 보고 결국에는 Xss를 증가시켜 JVM의 스택의 크기를 늘려서 해결함
  - 이 문제의 근본적인 원인은 Lombok의 AST(Abstract Syntax Tree) 처리 방식에 있다.
  - Lombok은 컴파일 시점에 소스 코드의 AST를 분석하고 수정하는데, 메서드의 코드 라인 수가 많아질수록 재귀적으로 AST를 탐색하는 과정에서 스택 오버플로우가 발생할 수 있다.
  - 특히 500줄 이상의 큰 메서드에서 이 문제가 발생한다..


- 결국 jenkins에서 build해서 jar를 gzip해서 s3 보내기 이전에 -Dorg.gradle.jvmargs로 젠킨스 파이프라인 코드를 변경함
```shell
sh './gradlew build -Dorg.gradle.jvmargs=-Xss4m -x test'
```

- 관련 라이브러리 문제들은 무지성 검색보다 한번 관련 PR이나 이슈를 체크하자 