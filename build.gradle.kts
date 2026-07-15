plugins {
    java
     application
    id("com.gradleup.shadow") version "9.4.3"
    id("org.danilopianini.gradle-java-qa") version "1.188.0"
}

repositories { // Where to search for dipendencies
    mavenCentral()
}

tasks.withType<Javadoc>().configureEach {
    isFailOnError = false
}

application {
    mainClass.set("it.unibo.TowerSiege.application.TowerSiege")
}