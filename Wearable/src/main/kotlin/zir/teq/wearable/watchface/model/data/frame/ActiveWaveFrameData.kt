package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Wave
import zir.watchface.DrawUtil
import java.util.*

class ActiveWaveFrameData(cal: Calendar, bounds: Rect, can: Canvas) : ActiveFrameData(cal, bounds, can) {
    val isUseUneven = false //render an additional line of pixels in the center.
    val timeStamp = cal.timeInMillis
    val res = ConfigData.wave.resolution.value
    val setOff = if (isUseUneven) 1 else 0
    val w = setOff + (bounds.width() / res)
    val h = setOff + (bounds.height() / res)

    val scaledUnit: Float = (bounds.width() - res) / (res * 2F)
    val scaledCenter = PointF(scaledUnit, scaledUnit)

    val waveSecLength = secLength * scaledUnit / unit
    val waveMinLength = minLength * scaledUnit / unit
    val waveHrLength = hrLength * scaledUnit / unit

    val waveHr = DrawUtil.calcPosition(hrRot, waveHrLength, scaledUnit)
    val waveMin = DrawUtil.calcPosition(minRot, waveMinLength, scaledUnit)
    val waveSec = DrawUtil.calcPosition(secRot, waveSecLength, scaledUnit)

    val isProportional = false //TODO
    val centerMass = if (isProportional) Wave.MASS_DEFAULT.value else Wave.MASS_DEFAULT.value
    val hourMass = if (isProportional) Wave.MASS_DEFAULT.value else Wave.MASS_DEFAULT.value
    val minuteMass = if (isProportional) hourMass / DrawUtil.PHI else Wave.MASS_DEFAULT.value
    val secondMass = if (isProportional) minuteMass / DrawUtil.PHI else Wave.MASS_DEFAULT.value
}