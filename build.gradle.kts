val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.ktor.plugin") version "2.3.0"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

group = "com.follis"
version = "0.0.1"
application {
    mainClass.set("com.follis.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.ktorm:ktorm-core:3.6.0")
    implementation("com.mysql:mysql-connector-j:8.0.33")
    implementation("com.google.code.gson:gson:2.8.9")
    // https://mvnrepository.com/artifact/org.mindrot/jbcrypt
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    // https://mvnrepository.com/artifact/io.ktor/ktor-auth
//    implementation("io.ktor:ktor-auth:1.6.8")
// https://mvnrepository.com/artifact/io.ktor/ktor-auth-jwt
//    implementation("io.ktor:ktor-auth-jwt:1.6.8")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")


}