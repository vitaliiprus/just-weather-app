package prus.justweatherapp.feature.locations.model

data class SearchLocationUiModel(
    val id: String,
    val city: String,
    val adminName: String?,
    val country: String?,
    var cityOccurrences: List<Pair<Int, Int>> = listOf(),
    var adminNameOccurrences: List<Pair<Int, Int>> = listOf(),
    var countryOccurrences: List<Pair<Int, Int>> = listOf(),
) {
    fun findOccurrences(searchQuery: String) {
        this.cityOccurrences = getOccurrences(this.city, searchQuery)
        this.adminNameOccurrences = getOccurrences(this.adminName ?: "", searchQuery)
        this.countryOccurrences = getOccurrences(this.country ?: "", searchQuery)
    }

    private fun getOccurrences(name: String, query: String): List<Pair<Int, Int>> {
        if (query.isEmpty())
            return emptyList()

        val result = mutableListOf<Pair<Int, Int>>()
        var index = -1
        do {
            if (index > -1)
                result.add(Pair(index, index + query.length - 1))
            index = name.indexOf(
                string = query,
                startIndex = (result.lastOrNull()?.second?.inc()) ?: (index + 1),
                ignoreCase = true
            )
        } while (index > -1)

        return result
    }
}