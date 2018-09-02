# kakaoIx-store
> 2018 카카오IX 공채 경력 서버 과제
- Restful API 기반의 간이 쇼핑몰을 구축하시오.
- 상품 데이터와 이미지는 카카오프렌즈 쇼핑몰( www.kakaofriends.com , us.kakaofriends.com ) 의 데이터를 활용 가능합니다.




## Implementation contents
1. Backend
  - 회원 API : 회원가입, 로그인, 로그아웃
  - 상품 API : 상품등록, 상품리스트, 상품삭제
  - 주문 API : 주문요청, 주문취소, 사용자요청 주문조회, 주문번호 조회
   
2. Front
  - 로그인, 회원가입
  - 상품구매

### Stack
- Back-End Framework : Spring Boot
- Front-End Framework : Vue.js
- DBMS : H2

### Prerequisites
- NPM
- Java 1.8.x
- Lombok plugin

### Run in development
#### Run Proxy server [port:80]
- nginx 설정 수정.
```
config
    server {
       listen       80;
       # vue-ui
       location / {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8080;
       }
       # api
       location /products {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8081/products;
       }
       # api
       location /members {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8081/members;
       }
       # api
       location /orders {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8081/orders;
       }
   }
```
#### Run front server [port: 8080]
``` bash
# Change to frontend root directory
cd /src/main/vue-ui

# Serve in dev mode, with hot reload at localhost:8080
npm run dev
```

#### Run backend server [port: 8081]
``` bash
# 터미널 환경.
./gradlew bootRun

# 인텔리J
1. Sync gradle
2. Run IxOrderApplication
```

### Project package structure
``` bash
ix-store
└── src
    ├── main
    │   └──com.kakaoix.store
    │      ├──config         -------> # 공통 스프링 환경설정 정의
    │      ├──exception      -------> # global 예외 정의
    │      ├──interceptor    -------> # 인터셉터 정의
    │      ├──member         -------> # 회원 API 정의
    │      ├──order          -------> # 주문 API 정의
    │      ├──product        -------> # 상품 API 정의
    │      ├──security       -------> # 토큰 관련 정의
    │      └──ixOrderApplication ---> * BootApplication.
    └── test                 -------> # testCase

```
----
## API Specifications
URL : http://localhost:8081:swagger-ui.html
* 우측 상단 [Select a spec] 선택 시, 각 API 이동 가능.
* 각 API Request | Response Example Value 및 Model 참고.
----