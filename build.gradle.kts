plugins {
    java
}

tasks.withType<Javadoc>().configureEach {
    isFailOnError = false
}