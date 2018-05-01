package zir.teq.wearable.watchface.model.setting.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting


enum class WaveSpectrum(override val label: String, override val value: Float) : Setting {
    PALETTE("Palette", 0F),
    BW("B&W", 0F),
    LINES("Lines", 0F),
    FULL("Full", 0F),
    CHAOS("Chaos", 0F),
    SPOOK("Spook", 0F),
    RAIN("Rain", 0F);

    override val pref: String = name

    companion object : Config {
        override val code = Item.WAVE_SPECTRUM.code
        override val label = Zir.string(R.string.label_spectrum)
        override val pref = Zir.string(R.string.saved_spectrum)
        override val iconId = R.drawable.icon_wave_spectrum
        override val default = BW
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
