import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.coroutines)

    api(platform(libs.retrofit.bom))
    api(libs.retrofit)
    implementation(libs.retrofit.serialization)
}