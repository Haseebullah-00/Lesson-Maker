import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.0"
}
// 1. Add global configuration for expect/actual suppression
configure<KotlinMultiplatformExtension> {
    sourceSets.all {
        languageSettings {
            optIn("kotlin.experimental.ExperimentalObjCName")
            // Suppress beta warnings for expect/actual:
            compilerOptions.freeCompilerArgs.addAll("-Xexpect-actual-classes")
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export("com.mohamedrejeb.calf:calf-ui:0.7.0")
        }
    }

    sourceSets {
        // ✅ Multiplatform (shared between Android & iOS)
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.koin.compose.viewmodel.nav)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.multiplatform.settings)
            implementation(libs.kotlinx.datetime)

            // Coil multiplatform
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)

            implementation(libs.ktor.client.cio)
            implementation(libs.ismai117.kottie)
            implementation(libs.compottie)
            implementation(libs.compottie.network)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.resources)

            implementation("media.kamel:kamel-image-default:1.0.3")
            implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")

            api("com.mohamedrejeb.calf:calf-ui:0.7.0")
        }

        // ✅ Android-only
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)

            // Android UI utilities
            implementation("id.zelory:compressor:3.0.1")
            implementation("com.google.android.material:material:1.11.0")
            implementation("com.intuit.sdp:sdp-android:1.1.0")
            implementation("com.intuit.ssp:ssp-android:1.1.0")

            // Android DI & network
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)

            // Google Maps & permissions
            implementation(libs.places)
            implementation(libs.play.services.maps)
            implementation(libs.play.services.location)
            implementation(libs.maps.compose)
            implementation(libs.accompanist.permissions)

            // ✅ Lifecycle (Android only!)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }

        // ✅ iOS-only
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


android {
    namespace = "com.lessonmaker.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.lessonmaker.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding  = true
        dataBinding  = true
        buildConfig  = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

