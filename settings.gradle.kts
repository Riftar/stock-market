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
        maven(url =  "https://jitpack.io" )
    }
}

rootProject.name = "StockMarket"
include(":app")
include(":core:common")
include(":data")
include(":domain")
include(":features:stockchart")
include(":features:searchstock")
