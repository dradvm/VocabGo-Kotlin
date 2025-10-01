plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.vocabgo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.vocabgo"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
    ndkVersion = "28.2.13676358"
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-moshi:3.0.0")
    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-runtime-ktx
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-viewmodel-compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("com.google.ar:core:1.50.0")
    // https://mvnrepository.com/artifact/androidx.appcompat/appcompat
    implementation("androidx.appcompat:appcompat:1.7.1")
    // https://mvnrepository.com/artifact/de.javagl/obj
    implementation("de.javagl:obj:0.4.0")
    implementation("androidx.credentials:credentials:1.6.0-alpha05")
    implementation("androidx.credentials:credentials-play-services-auth:1.6.0-alpha05")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation("com.google.android.gms:play-services-auth:21.4.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")
    implementation("com.airbnb.android:lottie:6.6.6")
    implementation("com.airbnb.android:lottie-compose:6.6.6")
    implementation("androidx.navigation:navigation-compose:2.9.4")
    // https://mvnrepository.com/artifact/com.google.accompanist/accompanist-navigation-animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")
    implementation("androidx.datastore:datastore-preferences-core:1.1.7")
    // https://mvnrepository.com/artifact/androidx.datastore/datastore-preferences
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    
    // https://mvnrepository.com/artifact/com.google.dagger/hilt-android
    implementation("com.google.dagger:hilt-android:2.57.1")
    // https://mvnrepository.com/artifact/androidx.hilt/hilt-navigation-compose
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-viewmodel
    runtimeOnly("androidx.lifecycle:lifecycle-viewmodel:2.9.4")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-runtime-ktx
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    // https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-viewmodel-ktx
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    kapt("com.google.dagger:hilt-compiler:2.57.1")
    kapt("com.google.dagger:hilt-android-compiler:2.57.1")
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.1")
    implementation("com.composables:core:1.43.1")
    implementation(libs.ads.mobile.sdk)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.material)
    implementation(libs.androidx.datastore.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}