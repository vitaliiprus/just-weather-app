
JustWeatherApp
=============
**JustWeatherApp** is a fully functional Android app built entirely with a modern Android tech stack:

- Kotlin
- Jetpack Compose
- Kotlin coroutines, flows
- Hilt
- Room
- Retrofit
- Clean Architecture


# Features
**JustWeatherApp** is a simple weather app that uses public weather APIs to display current weather and weather forecasts for selected locations.

The user can add multiple locations to their watchlist by searching for locations by name.

The weather is shown for each location:
- Current weather:
    - current temperature
    - min max temperature during the day
    - weather conditions
    - probability of precipitation
    - humidity
    - wind
    - pressure
    - UV index
    - sunrise and sunset times
- Hourly forecast for the next 48 hours
- Daily forecast for the next 10 days

The user can change units of temperature, pressure and wind. The user can also change the app's language and theme.


# UI
**JustWeatherApp** follows the Material3 Design guidelines, although the main design idea of the app was inspired by [this work published on Behance](https://www.behance.net/gallery/169457873/Fairy-weather-forecast-mobile-web-progressive-app).

>**DISCLAIMER:** All rights to the original design belong to its authors. JustWeatherApp is a non-profit project created for educational purposes.

The Screens and UI elements are built entirely using [Jetpack Compose](https://developer.android.com/jetpack/compose).

The app has two themes:

- Light theme
- Dark theme

The user can choose between these themes from the settings screen or let the app follow the system theme.

## Screenshots
> TODO: add screenshots here


# Architecture
**JustWeatherApp** follows the [official architecture guidance](https://developer.android.com/topic/architecture)
and therefore uses the Clean Architecture approach and the Unidirectional Data Flow principle.

## Modularization
The app is fully modularized. The modules are divided by application layers (from top to bottom):

- `:app` (top module)
- `:feature` modules
- `:domain` modules (the core layer, have no dependencies on other layers)
- `:data` modules
- `:local` module (database)
- `:remote` (network module)
- `:theme` (compose theming module)
- `:core` modules (common code and common ui)


# Testing
JUnit4 is used to build and run tests. Unit tests cover the whole data layer. Additionally, unit tests are also used to cover the computation functions. Here I used the TDD principle, which I find suitable for programming this kind of functions.

Instrumented tests are used to test the functionality of the database.