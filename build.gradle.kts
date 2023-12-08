plugins {
    java
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

application {
    mainClass = "org.example.Main"
}

dependencies {
    implementation("commons-cli:commons-cli:1.6.0")
}
