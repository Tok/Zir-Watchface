package zir.watchface

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import zir.teq.wearable.watchface.Col
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Stroke
import zir.teq.wearable.watchface.Theme
import java.util.concurrent.TimeUnit

data class Config(val drawCircle: Boolean,
                  val drawActiveCircles: Boolean, val drawHands: Boolean, val drawTriangle: Boolean,
                  val drawText: Boolean, val drawPoints: Boolean) {
    enum class PaintType {
        TEXT, HAND, HAND_AMB, SHAPE, SHAPE_AMB, CIRCLE, CIRCLE_AMB, POINT
    }

    val isStayActive = false //TODO reimplement
    val isFastUpdate = false //TODO reimplement
    val yOffset: Float = 98F
    var xOffset: Float = 0.toFloat()

    companion object {
        val PHI = 1.618033988F
        val TAG = "Config"

        val FAST_UPDATE_RATE_MS = 20L
        val NORMAL_UPDATE_RATE_MS = 1000L
        val MUTE_UPDATE_RATE_MS = TimeUnit.MINUTES.toMillis(1)
        fun updateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) FAST_UPDATE_RATE_MS else NORMAL_UPDATE_RATE_MS
        fun activeUpdateRateMs(isFastUpdate: Boolean) = if (isFastUpdate) NORMAL_UPDATE_RATE_MS else MUTE_UPDATE_RATE_MS

        val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        val MONO_TYPEFACE = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)



    }
}
