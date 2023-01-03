plugins {
    id("enigma.java-library-conventions")
}

dependencies {
    implementation(project(":common"))

    implementation("commons-codec:commons-codec:1.15")
    implementation("com.google.inject:guice")
}
