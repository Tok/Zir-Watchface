package zir.teq.wearable.watchface.model.data.settings.color

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.config.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.PaintType
import zir.watchface.DrawUtil

interface ColorConfigItem {
    val configId: Int
}

data class Palette(override val configId: Int, val name: String, val darkId: Int, val lightId: Int) : ColorConfigItem {

    fun dark() = ConfigData.ctx.getColor(darkId)
    fun half() = ColorUtils.blendARGB(ConfigData.ctx.getColor(darkId), ConfigData.ctx.getColor(lightId), 0.5F)
    fun light() = ConfigData.ctx.getColor(lightId)

    companion object {
        fun makeDarker(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.black), 1F / DrawUtil.PHI)
        fun makeLighter(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.white), 1F / DrawUtil.PHI)
        val BLACK = Palette(1010, "Black", R.color.black, R.color.dark_gray)
        val DARK = Palette(1020, "Dark", R.color.darker, R.color.dark_gray)
        val WHITE = Palette(1030, "White", R.color.dark_grey, R.color.white)
        val RED = Palette(1040, "Red", R.color.dark_red, R.color.red)
        val YELLOW = Palette(1050, "Yellow", R.color.yellow_dark, R.color.yellow)
        val GREEN = Palette(1060, "Green", R.color.dark_green, R.color.green_yellow)
        val BLUE = Palette(1070, "Blue", R.color.dark_blue, R.color.deep_sky_blue)
        val PURPLE = Palette(1080, "Purple", R.color.indigo, R.color.magenta)
        val PURPLE_GREEN = Palette(1090, "Purple and Green", R.color.indigo, R.color.green_yellow)
        val BLUE_ORANGE = Palette(1100, "Blue and Orange", R.color.deep_sky_blue, R.color.bright_orange)
        val RED_YELLOW = Palette(1110, "Red and Yellow", R.color.red, R.color.yellow)

        val defaultType = DARK
        fun default() = create(defaultType.name)

        val ALL = listOf(BLACK, DARK, WHITE,
                RED, YELLOW, GREEN, BLUE, PURPLE,
                PURPLE_GREEN, BLUE_ORANGE, RED_YELLOW)
        val selectable = listOf(RED, YELLOW, GREEN, BLUE, PURPLE)
        fun create(name: String): Palette = ALL.find { it.name.equals(name) } ?: defaultType

        fun createPaint(type: PaintType): Paint = createPaint(type, null)
        fun createPaint(type: PaintType, @ColorInt color: Int?): Paint {
            val paint = when (type) {
                PaintType.SHAPE -> preparePaint(color ?: ConfigData.palette.light())
                PaintType.HAND -> preparePaint(color ?: ConfigData.palette.half())
                PaintType.CIRCLE -> prepareStrokePaint(color ?: ConfigData.palette.dark())
                PaintType.POINT -> prepareStrokePaint(color ?: ConfigData.ctx.getColor(R.color.points))
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
            color = ConfigData.ctx.getColor(R.color.text)
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