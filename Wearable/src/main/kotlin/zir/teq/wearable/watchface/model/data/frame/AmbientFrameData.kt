package zir.teq.wearable.watchface.model.data.frame

import android.graphics.Canvas
import android.graphics.Rect
import zir.teq.wearable.watchface.model.ConfigData
import zir.watchface.DrawUtil
import java.util.*

open class AmbientFrameData(cal: Calendar, bounds: Rect, can: Canvas) : FrameData(cal, bounds) {
    val minLength = unit * DrawUtil.calcDistFromBorder(can, ConfigData.stroke) / DrawUtil.PHI
    val hrLength = minLength / DrawUtil.PHI

    val hr = DrawUtil.calcPosition(hrRot, hrLength, unit)
    val min = DrawUtil.calcPosition(minRot, minLength, unit)
    val hour = DrawUtil.HandData(hr, hrRot, center)

    val minute = DrawUtil.HandData(min, minRot, center)
    val ccCenter = DrawUtil.calcCircumcenter(center, hr, min)
    val ccRadius = DrawUtil.calcDistance(min, ccCenter)
}
