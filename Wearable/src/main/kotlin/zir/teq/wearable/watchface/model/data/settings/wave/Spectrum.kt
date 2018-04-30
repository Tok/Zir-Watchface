package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.config.general.ConfigItem

data class Spectrum(override val name: String) : ConfigItem {
    override val configId = Type.WAVE_SPECTRUM.code

    val hasCenter = false //TODO tune (performance)
    val hasHours = true
    val hasMinutes = true
    val hasSeconds = true

    fun velocity() = when {
        ConfigData.waveIsStanding() -> 0F
        ConfigData.waveIsInward() -> ConfigData.waveVelocity().value * -1
        else -> ConfigData.waveVelocity().value
    }

    companion object {
        val pref = Zir.string(R.string.saved_spectrum)
        val iconId = R.drawable.icon_wave_spectrum

        val PALETTE = Spectrum("Palette")
        val BW = Spectrum("B&W")
        val LINES = Spectrum("Lines")
        val FULL = Spectrum("Full")
        val CHAOS = Spectrum("Chaos")
        val SPOOK = Spectrum("Spook")
        val RAIN = Spectrum("Rain")

        val default = BW
        val ALL = listOf(PALETTE, BW, LINES, FULL, CHAOS, SPOOK, RAIN)

        fun getByName(name: String): Spectrum = ALL.find { it.name.equals(name) } ?: default
    }
}
