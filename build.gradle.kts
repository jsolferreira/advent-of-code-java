plugins {
    java
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

repositories {
    mavenCentral()
}

application {
    mainClass = "org.example.Main"
}

dependencies {
    implementation(libs.commons.cli)
}
