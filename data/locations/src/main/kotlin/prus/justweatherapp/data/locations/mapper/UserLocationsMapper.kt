package prus.justweatherapp.data.locations.mapper

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.local.db.entity.UserLocationEntity
import prus.justweatherapp.local.db.model.UserLocationDBO

internal fun UserLocationDBO.mapToDomainModel() =
    Location(
        id = this.locationId,
        city = this.city,
        adminName = this.adminName,
        displayName = this.displayName,
        orderIndex = this.orderIndex,
        country = this.country,
        lng = this.lng,
        lat = this.lat
    )

internal fun List<UserLocationDBO>.mapToDomainModels() =
    map {
        it.mapToDomainModel()
    }

internal fun Location.toDbEntity() =
    UserLocationEntity(
        locationId = this.id,
        displayName = this.displayName,
        orderIndex = this.orderIndex!!
    )
