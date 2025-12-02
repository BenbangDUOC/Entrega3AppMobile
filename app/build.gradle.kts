// 1. PLUGINS
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Usando KSP, que reemplaza a 'kotlin-kapt'
    id("com.google.devtools.ksp")
}

// 2. VERSIONES PARA DEPENDENCIAS DE TERCEROS
// Define las versiones aquí para facilitar el mantenimiento
val retrofit_version = "2.9.0"
val coroutines_version = "1.7.3"
val lifecycle_version_ktx = "2.7.0"
val appcompat_version = "1.6.1"
val material_version = "1.11.0"
val recyclerview_version = "1.3.2"

android {
    namespace = "com.example.gestorproductos"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gestorproductos"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        // Mantener en 1.8 si tu proyecto usa Java 8 features.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // === DEPENDENCIAS ORIGINALES DEL PROYECTO ===
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.androidx.activity.compose.v182)
    implementation(platform(libs.androidx.compose.bom.v20251001))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)

    // Room (Persistencia de Datos: 25%) - Utiliza KSP
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler) // Utilizando KSP
    implementation(libs.androidx.room.ktx)

    // === DEPENDENCIAS AGREGADAS PARA REQUERIMIENTOS DEL PROYECTO ===

    // Retrofit y Gson (Consumo API: 20%)
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")

    // Kotlin Coroutines (Manejo de Hilos/Calidad de Código: 10%)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    // ViewModel y LiveData (Arquitectura MVVM)
    // Ya tienes un ViewModel-ktx, solo agregamos LiveData-ktx para los observables en Listado/Home
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version_ktx")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version_ktx")

    // UI Tradicional para Activities (RecyclerView: 20%)
    implementation("androidx.appcompat:appcompat:$appcompat_version")
    implementation("com.google.android.material:material:$material_version")
    implementation("androidx.recyclerview:recyclerview:$recyclerview_version")

    // === DEPENDENCIAS DE PRUEBA ===
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20251001))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}