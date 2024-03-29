plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.test.cars"
        minSdk = 21
        targetSdk = 31
        versionCode  =1
        versionName = "1.0"

        testInstrumentationRunner =  "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName ("release") {
            isMinifyEnabled =  false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility  = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding {
            isEnabled = true
        }
    }
}

dependencies {

    val koinVersion = "2.2.3"
    val navVersion = "2.3.5"
    val roomVersion = "2.4.0"
    val mockkVersion = "1.10.0"

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation( "androidx.test.ext:junit-ktx:1.1.3")

    testImplementation( "junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.1.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    // Navigation Components
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    kapt( "com.github.bumptech.glide:compiler:4.12.0")

    // Koin
    implementation ("androidx.activity:activity-ktx:1.4.0")
    implementation ("io.insert-koin:koin-android:$koinVersion")
    implementation ("io.insert-koin:koin-android-ext:$koinVersion")
    implementation( "io.insert-koin:koin-core:$koinVersion")
    implementation ("io.insert-koin:koin-core-ext:$koinVersion")

    // Koin AndroidX Scope features
    implementation ("io.insert-koin:koin-androidx-scope:$koinVersion")
    // Koin AndroidX ViewModel features
    implementation ("io.insert-koin:koin-androidx-viewmodel:$koinVersion")

    //RxJava
    implementation( "io.reactivex.rxjava2:rxjava:2.2.12")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    //Paging
    implementation ("androidx.paging:paging-runtime:3.1.0")

    //Room
    implementation( "androidx.room:room-ktx:$roomVersion")
    implementation( "androidx.room:room-rxjava2:$roomVersion")
    kapt( "androidx.room:room-compiler:$roomVersion")

    //Testing
    testImplementation ("io.mockk:mockk:$mockkVersion")
    testImplementation( "org.jetbrains.kotlin:kotlin-test-junit:1.5.21")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("app.cash.turbine:turbine:0.7.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")

}