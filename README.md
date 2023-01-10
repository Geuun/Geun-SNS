# GEUN - SNS

## Summery

> 나만의 작고 소중한 🥹 SNS 만들기...

### Tech Stack

<div align="center">
 <img src="https://img.shields.io/badge/openJDK-FF9E0F.svg?logo=CoffeeScript&logoColor=white"/>
 <img src="https://img.shields.io/badge/Spring_Boot-6DB33F.svg?logo=Spring-Boot&logoColor=white"/> <br>
 <img src="https://img.shields.io/badge/Spring_Security-6DB33F.svg?logo=Spring-Security&logoColor=white"/> 
 <img src="https://img.shields.io/badge/JSON_Web_Token-000000.svg?logo=Json-Web-Tokens&logoColor=white"/> <br>
 <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> 
 <img src="https://img.shields.io/badge/Redis-DC382D.svg?logo=Redis&logoColor=white"/> <br>
 <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/>
 <img src="https://img.shields.io/badge/Raspberry_Pi-A22846.svg?logo=Raspberry-Pi&logoColor=white"/>
 <img src="https://img.shields.io/badge/Amazon_EC2-FF9900.svg?logo=Amazon-EC2&logoColor=white"/>
</div>

### URLs

- `Raspberry Home Server` (Arm64) : http://geun.me:9999/swagger-ui.html#/

- `AWS Server` (Amd64) : http://ec2-15-164-170-144.ap-northeast-2.compute.amazonaws.com:9999/swagger-ui.html#/

###  Functional Description

- 회원가입, 로그인, 회원 권한 수정 기능
- <s>Redis를 이용한 Token 관리 및 로그아웃 기능</s> (ing...)
- 포스트 CRUD 기능 (D: SoftDelete)
- 댓글 CRUD 기능 (D: SoftDelete)
- 좋아요 기능
- 마이피드 기능
- 알람 기능
- CI/CD PipeLine 구축으로 지속적인 통합과 지속적인 배포환경 구성
- Swagger API 명세 기능


## Development environment

- JVM 11 ver
- Spring Boot 2.7.5 ver
- Spring boot JPA
- MySQL
- Redis
- Spring Security
- Swagger
- JWT
- GitLab
- Docker

---

## Server

> 다양한 배포 경험을 위해서 arm64/amd64 환경에서 동시에 배포되었습니다.

- arm64 : RaspberryPi 4B
- amd64 : AWS EC2 t3.small

---

## Archetecture

![Geun-sns](https://user-images.githubusercontent.com/89567475/211610161-fdc47514-f8c8-4a38-bc55-0ffa9a73c616.png)

---

## ERD

![ERD](https://user-images.githubusercontent.com/89567475/211506328-f92534d6-c4ff-42a7-a12f-4a5017b23a0c.png)

---

## EndPoints

|           | REST |                 Route                 |        API         |
|:---------:|:------------------:|:-------------------------------------:|:------------------:|
|  `hello`  | GET |            `/api/v1/hello`            |   CI/CD TEST API   |
|  `users`  | POST |         `/api/v1/users/join`          |        회원가입        |
|  `users`  | POST |         `/api/v1/users/login`         |        로그인         |
|  `users`  | POST |         `/api/v1/users/login`         |        로그인         |
|  `users`  | POST |       /api/v1/users/{id}/roles        |   사용자권한변경(Admin)   |
|  `users`  | POST |    /api/v1/users/{id}/roles/admins    | 사용자권한변경(SuperUser) |
|  `posts`  | POST |            `/api/v1/posts`            |        글 작성        |
|  `posts`  | PUT |         `/api/v1/posts/{id}`          |        글 수정        |
|  `posts`  | DELETE |         `/api/v1/posts/{id}`          |        글 삭제        |
|  `posts`  | GET |         `/api/v1/posts/{id}`          |      글 세부 조회       |
|  `posts`  | GET |            `/api/v1/posts`            |      글 전체 조회       |
| `comment` | POST |     `/api/v1/posts/{id}/comment`      |       댓글 작성        |
| `comment` | PUT | `/api/v1/posts/{postid}/comment/{id}` |       댓글 수정        |
| `comment` | DELETE | `/api/v1/posts/{postid}/comment/{id}` |       댓글 삭제        |
| `comment` | GET |     `/api/v1/posts/{id}/comment`      |       댓글 조회        |
|  `good`   | POST |      `/api/v1/posts/{id}/likes`       |      좋아요 / 취소      |
|  `good`   | GET |      `/api/v1/posts/{id}/likes`       |       좋아요 조회       |
| `myfeed`  | GET |          `/api/v1/posts/my`           |      마이피드 조회       |
|  `alarm`  | GET |         /api/v1/users/alarms          |       알람 조회        |


---

## What I paid special attention to

### Lombok의 힘은 생각보다 강하다...!!!

<details>
<summary> @Setter 사용을 지양하는 코드를 작성합니다. </summary>
<div markdown="1">

코드 전반적으로 Entity에 Setter를 사용하지 않는 전략을 사용했습니다.
Entity 객체의 데이터는 불변한 성질을 가져야하기 때문에 위와 같은 전략을 사용해서 코드를 작성했습니다. 

</div>
</details>

<details>
<summary> @NoArgsConstructor의 접근을 최소화하는 코드를 작성합니다. </summary>
<div markdown="1">

JPA의 사용을 위해서 기본생성자는 필요가 필요합니다.
하지만 모든 접근 권한을 허용하게되면 객체의 안정성을 떨어뜨릴 수 있습니다.
따라서 `@NoArgsConstructor(access = AccessLevel.PROTECTED)` 를 사용해 무분별한 기본생성자의 접근을 막아서
최대한 Entity의 데이터를 보호하고자 하는 의도로 코드를 작성했습니다.

</div>
</details>


<details>
<summary> @Builder 패턴 사용 시 매개변수를 최소화 하는 코드를 작성합니다. </summary>
<div markdown="1">

@Builder 어노테이션을 사용하려면 @AllargsConstructor가 필요합니다.
하지만 이는 모든 매개변수를 받는 생성자를 만들어주기 때문에 불필요한 매개변수를 받는 생성자를 만들어주는 단점이 있습니다.
예를 들어서 User Entity의 경우 id값은 DB에서 자동으로 생성되기 때문에 생성자에서 id값을 받을 필요가 없습니다.
따라서 @Builder를 사용할 때는 @AllArgsConstructor 대신 생성자의 상단에 @Builder를 사용해서 불필요한 매개변수를 받는 생성자를 만들지 않도록 코드를 작성했습니다.

</div>
</details>

---

## Trouble Shooting

<details>
<summary>Error creating bean with name 'postController'</summary>
<div markdown="1">

![2022-12-27_9 35 39](https://user-images.githubusercontent.com/89567475/209871056-d013a2e3-fa62-4dbf-b9ab-eaab340d2683.png)

> TestCode 실행 중 마주친 에러입니다.  
> postController 에 대한 오류 구문이었어서 해당 클래스에 오류가 있는 줄 알고 엄청 찾아봤지만,  
> 구글링에서 나온 자료로는 해결할 수 없었습니다.  
> 왜냐하면 PostController에 대한 오류 구문이었지만,  
> PostController 에 대한 테스트코드는 정상적으로 작동 하고 있었기 때문입니다.  
> 그럼 왜 UserController 에서 오류가 났는지 디버깅을 해보며 해결책을 찾아봤습니다.  
> 해결책은 생각보다 간단했는데, `@WebMvcTest(UserControllerTest.class)`에서  
> `(UserControllerTest.class)`부분을 빼먹었더니 Unit Test에서 모든 의존성을 불러와서 생긴 오류였습니다,,,,,  

</div>
</details>



<details>
<summary>CPU Multi Architecture 에서의 배포</summary>
<div markdown="1">

### 2. CPU Multi Architecture 에서의 배포

![스크린샷 2022-12-22 오전 9 25 06](https://user-images.githubusercontent.com/89567475/209870996-a53d3eed-8942-4eec-8df3-f4013cbf233a.png)

![스크린샷 2022-12-24 오전 12 20 59](https://user-images.githubusercontent.com/89567475/209871002-0d4c61cc-1ca4-40a4-81ca-768d0ffc52a1.png)

> 제가 사용하고 있는 서버 환경은 총 2가지 입니다.
>  
> 1. Raspberry 4b
> 2. AWS
> 
> 위 두 서버는 CPU Architecture가 다르기 때문에 Docker Image 를 두 가지로 빌드 해줘야하는 상황이 발생했습니다.  
> 그래서 처음에는 두 Architecture를 각각 빌드해서 Docker Image 의 Tag를 다르게 빌드하고  
> 각 환경에서 맞는 Architectur의 Tag를 지정해서 끌어다쓰는 방식으로 CI PipeLine을 구성했습니다.  
> 그러나, 하나씩 빌드하는 CI PipeLine 이었고, 각각 빌드하는데 시간이 거의 20분 가량 소모되었습니다.  
> 그리고 각각 Tag를 지정해서 Docker Container 를 띄우는 것도 CI/CD의 목적과는 맞지 않는다고 생각해서  
> 두 가지 Architecture 를 병렬적으로 빌드할 수 있고 하나의 이미지로 합쳐서 관리할 수 있는 방법을 찾아보았고  
> CI PipeLine 상에서 두 가지 Architecture에 대해 병렬적으로 빌드한 뒤  
> Docker Manifest 기능을 이용해서 두가지 이미지를 하나로 합쳐주는 작업을 진행했습니다.  
> 해당 Trouble Shooting 과정을 거치면서 기존에 20분 걸리던 빌드 시간은 약 3분으로 단축되었습니다.  


</div>
</details>



