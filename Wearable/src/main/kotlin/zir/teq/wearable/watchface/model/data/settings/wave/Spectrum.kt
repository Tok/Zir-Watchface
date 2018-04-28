package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Spectrum(val name: String) {
    val iconId: Int = R.drawable.icon_dummy //TODO replace
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
        val DARK = Spectrum("Dark")
        val DARK_WAVE = Spectrum("Dark Spectrum")
        val LINES = Spectrum("Lines")
        val FULL = Spectrum("Full")
        val SPOOK = Spectrum("Spook")
        val RAIN = Spectrum("Rain")

        val default = DARK
        val ALL = listOf(PALETTE, BW, DARK, DARK_WAVE, LINES, FULL, SPOOK, RAIN)

        fun options() = ALL.toCollection(ArrayList())
        fun getByName(name: String): Spectrum = ALL.find { it.name.equals(name) } ?: default
    }
}
