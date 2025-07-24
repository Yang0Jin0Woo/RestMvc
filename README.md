## 소개
- 회원(Member) 데이터를 관리하는 CRUD 프로젝트
- `/api/members` 경로 : RESTful API
- `/members` 경로 : Thymeleaf 기반의 웹.

## 주요 기능
- 회원 목록 조회, 단건 조회, 회원 등록, 수정, 삭제
- REST API 및 MVC UI 동시 지원
- H2 인메모리 DB 기본 설정

## 실행 방법
- 브라우저 : `http://localhost:8080/members` 접속
- REST API : `http://localhost:8080/api/members` postman 접속
- H2 콘솔: `http://localhost:8080/h2-console`

## 작동 순서
- REST 요청 : `DispatcherServlet` → `HandlerMapping` → `MemberController` → `Service` → `Repository(JPA)` → DB → JSON 반환
- MVC 요청 : `DispatcherServlet` → `HandlerMapping` → `WebMemberController` → `Service` → `Repository(JPA)` → Thymeleaf → HTML 반환

### application.properties
```
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```