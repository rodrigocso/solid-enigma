plugins {
    id("enigma.java-application-conventions")
}

application {
    // Define the main class for the application.
    mainClass.set("enigma.startup.Startup")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation(project(":infrastructure"))

    implementation("com.google.inject:guice")
}