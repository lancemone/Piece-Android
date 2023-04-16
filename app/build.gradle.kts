plugins {
    alias(projectLibs.plugins.androidApplication)
    alias(projectLibs.plugins.kotlinAndroid)
    alias(projectLibs.plugins.ksp)
    id("therouter")
}

android {
    namespace = "com.timothy.piece"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = androidLibs.versions.applicationId.get()
        minSdk = androidLibs.versions.minSdk.get().toInt()
        targetSdk = androidLibs.versions.targetSdk.get().toInt()
        versionCode = androidLibs.versions.versionCode.get().toInt()
        versionName = androidLibs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        dataBinding {
            enable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":common"))

    ksp(projectLibs.theRouter.apt)
    implementation(projectLibs.theRouter.router)


    testImplementation(projectLibs.test.junit)
    androidTestImplementation(projectLibs.test.ext.junit)
    androidTestImplementation(projectLibs.test.espresso.core)
}