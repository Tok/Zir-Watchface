package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

enum class WaveIntensity(override val label: String, override val value: Float) : Setting {
    CALMEST("Calmest", WaveIntensity.def / (PHI * PHI * PHI)),
    CALMER("Calmer", WaveIntensity.def / (PHI * PHI)),
    CALM("Calm", WaveIntensity.def / PHI),
    NORMAL("Normal", WaveIntensity.def),
    INTENSE("Intense", WaveIntensity.def * PHI),
    INTENSER("Intenser", WaveIntensity.def * (PHI * PHI)),
    INTENSEST("Intensest", WaveIntensity.def * (PHI * PHI * PHI));

    companion object : Config {
        private val def = 7F
        override val code = Item.WAVE_INTENSITY.code
        override val label = Zir.string(R.string.label_wave_intensity)
        override val pref = Zir.string(R.string.saved_wave_intensity)
        override val iconId = R.drawable.icon_wave_intensity
        override val default = NORMAL
        override val all = values().toList()
        override fun getByName(name: String) = values().find { it.name.equals(name) } ?: default
    }
}
