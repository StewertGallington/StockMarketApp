package com.plcoding.stockmarketapp.presentation.company_info

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infos: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close }?.plus(1)?.roundToInt() ?: 0)
    }
    val lowerValue = remember(infos) {
        (infos.minOfOrNull { it.close }?.roundToInt() ?: 0)
    }

    val density = LocalDensity.current
    val textPaintObject = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    // Creat teh canvas
    Canvas(modifier = modifier)
    {
        // Draw the X axis.
        val spacePerHour = (size.width - spacing) / infos.size
        (0 until infos.size - 1 step 2).forEach { i ->
            val info = infos[i]
            val hour = info.date.hour

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + (i * spacePerHour),
                    size.height - 5,
                    textPaintObject
                )
            }
        }

        // Draw the Y axis.
        val priceStep = (upperValue - lowerValue) / 5f
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + (i * priceStep)).toString(),
                    30f,
                     size.height - spacing - (i * (size.height - spacing) / 5f),
                    textPaintObject
                )
            }
        }

        // Draw the path.
        var lastX = 0f;
        val strokePath = Path().apply {
            val height = size.height
            for (i in infos.indices) {
                val info = infos[i]
                val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(x1, y1, lastX   , (y1 + y2) / 2f)
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor, Color.Transparent,
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
            )
        )
    }

}

@Composable
@Preview(showBackground = true)
fun StockChartPreview() {
        val infos = listOf(
            IntradayInfo(
                close = 100f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T00:00:00")
            ),
            IntradayInfo(
                close = 200f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T01:00:00")
            ),
            IntradayInfo(
                close = 300f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T02:00:00")
            ),
            IntradayInfo(
                close = 400f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T03:00:00")
            ),
            IntradayInfo(
                close = 500f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T04:00:00")
            ),
            IntradayInfo(
                close = 600f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T05:00:00")
            ),
            IntradayInfo(
                close = 700f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T06:00:00")
            ),
            IntradayInfo(
                close = 800f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T07:00:00")
            ),
            IntradayInfo(
                close = 900f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T08:00:00")
            ),
            IntradayInfo(
                close = 1000f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T09:00:00")
            ),
            IntradayInfo(
                close = 1100f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T10:00:00")
            ),
            IntradayInfo(
                close = 1200f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T11:00:00")
            ),
            IntradayInfo(
                close = 1300f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T12:00:00")
            ),
            IntradayInfo(
                close = 1400f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T13:00:00")
            ),
            IntradayInfo(
                close = 1500f.toDouble(),
                date = LocalDateTime.parse("2021-01-01T14:00:00")
            ))

    StockChart(infos)
}