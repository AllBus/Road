package com.kos.townway.math

import com.kos.townway.model.GamePerson
import com.kos.townway.model.RoadConnector
import kotlin.math.hypot

object GameMath {
    fun calculateDistance(a: RoadConnector, b:RoadConnector): Float{
        return hypot(a.coord.x - b.coord.x, a.coord.y - b.coord.y).toFloat()
    }
}