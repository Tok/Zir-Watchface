package zir.teq.wearable.watchface.model.setting.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

enum class WaveFrequency(override val label: String, override val value: Float) : Setting {
    LOWEST("Lowest", (1F / PHI) / (PHI * PHI * PHI)),
    LOWER("Lower", (1F / PHI) / (PHI * PHI)),
    LOW("Low", (1F / PHI) / PHI),
    NORMAL("Normal", (1F / PHI)),
    HIGH("High", (1F / PHI) * PHI),
    HIGHER("Higher", (1F / PHI) * (PHI * PHI)),
    HIGHEST("Highest", (1F / PHI) * (PHI * PHI * PHI));

    override val pref: String = name

    companion object : Config {
        override val code = Item.WAVE_FREQUENCY.code
        override val label = Zir.string(R.string.label_wave_frequency)
        override val pref = Zir.string(R.string.saved_wave_frequency)
        override val iconId = R.drawable.icon_wave_frequency
        override val default = NORMAL
        override val all = values().toList()
        private fun valueOf(pref: String): Setting = values().find { it.pref.equals(pref) }
                ?: default

        override fun index() = all.indexOfFirst { it.equals(load()) }
        override fun load() = valueOf(ConfigData.prefs.getString(pref, default.pref))
        override fun save(setting: Setting) {
            val editor = ConfigData.prefs.edit()
            editor.putString(pref, setting.pref)
            editor.apply()
        }
    }
}
