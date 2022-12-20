############################################################################################
##
##  Record for me in the future (:D)
##  If you're still building and using the server on RaspberryPi
##  Check the contents below
##  Server architecture : arm64(arrch64) == arm64/v8
##  So we need an image that supports arm64/v8
##
##  Example)
##  Check https://hub.docker.com/_/gradle OS/ARCH tab :)
##  (If the tool you are going to use does not support arm, Search on the DockerHub)
##  Then to use CI/CD you have to build multi-cpu architecture for every architecture !
##
###############################################################################################

# FROM arm64v8/gradle:jdk11-jammy AS builder
FROM --platform=$BUILDPLATFORM gradle:jdk11-jammy AS builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:11.0-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                                \
   "java",                                                 \
   "-jar",                                                 \
   "-Djava.security.egd=file:/dev/./urandom",              \
   "-Dsun.net.inetaddr.ttl=0",                             \
   "app.jar"              \
]