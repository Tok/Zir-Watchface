package zir.teq.wearable.watchface.model.data.types.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.model.data.types.ComponentConfigItem
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI


data class WaveIntensity(val name: String, val value: Float) : ComponentConfigItem {
    override val configId = Type.WAVE_INTENSITY.code
    val pref = Zir.string(R.string.saved_wave_intensity)
    val iconId = R.drawable.icon_wave_intensity

    companion object {
        private val defInten = 7F

        val CALMEST = WaveIntensity("Calmest", defInten / (PHI * PHI * PHI))
        val CALMER = WaveIntensity("Calmer", defInten / (PHI * PHI))
        val CALM = WaveIntensity("Calm", defInten / PHI)
        val NORMAL = WaveIntensity("Normal", defInten)
        val INTENSE = WaveIntensity("Intense", defInten * PHI)
        val INTENSER = WaveIntensity("Intenser", defInten * (PHI * PHI))
        val INTENSEST = WaveIntensity("Intensest", defInten * (PHI * PHI * PHI))

        val ALL = listOf(CALMEST, CALMER, CALM, NORMAL, INTENSE, INTENSER, INTENSEST)

        val default = NORMAL
        fun getByName(name: String): WaveIntensity = ALL.find { it.name.equals(name) } ?: default
    }
}
