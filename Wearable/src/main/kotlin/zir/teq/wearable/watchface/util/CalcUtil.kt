package zir.teq.wearable.watchface.util

import android.graphics.Canvas
import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.style.StyleStroke
import zir.teq.wearable.watchface.model.setting.wave.WaveVelocity

object CalcUtil {
    val PHI = 1.618033988F
    val PI = Math.PI.toFloat()
    val TAU = PI * 2F
    val ONE_MINUTE_AS_RAD = PI / 30F
    val HALF_MINUTE_AS_RAD = ONE_MINUTE_AS_RAD / 2F

    fun velocity() = when {
        ConfigData.waveIsStanding() -> 0F
        ConfigData.waveIsInward() -> WaveVelocity.load().value * -1
        else -> WaveVelocity.load().value
    }

    fun calcDistFromBorder(can: Canvas, stroke: StyleStroke) = calcDistFromBorder(can.height, stroke.value)
    fun calcDistFromBorder(height: Int, dim: Float): Float {
        val assertedOutlineDimension = 8 //TODO use exact?
        val totalSetoff = 4F * (dim + assertedOutlineDimension)
        return height / (height + totalSetoff)
    }

    fun calcPosition(rot: Float, length: Float, centerOffset: Float): PointF {
        val x = centerOffset + (Math.sin(rot.toDouble()) * length).toFloat()
        val y = centerOffset + (-Math.cos(rot.toDouble()) * length).toFloat()
        return PointF(x, y)
    }

    fun calcCircumcenter(a: PointF, b: PointF, c: PointF): PointF {
        val dA = a.x * a.x + a.y * a.y
        val dB = b.x * b.x + b.y * b.y
        val dC = c.x * c.x + c.y * c.y
        val divisor = 2F * (a.x * (c.y - b.y) + b.x * (a.y - c.y) + c.x * (b.y - a.y))
        val x = (dA * (c.y - b.y) + dB * (a.y - c.y) + dC * (b.y - a.y)) / divisor
        val y = -(dA * (c.x - b.x) + dB * (a.x - c.x) + dC * (b.x - a.x)) / divisor
        return PointF(x, y)
    }

    fun calcDistance(a: PointF, b: PointF): Float {
        val p = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)
        return Math.sqrt(p.toDouble()).toFloat()
    }
}
