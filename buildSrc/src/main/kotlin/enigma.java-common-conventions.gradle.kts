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
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("ch.qos.logback:logback-classic:1.4.5")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
