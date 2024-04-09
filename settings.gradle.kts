pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Just Weather App"

include(":app")

include(":feature:locations")
include(":feature:weather")
include(":feature:settings")

include(":theme")

include(":domain:locations")
include(":domain:weather")
include(":domain:settings")

include(":data:locations")
include(":data:weather")

include(":local:db")
include(":remote")

include(":core:ui")
include(":core:common")
