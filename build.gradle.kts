plugins {
    //Apply the java plugin to add support for Java
    java

    //Apply the application plugin to add support foe building a CLI application
    application

    id("com.gradleup.shadow") version "9.4.2"
    
    id("org.danilopianini.unibo-oop-gradle-plugin") version "2.0.22"
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories { // Where to search for dipendencies
    mavenCentral()
}

tasks.withType<Javadoc>().configureEach {
    isFailOnError = false
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
    
    // Usa la BOM per gestire in automatico le versioni coerenti di JUnit
    testImplementation(platform("org.junit:junit-bom:5.11.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("it.unibo.towersiege.application.TowerSiege")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

