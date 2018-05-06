package zir.teq.wearable.watchface.model.data.frame

import Mass
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import zir.teq.wearable.watchface.util.CalcUtil
import java.util.*

open class ActiveWaveFrame(cal: Calendar, bounds: Rect, can: Canvas, res: Int) : ActiveFrame(cal, bounds, can) {
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

    val waveHr = CalcUtil.calcPosition(hrRot, waveHrLength, scaledUnit)
    val waveMin = CalcUtil.calcPosition(minRot, waveMinLength, scaledUnit)
    val waveSec = CalcUtil.calcPosition(secRot, waveSecLength, scaledUnit)
}
