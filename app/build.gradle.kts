plugins {
    id("java")
    id("application")
    id("io.freefair.lombok") version "8.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("jacoco")
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("com.h2database:h2:2.3.232")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("com.zaxxer:HikariCP:5.1.0")

    implementation("gg.jte:jte:3.1.9")
    implementation("io.javalin:javalin:6.1.3")
    implementation("io.javalin:javalin-rendering:6.1.3")

    implementation("com.konghq:unirest-java:3.14.5")
    implementation("org.jsoup:jsoup:1.17.2")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("io.javalin:javalin-testtools:6.1.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}