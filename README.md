# GEUN - SNS

![guen SNS](https://user-images.githubusercontent.com/89567475/211623115-bbe83231-7d49-443a-8866-43ec57000a3f.png)

## ๐ Summery

> ๋๋ง์ ์๊ณ  ์์คํ ๐ฅน SNS ๋ง๋ค๊ธฐ... ํ๋ก์ ํธ...

### โ๏ธ Tech Stack

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

### โถ๏ธ URLs

- `Raspberry Home Server` (Arm64) : http://geun.me:9999/swagger-ui/

- `AWS Server` (Amd64) : http://ec2-15-164-170-144.ap-northeast-2.compute.amazonaws.com:9999/swagger-ui/

### โ๏ธ Functional Description

- ํ์๊ฐ์, ๋ก๊ทธ์ธ, ํ์ ๊ถํ ์์  ๊ธฐ๋ฅ
- <s>Redis๋ฅผ ์ด์ฉํ Token ๊ด๋ฆฌ ๋ฐ ๋ก๊ทธ์์ ๊ธฐ๋ฅ</s> (ing...)
- ํฌ์คํธ CRUD ๊ธฐ๋ฅ (D: SoftDelete)
- ๋๊ธ CRUD ๊ธฐ๋ฅ (D: SoftDelete)
- ์ข์์ ๊ธฐ๋ฅ
- ๋ง์ดํผ๋ ๊ธฐ๋ฅ
- ์๋ ๊ธฐ๋ฅ
- CI/CD PipeLine ๊ตฌ์ถ์ผ๋ก ์ง์์ ์ธ ํตํฉ๊ณผ ์ง์์ ์ธ ๋ฐฐํฌํ๊ฒฝ ๊ตฌ์ฑ
- Swagger API ๋ช์ธ ๊ธฐ๋ฅ

---

## ๐ป Development environment

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

## ๐ฅ๏ธ Server

> ๋ค์ํ ๋ฐฐํฌ ๊ฒฝํ์ ์ํด์ `arm64`/`amd64` ํ๊ฒฝ์์ <u>`๋์`</u> ์ ๋ฐฐํฌ๋๊ณ  ์์ต๋๋ค.

- arm64 : RaspberryPi 4B
- amd64 : AWS EC2 t3.small

---

## ๐ Archetecture

![Geun-sns](https://user-images.githubusercontent.com/89567475/211634688-26245e8a-2495-4263-a976-c45550114c92.png)

---

## ๐ ERD

![ERD](https://user-images.githubusercontent.com/89567475/211506328-f92534d6-c4ff-42a7-a12f-4a5017b23a0c.png)

---

## ๐งพ EndPoints

|           | REST |                 Route                 |        API         |
|:---------:|:------------------:|:-------------------------------------:|:------------------:|
|  `hello`  | GET |            `/api/v1/hello`            |   CI/CD TEST API   |
|  `users`  | POST |         `/api/v1/users/join`          |        ํ์๊ฐ์        |
|  `users`  | POST |         `/api/v1/users/login`         |        ๋ก๊ทธ์ธ         |
|  `users`  | POST |         `/api/v1/users/login`         |        ๋ก๊ทธ์ธ         |
|  `users`  | POST |       /api/v1/users/{id}/roles        |   ์ฌ์ฉ์๊ถํ๋ณ๊ฒฝ(Admin)   |
|  `users`  | POST |    /api/v1/users/{id}/roles/admins    | ์ฌ์ฉ์๊ถํ๋ณ๊ฒฝ(SuperUser) |
|  `posts`  | POST |            `/api/v1/posts`            |        ๊ธ ์์ฑ        |
|  `posts`  | PUT |         `/api/v1/posts/{id}`          |        ๊ธ ์์         |
|  `posts`  | DELETE |         `/api/v1/posts/{id}`          |        ๊ธ ์ญ์         |
|  `posts`  | GET |         `/api/v1/posts/{id}`          |      ๊ธ ์ธ๋ถ ์กฐํ       |
|  `posts`  | GET |            `/api/v1/posts`            |      ๊ธ ์ ์ฒด ์กฐํ       |
| `comment` | POST |     `/api/v1/posts/{id}/comment`      |       ๋๊ธ ์์ฑ        |
| `comment` | PUT | `/api/v1/posts/{postid}/comment/{id}` |       ๋๊ธ ์์         |
| `comment` | DELETE | `/api/v1/posts/{postid}/comment/{id}` |       ๋๊ธ ์ญ์         |
| `comment` | GET |     `/api/v1/posts/{id}/comment`      |       ๋๊ธ ์กฐํ        |
|  `good`   | POST |      `/api/v1/posts/{id}/likes`       |      ์ข์์ / ์ทจ์      |
|  `good`   | GET |      `/api/v1/posts/{id}/likes`       |       ์ข์์ ์กฐํ       |
| `myfeed`  | GET |          `/api/v1/posts/my`           |      ๋ง์ดํผ๋ ์กฐํ       |
|  `alarm`  | GET |         `/api/v1/users/alarms`          |       ์๋ ์กฐํ        |

<details>
<summary> Detail API Specification </summary>
<div markdown="1">

### UserJoinRequest 
```json lines
UserJoinRequest{
  password: String,
  userName: String
}
```

### UserLoginRequest
```json lines
UserLoginRequest{
password:string,
userName:string
}
```

### UserRoleChangeRequest
```json lines
UserRoleChangeRequest{
roleToBeChanged:string
}
```

### PostAddRequest
```json lines
PostAddRequest{
body:string,
title:string
}
```

### PostUpdateRequest
```json lines
PostUpdateRequest{
body:string,
title:string
}
```

### CommentAddRequest
```json lines
CommentAddRequest{
comment:string
}
```

### CommentUpdateRequest
```json lines
CommentUpdateRequest{
comment:string
}
```

</div>
</details>


---

## ๐ง What I paid special attention to

### Lombok์ ํ์ ์๊ฐ๋ณด๋ค ๊ฐํ๋ค...!!!

<details>
<summary> @Setter ์ฌ์ฉ์ ์ง์ํ๋ ์ฝ๋๋ฅผ ์์ฑํฉ๋๋ค. </summary>
<div markdown="1">

์ฝ๋ ์ ๋ฐ์ ์ผ๋ก Entity์ Setter๋ฅผ ์ฌ์ฉํ์ง ์๋ ์ ๋ต์ ์ฌ์ฉํ์ต๋๋ค.
Entity ๊ฐ์ฒด์ ๋ฐ์ดํฐ๋ ๋ถ๋ณํ ์ฑ์ง์ ๊ฐ์ ธ์ผํ๊ธฐ ๋๋ฌธ์ ์์ ๊ฐ์ ์ ๋ต์ ์ฌ์ฉํด์ ์ฝ๋๋ฅผ ์์ฑํ์ต๋๋ค. 

</div>
</details>

<details>
<summary> @NoArgsConstructor์ ์ ๊ทผ์ ์ต์ํํ๋ ์ฝ๋๋ฅผ ์์ฑํฉ๋๋ค. </summary>
<div markdown="1">

JPA์ ์ฌ์ฉ์ ์ํด์ ๊ธฐ๋ณธ์์ฑ์๋ ํ์๊ฐ ํ์ํฉ๋๋ค.
ํ์ง๋ง ๋ชจ๋  ์ ๊ทผ ๊ถํ์ ํ์ฉํ๊ฒ๋๋ฉด ๊ฐ์ฒด์ ์์ ์ฑ์ ๋จ์ด๋จ๋ฆด ์ ์์ต๋๋ค.
๋ฐ๋ผ์ `@NoArgsConstructor(access = AccessLevel.PROTECTED)` ๋ฅผ ์ฌ์ฉํด ๋ฌด๋ถ๋ณํ ๊ธฐ๋ณธ์์ฑ์์ ์ ๊ทผ์ ๋ง์์
์ต๋ํ Entity์ ๋ฐ์ดํฐ๋ฅผ ๋ณดํธํ๊ณ ์ ํ๋ ์๋๋ก ์ฝ๋๋ฅผ ์์ฑํ์ต๋๋ค.

</div>
</details>


<details>
<summary> @Builder ํจํด ์ฌ์ฉ ์ ๋งค๊ฐ๋ณ์๋ฅผ ์ต์ํ ํ๋ ์ฝ๋๋ฅผ ์์ฑํฉ๋๋ค. </summary>
<div markdown="1">

@Builder ์ด๋ธํ์ด์์ ์ฌ์ฉํ๋ ค๋ฉด @AllargsConstructor๊ฐ ํ์ํฉ๋๋ค.
ํ์ง๋ง ์ด๋ ๋ชจ๋  ๋งค๊ฐ๋ณ์๋ฅผ ๋ฐ๋ ์์ฑ์๋ฅผ ๋ง๋ค์ด์ฃผ๊ธฐ ๋๋ฌธ์ ๋ถํ์ํ ๋งค๊ฐ๋ณ์๋ฅผ ๋ฐ๋ ์์ฑ์๋ฅผ ๋ง๋ค์ด์ฃผ๋ ๋จ์ ์ด ์์ต๋๋ค.
์๋ฅผ ๋ค์ด์ User Entity์ ๊ฒฝ์ฐ id๊ฐ์ DB์์ ์๋์ผ๋ก ์์ฑ๋๊ธฐ ๋๋ฌธ์ ์์ฑ์์์ id๊ฐ์ ๋ฐ์ ํ์๊ฐ ์์ต๋๋ค.
๋ฐ๋ผ์ @Builder๋ฅผ ์ฌ์ฉํ  ๋๋ @AllArgsConstructor ๋์  ์์ฑ์์ ์๋จ์ @Builder๋ฅผ ์ฌ์ฉํด์ ๋ถํ์ํ ๋งค๊ฐ๋ณ์๋ฅผ ๋ฐ๋ ์์ฑ์๋ฅผ ๋ง๋ค์ง ์๋๋ก ์ฝ๋๋ฅผ ์์ฑํ์ต๋๋ค.

</div>
</details>

---

## ๐ ๏ธ Trouble Shooting

<details>
<summary>Error creating bean with name 'postController'</summary>
<div markdown="1">

![2022-12-27_9 35 39](https://user-images.githubusercontent.com/89567475/209871056-d013a2e3-fa62-4dbf-b9ab-eaab340d2683.png)

> TestCode ์คํ ์ค ๋ง์ฃผ์น ์๋ฌ์๋๋ค.  
> postController ์ ๋ํ ์ค๋ฅ ๊ตฌ๋ฌธ์ด์์ด์ ํด๋น ํด๋์ค์ ์ค๋ฅ๊ฐ ์๋ ์ค ์๊ณ  ์์ฒญ ์ฐพ์๋ดค์ง๋ง,  
> ๊ตฌ๊ธ๋ง์์ ๋์จ ์๋ฃ๋ก๋ ํด๊ฒฐํ  ์ ์์์ต๋๋ค.  
> ์๋ํ๋ฉด PostController์ ๋ํ ์ค๋ฅ ๊ตฌ๋ฌธ์ด์์ง๋ง,  
> PostController ์ ๋ํ ํ์คํธ์ฝ๋๋ ์ ์์ ์ผ๋ก ์๋ ํ๊ณ  ์์๊ธฐ ๋๋ฌธ์๋๋ค.  
> ๊ทธ๋ผ ์ UserController ์์ ์ค๋ฅ๊ฐ ๋ฌ๋์ง ๋๋ฒ๊น์ ํด๋ณด๋ฉฐ ํด๊ฒฐ์ฑ์ ์ฐพ์๋ดค์ต๋๋ค.  
> ํด๊ฒฐ์ฑ์ ์๊ฐ๋ณด๋ค ๊ฐ๋จํ๋๋ฐ, `@WebMvcTest(UserControllerTest.class)`์์  
> `(UserControllerTest.class)`๋ถ๋ถ์ ๋นผ๋จน์๋๋ Unit Test์์ ๋ชจ๋  ์์กด์ฑ์ ๋ถ๋ฌ์์ ์๊ธด ์ค๋ฅ์์ต๋๋ค,,,,,  

</div>
</details>



<details>
<summary>CPU Multi Architecture ์์์ ๋ฐฐํฌ</summary>
<div markdown="1">

### 2. CPU Multi Architecture ์์์ ๋ฐฐํฌ

![แแณแแณแแตแซแแฃแบ 2022-12-22 แแฉแแฅแซ 9 25 06](https://user-images.githubusercontent.com/89567475/209870996-a53d3eed-8942-4eec-8df3-f4013cbf233a.png)

![แแณแแณแแตแซแแฃแบ 2022-12-24 แแฉแแฅแซ 12 20 59](https://user-images.githubusercontent.com/89567475/209871002-0d4c61cc-1ca4-40a4-81ca-768d0ffc52a1.png)

> ์ ๊ฐ ์ฌ์ฉํ๊ณ  ์๋ ์๋ฒ ํ๊ฒฝ์ ์ด 2๊ฐ์ง ์๋๋ค.
>  
> 1. Raspberry 4b
> 2. AWS
> 
> ์ ๋ ์๋ฒ๋ CPU Architecture๊ฐ ๋ค๋ฅด๊ธฐ ๋๋ฌธ์ Docker Image ๋ฅผ ๋ ๊ฐ์ง๋ก ๋น๋ ํด์ค์ผํ๋ ์ํฉ์ด ๋ฐ์ํ์ต๋๋ค.  
> ๊ทธ๋์ ์ฒ์์๋ ๋ Architecture๋ฅผ ๊ฐ๊ฐ ๋น๋ํด์ Docker Image ์ Tag๋ฅผ ๋ค๋ฅด๊ฒ ๋น๋ํ๊ณ   
> ๊ฐ ํ๊ฒฝ์์ ๋ง๋ Architectur์ Tag๋ฅผ ์ง์ ํด์ ๋์ด๋ค์ฐ๋ ๋ฐฉ์์ผ๋ก CI PipeLine์ ๊ตฌ์ฑํ์ต๋๋ค.  
> ๊ทธ๋ฌ๋, ํ๋์ฉ ๋น๋ํ๋ CI PipeLine ์ด์๊ณ , ๊ฐ๊ฐ ๋น๋ํ๋๋ฐ ์๊ฐ์ด ๊ฑฐ์ 20๋ถ ๊ฐ๋ ์๋ชจ๋์์ต๋๋ค.  
> ๊ทธ๋ฆฌ๊ณ  ๊ฐ๊ฐ Tag๋ฅผ ์ง์ ํด์ Docker Container ๋ฅผ ๋์ฐ๋ ๊ฒ๋ CI/CD์ ๋ชฉ์ ๊ณผ๋ ๋ง์ง ์๋๋ค๊ณ  ์๊ฐํด์  
> ๋ ๊ฐ์ง Architecture ๋ฅผ ๋ณ๋ ฌ์ ์ผ๋ก ๋น๋ํ  ์ ์๊ณ  ํ๋์ ์ด๋ฏธ์ง๋ก ํฉ์ณ์ ๊ด๋ฆฌํ  ์ ์๋ ๋ฐฉ๋ฒ์ ์ฐพ์๋ณด์๊ณ   
> CI PipeLine ์์์ ๋ ๊ฐ์ง Architecture์ ๋ํด ๋ณ๋ ฌ์ ์ผ๋ก ๋น๋ํ ๋ค  
> Docker Manifest ๊ธฐ๋ฅ์ ์ด์ฉํด์ ๋๊ฐ์ง ์ด๋ฏธ์ง๋ฅผ ํ๋๋ก ํฉ์ณ์ฃผ๋ ์์์ ์งํํ์ต๋๋ค.  
> ํด๋น Trouble Shooting ๊ณผ์ ์ ๊ฑฐ์น๋ฉด์ ๊ธฐ์กด์ 20๋ถ ๊ฑธ๋ฆฌ๋ ๋น๋ ์๊ฐ์ ์ฝ 3๋ถ์ผ๋ก ๋จ์ถ๋์์ต๋๋ค.  


</div>
</details>

---

## ๐คโ๏ธ Project Retrospective

<details>
<summary> 1. Logout ๊ธฐ๋ฅ </summary>
<div markdown="1">

> ๋ง์ง๋ง์ Logout ์ ๊ธฐ๋ฅ์ ๊ตฌํํ๋ ค ์๋ํ์ผ๋ ์๋ฒฝํ๊ฒ ๊ตฌํํ์ง ๋ชปํ์ต๋๋ค.  
> Redis์ ๊ธฐ๋ฅ์ ๋์ํ์ง๋ง ์์ง Spring Security์ Token ์ธ์ฆ ๋ฐฉ์์ ์ ํํ ์ดํด๊ฐ ๋ถ์กฑํ ๊ฒ ๊ฐ์ต๋๋ค.

> Token์ธ์ฆ ๋ฐฉ์์ ์๋ฒ์์ ๋ฐ๊ธํ ์ดํ์๋ ์๋ฒ์์๋ ํน๋ณํ๊ฒ ์ธ์ฆ ์ ์ฐจ๋ฅผ ๊ฑฐ์น์ง ์๊ธฐ ๋๋ฌธ์  
> Acceess Token / Refresh Token ๋ ๊ฐ์ง Token์ ๋ฐ๊ธํ๊ณ   
> Access Token์ ๋ง๋ฃ ์๊ฐ์ ์งง๊ฒ ์ค์ ํ์ฌ Refresh Token์ ์ด์ฉํด ์ฌ๋ฐ๊ธ์ ๋ฐ๋ ๋ฐฉ์์ ๋ง์ด ์ฌ์ฉํ๋ ๊ฒ ๊ฐ๋ค.
> ๋๋ ๋ง์ฐฌ๊ฐ์ง๋ก ํด๋น ๋ฐฉ์์ผ๋ก ๊ตฌํํ๋ คํ์ผ๋, ๋ง์ง๋ง์ ์ ์ฉ ํ Spring Security ๋จ์์ Filter๋ฅผ ์ ์ฉํ๋ ๊ณผ์ ์์  
> ์ ์ ์ ํ ํฐ์ด ๋ฐํ๋ ํ ๋ชจ๋  ๋ก์ง์ด 500 Error๊ฐ ๋์์ต๋๋ค.  
> ์ด ๋ถ๋ถ์ ์์ผ๋ก ๋ฆฌํํ ๋ง์ ์งํํด์ผ๊ฒ ์ต๋๋ค.

</div>
</details>

<details>
<summary> 2. CI/CD ๋์ </summary>
<div markdown="1">

> Raspberry Pi4 ๋ฅผ ์ด์ฉํ HomeServer๋ฅผ ๊ตฌ์ถํ๋ฉด์ ๊ฐ์ธ ํ๋ก์ ํธ๋ฅผ Deployํ๋ ์ฉ๋๋ก ์ฌ์ฉํ๊ณ  ์์ต๋๋ค.  
> ํ์ง๋ง Raspberry Pi๋ Cpu Architecture๊ฐ Arm ๊ธฐ๋ฐ์ด๊ธฐ ๋๋ฌธ์ ํด๋น ์๋ฒ์ ๋ค๋ฅธ Architecture์ Docker Image๋ฅผ Deploy ํ  ์ ์์์ต๋๋ค.  
> ๊ทธ๋์ Docker Manifest ๊ธฐ๋ฅ์ ์ด์ฉํด์ ๋๊ฐ์ง Architecture์ ๋ํด ๋ณ๋ ฌ์ ์ผ๋ก ๋น๋ํ ๋ค  
> ํ๋์ ์ด๋ฏธ์ง๋ก ํฉ์ณ์ ๊ด๋ฆฌํ  ์ ์๋ ๋ฐฉ๋ฒ์ ์ฐพ์๋ณด์๊ณ   
> CI PipeLine ์์์ ๋ ๊ฐ์ง Architecture์ ๋ํด ๋ณ๋ ฌ์ ์ผ๋ก ๋น๋ํ ๋ค  
> Docker Manifest ๊ธฐ๋ฅ์ ์ด์ฉํด์ ๋๊ฐ์ง ์ด๋ฏธ์ง๋ฅผ ํ๋๋ก ํฉ์ณ์ฃผ๋ ์์์ ์งํํ์ต๋๋ค.  
> ํด๋น Trouble Shooting ๊ณผ์ ์ ๊ฑฐ์น๋ฉด์ ๊ธฐ์กด์ 20๋ถ ๊ฑธ๋ฆฌ๋ ๋น๋ ์๊ฐ์ ์ฝ 3๋ถ์ผ๋ก ๋จ์ถ๋์์ต๋๋ค.  
> ์ด ๊ณผ์ ์ ํตํด Docker Manifest ๊ธฐ๋ฅ์ ๋ํด ์๊ฒ๋์๊ณ , Docker๋ก ๋ฐฐํฌํ  ๋๋ ์ฌ๋ฌ Cpu Architecture์ ๋ํด ๊ณ ๋ คํด์ค์ผ ํ๋ค๋ ์ ์ ์๊ฒ๋์์ต๋๋ค.   
> ํ์ง๋ง ํด๋น Trouble Shooting์ ์งํํ๋๋ฐ ๋๋ฌด ๋ง์ ์๊ฐ์ ๋ค์ธ ๊ฒ ๊ฐ์์ ์์ฝ์ต๋๋ค..  
> ์์ผ๋ก ์งํํ  ํ๋ก์ ํธ์์๋ ํ๋์ ๋๋ฌด ๋ง์ ์๊ฐ์ ํฌ์ํ์ง ์๊ณ  ์ ์ ํ ์๊ฐ ๋ถ๋ฐฐ๊ฐ ํ์ํ  ๊ฒ ๊ฐ์ต๋๋ค.

</div>
</details>

<details>
<summary> 3. FrontEnd ๊ตฌํ </summary>
<div markdown="1">

> FrontEnd ๋ฅผ ์๊ฒ๋๋ง ๊ตฌํํด๋ณด๋ฉด ๋ ์ข์ BackEnd ์ฝ๋๋ฅผ ์์ฑํ  ์ ์๊ฒ ๋์ง์์๊น ํ๋ ์๊ฐ๊ณผ   
> ๋ํด์ BackEnd ๋ง ๋๋  ์์ผ๋ ๋ญ๊ฐ ๋ฐ์ชฝ ์ง๋ฆฌ ์น ์๋น์ค๊ฐ ๋๋ ๊ฒ ๊ฐ์์ React๋ฅผ ์ด์ฉํด์ Frontend๋ฅผ ๊ตฌํํ  ๊ณํ์ด์์ต๋๋ค.  
> ํ์ง๋ง ์์ ์งํํ CI/CD๋ฅผ ์งํํ๋ฉด์ ์๊ฐ์ด ๋ถ์กฑํด์ ์ด๋ฒ ํ๋ก์ ํธ ๊ธฐ๊ฐ ๋ด์ React๋ฅผ ์ฌ์ฉํด๋ณด์ง ๋ชปํ ์ ์ด ์กฐ๊ธ ์์ฝ์ต๋๋ค.
> ๊ฐ์ธ์ ์ผ๋ก ์๊ฐ์ ์กฐ๊ธ ๋ ํ ์  ํด์ ํด๋น ๋ถ๋ถ์ ๊ตฌํ์ ํด๋ด์ผ๊ฒ ์ต๋๋ค.

</div>
</details>


<details>
<summary> 4. ์ค๋ณต๋๋ ๋ก์ง ์ ๊ฑฐ </summary>
<div markdown="1">

> ์ฝ๋๋ฅผ ์์ฑํ๋ฉฐ Controller ๋จ์์ ์ ์ ์ ๊ถํ์ ๋ณด ์ฆ Authentication ์ ๋ณด๋ฅผ ํ์ธํ๋ ๋ก์ง์ด ์ค๋ณต๋๋ ๊ฒ์ ๋ฐ๊ฒฌํ์ต๋๋ค.
> ํ๋ฒ ์ธ์ฆ์ ํ๊ฒ๋๋ฉด ์ดํ์ ๋ก์ง์์๋ Authentication ์ ๋ณด๋ฅผ ํ์ธํ  ํ์๊ฐ ์๋ค๊ณ  ์๊ฐ์ด ๋๋๋ฐ,
> ์ด ๋ถ๋ถ์ ํ ๋ฒ ๋ ์ฐพ์๋ณด๊ณ  Refactoring์ ์งํํด์ผ ํ  ๊ฒ ๊ฐ์ต๋๋ค.

</div>
</details>


<details>
<summary> 5. TestCode ๊ด๋ จ </summary>
<div markdown="1">

> ์ด๋ฒ ํ๋ก์ ํธ๋ฅผ ํตํด์ TDD ๋ฅผ ์งํํ๋ฉด์ TDD๊ฐ ๊ฐ๋ฐ์๊ฐ์ ๋ง์ด ๋ค์ง๋ง,  
> ์ ์ฒด์ ์ธ ๋ก์ง์ ๊ตฌ์ํ๊ณ  ์ฝ๋๋ฅผ ์์ฑํ๋๋ฐ ์์ฒญ๋ ๋์์ด ๋๋ค๋ ๊ฒ์ ์ด๋ฒ ํ๋ก์ ํธ๋ฅผ ํตํด์ ๋๊ผ์ต๋๋ค.  
> ์ต๊ทผ์ Unit Test์ ๋ํด ์งํํ๋ฉด์ Test Code๋ฅผ ์์ฑํ๋ ๋ฐฉ๋ฒ๋ก ์ ๋ํด ๋ง์ด ๋ณด๊ฒ๋์๊ณ   
> ๊ทธ ์ค `BDD(Behavior Driven Development)` ์ `SDD (Specification Driven Development)` ์ ๋ํด ์๊ฒ๋์์ต๋๋ค.  
> `BDD`๋ ํ๋์ ๊ธฐ๋ฅ์ ๊ตฌํํ๊ธฐ ์ํด ์ด๋ค ํ๋์ ํด์ผํ๋์ง์ ๋ํ ์๋๋ฆฌ์ค์ ํ์คํธ ์ฝ๋๋ฅผ ์์ฑํ๋ ๊ฒ์ธ๋ฐ,  
> `BDD` ๋ก๋ ํ ๋ฒ ์งํํด๋ณด๋ฉด ์ข์ ๊ฒ ๊ฐ๋ค๋ ์๊ฐ์ด ๋ค์์ต๋๋ค.

</div>
</details>
