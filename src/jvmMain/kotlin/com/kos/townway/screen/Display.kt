package com.kos.townway.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.onDrag
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp
import com.kos.townway.model.*
import vectors.Vec2
import vectors.toOffset


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun Display() {
    val posX = rememberSaveable("DisplayX") { mutableStateOf(0f) }
    val posY = rememberSaveable("DisplayY") { mutableStateOf(0f) }
    val displayScale = rememberSaveable("DisplayScale") { mutableStateOf(2f) }

    val gameData = remember { mutableStateOf(createGameData()) }
    val fontResolver = LocalFontFamilyResolver.current
    val textMeasurer = rememberTextMeasurer()

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

                        DrawGrid(this, gameData, textMeasurer, posX, posY)
                        DrawRoads(this, gameData)
                        DrawCrosses(this, gameData)
                        DrawHouses(this, gameData)
                        DrawSvetoforSignal(this, gameData)

                }
            }

        })
}

fun DrawGrid(
    drawScope: DrawScope,
    gameData: MutableState<GameData>,
    textMeasurer: TextMeasurer,
    posX: MutableState<Float>,
    posY: MutableState<Float>
) {

    val gridColor: Color = R.color.gridColor
    val gridStyle: DrawStyle = Stroke(width = R.dimen.gridWidth)


    val p = Path()
    (-1000..1000 step 100).forEach { y ->
        line(p, Vec2 (-1000.0, y.toDouble()),Vec2( 1000.0, y.toDouble()), )
        drawScope.drawText(
            textMeasurer.measure(AnnotatedString("${y}"),
                style = TextStyle(fontSize =14.sp),
            ),
            topLeft = Offset(-posX.value, y.toFloat())
        )
    }
       (-1000..1000 step 100).forEach { x ->

        line(p, Vec2 (x.toDouble(),-1000.0),Vec2( x.toDouble(),1000.0), )
drawScope.drawText(
    textMeasurer.measure(AnnotatedString("${x}"),
        style = TextStyle(fontSize =14.sp),
        ),
    topLeft = Offset(x.toFloat(), -posY.value)
    )

    }
    drawScope.drawPath(p, gridColor, style = gridStyle)
}

fun DrawSvetoforSignal(drawScope: DrawScope, gameData: MutableState<GameData>) {
    val svetoforSignalColor: Color = R.color.svetoforSignal

    gameData.value.svetoforSignal.forEach { svetoforSignal ->
        drawScope.drawCircle(
            color = svetoforSignalColor,
            center = svetoforSignal.crossRoad.coord.toOffset()-Offset(0f, 0f),
            radius = 20f,
            )
    }
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
    val houseColor: Color = R.color.haus
    val parkovkaColor: Color = R.color.parkovka

    val styleHause: DrawStyle = Fill

gameData.value.houses.forEach {houses ->
    drawScope.drawCircle(parkovkaColor, houses.parkovka.radius.toFloat(),houses.coord.toOffset())
    (1..houses.parkovka.spotNumber).forEach{index->
        val a = index%(houses.parkovka.spotNumber/4)
        val b = index%4
        val d =when (b){
            0 ->Vec2(-1.0, 0.0)
            1 ->Vec2(0,-1)
            2 ->Vec2 (1,0)
            3 ->Vec2(0,1)
            else -> Vec2(0,0)
        }

        drawScope.drawRect(R.color.svetoforSignal, houses.coord.toOffset()-Offset(25f, 25f)+
                (d*a.toDouble()*houses.parkovka.radius).toOffset() , Size(6f, 6f))

    }
}
    gameData.value.houses.forEach { houses ->
        drawScope.drawRect(houseColor, houses.coord.toOffset()-Offset(25f, 25f), Size(50f, 50f))
    }


}

private fun line(p: Path, start: Vec2, end: Vec2) {
    p.moveTo(start.x.toFloat(), start.y.toFloat())
    p.lineTo(end.x.toFloat(), end.y.toFloat())
}

