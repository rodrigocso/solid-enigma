plugins {
    id("enigma.java-library-conventions")
}

dependencies {
    implementation(project(":common"))

    implementation("com.google.inject:guice")
    implementation("io.vertx:vertx-core")
    implementation("commons-codec:commons-codec:1.15")
}
