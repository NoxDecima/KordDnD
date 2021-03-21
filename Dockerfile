FROM openjdk:11-jdk as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM openjdk:11-jre-slim

ENV APPLICATION_USER kord
RUN adduser --disabled-login --ingroup '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY --from=builder build/libs/KordDnD.jar /app

WORKDIR /app

CMD ["java", "-jar", "KordDnD.jar"]

