# GEUN - SNS

## Summery

> 나만의 작고 소중한 🥹 SNS 만들기...

- 회원가입 기능
- 로그인 기능
- 포스트 작성, 수정, 삭제, 페이징 기능
- CI/CD PipeLine 구축으로 지속적인 통합과 지속적인 배포환경 구성
- Swagger API 명세 기능


## Development environment

<div align="center">
 <img src="https://img.shields.io/badge/SpringBoot-6DB33F.svg?logo=Spring-Boot&logoColor=white" />
 <img src="https://img.shields.io/badge/SpringSecurity-6DB33F.svg?logo=Spring-Security&logoColor=white" />
 <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a>
 <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/></a>
 <img src="https://img.shields.io/badge/AmazonEC2-FF9900.svg?logo=Amazon-EC2&logoColor=white" />
</div>


- JVM 11 ver
- Spring Boot 2.7.5 ver
- Spring boot JPA
- MySQL
- Spring Security
- Swagger
- GitLab
- Docker

---

## SERVER

> 다양한 배포 경험을 위해서 arm64/amd64 환경에서 동시에 배포되었습니다.

- amd64 : AWS EC2 t3small
- arm64 : RaspberryPi4B 

---

## ERD

![ERD](https://user-images.githubusercontent.com/89567475/209869344-9e87058f-2d22-4c43-a5ba-e43a78d04aa2.png)

---

## EndPoints

- `Raspberry` : http://geun.me:9999/swagger-ui/#/

- `AWS` : ec2-15-164-170-144.ap-northeast-2.compute.amazonaws.com:9999/swagger-ui/#/

|  | REST | Route | API |
|:-----:|:------------------:|:-----------------------------:|:-----------------------------:|
| `hello` | GET | `/api/v1/hello` | CI/CD TEST API |
| `users` | POST | `/api/v1/users/join` | 회원가입 |
| `users` | POST | `/api/v1/users/login` | 로그인 |
| `posts` | POST | `/api/v1/posts` | 포스팅 |
| `posts` | PUT | `/api/v1/posts/{id}` | 글수정기능 |
| `posts` | DELETE | `/api/v1/posts/{id}` | 글삭제기능 |
| `posts` | GET | `/api/v1/posts/{id}` | 글조회기능 |
| `posts` | GET | `/api/v1/posts` | 글전체조회 |
| `comment` | POST | `/api/v1/posts/{id}/comment` | 댓글작성기능 |
| `comment` | PUT | `/api/v1/posts/{postid}/comment/{id}` | 댓글수정기능 |
| `comment` | DELETE | `/api/v1/posts/{postid}/comment/{id}` | 댓글삭제기능 |
| `comment` | GET | `/api/v1/posts/{id}/comment` | 댓글조회기능 |



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



