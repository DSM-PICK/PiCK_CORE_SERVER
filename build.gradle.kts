import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KLINT_VERSION
    kotlin("jvm") version PluginVersions.JVM_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    kotlin("kapt") version PluginVersions.KAPT_VERSION
}

dependencyManagement {
    imports {
        mavenBom(Dependencies.SPRING_CLOUD)
    }
}

group = "DSM"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {

    implementation(Dependencies.SPRING_DATA_JPA)
    implementation(Dependencies.SPRING_REDIS)
    runtimeOnly(Dependencies.MYSQL_CONNECTOR)

    implementation(Dependencies.SPRING_WEB)

    implementation(Dependencies.SPRING_SECURITY)

    implementation(Dependencies.JACKSON)
    implementation(Dependencies.KOTLIN_REFLECT)

    testImplementation(Dependencies.SPRING_TEST)

    implementation(Dependencies.SPRING_VALIDATION)

    implementation(Dependencies.JSON)

    implementation(Dependencies.JWT)

    implementation(Dependencies.MYSQL_CONNECTOR)

    implementation(Dependencies.OPEN_FEIGN)

    implementation(Dependencies.SWAGGER)

    implementation(Dependencies.REDIS)

    implementation(Dependencies.QUERYDSL)
    kapt(Dependencies.QUERYDSL_PROCESSOR)

    implementation(Dependencies.APACHE_POI)
    implementation(Dependencies.APACHE_POI_OOXML)

    implementation(Dependencies.GSON)

    implementation(Dependencies.AWS)

    implementation(Dependencies.CLOUD)

    implementation(Dependencies.CACHE)

    implementation(Dependencies.FCM)

    implementation(Dependencies.WEB_SOCKET)

    implementation(Dependencies.SMTP)

    implementation(Dependencies.GOOGLE_OAUTH)

    implementation(Dependencies.QUARTZ)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
