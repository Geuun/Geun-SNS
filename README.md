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

![ERD](/uploads/fc0ce73c24b24c416bab4e3c35162c01/스크린샷_2022-12-27_오후_6.40.25.png)

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




