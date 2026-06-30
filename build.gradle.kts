plugins {
    java
     application
    id("com.gradleup.shadow") version "8.3.6"
    id("org.danilopianini.gradle-java-qa") version "1.188.0"
}

tasks.withType<Javadoc>().configureEach {
    isFailOnError = false
}