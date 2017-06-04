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

data class Palette(val name: String, val darkId: Int, val id: Int, val lightId: Int) {
    companion object {
        val BLACK = Palette("Black", R.color.black, R.color.gray, R.color.dark_gray)
        val WHITE = Palette("White", R.color.dark_grey, R.color.light_gray, R.color.white)
        val RED = Palette("Red", R.color.dark_red, R.color.fire_brick, R.color.red)
        val ORANGE = Palette("Orange", R.color.orange_red, R.color.dark_orange, R.color.orange)
        val YELLOW = Palette("Yellow", R.color.yellow_dark, R.color.gold, R.color.yellow)
        val GREEN = Palette("Green", R.color.dark_green, R.color.lime_green, R.color.green_yellow)
        val BLUE = Palette("Blue", R.color.dark_blue, R.color.blue, R.color.deep_sky_blue)
        val PURPLE = Palette("Purple", R.color.indigo, R.color.dark_violet, R.color.magenta)
        val default = BLACK
        val all = listOf(BLACK, WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE)
        fun options() = all.toCollection(ArrayList<Palette>())
        fun getByName(name: String): Palette = all.find { it.name.equals(name) } ?: default
        fun prep(color: Int): Paint {
            val paint = inst()
            paint.color = color
            return paint
        }

        fun createPaint(ctx: Context, type: PaintType, theme: Theme, stroke: Stroke): Paint  {
            return createPaint(ctx, type, theme, stroke, Palette.default)
        }
        fun createPaint(ctx: Context, type: PaintType, theme: Theme, stroke: Stroke, pal: Palette): Paint {
            val paint = when (type) {
                PaintType.SHAPE -> prepareShapePaint(ctx, pal.lightId)
                PaintType.HAND -> prepareLinePaint(ctx, pal.id)
                PaintType.CIRCLE -> prepareCirclePaint(ctx, pal.darkId)
                PaintType.POINT -> preparePointPaint(ctx, R.color.points)

                PaintType.SHAPE_OUTLINE -> prepareShapePaint(ctx, R.color.black)
                PaintType.HAND_OUTLINE -> prepareLinePaint(ctx, R.color.black)
                PaintType.CIRCLE_OUTLINE -> prepareCirclePaint(ctx, R.color.black)
                PaintType.POINT_OUTLINE -> preparePointPaint(ctx, R.color.black)

                PaintType.SHAPE_AMB -> prepareShapePaint(ctx, pal.lightId)
                PaintType.HAND_AMB -> prepareLinePaint(ctx, pal.id)
                PaintType.CIRCLE_AMB -> prepareCirclePaint(ctx, pal.darkId)

                //PaintType.TEXT -> prepareTextPaint(ctx, R.color.text)
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = createStrokeWidth(ctx, type, theme, stroke)
            return paint
        }

        private fun createStrokeWidth(ctx: Context, type: PaintType, theme: Theme, stroke: Stroke): Float {
            val isPoint = PaintType.POINT.equals(type) || PaintType.POINT_OUTLINE.equals(type)
            val pointGrowth = if (isPoint) { Growth.create(ctx, theme.growthName).dim ?: 0F } else { 0F }
            return if (type.isOutline) {
                val outline = Outline.create(ctx, theme.outlineName)
                stroke.dim + pointGrowth + outline.dim
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

        fun createFilter(ctx: Context, pal: Palette): ColorFilter {
            return PorterDuffColorFilter(ctx.getColor(pal.lightId), PorterDuff.Mode.MULTIPLY)
        }

        fun findActive(ctx: Context): Palette {
            val colRes = ctx.resources.getString(R.string.saved_palette)
            val colName = ConfigData.prefs(ctx).getString(colRes, Palette.WHITE.name)
            return Palette.getByName(colName)
        }
    }
}
