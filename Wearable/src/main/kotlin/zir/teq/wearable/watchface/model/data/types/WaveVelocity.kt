package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI


data class WaveVelocity(val name: String, val value: Float, override val configId: Int, val iconId: Int?) : ComponentConfigItem {
    val type = Type.WAVE_VELOCITY

    companion object {
        private val defVelo = -1F / PHI //cycles per second (negative out, positive in)
        private val INIT = WaveVelocity("INIT", defVelo, R.string.saved_wave_velocity, R.drawable.icon_dummy)

        val SLOWEST = INIT.copy(name = "Slowest", value = defVelo / (PHI * PHI * PHI))
        val SLOWER = INIT.copy(name = "Slower", value = defVelo / (PHI * PHI))
        val SLOW = INIT.copy(name = "Slow", value = defVelo / PHI)
        val NORMAL = INIT.copy(name = "Normal")
        val FAST = INIT.copy(name = "Fast", value = defVelo * PHI)
        val FASTER = INIT.copy(name = "Faster", value = defVelo * (PHI * PHI))
        val FASTEST = INIT.copy(name = "Fastest", value = defVelo * (PHI * PHI * PHI))

        val ALL = listOf(SLOWEST, SLOWER, SLOW, NORMAL, FAST, FASTER, FASTEST)

        val default = NORMAL
        fun getByName(name: String): WaveVelocity = ALL.find { it.name.equals(name) } ?: default
    }
}
