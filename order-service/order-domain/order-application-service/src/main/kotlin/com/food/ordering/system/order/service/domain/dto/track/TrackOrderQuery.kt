package com.food.ordering.system.order.service.domain.dto.track

import jakarta.validation.constraints.NotNull
import java.util.*

class TrackOrderQuery (

    @NotNull
    val orderTrackingId: UUID?
)
