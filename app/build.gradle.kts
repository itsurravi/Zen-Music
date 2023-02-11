import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.protobuf") version "0.8.19"
}

android {
    namespace = "com.ravisharma.zen_music"
    compileSdk = Api.compileSdk

    defaultConfig {
        applicationId = "com.ravisharma.zen_music"
        minSdk = Api.minSdk
        targetSdk = Api.targetSdk
        versionCode = AppVersion.Code
        versionName = AppVersion.Name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        kapt {
            arguments {
                arg("room.schemaLocation","$projectDir/schemas")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        viewBinding = true
        dataBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.androidxComposeCompiler
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(Libraries.androidxCore)

    implementation(Libraries.androidxLifecycle)
    implementation(Libraries.androidxLifecycleRuntimeCompose)

    implementation(Libraries.androidxActivityCompose)

    implementation(Libraries.androidxComposeUi)
    implementation(Libraries.androidxComposeUiToolingPreview)
    debugImplementation(Libraries.androidxComposeUiTooling)
    debugImplementation(Libraries.androidxComposeUiTestManifest)
    androidTestImplementation(Libraries.androidxComposeUiTestJunit4)
    implementation(Libraries.androidxComposeConstraintLayout)
    implementation(Libraries.androidxSplashScreen)

    implementation(Libraries.androidxComposeMaterial)
    implementation(Libraries.material3)
    implementation(Libraries.material3WindowSizeClass)

    implementation(Libraries.accompanistSystemUiController)
    implementation(Libraries.accompanistPermissions)
    implementation(Libraries.accompanistPager)

    implementation(Libraries.appCompat)
    implementation(Libraries.navigationUi)
    implementation(Libraries.navigationFragment)

    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.androidxJunit)
    androidTestImplementation(Libraries.androidxEspresso)

    debugImplementation(Libraries.leakcanary)

    implementation(Libraries.hilt)
    kapt(AnnotationProcessors.hiltCompiler)

    implementation(Libraries.timber)

    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    kapt(AnnotationProcessors.roomCompiler)
    implementation(Libraries.datastore)
    implementation(Libraries.kotlinLite)

    implementation(Libraries.exoPlayer)
    implementation(Libraries.media3ExoPlayer)
    implementation(Libraries.media3Transformer)
    implementation(Libraries.exoPlayerUi)

    implementation(Libraries.coilCompose)
    implementation(Libraries.lottie)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.kotlinLite}"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}