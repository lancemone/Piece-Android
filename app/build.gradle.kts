plugins {
    alias(projectLibs.plugins.androidApplication)
    alias(projectLibs.plugins.kotlinAndroid)
    alias(projectLibs.plugins.ksp)
    alias(projectLibs.plugins.kotlinParcelize)
//    id ("therouter")
    alias(projectLibs.plugins.navigationSafeArgsPlugin)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("piece_release.keystore")
            storePassword = "URHwhw@tea0vzt5pvp"
            keyPassword = "URHwhw@tea0vzt5pvp"
            keyAlias = "key_sounding"
        }

        create("release") {
            storePassword = "URHwhw@tea0vzt5pvp"
            keyPassword = "URHwhw@tea0vzt5pvp"
            storeFile = file("/Users/tao/code/Android/Piece-Android/app/piece_release.keystore")
            keyAlias = "key_sounding"
        }
    }
    namespace = "com.timothy.piece"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = androidLibs.versions.applicationId.get()
        minSdk = androidLibs.versions.minSdk.get().toInt()
        targetSdk = androidLibs.versions.targetSdk.get().toInt()
        versionCode = androidLibs.versions.versionCode.get().toInt()
        versionName = androidLibs.versions.versionName.get()

        resourceConfigurations.addAll(listOf("en", "zh"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            isShrinkResources = false
        }

        dataBinding {   // 主app也需要把dataBinding开关打开,否则会报错[java.lang.NoClassDefFoundError: Failed resolution of: Landroidx/databinding/DataBinderMapperImpl;]
            enable = true
        }

        viewBinding {
            enable = true
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

    implementation(project(path = ":common"))
    implementation(project(path = ":feature:ui"))
    implementation(project(path = ":library:nativelib"))
    implementation(project(path = ":library:authentication"))

//    ksp(projectLibs.theRouter.apt)
//    implementation(projectLibs.theRouter.router)
    implementation(projectLibs.splashscreen)
    implementation(projectLibs.androidx.multidex)

    testImplementation(projectLibs.test.junit)
    androidTestImplementation(projectLibs.test.extJunit)
    androidTestImplementation(projectLibs.test.espressoCore)
}