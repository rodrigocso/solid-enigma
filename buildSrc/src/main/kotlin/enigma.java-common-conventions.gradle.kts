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

    implementation("com.google.guava:guava:31.1-jre")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
