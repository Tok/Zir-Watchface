package zir.teq.wearable.watchface.model.data

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.Config

data class Col(val name: String, val darkId: Int, val lightId: Int) {
    companion object {
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

        fun prep(): Paint {
            val paint = Paint()
            paint.isAntiAlias = true
            return paint
        }

        fun prepareLinePaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun prepareShapePaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun prepareCirclePaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun prepareTextPaint(ctx: Context, colorId: Int): Paint {
            val paint = prep()
            paint.typeface = Config.NORMAL_TYPEFACE
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

        fun createFilter(ctx: Context, col: Col): ColorFilter {
            return PorterDuffColorFilter(ctx.getColor(col.lightId), PorterDuff.Mode.MULTIPLY)
        }

        fun findActive(ctx: Context): Col {
            val shared = ctx.getSharedPreferences(
                    ctx.getString(R.string.zir_watch_preference_file_key),
                    Context.MODE_PRIVATE)
            val colRes = ctx.resources.getString(R.string.saved_color)
            val colName = shared.getString(colRes, Col.WHITE.name)
            return Col.getColorByName(colName)
        }
    }
}
