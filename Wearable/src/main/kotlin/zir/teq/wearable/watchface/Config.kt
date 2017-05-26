package zir.watchface

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import zir.teq.wearable.watchface.Col
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Stroke
import zir.teq.wearable.watchface.Theme
import java.util.concurrent.TimeUnit

data class Config(val drawCircle: Boolean,
                  val drawActiveCircles: Boolean, val drawHands: Boolean, val drawTriangle: Boolean,
                  val drawText: Boolean, val drawPoints: Boolean) {
    enum class PaintType {
        TEXT, HAND, HAND_AMB, SHAPE, SHAPE_AMB, CIRCLE, CIRCLE_AMB, POINT
    }

    val isStayActive = false //TODO reimplement
    val isFastUpdate = false //TODO reimplement
    val yOffset: Float = 98F
    var xOffset: Float = 0.toFloat()

    companion object {
        val PHI = 1.618033988F
        val TAG = "Config"

        val FAST_UPDATE_RATE_MS = 20L
        val NORMAL_UPDATE_RATE_MS = 1000L
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        fun activeUpdateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS

        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)

        val WHITE = Col("White", R.color.white_dark, R.color.white_light)
        val RED = Col("Red", R.color.red_dark, R.color.red_light)
        val ORANGE = Col("Orange", R.color.orange_dark, R.color.orange)
        val YELLOW = Col("Yellow", R.color.yellow_dark, R.color.yellow_light)
        val GREEN = Col("Green", R.color.green_dark, R.color.green_light)
        val BLUE = Col("Blue", R.color.blue_dark, R.color.blue_light)
        val PURPLE = Col("Purple", R.color.purple_dark, R.color.purple_light)
        val PINK = Col("Pink", R.color.pink_dark, R.color.pink_light)
        val defaultColor = WHITE
        val ALL_COLORS = listOf(WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK)
        fun getColorOptions() = ALL_COLORS.toCollection(ArrayList())
        fun getColorByName(name: String): Col = ALL_COLORS.find { it.name.equals(name) } ?: defaultColor
        fun prep(color: Int): Paint {
            val paint = prep()
            paint.color = color
            return paint
        }

        private fun prep(): Paint {
            val paint = Paint()
            paint.isAntiAlias = true
            return paint
        }

        private fun prepareLinePaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun prepareShapePaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun prepareCirclePaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun createPaint(ctx: Context, type: PaintType, col: Col, stroke: Stroke): Paint {
            val paint = when (type) {
                PaintType.HAND -> prepareLinePaint(ctx, col.lightId)
                PaintType.HAND_AMB -> prepareLinePaint(ctx, col.darkId)
                PaintType.SHAPE -> prepareShapePaint(ctx, col.lightId)
                PaintType.SHAPE_AMB -> prepareShapePaint(ctx, col.darkId)
                PaintType.CIRCLE -> prepareCirclePaint(ctx, col.darkId)
                PaintType.CIRCLE_AMB -> prepareCirclePaint(ctx, col.lightId)
                PaintType.TEXT -> prepareTextPaint(ctx, R.color.text)
                PaintType.POINT -> preparePointPaint(ctx, R.color.points)
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = stroke.dim
            return paint
        }

        fun prepareTextPaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.typeface = NORMAL_TYPEFACE
            paint.isFakeBoldText = true
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun preparePointPaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }
    }
}
