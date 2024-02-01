package prus.justweatherapp.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_locations")
@Parcelize
data class UserLocationEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "location_id") val locationId: String,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "order_index") val orderIndex: Int,
) : Parcelable