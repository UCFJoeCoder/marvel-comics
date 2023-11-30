import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.ucfjoe.marvelcomics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ucfjoe.marvelcomics"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // Tell room where to store the export the schema to
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        try {
            val keyStoreFile = project.rootProject.file("apikey.properties")
            if (keyStoreFile.exists()) {
                val properties = Properties()
                properties.load(keyStoreFile.inputStream())
                buildConfigField(
                    "String",
                    "MARVEL_API_KEY",
                    properties.getProperty("MARVEL_API_KEY") ?: ""
                )
                buildConfigField(
                    "String",
                    "MARVEL_PRIVATE_KEY",
                    properties.getProperty("MARVEL_PRIVATE_KEY") ?: ""
                )
            }
            else
            {
                throw StopExecutionException("apikey.properties file is missing from the root of the project.")
            }
        }
        catch (e:Exception) {
            throw StopExecutionException("apikey.properties file is missing from the root of the project.\r\n${e.message!!}")
        }
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Lifecycle ViewModel and Runtime dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Retrofit dependencies (Http Requests)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")

//    // Coroutines
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Coil (for async image downloading)
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Room database
    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Dagger Hilt (Dependency Injection)
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Local Unit tests
    testImplementation("com.google.truth:truth:1.1.3")
}