package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI


data class WaveFrequency(val name: String, val value: Float) : ComponentConfigItem {
    override val configId = Type.WAVE_FREQUENCY.code
    val pref = Zir.string(R.string.saved_wave_frequency)
    val iconId = R.drawable.icon_wave_frequency

    companion object {
        private val defFreq = 1F / PHI //waves per units

        val LOWEST = WaveFrequency("Lowest", defFreq / (PHI * PHI * PHI))
        val LOWER = WaveFrequency("Lower", defFreq / (PHI * PHI))
        val LOW = WaveFrequency("Low", defFreq / PHI)
        val NORMAL = WaveFrequency("Normal", defFreq)
        val HIGH = WaveFrequency("High", defFreq * PHI)
        val HIGHER = WaveFrequency("Higher", defFreq * (PHI * PHI))
        val HIGHEST = WaveFrequency("Highest", defFreq * (PHI * PHI * PHI))

        val ALL = listOf(LOWEST, LOWER, LOW, NORMAL, HIGH, HIGHER, HIGHEST)

        val default = NORMAL
        fun getByName(name: String): WaveFrequency = ALL.find { it.name.equals(name) } ?: default
    }
}
