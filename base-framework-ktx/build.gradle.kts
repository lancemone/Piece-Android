plugins {
    alias(projectLibs.plugins.androidLibrary)
    alias(projectLibs.plugins.kotlinAndroid)
}

android {
    namespace = "com.timothy.framework"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = androidLibs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = androidLibs.versions.targetJava.get()
    }
}

dependencies {

    api(projectLibs.bundles.androidCore)
    api(projectLibs.bundles.ktxAndroid)
    api(projectLibs.datastorePreferences)
    api(projectLibs.androidx.window)
}