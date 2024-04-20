object Dependencies {
    // kotlin
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"
    const val JACKSON_TYPE = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersions.JACKSON_VERSION}"

    // web
    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"

    const val REDIS = "org.springframework.boot:spring-boot-starter-data-redis:${DependencyVersions.REDIS_VERSION}"

    // database
    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa:${PluginVersions.SPRING_BOOT_VERSION}"
    const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:${DependencyVersions.MYSQL}"
    const val SPRING_REDIS = "org.springframework.boot:spring-boot-starter-data-redis:${PluginVersions.SPRING_BOOT_VERSION}"

    // security
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"

    // test
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${PluginVersions.SPRING_BOOT_VERSION}"

    // open feign
    const val OPEN_FEIGN = "org.springframework.cloud:spring-cloud-starter-openfeign:${DependencyVersions.OPEN_FEIGN_VERSION}"

    // validation
    const val SPRING_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"

    // json
    const val JSON = "org.json:json:${DependencyVersions.JSON_VERSION}"

    // Jwt
    const val JWT = "io.jsonwebtoken:jjwt:${DependencyVersions.JWT_VERSION}"

    // cloud
    const val SPRING_CLOUD = "org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}"

    const val SWAGGER = "org.springdoc:springdoc-openapi-ui:${DependencyVersions.SWAGGER_VERSION}"

    // QueryDSL
    const val QUERYDSL = "com.querydsl:querydsl-jpa:${DependencyVersions.QUERYDSL}"
    const val QUERYDSL_PROCESSOR = "com.querydsl:querydsl-apt:${DependencyVersions.QUERYDSL}:jpa"

    // apachePOI
    const val APACHE_POI = "org.apache.poi:poi:${DependencyVersions.APACHE_POI}"
    const val APACHE_POI_OOXML = "org.apache.poi:poi-ooxml:${DependencyVersions.APACHE_POI}"

    const val GSON = "com.google.code.gson:gson:${DependencyVersions.GSON}"

    const val AWS = "com.amazonaws:aws-java-sdk-s3:${DependencyVersions.AWS}"
}
