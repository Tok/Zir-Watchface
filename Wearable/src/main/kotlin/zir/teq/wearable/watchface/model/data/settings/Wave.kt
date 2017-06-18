package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.model.data.types.Operator
import zir.watchface.DrawUtil

data class Wave(val name: String, val waveNumber: Float,
                val velocity: Double, val intensity: Double,
                val spectrum: zir.teq.wearable.watchface.model.data.settings.Wave.Companion.Spectrum = DEF_SPEC,
                val isBlur: Boolean = true, val op: zir.teq.wearable.watchface.model.data.types.Operator = zir.teq.wearable.watchface.model.data.types.Operator.Companion.ADD,
                val resolution: zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution = DEF_RES) {
    val iconId: Int = zir.teq.wearable.watchface.R.drawable.icon_dummy //TODO replace
    companion object {
        private val DEFAULT_VELOCITY = -0.0001
        private val DEFAULT_INTENSITY = 7.0

        data class WaveResolution(val value: Int) { fun get() = value } //TODO seal
        val _1_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(1)
        val _2_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(2)
        val _4_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(4)
        val _5_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(5)
        val _8_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(8)
        val _10_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(10)
        val _16_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(16)
        val _20_ = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WaveResolution(20)
        val DEF_RES = zir.teq.wearable.watchface.model.data.settings.Wave.Companion._20_

        data class Spectrum(val name: String) //TODO seal
        val SPEC_FULL = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.Spectrum("Full")
        val SPEC_LINES = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.Spectrum("Lines")
        val SPEC_SPOOK = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.Spectrum("Spook")
        val SPEC_RAIN = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.Spectrum("Rain")
        val DEF_SPEC = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.SPEC_FULL

        val OFF = zir.teq.wearable.watchface.model.data.settings.Wave("Off", 1F, 0.0, 1.0, SPEC_FULL, false)
        val DEFAULT = zir.teq.wearable.watchface.model.data.settings.Wave("Default", DrawUtil.PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY)
        val NO_BLUR = zir.teq.wearable.watchface.model.data.settings.Wave("No Blur", DrawUtil.PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_FULL, false)
        val INTENSE = zir.teq.wearable.watchface.model.data.settings.Wave("Intense", DrawUtil.PHI, DEFAULT_VELOCITY, 10.0, SPEC_FULL, false)
        val WEAK = zir.teq.wearable.watchface.model.data.settings.Wave("Weak", DrawUtil.PHI, DEFAULT_VELOCITY, 3.0, SPEC_FULL, false)
        val FAST = zir.teq.wearable.watchface.model.data.settings.Wave("Fast", DrawUtil.PHI, DEFAULT_VELOCITY * 5, DEFAULT_INTENSITY)
        val STANDING = zir.teq.wearable.watchface.model.data.settings.Wave("Standing", DrawUtil.PHI, 0.0, DEFAULT_INTENSITY)
        val MULTIPLY = zir.teq.wearable.watchface.model.data.settings.Wave("Multiply", DrawUtil.PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_FULL, true, Operator.MULTIPLY)
        val LINES = zir.teq.wearable.watchface.model.data.settings.Wave("Lines", DrawUtil.PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_LINES, false)
        val SPOOK = zir.teq.wearable.watchface.model.data.settings.Wave("Spook", DrawUtil.PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_SPOOK)
        val RAIN = zir.teq.wearable.watchface.model.data.settings.Wave("Rain", DrawUtil.PHI, DEFAULT_VELOCITY, DEFAULT_INTENSITY, SPEC_RAIN)

        val default = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.NO_BLUR
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Wave.Companion.OFF, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.DEFAULT, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.NO_BLUR, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.INTENSE, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.WEAK, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.FAST, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.STANDING, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.MULTIPLY, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.LINES, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.SPOOK, zir.teq.wearable.watchface.model.data.settings.Wave.Companion.RAIN)
        fun options() = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.all.toCollection(ArrayList())
        fun getByName(name: String): zir.teq.wearable.watchface.model.data.settings.Wave = zir.teq.wearable.watchface.model.data.settings.Wave.Companion.all.find { it.name.equals(name) } ?: zir.teq.wearable.watchface.model.data.settings.Wave.Companion.default
    }
}
