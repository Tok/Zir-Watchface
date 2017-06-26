package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.watchface.DrawUtil

data class Wave(val name: String,
                val waveLength: Float = DEFAULT_WAVE_LENGTH,
                val velocity: Float = DEFAULT_VELOCITY,
                val intensity: Float = DEFAULT_INTENSITY,
                val spectrum: Spectrum = Spectrum.DEFAULT,
                val isBlur: Boolean = true,
                val isPixel: Boolean = false,
                val op: Operator = Operator.ADD) {
    val iconId: Int = R.drawable.icon_dummy //TODO replace
    val isOff = name.equals("Off")
    val isOn = !isOff
    val hasCenter = true //TODO tune
    val hasHours = true
    val hasMinutes = true
    val hasSeconds = true
    val isKeepState = false
    val lastWeight = 1F / Math.E.toFloat()
    fun frequency() = 1F / waveLength

    companion object {
        private val DEFAULT_VELOCITY = -0.1F //approx. cycles per second
        private val DEFAULT_WAVE_LENGTH = 1F //units
        private val DEFAULT_INTENSITY = DrawUtil.TAU * DrawUtil.PHI
        val OFF = Wave("Off")
        val DEF = Wave(name = "Default")
        val PALETTE = DEF.copy(name = "Palette", spectrum = Spectrum.PALETTE)
        val LINES = DEF.copy(name = "Lines", spectrum = Spectrum.LINES)
        val SPOOK = DEF.copy(name = "Spook", spectrum = Spectrum.SPOOK)
        val RAIN = DEF.copy(name = "Rain", spectrum = Spectrum.RAIN)
        val DARK = DEF.copy(name = "Dark", spectrum = Spectrum.DARK)
        val DARK_WAVE = DEF.copy(name = "Dark Wave", spectrum = Spectrum.DARK_WAVE)
        val BW = DEF.copy(name = "Black White", spectrum = Spectrum.BW)
        val FULL = DEF.copy(name = "Full", spectrum = Spectrum.FULL)
        val PIXEL = OFF.copy(name = "Pixel", isPixel = true, isBlur = false)
        val LONG = DEF.copy(name = "Long", waveLength = DEFAULT_WAVE_LENGTH * 2F)
        val SHORT = DEF.copy(name = "Short", waveLength = DEFAULT_WAVE_LENGTH * 0.5F)
        val FAST = DEF.copy(name = "Fast", velocity = DEFAULT_VELOCITY * 2F)
        val SLOW = DEF.copy(name = "Slow", velocity = DEFAULT_VELOCITY * 0.5F)
        val INTENSE = DEF.copy(name = "Intense", intensity = DEFAULT_INTENSITY * 2F)
        val WEAK = DEF.copy(name = "Weak", intensity = DEFAULT_INTENSITY * 0.5F)
        val STANDING = DEF.copy(name = "Full", velocity = 0F)
        val MULTIPLY = DEF.copy(name = "Multiply", op = Operator.MULTIPLY)

        val default = DEF
        val all = listOf(OFF, DEF, PALETTE, LINES, SPOOK, RAIN, DARK, DARK_WAVE, BW, FULL,
                PIXEL, LONG, SHORT, FAST, SLOW, INTENSE, WEAK, STANDING, MULTIPLY)

        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Wave = all.find { it.name.equals(name) } ?: default
    }
}