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

rootProject.name = "Android-JetpackCompose-MultiApp-Modular"
include(":app")
include(":cartaalta")
include(":discosfavoritos")
include(":discosfavoritos2")
include(":listacomprapersistente")
include(":listacompraexamenbd")
include(":triviaapproom")
include(":baseexamenjunio")
include(":introcompose")
include(":listas")
include(":modificadores")
include(":navigation")
include(":splash")
include(":state")
include(":listacompracompose")
