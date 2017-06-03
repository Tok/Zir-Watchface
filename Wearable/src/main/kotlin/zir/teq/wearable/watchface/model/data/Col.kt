package zir.teq.wearable.watchface.model.data

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.model.item.ConfigItem

data class Col(val name: String, val darkId: Int, val lightId: Int) {
    companion object {
        val BLACK = Col("Red", R.color.black, R.color.light_grey)
        val WHITE = Col("Red", R.color.dark_grey, R.color.white)
        val RED = Col("Red", R.color.red_dark, R.color.red_light)
        val ORANGE = Col("Orange", R.color.orange_dark, R.color.orange)
        val YELLOW = Col("Yellow", R.color.yellow_dark, R.color.yellow_light)
        val GREEN = Col("Green", R.color.green_dark, R.color.green_light)
        val BLUE = Col("Blue", R.color.blue_dark, R.color.blue_light)
        val PURPLE = Col("Purple", R.color.purple_dark, R.color.purple_light)
        val PINK = Col("Pink", R.color.pink_dark, R.color.pink_light)
        val default = BLACK
        val all = listOf(BLACK, WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK)
        fun options() = all.toCollection(ArrayList<Col>())
        fun getByName(name: String): Col = all.find { it.name.equals(name) } ?: default
        fun prep(color: Int): Paint {
            val paint = inst()
            paint.color = color
            return paint
        }

        fun createPaint(ctx: Context, type: PaintType, col: Col, stroke: Stroke): Paint {
            val paint = when (type) {
                PaintType.HAND -> prepareLinePaint(ctx, col.lightId)
                PaintType.HAND_AMB -> prepareLinePaint(ctx, col.darkId)
                PaintType.HAND_OUTLINE -> prepareLinePaint(ctx, col.darkId)
                PaintType.SHAPE -> prepareShapePaint(ctx, col.lightId)
                PaintType.SHAPE_AMB -> prepareShapePaint(ctx, col.darkId)
                PaintType.SHAPE_OUTLINE -> prepareShapePaint(ctx, col.darkId)
                PaintType.CIRCLE -> prepareCirclePaint(ctx, col.darkId)
                PaintType.CIRCLE_AMB -> prepareCirclePaint(ctx, col.lightId)
                PaintType.CIRCLE_OUTLINE -> prepareCirclePaint(ctx, col.darkId)
                //PaintType.TEXT -> prepareTextPaint(ctx, R.color.text)
                PaintType.POINT -> preparePointPaint(ctx, R.color.points)
                PaintType.POINT_OUTLINE -> preparePointPaint(ctx, col.darkId)
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = createStrokeWidth(type, stroke)
            return paint
        }

        private fun createStrokeWidth(type: PaintType, stroke: Stroke): Float {
            val pointGrowth = if (PaintType.POINT.equals(type) || PaintType.POINT_OUTLINE.equals(type)) {
                13F //TODO implement selection
            } else { 0F }
            return if (PaintType.HAND_OUTLINE.equals(type) ||
                    PaintType.SHAPE_OUTLINE.equals(type) ||
                    PaintType.CIRCLE_OUTLINE.equals(type) ||
                    PaintType.POINT_OUTLINE.equals(type)) {
                val outline = 8F //TODO implement selection
                stroke.dim + pointGrowth + outline
            } else {
                stroke.dim + pointGrowth
            }
        }

        fun prepareTextPaint(ctx: Context, colorId: Int): Paint {
            val paint = inst()
            paint.typeface = ConfigItem.NORMAL_TYPEFACE
            paint.isFakeBoldText = true
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun inst(): Paint {
            val paint = Paint()
            paint.isAntiAlias = true
            return paint
        }

        private fun prepareLinePaint(ctx: Context, colorId: Int): Paint {
            val paint = inst()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun prepareOutlinePaint(ctx: Context, colorId: Int): Paint {
            val paint = inst()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun prepareShapePaint(ctx: Context, colorId: Int): Paint {
            val paint = inst()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun prepareCirclePaint(ctx: Context, colorId: Int): Paint {
            val paint = inst()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        private fun preparePointPaint(ctx: Context, colorId: Int): Paint {
            val paint = inst()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = ctx.getColor(colorId)
            return paint
        }

        fun createFilter(ctx: Context, col: Col): ColorFilter {
            return PorterDuffColorFilter(ctx.getColor(col.lightId), PorterDuff.Mode.MULTIPLY)
        }

        fun findActive(ctx: Context): Col {
            val colRes = ctx.resources.getString(R.string.saved_color)
            val colName = ConfigData.prefs(ctx).getString(colRes, Col.WHITE.name)
            return Col.getByName(colName)
        }
    }
}
