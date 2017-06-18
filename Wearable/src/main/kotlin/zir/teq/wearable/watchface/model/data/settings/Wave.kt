package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.model.data.types.Operator
import zir.watchface.DrawUtil.Companion.PHI

data class Wave(val name: String, val waveNumber: Float,
                val velocity: Double, val intensity: Double,
                val spectrum: Spectrum = DEF_SPEC,
                val isBlur: Boolean = true, val op: Operator = Operator.ADD,
                val resolution: WaveResolution = DEF_RES) {
    val iconId: Int = zir.teq.wearable.watchface.R.drawable.icon_dummy //TODO replace
    companion object {
        private val DEFAULT_VELOCITY = -0.0001
        private val DEFAULT_INTENSITY = 7.0

        data class WaveResolution(val value: Int) { fun get() = value } //TODO seal
        val _1_ = WaveResolution(1)
        val _2_ = WaveResolution(2)
        val _4_ = WaveResolution(4)
        val _5_ = WaveResolution(5)
        val _8_ = WaveResolution(8)
        val _10_ = WaveResolution(10)
        val _16_ = WaveResolution(16)
        val _20_ = WaveResolution(20)
        val DEF_RES = _20_

        data class Spectrum(val name: String) //TODO seal
        val SPEC_FULL = Spectrum("Full")
        val SPEC_LINES = Spectrum("Lines")
        val SPEC_SPOOK = Spectrum("Spook")
        val SPEC_RAIN = Spectrum("Rain")
        val DEF_SPEC = SPEC_FULL

        data class Mass(val name: String, val value: Double) //TODO seal
        val MASS_DEFAULT = Mass("Default", 1.0)
        val MASS_LIGHT = Mass("Light", 1.0 / PHI.toDouble())
        val MASS_LIGHTER = Mass("Lighter", 1.0 / (PHI.toDouble() * PHI.toDouble()))
        val MASS_HEAVY = Mass("Heavy", PHI.toDouble())
        val MASS_HEAVIER = Mass("Heavier", PHI.toDouble() * PHI.toDouble())
        val DEF_MASS = MASS_DEFAULT
        
        val OFF = Wave("Off", 1F, 0.0, 1.0, SPEC_FULL, false)
        val DEFAULT = Wave("Default", PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY)
        val NO_BLUR = Wave("No Blur", PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_FULL, false)
        val INTENSE = Wave("Intense", PHI, DEFAULT_VELOCITY, 10.0, SPEC_FULL, false)
        val WEAK = Wave("Weak", PHI, DEFAULT_VELOCITY, 3.0, SPEC_FULL, false)
        val FAST = Wave("Fast", PHI, DEFAULT_VELOCITY * 5, DEFAULT_INTENSITY)
        val STANDING = Wave("Standing", PHI, 0.0, DEFAULT_INTENSITY)
        val MULTIPLY = Wave("Multiply", PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_FULL, true, Operator.MULTIPLY)
        val LINES = Wave("Lines", PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_LINES, false)
        val SPOOK = Wave("Spook", PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_SPOOK)
        val RAIN = Wave("Rain", PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_RAIN)

        val default = NO_BLUR
        val all = listOf(OFF, DEFAULT, NO_BLUR, INTENSE, WEAK, FAST, STANDING, MULTIPLY, LINES, SPOOK, RAIN)
        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Wave = all.find { it.name.equals(name) } ?: default
    }
}
