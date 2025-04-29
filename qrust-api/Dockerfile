# 1단계: 빌드 전용 스테이지
FROM gradle:8.5-jdk21 AS build

# 앱 경로를 변수로 설정
ARG APP_DIR=/home/app

# 소스 코드 복사
COPY .. ${APP_DIR}
WORKDIR ${APP_DIR}

# 빌드 실행
RUN gradle build --no-daemon -x test

# 2단계: 실행 전용 스테이지
FROM openjdk:21-jdk

# 앱 경로를 변수로 설정 (두 번째 스테이지에서도 동일하게 유지)
ARG APP_DIR=/home/app


# 빌드 결과물 복사
ARG JAR_FILE=build/libs/*.jar


COPY --from=build ${APP_DIR}/${JAR_FILE} app.jar

# 포트 오픈
EXPOSE 8080

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
