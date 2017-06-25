package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.types.Operator

data class Wave(val name: String, val waveNumber: Float,
                val velocity: Double, val intensity: Double,
                val spectrum: Spectrum = Spectrum.DEFAULT,
                val isBlur: Boolean = true, val isPixel: Boolean = false,
                val op: Operator = Operator.ADD) {
    val iconId: Int = R.drawable.icon_dummy //TODO replace
    val isOff = name.equals("Off")
    val isOn = !isOff
    val hasCenter = false //TODO tune
    val hasHours = true
    val hasMinutes = true
    val hasSeconds = true
    val isKeepState = true
    val lastWeight = 1.0F

    companion object {
        private val VELOCITY = -0.0002
        private val INTENSITY = 7.0
        val OFF = Wave("Off", 1F, 0.0, 1.0, Spectrum.FULL, false)
        val DEFAULT = Wave("Default", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.PALETTE, true)
        val PIXEL = Wave("Pixel", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.PALETTE, false, true)
        val DARK = Wave("Dark", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.DARK, true)
        val DARK_WAVE = Wave("Dark Wave", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.DARK_WAVE, true)
        val DARK_WAVE_NOBL = Wave("Dark Wave No Blur", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.DARK_WAVE, false)
        val DARK_WAVE_PIXEL = Wave("Dark Wave PIXEL", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.DARK_WAVE, false, true)
        val BW = Wave("Black White", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.BW, true)
        val BW_NOBL = Wave("BW No Blur", 2F, VELOCITY * 3, INTENSITY * 1.5, Spectrum.BW, false)
        val SPEC = Wave("Spec", 2F, VELOCITY, INTENSITY, Spectrum.FULL)
        val NO_BLUR = Wave("No Blur", 1F, VELOCITY, INTENSITY, Spectrum.FULL, false)
        val LONG = Wave("Long", 2F, VELOCITY, INTENSITY, Spectrum.FULL, false)
        val SHORT = Wave("Short", 0.5F, VELOCITY, INTENSITY)
        val INTENSE = Wave("Intense", 1F, VELOCITY, INTENSITY * 2, Spectrum.FULL, false)
        val WEAK = Wave("Weak", 1F, VELOCITY, INTENSITY, Spectrum.FULL, false)
        val FAST = Wave("Fast", 1F, VELOCITY * 2.0, INTENSITY)
        val SLOW = Wave("Slow", 1F, VELOCITY * 0.5, INTENSITY)
        val STANDING = Wave("Standing", 1F, 0.0, INTENSITY)
        val MULTIPLY = Wave("Multiply", 1F, VELOCITY, INTENSITY, Spectrum.FULL, true, false, Operator.MULTIPLY)
        val LINES = Wave("Lines", 1F, VELOCITY, INTENSITY, Spectrum.LINES, false)
        val SPOOK = Wave("Spook", 1F, VELOCITY, INTENSITY, Spectrum.SPOOK)
        val RAIN = Wave("Rain", 1F, VELOCITY, INTENSITY, Spectrum.RAIN)

        val default = DARK_WAVE
        val all = listOf(OFF, DEFAULT, PIXEL, DARK, DARK_WAVE, DARK_WAVE_NOBL, DARK_WAVE_PIXEL,
                BW, BW_NOBL, SPEC, NO_BLUR, LONG, SHORT, INTENSE, WEAK, FAST, SLOW, STANDING,
                MULTIPLY, LINES, SPOOK, RAIN)

        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Wave = all.find { it.name.equals(name) } ?: default
    }
}
