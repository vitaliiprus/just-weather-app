pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Just Weather App"

include(":app")

include(":feature:home")
include(":feature:locations")
include(":feature:weather")
include(":feature:settings")

include(":theme")

include(":domain:home")
include(":domain:locations")

include(":data:home")
include(":data:locations")

include(":local:db")

include(":core")
