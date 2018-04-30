package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.ComponentConfigItem

data class Spectrum(val name: String) : ComponentConfigItem {
    override val configId = Type.WAVE_SPECTRUM.code
    val pref = Zir.string(R.string.saved_spectrum)
    val iconId = R.drawable.icon_wave_spectrum
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
        val PALETTE = Spectrum("Palette")
        val BW = Spectrum("B&W")
        val LINES = Spectrum("Lines")
        val FULL = Spectrum("Full")
        val CHAOS = Spectrum("Chaos")
        val SPOOK = Spectrum("Spook")
        val RAIN = Spectrum("Rain")

        val default = BW
        val ALL = listOf(PALETTE, BW, LINES, FULL, CHAOS, SPOOK, RAIN)

        fun options() = ALL.toCollection(ArrayList())
        fun getByName(name: String): Spectrum = ALL.find { it.name.equals(name) } ?: default
    }
}
