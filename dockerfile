FROM openjdk:17-jdk

ARG PROFILE
ENV PROFILE ${PROFILE}

ARG DB_URL
ENV DB_URL ${DB_URL}

ARG DB_USERNAME
ENV DB_USERNAME ${DB_USERNAME}

ARG DB_PASSWORD
ENV DB_PASSWORD ${DB_PASSWORD}

ARG REDIS_HOST
ENV REDIS_HOST ${REDIS_HOST}

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=doker", "-jar", "app.jar"]
