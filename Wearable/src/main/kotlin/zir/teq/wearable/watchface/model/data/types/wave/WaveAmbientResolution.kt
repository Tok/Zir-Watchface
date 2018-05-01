package zir.teq.wearable.watchface.model.data.types.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item


enum class WaveAmbientResolution(override val label: String, override val value: Float) : Setting {
    SUPER_LOW("Super Low", 40F),
    LOWEST("Lowest", 32F),
    LOWER("Lower", 20F),
    LOW("Low", 16F),
    NORMAL("Normal", 12F),
    HIGH("High", 10F),
    HIGHER("Higher", 5F),
    HIGHEST("Highest", 2F);

    companion object : Config {
        override val code = Item.WAVE_AMB_RESO.code
        override val label = Zir.string(R.string.label_wave_ambient_resolution)
        override val pref = Zir.string(R.string.saved_wave_ambient_resolution)
        override val iconId = R.drawable.icon_wave_ambient_resolution
        override val default = NORMAL
        override val all = values().toList()
        override fun getByName(name: String) = values().find { it.name.equals(name) } ?: default
    }
}
