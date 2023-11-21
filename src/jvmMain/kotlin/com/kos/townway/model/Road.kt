package com.kos.townway.model

import vectors.Vec2

data class Road(
    override val id: Int,
    override val name: String,
    val begin: RoadConnector,
    val end: RoadConnector,
    val length:Float,
    ): GamePerson {
}