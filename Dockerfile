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

ARG REDIS_PORT
ENV REDIS_PORT ${REDIS_PORT}

ARG JWT_SECRET_KEY
ENV JWT_SECRET_KEY ${JWT_SECRET_KEY}

ARG JWT_ACCESS_EXP
ENV JWT_ACCESS_EXP ${JWT_ACCESS_EXP}

ARG JWT_REFRESH_EXP
ENV JWT_REFRESH_EXP ${JWT_REFRESH_EXP}

ARG JWT_HEADER
ENV JWT_HEADER ${JWT_HEADER}

ARG JWT_PREFIX
ENV JWT_PREFIX ${JWT_PREFIX}

ARG NEIS_URL
ENV NEIS_URL ${NEIS_URL}

ARG DAE_URL
ENV DAE_URL ${DAE_URL}

ARG DSM_LOGIN
ENV DSM_LOGIN ${DSM_LOGIN}

ARG NEIS_KEY
ENV NEIS_KEY ${NEIS_KEY}

ARG WEBHOOK_MESSAGE
ENV WEBHOOK_MESSAGE ${WEBHOOK_MESSAGE}

ARG AWS_ACCESS_KEY
ENV AWS_ACCESS_KEY ${AWS_ACCESS_KEY}

ARG AWS_SECRET_KEY
ENV AWS_SECRET_KEY ${AWS_SECRET_KEY}

ARG AWS_BUCKET
ENV AWS_BUCKET ${AWS_BUCKET}

ARG AWS_STATIC
ENV AWS_STATIC ${AWS_STATIC}

ARG FIREBASE_URL
ENV FIREBASE_URL ${FIREBASE_URL}

ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} application.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]

