package zir.teq.wearable.watchface.model.data.frame

import Mass
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import zir.watchface.DrawUtil
import java.util.*

open class ActiveWaveFrameData(cal: Calendar, bounds: Rect, can: Canvas, res: Int) : ActiveFrameData(cal, bounds, can) {
    val isUseUneven = false //render an additional line of pixels in the center.
    val timeStamp = cal.timeInMillis
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

    val isProportional = true //TODO
    val centerMass = if (isProportional) Mass.DEFAULT.value else Mass.DEFAULT.value //FIXME
    val hourMass = if (isProportional) Mass.DEFAULT.value else Mass.DEFAULT.value //FIXME
    val minuteMass = if (isProportional) hourMass / DrawUtil.PHI else Mass.DEFAULT.value
    val secondMass = if (isProportional) minuteMass / DrawUtil.PHI else Mass.DEFAULT.value
}