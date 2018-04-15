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
import zir.watchface.DrawUtil.Companion.TAU

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
        val spec = ConfigData.wave.spectrum
        if (Spectrum.PALETTE == spec || Spectrum.BW == spec ||
                Spectrum.DARK == spec || Spectrum.DARK_WAVE == spec) {
            val pp = pha * 2F / TAU
            val range = Math.min(1.0, Math.max(0.0, pp.toDouble())).toInt()
            return when (spec) {
                Spectrum.PALETTE -> getFromPalette(range, pp - range)
                Spectrum.DARK -> getDark(range, pp - range)
                Spectrum.DARK_WAVE -> getDarkWave(range, pp - range, magnitude)
                Spectrum.BW -> getBlackWhite(range, pp - range)
                else -> getBlackWhite(range, pp - range)
            }
        } else {
            val p = pha * 6F / TAU
            val range = Math.min(5.0, Math.max(0.0, p.toDouble())).toInt()
            val fraction = p - range
            val rgbValues = when (spec) {
                Spectrum.FULL -> getFullSpectrum(range, fraction)
                Spectrum.LINES -> getLines(range)
                Spectrum.SPOOK -> getSpook(fraction)
                Spectrum.RAIN -> getRain(range, fraction)
                else -> getFullSpectrum(range, fraction)
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
    private fun getBlackWhite(range: Int, fraction: Float) = when (range) {
        0 -> ColorUtils.blendARGB(BLACK, WHITE, fraction.toFloat())
        1 -> ColorUtils.blendARGB(WHITE, BLACK, fraction.toFloat())
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getFromPalette(range: Int, fraction: Float) = when (range) {
        0 -> ColorUtils.blendARGB(ConfigData.palette.dark(), ConfigData.palette.light(), fraction.toFloat())
        1 -> ColorUtils.blendARGB(ConfigData.palette.light(), ConfigData.palette.dark(), fraction.toFloat())
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getDarkWave(range: Int, fraction: Float, mag: Float): Int {
        val original = getFullSpectrum(range, fraction)
        val maxMag = mag * MAX_RGB
        val red = original.r * maxMag
        val green = original.g * maxMag
        val blue = original.b * maxMag
        val g = ((red + green + blue) / 4F).toInt()
        return Color.rgb(g, g, g)
    }

    private fun getDark(range: Int, fraction: Float): Int {
        val original = getFromPalette(range, fraction)
        return ColorUtils.blendARGB(Zir.color(R.color.black), original, 0.5F)
    }

    private fun getFullSpectrum(range: Int, fraction: Float) = when (range) {
        0 -> Rgb(1F, fraction, 0F) //Red -> Yellow
        1 -> Rgb(1F - fraction, 1F, 0F) //Yellow -> Green
        2 -> Rgb(0F, 1F, fraction) //Green -> Cyan
        3 -> Rgb(0F, 1F - fraction, 1F) //Cyan -> Blue
        4 -> Rgb(fraction, 0F, 1F) //Blue -> Magenta
        5 -> Rgb(1F, 0F, 1F - fraction) //Magenta -> Red
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private val BLACK_TRIP = Rgb(0F, 0F, 0F)
    private val WHITE_TRIP = Rgb(1F, 1F, 1F)
    private fun getLines(range: Int) = if (range == 0) WHITE_TRIP else BLACK_TRIP
    private fun getSpook(fraction: Float) = Rgb(fraction, fraction, fraction)
    private fun getRain(range: Int, fraction: Float) =
            if (range == 0) Rgb(fraction, fraction, fraction) else BLACK_TRIP
}
