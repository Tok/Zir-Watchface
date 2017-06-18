package zir.teq.wearable.watchface.util

import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Wave.Companion.Mass
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.watchface.DrawUtil.Companion.TAU

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
object WaveCalc {
    fun calc(xFrom: Double, yFrom: Double, xTo: Double, yTo: Double, t: Double, mass: Mass): Complex {
        val distance = calcDistance(xFrom, yFrom, xTo, yTo)
        val phase = (distance + t) * ConfigData.wave.waveNumber / mass.value
        val mag = calcIntensity(distance, xFrom, yFrom)
        return Complex.fromMagnitudeAndPhase(mag, phase)
    }

    private fun addSquares(first: Double, second: Double) = first * first + second * second
    private fun calcIntensity(distance: Double, x: Double, y: Double) =
            minOf(1.0, ConfigData.wave.intensity * (x + y) / (Math.pow(distance, 2.0) * TAU))
    private fun calcDistance(xFrom: Double, yFrom: Double, xTo: Double, yTo: Double) =
            Math.sqrt(addSquares((xFrom - xTo), (yFrom - yTo)))
}
