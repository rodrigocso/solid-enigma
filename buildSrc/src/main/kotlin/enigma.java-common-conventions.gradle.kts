plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("com.google.inject:guice:5.1.0")
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
