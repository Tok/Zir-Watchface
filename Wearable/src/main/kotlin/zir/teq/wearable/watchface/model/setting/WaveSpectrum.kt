package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.model.ConfigData


enum class WaveSpectrum(override val label: String, override val value: Float) : Setting {
    PALETTE("Palette", 0F),
    BW("B&W", 0F),
    LINES("Lines", 0F),
    FULL("Full", 0F),
    CHAOS("Chaos", 0F),
    SPOOK("Spook", 0F),
    RAIN("Rain", 0F);

    val hasCenter = false //TODO tune (performance)
    val hasHours = true
    val hasMinutes = true
    val hasSeconds = true

    fun velocity() = when {
        ConfigData.waveIsStanding() -> 0F
        ConfigData.waveIsInward() -> WaveVelocity.load().value * -1
        else -> WaveVelocity.load().value
    }

    companion object : Config {
        override val code = Item.WAVE_SPECTRUM.code
        override val label = Zir.string(R.string.label_spectrum)
        override val pref = Zir.string(R.string.saved_spectrum)
        override val iconId = R.drawable.icon_wave_spectrum
        override val default = BW
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