package zir.teq.wearable.watchface.util

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.wave.Spectrum
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.model.data.types.Rgb
import zir.teq.wearable.watchface.util.DrawUtil.Companion.TAU

object ColorUtil {
    val MAX_RGB = 0xFF

    @ColorInt
    fun getColor(c: Complex): Int = getColorFromMagnitudeAndPhase(c.magnitude, c.phase)

    @ColorInt
    fun getColorFromMagnitudeAndPhase(magnitude: Float, phase: Float): Int {
        if (magnitude <= 1.0 / MAX_RGB) {
            return Color.BLACK
        }
        val mag = Math.min(1F, magnitude)
        val pha: Float = if (phase < 0.0) phase + TAU else phase
        val spec = ConfigData.waveSpectrum()
        if (Spectrum.PALETTE == spec || Spectrum.DARK_PALETTE == spec || Spectrum.DARKER_PALETTE == spec ||
                Spectrum.CHAOS == spec || Spectrum.DARK_CHAOS == spec || Spectrum.DARKER_CHAOS == spec ||
                Spectrum.BW == spec || Spectrum.DARK_BW == spec || Spectrum.DARKER_BW == spec) { //TODO cleanup
            val pp = pha * 2F / TAU
            val range = Math.min(1.0, Math.max(0.0, pp.toDouble())).toInt()
            return when (spec) {
                Spectrum.PALETTE -> getFromPalette(range, pp - range)
                Spectrum.DARK_PALETTE -> getFromDarkPalette(range, pp - range)
                Spectrum.DARKER_PALETTE -> getFromDarkerPalette(range, pp - range)
                Spectrum.CHAOS -> getChaos(range, pp - range, magnitude)
                Spectrum.DARK_CHAOS -> getDarkChaos(range, pp - range, magnitude)
                Spectrum.DARKER_CHAOS -> getDarkerChaos(range, pp - range, magnitude)
                Spectrum.BW -> getBlackWhite(range, pp - range)
                Spectrum.DARK_BW -> getDarkBlackWhite(range, pp - range)
                Spectrum.DARKER_BW -> getDarkerBlackWhite(range, pp - range)
                else -> getBlackWhite(range, pp - range)
            }
        } else {
            val p = pha * 6F / TAU
            val range = Math.min(5.0, Math.max(0.0, p.toDouble())).toInt()
            val fraction = p - range
            val rgbValues = when (spec) {
                Spectrum.FULL -> getFull(range, fraction)
                Spectrum.DARK_FULL -> getDarkFull(range, fraction)
                Spectrum.DARKER_FULL -> getDarkerFull(range, fraction)
                Spectrum.LINES -> getLines(range)
                Spectrum.DARK_LINES -> getDarkLines(range)
                Spectrum.DARKER_LINES -> getDarkerLines(range)
                Spectrum.SPOOK -> getSpook(fraction)
                Spectrum.RAIN -> getRain(range, fraction)
                else -> getFull(range, fraction)
            }
            val maxMag = mag * MAX_RGB
            val red = rgbValues.r * maxMag
            val green = rgbValues.g * maxMag
            val blue = rgbValues.b * maxMag
            return Color.rgb(red.toInt(), green.toInt(), blue.toInt())
        }
    }

    val BLACK = Zir.color(R.color.black)
    val WHITE = Zir.color(R.color.white)

    private fun getDarkerBlackWhite(range: Int, fraction: Float): Int {
        return ColorUtils.blendARGB(getBlackWhite(range, fraction), BLACK, 0.5F)
    }

    private fun getDarkBlackWhite(range: Int, fraction: Float): Int {
        return ColorUtils.blendARGB(getBlackWhite(range, fraction), BLACK, 0.75F)
    }

    private fun getBlackWhite(range: Int, fraction: Float) = when (range) {
        0 -> ColorUtils.blendARGB(BLACK, WHITE, fraction)
        1 -> ColorUtils.blendARGB(WHITE, BLACK, fraction)
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getFromDarkerPalette(range: Int, fraction: Float): Int {
        return ColorUtils.blendARGB(getFromPalette(range, fraction), BLACK, 0.5F)
    }

    private fun getFromDarkPalette(range: Int, fraction: Float): Int {
        return ColorUtils.blendARGB(getFromPalette(range, fraction), BLACK, 0.75F)
    }

    private fun getFromPalette(range: Int, fraction: Float) = when (range) {
        0 -> ColorUtils.blendARGB(ConfigData.palette().dark(), ConfigData.palette().light(), fraction)
        1 -> ColorUtils.blendARGB(ConfigData.palette().light(), ConfigData.palette().dark(), fraction)
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getDarkerChaos(range: Int, fraction: Float, mag: Float) =
            ColorUtils.blendARGB(Zir.color(R.color.black), getChaos(range, fraction, mag), 0.5F)

    private fun getDarkChaos(range: Int, fraction: Float, mag: Float) =
            ColorUtils.blendARGB(Zir.color(R.color.black), getChaos(range, fraction, mag), 0.75F)

    private fun getChaos(range: Int, fraction: Float, mag: Float): Int {
        val original = getFull(range, fraction)
        val maxMag = mag * MAX_RGB
        val red = original.r * maxMag
        val green = original.g * maxMag
        val blue = original.b * maxMag
        val g = ((red + green + blue) / 4F).toInt()
        return Color.rgb(g, g, g)
    }

    private fun getDarkerFull(range: Int, fraction: Float): Rgb {
        val full = getFull(range, fraction)
        return Rgb(full.r * 0.5F, full.g * 0.5F, full.b * 0.5F)
    }

    private fun getDarkFull(range: Int, fraction: Float): Rgb {
        val full = getFull(range, fraction)
        return Rgb(full.r * 0.75F, full.g * 0.75F, full.b * 0.75F)
    }

    private fun getFull(range: Int, fraction: Float) = when (range) {
        0 -> Rgb(1F, fraction, 0F) //Red -> Yellow
        1 -> Rgb(1F - fraction, 1F, 0F) //Yellow -> Green
        2 -> Rgb(0F, 1F, fraction) //Green -> Cyan
        3 -> Rgb(0F, 1F - fraction, 1F) //Cyan -> Blue
        4 -> Rgb(fraction, 0F, 1F) //Blue -> Magenta
        5 -> Rgb(1F, 0F, 1F - fraction) //Magenta -> Red
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getDarkerLines(range: Int): Rgb {
        val full = getLines(range)
        return Rgb(full.r * 0.5F, full.g * 0.5F, full.b * 0.5F)
    }

    private fun getDarkLines(range: Int): Rgb {
        val full = getLines(range)
        return Rgb(full.r * 0.75F, full.g * 0.75F, full.b * 0.75F)
    }

    private fun getLines(range: Int) = if (range == 0) WHITE_TRIP else BLACK_TRIP

    private fun getSpook(fraction: Float) = Rgb(fraction, fraction, fraction)

    private fun getRain(range: Int, fraction: Float) =
            if (range == 0) Rgb(fraction, fraction, fraction) else BLACK_TRIP

    private val BLACK_TRIP = Rgb(0F, 0F, 0F)
    private val WHITE_TRIP = Rgb(1F, 1F, 1F)
}
