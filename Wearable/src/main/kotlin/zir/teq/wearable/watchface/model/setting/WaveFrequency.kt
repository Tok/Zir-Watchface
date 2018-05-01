package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

enum class WaveFrequency(override val label: String, override val value: Float) : Setting {
    LOWEST("Lowest", WaveFrequency.def / (PHI * PHI * PHI)),
    LOWER("Lower", WaveFrequency.def / (PHI * PHI)),
    LOW("Low", WaveFrequency.def / PHI),
    NORMAL("Normal", WaveFrequency.def),
    HIGH("High", WaveFrequency.def * PHI),
    HIGHER("Higher", WaveFrequency.def * (PHI * PHI)),
    HIGHEST("Highest", WaveFrequency.def * (PHI * PHI * PHI));

    companion object : Config {
        private val def = 1F / PHI
        override val code = Item.WAVE_FREQUENCY.code
        override val label = Zir.string(R.string.label_wave_frequency)
        override val pref = Zir.string(R.string.saved_wave_frequency)
        override val iconId = R.drawable.icon_wave_frequency
        override val default = NORMAL
        override val all = values().toList()
        override fun getByName(name: String) = values().find { it.name.equals(name) } ?: default
        override fun load() = getByName(ConfigData.prefs.getString(pref, default.name))
        override fun save(setting: Setting) {
            val editor = ConfigData.prefs.edit()
            editor.putString(pref, setting.name)
            editor.apply()
        }
    }
}
