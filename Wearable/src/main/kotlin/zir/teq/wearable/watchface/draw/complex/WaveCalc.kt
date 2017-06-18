package zir.teq.wearable.watchface.draw.complex

import zir.teq.wearable.watchface.draw.complex.data.Operator
import zir.watchface.DrawUtil.Companion.PHI
import zir.watchface.DrawUtil.Companion.TAU

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
object WaveCalc {
    fun calc(xFrom: Double, yFrom: Double, xTo: Double, yTo: Double, t: Double): Complex {
        val distance = calcDistance(xFrom, yFrom, xTo, yTo)
        val phase = (distance + t) * DEFAULT_WAVE_NUMBER
        val mag = calcIntensity(distance, xFrom, yFrom)
        return Complex.fromMagnitudeAndPhase(mag, phase)
    }

    private fun addSquares(first: Double, second: Double) = first * first + second * second
    private fun calcIntensity(distance: Double, x: Double, y: Double) =
            minOf(1.0, INTENSITY_MULTIPLIER * (x + y) / (Math.pow(distance, 2.0) * TAU))
    private fun calcDistance(xFrom: Double, yFrom: Double, xTo: Double, yTo: Double) =
            Math.sqrt(addSquares((xFrom - xTo), (yFrom - yTo)))

    val OP = Operator.ADD //TODO
    val RESOLUTION = 20 //one of 1, 2, 4, 5, 8, 10, 16, 20
    val DEFAULT_WAVE_NUMBER = PHI
    val DEFAULT_VELOCITY = -0.0005
    private val INTENSITY_MULTIPLIER = 7.0
}
