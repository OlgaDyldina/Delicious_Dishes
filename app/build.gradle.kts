plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-parcelize")
    id ("org.jetbrains.kotlin.kapt")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.delicious_dishes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.delicious_dishes"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.engage.core)
    implementation(libs.litert.support.api)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.espresso.core)
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("com.google.dagger:dagger:2.34")
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation ("org.hamcrest:hamcrest-library:1.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("androidx.activity:activity-ktx:1.4.0")
    implementation ("androidx.fragment:fragment-ktx:1.4.1")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    kapt(libs.androidx.room.compiler)
    implementation ("io.reactivex.rxjava3:rxkotlin:3.0.1")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.google.firebase:firebase-config:20.0.4")
    kapt ("com.google.dagger:dagger-compiler:2.34")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    implementation ("androidx.compose.ui:ui:1.0.5")
    implementation ("androidx.compose.material:material:1.0.5")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.0.5")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.0.5")
    implementation ("androidx.compose.material3:material3:1.5.0-alpha13")
    implementation ("androidx.compose.runtime:runtime")
}