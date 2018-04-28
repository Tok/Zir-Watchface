package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Wave(val name: String, val spectrum: Spectrum) {
    val iconId: Int = R.drawable.icon_dummy //TODO replace
    val isOff = name.equals("Off")
    val isOn = !isOff
    val hasCenter = false //TODO tune (performance)
    val hasHours = true
    val hasMinutes = true
    val hasSeconds = true
    fun velocity() = if (ConfigData.waveIsStanding()) 0F else ConfigData.waveVelocity().value

    companion object {
        val OFF = Wave("Off", Spectrum.default)
        private val DEF = Wave("Default", Spectrum.default)
        private val SPECS: List<Wave> = Spectrum.values().map {
            DEF.copy(it.getName(), spectrum = it)
        }

        val default = DEF
        val ALL = listOf(OFF) + listOf(DEF) + SPECS

        fun options() = ALL.toCollection(ArrayList())
        fun getByName(name: String): Wave = ALL.find { it.name.equals(name) } ?: default
    }
}
