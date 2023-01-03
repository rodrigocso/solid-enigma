plugins {
    id("enigma.java-library-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation("com.google.inject:guice")
}
