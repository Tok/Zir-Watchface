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
        val DARK_PALETTE = Spectrum("Dark Palette")
        val DARKER_PALETTE = Spectrum("Darker Palette")
        val BW = Spectrum("B&W")
        val DARK_BW = Spectrum("Dark B&W")
        val DARKER_BW = Spectrum("Darker B&W")
        val LINES = Spectrum("Lines")
        val DARK_LINES = Spectrum("Dark Lines")
        val DARKER_LINES = Spectrum("Darker Lines")
        val FULL = Spectrum("Full")
        val DARK_FULL = Spectrum("Dark Full")
        val DARKER_FULL = Spectrum("Darker Full")
        val CHAOS = Spectrum("Chaos")
        val DARK_CHAOS = Spectrum("Dark Chaos")
        val DARKER_CHAOS = Spectrum("Darker Chaos")
        val SPOOK = Spectrum("Spook")
        val RAIN = Spectrum("Rain")

        val default = DARK_BW
        val ALL = listOf(
                PALETTE, DARK_PALETTE, DARKER_PALETTE,
                BW, DARK_BW, DARKER_BW,
                LINES, DARK_LINES, DARKER_LINES,
                FULL, DARK_FULL, DARKER_FULL,
                CHAOS, DARK_CHAOS, DARKER_CHAOS,
                SPOOK, RAIN)

        fun options() = ALL.toCollection(ArrayList())
        fun getByName(name: String): Spectrum = ALL.find { it.name.equals(name) } ?: default
    }
}
