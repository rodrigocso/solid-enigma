plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("com.google.inject:guice:5.1.0")
        implementation("io.vertx:vertx-core:4.3.7")
    }

    implementation("com.google.guava:guava:31.1-jre")
    implementation("ch.qos.logback:logback-classic:1.4.5")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
