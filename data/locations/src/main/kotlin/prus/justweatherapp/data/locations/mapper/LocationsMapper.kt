package prus.justweatherapp.data.locations.mapper

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.local.db.entity.LocationEntity

internal fun LocationEntity.mapToDomainModel() =
    Location(
        id = this.id,
        city = this.city,
        adminName = this.adminName,
        country = this.country,
        displayName = this.city,
        lng = this.lng,
        lat = this.lat
    )