package prus.justweatherapp.data.locations.mapper

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.local.db.entity.LocationEntity

internal fun LocationEntity.mapToDomainModel() =
    Location(
        id = this.id.toString(),
        city = this.city,
        adminName = this.adminName,
        country = this.country,
        lng = this.lng,
        lat = this.lat
    )

internal fun List<LocationEntity>.mapToDomainModels() =
    map {
        it.mapToDomainModel()
    }