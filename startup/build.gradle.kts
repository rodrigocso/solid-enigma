plugins {
    id("enigma.java-application-conventions")
}

application {
    // Define the main class for the application.
    mainClass.set("enigma.startup.Startup")
}

dependencies {
    implementation("io.vertx:vertx-core")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.86.Final:osx-aarch_64")
}