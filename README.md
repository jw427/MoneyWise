# 💰 MoneyWise 💸

## 💬 프로젝트 소개

사용자들의 현명한 소비 습관을 위한 서비스 **머니와이즈(MoneyWise)**! <br>
효율적인 소비 습관을 위해 예산과 지출을 머니와이즈에서 관리해보세요! <br>
<br>

## 🛠️ 기술 스택
<div align=center>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/springdatajpa-13C100?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
</div>
<br>

## 🏷️ 목차
1. [🏃‍♀️ Quick Start](#quick-start)
2. [📁 ERD 및 디렉터리 구조](#erd-및-디렉터리-구조)
3. [📑 구현 내용](#구현-내용)
4. [💌 API 명세](#api-명세)
5. [🤔 고민 흔적](#고민-흔적)
<br>

## 🏃‍♀️ Quick Start <a id="quick-start"></a>
### 1. 환경변수 및 환경설정 파일 생성
- `.env` 파일
```
DB_NAME=
MYSQL_PASSWORD=
REDIS_PASSWORD=
```
- `application-secret.yml` 파일에 DB 정보를 설정해주세요.
- 포트 정보가 다른 경우 `applicaion.yml`의 포트를 수정해주세요.
### 2. docker 설정
- docker가 설치되지 않은 경우 설치해주세요.
- docker compose로 DB를 실행합니다.
```
docker-compose up -d
```
- redis 비밀번호를 설정해주세요.
```
docker exec -it {redis container name} redis-cli // 접속

config get requirepass // 비밀번호 확인
config set requirepass {redis-password} // 비밀번호 설정
config get requirepass // 비밀번호 확인
exit // 종료
docker exec -it {redis container name} redis-cli // 접속
auth {redis-password} // 인증
```
<br>

## 📁 ERD 및 디렉터리 구조 <a id="erd-및-디렉터리-구조"></a>
<details>
<summary>
ERD
</summary>
<div markdown="1">
<ul>
<div>

![MoneyWise](https://github.com/user-attachments/assets/ea5e8ec5-c3e7-4349-bc9f-987647818680)
  
</div>
</ul>
</div>
</details>

<details>
<summary>
디렉터리 구조
</summary>
<div markdown="1">
<ul>
<div>

```
src
├─main
│  ├─java
│  │  └─com
│  │      └─finance
│  │          │  FinanceApplication.java
│  │          │
│  │          ├─budget
│  │          │  ├─controller
│  │          │  │      BudgetController.java
│  │          │  │
│  │          │  ├─domain
│  │          │  │      Budget.java
│  │          │  │
│  │          │  ├─dto
│  │          │  │      BudgetListResponseDto.java
│  │          │  │      BudgetRatioDto.java
│  │          │  │      CreateBudgetRequestDto.java
│  │          │  │      CreateBudgetResponseDto.java
│  │          │  │      DeleteBudgetResponseDto.java
│  │          │  │      ModifyBudgetRequestDto.java
│  │          │  │      ModifyBudgetResponseDto.java
│  │          │  │      RecommendBudgetRequestDto.java
│  │          │  │      RecommendBudgetResponseDto.java
│  │          │  │
│  │          │  ├─repository
│  │          │  │      BudgetRepository.java
│  │          │  │
│  │          │  └─service
│  │          │          BudgetService.java
│  │          │
│  │          ├─category
│  │          │  ├─controller
│  │          │  │      CategoryController.java
│  │          │  │
│  │          │  ├─domain
│  │          │  │      Category.java
│  │          │  │
│  │          │  ├─dto
│  │          │  │      CategoryListResponseDto.java
│  │          │  │      CreateCategoryRequestDto.java
│  │          │  │      CreateCategoryResponseDto.java
│  │          │  │      DeleteCategoryResponseDto.java
│  │          │  │      ModifyCategoryRequestDto.java
│  │          │  │      ModifyCategoryResponseDto.java
│  │          │  │
│  │          │  ├─repository
│  │          │  │      CategoryRepository.java
│  │          │  │
│  │          │  └─service
│  │          │          CategoryService.java
│  │          │
│  │          ├─exception
│  │          │  │  BadRequestException.java
│  │          │  │  BaseException.java
│  │          │  │  ConflictException.java
│  │          │  │  ErrorCode.java
│  │          │  │  ErrorResponse.java
│  │          │  │  ForbiddenException.java
│  │          │  │  NotFoundException.java
│  │          │  │  UnauthorizedException.java
│  │          │  │
│  │          │  └─handler
│  │          │          GlobalExceptionHandler.java
│  │          │
│  │          ├─expense
│  │          │  ├─controller
│  │          │  │      ExpenseController.java
│  │          │  │
│  │          │  ├─domain
│  │          │  │      Expense.java
│  │          │  │
│  │          │  ├─dto
│  │          │  │      CreateExpenseRequestDto.java
│  │          │  │      CreateExpenseResponseDto.java
│  │          │  │      DeleteExpenseResponseDto.java
│  │          │  │      ExpenseDetailResponseDto.java
│  │          │  │      ExpenseListResponseDto.java
│  │          │  │      ExpenseTotalResponseDto.java
│  │          │  │      ModifyExpenseRequestDto.java
│  │          │  │      ModifyExpenseResponseDto.java
│  │          │  │
│  │          │  ├─repository
│  │          │  │      ExpenseRepository.java
│  │          │  │
│  │          │  └─service
│  │          │          ExpenseService.java
│  │          │
│  │          └─user
│  │              ├─config
│  │              │      SecurityConfig.java
│  │              │      TokenAuthenticationFilter.java
│  │              │      TokenProvider.java
│  │              │
│  │              ├─controller
│  │              │      TokenController.java
│  │              │      UserController.java
│  │              │
│  │              ├─domain
│  │              │      Role.java
│  │              │      Token.java
│  │              │      User.java
│  │              │      UserDetail.java
│  │              │
│  │              ├─dto
│  │              │      SignUpRequestDto.java
│  │              │      SignUpResponseDto.java
│  │              │      TokenRequestDto.java
│  │              │      TokenResponseDto.java
│  │              │      UserLoginRequestDto.java
│  │              │      UserLoginResponseDto.java
│  │              │
│  │              ├─repository
│  │              │      TokenRepository.java
│  │              │      UserRepository.java
│  │              │
│  │              └─service
│  │                      TokenService.java
│  │                      UserDetailService.java
│  │                      UserService.java
│  │
│  └─resources
│      │  application-secret.yml
│      │  application.yml
│      │  data.sql
│      │  schema.sql
│      │
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─finance
                    FinanceApplicationTests.java
```
  
</div>
</ul>
</div>
</details>
<br>

## 📑 구현 내용 <a id="구현-내용"></a>
### 회원가입
- 중복 계정명 방지 기능
### 로그인
- 로그인 시 액세스 토큰과 리프레시 토큰 발급
- 모든 서비스에 접근 시 로그인 한 회원만 접근 가능하도록 JWT 인가
- 효율적인 관리를 위해 리프레시 토큰을 Redis에 저장해 관리
### 액세스 토큰 재발급
- 액세스 토큰 만료 시 리프레시 토큰이 유효할 경우 액세스 토큰 재발급
### 카테고리
- 11개(식비, 교통, 주거/통신, 카페/간식, 쇼핑, 문화/여가, 여행, 의료, 생활, 교육, 금융)의 기본 카테고리 지정
- 기본 카테고리는 DB에 `user_id=null`로 저장해 회원이 카테고리를 조회할 경우 조회할 경우 `user_id=null`인 카테고리도 함께 조회
- 회원은 카테고리를 생성, 수정, 삭제 가능
### 예산
- 회원은 예산을 카테고리별로 생성, 수정, 삭제 가능
- 예산 생성, 수정, 삭제 시 기본 카테고리 또는 회원 본인이 만든 카테고리만 사용 가능
### 예산 추천
- 카테고리 지정 없이 예산 총액 설정 후 카테고리별로 예산 금액을 추천받는 기능
- 기본 카테고리로만 예산 추천
- 예산 총액이 비슷한 경우 소비 습관이 비슷할 것이라고 가정하여 입력한 예산 총액의 `±20%` 범위 안에서 예산 총액을 설정한 회원들의 카테고리별 예산의 평균 비율을 계산해 카테고리별로 예산 금액 추천
- 예산의 평균 비율이 5% 미만인 카테고리는 모두 묶어 `기타`로 제공
- 예산 금액은 100원 단위로 추천
### 지출
- 카테고리별로 지출 일시, 지출 금액, 카테고리, 메모와 지출 합계 제외 여부를 생성, 수정, 삭제 가능
- 지출 생성 시 해당 카테고리로 설정된 예산이 없는 경우 생성 불가
- 지출 목록은 기간을 필수적으로 입력하고 추가적으로 카테고리, 지출 최소/최대 금액으로 조회 가능
- 지출 목록 조회 시 조회된 모든 내용의 지출 합계, 카테고리 별 지출 합계를 같이 반환(합계 제외 처리한 지출은 목록에 포함되지만, 모든 지출 합계에서 제외)
- 지출 상세 조회 가능
<br>

## 💌 API 명세 <a id="api-명세"></a>
### 👉 [API 명세서](https://documenter.getpostman.com/view/29531239/2sAXqy1e7e)
| No | 기능 | Method | URL |
|----|------|--------|------|
| 1 | 회원가입 | `POST` | `/api/users/sign-up` |
| 2| 로그인 | `POST` | `/api/users/login` |
| 3 | 액세스 토큰 재발급 | `POST` | `/api/tokens/access-token` |
| 4 | 카테고리 생성 | `POST` | `/api/categories` |
| 5 | 카테고리 전체 조회 | `GET` | `/api/categories` |
| 6 | 카테고리 수정 | `PATCH` | `/api/categories/:categoryId` |
| 7 | 카테고리 삭제 | `DELETE` | `/api/categories/:categoryId` |
| 8 | 예산 설정 | `POST` | `/api/budgets` |
| 9 | 예산 전체 조회 | `GET` | `/api/budgets` |
| 10 | 예산 수정 | `PATCH` | `/api/budgets/:budgetId` |
| 11 | 예산 삭제 | `DELETE` | `/api/budgets/:budgetId` |
| 12 | 예산 추천 | `GET` | `/api/budgets/recommendation` |
| 13 | 지출 생성 | `POST` | `/api/expenses` |
| 14 | 지출 목록 조회 | `GET` | `/api/expenses?startAt=2024-09-22&endAt=2024-09-24...` |
| 15 | 지출 상세 조회 | `GET` | `/api/expenses/:expenseId` |
| 16 | 지출 수정 | `PATCH` | `/api/expenses/:expenseId` |
| 17 | 지출 삭제 | `DELETE` | `/api/expenses/:expenseId` |
<br>

## 🤔 고민 흔적 <a id="고민-흔적"></a>
<details>
<summary>💭 refresh token을 어떻게 관리할까?</summary>
<div markdown="1">
<ul>
<div>
이전 프로젝트까지는 refresh token을 MySQL같은 일반 DB에 저장해 관리했다. <br>
그러나 만료 기간이 짧은 access token을 재발급하는 목적으로 사용하는 refresh token의 경우 호출의 빈도가 높아 RDB에 저장하는 것은 비효율적이라는 생각이 들었다. <br> 
또, refresh token도 만료 기간이 있기 때문에 만료 기간이 지난 refresh token이 DB에 계속 남아 있을 가능성도 존재했다. <br>
따라서 만료일을 지정할 수 있고 적은 메모리로도 데이터를 저장할 수 있으며 조회 성능이 뛰어난 Redis 사용을 고려하여 도입하게 됐다.
</div>
</ul>
</div>
</details>
<details>
<summary>💭 카테고리는 어떻게 구현하면 좋을까?</summary>
<div markdown="1">
<ul>
<div>
요구사항에는 카테고리를 자유롭게 구현하라고 명시되어 있었다. <br>
기본 카테고리로만 구현하는 것이 좋을지 회원에게 카테고리를 생성할 수 있는 기능을 구현하는 것이 좋을지 고민하던 중 뱅크샐러드라는 자산관리 서비스가 생각이 났다. <br>
해당 서비스에서는 14개의 기본 카테고리와 각 카테고리별로 세부 카테고리가 존재했다. <br>
또한 회원들이 카테고리를 자유롭게 생성, 수정, 삭제할 수 있었다. <br>
이 점을 참고해 사용자의 편의성 면에서 기본 카테고리를 제공하고 추가적으로 카테고리를 생성, 수정, 삭제할 수 있는 방향으로 구현하기로 했다. <br>
기본 카테고리는 11개 정도로 결정해 초기 DB에 저장했고, 회원들이 필요한 카테고리를 생성, 수정, 삭제할 수 있는 기능을 구현하게 됐다.
</div>
</ul>
</div>
</details>
<details>
<summary>💭 예산 추천 시 어떤 회원들의 예산을 기준으로 추천해주는 것이 좋을까?</summary>
<div markdown="1">
<ul>
<div>

요구사항에는 _기존 이용중인 유저들이 설정한 평균 값_ 으로 카테고리별 예산을 추천하라고 명시되어 있었다. <br>
그러나 요구사항을 분석하면서 예산 추천 시 모든 회원들이 설정한 평균 값으로 추천하게 되면 모든 회원에게 같은 비율의 예산을 추천하게 되어 서비스의 의도에서 벗어나는 것은 아닐까 생각하게 되었다. <br>
그래서 고민하던 중 _소비 금액이 비슷한 사람들은 소비 패턴이 비슷할 것_ 이라고 가정하고, 입력한 예산 총액과 비슷한 예산 총액을 설정한 회원들이 설정한 평균 값으로 추천해주는 방식을 고려하게 되었다. <br>
입력한 예산 총액과 비슷한 예산 총액의 기준은 입력한 예산 총액의 `±20%` 범위로 설정해 해당하는 회원들의 범위를 좁히기로 결정했다. <br>
이렇게 사용자 데이터를 기반으로 추천하여 서비스의 기획 의도를 반영하고자 했다.
</div>
</ul>
</div>
</details>
<details>
<summary>💭 <code>schema.sql</code>과 <code>data.sql</code>의 실행 시점을 어떻게 결정하는 것이 좋을까?</summary>
<div markdown="1">
<ul>
<div>

<code>schema.sql</code>은 데이터베이스 스키마를 정의하고, <code>data.sql</code>은 데이터베이스에 초기 데이터를 삽입하는 파일이다. <br>
docker로 DB 컨테이너를 생성해 관리할 경우 해당 파일들을 언제 실행하면 좋을지 고민하게 됐다. <br>
<code>schema.sql</code>의 경우 docker 컨테이너를 실행할 때, 즉 DB 컨테이너가 생성되고 초기화될 때 실행하고, <br>
<code>data.sql</code>는 서버가 실행될 때, 즉 DB 구조가 이미 설정됐을 때 실행하기로 결정했다. <br>
두 파일 모두 한 시점에 실행해도 상관없겠지만, <code>schema.sql</code>는 DB의 구조를 설정하는 데 집중하고, <code>data.sql</code>은 초기 데이터를 삽입하는 데 집중하여 각 파일의 역할을 확실히 분리하기로 했다.
</div>
</ul>
</div>
</details>
