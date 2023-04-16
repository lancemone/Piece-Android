// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
    }
}

plugins {
    alias(projectLibs.plugins.androidApplication) apply false
    alias(projectLibs.plugins.androidLibrary) apply false
    alias(projectLibs.plugins.kotlinAndroid) apply false
    alias(projectLibs.plugins.ksp) apply false
    alias(projectLibs.plugins.therouter) apply false
}