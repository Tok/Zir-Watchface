package zir.teq.wearable.watchface.model.data

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.watchface.DrawUtil

data class Palette(val name: String, val darkId: Int, val lightId: Int) {
    val isAntiAlias = true
    var isAmbient = false
    var isMute = false
    var background = Background.default
    var alpha = Alpha.default
    var dim = Dim.default
    var isElastic = false

    fun dark(ctx: Context) = ctx.getColor(darkId)
    fun half(ctx: Context) = ColorUtils.blendARGB(ctx.getColor(darkId), ctx.getColor(lightId), 0.5F)
    fun light(ctx: Context) = ctx.getColor(lightId)

    companion object {
        fun makeDarker(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.black), 1F / DrawUtil.PHI)
        fun makeLighter(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.white), 1F / DrawUtil.PHI)
        val BLACK = Palette("Black", R.color.black, R.color.dark_gray)
        val WHITE = Palette("White", R.color.dark_grey, R.color.white)
        val RED = Palette("Red", R.color.dark_red, R.color.red)
        val ORANGE = Palette("Orange", R.color.orange_red, R.color.bright_orange)
        val YELLOW = Palette("Yellow", R.color.yellow_dark, R.color.yellow)
        val GREEN = Palette("Green", R.color.dark_green, R.color.green_yellow)
        val BLUE = Palette("Blue", R.color.dark_blue, R.color.deep_sky_blue)
        val PURPLE = Palette("Purple", R.color.indigo, R.color.magenta)
        val PURPLE_GREEN = Palette("Purple and Green", R.color.indigo, R.color.green_yellow)
        val RED_YELLOW = Palette("Red and Yellow", R.color.red, R.color.yellow)
        val BLUE_ORANGE = Palette("Blue and Orange", R.color.deep_sky_blue, R.color.bright_orange)
        val BLACK_YELLOW = Palette("Black and Yellow", R.color.black, R.color.yellow)
        val default = BLACK
        private val all = listOf(BLACK, WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE,
                PURPLE_GREEN, RED_YELLOW, BLUE_ORANGE, BLACK_YELLOW)
        val selectable = listOf(RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE)
        fun options() = all.toCollection(ArrayList<Palette>())
        fun getByName(name: String): Palette = all.find { it.name.equals(name) } ?: default
        fun prep(color: Int): Paint {
            val paint = inst()
            paint.color = color
            //paint.isAntiAlias = true
            //paint.isDither = true
            return paint
        }

        fun createPaint(ctx: Context, type: PaintType, theme: Theme, stroke: Stroke): Paint {
            return createPaint(ctx, type, theme, stroke, Palette.default)
        }

        fun createPaint(ctx: Context, type: PaintType, theme: Theme, stroke: Stroke, pal: Palette): Paint {
            val paint = when (type) {
                PaintType.SHAPE -> prepareShapePaint(pal.light(ctx))
                PaintType.HAND -> prepareLinePaint(pal.half(ctx))
                PaintType.CIRCLE -> prepareCirclePaint(pal.dark(ctx))
                PaintType.POINT -> preparePointPaint(ctx.getColor(R.color.points))

                PaintType.SHAPE_OUTLINE -> prepareShapePaint(ctx.getColor(R.color.black))
                PaintType.HAND_OUTLINE -> prepareLinePaint(ctx.getColor(R.color.black))
                PaintType.CIRCLE_OUTLINE -> prepareCirclePaint(ctx.getColor(R.color.black))
                PaintType.POINT_OUTLINE -> preparePointPaint(ctx.getColor(R.color.black))

                PaintType.SHAPE_AMB -> prepareShapePaint(pal.light(ctx))
                PaintType.HAND_AMB -> prepareLinePaint(pal.half(ctx))
                PaintType.CIRCLE_AMB -> prepareCirclePaint(pal.dark(ctx))
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = createStrokeWidth(ctx, type, theme, stroke)
            paint.alpha = pal.alpha.value
            paint.isAntiAlias = pal.isAntiAlias

            //apply dimming..
            val dimColor = Color.argb(255, pal.dim.value, pal.dim.value, pal.dim.value)
            paint.colorFilter = PorterDuffColorFilter(dimColor, PorterDuff.Mode.MULTIPLY)

            return paint
        }

        fun createTextPaint(ctx: Context, pal: Palette): Paint {
            val paint = inst()
            paint.typeface = ConfigItem.MONO_TYPEFACE
            paint.isFakeBoldText = true
            paint.color = ctx.getColor(R.color.text)
            paint.alpha = pal.alpha.value
            paint.isAntiAlias = pal.isAntiAlias
            addShadows(paint, pal)
            return paint
        }

        private fun addShadows(paint: Paint, pal: Palette) {
            if (pal.isAmbient) {
                paint.clearShadowLayer()
            } else {
                val blurRadius = 5F
                paint.setShadowLayer(blurRadius, 0F, 0F, Color.BLACK)
            }
        }

        private fun createStrokeWidth(ctx: Context, type: PaintType, theme: Theme, stroke: Stroke): Float {
            val isPoint = PaintType.POINT.equals(type) || PaintType.POINT_OUTLINE.equals(type)
            val pointGrowth = if (isPoint) {
                Growth.create(ctx, theme.growthName).dim
            } else {
                0F
            }
            return if (type.isOutline) {
                val outline = Outline.create(ctx, theme.outlineName)
                stroke.dim + pointGrowth + outline.dim
            } else {
                stroke.dim + pointGrowth
            }
        }

        private fun inst(): Paint {
            val paint = Paint()
            paint.isAntiAlias = true
            return paint
        }

        private fun prepareLinePaint(@ColorInt color: Int): Paint {
            val paint = inst()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = color
            return paint
        }

        private fun prepareOutlinePaint(@ColorInt color: Int): Paint {
            val paint = inst()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = color
            return paint
        }

        private fun prepareShapePaint(@ColorInt color: Int): Paint {
            val paint = inst()
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = color
            return paint
        }

        private fun prepareCirclePaint(@ColorInt color: Int): Paint {
            val paint = inst()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = color
            return paint
        }

        private fun preparePointPaint(@ColorInt color: Int): Paint {
            val paint = inst()
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = color
            return paint
        }

        fun createFilter(ctx: Context, pal: Palette): ColorFilter {
            return PorterDuffColorFilter(pal.half(ctx), PorterDuff.Mode.MULTIPLY)
        }

        fun findActive(ctx: Context): Palette {
            val colRes = ctx.resources.getString(R.string.saved_palette)
            val colName = ConfigData.prefs(ctx).getString(colRes, Palette.WHITE.name)
            return Palette.getByName(colName)
        }
    }
}
