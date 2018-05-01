package zir.teq.wearable.watchface.model.setting.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

enum class WaveResolution(override val label: String, override val value: Float) : Setting {
    SUPER_LOW("Super Low", 40F),
    LOWEST("Lowest", 32F),
    LOWER("Lower", 20F),
    LOW("Low", 16F),
    NORMAL("Normal", 12F),
    HIGH("High", 10F),
    HIGHER("Higher", 5F),
    HIGHEST("Highest", 2F);

    override val pref: String = name

    companion object : Config {
        override val code = Item.WAVE_RESO.code
        override val label = Zir.string(R.string.label_wave_resolution)
        override val pref = Zir.string(R.string.saved_wave_resolution)
        override val iconId = R.drawable.icon_wave_resolution
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
