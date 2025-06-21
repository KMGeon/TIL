# 📚 CSS 레이아웃 마스터 가이드

## 📋 핵심 요약
> **CSS 레이아웃의 핵심은 Display와 Position 속성입니다. Display는 요소의 렌더링 방식을 결정하고, Position은 요소의 배치 방법을 제어합니다. 이 두 속성을 완전히 이해하면 모든 레이아웃 문제를 해결할 수 있습니다.**

## 🧠 학습 목표
- [ ] Display 속성의 모든 값과 동작 원리 완전 이해
- [ ] Position 속성을 이용한 정교한 레이아웃 제어 마스터
- [ ] Flexbox와 Grid의 고급 패턴 구현 능력 획득
- [ ] 실무에서 자주 사용되는 레이아웃 패턴 숙달
- [ ] 반응형 레이아웃 설계 전략 수립

## 📖 본문 내용

### 🎯 Display 속성 완전 분석
#### ⚡ 핵심 포인트
- Display는 요소가 어떻게 렌더링될지 결정하는 가장 중요한 속성
- Block, Inline, Flex, Grid, Table 각각의 고유한 레이아웃 모델 존재
- 내부 디스플레이와 외부 디스플레이 개념 구분 필요

#### 💡 Display 값별 상세 분석

##### 🔹 Block Display
```css
.block-container {
  display: block;
  
  /* Block 요소의 특징 */
  width: 100%;           /* 사용 가능한 전체 너비 */
  height: auto;          /* 내용에 따른 높이 */
  margin: 20px 0;        /* 상하좌우 margin 모두 적용 */
  padding: 15px;         /* 모든 방향 padding 적용 */
  
  /* 새로운 줄에서 시작 */
  clear: both;
}

/* Block 요소 예시 */
.card {
  display: block;
  max-width: 400px;
  margin: 20px auto;     /* 수평 중앙 정렬 */
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}
```

##### 🔹 Inline Display
```css
.inline-container {
  display: inline;
  
  /* Inline 요소의 제약 */
  /* width: 200px;  ❌ 적용 안됨 */
  /* height: 100px; ❌ 적용 안됨 */
  
  /* 좌우 margin/padding만 레이아웃에 영향 */
  margin: 0 10px;        /* 좌우만 효과적 */
  padding: 5px 10px;     /* 상하는 시각적으로만 */
  
  /* 줄바꿈 없이 연속 배치 */
  vertical-align: baseline;
}

/* Inline 요소 실용 예시 */
.badge {
  display: inline;
  background: #007bff;
  color: white;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 0.8em;
}
```

##### 🔹 Inline-Block Display (하이브리드)
```css
.inline-block-container {
  display: inline-block;
  
  /* Block의 장점: 크기 조절 가능 */
  width: 200px;
  height: 100px;
  margin: 10px;
  padding: 15px;
  
  /* Inline의 장점: 같은 줄에 배치 */
  vertical-align: top;   /* 수직 정렬 조절 */
}

/* 카드 그리드 예시 */
.card-grid .card {
  display: inline-block;
  width: calc(33.333% - 20px);
  margin: 10px;
  vertical-align: top;
}
```

#### 🔍 Flexbox 마스터 패턴
```css
/* 기본 Flex Container */
.flex-container {
  display: flex;
  
  /* 주축 방향 설정 */
  flex-direction: row;      /* row | column | row-reverse | column-reverse */
  
  /* 주축 정렬 */
  justify-content: center;  /* flex-start | center | space-between | space-around | space-evenly */
  
  /* 교차축 정렬 */
  align-items: center;      /* stretch | flex-start | center | flex-end | baseline */
  
  /* 줄바꿈 설정 */
  flex-wrap: wrap;          /* nowrap | wrap | wrap-reverse */
  
  /* 간격 설정 (최신) */
  gap: 20px;               /* row-gap: 20px; column-gap: 20px; */
}

/* 고급 Flex 패턴들 */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
}

.navbar .logo { flex-shrink: 0; }
.navbar .nav-links { 
  display: flex;
  gap: 2rem;
  margin-left: auto;
}

/* 반응형 카드 레이아웃 */
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  padding: 2rem;
}

.card-container .card {
  flex: 1 1 300px;        /* grow shrink basis */
  min-width: 0;           /* flex item 최소 너비 해제 */
}

/* 성장/축소 제어 */
.flex-item {
  flex-grow: 1;           /* 여분 공간 분배 비율 */
  flex-shrink: 0;         /* 축소 방지 */
  flex-basis: 200px;      /* 기본 크기 */
  
  /* 단축 속성 */
  flex: 1 0 200px;        /* grow shrink basis */
}
```

#### 🔍 CSS Grid 고급 시스템
```css
/* 기본 Grid Container */
.grid-container {
  display: grid;
  
  /* 컬럼 정의 */
  grid-template-columns: repeat(12, 1fr);    /* 12컬럼 시스템 */
  grid-template-columns: 200px 1fr 100px;    /* 고정-유동-고정 */
  grid-template-columns: minmax(200px, 1fr) 2fr;  /* 최소/최대 크기 */
  
  /* 행 정의 */
  grid-template-rows: auto 1fr auto;          /* 헤더-본문-푸터 */
  
  /* 간격 설정 */
  gap: 2rem 1rem;         /* row-gap column-gap */
  
  /* 정렬 */
  justify-items: center;   /* 각 그리드 아이템의 수평 정렬 */
  align-items: center;     /* 각 그리드 아이템의 수직 정렬 */
  justify-content: center; /* 그리드 전체의 수평 정렬 */
  align-content: center;   /* 그리드 전체의 수직 정렬 */
}

/* 그리드 영역 템플릿 */
.layout-grid {
  display: grid;
  grid-template-areas: 
    "header header header"
    "sidebar main aside"
    "footer footer footer";
  grid-template-columns: 200px 1fr 200px;
  grid-template-rows: auto 1fr auto;
  min-height: 100vh;
}

.header { grid-area: header; }
.sidebar { grid-area: sidebar; }
.main { grid-area: main; }
.aside { grid-area: aside; }
.footer { grid-area: footer; }

/* 그리드 아이템 배치 */
.grid-item {
  /* 라인 기반 배치 */
  grid-column: 1 / 3;     /* 1번째~3번째 컬럼 라인 */
  grid-row: 2 / 4;        /* 2번째~4번째 행 라인 */
  
  /* span 사용 */
  grid-column: span 2;    /* 2개 컬럼 차지 */
  grid-row: span 3;       /* 3개 행 차지 */
  
  /* 단축 속성 */
  grid-area: 2 / 1 / 4 / 3;  /* row-start / col-start / row-end / col-end */
}

/* 반응형 그리드 */
.responsive-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

/* 복잡한 그리드 레이아웃 */
.magazine-layout {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  grid-template-rows: repeat(4, 200px);
  gap: 1rem;
}

.featured-article {
  grid-column: 1 / 4;
  grid-row: 1 / 3;
}

.secondary-article {
  grid-column: 4 / 7;
  grid-row: 1 / 2;
}
```

#### 🔍 Table Display 시스템
```css
/* CSS Table 구조 */
.table-container {
  display: table;
  width: 100%;
  border-collapse: collapse;
}

.table-row {
  display: table-row;
}

.table-cell {
  display: table-cell;
  padding: 12px;
  border: 1px solid #ddd;
  vertical-align: middle;  /* top | middle | bottom */
}

.table-header {
  display: table-header-group;
  font-weight: bold;
  background-color: #f5f5f5;
}

.table-footer {
  display: table-footer-group;
  background-color: #f9f9f9;
}

/* 등높이 컬럼 레이아웃 (Table Cell 활용) */
.equal-height-layout {
  display: table;
  width: 100%;
  table-layout: fixed;
}

.equal-height-column {
  display: table-cell;
  vertical-align: top;
  padding: 20px;
}
```

### 🎯 Position 속성 완전 마스터
#### ⚡ 핵심 포인트
- Position은 요소의 배치 컨텍스트를 결정
- 각 값마다 고유한 배치 규칙과 기준점 존재
- z-index와 stacking context 개념 중요

#### 💡 Position 값별 심화 분석

##### 🔹 Static Position (기본값)
```css
.static-element {
  position: static;
  
  /* 일반적인 문서 흐름을 따름 */
  /* top, right, bottom, left 무시 */
  /* z-index 적용 안됨 */
  
  /* 블록 요소는 세로로 쌓임 */
  /* 인라인 요소는 가로로 배치 */
}
```

##### 🔹 Relative Position (상대 위치)
```css
.relative-element {
  position: relative;
  
  /* 원래 위치를 기준으로 이동 */
  top: 20px;              /* 원래 위치에서 20px 아래로 */
  left: 30px;             /* 원래 위치에서 30px 오른쪽으로 */
  
  /* 원래 공간은 그대로 유지 */
  /* 다른 요소들은 영향받지 않음 */
  
  /* Absolute 자식의 기준점 역할 */
  z-index: 1;
}

/* 실용적인 Relative 사용 예시 */
.tooltip-container {
  position: relative;
  display: inline-block;
}

.tooltip {
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translateX(-50%);
  background: rgba(0,0,0,0.8);
  color: white;
  padding: 8px 12px;
  border-radius: 4px;
  white-space: nowrap;
  opacity: 0;
  transition: opacity 0.3s;
}

.tooltip-container:hover .tooltip {
  opacity: 1;
}
```

##### 🔹 Absolute Position (절대 위치)
```css
.absolute-element {
  position: absolute;
  
  /* 가장 가까운 positioned 부모 기준 */
  top: 0;
  right: 0;
  
  /* 문서 흐름에서 제거됨 */
  /* 원래 공간 사라짐 */
  
  /* 크기 조절 */
  width: 200px;
  height: 100px;
  
  /* 또는 위치로 크기 결정 */
  /* top: 10px; right: 10px; bottom: 10px; left: 10px; */
}

/* 고급 Absolute 패턴들 */
.modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.corner-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #ff4444;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
}

/* 전체 화면 레이아웃 */
.fullscreen-layout {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: grid;
  grid-template-rows: auto 1fr auto;
}
```

##### 🔹 Fixed Position (고정 위치)
```css
.fixed-element {
  position: fixed;
  
  /* 뷰포트 기준 고정 */
  top: 0;
  left: 0;
  
  /* 스크롤해도 위치 유지 */
  /* 항상 같은 곳에 표시 */
  
  width: 100%;
  z-index: 1000;
}

/* 실용적인 Fixed 패턴들 */
.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  z-index: 100;
}

/* 고정 헤더로 인한 콘텐츠 여백 조정 */
.main-content {
  margin-top: 60px;  /* 헤더 높이만큼 */
}

.fixed-sidebar {
  position: fixed;
  top: 60px;         /* 헤더 아래 */
  left: 0;
  width: 250px;
  height: calc(100vh - 60px);
  overflow-y: auto;
  background: #f8f9fa;
}

.floating-action-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: #007bff;
  color: white;
  border: none;
  box-shadow: 0 4px 12px rgba(0,0,0,0.3);
  cursor: pointer;
  z-index: 1000;
}
```

##### 🔹 Sticky Position (끈끈한 위치)
```css
.sticky-element {
  position: sticky;
  
  /* 임계점 설정 */
  top: 20px;             /* 상단에서 20px 떨어진 지점에서 고정 */
  
  /* 부모 요소 내에서만 고정 */
  /* 부모 끝에 도달하면 같이 스크롤 */
  
  /* 일반 흐름 + 고정 위치의 하이브리드 */
}

/* 실용적인 Sticky 패턴들 */
.sticky-nav {
  position: sticky;
  top: 0;
  background: white;
  border-bottom: 1px solid #eee;
  z-index: 10;
}

.sticky-sidebar {
  position: sticky;
  top: 20px;
  height: fit-content;
  max-height: calc(100vh - 40px);
  overflow-y: auto;
}

/* 테이블 헤더 고정 */
.sticky-table-header {
  position: sticky;
  top: 0;
  background: white;
  z-index: 5;
}

/* 섹션별 고정 헤더 */
.section-header {
  position: sticky;
  top: 60px;            /* 메인 헤더 아래 */
  background: #f8f9fa;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
  z-index: 5;
}
```

#### 🔍 고급 배치 패턴 조합
```css
/* 복합 레이아웃 시스템 */
.app-layout {
  display: grid;
  grid-template-areas: 
    "header header"
    "sidebar main";
  grid-template-columns: 250px 1fr;
  grid-template-rows: auto 1fr;
  min-height: 100vh;
}

.app-header {
  grid-area: header;
  position: sticky;
  top: 0;
  z-index: 100;
  background: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.app-sidebar {
  grid-area: sidebar;
  position: relative;
  overflow-y: auto;
}

.app-main {
  grid-area: main;
  position: relative;
  overflow-y: auto;
}

/* 반응형 레이아웃 조합 */
@media (max-width: 768px) {
  .app-layout {
    grid-template-areas: 
      "header"
      "main";
    grid-template-columns: 1fr;
  }
  
  .app-sidebar {
    position: fixed;
    top: 0;
    left: -250px;
    width: 250px;
    height: 100vh;
    transition: left 0.3s ease;
    z-index: 200;
  }
  
  .app-sidebar.open {
    left: 0;
  }
}

/* 중첩된 Positioning Context */
.card-stack {
  position: relative;
  
  .card {
    position: absolute;
    top: 0;
    left: 0;
    
    &:nth-child(1) { z-index: 3; transform: rotate(0deg); }
    &:nth-child(2) { z-index: 2; transform: rotate(2deg); }
    &:nth-child(3) { z-index: 1; transform: rotate(-1deg); }
  }
}
```

### 🎯 실무 레이아웃 패턴 모음
#### ⚡ 핵심 포인트
- 실제 프로젝트에서 자주 사용되는 검증된 패턴들
- 반응형 디자인과 접근성을 고려한 구현

#### 💡 완성형 레이아웃 패턴들
```css
/* 1. Holy Grail Layout (성배 레이아웃) */
.holy-grail {
  display: grid;
  grid-template: 
    "header header header" auto
    "nav main aside" 1fr
    "footer footer footer" auto
    / 200px 1fr 200px;
  min-height: 100vh;
  gap: 1rem;
}

/* 2. 카드 그리드 (자동 조정) */
.auto-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  padding: 2rem;
}

/* 3. 센터링 마스터 */
.perfect-center {
  /* Flexbox 방식 */
  display: flex;
  justify-content: center;
  align-items: center;
  
  /* Grid 방식 */
  display: grid;
  place-items: center;
  
  /* Position 방식 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

/* 4. 반응형 내비게이션 */
.responsive-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  
  .nav-links {
    display: flex;
    gap: 2rem;
    
    @media (max-width: 768px) {
      position: fixed;
      top: 100%;
      left: 0;
      right: 0;
      background: white;
      flex-direction: column;
      padding: 2rem;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      transform: translateY(-100%);
      transition: transform 0.3s ease;
      
      &.open {
        transform: translateY(0);
      }
    }
  }
}

/* 5. 마스헤드 레이아웃 */
.masthead {
  position: relative;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('bg.jpg');
  background-size: cover;
  background-position: center;
  
  .content {
    text-align: center;
    color: white;
    max-width: 600px;
    padding: 2rem;
  }
}
```

## 🧪 실습/적용
### 🔧 실무 적용법
1. **레이아웃 전략 수립**: Grid로 전체 구조, Flex로 컴포넌트 내부
2. **Position 활용**: Sticky for UI, Fixed for 오버레이, Absolute for 배지
3. **반응형 고려**: Mobile-first 접근, 컨테이너 쿼리 활용
4. **성능 최적화**: will-change, transform 활용한 GPU 가속

### 🎮 연습 문제
1. **복합 레이아웃**: Grid + Flex + Sticky를 조합한 대시보드 구현
2. **고급 내비게이션**: 메가 메뉴와 모바일 햄버거 메뉴 통합
3. **인터랙티브 갤러리**: Position과 Transform을 활용한 이미지 갤러리
4. **복잡한 폼 레이아웃**: 다단계 폼의 반응형 레이아웃 설계

## 🎯 핵심 정리
### 📌 반드시 기억할 5가지
1. **Display 선택**: Block(문서 구조), Flex(1차원 레이아웃), Grid(2차원 레이아웃) 용도별 구분
2. **Position 기준점**: Static(흐름), Relative(원래 위치), Absolute(부모), Fixed(뷰포트), Sticky(스크롤)
3. **Flexbox 핵심**: justify-content(주축), align-items(교차축), flex-grow/shrink/basis 비율 제어
4. **Grid 시스템**: Template으로 구조 정의, Area로 영역 배치, Auto-fit으로 반응형
5. **Stacking Context**: z-index는 같은 컨텍스트 내에서만 비교, Position 값에 따라 새 컨텍스트 생성

### 🚀 다음 학습 단계
- [ ] Container Queries와 차세대 반응형 기법
- [ ] CSS Subgrid와 고급 Grid 패턴
- [ ] CSS Logical Properties와 다국어 지원
- [ ] CSS Transforms와 3D 레이아웃
- [ ] Web Components와 CSS 캡슐화

## 🧠 자가 점검
### ❓ 이해도 체크
1. Flexbox와 Grid의 사용 시점을 명확히 구분할 수 있나요?
2. Position 각 값의 기준점과 특성을 정확히 설명할 수 있나요?
3. Stacking Context가 언제 생성되는지 알고 있나요?
4. 반응형 레이아웃을 위한 최적의 Display 조합을 선택할 수 있나요?

### 💭 성찰 질문  
- 복잡한 레이아웃을 마주했을 때 어떤 순서로 접근하시나요?
- Float를 사용하던 시대와 현재의 차이점은 무엇인가요?
- 성능을 고려한 레이아웃 설계 방법은 무엇인가요?

---
**[🔝 목차로 돌아가기](#📚-css-레이아웃-마스터-가이드)**