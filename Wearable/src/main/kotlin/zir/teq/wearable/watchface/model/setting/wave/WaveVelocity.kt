package zir.teq.wearable.watchface.model.setting.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Setting
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

enum class WaveVelocity(override val label: String, override val value: Float) : Setting {
    SLOWEST("Slowest", WaveVelocity.def / (PHI * PHI * PHI)),
    SLOWER("Slower", WaveVelocity.def / (PHI * PHI)),
    SLOW("Slow", WaveVelocity.def / PHI),
    NORMAL("Normal", WaveVelocity.def),
    FAST("Fast", WaveVelocity.def * PHI),
    FASTER("Faster", WaveVelocity.def * (PHI * PHI)),
    FULL_SPEED("Full Speed", WaveVelocity.def * (PHI * PHI * PHI)),
    SUPER_SPEED("Super Speed", WaveVelocity.def * (PHI * PHI * PHI * PHI)),
    MEGA_SPEED("Mega Speed", WaveVelocity.def * (PHI * PHI * PHI * PHI * PHI)),
    GIGA_SPEED("Giga Speed", WaveVelocity.def * (PHI * PHI * PHI * PHI * PHI * PHI));

    companion object : Config {
        private val def = -1F
        override val code = Item.WAVE_VELOCITY.code
        override val label = Zir.string(R.string.label_wave_velocity)
        override val pref = Zir.string(R.string.saved_wave_velocity)
        override val iconId = R.drawable.icon_wave_velocity
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
