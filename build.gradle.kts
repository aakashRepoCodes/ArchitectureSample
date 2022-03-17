// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val navVersion = "2.3.5"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath("io.insert-koin:koin-gradle-plugin:2.2.3")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}