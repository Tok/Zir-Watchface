package zir.teq.wearable.watchface.model.data.settings.wave

import android.graphics.PointF
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.frame.ActiveWaveFrameData
import zir.teq.wearable.watchface.model.data.types.Complex
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.teq.wearable.watchface.util.WaveCalc

class Layer private constructor(val wave: Wave, val center: Complex?, val hour: Complex?, val min: Complex?, val sec: Complex?) {
    fun all(): List<Complex> = listOfNotNull<Complex>(center, hour, min, sec)
    fun get(): Complex {
        val hasNoValue = center == null && hour == null && min == null && sec == null
        if (hasNoValue) throw IllegalStateException("No values provided.")
        return when (wave.op) {
            Operator.MULTIPLY -> all().fold(all().first()) { total, next -> total * next }
            Operator.ADD -> all().fold(all().first()) { total, next -> total + next }
            else -> throw IllegalArgumentException("Unknown operator: " + wave.op)

        }
    }

    companion object {
        fun fromData(data: ActiveWaveFrameData, point: PointF, t: Double, isActive: Boolean): Layer {
            with(data) {
                val wave = ConfigData.wave
                val center: Complex? = if (!isActive || wave.hasCenter) WaveCalc.calc(point, scaledCenter, t, centerMass) else null
                val hour: Complex? = if (wave.hasHours) WaveCalc.calc(point, waveHr, t, hourMass) else null
                val min: Complex? = if (wave.hasMinutes) WaveCalc.calc(point, waveMin, t, minuteMass) else null
                val sec: Complex? = if (isActive && wave.hasSeconds) WaveCalc.calc(point, waveSec, t, secondMass) else null
                return Layer(wave, center, hour, min, sec)
            }
        }
    }
}