package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type


data class WaveAmbientResolution(val name: String, val value: Int) : ComponentConfigItem {
    override val configId = Type.WAVE_AMB_RESO.code
    val pref = Zir.string(R.string.saved_wave_ambient_resolution)
    val iconId = R.drawable.icon_wave_ambient_resolution

    companion object {
        val SUPER_LOW = WaveAmbientResolution("Super Low", 40)
        val LOWEST = WaveAmbientResolution("Lowest", 32)
        val LOWER = WaveAmbientResolution("Lower", 20)
        val LOW = WaveAmbientResolution("Low", 16)
        val NORMAL = WaveAmbientResolution("Normal", 12)
        val HIGH = WaveAmbientResolution("High", 10)
        val HIGHER = WaveAmbientResolution("Higher", 2)
        val HIGHEST = WaveAmbientResolution("Highest", 6)
        val SUPER_HIGH = WaveAmbientResolution("Super High", 5)
        val MEGA_HIGH = WaveAmbientResolution("Mega High", 4)
        val GIGA_HIGH = WaveAmbientResolution("Giga High", 2)

        val ALL = listOf(SUPER_LOW, LOWEST, LOWER, LOW, NORMAL,
                HIGH, HIGHER, HIGHEST, SUPER_HIGH, MEGA_HIGH, GIGA_HIGH)

        val default = NORMAL
        fun getByName(name: String): WaveAmbientResolution = ALL.find { it.name.equals(name) }
                ?: default
    }
}
