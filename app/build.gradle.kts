plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "com.kaninitech.salesnote"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kaninitech.salesnote"
        minSdk = 26
        targetSdk = 36
        versionCode = 5
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Enables code shrinking, obfuscation, and optimization
            isMinifyEnabled = false
//            isShrinkResources = true  //  use this in Kotlin DSL
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
    kotlinOptions {
        jvmTarget = "17"
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
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)



    // Koin dependencies for dependency injection
    // Koin Core (required)
    implementation("io.insert-koin:koin-android:4.1.1")

    // Koin for Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:4.1.1")

    // Koin for Navigation in Jetpack Compose
    implementation("io.insert-koin:koin-androidx-navigation:3.5.0")

    // Lifecycle (to work with ViewModel)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Room dependencies
    implementation("androidx.room:room-runtime:2.8.1")
    ksp("androidx.room:room-compiler:2.8.1")

    // For coroutine support (if you're using suspend functions in DAO)
    implementation("androidx.room:room-ktx:2.8.1")


    //animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")

    //pager APi
    implementation("androidx.compose.foundation:foundation:1.7.8") // Use latest version

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

    //font Awesome Icons
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.1")
//    implementation("org.webjars:font-awesome:7.0.1")
    // Accompanist Pager (for horizontal paging) paging
    implementation("com.google.accompanist:accompanist-pager:0.25.0")

    // Accompanist Pager Indicators (for page indicators like dots)
    implementation("com.google.accompanist:accompanist-pager-indicators:0.25.0")

    // DataStore for storing preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coroutine Support for Retrofit (if using suspend functions)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")





}