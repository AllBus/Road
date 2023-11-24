package com.kos.townway.screen

import com.kos.townway.math.GameMath
import com.kos.townway.model.*
import vectors.Vec2

fun createGameData(): GameData {

    val game = GameData()

    val h1  =  House(1, "1", Parkovka (radius = 50.0, spotNumber = 12), Vec2(-300.0, 0.0 ))
    val h2  =  House(2, "2", Parkovka (radius = 50.0, spotNumber = 12), Vec2(-300.0, 100.0))
    val h3  =  House(3, "3", Parkovka (radius = 50.0, spotNumber = 12), Vec2(-300.0, 200.0))
    val h4  =  House(4, "4", Parkovka (radius = 50.0, spotNumber = 12), Vec2(-300.0, 300.0))
    val h5  =  House (5, "5", Parkovka (radius = 50.0, spotNumber = 12), Vec2(0.0, -300.0))
    val h6  =  House (6, "6", Parkovka (radius = 50.0, spotNumber = 12), Vec2(0.0, 300.0))
    val h7  =  House (7, "7", Parkovka (radius = 50.0, spotNumber = 12), Vec2(300.0, 0.0))
    val h8  =  House (8, "8", Parkovka (radius = 50.0, spotNumber = 12), Vec2(300.0, -300.0))
    val h9  =  House (9, "9", Parkovka (radius = 50.0, spotNumber = 12), Vec2(300.0, 300.0))
    val h10  = House (10, "10", Parkovka (radius = 50.0, spotNumber = 12), Vec2(-300.0, -300.0))

    val c1 = CrossRoad(100, "1", Vec2(0.0, 0.0))

    val r12 = Road(12, "12", h1, h2, GameMath.calculateDistance(h1, h2))
    val r13 = Road(13, "13", h1, h3, GameMath.calculateDistance(h1, h3))
    val r14 = Road(14, "14", h1, h4, GameMath.calculateDistance(h1, h4))

    val r101 = Road(101, "101", h1, c1, GameMath.calculateDistance(c1, h1))
    val r103 = Road(103, "103", c1, h3, GameMath.calculateDistance(c1, h3))
    val r104 = Road(104, "104", c1, h4, GameMath.calculateDistance(c1, h4))
    val r105 = Road(105, "105", c1, h5, GameMath.calculateDistance(c1, h5))
    val r106 = Road(106, "106", c1, h6, GameMath.calculateDistance(c1, h6))
    val r107 = Road(107, "107", c1, h7, GameMath.calculateDistance(c1, h7))
val r108 = createRoad(begin = h7, end = h4)
    val svetoforSignal = SvetoforSignal(
        2,
        "", 101,
        idRoadTo = 103,
        duration = 30,
        period = 60,
        delay = 0,
        crossRoad = c1
    )
    game.houses = listOf(
        h1,
        h2,
        h3,
        h4,
        h5,
        h6,
        h7,
        h8,
        h9,
        h10
    )
    game.svetoforSignal = listOf(svetoforSignal)

    game.roads = listOf(
        r12,
        r13,
        r14,
        r101,
        r105,
        r106,
        r107,
        r108
    )

    return game
}
fun createRoad (begin: RoadConnector, end: RoadConnector):Road {
    return Road(1000000*begin.id+end.id, "${1000000*begin.id+end.id}",
        begin, end, GameMath.calculateDistance(begin, end))

}