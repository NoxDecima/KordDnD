FROM openjdk:11-jdk-slim

# create user and app dir
ENV APPLICATION_USER kord
RUN adduser --disabled-login --ingroup '' $APPLICATION_USER
RUN mkdir /app

# create and move fatJar
RUN mkdir /temp
COPY . /temp
WORKDIR /temp
RUN ./gradlew --no-daemon fatJar
RUN cp /temp/build/libs/KordDnD.jar /app

# delete temporary files
RUN rm -R /temp

# switch to user
RUN chown -R $APPLICATION_USER /app
USER $APPLICATION_USER

WORKDIR /app

CMD ["java", "-jar", "KordDnD.jar"]

