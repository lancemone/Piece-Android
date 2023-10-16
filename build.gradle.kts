// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath (projectLibs.androidx.navigationSafeArgsPlugin)
    }
}

plugins {
    alias(projectLibs.plugins.androidApplication) apply false
    alias(projectLibs.plugins.androidLibrary) apply false
    alias(projectLibs.plugins.kotlinAndroid) apply false
    alias(projectLibs.plugins.ksp) apply false
//    alias(projectLibs.plugins.therouter) apply false
    alias(projectLibs.plugins.kotlinParcelize) apply false
    alias(projectLibs.plugins.kotlinJvm) apply false
    alias(projectLibs.plugins.navigationSafeArgsPlugin) apply false
}