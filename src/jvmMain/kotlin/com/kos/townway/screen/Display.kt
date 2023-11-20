package com.kos.townway.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import vectors.Vec2


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun Display() {
    val posX = rememberSaveable("DisplayX") { mutableStateOf(0f) }
    val posY = rememberSaveable("DisplayY") { mutableStateOf(0f) }
    val displayScale = rememberSaveable("DisplayScale") { mutableStateOf(1f) }

    Canvas(modifier = Modifier.fillMaxSize().clipToBounds().onDrag(
        onDrag = { offset ->
            posX.value = posX.value + offset.x
            posY.value = posY.value + offset.y
        }
    ),
        onDraw = {
            val c = size / 2f

            val hauseColor: Color = Color.Yellow
            val roadColor: Color = Color.Gray
            val roadStyle: DrawStyle = Stroke(width = 1.0f)
            val styleHause: DrawStyle = Fill


            this.translate(posX.value, posY.value) {
                this.scale(scale = displayScale.value) {
                    this.translate(c.width + posX.value, c.height + posY.value) {
                        val p = Path()
                        line(p, Vec2(60.0, 70.0), Vec2(100.0, 200.0))
                        drawPath(p, roadColor, style = roadStyle)

                        drawRect(hauseColor, Offset(10f, 20f), Size(50f, 50f))

                        drawRect(hauseColor, Offset(100f, 200f), Size(50f, 50f))
                    }
                }
            }

        })
}

private fun line(p: Path, start: Vec2, end: Vec2) {
    p.moveTo(start.x.toFloat(), start.y.toFloat())
    p.lineTo(end.x.toFloat(), end.y.toFloat())
}