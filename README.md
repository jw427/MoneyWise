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
