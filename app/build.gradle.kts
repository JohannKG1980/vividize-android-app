import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("io.objectbox")

}

val apiKey :String = gradleLocalProperties(rootDir).getProperty("apiKey")

android {
    namespace = "com.example.vividize_unleashyourself"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vividize_unleashyourself"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        renderscriptTargetApi = 26
        renderscriptSupportModeEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

     buildFeatures {
         viewBinding = true
         buildConfig = true
     }
    buildTypes {
        release {
            buildConfigField("String","apiKey", apiKey)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String","apiKey", apiKey)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {
    implementation("androidx.preference:preference-ktx:1.2.1")
    val roomVersion = "2.5.2"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //BlurView
    implementation ("com.github.Dimezis:BlurView:version-2.0.3")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // Coil
    implementation("io.coil-kt:coil:2.4.0")

    // Room Dependencies
//    implementation("androidx.room:room-runtime:$roomVersion")
//    implementation("androidx.room:room-ktx:$roomVersion")
//    kapt("androidx.room:room-compiler:${roomVersion}")


    //Charts

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")


    // Rich Text editor

    implementation ("org.wordpress:aztec:v1.6.2")
   // implementation ("com.github.wordpress-mobile.WordPress-Aztec-Android:aztec-toolbar:v1.6.2")


    //glide

    implementation ("com.github.bumptech.glide:glide:4.14.2")
    // Skip this if you don't want to use integration libraries or configure Glide.
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")


    //circular Progress bar

    implementation ("com.mikhaellopez:circularprogressbar:3.1.0")

    implementation ("com.google.dagger:hilt-android:2.48.1")
    kapt ("com.google.dagger:hilt-compiler:2.48.1")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.48.1")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.48.1")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.48.1")
    kaptTest ("com.google.dagger:hilt-compiler:2.48.1")


    //ObjectBox
    implementation ("io.objectbox:objectbox-android:3.7.0")
}
kapt {
    correctErrorTypes = true
}
hilt {
    enableAggregatingTask = true
}
