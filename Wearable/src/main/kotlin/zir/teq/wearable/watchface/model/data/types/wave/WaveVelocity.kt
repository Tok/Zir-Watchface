package zir.teq.wearable.watchface.model.data.types.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Type
import zir.teq.wearable.watchface.config.general.ConfigItem
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI


data class WaveVelocity(override val name: String, val value: Float) : ConfigItem {
    override val configId = Type.WAVE_VELOCITY.code

    companion object {
        val pref = Zir.string(R.string.saved_wave_velocity)
        val iconId = R.drawable.icon_wave_velocity

        private val defVelo = -1F / PHI //cycles per second (negative out, positive in)
        val SLOWEST = WaveVelocity("Slowest", defVelo / (PHI * PHI * PHI))
        val SLOWER = WaveVelocity("Slower", defVelo / (PHI * PHI))
        val SLOW = WaveVelocity("Slow", defVelo / PHI)
        val NORMAL = WaveVelocity("Normal", defVelo)
        val FAST = WaveVelocity("Fast", defVelo * PHI)
        val FASTER = WaveVelocity("Faster", defVelo * (PHI * PHI))
        val FULL_SPEED = WaveVelocity("Full Speed", defVelo * (PHI * PHI * PHI))
        val SUPER_SPEED = WaveVelocity("Super Speed", defVelo * (PHI * PHI * PHI * PHI))
        val MEGA_SPEED = WaveVelocity("Mega Speed", defVelo * (PHI * PHI * PHI * PHI * PHI))
        val GIGA_SPEED = WaveVelocity("Giga Speed", defVelo * (PHI * PHI * PHI * PHI * PHI * PHI))

        val ALL = listOf(SLOWEST, SLOWER, SLOW, NORMAL, FAST, FASTER, FULL_SPEED,
                SUPER_SPEED, MEGA_SPEED, GIGA_SPEED)

        val default = NORMAL
        fun getByName(name: String): WaveVelocity = ALL.find { it.name.equals(name) } ?: default
    }
}
