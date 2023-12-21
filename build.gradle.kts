buildscript {
    repositories {
        google()
        mavenCentral()
    }

    val kotlinVersion = "1.8.20"

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id("com.android.library") version "8.1.3" apply false
    id("com.google.devtools.ksp") version "1.8.20-1.0.10" apply false
}
