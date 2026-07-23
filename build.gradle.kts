plugins {
    //Apply the java plugin to add support for Java
    java

    //Apply the application plugin to add support foe building a CLI application
    application

    id("com.gradleup.shadow") version "9.4.3"
    id("org.danilopianini.gradle-java-qa") version "1.164.0"
    id("org.danilopianini.unibo-oop-gradle-plugin") version "2.0.22"
}

repositories { // Where to search for dipendencies
    mavenCentral()
}

tasks.withType<Javadoc>().configureEach {
    isFailOnError = false;
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
    val jUnitVersion = "5.11.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

application {
    mainClass.set("it.unibo.towersiege.application.TowerSiege")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}