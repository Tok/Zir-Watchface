package zir.teq.wearable.watchface.util

import android.graphics.Color
import android.support.annotation.ColorInt
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Wave
import zir.watchface.DrawUtil.Companion.TAU


object ColorUtil {
    val MAX_RGB = 0xFF
    @ColorInt fun getColor(c: Complex): Int = getColorFromMagnitudeAndPhase(c.magnitude, c.phase)
    @ColorInt fun getColorFromMagnitudeAndPhase(magnitude: Double, phase: Double): Int {
        if (magnitude <= 1.0 / MAX_RGB) {
            return Color.BLACK
        }
        val mag = Math.min(1.0, magnitude)
        val pha = if (phase < 0.0) phase + TAU else phase
        val p = pha * 6.0 / TAU
        val range = Math.min(5.0, Math.max(0.0, p)).toInt()
        val fraction = p - range
        val rgbValues = when (ConfigData.wave.spectrum) {
            Wave.SPEC_FULL -> getFullSpectrum(range, fraction)
            Wave.SPEC_LINES -> getLines(range)
            Wave.SPEC_SPOOK -> getSpook(fraction)
            Wave.SPEC_RAIN -> getRain(range, fraction)
            else -> getFullSpectrum(range, fraction)
        }
        val red = (rgbValues.first * mag * MAX_RGB).toInt()
        val green = (rgbValues.second * mag * MAX_RGB).toInt()
        val blue = (rgbValues.third * mag * MAX_RGB).toInt()
        return Color.rgb(red, green, blue)
    }

    private fun getFullSpectrum(range: Int, fraction: Double): Triple<Double, Double, Double> {
        return when (range) {
            0 -> Triple(1.0, fraction, 0.0)       //Red -> Yellow
            1 -> Triple(1.0 - fraction, 1.0, 0.0) //Yellow -> Green
            2 -> Triple(0.0, 1.0, fraction)       //Green -> Cyan
            3 -> Triple(0.0, 1.0 - fraction, 1.0) //Cyan -> Blue
            4 -> Triple(fraction, 0.0, 1.0)       //Blue -> Magenta
            5 -> Triple(1.0, 0.0, 1.0 - fraction) //Magenta -> Red
            else -> throw IllegalArgumentException("Out of range: " + range)
        }
    }

    private fun getLines(range: Int) =
            if (range == 0) {
                Triple(1.0, 1.0, 1.0)
            } else {
                Triple(0.0, 0.0, 0.0)
            }

    private fun getSpook(fraction: Double) = Triple(fraction, fraction, fraction)

    private fun getRain(range: Int, fraction: Double) =
            if (range == 0) {
                Triple(fraction, fraction, fraction)
            } else {
                Triple(0.0, 0.0, 0.0)
            }
}
