package zir.teq.wearable.watchface.util

import android.graphics.Point
import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.util.DrawUtil.Companion.TAU

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
object WaveCalc {
    fun calc(from: Point, to: PointF, t: Float, frequency: Float, mass: Float) =
            calc(from.x.toFloat(), from.y.toFloat(), to.x, to.y, t, frequency, mass)

    fun calc(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float, t: Float,
             frequency: Float, mass: Float): Complex {
        val distance = calcDistance(xFrom, yFrom, xTo, yTo)
        val phase = (t + (frequency * distance / mass)) % TAU
        val mag = calcIntensity(distance, xFrom, yFrom)
        return Complex.valueOf(mag, phase)
    }

    private fun addSquares(first: Float, second: Float): Float = (first * first) + (second * second)
    private fun calcIntensity(distance: Float, x: Float, y: Float): Float =
            minOf(1.0, ConfigData.wave.intensity * (x + y) /
                    (Math.pow(distance.toDouble(), 2.0) * TAU)).toFloat()

    private fun calcDistance(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float): Float =
            Math.sqrt(addSquares(xFrom - xTo, yFrom - yTo).toDouble()).toFloat()
}
