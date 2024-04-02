package prus.justweatherapp.remote.openmeteo.api

import prus.justweatherapp.remote.openmeteo.model.OpenMeteoResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {

    @GET(
        "forecast" +
                "?hourly=temperature_2m,relative_humidity_2m,apparent_temperature," +
                "precipitation_probability,rain,showers,snowfall,weather_code,surface_pressure," +
                "cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,uv_index" +
                "&daily=sunrise,sunset,daylight_duration,sunshine_duration&wind_speed_unit=ms" +
                "&timeformat=unixtime&timezone=auto&forecast_days=14&models=best_match"
    )
    suspend fun getForecastWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
    ): Result<OpenMeteoResponseDTO>
}