package prus.justweatherapp.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "locations")
@Parcelize
@Serializable
data class LocationEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "location_id")
    val locationId: String = id.toString(),

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String? = "",

    @ColumnInfo(name = "admin_name")
    @SerialName("admin_name")
    val adminName: String? = "",

    @ColumnInfo(name = "lng")
    val lng: Double = 0.0,

    @ColumnInfo(name = "lat")
    val lat: Double = 0.0

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

    private fun getAdminAndCountry(): String {
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
}