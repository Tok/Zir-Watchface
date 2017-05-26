package zir.watchface

import android.graphics.Typeface
import java.util.concurrent.TimeUnit

data class Config(val drawCircle: Boolean,
                  val drawActiveCircles: Boolean, val drawHands: Boolean, val drawTriangle: Boolean,
                  val drawText: Boolean, val drawPoints: Boolean) {
    val isStayActive = false //TODO reimplement
    val isFastUpdate = false //TODO reimplement

    companion object {
        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        val FAST_UPDATE_RATE_MS = 20L
        val NORMAL_UPDATE_RATE_MS = 1000L
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        fun activeUpdateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS
    }
}
