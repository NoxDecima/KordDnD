FROM openjdk:11-jdk-slim

ENV APPLICATION_USER kord
RUN adduser --disabled-login --ingroup '' $APPLICATION_USER

RUN mkdir /app

# Create fatJar
RUN mkdir /temp
COPY . /temp
RUN cd /temp
WORKDIR /temp
RUN cat settings.gradle.kts
RUN ./gradlew --no-daemon fatJar

RUN cp /temp/build/libs/KordDnD.jar /app

# delete temporary files
RUN rm -R /temp

RUN chown -R $APPLICATION_USER /app
USER $APPLICATION_USER

WORKDIR /app

CMD ["java", "-jar", "KordDnD.jar"]

