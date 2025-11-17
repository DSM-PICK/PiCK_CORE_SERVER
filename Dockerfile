FROM --platform=$BUILDPLATFORM gradle:8.5-jdk17 AS deps
WORKDIR /app
COPY gradle gradle
COPY gradlew gradlew.bat ./
COPY settings.gradle.kts ./
RUN ./gradlew --version
COPY buildSrc buildSrc
RUN ./gradlew buildSrc:build --no-daemon --parallel
COPY build.gradle.kts ./
RUN ./gradlew dependencies --no-daemon --parallel --build-cache

FROM deps AS builder
COPY src src
RUN ./gradlew bootJar -x test --no-daemon --parallel --build-cache \
    -Dorg.gradle.caching=true \
    -Dorg.gradle.parallel=true \
    -Dorg.gradle.jvmargs="-Xmx2g -XX:+UseParallelGC" && \
    mkdir -p extracted && \
    java -Djarmode=layertools -jar build/libs/*.jar extract --destination extracted

FROM eclipse-temurin:17-jre AS optimizer
WORKDIR /app
COPY --from=builder /app/extracted/dependencies/ ./
COPY --from=builder /app/extracted/spring-boot-loader/ ./
COPY --from=builder /app/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/extracted/application/ ./

RUN EXIT_CODE=0; \
    java -XX:ArchiveClassesAtExit=/app/app.jsa \
    -Dspring.context.exit=onRefresh \
    org.springframework.boot.loader.launch.JarLauncher || EXIT_CODE=$?; \

FROM gcr.io/distroless/java17-debian12:nonroot
WORKDIR /app

COPY --from=optimizer /app/ ./

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:InitialRAMPercentage=50.0 \
-XX:+UseG1GC \
-XX:MaxGCPauseMillis=100 \
-XX:G1HeapRegionSize=16m \
-XX:ParallelGCThreads=2 \
-XX:ConcGCThreads=1 \
-XX:+UseStringDeduplication \
-XX:+OptimizeStringConcat \
-XX:+UseCompressedOops \
-XX:+UseCompressedClassPointers \
-XX:+TieredCompilation \
-XX:+ExitOnOutOfMemoryError \
-XX:+AlwaysPreTouch \
-XX:+UseNUMA \
-XX:+DisableExplicitGC \
-XX:ReservedCodeCacheSize=256m \
-XX:+UnlockExperimentalVMOptions \
-XX:+UseContainerCpuShares \
-XX:SharedArchiveFile=/app/app.jsa \
-Djava.security.egd=file:/dev/./urandom \
-Dspring.backgroundpreinitializer.ignore=true \
-Dspring.jmx.enabled=false \
-Dspring.main.lazy-initialization=false"

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
