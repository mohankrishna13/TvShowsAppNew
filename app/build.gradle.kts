plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.mohankrishna.tvshowsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mohankrishna.tvshowsapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"73fb153622a97cfe5bdc378a6213b39c\"")
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")

    }
    buildFeatures {
        buildConfig = true
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

    dataBinding{
        enable=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    var room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt ("androidx.room:room-compiler:$room_version")


    // optional - RxJava2 support for Room
    implementation ("androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
    implementation ("androidx.room:room-rxjava3:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation ("androidx.room:room-guava:$room_version")
    // optional - Paging 3 Integration
    implementation ("androidx.room:room-paging:$room_version")
    implementation ("androidx.room:room-ktx:2.4.1")
    implementation ("androidx.paging:paging-common-ktx:3.1.1")

    //Koin
    implementation ("io.insert-koin:koin-core:3.2.2")
    implementation( "io.insert-koin:koin-android:3.2.2")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

//Lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //Client
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.1")
    //OkHttp Interceptor
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.1")
    //Gson
    implementation ("com.google.code.gson:gson:2.9.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //paging
    implementation ("androidx.paging:paging-common-ktx:3.1.1")
    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")

    var lifecycle_version = "2.4.0"
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
}