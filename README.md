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
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
</div>
<br>

## 🏷️ 목차
1. [🏃‍♀️ Quick Start](#quick-start)
2. [📁 ERD 및 디렉터리 구조](#erd-및-디렉터리-구조)
3. [🖥️ 서비스 아키텍처 및 배포](#서비스-아키텍처-및-배포)
4. [📑 구현 내용](#구현-내용)
5. [💌 API 명세](#api-명세)
6. [⚡ 트러블 슈팅](#트러블-슈팅)
7. [🤔 고민 흔적](#고민-흔적)
<br>

## 🏃‍♀️ Quick Start <a id="quick-start"></a>
### 1. 환경변수 및 환경설정 파일 생성
- `.env` 파일
```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
DB_PORT=

MYSQL_NAME=
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

## 🖥️ 서비스 아키텍처 및 배포 <a id="서비스-아키텍처-및-배포"></a>
### 서비스 아키텍처
<img alt="서비스 아키텍쳐" src="https://github.com/user-attachments/assets/ecf7df96-4638-47a7-b272-cc9d4b477468" width="80%" height="100%">

### 배포 이미지
(현재는 비용 문제로 EC2 인스턴스 중지한 상태)
<img alt="배포 이미지" src="https://github.com/user-attachments/assets/6254eeac-208d-464c-b51a-92bf94c7823e" width="80%" height="100%">

</br>

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

## ⚡ 트러블 슈팅 <a id="트러블-슈팅"></a>
<details>
<summary>⚡ build시 Gradle이 jdk를 찾지 못하는 문제 발생</summary>
<div markdown="1">
<ul>
<div>

### 문제 상황

- 프로젝트 build시 Gradle이 jdk를 찾지 못하는 문제 발생
    
    ```yaml
    FAILURE: Build failed with an exception.
    
    * What went wrong:
    Could not determine the dependencies of task ':bootJar'.
    > Could not resolve all dependencies for configuration ':runtimeClasspath'.
       > Failed to calculate the value of task ':compileJava' property 'javaCompiler'.
          > Cannot find a Java installation on your machine matching this tasks requirements: {languageVersion=17, vendor=any vendor, implementation=vendor-specific} for LINUX on x86_64.
             > No locally installed toolchains match and toolchain download repositories have not been configured.
    ```
    

### 원인 분석

1. java 환경 변수 설정
2. 벤더 설정 추가
3. gradle.properties 파일에 설정 추가

### 해결 과정

- 위 세 가지 원인을 해결해도 build시 똑같은 오류 발생
- 에러 코드 마지막 문장을 해석하면 “로컬에 설치된 툴체인 중에서 일치하는 것이 없으며, 툴체인을 다운로드할 레포지토리도 설정되지 않았습니다.”
    
    ➡️ 즉, 현재 시스템에 요구되는 버전의 툴체인이 설치되어 있지 않으며, 툴체인을 자동으로 다운로드할 수 있는 레포지토리 설정도 누락된 상태라는 의미
    
- 공식 문서를 참고하면, Toolchain Resolver Plugins은 빌드에 필요한 툴체인을 자동으로 다운로드하고 구성하므로, 이 중 하나인 Foojay Toolchains Plugin을 적용하기 위해 Foojay Toolchain Resolver를 사용해보기로 함
    
    ➡️ 이를 위해 settings.gradle에 아래 내용을 추가 후 다시 build
    
    ```yaml
    plugins {
        id 'org.gradle.toolchains.foojay-resolver-convention' version '0.8.0'
    }
    ```
    

### 결과

- 오류 없이 build 성공

### 참고

[Gradle 공식 문서](https://docs.gradle.org/8.10.2/userguide/toolchains.html#sub:download_repositories)
</div>
</ul>
</div>
</details>
<details>
<summary>⚡ EC2 배포 시 build 오래 걸리는 문제</summary>
<div markdown="1">
<ul>
<div>

### 문제 상황

- EC2 배포 과정에서 프로젝트 build시 더 이상 진행되지 않고 멈춰버리는 상황이 자주 발생
    
    EC2 인스턴스를 중지했다가 재시작하면 속도가 빨라지지만, 그 이후 계속 서버가 느려짐
    

### 원인 분석

- 검색해서 찾아본 결과 메모리 부족 때문에 생기는 현상이라고 파악
    
    ➡️ 현재 AWS free tier로 서비스를 사용중인데, free tier로 제공하는 t2.micro의 메모리는 고작 1GB 정도로, 메모리 부족 때문에 gradle 멈춤 현상이 발생하는 것이겠다고 판단
    

### 해결 과정

- 공식 홈페이지에서 제공하는 스왑 파일을 사용하여 메모리를 할당해주는 방법 사용
    
    이때 SWAP 메모리란 RAM이 부족할 경우 HDD의 일정공간을 마치 RAM처럼 사용하는 것을 의미
    
1. 메모리 확인
    
    ```yaml
    free
    ```
    
    ![image](https://github.com/user-attachments/assets/463d0531-c6ee-46f9-9514-8e519f39ef2e)
    
2. swap 메모리 할당 - 2GB 크기의 스왑 파일(128M씩 16개 공간)
    
    ```yaml
    sudo dd if=/dev/zero of=/swapfile bs=128M count=16
    ```
    
3. 스왑 파일에 대한 읽기 및 쓰기 권한 업데이트
    
    ```yaml
    sudo chmod 600 /swapfile
    ```
    
4. Linux 스왑 영역 설정
    
    ```yaml
    sudo mkswap /swapfile
    ```
    
5. 스왑 공간에 스왑 파일을 추가하여 스왑 파일을 즉시 사용할 수 있도록 만듦
    
    ```yaml
    sudo swapon /swapfile
    ```
    
6. 절차가 성공적으로 완료되었는지 확인
    
    ```yaml
    sudo swapon -s
    ```
    
    ![image](https://github.com/user-attachments/assets/4ec6e06a-dbc5-4618-815a-812f358b176d)
    
7. 부팅 시 **`/etc/fstab`** 파일을 편집하여 스왑 파일을 시작
    
    편집기에서 파일 엶
    
    ```yaml
    sudo nano /etc/fstab
    ```
    
    파일 끝에 다음 새 줄을 추가하고 파일을 저장한 다음 종료
    
    ```yaml
    /swapfile swap swap defaults 0 0
    ```
    
8. 적용됐는지 확인
    
    ![image](https://github.com/user-attachments/assets/bad27509-4605-4686-acda-f657e9f7f3af)
    

### 결과

- 멈춤 없이 프로젝트 build

### 참고

[AWS 공식 홈페이지](https://repost.aws/ko/knowledge-center/ec2-memory-swap-file)
</div>
</ul>
</div>
</details>
<details>
<summary>⚡ EC2 인스턴스에서 docker-compose 실행 시 생긴 Spring Boot와 DB 간의 네트워크 통신 오류</summary>
<div markdown="1">
<ul>
<div>
  
### 문제 상황

- EC2 인스턴스에서 docker-compose 실행 후 API 테스트 진행을 하니 결과가 제대로 출력되지 않는 상황 발생
- log 출력 시 아래와 같은 에러메시지 출력
    
    ![image](https://github.com/user-attachments/assets/2489ac99-67b6-424d-abbe-9fa3663fcf92)
    
    The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server. ➡️ 애플리케이션 서버가 DB에 패킷을 보냈으나, JDBC Driver가 DB로부터 어떠한 패킷도 받지 못했다는 에러메시지
    

### 원인 분석

- MySQL 서버 주소를 잘못 설정했거나, MySQL 서버가 실행중이지 않거나, 포트 설정으로 인한 방화벽 문제
    
    ➡️ 확인 결과 모두 문제 없이 설정됨
    
- 추가로 찾아본 결과, Mysql 설정 파일에서 bind-address를 제대로 설정해주지 않은 것으로 파악
    
    127.0.0.1은 localhost를 의미하는데, bind-address 디폴트 값이 127.0.0.1이기에 localhost에서 보내는 요청만 받게 됨
    

### 해결 과정

1. EC2 인스턴스에서 설정 파일(`my.cnf`) 복사하기
    
    ```yaml
    docker cp money-wise-mysql:/etc/mysql/my.cnf ./my.cnf
    ```
    
2. `my.cnf` 파일 편집
    
    ```yaml
    [mysqld]
    bind-address = 0.0.0.0
    ```
    
    외부에서 보내는 요청을 받기 위해 0.0.0.0 으로 설정
    
3. 수정한 파일을 다시 컨테이너로 복사
    
    ```yaml
    docker cp ./my.cnf money-wise-mysql:/etc/my.cnf
    ```
    

### 결과

- docker-compose 재시작 후 API 테스트 결과 정상적으로 결과 출력
</div>
</ul>
</div>
</details>
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
