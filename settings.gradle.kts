pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    // 配置只能在当前文件配置三方依赖仓库，否则编译异常退出
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("androidLibs"){
            version("minSdk", "24")
            version("compileSdk", "33")
            version("targetSdk", "33")
            version("applicationId", "com.timothy.piece")
            version("versionCode", "1")
            version("versionName", "1.0")
            version("targetJava", "17")
        }

        create("projectLibs"){
            from(files("gradle/project.versions.toml"))
        }
    }
}

rootProject.name = "Piece"
include(":app")
include(":common")
include(":base-framework-ktx")
include(":library:network")
include(":feature")
include(":feature:compose")
include(":feature:login")
include(":feature:ui")
include(":library:nativelib")
include(":library:irController")
include(":library:authentication")
include(":library:widget-image")
