package zir.teq.wearable.watchface.util

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.wave.Spectrum
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.watchface.DrawUtil.Companion.TAU


object ColorUtil {
    val MAX_RGB = 0xFF
    @ColorInt fun getColor(c: Complex): Int = getColorFromMagnitudeAndPhase(c.magnitude, c.phase)
    @ColorInt fun getColorFromMagnitudeAndPhase(magnitude: Double, phase: Double): Int {
        if (magnitude <= 1.0 / MAX_RGB) {
            return Color.BLACK
        }
        val mag = Math.min(1.0, magnitude)
        val pha: Double = if (phase < 0.0) phase + TAU else phase
        val spec = ConfigData.wave.spectrum
        if (Spectrum.PALETTE == spec || Spectrum.BW == spec ||
                Spectrum.DARK == spec || Spectrum.DARK_WAVE == spec) {
            val pp = pha * 2.0 / TAU
            val range = Math.min(1.0, Math.max(0.0, pp)).toInt()
            return when (spec) {
                Spectrum.PALETTE -> getFromPalette(range, pp - range)
                Spectrum.DARK -> getDark(range, pp - range)
                Spectrum.DARK_WAVE -> getDarkWave(range, pp - range, magnitude)
                Spectrum.BW -> getBlackWhite(range, pp - range)
                else -> getBlackWhite(range, pp - range)
            }
        } else {
            val p = pha * 6.0 / TAU
            val range = Math.min(5.0, Math.max(0.0, p)).toInt()
            val fraction = p - range
            val rgbValues = when (spec) {
                Spectrum.FULL -> getFullSpectrum(range, fraction)
                Spectrum.LINES -> getLines(range)
                Spectrum.SPOOK -> getSpook(fraction)
                Spectrum.RAIN -> getRain(range, fraction)
                else -> getFullSpectrum(range, fraction)
            }
            val maxMag = mag * MAX_RGB
            val red = (rgbValues.first * maxMag).toInt()
            val green = (rgbValues.second * maxMag).toInt()
            val blue = (rgbValues.third * maxMag).toInt()
            return Color.rgb(red, green, blue)
        }
    }

    private val BLACK = ConfigData.ctx.getColor(R.color.black)
    private val WHITE = ConfigData.ctx.getColor(R.color.white)
    private fun getBlackWhite(range: Int, fraction: Double) = when (range) {
        0 -> ColorUtils.blendARGB(BLACK, WHITE, fraction.toFloat())
        1 -> ColorUtils.blendARGB(WHITE, BLACK, fraction.toFloat())
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getFromPalette(range: Int, fraction: Double) = when (range) {
        0 -> ColorUtils.blendARGB(ConfigData.palette.dark(), ConfigData.palette.light(), fraction.toFloat())
        1 -> ColorUtils.blendARGB(ConfigData.palette.light(), ConfigData.palette.dark(), fraction.toFloat())
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private fun getDarkWave(range: Int, fraction: Double, mag: Double): Int {
        val original = getFullSpectrum(range, fraction)
        val maxMag = mag * MAX_RGB
        val red = (original.first * maxMag).toInt()
        val green = (original.second * maxMag).toInt()
        val blue = (original.third * maxMag).toInt()
        val g = ((red + green + blue) / 4F).toInt()
        return Color.rgb(g, g, g)
    }

    private fun getDark(range: Int, fraction: Double): Int {
        val original = getFromPalette(range, fraction)
        return ColorUtils.blendARGB(ConfigData.ctx.getColor(R.color.black), original, 0.5F)
    }

    private fun getFullSpectrum(range: Int, fraction: Double) = when (range) {
        0 -> Triple(1.0, fraction, 0.0)       //Red -> Yellow
        1 -> Triple(1.0 - fraction, 1.0, 0.0) //Yellow -> Green
        2 -> Triple(0.0, 1.0, fraction)       //Green -> Cyan
        3 -> Triple(0.0, 1.0 - fraction, 1.0) //Cyan -> Blue
        4 -> Triple(fraction, 0.0, 1.0)       //Blue -> Magenta
        5 -> Triple(1.0, 0.0, 1.0 - fraction) //Magenta -> Red
        else -> throw IllegalArgumentException("Out of range: " + range)
    }

    private val BLACK_TRIP = Triple(0.0, 0.0, 0.0)
    private val WHITE_TRIP = Triple(1.0, 1.0, 1.0)
    private fun getLines(range: Int) = if (range == 0) WHITE_TRIP else BLACK_TRIP
    private fun getSpook(fraction: Double) = Triple(fraction, fraction, fraction)
    private fun getRain(range: Int, fraction: Double) =
            if (range == 0) Triple(fraction, fraction, fraction) else BLACK_TRIP
}
