package zir.teq.wearable.watchface.model.setting.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.types.WaveItem
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

enum class WaveVelocity(override val label: String, override val value: Float) : Setting {
    SLOWEST("Slowest", -1F / (PHI * PHI * PHI)),
    SLOWER("Slower", -1F / (PHI * PHI)),
    SLOW("Slow", -1F / PHI),
    NORMAL("Normal", -1F),
    FAST("Fast", -1F * PHI),
    FASTER("Faster", -1F * (PHI * PHI)),
    FULL_SPEED("Full Speed", -1F * (PHI * PHI * PHI)),
    SUPER_SPEED("Super Speed", -1F * (PHI * PHI * PHI * PHI)),
    MEGA_SPEED("Mega Speed", -1F * (PHI * PHI * PHI * PHI * PHI)),
    GIGA_SPEED("Giga Speed", -1F * (PHI * PHI * PHI * PHI * PHI * PHI));

    override val pref: String = name

    companion object : Config {
        override val code = WaveItem.WAVE_VELOCITY.code
        override val label = Zir.string(R.string.label_wave_velocity)
        override val pref = Zir.string(R.string.saved_wave_velocity)
        override val iconId = R.drawable.wave_icon_velocity
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
