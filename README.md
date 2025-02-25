# 도서 관리 시스템 CRUD 과제
## 프로젝트 개요
본 프로젝트는 저자와 도서를 관리하는 도서 관리 시스템으로, 주어진 요구사항에 맞춰 기본적인 CRUD 기능과 데이터 모델을 구현하였습니다.  
RESTful API를 제공하며, Swagger 문서를 통해 API 명세를 확인할 수 있습니다.  
&nbsp;
&nbsp;
## 기술 스택
- **언어**: Java 17
- **프레임워크**: Spring Boot
- **빌드 툴**: Gradle
- **데이터베이스**: H2 (테스트 환경에서는 H2 인메모리)
- **ORM**: Spring Data JPA, QueryDSL
- **API 문서**: Swagger (Springdoc OpenAPI 3)  
&nbsp;
&nbsp;
## 프로젝트 실행 방법
1. 현재 프로젝트를 클론한 뒤, 해당 루트 디렉토리로 이동하여 gradle을 통해 빌드를 수행합니다.
```bash
./gradlew build
```
2. Dockerfile을 기반 이미지 빌드를 수행한 뒤 백그라운드에서 어플리케이션을 실행합니다. 
```bash
docker compose build
docker compose up -d
```
3. 어플리케이션이 실행되면 [🔗아래 URL](http://localhost:8080/docs)을 통하여 **Swagger API 문서**를 확인할 수 있습니다.
```
http://localhost:8080/docs
혹은
http://localhost:8080/swagger-ui/index.html
```

&nbsp;
&nbsp;
## API 명세
전체 API 명세는 Swagger 문서를 통해 확인할 수 있습니다. [🔗Swagger UI](http://localhost:8080/docs)  

### 저자 (Author) API
- POST /authors - 저자 생성
- GET /authors - 저자 목록 조회
- GET /authors/{id} - 저자 상세 조회
- PATCH /authors/{id} - 저자 정보 수정
- DELETE /authors/{id} - 저자 삭제 (연관된 도서가 있을 경우 삭제 불가)  

### 도서 (Book) API
- POST /books - 도서 생성
- GET /books - 도서 목록 조회
- GET /books/{id} - 도서 상세 조회
- PATCH /books/{id} - 도서 정보 수정
- DELETE /books/{id} - 도서 삭제  
&nbsp;
&nbsp;
## 프로젝트 구조
    src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── hwk9407
    │   │           └── bookmanagementassignment
    │   │               ├── api
    │   │               │   ├── author
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   │   ├── request
    │   │               │   │   │   └── response
    │   │               │   │   └── service
    │   │               │   └── book
    │   │               │       ├── controller
    │   │               │       ├── dto
    │   │               │       │   ├── request
    │   │               │       │   └── response
    │   │               │       ├── service
    │   │               │       └── validator
    │   │               ├── config
    │   │               ├── domain
    │   │               │   ├── author
    │   │               │   └── book
    │   │               ├── exception
    │   │               │   └── dto
    │   │               └── util
    │   └── resources
    │       ├── static
    │       └── templates
    └── test
        ├── java
        │   └── com
        │       └── hwk9407
        │           └── bookmanagementassignment
        │               ├── api
        │               │   ├── author
        │               │   │   └── service
        │               │   └── book
        │               │       ├── service
        │               │       └── validator
        │               └── util
        └── resources