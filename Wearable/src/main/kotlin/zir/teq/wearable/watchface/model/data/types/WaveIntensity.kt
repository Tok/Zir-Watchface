package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI


data class WaveIntensity(val name: String, val value: Float, override val configId: Int, val iconId: Int?) : ComponentConfigItem {
    val type = Type.WAVE_INTENSITY

    companion object {
        private val defInten = 7F
        private val INIT = WaveIntensity("INIT", defInten, R.string.saved_wave_intensity, R.drawable.icon_dummy)

        val CALMEST = INIT.copy(name = "Calmest", value = defInten / (PHI * PHI * PHI))
        val CALMER = INIT.copy(name = "Calmer", value = defInten / (PHI * PHI))
        val CALM = INIT.copy(name = "Calm", value = defInten / PHI)
        val NORMAL = INIT.copy(name = "Normal")
        val INTENSE = INIT.copy(name = "Intense", value = defInten * PHI)
        val INTENSER = INIT.copy(name = "Intenser", value = defInten * (PHI * PHI))
        val INTENSEST = INIT.copy(name = "Intensest", value = defInten * (PHI * PHI * PHI))

        val ALL = listOf(CALMEST, CALMER, CALM, NORMAL, INTENSE, INTENSER, INTENSEST)

        val default = NORMAL
        fun getByName(name: String): WaveIntensity = ALL.find { it.name.equals(name) } ?: default
    }
}
