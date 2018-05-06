package zir.teq.wearable.watchface.model.setting.color

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.style.StyleAlpha
import zir.teq.wearable.watchface.model.setting.style.StyleDim
import zir.teq.wearable.watchface.model.setting.style.StyleGrowth
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.model.types.PaintType
import zir.teq.wearable.watchface.util.CalcUtil.PHI

abstract class PaletteConfigItem(override val pref: String) : ColorConfigItem

data class Palette(val name: String, val darkId: Int, val lightId: Int) : PaletteConfigItem(name) {
    fun dark() = Zir.color(darkId)
    fun half() = ColorUtils.blendARGB(Zir.color(darkId), Zir.color(lightId), 0.5F)
    fun light() = Zir.color(lightId)
    override val pref = name
    override val viewType = 777

    companion object {
        fun makeDarker(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.black), 1F / PHI)
        fun makeLighter(ctx: Context, @ColorInt color: Int) = ColorUtils.blendARGB(color, ctx.getColor(R.color.white), 1F / PHI)
        val DARK = Palette("Dark", R.color.darker, R.color.dark_gray)
        val WHITE = Palette("White", R.color.dark_grey, R.color.white)
        val RED = Palette("Red", R.color.fire_brick, R.color.red)
        val BLUE = Palette("Blue", R.color.dark_blue, R.color.deep_sky_blue)
        val YELLOW = Palette("Yellow", R.color.yellow_dark, R.color.yellow)
        val GREEN = Palette("Green", R.color.dark_green, R.color.green_yellow)
        val PURPLE = Palette("Purple", R.color.indigo, R.color.magenta)
        val RED_YELLOW = Palette("Red and Yellow", R.color.red, R.color.yellow)
        val YELLOW_RED = Palette("Yellow and Red", R.color.yellow, R.color.red)
        val PURPLE_GREEN = Palette("Purple and Green", R.color.dark_violet, R.color.lime_green)
        val GREEN_PURPLE = Palette("Green and Purple", R.color.lime_green, R.color.dark_violet)
        val BLUE_ORANGE = Palette("Blue and Orange", R.color.deep_sky_blue, R.color.orange)
        val ORANGE_BLUE = Palette("Orange and Blue", R.color.orange, R.color.deep_sky_blue)
        val ICE = Palette("Ice", R.color.blue, R.color.white)
        val REVERSE_ICE = Palette("Reverse Ice", R.color.white, R.color.blue)
        val FIRE = Palette("Fire", R.color.red, R.color.white)
        val REVERSE_FIRE = Palette("Reverse Fire", R.color.white, R.color.red)
        val GRASS = Palette("Grass", R.color.green, R.color.white)
        val REVERSE_GRASS = Palette("Reverse Grass", R.color.white, R.color.green)

        val defaultType = DARK
        fun default() = create(defaultType.name)

        val ALL = listOf(DARK, WHITE, RED, BLUE, YELLOW, GREEN, PURPLE,
                RED_YELLOW, YELLOW_RED, PURPLE_GREEN, GREEN_PURPLE, BLUE_ORANGE, ORANGE_BLUE,
                ICE, REVERSE_ICE, FIRE, REVERSE_FIRE, GRASS, REVERSE_GRASS)

        fun create(name: String): Palette = ALL.find { it.name.equals(name) } ?: defaultType

        fun createPaint(type: PaintType): Paint = createPaint(type, null)
        fun createPaint(type: PaintType, @ColorInt color: Int?): Paint {
            val pal = ConfigData.palette()
            val paint = when (type) {
                PaintType.SHAPE -> preparePaint(color ?: pal.light())
                PaintType.HAND -> preparePaint(color ?: pal.half())
                PaintType.CIRCLE -> prepareStrokePaint(color ?: pal.dark())
                PaintType.POINT -> prepareStrokePaint(color ?: Zir.color(R.color.points))
                PaintType.META -> prepareMetaPaint(color ?: Zir.color(R.color.points))
                PaintType.SHAPE_AMB -> preparePaint(color ?: pal.light())
                PaintType.HAND_AMB -> preparePaint(color ?: pal.half())
                PaintType.CIRCLE_AMB -> prepareStrokePaint(color ?: pal.dark())
                PaintType.BACKGROUND -> preparePaint(color ?: R.color.background)
                else -> throw IllegalArgumentException("Unknown paintType: $type.")
            }
            paint.strokeWidth = createStrokeWidth(type)
            paint.alpha = (StyleAlpha.load() as StyleAlpha).value.toInt()
            paint.isAntiAlias = ConfigData.isAntiAlias

            //apply dimming..
            val dimValue = StyleDim.load().value.toInt()
            val dimColor = Color.argb(255, dimValue, dimValue, dimValue)
            paint.colorFilter = PorterDuffColorFilter(dimColor, PorterDuff.Mode.MULTIPLY)

            return paint
        }

        fun createTextPaint(): Paint = inst().apply {
            typeface = ConfigData.MONO_TYPEFACE
            isFakeBoldText = true
            color = Zir.color(R.color.text)
            alpha = StyleAlpha.load().value.toInt()
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
            val pointGrowth = if (isPoint) StyleGrowth.load().value else 0F
            return StyleStroke.load().value + pointGrowth
        }

        private fun inst() = Paint().apply { isAntiAlias = true; isDither = true }
        fun preparePaint(@ColorInt col: Int):Paint = inst().apply {
            color = col
            strokeCap = Paint.Cap.ROUND
        }
        private fun prepareMetaPaint(@ColorInt col: Int):Paint = preparePaint(col).apply {
            style = Paint.Style.FILL
        }
        private fun prepareStrokePaint(@ColorInt col: Int):Paint = preparePaint(col).apply {
            style = Paint.Style.STROKE
        }

        fun createFilter(pal: Palette) = PorterDuffColorFilter(pal.half(), PorterDuff.Mode.MULTIPLY)

        fun save(item: Palette) {
            val editor = ConfigData.prefs.edit()
            val key = Zir.string(R.string.saved_palette)
            editor.putString(key, item.name)
            editor.apply()
        }
    }
}
