package com.kos.townway.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import com.kos.townway.math.GameMath.calculateDistance
import com.kos.townway.model.CrossRoad
import com.kos.townway.model.GameData
import com.kos.townway.model.House
import com.kos.townway.model.Road
import vectors.Vec2
import vectors.toOffset


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun Display() {
    val posX = rememberSaveable("DisplayX") { mutableStateOf(0f) }
    val posY = rememberSaveable("DisplayY") { mutableStateOf(0f) }
    val displayScale = rememberSaveable("DisplayScale") { mutableStateOf(1f) }

    val gameData = remember { mutableStateOf(createGameData()) }

    Canvas(modifier = Modifier.fillMaxSize().clipToBounds().onDrag(
        onDrag = { offset ->
            posX.value = posX.value + offset.x
            posY.value = posY.value + offset.y
        }
    ),
        onDraw = {
            val c = size / 2f

            this.translate(posX.value, posY.value) {
                this.scale(scale = displayScale.value) {
                    this.translate(c.width + posX.value, c.height + posY.value) {

                        DrawRoads(this, gameData)
                        DrawCrosses(this, gameData)
                        DrawHouses(this, gameData)
                    }
                }
            }

        })
}

fun createGameData(): GameData {

    val game = GameData()

    val h1 = House(1, "1", Vec2(10.0, 20.0))
    val h2 = House(2, "2", Vec2(100.0, 200.0))
    val h3 = House(3, "3", Vec2(200.0, 300.0))
    val h4 = House(4, "4", Vec2(300.0, 400.0))

    val c1 = CrossRoad(100, "1", Vec2(280.0, 30.0))

    val r12 = Road(12, "12",h1 , h2, calculateDistance(h1,h2))
    val r13 = Road(13, "13",h1 , h3, calculateDistance(h1,h3))
    val r14 = Road(14, "14",h1 , h4, calculateDistance(h1,h4))

    val r101 = Road(101, "101",c1 , h1, calculateDistance(c1,h1))
    val r103 = Road(103, "103",c1 , h3, calculateDistance(c1,h3))
    val r104 = Road(104, "104",c1 , h4, calculateDistance(c1,h4))

    game.houses = listOf(
        h1,
        h2,
        h3,
        h4,
    )

    game.roads = listOf(
        r12,
        r13,
        r14,
        r101,
        r103,
        r104,
    )

    return game
}

fun DrawRoads(drawScope: DrawScope, gameData: MutableState<GameData>) {
    val roadColor: Color = R.color.road
    val roadStyle: DrawStyle = Stroke(width = R.dimen.roadWidth)

    val p = Path()
    gameData.value.roads.forEach { road ->
        line(p, road.begin.coord, road.end.coord)
    }

    drawScope.drawPath(p, roadColor, style = roadStyle)
}

fun DrawCrosses(drawScope: DrawScope, gameData: MutableState<GameData>) {
    val roadColor: Color = R.color.road
    val roadStyle: DrawStyle = Fill

    gameData.value.crosses.forEach { cross ->
        drawScope.drawCircle(color = roadColor, style = roadStyle, radius = 20f, center = cross.coord.toOffset())
    }
}

fun DrawHouses(drawScope: DrawScope, gameData: MutableState<GameData>){
    val hauseColor: Color = R.color.haus
    val styleHause: DrawStyle = Fill

    gameData.value.houses.forEach { houses ->
        drawScope.drawRect(hauseColor, houses.coord.toOffset()-Offset(25f, 25f), Size(50f, 50f))
    }
}

private fun line(p: Path, start: Vec2, end: Vec2) {
    p.moveTo(start.x.toFloat(), start.y.toFloat())
    p.lineTo(end.x.toFloat(), end.y.toFloat())
}

