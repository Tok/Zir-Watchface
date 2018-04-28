package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI


data class WaveFrequency(val name: String, val value: Float, override val configId: Int, val iconId: Int?) : ComponentConfigItem {
    val type = Type.WAVE_FREQUENCY

    companion object {
        private val defFreq = 1F / PHI //waves per units
        private val INIT = WaveFrequency("INIT", defFreq, R.string.saved_wave_frequency, R.drawable.icon_dummy)

        val LOWEST = INIT.copy(name = "Lowest", value = defFreq / (PHI * PHI * PHI))
        val LOWER = INIT.copy(name = "Lower", value = defFreq / (PHI * PHI))
        val LOW = INIT.copy(name = "Low", value = defFreq / PHI)
        val NORMAL = INIT.copy(name = "Normal")
        val HIGH = INIT.copy(name = "High", value = defFreq * PHI)
        val HIGHER = INIT.copy(name = "Higher", value = defFreq * (PHI * PHI))
        val HIGHEST = INIT.copy(name = "Highest", value = defFreq * (PHI * PHI * PHI))

        val ALL = listOf(LOWEST, LOWER, LOW, NORMAL, HIGH, HIGHER, HIGHEST)

        val default = NORMAL
        fun getByName(name: String): WaveFrequency = ALL.find { it.name.equals(name) } ?: default
    }
}
