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

    api(projectLibs.ktx.core)
    api(projectLibs.appcompat)
    api(projectLibs.material)
    api(projectLibs.recycleview)
    api(projectLibs.constraintlayout)
    api(projectLibs.ktx.lifecycle)
    api(projectLibs.ktx.activity)
    api(projectLibs.ktx.fragment)
    api(projectLibs.ktx.coroutines)
    api(projectLibs.ktx.coroutinesAndroid)
}