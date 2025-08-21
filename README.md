# 📌 Member CRUD 프로젝트

## 소개

Spring Boot 기반의 회원 관리 CRUD 프로젝트**입니다.

* **REST API** (`/api/members`)
* **Thymeleaf 웹 UI** (`/members`)

---

## 주요 기능

* 회원 목록 조회 (페이징 지원)
* 회원 단건 조회
* 회원 등록 / 수정 / 삭제
* REST API & MVC UI 동시 지원
* H2 인메모리 DB 기본 설정 -> MySQL DB 기본 설정
* 서비스 & DB 연동 통합 테스트

---

## UI 화면 (타임리프)

1. **회원 목록 (list.html)**

    * 테이블 형태로 회원 출력
    * **페이지네이션** 지원 (이전/다음 그룹 이동 포함)
    * 상세/수정/삭제 버튼 제공

2. **회원 등록 & 수정 (form.html)**

    * 같은 화면에서 신규 등록 및 수정 모두 처리
    * 이름, 이메일 입력 필수

3. **회원 상세 (view.html)**

    * 단건 회원 상세 정보 출력
    * 수정 및 목록 이동 링크 제공

---

## 작동 순서

### REST 요청 흐름

`DispatcherServlet` → `HandlerMapping` → **`MemberController`** → `Service` → `Repository(JPA)` → DB → **JSON 반환**

### MVC 요청 흐름

`DispatcherServlet` → `HandlerMapping` → **`WebMemberController`** → `Service` → `Repository(JPA)` → Thymeleaf → **HTML 반환**

---

## REST API 예시

* `GET /api/members` : 전체 회원 조회
* `GET /api/members/{id}` : 단건 조회
* `POST /api/members` : 회원 등록
* `PUT /api/members/{id}` : 회원 수정
* `DELETE /api/members/{id}` : 회원 삭제

---

## 실행 방법

* 브라우저 접속
    * `http://localhost:8080/members`
* REST API 호출(Postman 등)
    * `http://localhost:8080/api/members`

---

## 통합 테스트

* `@SpringBootTest` 기반의 통합 테스트 제공
* `MockMvc`를 활용한 **API 응답/페이징 검증**
* 서비스 ↔ DB 연동 검증 포함

---

## application.properties

```properties
# H2 DB 설정
#spring.datasource.url=jdbc:h2:tcp://localhost/~/test
#spring.datasource.driver-class-name=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=

# MySQL 설정
spring.datasource.url=jdbc:mysql://localhost:3306/restmvc?serverTimezone=Asia/Seoul
spring.datasource.username=restmvc
spring.datasource.password=1111
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update

# SQL 초기화
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
```
- H2, MySQL 중 1개 선택해서 사용
