plugins {
    alias(projectLibs.plugins.androidLibrary)
    alias(projectLibs.plugins.kotlinAndroid)
}

android {
    namespace = "com.timothy.nativelib"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = androidLibs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                cppFlags("")
            }
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
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
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

    implementation(projectLibs.appcompat)

    testImplementation(projectLibs.test.junit)
    androidTestImplementation(projectLibs.test.extJunit)
    androidTestImplementation(projectLibs.test.espressoCore)
}