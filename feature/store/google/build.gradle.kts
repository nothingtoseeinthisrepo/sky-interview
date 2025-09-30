import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.test.interview.feature.store.google"
    compileSdk {
        version = release(36)
    }

    productFlavors {
        flavorDimensions += listOf("environment")

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
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

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

    implementation(projects.feature.store.api)
}