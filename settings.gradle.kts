dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        // sdk located here
        maven("https://nexus.livecom.tech/repository/maven-android/")
        // sdk uses https://github.com/muxinc/mux-stats-sdk-exoplayer for statistics
        maven("https://muxinc.jfrog.io/artifactory/default-maven-release-local")
    }
}
rootProject.name = "SDK demo app"
include(":app")
