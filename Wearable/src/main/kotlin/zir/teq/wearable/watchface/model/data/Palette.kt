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
    fun dark() = ConfigData.ctx.getColor(darkId)
    fun half() = ColorUtils.blendARGB(ConfigData.ctx.getColor(darkId), ConfigData.ctx.getColor(lightId), 0.5F)
    fun light() = ConfigData.ctx.getColor(lightId)

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
        val defaultType = BLACK
        fun default() = create(defaultType.name)

        private val all = listOf(BLACK, WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE,
                PURPLE_GREEN, RED_YELLOW, BLUE_ORANGE, BLACK_YELLOW)
        val selectable = listOf(RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE)
        fun options() = all.toCollection(ArrayList<Palette>())
        fun create(name: String): Palette = all.find { it.name.equals(name) } ?: defaultType
        fun prep(color: Int): Paint {
            val paint = inst()
            paint.color = color
            //paint.isAntiAlias = true
            //paint.isDither = true
            return paint
        }

        fun createPaint(type: PaintType): Paint {
            val paint = when (type) {
                PaintType.SHAPE -> prepareShapePaint(ConfigData.palette.light())
                PaintType.HAND -> prepareLinePaint(ConfigData.palette.half())
                PaintType.CIRCLE -> prepareCirclePaint(ConfigData.palette.dark())
                PaintType.POINT -> preparePointPaint(ConfigData.ctx.getColor(R.color.points))
                PaintType.SHAPE_AMB -> prepareShapePaint(ConfigData.palette.light())
                PaintType.HAND_AMB -> prepareLinePaint(ConfigData.palette.half())
                PaintType.CIRCLE_AMB -> prepareCirclePaint(ConfigData.palette.dark())
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = createStrokeWidth(type)
            paint.alpha = ConfigData.alpha.value
            paint.isAntiAlias = ConfigData.isAntiAlias

            //apply dimming..
            val dimValue = ConfigData.dim.value
            val dimColor = Color.argb(255, dimValue, dimValue, dimValue)
            paint.colorFilter = PorterDuffColorFilter(dimColor, PorterDuff.Mode.MULTIPLY)

            return paint
        }

        fun createTextPaint(): Paint {
            val paint = inst()
            paint.typeface = ConfigItem.MONO_TYPEFACE
            paint.isFakeBoldText = true
            paint.color = ConfigData.ctx.getColor(R.color.text)
            paint.alpha = ConfigData.alpha.value
            paint.isAntiAlias = ConfigData.isAntiAlias
            addShadows(paint)
            return paint
        }

        private fun addShadows(paint: Paint) {
            if (ConfigData.isAmbient) {
                paint.clearShadowLayer()
            } else {
                val blurRadius = 5F
                paint.setShadowLayer(blurRadius, 0F, 0F, Color.BLACK)
            }
        }

        private fun createStrokeWidth(type: PaintType): Float {
            val isPoint = PaintType.POINT.equals(type)
            val pointGrowth = if (isPoint) ConfigData.growth.dim else 0F
            return ConfigData.stroke.dim + pointGrowth
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

        fun createFilter(pal: Palette): ColorFilter {
            return PorterDuffColorFilter(pal.half(), PorterDuff.Mode.MULTIPLY)
        }
    }
}
