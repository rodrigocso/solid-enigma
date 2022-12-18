plugins {
    id("enigma.java-library-conventions")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))

    implementation("com.google.inject:guice")
}
