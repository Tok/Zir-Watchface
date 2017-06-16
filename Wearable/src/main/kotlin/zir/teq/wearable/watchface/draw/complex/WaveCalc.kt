package zir.teq.wearable.watchface.draw.complex

import zir.watchface.DrawUtil.Companion.HALF
import zir.watchface.DrawUtil.Companion.H_BAR
import zir.watchface.DrawUtil.Companion.MASS
import zir.watchface.DrawUtil.Companion.TAU

/**
 * Recreated by Zir on 16.06.2017.
 * Transpiled and rearranged from: https://github.com/Tok/Erwin/tree/master/src/main/java/erwin
 */
class WaveCalc(private val centerX: Int, private val centerY: Int, private val useMagnitude: Boolean) {
    fun calculateWave(x: Int, y: Int, t: Double, waveNumber: Double, op: Operator): Complex {
        val phase = H_BAR * H_BAR * Math.PI * Math.PI *
                addSquares((x - centerX).toDouble(), (y - centerY).toDouble()) /
                (2.0 * MASS * waveNumber * waveNumber)
        val mag = if (useMagnitude) calculateIntensity(calculateDistanceToCenter(x, y)) else 1.0
        val halfPhase = HALF * phase
        return Complex.fromMagnitudeAndPhase(mag, if (Operator.ADD.equals(op)) halfPhase - t else halfPhase * t)
    }

    fun calculateDual(x: Int, y: Int, centerX: Int, centerY: Int,
                      t: Double, waveNumber: Double): Complex {
        val distanceToCenter = calculateDistanceToCenter(x, y, centerX, centerY)
        val phase = (distanceToCenter - t) * waveNumber
        val mag = if (useMagnitude) calculateIntensity(calculateDistanceToCenter(x, y)) else 1.0
        return Complex.fromMagnitudeAndPhase(mag, phase)
    }

    fun calculateMandala(x: Int, y: Int, dt: Double, whatever: Double,
                         op: Operator, old: Complex): Complex {
        val distanceToCenter = calculateDistanceToCenter(
                (x.toDouble() / whatever).toInt(),
                (y.toDouble() / whatever).toInt())
        val oldPhase = if (old.phase != 0.0) old.phase else 1.0
        val dph = distanceToCenter * dt * 0.1
        val phase = if (op.equals(Operator.ADD)) oldPhase + dph else oldPhase * dph
        val mag = if (useMagnitude) calculateIntensity(calculateDistanceToCenter(x, y)) else 1.0
        return Complex.fromMagnitudeAndPhase(if (old.phase != 0.0) mag else 0.0, phase)
    }

    private fun addSquares(first: Double, second: Double) = first * first + second * second
    private fun calculateIntensity(distance: Double) =
            minOf(INTENSITY_MULTIPLIER * (centerX + centerY) / (Math.pow(distance, 2.0) * TAU), 1.0)
    private fun calculateDistanceToCenter(x: Int, y: Int, cX: Int = centerX, cY: Int = centerY) =
            Math.sqrt(addSquares((x - cX).toDouble(), (y - cY).toDouble()))

    companion object {
        private val INTENSITY_MULTIPLIER = 10.0
    }
}
