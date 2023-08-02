plugins {
    alias(projectLibs.plugins.androidLibrary)
    alias(projectLibs.plugins.kotlinAndroid)
    alias(projectLibs.plugins.ksp)
    alias(projectLibs.plugins.kotlinParcelize)
}

android {
    namespace = "com.timothy.common"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = androidLibs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        resourceConfigurations += setOf()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }
    }

    viewBinding {
        enable = true
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

    api(project(":base-framework-ktx"))
    api(project(":library:network"))
    api(projectLibs.coil)
    api(projectLibs.gson)
//    ksp(projectLibs.theRouter.apt)
//    api(projectLibs.theRouter.router)
//    api(projectLibs.androidx.startup)
    implementation(projectLibs.retrofit2)
    implementation(projectLibs.okhttp3Logging)

    compileOnly(projectLibs.androidx.navigationSafeArgsGenerator)
    api(projectLibs.androidx.navigationUI)
    api(projectLibs.androidx.navigationFragment)
    api(projectLibs.androidx.navigationDynamicFragment)
    api(projectLibs.androidx.navigationDynamicRuntime)
}