package zir.teq.wearable.watchface.config.select.config

import android.app.Activity
import android.graphics.Typeface
import zir.teq.wearable.watchface.model.ConfigData
import java.util.concurrent.TimeUnit

open class Item(val type: Type, val pref: String, val name: String,
                val activity: Class<out Activity>) : ConfigData.ConfigItemType {
    override val configType: Int get() = type.code
    override fun toString() = name
    companion object {
        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        val FAST_UPDATE_RATE_MS = TimeUnit.MILLISECONDS.toMillis(20)
        val NORMAL_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1)
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(inMuteMode: Boolean) = if (inMuteMode) activeUpdateRateMs() else ambientUpdateRateMs()
        private fun ambientUpdateRateMs() = if (ConfigData.isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        private fun activeUpdateRateMs() = if (ConfigData.isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
    }
}
