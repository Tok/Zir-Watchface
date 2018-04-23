package zir.teq.wearable.watchface.model.data.settings.wave

import android.graphics.Point
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveWaveFrameData
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.teq.wearable.watchface.util.WaveCalc

class Layer private constructor(val wave: Wave, val center: Complex?, val hour: Complex?, val min: Complex?, val sec: Complex?) {
    fun all(): List<Complex> = listOfNotNull(center, hour, min, sec)
    fun get(): Complex {
        val hasNoValue = center == null && hour == null && min == null && sec == null
        if (hasNoValue) throw IllegalStateException("No values provided.")
        return when (wave.op) {
            Operator.MULTIPLY -> Complex.multiplyAll(all())
            Operator.ADD -> Complex.addAll(all())
            else -> throw IllegalArgumentException("Unknown operator: " + wave.op)
        }
    }

    companion object {
        fun fromData(data: ActiveWaveFrameData, point: Point, t: Float, isActive: Boolean): Layer {
            with(data) {
                val wave = ConfigData.wave
                val freq = wave.frequency
                val center: Complex? = if (!isActive || wave.hasCenter) WaveCalc.calc(point, scaledCenter, t, freq, centerMass) else null
                val hour: Complex? = if (wave.hasHours) WaveCalc.calc(point, waveHr, t, freq, hourMass) else null
                val min: Complex? = if (wave.hasMinutes) WaveCalc.calc(point, waveMin, t, freq, minuteMass) else null
                val sec: Complex? = if (isActive && wave.hasSeconds) WaveCalc.calc(point, waveSec, t, freq, secondMass) else null
                return Layer(wave, center, hour, min, sec)
            }
        }
    }
}
