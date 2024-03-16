
plugins {
    alias(libs.plugins.androidApplication)
    id("com.diffplug.spotless") version "6.5.2"

}


android {
    namespace = "com.example.tripmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tripmanager"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
spotless {
    java {
        target("**/*.java")
        importOrder()
        removeUnusedImports()
        palantirJavaFormat()
    }
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation("com.squareup.okhttp3:okhttp:3.2.0")
    runtimeOnly("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.github.evrencoskun:TableView:v0.8.9.4")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}