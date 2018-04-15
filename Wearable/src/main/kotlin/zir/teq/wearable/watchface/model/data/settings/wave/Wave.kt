package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.watchface.DrawUtil.Companion.PHI

data class Wave(val name: String,
                val waveLength: Float,
                val velocity: Float,
                val intensity: Float,
                val spectrum: Spectrum,
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
        private val DEFAULT_VELOCITY = -1F //approx. cycles per second //FIXME
        private val DEFAULT_WAVE_LENGTH = 1F //units //FIXME
        private val DEFAULT_INTENSITY = 7F //~brightness

        val OFF = Wave("Off", 0F, 0F, 0F, Spectrum.default)
        val DEF = Wave("Default", DEFAULT_WAVE_LENGTH, DEFAULT_VELOCITY, DEFAULT_INTENSITY, Spectrum.default)
        val SPECS: List<Wave> = Spectrum.values().map { DEF.copy(name = it.getName(), spectrum = it) }
        val PIXEL = DEF.copy(name = "Pixel", isPixel = true, isBlur = false)
        val LONG = DEF.copy(name = "Long", waveLength = DEFAULT_WAVE_LENGTH * PHI)
        val SHORT = DEF.copy(name = "Short", waveLength = DEFAULT_WAVE_LENGTH / PHI)
        val FAST = DEF.copy(name = "Fast", velocity = DEFAULT_VELOCITY * PHI)
        val SLOW = DEF.copy(name = "Slow", velocity = DEFAULT_VELOCITY / PHI)
        val INTENSE = DEF.copy(name = "Intense", intensity = DEFAULT_INTENSITY * PHI)
        val WEAK = DEF.copy(name = "Weak", intensity = DEFAULT_INTENSITY / PHI)
        val STANDING = DEF.copy(name = "Full", velocity = 0F)
        val MULTIPLY = DEF.copy(name = "Multiply", op = Operator.MULTIPLY)

        val default = DEF
        val noSpec = listOf(OFF, DEF, PIXEL, LONG, SHORT, FAST, SLOW, INTENSE, WEAK, STANDING, MULTIPLY)
        val all = noSpec + SPECS

        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Wave = all.find { it.name.equals(name) } ?: default
    }
}
