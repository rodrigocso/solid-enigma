plugins {
    id("enigma.java-library-conventions")
}

dependencies {
    implementation(project(":domain"))

    implementation("com.google.inject:guice")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.86.Final:osx-aarch_64")
    implementation("org.reflections:reflections:0.10.2")
}
