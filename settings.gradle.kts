pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://a8c-libs.s3.amazonaws.com/android") }
        include("org.wordpress")
        include("org.wordpress.aztec")


    }

}

rootProject.name = "Vividize - unleash yourself"
include(":app")
 