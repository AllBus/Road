package com.kos.townway.model

data class Vehicle(
    override val id: Int,
    override val name: String,
    val size:Int,
    val maxSpeed : Float,
) : GamePerson{
}