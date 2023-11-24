package com.kos.townway.model

import vectors.Vec2

data class House(
    override val id: Int,
    override val name:String, val parkovka: Parkovka,
    override val coord: Vec2,

): GamePerson, RoadConnector {

}
