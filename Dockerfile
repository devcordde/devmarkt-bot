FROM gradle:jdk21-alpine as build

COPY . .
RUN gradle clean build --no-daemon

FROM eclipse-temurin:21-alpine as runtime

WORKDIR /app

# Its using application plugin. So this doesnt work
COPY --from=build /home/gradle/bot/build/libs/bot-*.jar bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]
