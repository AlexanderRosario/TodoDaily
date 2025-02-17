plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
// Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.alexander.tododaily"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alexander.tododaily"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Icone Extended
    implementation("androidx.compose.material:material-icons-extended-android:1.7.5")



    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))

    // When using the BoM, you don't specify versions in Firebase library dependencies
    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")


    // TODO: Add the dependencies for any other Firebase products you want to use
    // See https://firebase.google.com/docs/android/setup#available-libraries
    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
    implementation("com.google.firebase:firebase-auth")
//    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-firestore-ktx")


    //HiltInjection
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    val nav_version = "2.8.4"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//    implementation(libs.hilt.android.core)
//    implementation(libs.androidx.hilt.navigation.compose)
//    ksp(libs.hilt.compiler)



    implementation(platform("androidx.compose:compose-bom:2025.01.01"))

//coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.core:core-ktx:1.15.0")

}

kapt {
    correctErrorTypes = true
}
