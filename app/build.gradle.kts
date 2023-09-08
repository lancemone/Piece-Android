import java.util.Properties
import java.io.FileInputStream

val keystorePropertiesFile: File = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

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
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
        }

        create("release") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
        }
    }
    namespace = "com.timothy.piece"
    compileSdkVersion(androidLibs.versions.compileSdk.get().toInt())

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
    }

    //        dataBinding {   // 主app也需要把dataBinding开关打开,否则会报错[java.lang.NoClassDefFoundError: Failed resolution of: Landroidx/databinding/DataBinderMapperImpl;]
//            enable = false
//        }

    viewBinding {
        enable = true
    }

    // 构建变体的维度
    flavorDimensions += "version"

    // 构建变体的维度组合
//    flavorDimensions += listOf("api", "mode")

    // 构建的变体  https://developer.android.com/studio/build/build-variants?hl=zh-cn
    productFlavors {

        create("EN"){
            dimension = "version"
        }

        create("ZH"){
            dimension = "version"
            applicationIdSuffix = ".zh"
            versionNameSuffix = "-zh"
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
    implementation(project(path = ":library:widget-image"))

//    ksp(projectLibs.theRouter.apt)
//    implementation(projectLibs.theRouter.router)
    implementation(projectLibs.splashscreen)
    implementation(projectLibs.androidx.multidex)

    testImplementation(projectLibs.test.junit)
    androidTestImplementation(projectLibs.test.extJunit)
    androidTestImplementation(projectLibs.test.espressoCore)
}