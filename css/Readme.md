```
<p> 안녕 </p>
```

- 하나의 문단
```
<ol><ul><li>
%%  위에처럼 하면 1,2,3으로 처리되고 %%
<ol>
	<li></li>
<ol>
```

```
ul로 위에를 감싸면 - 으로 처리된다.
<ul>
	<li></li>
<ul>
```


| Semantic Web Tag | Meaning |
|------------------|---------|
| header           | 헤더를 의미 |
| nav              | 내비게이션을 의미 |
| aside            | 옆에 위치하는 부분을 의미 |
| section          | 본문의 여러 내용을 포함하는 부분의 의미 |
| article          | 본문의 주내용이 들어가는 부분을 의미 |
| footer           | 하단부를 의미 |

# CSS란 (Cascading Style Sheets)
- html로 구조화된 문서를 어떻게 브라우저 상에 렌더링할지 표현 방법을 정의하기 위한 언어


### Selector
- html 문서의 특정 부분에 렌더링 방법을 정의하기 위해서 선택할 수 있어애 한다.
    - 이것을 선택하는 것이 css selector

```
.course {
	font-size: 12px
}
```


### Reset Css
- 웹브라우저마다 각기 다른 default 스타일이 지정되어 있으므로, 이를 초기화하여, 다양한 웹브라우저에 서도 동일한 스타일로 표시되도록 하는 설정

```
nomarlize css
```


### CSS 크기 단위
1. px
    - 픽셀은 해상도에 따라 상대적인 크기를 가짐

2. %
    - 백분율 단위의 상대 단위, 지정 사이즈를 기반으로 상대적인 비율의 크기를 가짐

3.  em
    - 배수 단위, 지정 사이즈를 기반으로 배수로 계산된 크기를 가짐
    - 중첩된 자식 요소들이 바로 상위 단위에 *2

4. rem ( 가장 많이 사용함 )
    - rem은 root em으로 최상위 요소 사이즈 기준으로 함


- rem에서 기본적인  폰트를 기준으로 하기 때문에
```html
html{
	font-size: 16px;
}
	
```
![[Pasted image 20240526112453.png]]


### 반응형 ViewPort

- 반응형이란 하나의 웹사이트가 다양한 디바이스 사이즈에 맞추어서 자동으로 크기가 변하도록 하는 기능

### 단위

| vw   | viewport 너비의 1/100 (1%)       |
| ---- | ----------------------------- |
| vh   | viewport 높이의 1/100 (1%)       |
| vmin | viewport 너비 또는 높이 중 작은 쪽의  1% |
| vmax | viewport 너비 또는 높이 중 큰쪽의  1%   |

## CSS Selector

- 전체 selector
    - 전체의 요소를 적용한다.
```html
<style>
	*{
		color: black
	}
</style>
```


- content는 글자를 의미한다.
    -  padding은 border와의 거리를 의미한다.
        - box를 의미한다.
          ![[Pasted image 20240526113258.png]]


### class
- .클래스명으로 선택 (점)을 앞에 붙여서 class를 확인할 수 있다.

```html
.test{

color: black;

}
```

### attribute (속성) 선택자
- [속성]: attr 속성을 가지는 모든 요소


	- [속성=값]: attr 속성값이 정확히 속성을 가지는 태그, 대소문자를 구분하지 않는다.
	- [속성~=값]: attr속성값이 value를(공백으로 분리된) 단어가 포함하는 모든 요소

```html

# 속성 = 값 
  <style>    [data-value="dave"] {  
      color: red;  
    }  
  </style>  
<p data-value="dave">안녕하세요</p>  
<p data-value="geon">김무건</p>  


# 속성 ~= 값
- 1,2 둘다 빨간색
  <style>    [data-value~="dave"] {  
      color: red;  
    }  
  </style>  

<p data-value="dave">안녕하세요</p>  
<p data-value="geon  dave">김무건</p>  




```

|
![[Pasted image 20240526114105.png]]
![[Pasted image 20240526114352.png]]




## 다양한 Class selector 조합
- 태그, 아이디, 클래스 선택자를 조합해서 복합적으로 사용 가능
    - 예를 들어서 class='클래스명1 클래스명2 클래스명3
```html
  <style>  
    h1.dave.funcoding#geon{  
      color: red;  
    }  
  </style>    
<h1 class="dave funcoding" id="geon">안녕</h1>
```


### 복합 Selector
- 태그 안에, 또다른 태그를 넣을 수 있으므로, 각 태그 또는 요소 간에 부모/자식의 관계가 매겨짐
- 관계를 기반으로 HTML 문서 특정 부분을 선택할 수 있는 문법

> 후손 선택자 : 스페이스로 표시
> 자식 셀렉터 : >로 표시
> 인접 형제 선택자 : +로 표시
> 일반 형제 셀렉터 : ~로 표시


### 자식 선택자 (>)
- 부모 태그(select1)안에 있는 바로 다음 레벨의 태그 중에 selector2를 선택한다.
```html
# 여기서 살펴보면 div 바로 아래의 span만 바꾼다. ->   <span>span</span>  
<style>  
    h1 {  
      color: blue;  
    }  
  
    div span {  
      color: red;  
    }  
  
    div > span{  
      color: green;  
    }  
  </style>  
<div>  
  <h1>김무건</h1>  
  <p>백엔드 개발자</p>  
  <p><span>코딩하고 있어요</span></p>  
  <span>span</span>  
</div>  
```

### 인접 형제 셀렉터 (+)
- 태그 (selector1)와 동일 레벨에 위치하고 selector1의 바로 뒤에 위치하는 selector2를 선택한다.
    - 즉 selector1과 selector2사이에 다른 태그 위치시 선택 안됨

> 바로 뒤에 와야한다. 만약에 h1, h2, p 순으로 있으면 h1+p를 하면 안먹힌다.

```html
# selector1 + selector2

  <style>  
    h1 + h2{  
      color: red;  
    }  
  </style>  
</head>  
  
<body>  
  
<div>  
  <h1>김무건</h1>  
  <h2>h2</h2>  
  <p>백엔드 개발자</p>  
  <p><span>코딩하고 있어요</span></p>  
  <span>span</span>  
</div>  
</body>
```


### 일반 형제 선택자 (~)
- 위에 인접 형재는 바로 뒤에 와야지만 선택이 되었는데 ~ 형태를 가져갈 수 있도록 한다.
    - 즉 1,2사이에 다른 태그가 위치해도 괜찮다.

```html
  <style>  
    h1 ~ p{  
      color: red;  
    }  
  </style>  
</head>  
  
<body>  
  
<div>  
  <h1>김무건</h1>  
  <h2>h2</h2>  
  <p>백엔드 개발자</p>  
  <p><span>코딩하고 있어요</span></p>  
  <span>span</span>  
</div>  
</body>
```


## 가상 클래스 셀렉터 (이벤트)
- 가상 클래스는 요소에 특정 이벤트 발생시를 선택하는 문법
- 예 ) h1 태그 요소에 마우스가 올라갔을 때

### 가상 클래스 종류 (: 콜론을 사용한다.)
1. link : 방문하지 않은 링크가 적용하는 요소 (a태그로 링크가 적용된 요소를 한번도 클릭하지 않은 경우)
2. visited : 방문한 링크에 적용한 요소
3. hover : 특정 요소에 마우스가 올라간 경우
4. actice : 링크 요소를 클릭한 상태 (a태그로 링크가 적용된 요소를 마우스로 클릭하고 있는 상태)
5. focus : 특정 요소에 포커스를 하고 있는 상태 (input 태스에 포커스를 하고 있어서 깜빡임)

```html
  <style>  
    a:link{  
      color: black;  
      text-decoration: none;  
    }  
  
    a:visited{  
      color: rebeccapurple;  
      text-decoration: none;  
    }  
  
    a:hover{  
      color: red;  
      text-decoration: underline;  
    }  
  
    a:active{  
      color: blue;  
      text-decoration: underline;  
    }  
  
    input:focus{  
      background-color: lightgray;  
    }  
  </style>  
</head>  
<body>  
  
<div>  
  <a href="http://localhost:8080">기본 페이지</a>  
  
  <form>    <input type="text" name="name" placeholder="이름을 입력하세요">  
    <input type="text" name="age" placeholder="나이를 입력하세요">  
    <input type="submit" value="전송">  
  </form>
```


## UI 요소 상태 선택자

| enabled     | UI 셀렉터가 사용 가능한 상태                           |
| ----------- | ------------------------------------------- |
| disabled    | UI 셀렉터가 사용 불가능한 상태                          |
| checked     | UI 셀렉터가 체크된 상태                              |
| indetermina | UI 셀렉터의 상태가 결정되지 않은 상태 ( 라디오, 체크박스가 선택이 안됨) |


## 구조 가상 클래스 선택자
- 여러가지 자식 요소에서 몇번째 요소를 선택을 하는지 의미한다.

https://codesandbox.io/p/sandbox/css-structured-pseudo-class-selector1-b3r9x?file=%2Findex.html

| first-child       | 부모의 첫 번째 자식인 요소 선택             |
| ----------------- | ------------------------------ |
| last-child        | 부모의 마지막 자신 요소 선택               |
| nth-child(n)      | 부모의 첫 번째 자식 요소부터 시작해 n번째 요소 선택 |
| ntt-last-child(N) | 부모의 마지막 자식 요소부터 시작해 n번째 요소 선택  |
```html
<!doctype html>  
<html class="no-js" lang="">  
  
<head>  
  <meta charset="utf-8">  
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <style>    p:first-child {  
      color: red;  
    }  
  
    p:last-child {  
      color: blue;  
    }  
  
    p:nth-child(2) {  
      color: green;  
    }  
  
    p:nth-last-child(3) {  
      color: brown;  
    }  
  </style>  
</head>  
<body>  
<div>  
  <h1>Dave Lee</h1>  
  <h2>잔재미코딩</h2>  
  <div>    <p>잔재미코딩을 운영하고 있습니다. 현업 기획자 및 개발자입니다.</p>  
    <p>      <span>좋은 컨텐츠와 서비스</span>를 만드는 일에 집중하고 있습니다.  
    </p>  
    <p>      새로운 기술을 익힐 때는, 아주 쉬운 부분부터 집중해서 익숙해지도록!  
    </p>  
    <p>세세한 부분은 신경도 쓰지 말도록!</p>  
    <p>문제는 이슈가 있으면, 여러가지 테스트해보고!</p>  
    <p>문제는 이슈가 있으면, 여러가지 테스트해보고!</p>  
  </div>  <div>    <h2>first-child/last-child가 여기는 왜 안될까요?</h2>  
    <p>잔재미코딩을 운영하고 있습니다. 현업 기획자 및 개발자입니다.</p>  
    <p>      <span>좋은 컨텐츠와 서비스</span>를 만드는 일에 집중하고 있습니다.  
    </p>  
    <p>      새로운 기술을 익힐 때는, 아주 쉬운 부분부터 집중해서 익숙해지도록!  
    </p>  
    <p>세세한 부분은 신경도 쓰지 말도록!</p>  
    <p>문제는 이슈가 있으면, 여러가지 테스트해보s고!</p>  
    <p>문제는 이슈가 있으면, 여러가지 테스트해보고!</p>  
    <h2>first-child/last-child가 여기는 왜 안될까요?</h2>  
  </div></div>  
</body>  
  
</html>  
<!--  <script src="js/app.js"></script>-->
```

### 부정 선택자 (not)
- not - 셀렉터에 해당하지 않는 모든 요소 선택

```html
  <style>  
    h2:not(.test){  
      color: red;  
    }  
  </style>  
</head>  
<body>  
  <div>    <h2>제목</h2>  
    <h2 class="test">test</h2>  
  </div></body>
```


### 정합성 체크 선택자
- valid : 정합성이 검증된 input 또는 form 요소 선택
- invalid  : 정합성 검증이 실패한 input 또는 form 요소 선택

> required 속성
> input 태그로 생성된 입력창에 데이터를 무조건 넣어야함 ( 데이터가 없으면 검증이 안되었다고 나온다.)

```html
    input[type="text"]:invalid {  
      background: rebeccapurple;  
    }  
  
    input[type="text"]:valid {  
      background: lightgreen;  
    }  
  </style>  
</head>  
<body>  
  <form>    <input type="text" id="id" name="id" required>  
    <input type="submit"/>  
  </form>
```

## 1. CSS Box Model
- block 또는 inline-block 특성을 가지는 요소는 box형태를 가지며, box 형태의 세부 사항을 수정할 수 있음

![[Pasted image 20240526122459.png]]


### 박스 모델 프로퍼티
---

| content | 요소의 텍스트나 이미지 등 실제 내용이 위치하는 영역, width, height 프로퍼티를 가짐        |     |
| ------- | ------------------------------------------------------------ | --- |
| padding | border 안쪽에 위치하는 내부 여백 영역, padding 프로퍼티는 **패딩 영역의 두깨를 의미**한다. |     |
| border  | 테두리 영역, border 프러퍼티 값으로 테두리 두께                               |     |
| margin  | border 바깥의 위치하는                                              |     |

### 주요 프로퍼티

### Width/height 프로퍼티
- box-sizing  프로퍼티는 디폴트로 content-box가 지정되어 있음
    - 이 경우 width/height는 기본적으로 content 영역의 너비와 높이가 됨

- box-sizing 프로퍼티가 border-box로 지정되어 있으면 content + padding + border 영역의 너비와 높이가 됨 (margin은 제외)
    - 대부분 border box로 설정한다.
```html
box-sizing: border-box;
```


- width/ height 포함, 모든 box model 프로퍼티는 상속이 되지 않는다.


> max-width height 프로퍼티는 브라우저에 따라서 다르다.



###  Margin, Padding 프로퍼티
- margin 또는 padding에 top, right , bottom, left에 붙여진다.
    - 순서대로 사용하는 것이 단축 프로퍼티

- 1개의 프로퍼티만 사용한다.
  `margin: 10px` 전체에 적용된다.

> Block 특성을 가진 요소에 대한 중앙 정렬
> ```html
> width: 100px
> # 가운데 지정
> margin-left: auto;
> margin-right: auto;


```html
블럭요소 : <div>,<table>,<h1>~<h6>,<p>,<form>,<ul>,<ol>,<li>,<dl>,<dt>,<dd>,<pre>,<blockquote>등

인라인요소 : <span>,<a>,<br>,<em>,<strong>,<input>,<label>,<img>
```

### border 프로퍼티
- border-style : 선 스타일 설정 : https://developer.mozilla.org/ko/docs/Web/CSS/border-style
- border-width : 선 굵기 설정
    - 1px과 thin, medium, thick의 키워드
        1. border-bottom-width
        2. border-left-width
        3. border-right-width
        4. border-top-width
- border의 solid의 줄이 두깨가 달라짐
- border-width는 border-style과 함께 설정되어야 사용 가능하다.

```html
  <style>  
    div{  
    width: 1000px;  
      background-color: green;  
      border-style: solid;  
      border-top-width: thin;  
    }  
  </style>  
</head>  
<body>  
  
</body>  
<div>  
  김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건김무건  
</div>  
</html>
```


- border-radius
    - 요소 테두리 경계의 꼭지점을 둥글게 설정한다.
    - 버튼을 만드는데 많이 사용한다.

### box-sizing
- width, height 대상 영역 설정
```html
*,
*::before,
*::after {
	box-sizing: border-box;
}
```


## CSS block, inline

> block
- 항생 새로운 라인에 표시 ( 밑에 라인에 )
    - div, span 태그를 비교

- 화면 너비 전체를 차지한다. (자동으로 width: 100%, height: auto)
- width, height, margin, padding 프로퍼티 가능하다.
- block요소 안에 inline 요소 포함이 가능하다.
```
div
h1~h6
p,ol,ul,li
hr,table, form
```


> inline
- 새로운 라인세서 시작하지 않고 동일한 라인에서 시작한다.
- `width, height, margin, padding 프로퍼티 불가능하다.`
- inline 요소 안에 block이 있을 수 없다.

```html
span
a
strong
img
br
input
select
textarea
button
```


### CSS display 프로퍼티는 요소의 display 프로퍼티를 설정

- 모든 html 태그는 각 태그마다 디폴트로 block 또는 inline 특징을 가지지만 display를 통하여 변경이 가능하다.

| block        | block 특성을 가지는 요소로 지정        |
| ------------ | --------------------------- |
| inline       | inline 특성을 가지는 요소로 지정       |
| inline-block | inline-block 특성을 가지는 요소로 지정 |
| none         | 지운다.                        |

- ![[Pasted image 20240526183639.png]]

display:none을 하면 화면에서 안보이지만 영역을 차지한다. 이때 display: none으로 해결할 수 있다.


### inline-block
- block과 inline 모두 가짐
- 한 라인에 표현되고, margin, height, margin, padding의 프로퍼티가 가능하다.


## Font 프로퍼티

### font-size
- 문자 크기 설정

1. medium (디폴트)
2. xx-small, x-small, small, large, x-large
3. smaller, larger : 부모 요소를 기반하여
4. 일반적으로 rem을 많이 사용한다.


# text-overflow 프로퍼티
- 문자열이 넘칠 경우 ( 부모 영역을 벗어날 경우)에 자동으로 줄바꿈이 되지 않은 문자열 처리 설정
1. width 프로퍼티 설정
2. white-spce: nowrap 설정
3. overflow 프로퍼티 설정

> text-overflow 프로퍼티 값
- clip : 텍스트 잘라냄
- clipsis: ...표시

```html
white-space: nowrap;  
overflow: hidden;  
text-overflow: ellipsis;
```

## 1. position  프로퍼티
- html위치를 결정하는 방식

### 1. 정적 위치 ( 디폴트 )
- 다른 태그와의 관계에 의해서 자동으로 배치

### 2. 상대 위치
- relative 미설정시 원래 있어야 하는 위치를 기준으로, 좌표를 지정할 수 있음
- 문서의 일반적인 흐름에 포함됨
```html
.box2 {  
  background: #5fb6d9;  
  position: relative;  
  top: 50px;  
  left: 50px;  
}
```
![[Pasted image 20240527205321.png]]
- 원래 inline-block처럼 빨간색 위치에 가야하는데 거기서 top, left 이동


### 3. 절대 위치 (absolute position)
- 가장 가까운 위치에 있는 position 속성이 relative인 부모 요소를 기준으로, 위치의 요소가 변경한다.
    - 다른 요소가 먼저 위치를 점유해도 덮어씌우게 됨
```html
.box3 {  
  background: #d90404;  
  position: absolute;  
  top: 50px;  
  left: 50px;  
}
```

![[Pasted image 20240527205155.png]]
- 빨간색을 보면 body를 기준으로 50px씩 띄운것을 볼 수 있다. 만약에 다른 객체 안으로 들어가면 그 객체를 기준으로

### 4. 고정 위치
- position을 viewport의 특정 영역에 위치를 시킴
- 다른 객체가 자리를 차지할 수 있다.
```html
.box4 {  
  background: #3b9cd9;  
  position: fixed;  
  top: 50px;  
  left: 400px;  
}
```



## z-index, overflow 프로퍼티

---

### z-index 프로퍼티에 큰 숫자값을 지정할수록 전면에 출력
	position 프로퍼티가 static 이외의 요소에 적용



![[Pasted image 20240527205701.png]]

```html
    div {  
      display: inline-block;  
      width: 200px;  
      height: 200px;  
      border: 2px solid #f2b441;  
      border-radius: 10px;  
    }  
  
    .box1 {  
      background: green;  
      position: absolute;  
  
    }  
  
    .box2 {  
      background: #5fb6d9;  
      position: absolute;  
      top: 10px;  
      left: 10px;  
    }  
  
    .box3 {  
      background: #d90404;  
      position: absolute;  
      top: 15px;  
      left: 15px;  
    }  
  
    .box4 {  
      background: #3b9cd9;  
      position: fixed;  
      top: 20px;  
      left: 20px;  
    }  
  </style>  
</head>  
<body>  
<div class="box1" style="z-index: 1"></div>  
<div class="box2" style="z-index: 2"></div>  
<div class="box3"></div>  
<div class="box4"></div>
```


## overflow ( hidden 많이 사용함)
- 자식 요소가 부모 요소를 벗어날 때 사용하는 방식

| visible | 벗어난 부분 표시                 |
| ------- | ------------------------- |
| hidden  | 벗어난 부분 짤라냄                |
| scroll  | 부모 영역을 벗어난 부분이 없어도 스크롤 표시 |
| auto    | 부모 영역을 벗어나면 스크롤 표시        |
![[Pasted image 20240527210210.png]]



## CSS 애니메이션

- 중간에 변경되는 스타일을 세밀하게 지정할 수 있음
- 중간 상태를 나타내는 @keyframes로 이루어짐

- js보다 랜더링 속도가 좋다.
- ![[Pasted image 20240527211549.png]]

### keyframes 문법
- from 또는 0%에 설정한 스타일에서 출발해서 to 또는 100%까지, 아니면 중간 시점을 %표기하여, 각 시점에 설정한 스타일로 변경되는 스타일을 설정할 수 있음

```css
@keyfames ball{
	0%{
		top: 0;
	}
	50%{
		top: 100px;
	}
	100%{
		top: 300px;
	}
}
```


##  CSS float 프로퍼티 ( 수평 정렬 )
- 수평 정렬을 위해서 float, flexbox, grid를 사용할 수 있다.
- float를 이용하여 수평 정렬을 지향해야 한다. -> 복잡한 룰렛 현상으로

| right | 요소를 오른쪽으로 |
| ----- | --------- |
| left  |           |
![[Pasted image 20240527215440.png]]


- float 프로퍼티로 설정한 요소는 기본적으로 display block 특성을 가진다.
    - display가 flex 설정한 경우 제외

- block 특성을 가진 요소는 width를 설정하지 않을 경우 100%가 되는데 반해,
    - float으로 설정한 요소는 요소의 크기만큼 width설정되어 display 프로퍼티가 마치 inline-block처럼 선언한 것처럼 동작