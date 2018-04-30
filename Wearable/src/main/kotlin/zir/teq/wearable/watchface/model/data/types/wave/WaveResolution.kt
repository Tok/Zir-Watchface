package zir.teq.wearable.watchface.model.data.types.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.ConfigItem


data class WaveResolution(override val name: String, val value: Int) : ConfigItem {
    override val configId = Item.WAVE_RESO.code

    companion object {
        val pref = Zir.string(R.string.saved_wave_resolution)
        val iconId = R.drawable.icon_wave_resolution

        val SUPER_LOW = WaveResolution("Super Low", 40)
        val LOWEST = WaveResolution("Lowest", 32)
        val LOWER = WaveResolution("Lower", 20)
        val LOW = WaveResolution("Low", 16)
        val NORMAL = WaveResolution("Normal", 12)
        val HIGH = WaveResolution("High", 10)
        val HIGHER = WaveResolution("Higher", 2)
        val HIGHEST = WaveResolution("Highest", 6)

        val ALL = listOf(SUPER_LOW, LOWEST, LOWER, LOW, NORMAL, HIGH, HIGHER, HIGHEST)

        val default = NORMAL
        fun getByName(name: String): WaveResolution = ALL.find { it.name.equals(name) } ?: default
    }
}
