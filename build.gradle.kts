import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("application")
}

group = "ru.koskokos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")
    implementation("com.google.api-client:google-api-client:1.23.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev493-1.23.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.23.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.koskokos.moneybot.Main"
    }
}