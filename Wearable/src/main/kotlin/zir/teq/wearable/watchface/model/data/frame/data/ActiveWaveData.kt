package zir.teq.wearable.watchface.model.data.frame.data

import Mass
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import zir.teq.wearable.watchface.util.DrawUtil
import java.util.*

open class ActiveWaveData(cal: Calendar, bounds: Rect, can: Canvas, res: Int) : ActiveData(cal, bounds, can) {
    val isUseUneven = false //render an additional line of pixels in the center.
    val timeStampMs = cal.timeInMillis
    val setOff = if (isUseUneven) 1 else 0
    val w = setOff + (bounds.width() / res)
    val h = setOff + (bounds.height() / res)
    private val xRange = 0..(h - 1)
    private val yRange = 0..(w - 1)
    val keys = xRange.flatMap { x -> yRange.map { y -> Point(x, y) } }

    val scaledUnit: Float = (bounds.width() - res) / (res * 2F)
    val scaledCenter = PointF(scaledUnit, scaledUnit)

    val waveSecLength = secLength * scaledUnit / unit
    val waveMinLength = minLength * scaledUnit / unit
    val waveHrLength = hrLength * scaledUnit / unit

    val waveHr = DrawUtil.calcPosition(hrRot, waveHrLength, scaledUnit)
    val waveMin = DrawUtil.calcPosition(minRot, waveMinLength, scaledUnit)
    val waveSec = DrawUtil.calcPosition(secRot, waveSecLength, scaledUnit)

    val isProportional = false //TODO
    val centerMass = Mass.DEFAULT.value
    val hourMass = Mass.DEFAULT.value
    val minuteMass = if (isProportional) Mass.LIGHT.value else Mass.DEFAULT.value
    val secondMass = if (isProportional) Mass.LIGHTER.value else Mass.DEFAULT.value
}
