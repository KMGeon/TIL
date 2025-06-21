# 📚 CSS 완전 정복 가이드

## 📋 핵심 요약
> **CSS는 HTML 문서의 구조화된 내용을 브라우저에서 시각적으로 표현하기 위한 스타일 시트 언어입니다. 선택자(Selector)를 통해 HTML 요소를 선택하고, 다양한 프로퍼티로 레이아웃, 색상, 폰트, 애니메이션 등을 제어할 수 있습니다.**

## 🧠 학습 목표
- [ ] CSS 기본 문법과 선택자 완전 이해
- [ ] Box Model과 레이아웃 시스템 마스터  
- [ ] 반응형 디자인과 단위 체계 활용
- [ ] Position과 Float를 이용한 배치 제어
- [ ] 애니메이션과 상호작용 구현

## 📖 본문 내용

### 🎯 HTML 기본 구조와 Semantic Tag
#### ⚡ 핵심 포인트
- HTML은 구조화된 문서를 위한 마크업 언어
- Semantic Tag는 의미있는 구조를 제공

#### 💡 기본 태그 구조
```html
<p>하나의 문단</p>

<!-- 순서가 있는 리스트 -->
<ol>
  <li>첫 번째 항목</li>
  <li>두 번째 항목</li>
</ol>

<!-- 순서가 없는 리스트 -->
<ul>
  <li>첫 번째 항목</li>
  <li>두 번째 항목</li>
</ul>
```

#### 🔍 Semantic Web Tag
| 태그 | 의미 |
|------|------|
| `<header>` | 헤더 영역 |
| `<nav>` | 내비게이션 영역 |
| `<aside>` | 사이드바 영역 |
| `<section>` | 본문의 여러 내용 포함 |
| `<article>` | 본문의 주요 내용 |
| `<footer>` | 하단 영역 |

### 🎯 CSS 기본 개념과 선택자
#### ⚡ 핵심 포인트
- CSS는 Cascading Style Sheets의 약자
- 선택자(Selector)를 통해 HTML 요소를 선택하고 스타일 적용

#### 💡 기본 선택자 종류
```css
/* 전체 선택자 */
* {
  color: black;
}

/* 클래스 선택자 */
.class-name {
  color: red;
}

/* ID 선택자 */
#id-name {
  color: blue;
}

/* 속성 선택자 */
[data-value="dave"] {
  color: green;
}
```

#### 🔍 복합 선택자
```css
/* 후손 선택자 (공백) */
div span { }

/* 자식 선택자 (>) */
div > span { }

/* 인접 형제 선택자 (+) */
h1 + h2 { }

/* 일반 형제 선택자 (~) */
h1 ~ p { }
```

### 🎯 CSS 크기 단위 시스템
#### ⚡ 핵심 포인트
- 절대 단위와 상대 단위의 적절한 선택이 중요
- 반응형 디자인을 위해 상대 단위 활용

#### 💡 주요 단위 비교
| 단위 | 특징 | 사용 예시 |
|------|------|----------|
| `px` | 절대 단위, 픽셀 기준 | `font-size: 16px` |
| `%` | 부모 요소 기준 상대 단위 | `width: 50%` |
| `em` | 현재 요소 폰트 크기 기준 | `margin: 1.5em` |
| `rem` | 루트 요소 폰트 크기 기준 | `padding: 2rem` |

#### 🔍 반응형 ViewPort 단위
```css
/* ViewPort 기준 단위 */
.container {
  width: 100vw;    /* 뷰포트 너비의 100% */
  height: 100vh;   /* 뷰포트 높이의 100% */
  font-size: 4vmin; /* 뷰포트 작은 쪽 기준 4% */
}
```

### 🎯 가상 클래스와 이벤트 선택자
#### ⚡ 핵심 포인트
- 사용자 상호작용에 따른 동적 스타일링
- 콜론(:)을 사용하여 특정 상태 선택

#### 💡 주요 가상 클래스
```css
/* 링크 상태 */
a:link { color: blue; }      /* 미방문 링크 */
a:visited { color: purple; }  /* 방문한 링크 */
a:hover { color: red; }      /* 마우스 오버 */
a:active { color: green; }   /* 클릭 중 */

/* 폼 요소 상태 */
input:focus { background: lightgray; }
input:disabled { opacity: 0.5; }
input:checked { transform: scale(1.1); }
```

#### 🔍 구조 가상 클래스
```css
/* 구조 기반 선택 */
p:first-child { }     /* 첫 번째 자식 */
p:last-child { }      /* 마지막 자식 */
p:nth-child(2) { }    /* 두 번째 자식 */
p:nth-last-child(3) { } /* 뒤에서 세 번째 */

/* 부정 선택자 */
h2:not(.test) { color: red; }
```

### 🎯 CSS Box Model 시스템
#### ⚡ 핵심 포인트
- 모든 HTML 요소는 박스 형태로 구성
- Content, Padding, Border, Margin으로 구성

#### 💡 Box Model 구조
```css
.box {
  /* Content 영역 */
  width: 200px;
  height: 100px;
  
  /* Padding (내부 여백) */
  padding: 20px;
  
  /* Border (테두리) */
  border: 2px solid #333;
  
  /* Margin (외부 여백) */
  margin: 10px;
  
  /* Box Sizing 설정 */
  box-sizing: border-box; /* 권장 설정 */
}
```

#### 🔍 Box Sizing 비교
```css
/* Content Box (기본값) */
.content-box {
  box-sizing: content-box;
  width: 200px; /* content 영역만 200px */
}

/* Border Box (권장) */
.border-box {
  box-sizing: border-box;
  width: 200px; /* content + padding + border = 200px */
}
```

### 🎯 Block vs Inline 요소
#### ⚡ 핵심 포인트
- Block 요소는 새 줄에서 시작하며 전체 너비 차지
- Inline 요소는 같은 줄에서 시작하며 내용만큼 너비 차지

#### 💡 요소 특성 비교
```css
/* Block 요소 특성 */
.block-element {
  display: block;
  width: 100%;          /* 전체 너비 */
  height: auto;         /* 내용에 따라 높이 */
  margin: 10px;         /* 모든 margin 적용 가능 */
  padding: 10px;        /* 모든 padding 적용 가능 */
}

/* Inline 요소 특성 */
.inline-element {
  display: inline;
  /* width, height 적용 불가 */
  /* margin top/bottom 적용 불가 */
  margin-left: 10px;    /* 좌우 margin만 적용 */
  padding: 5px;         /* padding은 적용되지만 레이아웃에 영향 없음 */
}

/* Inline-block (두 특성 모두) */
.inline-block-element {
  display: inline-block;
  width: 200px;         /* width, height 적용 가능 */
  height: 100px;
  margin: 10px;         /* 모든 margin 적용 가능 */
  vertical-align: top;  /* 수직 정렬 조절 */
}
```

### 🎯 Position 프로퍼티와 레이아웃
#### ⚡ 핵심 포인트
- 요소의 위치 결정 방식을 제어
- static, relative, absolute, fixed 네 가지 주요 값

#### 💡 Position 유형별 특성
```css
/* Static (기본값) */
.static {
  position: static;
  /* top, right, bottom, left 무시 */
}

/* Relative (상대 위치) */
.relative {
  position: relative;
  top: 20px;           /* 원래 위치에서 20px 아래로 */
  left: 30px;          /* 원래 위치에서 30px 오른쪽으로 */
}

/* Absolute (절대 위치) */
.absolute {
  position: absolute;
  top: 50px;           /* 가장 가까운 positioned 부모 기준 */
  right: 20px;
}

/* Fixed (고정 위치) */
.fixed {
  position: fixed;
  top: 0;              /* 뷰포트 기준 */
  width: 100%;
  background: white;
  z-index: 1000;       /* 다른 요소 위에 표시 */
}
```

#### 🔍 z-index와 레이어 관리
```css
.layer-1 { z-index: 1; }
.layer-2 { z-index: 2; }
.layer-3 { z-index: 3; }

/* 높은 숫자가 앞에 표시됨 */
```

### 🎯 텍스트와 폰트 스타일링
#### ⚡ 핵심 포인트
- 텍스트 오버플로우 처리와 폰트 크기 조절
- 반응형 폰트 설정

#### 💡 텍스트 오버플로우 처리
```css
.text-ellipsis {
  width: 200px;
  white-space: nowrap;    /* 줄바꿈 방지 */
  overflow: hidden;       /* 넘치는 부분 숨김 */
  text-overflow: ellipsis; /* ... 표시 */
}
```

#### 🔍 폰트 크기 설정
```css
.font-sizes {
  font-size: 16px;        /* 절대 단위 */
  font-size: 1rem;        /* 루트 기준 */
  font-size: 1.2em;       /* 부모 기준 */
  font-size: 4vw;         /* 뷰포트 기준 */
}
```

### 🎯 CSS 애니메이션
#### ⚡ 핵심 포인트
- @keyframes로 중간 상태 정의
- JavaScript보다 성능이 우수

#### 💡 애니메이션 구현
```css
/* 키프레임 정의 */
@keyframes slideIn {
  0% {
    transform: translateX(-100%);
    opacity: 0;
  }
  50% {
    transform: translateX(-10%);
    opacity: 0.8;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
}

/* 애니메이션 적용 */
.animated-element {
  animation: slideIn 0.5s ease-in-out;
}
```

### 🎯 Float와 수평 정렬
#### ⚡ 핵심 포인트
- 요소를 좌우로 띄워 배치
- 현재는 Flexbox나 Grid 사용 권장

#### 💡 Float 사용법
```css
.float-left {
  float: left;
  width: 50%;
}

.float-right {
  float: right;
  width: 50%;
}

/* Float 해제 */
.clearfix::after {
  content: "";
  display: table;
  clear: both;
}
```

## 🧪 실습/적용
### 🔧 실무 적용법
1. **Reset CSS 적용**: 브라우저 기본 스타일 초기화
2. **Box-sizing 설정**: 모든 요소에 border-box 적용
3. **반응형 단위 활용**: rem, vw, vh 등 상대 단위 사용
4. **Semantic 마크업**: 의미있는 HTML 구조 작성

### 🎮 연습 문제
1. 카드 레이아웃을 Box Model을 이용해 구현해보기
2. 네비게이션 메뉴에 호버 효과 추가하기
3. 반응형 그리드 시스템 만들어보기
4. 모달 창을 Position을 이용해 구현하기

## 🎯 핵심 정리
### 📌 반드시 기억할 3가지
1. **Box Model**: 모든 요소는 content, padding, border, margin으로 구성되며 box-sizing: border-box 사용 권장
2. **Position**: static(기본), relative(상대), absolute(절대), fixed(고정) 각각의 특성과 용도 이해
3. **선택자**: 적절한 선택자 사용으로 효율적인 스타일링과 유지보수성 확보

### 🚀 다음 학습 단계
- [ ] Flexbox 레이아웃 시스템 학습
- [ ] CSS Grid 고급 레이아웃 마스터
- [ ] CSS 전처리기 (Sass, Less) 활용
- [ ] CSS-in-JS와 모던 스타일링 기법

## 🧠 자가 점검
### ❓ 이해도 체크
1. Box Model의 4가지 구성 요소를 설명할 수 있나요?
2. Position의 4가지 값의 차이점을 이해하고 있나요?
3. Block과 Inline 요소의 특성을 구분할 수 있나요?
4. 선택자 우선순위를 알고 있나요?

### 💭 성찰 질문  
- 반응형 디자인을 위한 단위 선택 기준은 무엇인가요?
- Float 대신 Flexbox/Grid를 사용해야 하는 이유는?
- CSS 애니메이션과 JavaScript 애니메이션의 차이점은?

---
**[🔝 목차로 돌아가기](#📚-css-완전-정복-가이드)**