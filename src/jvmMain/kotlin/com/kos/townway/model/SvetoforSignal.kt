package com.kos.townway.model

data class SvetoforSignal(
    override val id: Int, override val name: String,
    val idRoadFrom: Int, val idRoadTo: Int, val duration: Int, val period: Int,
    val delay: Int,
val crossRoad: CrossRoad): GamePerson
