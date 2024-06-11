plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.gms.google-services")
    alias(libs.plugins.daggerHiltPlugin)

}

android {
    namespace = "kg.geekspro.android_lotos"
    compileSdk = 34

    defaultConfig {
        applicationId = "kg.geekspro.android_lotos"
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.ktx)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Gson
    implementation (libs.gson)
    //Splash Screen
    implementation(libs.splashScreen)
    // val nav_version = "2.7.7"
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Recyclerview
    implementation (libs.androidx.recyclerview)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // Retrofit
    implementation (libs.retrofit)
    // Glide
    implementation (libs.glide)
    // circle Image
    implementation(libs.circelImage)
    //circle indicator
    implementation(libs.circelIndicator)
    //room
    implementation(libs.room)
    kapt(libs.roomCompiler)
    //Google API Client
    implementation (libs.googleApiClient)
    //implementation(libs.glide)
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-analytics")
    // view pager2
    implementation(libs.androidx.viewpager2)
    // material - material
    implementation (libs.material.vversion)
    implementation (libs.googleMaterial)

    implementation(libs.daggerHiltImpl)
    kapt(libs.daggerHiltKapt)
    implementation(libs.okHttpClient)
    implementation(libs.loggingInterceptor)
    //Coroutines
    implementation(libs.coroutines)
    //Paging
    implementation (libs.androidx.paging.common.ktx)
    implementation (libs.androidx.paging.runtime.ktx)

}
