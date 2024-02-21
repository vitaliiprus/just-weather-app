package prus.justweatherapp.local.db.model

import androidx.room.ColumnInfo

data class UserLocationDbModel(
    @ColumnInfo("location_id")
    val locationId: String,

    @ColumnInfo("city")
    val city: String,

    @ColumnInfo("admin_name")
    val adminName: String,

    @ColumnInfo("country")
    val country: String,

    @ColumnInfo("display_name")
    val displayName: String = city,

    @ColumnInfo("order_index")
    val orderIndex: Int,

    @ColumnInfo("lng")
    val lng: Double,

    @ColumnInfo("lat")
    val lat: Double,
)