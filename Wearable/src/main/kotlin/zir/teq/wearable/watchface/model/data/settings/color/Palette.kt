package zir.teq.wearable.watchface.model.data.settings.color

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.select.config.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.teq.wearable.watchface.util.DrawUtil

interface ColorConfigItem {
    val configId: Int
}

data class Palette(override val configId: Int, val name: String, val darkId: Int, val lightId: Int) : ColorConfigItem {
    fun dark() = Zir.color(darkId)
    fun half() = ColorUtils.blendARGB(Zir.color(darkId), Zir.color(lightId), 0.5F)
    fun light() = Zir.color(lightId)

    companion object {
        fun makeDarker(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.black), 1F / DrawUtil.PHI)
        fun makeLighter(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.white), 1F / DrawUtil.PHI)
        val DARK = Palette(1020, "Dark", R.color.darker, R.color.dark_gray)
        val WHITE = Palette(1030, "White", R.color.dark_grey, R.color.white)
        val RED = Palette(1040, "Red", R.color.fire_brick, R.color.red)
        val BLUE = Palette(1050, "Blue", R.color.dark_blue, R.color.deep_sky_blue)
        val YELLOW = Palette(1060, "", R.color.yellow_dark, R.color.yellow)
        val GREEN = Palette(1070, "Green", R.color.dark_green, R.color.green_yellow)
        val PURPLE = Palette(1080, "Purple", R.color.indigo, R.color.magenta)
        val RED_YELLOW = Palette(1090, "Red and Yellow", R.color.red, R.color.yellow)
        val YELLOW_RED = Palette(1091, "Yellow and Red", R.color.yellow, R.color.red)
        val PURPLE_GREEN = Palette(1100, "Purple and Green", R.color.dark_violet, R.color.lime_green)
        val GREEN_PURPLE = Palette(1101, "Green and Purple", R.color.lime_green, R.color.dark_violet)
        val BLUE_ORANGE = Palette(1110, "Blue and Orange", R.color.deep_sky_blue, R.color.orange)
        val ORANGE_BLUE = Palette(1111, "Orange and Blue", R.color.orange, R.color.deep_sky_blue)
        val ICE = Palette(1120, "Ice", R.color.blue, R.color.white)
        val REVERSE_ICE = Palette(1121, "Reverse Ice", R.color.white, R.color.blue)
        val FIRE = Palette(1130, "Fire", R.color.red, R.color.white)
        val REVERSE_FIRE = Palette(1131, "Reverse Fire", R.color.white, R.color.red)
        val GRASS = Palette(1140, "Grass", R.color.green, R.color.white)
        val REVERSE_GRASS = Palette(1141, "Reverse Grass", R.color.white, R.color.green)

        val defaultType = DARK
        fun default() = create(defaultType.name)

        val ALL = listOf(DARK, WHITE, RED, BLUE, YELLOW, GREEN, PURPLE,
                RED_YELLOW, YELLOW_RED, PURPLE_GREEN, GREEN_PURPLE, BLUE_ORANGE, ORANGE_BLUE,
                ICE, REVERSE_ICE, FIRE, REVERSE_FIRE, GRASS, REVERSE_GRASS)
        fun create(name: String): Palette = ALL.find { it.name.equals(name) } ?: defaultType

        fun createPaint(type: PaintType): Paint = createPaint(type, null)
        fun createPaint(type: PaintType, @ColorInt color: Int?): Paint {
            val paint = when (type) {
                PaintType.SHAPE -> preparePaint(color ?: ConfigData.palette.light())
                PaintType.HAND -> preparePaint(color ?: ConfigData.palette.half())
                PaintType.CIRCLE -> prepareStrokePaint(color ?: ConfigData.palette.dark())
                PaintType.POINT -> prepareStrokePaint(color ?: Zir.color(R.color.points))
                PaintType.SHAPE_AMB -> preparePaint(color ?: ConfigData.palette.light())
                PaintType.HAND_AMB -> preparePaint(color ?: ConfigData.palette.half())
                PaintType.CIRCLE_AMB -> prepareStrokePaint(color ?: ConfigData.palette.dark())
                PaintType.BACKGROUND -> preparePaint(color ?: R.color.background)
                else -> {
                    val msg = "Ignoring paintType: " + type
                    throw IllegalArgumentException(msg)
                }
            }
            paint.strokeWidth = createStrokeWidth(type)
            paint.alpha = ConfigData.style.alpha.value
            paint.isAntiAlias = ConfigData.isAntiAlias

            //apply dimming..
            val dimValue = ConfigData.style.dim.value
            val dimColor = Color.argb(255, dimValue, dimValue, dimValue)
            paint.colorFilter = PorterDuffColorFilter(dimColor, PorterDuff.Mode.MULTIPLY)

            return paint
        }

        fun createTextPaint(): Paint = inst().apply {
            typeface = Item.MONO_TYPEFACE
            isFakeBoldText = true
            color = Zir.color(R.color.text)
            alpha = ConfigData.style.alpha.value
            isAntiAlias = ConfigData.isAntiAlias
            if (ConfigData.isAmbient) {
                clearShadowLayer()
            } else {
                val blurRadius = 5F
                setShadowLayer(blurRadius, 0F, 0F, Color.BLACK)
            }
        }

        private fun createStrokeWidth(type: PaintType): Float {
            val isPoint = PaintType.POINT.equals(type)
            val pointGrowth = if (isPoint) ConfigData.style.growth.dim else 0F
            return ConfigData.style.stroke.dim + pointGrowth
        }

        private fun inst() = Paint().apply { isAntiAlias = true; isDither = true }
        private fun preparePaint(@ColorInt col: Int) = inst().apply {
            strokeCap = Paint.Cap.ROUND; color = col
        }

        private fun prepareStrokePaint(@ColorInt col: Int) = preparePaint(col).apply {
            style = Paint.Style.STROKE
        }

        fun createFilter(pal: Palette) = PorterDuffColorFilter(pal.half(), PorterDuff.Mode.MULTIPLY)
    }
}
