package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.math.sqrt

@Composable
fun VirtualJoystick(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    onValueChange: (Offset) -> Unit
) {
    var dragPosition by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {},
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val maxRadius = size.toPx() / 2f
                        val newPos = dragPosition + dragAmount
                        val distance = sqrt(newPos.x * newPos.x + newPos.y * newPos.y)

                        dragPosition = if (distance > maxRadius) {
                            Offset(
                                x = (newPos.x / distance) * maxRadius,
                                y = (newPos.y / distance) * maxRadius
                            )
                        } else {
                            newPos
                        }

                        // Normalize to range [-1.0, 1.0]
                        onValueChange(
                            Offset(
                                x = dragPosition.x / maxRadius,
                                y = dragPosition.y / maxRadius
                            )
                        )
                    },
                    onDragEnd = {
                        dragPosition = Offset.Zero
                        onValueChange(Offset.Zero)
                    },
                    onDragCancel = {
                        dragPosition = Offset.Zero
                        onValueChange(Offset.Zero)
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val outerRadius = size.toPx() / 2
            val innerRadius = outerRadius * 0.4f

            // Draw outer boundaries (dark bloody ring)
            drawCircle(
                color = Color(0x33C70039),
                radius = outerRadius,
                center = center
            )
            drawCircle(
                color = Color(0x99C70039),
                radius = outerRadius,
                center = center,
                style = Stroke(width = 3.dp.toPx())
            )

            // Draw central knob
            val knobCenter = center + dragPosition
            drawCircle(
                color = Color(0xFFC70039),
                radius = innerRadius,
                center = knobCenter
            )
            drawCircle(
                color = Color(0xFFFFFFFF),
                radius = innerRadius * 0.5f,
                center = knobCenter
            )
        }
    }
}
