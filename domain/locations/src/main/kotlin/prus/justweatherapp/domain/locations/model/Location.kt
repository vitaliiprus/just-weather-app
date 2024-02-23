package prus.justweatherapp.domain.locations.model

data class Location(
    val id: String,
    val city: String,
    val adminName: String? = null,
    val country: String? = null,
    val displayName: String = city,
    var orderIndex: Int? = null,
    val lng: Double,
    val lat: Double,
) {
    fun isDisplayNameChanged(): Boolean = this.displayName != this.city
}
