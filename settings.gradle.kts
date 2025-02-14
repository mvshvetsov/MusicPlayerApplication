pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MusicPlayerApplication"
include(":app")
include(":common")
include(":local_music_feature")
include(":local_music_feature:data")
include(":local_music_feature:domain")
include(":local_music_feature:presentation")
include(":remote_music_feature")
include(":remote_music_feature:data")
include(":remote_music_feature:domain")
include(":remote_music_feature:presentation")
include(":music_player_feature")
include(":track_list_common")
include(":network")
include(":music_player_feature:data")
include(":music_player_feature:domain")
include(":music_player_feature:presentation")
