plugins {
    java
     application
    id("com.gradleup.shadow") version "9.4.3"
    id("org.danilopianini.gradle-java-qa") version "1.188.0"
}

repositories { // Where to search for dipendencies
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
    val jUnitVersion = "5.11.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

tasks.withType<Javadoc>().configureEach {
    isFailOnError = false
}

application {
    mainClass.set("it.unibo.TowerSiege.application.TowerSiege")
}