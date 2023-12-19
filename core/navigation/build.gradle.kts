plugins {
    id("getstream.android.library")
    id("getstream.android.library.compose")
    id("getstream.android.hilt")
    id("getstream.spotless")
}

android {
    namespace = "com.example.willog_unsplash.navigation"
}

dependencies {
    implementation(project(":core:model"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")

    implementation("androidx.navigation:navigation-compose:2.5.3")
}
