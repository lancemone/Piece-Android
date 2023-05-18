plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projectLibs.gson)
    implementation(projectLibs.okhttp3)
    implementation(projectLibs.okhttp3Logging)
    implementation(projectLibs.retrofit2)
    implementation(projectLibs.retrofit2Gson)
    implementation(projectLibs.ktx.coroutines)
    implementation(projectLibs.ktx.coroutinesAndroid)
    implementation(projectLibs.retrofit2RxJava2)
    implementation(projectLibs.retrofit2RxJava3)
}