package zir.teq.wearable.watchface.model.setting.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.WaveItem
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting
import zir.teq.wearable.watchface.util.CalcUtil.PHI

enum class WaveIntensity(override val label: String, override val value: Float) : Setting {
    CALMEST("Calmest", 7F / (PHI * PHI * PHI)),
    CALMER("Calmer", 7F / (PHI * PHI)),
    CALM("Calm", 7F / PHI),
    NORMAL("Normal", 7F),
    INTENSE("Intense", 7F * PHI),
    INTENSER("Intenser", 7F * (PHI * PHI)),
    INTENSEST("Intensest", 7F * (PHI * PHI * PHI));

    override val pref: String = name

    companion object : Config {
        override val code = WaveItem.WAVE_INTENSITY.code
        override val label = Zir.string(R.string.label_wave_intensity)
        override val pref = Zir.string(R.string.saved_wave_intensity)
        override val iconId = R.drawable.wave_icon_intensity
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
