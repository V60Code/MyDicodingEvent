plugins {
    id("com.android.application") version "8.7.3"
    id("org.jetbrains.kotlin.android") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.mydicodingevent"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mydicodingevent"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    // Core libraries
    implementation(libs.androidx.core.ktx) // Ensure defined in libs.versions.toml
    implementation(libs.androidx.appcompat) // Ensure defined in libs.versions.toml
    implementation(libs.material) // Ensure defined in libs.versions.toml
    implementation(libs.androidx.constraintlayout) // Ensure defined in libs.versions.toml
    implementation(libs.androidx.fragment.ktx) // Ensure defined in libs.versions.toml
    implementation(libs.androidx.datastore.preferences) // Ensure defined in libs.versions.toml

    // Jetpack Lifecycle
    implementation(libs.lifecycle.viewmodel.ktx) // Ensure defined in libs.versions.toml
    implementation(libs.lifecycle.livedata.ktx) // Ensure defined in libs.versions.toml
    implementation(libs.androidx.lifecycle.runtime.ktx) // Ensure defined in libs.versions.toml

    // Room dependencies with KSP
    implementation(libs.room.runtime) // Ensure defined in libs.versions.toml
    ksp(libs.room.compiler) // Room compiler with KSP
    implementation(libs.room.ktx) // Ensure defined in libs.versions.toml

    // Glide for image loading (Remove `ksp` if not using KSP-based Glide extension)
    implementation(libs.glide) // Ensure defined in libs.versions.toml
    ksp(libs.ksp)

    // Retrofit and Gson converter
    implementation(libs.retrofit) // Ensure defined in libs.versions.toml
    implementation(libs.converter.gson) // Ensure defined in libs.versions.toml

    // Testing libraries
    testImplementation(libs.junit) // Ensure defined in libs.versions.toml
    androidTestImplementation(libs.androidx.junit) // Ensure defined in libs.versions.toml
    androidTestImplementation(libs.espresso.core) // Ensure defined in libs.versions.toml
}

