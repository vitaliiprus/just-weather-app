package prus.justweatherapp.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "locations")
@Parcelize
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "location_id") val locationId: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String? = "",
    @ColumnInfo(name = "admin_name") val adminName: String? = "",
    @ColumnInfo(name = "lng") val lng: Double = 0.0,
    @ColumnInfo(name = "lat") val lat: Double = 0.0
) : Parcelable {

    override fun toString(): String {
        val result = StringBuilder(city)
        val adminCountry = getAdminAndCountry()
        if (adminCountry.isNotEmpty()) {
            result.append(", ")
            result.append(adminCountry)
        }
        return result.toString()
    }

    fun getAdminAndCountry(): String {
        val result = StringBuilder()
        adminName?.let {
            result.append(it)
        }
        country?.let {
            if (result.isNotEmpty())
                result.append(", ")
            result.append(it)
        }
        return result.toString()
    }

    companion object {
        fun getMockData(): List<LocationEntity> {
            return listOf(
                LocationEntity(
                    id = "1",
                    locationId = "id_1",
                    city = "Helsinki",
                    adminName = "Uusimaa",
                    country = "Finland",
                    lat = 60.1756,
                    lng = 24.9342
                ),
                LocationEntity(
                    id = "2",
                    locationId = "id_2",
                    city = "Saint Petersburg",
                    adminName = "Sankt-Peterburg",
                    country = "Russia",
                    lat = 59.95,
                    lng = 30.3167
                ),
                LocationEntity(
                    id = "3",
                    locationId = "id_3",
                    city = "Phuket",
                    adminName = "Phuket",
                    country = "Thailand",
                    lat = 7.8881,
                    lng = 98.3975
                ),
            )
        }
    }
}