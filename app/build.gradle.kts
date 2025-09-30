import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.test.interview"
    buildFeatures {
        compose = true
    }
    compileSdk {
        version = release(36)
    }

    productFlavors {
        flavorDimensions += listOf("store", "environment")

        //region Store
        register("google") {
            dimension = "store"
        }
        register("amazon") {
            dimension = "store"
        }
        //endregion

        //region Environment
        register("production") {
            dimension = "environment"
        }
        register("stage") {
            dimension = "environment"
        }
        //endregion
    }

    defaultConfig {
        applicationId = "com.test.interview"
        minSdk = 24
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

        // https://developer.android.com/studio/write/java8-support
        isCoreLibraryDesugaringEnabled = true
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)

    implementation(libs.coroutines)
    implementation(libs.coroutines.android)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.backend)
    implementation(projects.database)
    implementation(projects.feature.list)
    implementation(projects.feature.pokemon.api)
    implementation(projects.feature.pokemon.impl)


    implementation(projects.feature.store)
    implementation(projects.feature.store.api)
    implementation(projects.feature.store.amazon)
    implementation(projects.feature.store.google)
}
