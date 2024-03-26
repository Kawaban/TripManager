
plugins {
    alias(libs.plugins.androidApplication)
    id("com.diffplug.spotless") version "6.5.2"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
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

    buildFeatures {
        buildConfig = true
        // ...
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

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"

    // Configure which keys should be ignored by the plugin by providing regular expressions.
    // "sdk.dir" is ignored by default.
    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    implementation("com.squareup.okhttp3:okhttp:3.2.0")
    runtimeOnly("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.fragment:fragment:1.6.2")

    implementation("com.github.ISchwarz23:SortableTableView:2.8.1")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}